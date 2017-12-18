import { Link } from 'react-router';
import BasicDetails from './BasicDetails';
import Contact from '../../../services/contact';
import User from '../../../services/user';
import CONSTANTS from '../../../constants/app-constant';
import NotificationDetailsWrapper from './NotificationDetailsWrapper';
import http from '../../../mixins/restutils';
import Session from '../../../mixins/sessionUtils';
import Loader from 'react-loader';
import LoaderOptions from '../../../mixins/loader';
import UI from '../../../mixins/ui';

var ContactDetails = createReactClass({
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
            loaded: false
        };
    },

    componentWillMount() {
        this.redirectIfUnauthorized.call(this,
            CONSTANTS.PERMISSION_CODES.contacts.details);
        Session.remove('notificationsModulesList');
    },

    simulatenotificationsModulesListData() {
        var notificationsModulesList = [];
        var tempData = User.getCustomerParams()
        var contactTypesList = [{
            associteCustomerID: tempData && tempData.customerId,
            associteCustomerName: tempData && tempData.customerName,
            notificationTypeId: null,
            notificationTypeName: null,
            contactNotifysList: null,
            projectName: null,
            projectId: ""
        }];
        if (tempData && (tempData.assoCustomerId != tempData.customerId)) {
            var contactTypeListObjMCAC = this.simulateAddAssocCustomer(_.clone(_.last(contactTypesList)), tempData)
            contactTypesList.push(contactTypeListObjMCAC)
        }
        if (tempData && tempData.projectId) {
            var contactTypeListObjProject = this.simulateAddProject(_.clone(_.last(contactTypesList)), tempData)
            contactTypesList.push(contactTypeListObjProject)
        }
        var obj = {
            notificationModuleName: 'Ticket',
            contactTypesList: contactTypesList,
        }
        notificationsModulesList.push(obj)
        return notificationsModulesList
    },

    simulateAddAssocCustomer(obj, tempData) {
        obj.associteCustomerID = tempData && tempData.assoCustomerId
        obj.associteCustomerName = tempData && tempData.associteCustomerName
        return obj;
    },

    simulateAddProject(obj, tempData) {
        obj.projectId = tempData && tempData.projectId
        obj.projectName = tempData && tempData.projectName
        return obj;
    },

    componentDidMount() {
        var url = http.buildRestUrl(CONSTANTS.API_URLS.contacts.details,
            { userId: Session.get('contactId') });
        Contact.getContactData(url, {})
            .then((result) => {
                var notificationsModulesList = _.first(result.notificationsModulesList)
                if (!notificationsModulesList) {
                    result.notificationsModulesList = this.simulatenotificationsModulesListData();
                    notificationsModulesList = _.first(result.notificationsModulesList)
                }
                var search;
                _.each(notificationsModulesList.contactTypesList, function (contactType) {
                    contactType.contactNotifysList = contactType.contactNotifysList ? contactType.contactNotifysList : CONSTANTS.DEFAULT_NOTIFICATION_BEANS
                    if (!_.isEqual(contactType.contactNotifysList, CONSTANTS.DEFAULT_NOTIFICATION_BEANS)) {
                        _.each(CONSTANTS.DEFAULT_NOTIFICATION_BEANS, function (data) {
                            search = _.findWhere(contactType.contactNotifysList, { notificationType: data.notificationType });
                            if (!search) {
                                contactType.contactNotifysList.push(data);
                            }
                        });
                        contactType.contactNotifysList = _.sortBy(contactType.contactNotifysList, 'notificationType')
                    }
                });
                this.state.contact = result;
                this.state.loaded = true;
                this.setState(this.state);
            })
            .catch((err) => UI.notifyError(err))
        Contact.getTypes()
            .then((result) => this.setState({ types: _.reject(result && result.contactTypeBeans, { typeName: CONSTANTS.GENERIC_TYPE }) }))
            .catch((err) => {
                UI.notifyError(err && err.statusText)
            });
    },

    render() {
        let contact = this.state.contact;
        return (
            <section className="peopleWrap">
                <section className="midHeader">
                    <div className="container">
                        <div className="row">
                            <div className="col-xs-10 col-sm-10 col-md-10">
                                <ol className="breadcrumb breadCrumbCustom clearfix">
                                    <li><Link to={User.getSplashScreenURL()}>Back to Home</Link></li>
                                    <li><Link to="dashboard">Back to Dashboard</Link></li>
                                    <li><Link to="contacts">Contact list</Link></li>
                                    <li className="active">Contact detail</li>
                                </ol>
                                <h1 className="mainTitle">People Management</h1>
                            </div>
                        </div>
                    </div>
                </section>

                <Loader loaded={this.state.loaded} top="50%" left="50%" options={LoaderOptions}>
                    <article className="container listStyle peopleManagementDetails">
                        <BasicDetails data={contact} />
                        <NotificationDetailsWrapper contact={contact} contactTypes={this.state.types} />
                    </article>
                </Loader>
            </section>
        )
    }
});

export default ContactDetails;
