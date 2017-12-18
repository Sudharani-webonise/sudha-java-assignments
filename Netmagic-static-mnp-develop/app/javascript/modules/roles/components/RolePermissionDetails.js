import { Link } from 'react-router';
import ModuleNotificationData from './ModuleNotificationData';
import CONSTANTS from '../../../constants/app-constant';
import UI from '../../../mixins/ui';
import Session from '../../../mixins/sessionUtils';

var RolePermissionDetails = createReactClass({
    mixins: [UI],

    getInitialState() {
        return { selectedModule: _.first(Session.get('modulePermissions')) };
    },

    tabClassSet(moduleId) {
        return this.classSet({
            'tabPeople': true,
            'activeTab': moduleId === this.state.selectedModule.moduleId
        });
    },

    changeModule(data) {
        this.setState({ selectedModule: data });
    },

    getModuleNames() {
        return _.map(Session.get('modulePermissions'), function (data, index) {
            return (<li key={index}>
                <a onClick={this.changeModule.bind(null, data) } className={this.tabClassSet(data.moduleId) }>{data.moduleName}</a>
            </li>)
        }.bind(this));
    },

    render() {
        return (
            <div className="notificationWrap">
                <div className="tabContents activeTabContent">
                    <div className="settingTabs">
                        <ul className="clearfix">
                            {this.getModuleNames() }
                        </ul>
                        <div className="outerTabWrap">
                            <ModuleNotificationData functionIds={this.props.functionIds} addRole={this.props.addRole} moduleData={this.state.selectedModule}
                                data={this.props.data} updateRoleName={this.props.updateRoleName}/>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
});

export default RolePermissionDetails;
