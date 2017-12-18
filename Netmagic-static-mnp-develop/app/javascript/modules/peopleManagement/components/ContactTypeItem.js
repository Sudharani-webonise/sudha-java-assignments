import CONSTANTS from '../../../constants/app-constant';
import Severity from './Severity';
import Contact from '../../../services/contact';
import Session from '../../../mixins/sessionUtils';

var ContactTypeItem = createReactClass({

    getInitialState() {
        return { contactNotifysList: null }
    },

    getContectNotifyList(data) {
        var contactNotifysList = null;
        if (_.isArray(data.contactNotifysList)) {
            var list = this.state.contactNotifysList || data.contactNotifysList;
            contactNotifysList = _.map(list, (data, key) => {
                var checkboxData = _.extend({}, this.props.checkboxData, { notificationType: data.notificationType })
                return (<Severity data={data} key={key} editable={this.props.editable}
                    updateNotification={this.props.updateNotification} checkboxData={checkboxData}/>)
            });

        }
        return contactNotifysList;
    },

    getDummyCheckboxes(data) {
        var dummyCheckboxes = (<div>{CONSTANTS.NOT_AVAILABLE}</div>);
        if (data) {
            dummyCheckboxes = _.map(data, (item, key) =>
                (<Severity data={data} key={key} editable={this.props.editable} updateNotification={this.props.updateNotification}/>))
        }
        return dummyCheckboxes;
    },

    getNotificationData(typeId) {
        var notifications = Session.get('notificationConfig');
        var typeNotification = notifications &&
            _.findWhere(notifications.notificationTemplateTypeBeans, { notificationTypeId: typeId })
        var updatedList = typeNotification &&
            typeNotification.notificationTypeDataBeans;
        if (updatedList) {
            this.setState({ contactNotifysList: updatedList });
            var contact = Session.get('contact');
            var contactType = contact.notificationsModulesList[0].contactTypesList[this.props.index];
            _.extend(contactType, {
                contactNotifysList: updatedList,
                notificationTypeId: typeNotification.notificationTypeId,
                notificationTypeName: typeNotification.notificationTypeName
            });
            Session.set('contact', contact);
        } else {
            this.setState({ contactNotifysList: [] });
        }
    },

    showContactTypes() {
        return _.map(this.props.contactTypes, (type, key) => (<li key={key} data-id={type.typeId}
            onClick={this.getNotificationData.bind(null, type.typeId) }>{type.typeName}</li>)
        );
    },

    render() {
        let props = this.props;
        let list = this.state.contactNotifysList || props.data.contactNotifysList;
        let dummyCheckboxCount = _.range(props.maxSeverityTypes - (list && list.length || 0));
        let NA = CONSTANTS.NOT_AVAILABLE;
        return (
            <div className="customerWrapper clearfix">
                <div className="contactTypeGrid pull-left">
                    <div className="settingHead">{props.data.associteCustomerName }</div>
                    <div className="">{props.data.projectName }</div>
                    <div className="settingTitle">{this.props.data.notificationTypeName }</div>
                </div>
                <div className="clearfix">
                    {this.getContectNotifyList(props.data) }
                    {this.getDummyCheckboxes(dummyCheckboxCount) }
                </div>
            </div>
        );
    }
});

export default ContactTypeItem;
