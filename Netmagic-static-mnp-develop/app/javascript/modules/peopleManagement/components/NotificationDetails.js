import { Link } from 'react-router';
import ModuleNotificationData from './ModuleNotificationData';
import CONSTANTS from '../../../constants/app-constant';
import Session from '../../../mixins/sessionUtils';
import Utility from '../../../mixins/basicUtils';
import User from '../../../services/user';

var NotificationDetails = createReactClass({
    contextTypes: {
        router: React.PropTypes.object
    },

    getInitialState() {
        return {
            selectedCustomers: []
        }
    },

    getNotificationData(data) {
        let notificationModule = null;
        let editable = this.props.editable;
        if(!Session.get('notificationsModulesList')) {
            Session.set('notificationsModulesList', data);
        }
        data = Session.get('notificationsModulesList') || data;
        if (data) {
            notificationModule = _.map(data, (data, key) => {
                return (<ModuleNotificationData moduleData={data} key={key} editable={editable} contactTypes={this.props.contactTypes}
                    updateNotification={this.updateNotification}/>)
            });
        }
        return notificationModule;
    },

    updateData() {
        var {notificationData, contactTypes, selectedCustomers} = this.props;
        var projectCustomerCombo = [];
        let userObj = User.getCustomerParams();
        var firstData = _.first(notificationData);
        var contactNotifysList = firstData && this.getContactNotyfysList(firstData.notificationTypeDataBeans) ||
            CONSTANTS.DEFAULT_NOTIFICATION_BEANS;
        if (!_.findWhere(selectedCustomers, { cvCrmId: userObj.customerId})) {
            projectCustomerCombo.push({
                associteCustomerID: userObj.customerId,
                associteCustomerName: userObj.customerName,
                notificationTypeId: null,
                notificationTypeName: null,
                contactNotifysList: this.getNotificationFromSession(User.getMainCustomer('customerId') || User.getMainCustomer('cvCrmId')) || contactNotifysList,
                projectName: null,
                projectId: ""
            });
        }

        if (notificationData && notificationData.length && contactTypes && contactTypes.length || !(notificationData && notificationData.length)) {
            _.each(selectedCustomers, (customer) => {
                var projectId = customer.projectId;
                if (projectId) {
                    projectCustomerCombo.push({
                        associteCustomerID: customer.cvCrmId,
                        associteCustomerName: customer.cvName,
                        notificationTypeId: 0,
                        notificationTypeName: null,
                        contactNotifysList: this.getNotificationFromSession(customer.cvCrmId, customer.projectId)
                        || contactNotifysList,
                        projectId: customer.projectId || "",
                        projectName: customer.projectName
                    });
                } else {
                    projectCustomerCombo.push({
                        associteCustomerID: customer.cvCrmId,
                        associteCustomerName: customer.cvName,
                        notificationTypeId: 0,
                        notificationTypeName: null,
                        contactNotifysList: this.getNotificationFromSession(customer.cvCrmId) || contactNotifysList,
                        projectId: "",
                        projectName: ""
                    });
                }
            })
        }
        Session.set('notificationsModulesList', [{
            notificationModuleName: 'Ticket',
            contactTypesList: projectCustomerCombo
        }]);
        Session.set('notificationPropsData', this.props.notificationData);
    },

    getNotificationFromSession(customerId, projectId) {
        var sessionNotification = Utility.getVal(Session.get('notificationsModulesList'), '[0].contactTypesList');
        var condition = projectId ? { associteCustomerID: customerId, projectId: projectId }
            : { associteCustomerID: customerId };
        var existingSessionData = _.findWhere(sessionNotification, condition);
        return existingSessionData && existingSessionData.contactNotifysList;
    },

    getContactNotyfysList(list) {
        var no = CONSTANTS.NO;
        return _.map(list, (item) => {
            return _.extend(item, {
                sendEmailNotification: no,
                sendSmsNotification: no
            });
        })
    },

    getModuleName(data) {
        var moduleName = null;
        if (data && data.length) {
            moduleName = _.map(data, function (data, key) {
                return (<li key={key}>
                    <a href={"#" + data.notificationModuleName}
                        className="tabPeople activeTab">{data.notificationModuleName}</a>
                </li>)
            });
        } else {
            moduleName = (<li>
                <a href="javascript:void(0);" className="tabPeople activeTab">{'Ticket'}</a>
            </li>)
        }
        return moduleName;
    },

    customerNotificationData() {
        var {editable, contactTypes} = this.props;
        this.updateData();
        var notificationsModulesList = Session.get('notificationsModulesList') || [];
        Session.set('notificationsModulesList', notificationsModulesList);
        return _.map(notificationsModulesList, (data, key) => {
            return (<ModuleNotificationData  moduleData={data} key={key} editable={editable}
                contactTypes={contactTypes} updateNotification={this.updateNotification}/>)
        });
    },

    updateNotification(checked, data) {
        var notificationsModulesList = Session.get('notificationsModulesList');
        var notificationPropsData = Session.get('notificationPropsData');
        if (notificationsModulesList && data) {
            var notification = _.first(notificationsModulesList);
            var contactTypesList = notification.contactTypesList;
            var contactType = _.findWhere(contactTypesList, { associteCustomerID: data.customerId, projectId: data.projectId });
            if (!contactType) {
                var notificationData = _.first(Session.get('notificationPropsData'));
                var notificationDataContacts = notificationData && notificationData.contactTypesList;
                var contactType = _.findWhere(notificationDataContacts, { associteCustomerID: data.customerId });
                var notificationType = contactType &&
                    _.findWhere(contactType.contactNotifysList, { notificationType: data.notificationType });
                notificationType[data.type] = CONSTANTS[checked ? 'YES' : 'NO'];
                Session.set('notificationsModulesList', notificationPropsData);
                return;
            }
            var notificationType = contactType &&
                _.findWhere(contactType.contactNotifysList, { notificationType: data.notificationType });
            notificationType[data.type] = CONSTANTS[checked ? 'YES' : 'NO'];
            Session.set('notificationsModulesList', notificationsModulesList);
        } else {
            var notificationsModulesList = notificationsModulesList ||
                Session.get('existingData');
            var notification = _.first(notificationsModulesList);
            var contactTypesList = notification.contactTypesList;
            var contactType = _.findWhere(contactTypesList, { associteCustomerID: data.customerId, projectId: data.projectId });
            var notificationType = contactType &&
                _.findWhere(contactType.contactNotifysList, { notificationType: data.notificationType });
            notificationType[data.type] = CONSTANTS[checked ? 'YES' : 'NO'];
            Session.set('notificationsModulesList', notificationsModulesList);
        }
    },

    render() {
        var {data, selectedCustomers} = this.props;
        Session.set('existingData', data);
        var ticketModule = _.first(data);
        Session.set('customerModuleData', ticketModule && ticketModule.contactTypesList);
        return (
            <div className="notificationWrap">
                <div className="tabContents activeTabContent">
                    <div className="settingTabs">
                        <ul className="clearfix">
                            {this.getModuleName(data) }
                        </ul>
                        <div className="outerTabWrap">
                            {selectedCustomers ? this.customerNotificationData() : this.getNotificationData(data) }
                        </div>
                    </div>
                </div>
            </div>
        )
    }
});

export default NotificationDetails;
