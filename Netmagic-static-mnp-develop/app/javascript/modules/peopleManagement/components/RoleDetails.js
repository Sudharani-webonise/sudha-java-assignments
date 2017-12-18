import { Link } from 'react-router';
import ModuleNotificationData from './ModuleNotificationData';
import RolePermissions from './RolePermissions';
import CONSTANTS from '../../../constants/app-constant';
import Session from '../../../mixins/sessionUtils';
import UI from '../../../mixins/ui';

var RoleDetails = createReactClass({
  mixins: [UI, Session],

  getInitialState() {
    return {
      activeModule: _.first(this.get('modulePermissions'))
    };
  },

  selectModule(module) {
    this.setState({ activeModule: module || this.state.activeModule });
  },

  getTabClass(id) {
    return this.classSet({
      tabPeople: true,
      activeTab: this.state.activeModule.moduleId === id
    });
  },

  showModuleNames() {
    var modules = Session.get('modulePermissions');
    return _.isArray(modules) && _.map(modules, (module, index) => {
      return (<li key={index} onClick={this.selectModule.bind(null, module)}>
          <a href="javascript:void(0);"
          className={this.getTabClass(module.moduleId)}>{module.moduleName}</a>
        </li>);
    });
  },

  showRoleDetails() {
    return (<RolePermissions activeModule={this.state.activeModule} data={this.props.data}
      editable={this.props.editable} selectedCustomers={this.props.selectedCustomers} roleDetails={this.props.roleDetails} editContact={this.props.editContact}/>)
  },

  render () {
    return (
      <div className="notificationWrap">
        <div className="tabContents activeTabContent">
          <div className="settingTabs">
            <ul className="clearfix">
            {this.showModuleNames()}
            </ul>
            <div className="outerTabWrap editableTabWrap">
            {this.showRoleDetails()}
            </div>
          </div>
        </div>
      </div>
    )
  }
});

export default RoleDetails;
