import { Link } from 'react-router';
import CONSTANTS from '../../../constants/app-constant';
import Session from '../../../mixins/sessionUtils';
import Utility from '../../../mixins/basicUtils';
import UI from '../../../mixins/ui';
import Contact from '../../../services/contact';
import Role from '../../../services/role';
import EditDetails from './EditDetails';
import NotificationDetailsWrapper from './NotificationDetailsWrapper';
import http from '../../../mixins/restutils';
import PostRequestLoader from '../../commons/Loader';
import Ticket from '../../../services/ticket';
import User from '../../../services/user';

var ContactEdit = createReactClass({
    contextTypes: {
        router: React.PropTypes.object
    },

    getInitialState() {
        return {
            configData: {},
            roleDetails: {},
            types: [],
            showLoader: false,
            selectedCustomers: [],
            projects: [],
            nmUser: false,
            customers: []
        }
    },

    componentWillMount() {
        let userObj = User.getCustomerParams();
        Contact.getCallingConfigData()
            .then((result) => this.setState({ configData: result }))
            .catch((err) => UI.notifyError(err && err.statusText));
        Role.fetchRoleDetails(userObj.customerId)
            .then((result) => this.setState({ roleDetails: result }))
            .catch((err) => UI.notifyError(err && err.statusText));
        Contact.getTypes()
            .then((result) => this.setState({ types: _.reject(result && result.contactTypeBeans, { typeName: CONSTANTS.GENERIC_TYPE }) }))
            .catch((err) => {
                UI.notifyError(err && err.statusText)
            });
        Contact.getNotificationData(userObj.customerId)
            .then((result) => Session.set('notificationConfig', result))
            .catch((err) => UI.notifyError(err && err.statusText));
        Ticket.getAssociatedCustomers()
            .then((result) => {
                var associatedCustomers = _.reject(result.listOfSupportToCustomerInfo,
                    (item) => item.cvCrmId == (userObj.customerId));
                var contact = Session.get('contact');
                var contactTypesList = _.first(contact.notificationsModulesList).contactTypesList;
                var item = {};
                _.each(associatedCustomers, (cust) => {
                    item =_.findWhere(contactTypesList, { associteCustomerID: cust.cvCrmId })
                    if(item) {
                        associatedCustomers = _.reject(associatedCustomers, { cvCrmId: Number(item.associteCustomerID) })
                    }
                });
                this.setState({ customers: associatedCustomers });
            })
            .catch((err) => UI.notifyError(err && err.statusText));
        Session.set('existingCustomers', []);
        Session.set('removedContactTypeSubTypeBeansList', []);
    },

    componentWillUnmount() {
        Session.removeItems(['notificationsModulesList', 'contactTypeSubTypeBeansList',
            'updatedRolePermissions', 'existingCustomers',
            'removedContactTypeSubTypeBeansList']);
    },

    updateContactInfo(event) {
        return function (event) {
            let contact = Session.get('contact');
            var validationError = this.getValidationErrorMessage();
            if (validationError) {
                return UI.notifyError(validationError);
            }
            var callingHours = _.first(this.get('#callingHours'));
            var callingDaysId = this.get('#callingDays') && _.first(this.get('#callingDays')).dataset.selectedItem;
            var callingDays = _.findWhere(this.state.configData.mynmContactCallingTypeBeans,
                { contactCallingTypeId: Number(callingDaysId) });
            contact.notificationsModulesList = Session.get('notificationsModulesList') || contact.notificationsModulesList;
            let userObj = User.getCustomerParams();
            var contactDetails = _.chain(contact)
                .extend({
                    primaryEmailId: _.first(this.get('#email')).value,
                    secondaryEmailId: _.first(this.get('#secondaryEmail')).value,
                    firstName: _.first(this.get('#firstname')).value,
                    lastName: _.first(this.get('#lastname')).value,
                    callingPreferredTime: callingHours &&
                        callingHours.dataset.selectedItem ||
                        contact.callingPreferredTime || '',
                    callingPreferredDaysTypeId: callingDays && callingDays.contactCallingTypeId || contact.callingPreferredDaysTypeId || '',
                    callingPreferredDaysTypeName: callingDays && callingDays.contactCallingTypeName || contact.callingPreferredDaysTypeName || '',
                    mainCustomerId: userObj.customerId,
                    mainCustomerName: userObj.customerName,
                    contactTypeSubTypeBeansList:this.getFilteredContactTypeBeansList(Session.get('contactTypeSubTypeBeansList'), CONSTANTS.YES) || contact.contactTypeSubTypeBeansList,
                    removedContactTypeSubTypeBeansList: this.getFilteredContactTypeBeansList(Session.get('removedContactTypeSubTypeBeansList'), CONSTANTS.NO) || [],
                    mynmRoleFunctionsBeanList: this.getRolePermission(contact),
                    activeUser: 'Y',
                    designation: _.first(this.get('#designation')).value,
                    contactNumbersInfoList: this.getContactNumbers(),
                }).omit('accessCardStatus', 'callingHrs', 'kycStatus', 'mainCustCrmId', 'mainCustName')
                .value();
            this.setState({ showLoader: true });
            Contact.updateInfo(contactDetails)
                .then((result) => {
                    this.setState({ showLoader: false });
                    if(result.errCode === CONSTANTS.ERROR_CODE.compulsaryContactType) {
                        UI.notifyError(result.errMsg);
                    } else if(result.errCode === CONSTANTS.STATUS_CODES.internalServerError) {
                        UI.notifyError(result.errMsg);
                    } else {
                        UI.notifySuccess(CONSTANTS.UI_MESSAGES.contactUpdated);
                        this.context.router.push(`contacts/details`);
                    }
                })
                .catch((error) => {
                    this.setState({ showLoader: false });
                    UI.notifyError(error);
                });
        }.bind(this);
    },

    getFilteredContactTypeBeansList(contactTypeBeansList, flag) {
        var contactTypeBeansList = contactTypeBeansList;
        _.each(contactTypeBeansList, (contactType) => {
            if(contactType.contactSubTypeBeans && contactType.contactSubTypeBeans.length) {
                contactType.contactSubTypeBeans = _.filter(contactType.contactSubTypeBeans, { isActive: flag });
            }
        });
        _.each(contactTypeBeansList, (contactType) => {
            if((CONSTANTS.CONTACT_SUB_TYPES.indexOf(contactType.typeName) > -1) && !(contactType.contactSubTypeBeans && contactType.contactSubTypeBeans.length)) {
                contactTypeBeansList = _.reject(contactTypeBeansList, {
                    typeName: contactType.typeName
                })
            }
        });
        return contactTypeBeansList;
    },

    getValidationErrorMessage() {
        var contact = Session.get('contact');
        const message = CONSTANTS.UI_MESSAGES;
        var emptyPermissions = '';
        var emptyFields = _.every(this.get('.mandatory'), (item) => {
            if (_.contains(item.classList, 'customSelectList')) {
                return item.dataset && item.dataset.selectedItem
            } else {
                return item.value;
            }
        }) ? '' : message.emptyFields;
        var phones = this.get('.phone');
        var wrongPhone = (_.every(phones,
            (phone) => !phone.value || Utility.checkPhone(phone.value))) ? '' : message.wrongPhone;
        var emails = this.get('.email');
        var wrongEmail = (_.every(emails,
            (email) => !email.value || Utility.checkEmail(email.value))) ? '' : message.wrongEmail;
        var contactBean = Session.get('contactTypeSubTypeBeansList') || contact.contactTypeSubTypeBeansList;
        contactBean = this.getFilteredContactTypeBeansList(contactBean, CONSTANTS.YES);
        var mandatoryCheck = _.isArray(contactBean) && contactBean.length ?
            '' : message.mandatoryCheckbox;
        if(contact && contact.isMynmUser === CONSTANTS.YES) {
            var customerData = Session.get('customerModuleData');
            var updatedPermissions = this.removeObjects(Session.get('updatedRolePermissions'));
            var mynmRoleFunctionsBeanList = this.removeObjects(contact.mynmRoleFunctionsBeanList);
            mynmRoleFunctionsBeanList = this.filterData(updatedPermissions, mynmRoleFunctionsBeanList);
            updatedPermissions = _.union(updatedPermissions, mynmRoleFunctionsBeanList);
            if(_.isArray(updatedPermissions) && !updatedPermissions.length) {
                emptyPermissions = message.roleMandatory;
            }
        }
        var errorMessage = emptyFields || wrongPhone || wrongEmail ||
            mandatoryCheck || emptyPermissions;
        return _.some([emptyFields, wrongPhone, wrongEmail,
            mandatoryCheck, emptyPermissions]) ? errorMessage : '';
    },

    filterData(updateRoles, currentRoles) {
        _.each(updateRoles, (data) =>{
            var item = _.findWhere(currentRoles,{associteCustomerID: data.associteCustomerID});
            if(item) {
                currentRoles = _.reject(currentRoles, item);
            }
        });
        return currentRoles;
    },

    getRolePermission(contact) {
        var permissions = this.removeObjects(contact && contact.mynmRoleFunctionsBeanList) || [];
        var updatedData = _.union(Session.get('updatedRolePermissions'),
            Session.get('existingCustomers'));
        updatedData = this.removeObjects(updatedData);
        permissions = this.filterData(updatedData, permissions);
        permissions = this.removeObjects(_.union(permissions, updatedData));
        return permissions;
    },

    removeObjects(list) {
        _.each(list, (role) => {
            if(_.has(role, 'cvCrmId')) {
                list = _.reject(list, { cvCrmId: role.cvCrmId})
            }
        });
        _.each(list, (role) => {
            var item = _.findWhere(list, { notificationTypeName: role.notificationTypeName });
            if(item) {
                list = _.reject(list, item)
            }
        });
        return list;
    },

    getContactNumbers() {
        var arr = [];
        var callingHoursInfo = this.get('.timeWrapper');
        _.each(this.get('.contactNumberInfo'), function (contactInfo, index) {
            var info = $(contactInfo), callingHours = callingHoursInfo[index];
            var getSelectedData = (parent, id) =>
                parent && $(parent).find('[data-id="' + id + '"] p.selectContent')[0].textContent || null;
            var number = _.first(info.find('[data-id="contactNumber"]'));
            var type = _.first(info.find('[data-id="contactCategory"]'));
            if (number && number.value) {
                arr.push({
                    contactNumberCategory: type.value,
                    countryCode: getSelectedData(info, 'countryCode'),
                    contactNumber: number.value
                });
            }
        });
        return arr;
    },

    get(selector) {
        return this.refs.editDetails &&
            $(this.refs.editDetails).find(selector);
    },


    getProjectsForCustomer(customer, checked) {
        var contact = Session.get('contact');
        var contactTypesList = _.first(contact.notificationsModulesList).contactTypesList;
        var existingCombination = _.findWhere(contactTypesList, { associteCustomerID: customer.cvCrmId });
        var mynmRoleFunctionsBeanList = contact.mynmRoleFunctionsBeanList || [];
        var updatedRolePermissions = Session.get('updatedRolePermissions') || [];
        var customerModuleData = Session.get('customerModuleData') || [];
        if (checked && !existingCombination) {
            contactTypesList.push(customer);
            mynmRoleFunctionsBeanList.push(customer);
            _.first(contact.notificationsModulesList).contactTypesList = contactTypesList;
            Session.set('contact', contact);
            updatedRolePermissions.push(customer);
            customerModuleData.push(customer);
            this.getProjects(customer);
        } else {
            contactTypesList = _.reject(contactTypesList,
                (item) => item.associteCustomerID == customer.cvCrmId);
            _.first(contact.notificationsModulesList).contactTypesList = contactTypesList;
            mynmRoleFunctionsBeanList = _.reject(mynmRoleFunctionsBeanList,(item) => item.associteCustomerID == customer.cvCrmId);
            mynmRoleFunctionsBeanList = _.reject(mynmRoleFunctionsBeanList,(item) => item.cvCrmId == customer.cvCrmId);
            contact.mynmRoleFunctionsBeanList = mynmRoleFunctionsBeanList;
            updatedRolePermissions = _.reject(updatedRolePermissions,(item) => item.associteCustomerID == customer.cvCrmId);
            updatedRolePermissions = _.reject(updatedRolePermissions,(item) => item.cvCrmId == customer.cvCrmId);
            customerModuleData = _.reject(customerModuleData, (item) => item.cvCrmId == customer.cvCrmId);
            this.state.projects = _.reject(this.state.projects, (item) => item.cvCrmId === customer.cvCrmId);
            this.setState({ projects: this.state.projects });
        }
        contact.mynmRoleFunctionsBeanList = mynmRoleFunctionsBeanList;
        Session.set('contact', contact);
        Session.set('updatedRolePermissions', updatedRolePermissions);
        Session.set('customerModuleData', customerModuleData);
    },

    addOrRemoveProject(project, event) {
        var contact = Session.get('contact');
        var contactTypesList = _.first(contact.notificationsModulesList).contactTypesList;
        var mynmRoleFunctionsBeanList =  contact.mynmRoleFunctionsBeanList || [];
        var updatedRolePermissions = Session.get('updatedRolePermissions') || [];
        var customerModuleData = Session.get('customerModuleData') || [];
        var checked = event.currentTarget.checked;
        if (checked) {
            var targetCustomer = _.findWhere(contactTypesList, { associteCustomerID: project.cvCrmId });
            var newCustomer = _.clone(targetCustomer);
            newCustomer.selected = checked;
            var targetProject = _.findWhere(this.state.projects,
                { cvCrmId: project.cvCrmId, projectId: project.projectId });
            targetProject.selected = checked;
            newCustomer.projectName = targetProject.projectName || '';
            newCustomer.projectId = targetProject.projectId || '';
            contactTypesList.push(newCustomer);
            mynmRoleFunctionsBeanList.push(newCustomer);
            updatedRolePermissions.push(newCustomer);
            customerModuleData.push(newCustomer);
        } else {
            contactTypesList = _.chain(contactTypesList).reject((customer) => customer.projectId == project.projectId).value();
            mynmRoleFunctionsBeanList = _.chain(mynmRoleFunctionsBeanList).reject((customer) => customer.projectId == project.projectId).value();
            updatedRolePermissions = _.chain(updatedRolePermissions).reject((customer) => customer.projectId == project.projectId).value();
            customerModuleData = _.chain(customerModuleData).reject((customer) => customer.projectId == project.projectId).value();    
        }
        _.first(contact.notificationsModulesList).contactTypesList = contactTypesList;
        contact.mynmRoleFunctionsBeanList = mynmRoleFunctionsBeanList;
        Session.set('contact', contact);
        Session.set('updatedRolePermissions', updatedRolePermissions);
        Session.set('customerModuleData', customerModuleData);
        this.setState(this.state);
    },

    getProjects(customer) {
        let userObj = User.getCustomerParams();
        var params = {
            id: userObj.customerId,
            associatedId: userObj.assoCustomerId
        };
        var contact = Session.get('contact');
        var contactTypesList = _.first(contact.notificationsModulesList).contactTypesList;
        Ticket.getProjects(params)
            .then((result) => {
                var targetCustomer = _.findWhere(contactTypesList, { cvCrmId: customer.cvCrmId });
                var updatedProjects = _.map(result,
                    (item) => _.extend(item, { selected: false }, targetCustomer));
                this.setState({
                    projects: _.union(this.state.projects, updatedProjects)
                });
            })
            .catch((err) => {
                UI.notifyError(err && err.statusText);
        });
    },

    updateIsMyUser(event) {
        var contact = Session.get('contact')
        if (event.currentTarget.dataset.id === CONSTANTS.NO) {
            this.state.nmUser = true;
            contact.isMynmUser = CONSTANTS.YES;
        } else {
            this.state.nmUser = false;
            contact.isMynmUser = CONSTANTS.NO;
        }
        Session.set('contact', contact);
        this.setState(this.state);
    },

    render() {
        let contact = Session.get('contact');
        var {projects, types, configData, nmUser} = this.state;
        var notificationData = Utility.getVal(contact, 'notificationsModulesList');
        return (
            <section className="ticketListSection outStandingWrap peopleWrap addContactWrap">
                <section className="midHeader">
                    <div className="container">
                        <div className="row">
                            <div className="col-xs-9 col-sm-9 col-md-9">
                                <ol className="breadcrumb breadCrumbCustom clearfix">
                                    <li><Link to={User.getSplashScreenURL()}>Back to Home</Link></li>
                                    <li><Link to="dashboard">Back to Dashboard</Link></li>
                                    <li><Link to="contacts">Contact list</Link></li>
                                    <li className="active">Edit Contact</li>
                                </ol>
                                <h1 className="mainTitle">Edit Contact</h1>
                            </div>
                            <div className="col-xs-3 col-sm-3 col-md-3">
                                <Link to={`contacts/details`} className="whiteCancelBtn pull-right">Cancel</Link>
                                <a href="javascript:void(0);" className="darkPinkBtn pull-right" ref="saveButton" onClick={this.updateContactInfo()}>Save</a>
                            </div>
                        </div>
                    </div>
                </section>
                <PostRequestLoader show={this.state.showLoader} />
                <article className="container" ref="editDetails">
                    <EditDetails data={contact} configData={configData} contactTypes={types} addOrRemoveProject={this.addOrRemoveProject}
                        getProjects={this.getProjectsForCustomer}
                        projects={projects} updateIsMyUser={this.updateIsMyUser}
                        nmUser={nmUser}
                        customers={this.state.customers}
                        editable={true}/>
                        <NotificationDetailsWrapper contact={contact}
                            editable={true} contactTypes={this.state.types}
                            roleDetails={this.state.roleDetails}
                            notificationData={notificationData}
                            editContact={true}/>
                </article>
            </section>
        );
    }
});

export default ContactEdit;
