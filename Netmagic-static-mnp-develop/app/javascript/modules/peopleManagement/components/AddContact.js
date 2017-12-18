import { Link } from 'react-router';
import EditDetails from './EditDetails';
import Contact from '../../../services/contact';
import User from '../../../services/user';
import Role from '../../../services/role';
import Ticket from '../../../services/ticket';
import CONSTANTS from '../../../constants/app-constant';
import NotificationDetailsWrapper from './NotificationDetailsWrapper';
import http from '../../../mixins/restutils';
import Session from '../../../mixins/sessionUtils';
import Utility from '../../../mixins/basicUtils';
import UI from '../../../mixins/ui';
import PostRequestLoader from '../../commons/Loader';
import { Modal, ModalHeader, ModalTitle, ModalClose, ModalBody, ModalFooter} from 'react-modal-bootstrap';


var AddContact = createReactClass({
    mixins: [User],
    contextTypes: {
        router: React.PropTypes.object
    },

    getInitialState() {
        return {
            contact: {
                userId: 0,
                activeUser: null,
                mainCustCrmId: null,
                mainCustName: null,
                firstName: null,
                lastName: null,
                isMynmUser: CONSTANTS.NO,
                designation: null,
                primaryEmailId: null,
                secondaryEmailId: null,
                kycStatus: null,
                accessCardStatus: null,
                callingHrs: null,
                contactTypeSubTypeBeansList: [],
                contactNumbersInfoList: [],
                rolesModulesList: [],
                notificationsModulesList: [],
            },
            types: [],
            roleDetails: {},
            configData: {},
            showLoader: false,
            customers: [],
            projects: [],
            selectedCustomers: [],
            nmUser: false,
            modalState: false,
            modalErrorMessage: null
        };
    },

    componentWillMount() {
        this.redirectIfUnauthorized.call(this,
            CONSTANTS.PERMISSION_CODES.contacts.add);
    },

    componentWillUnmount() {
        Session.removeItems(['notificationsModulesList', 'updatedRolePermissions',
            'contactTypeSubTypeBeansList']);
    },

    componentDidMount() {
        let userObj = User.getCustomerParams();
        Contact.getCallingConfigData()
            .then((result) => this.setState({ configData: result }))
            .catch((err) => UI.notifyError(err && err.statusText));
        Contact.getTypes()
            .then((result) => this.setState({ types: _.reject(result && result.contactTypeBeans, { typeName: CONSTANTS.GENERIC_TYPE }) }))
            .catch((err) => UI.notifyError(err && err.statusText));
        Role.fetchRoleDetails(userObj.customerId)
            .then((result) => this.setState({ roleDetails: result }))
            .catch((err) => UI.notifyError(err && err.statusText));
        Contact.getNotificationData(userObj.customerId)
            .then((result) => this.setState({ notificationData: result && result.notificationTemplateTypeBeans }))
            .catch((err) => UI.notifyError(err && err.statusText));
        Ticket.getAssociatedCustomers()
            .then((result) => {
                var associatedCustomers = _.reject(result.listOfSupportToCustomerInfo,
                    (item) => item.cvCrmId == (userObj.customerId));
                this.setState({ customers: associatedCustomers });
            })
        .catch((err) => UI.notifyError(err && err.statusText));
    },

    getProjectsForCustomer(customer, checked) {
        var selectedCustomers = this.state.selectedCustomers;
        var updatedRolePermissions = Session.get('updatedRolePermissions') || [];
        if (checked) {
            selectedCustomers.push(customer);
            this.getProjects(customer);
        } else {
            selectedCustomers = _.reject(selectedCustomers,
                (item) => item.cvCrmId == customer.cvCrmId);
            updatedRolePermissions = _.reject(updatedRolePermissions, (item) => item.associteCustomerID == customer.cvCrmId );
            this.state.projects = _.reject(this.state.projects, (item) => item.cvCrmId == customer.cvCrmId);
            this.setState({ projects: this.state.projects });
        }
        Session.set('updatedRolePermissions', updatedRolePermissions);
        this.setState({ selectedCustomers: selectedCustomers });
    },

    addOrRemoveProject(project, event) {
        var checked = event.currentTarget.checked;
        if (checked) {
            var targetCustomer = _.findWhere(this.state.selectedCustomers, { cvCrmId: project.cvCrmId });
            var newCustomer = _.clone(targetCustomer);
            newCustomer.selected = checked;
            var targetProject = _.findWhere(this.state.projects,
                { cvCrmId: project.cvCrmId, projectId: project.projectId });
            targetProject.selected = checked;
            newCustomer.projectName = targetProject.projectName || '';
            newCustomer.projectId = targetProject.projectId || '';
            this.state.selectedCustomers.push(newCustomer);
        } else {
            this.state.selectedCustomers = _.chain(this.state.selectedCustomers).reject((customer) => customer.projectId === project.projectId).value();
        }
        this.setState(this.state);
    },

    getProjects(customer) {
        let userObj = User.getCustomerParams();
        var params = {
            id: userObj.customerId,
            associateCustomerId: userObj.assoCustomerId
        };
        Ticket.getProjects(params)
            .then((result) => {
                var targetCustomer = _.findWhere(this.state.selectedCustomers, { cvCrmId: customer.cvCrmId });
                var updatedProjects = _.map(result,
                    (item) => _.extend(item, { selected: false }, targetCustomer));
                this.setState({
                    projects: _.union(this.state.projects, updatedProjects),
                    selectedCustomers: this.state.selectedCustomers
                });
            })
            .catch((err) => {
                UI.notifyError(err && err.statusText);
        });
    },

    addContact() {
        var validationError = this.getValidationErrorMessage();
        if (validationError) {
            return UI.notifyError(validationError);
        }
        let userObj = User.getCustomerParams();
        var detailsRef = this.refs.details.refs;
        var callingDaysId = Utility.getVal(detailsRef, 'callingDays.dataset.selectedItem');
        var callingDays = _.findWhere(this.state.configData.mynmContactCallingTypeBeans,
            { contactCallingTypeId: Number(callingDaysId) });
        var myNmUser =  this.state.nmUser ? 'Y' : 'N';
        var contact = {
            mainCustomerId: userObj.customerId,
            firstName: detailsRef.firstName && detailsRef.firstName.value,
            lastName: detailsRef.lastName && detailsRef.lastName.value,
            designation: detailsRef.designation && detailsRef.designation.value,
            primaryEmailId: detailsRef.email && detailsRef.email.value,
            secondaryEmailId: detailsRef.secondaryEmail && detailsRef.secondaryEmail.value,
            callingPreferredDaysTypeId: callingDays && callingDays.contactCallingTypeId,
            callingPreferredDaysTypeName: callingDays && callingDays.contactCallingTypeName,
            callingPreferredTime: Utility.getVal(detailsRef, 'callingHours.dataset.selectedItem'),
            contactNumbersInfoList: this.getContactNumbers(),
            contactTypeSubTypeBeansList: Session.get('contactTypeSubTypeBeansList') || [],
            mynmRoleFunctionsBeanList: Session.get('updatedRolePermissions') || [],
            notificationsModulesList: this.getNotificationData() || [],
            isMynmUser: myNmUser
        };
        this.setState({ showLoader: true });
        Contact.createNew(contact)
            .then((result) => {
                if(result.errCode === CONSTANTS.STATUS_CODES.duplicationUser) {
                    this.state.modalState = true;
                    this.state.modalErrorMessage = result.errMsg;
                } else if(result.errCode === CONSTANTS.STATUS_CODES.internalServerError) {
                    this.state.modalState = true;
                    this.state.modalErrorMessage = result.errMsg;
                } else {
                    UI.notifySuccess(CONSTANTS.UI_MESSAGES.contactCreated);
                    this.context.router.push('contacts');
                }
                this.setState({ showLoader: false });
            })
            .catch((err) => {
                this.setState({ showLoader: false });
                UI.notifyError(err && err.textContent || err)
            });
    },

    getNotificationData() {
        var data = Session.get('notificationsModulesList');
        var firstData = _.first(data);
        if (firstData && firstData.contactTypesList) {
            _.each(firstData.contactTypesList, item => {
                delete item.projectName;
            });
        }
        return data;
    },

    getValidationErrorMessage() {
        const message = CONSTANTS.UI_MESSAGES;
        var contact = Session.get('contact');
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
        var contactBean = _.first(Session.get('contactTypeSubTypeBeansList'));
        var mandatoryCheck = (contactBean && _.findWhere(contactBean.contactSubTypeBeans, { isActive: CONSTANTS.YES })) ||
            (contactBean && (contactBean.isActive === CONSTANTS.YES))
            ?
            '' : message.mandatoryCheckbox;
        if(contact && contact.isMynmUser === CONSTANTS.YES) {
            var customerData = Session.get('customerModuleData');
            _.each(customerData, (data)=>{
                data.cvCrmId = Number(data.cvCrmId);
            });
            var updatedPermissions = Session.get('updatedRolePermissions') || [];
            _.each(updatedPermissions, (data)=>{
                data.associteCustomerID = Number(data.associteCustomerID);
            });
            if(_.isArray(updatedPermissions) && !updatedPermissions.length) {
                emptyPermissions = message.roleMandatory;
            }
        }
        var errorMessage = emptyFields || wrongPhone || wrongEmail ||
            mandatoryCheck || emptyPermissions;
        return _.some([emptyFields, wrongPhone, wrongEmail,
            mandatoryCheck, emptyPermissions]) ? errorMessage : '';
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

    updateIsMyUser(event) {
        var contact = this.state.contact || Session.get('contact');
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

    hideModal() {
        this.state.modalState = false;
        this.setState(this.state)
    },

    render () {
        var {contact, types, configData, projects, customers, showLoader, nmUser, modalErrorMessage} =  this.state;
        return (
          <section className="ticketListSection outStandingWrap peopleWrap addContactWrap">
            <section className="midHeader">
              <div className="container">
                <div className="row">
                  <div className="col-xs-9">
                    <ol className="breadcrumb breadCrumbCustom clearfix">
                      <li><Link to={User.getSplashScreenURL()}>Back to Home</Link></li>
                      <li><Link to="dashboard">Back to Dashboard</Link></li>
                      <li><Link to="contacts">Contact list</Link></li>
                      <li className="active">Add New Contact</li>
                    </ol>
                    <h1 className="mainTitle">Add New Contact</h1>
                  </div>
                  <div className="col-xs-3">
                    <Link to={'contacts'} className="whiteCancelBtn pull-right">Cancel</Link>
                    <a href="javascript:void(0);" className="darkPinkBtn pull-right" ref="saveButton" onClick={this.addContact}>Add Contact</a>
                  </div>
                </div>
              </div>
            </section>
            <PostRequestLoader show={this.state.showLoader} />
            <article className="container" ref="editDetails">
              <EditDetails data={contact}
              configData={configData}
               contactTypes={types} ref="details"
                customers={customers}
                projects={projects}
                getProjects={this.getProjectsForCustomer}
                addOrRemoveProject={this.addOrRemoveProject}
                updateIsMyUser={this.updateIsMyUser}
                nmUser={nmUser}/>
              <NotificationDetailsWrapper contact={contact} projects={projects} editable={true} contactTypes={types}
                roleDetails={this.state.roleDetails} selectedCustomers={this.state.selectedCustomers}
                notificationData={this.state.notificationData}/>
            </article>
            <Modal isOpen={this.state.modalState} onRequestHide={this.hideModal}>
              <ModalHeader>
                  <ModalClose onClick={this.hideModal}/>
                  <ModalTitle>User Already Exists</ModalTitle>
              </ModalHeader>
              <ModalBody>
                <p className="warningMsg">{modalErrorMessage}</p>
              </ModalBody>
              <ModalFooter>
                <input type="submit" className="btn btn-primary pull-right" value="OK" onClick={this.hideModal}/>
              </ModalFooter>
            </Modal>
          </section>
        )
      }
    });

export default AddContact;
