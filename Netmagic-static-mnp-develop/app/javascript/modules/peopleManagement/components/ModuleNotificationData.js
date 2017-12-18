import { Link } from 'react-router';
import CONSTANTS from '../../../constants/app-constant';
import Utility from '../../../mixins/basicUtils';
import ContactTypeHeader from './ContactTypeHeader';
import ContactTypeItem from './ContactTypeItem';

var ModuleNotificationData = createReactClass({
    maxSeverityTypes() {
        return this.props.maxSeverity || _.chain(this.props.moduleData.contactTypesList)
            .pluck('contactNotifysList')
            .map((arr) => arr && arr.length)
            .max().value();
    },

    getContactTypesList(data, maxSeverity) {
        var contactTypesList = null;
        if (_.isArray(data)) {
            contactTypesList = _.map(data, (item, key) => {
                var checkboxData = { customerId: item.associteCustomerID, projectId: item.projectId };
                return (<ContactTypeItem data={item} index={key} key={key} maxSeverityTypes={maxSeverity}
                    updateNotification={this.props.updateNotification} editable={this.props.editable}
                    contactTypes={this.props.contactTypes} checkboxData={checkboxData} />)
            }
            );
        }
        return contactTypesList;
    },

    render() {
        let moduleData = this.props.moduleData;
        let maxSeverity = this.maxSeverityTypes();
        return (<div id={moduleData.notificationModuleName} className="settingTabWrap activeContentTab clearfix">
            <ContactTypeHeader data={moduleData.contactTypesList} maxSeverityTypes={maxSeverity}/>
            <div className="roleTypes clearfix">
                { this.getContactTypesList(moduleData.contactTypesList, maxSeverity) }
            </div>
        </div>)
    }
});

export default ModuleNotificationData;
