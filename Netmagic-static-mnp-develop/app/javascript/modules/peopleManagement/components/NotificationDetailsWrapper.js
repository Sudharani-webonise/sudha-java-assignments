import NotificationDetails from './NotificationDetails';
import ContactTypeDetails from './ContactTypeDetails';
import RoleDetails from './RoleDetails';
import CONSTANTS from '../../../constants/app-constant';
import UI from '../../../mixins/ui';
import Utility from '../../../mixins/basicUtils';
import Session from '../../../mixins/sessionUtils';
import User from '../../../services/user';

var NotificationDetailsWrapper = createReactClass({
    mixins: [UI],

    getInitialState() {
        return {
            showNotificationTab: true,
            activeTab: _.first(CONSTANTS.SETTINTS_TABS)
        };
    },


    componentWillReceiveProps(nextProps) {
        let {contact} = nextProps;
        if(nextProps.editContact) {
            this.setNotificationBean(contact && contact.notificationsModulesList);
        }
        if(!nextProps.nmUser && (this.state.activeTab.key == 'role')) {
            this.setState({ activeTab: _.first(CONSTANTS.SETTINTS_TABS) });
        }
    },

    showNotificationDetails(tab) {
        tab && this.setState({ activeTab: tab });
    },

    getActiveTabClass(tabKey) {
        return tabKey === this.state.activeTab.key ? 'active' : '';
    },

    showTabContent() {
        var result = (null);
        let userObj = User.getCustomerParams();
        var {contact, roleDetails, editable, selectedCustomers, contactTypes, notificationData} = this.props;
        var activeTab = this.state.activeTab.key;
        var notifyBean = Utility.getVal(contact,'notificationsModulesList.[0]');
        if(!(contact.mynmRoleFunctionsBeanList && contact.mynmRoleFunctionsBeanList.length)) {
            var roleData = _.chain(notifyBean && notifyBean.contactTypesList)
            .reject({associteCustomerID: userObj.customerId})
            .map((item) => {
                return {
                    cvCrmId: item.associteCustomerID,
                    cvName: item.associteCustomerName,
                    projectId: item.projectId,
                    projectName: item.projectName || ''
                }
            })
            .value();
        }
        var notifyModuleData = _.first(Session.get('notificationsModulesList'));
        var notificationModulesList = notifyModuleData &&
            notifyModuleData.contactTypesList;
        if (activeTab == 'notification') {
            result = (<NotificationDetails data={Session.get('notificationsModulesList') || contact && contact.notificationsModulesList}
                editable={editable} contactTypes={contactTypes} selectedCustomers={selectedCustomers} notificationData={notificationData}/>)
        } else if (activeTab == 'role') {
            result = (contact.isMynmUser === CONSTANTS.NO ? null : <RoleDetails data={contact && contact.mynmRoleFunctionsBeanList ||  Session.get('customerModuleData')}
                editable={editable} roleDetails={roleDetails} selectedCustomers={selectedCustomers || roleData} editContact={this.props.editContact}/>)
        } else {
            result = (<ContactTypeDetails data={contact && contact.contactTypeSubTypeBeansList}
                editable={editable} contactTypes={contactTypes} notifyData={contact && contact.notificationsModulesList}/>)
        }
        return result;
    },

    getFilteredTabs() {
        var tabs = CONSTANTS.SETTINTS_TABS;
        if (this.props.contact.isMynmUser === CONSTANTS.NO) {
            tabs = _.reject(tabs, (tab) => tab.title === "Roles Management")
        }
        return tabs;
    },

    showFilteredTabs() {
        return _.map(this.getFilteredTabs(), (tab,key) => {
            return (<li key={key} onClick={this.showNotificationDetails.bind(null, tab) }>
                <a href="javascript:void(0)" className={this.getActiveTabClass(tab.key) }>{tab.title}</a>
            </li>)
        });
    },

    setNotificationBean(notificationsModulesList) {
        var contact = Session.get('contact');
        var contactNotifysList = CONSTANTS.DEFAULT_NOTIFICATION_BEANS;
        var notifyModule =_.first(notificationsModulesList);
        var contactTypesList = notifyModule && notifyModule.contactTypesList;
        if (notificationsModulesList) {
            _.each(contactTypesList, (customer) => {
                var projectId = customer.projectId;
                if(_.findWhere(contactTypesList, { cvCrmId: customer.cvCrmId })) {
                    if (projectId) {
                        contactTypesList.push({
                            associteCustomerID: customer.cvCrmId,
                            associteCustomerName: customer.cvName,
                            notificationTypeId: 0,
                            notificationTypeName: null,
                            contactNotifysList: contactNotifysList
                            || contactNotifysList,
                            projectId: customer.projectId || "",
                            projectName: customer.projectName
                        });
                    } else {
                        contactTypesList.push({
                            associteCustomerID: customer.cvCrmId,
                            associteCustomerName: customer.cvName,
                            notificationTypeId: 0,
                            notificationTypeName: null,
                            contactNotifysList: contactNotifysList,
                            projectId: "",
                            projectName: ""
                        });
                    }
                    contactTypesList = _.reject(contactTypesList, { cvCrmId: customer.cvCrmId });
                }
            })
            _.first(notificationsModulesList).contactTypesList = contactTypesList;
        }
        Session.set('notificationsModulesList', notificationsModulesList);
        contact.notificationsModulesList = notificationsModulesList;
        Session.set('contact', contact);
    },

    render() {
        return (
            <div className="notificationWrap">
                <ul className="tabList clearfix">
                    { this.showFilteredTabs()}
                </ul>
                {this.showTabContent() }
            </div>
        )
    }
});

export default NotificationDetailsWrapper;
