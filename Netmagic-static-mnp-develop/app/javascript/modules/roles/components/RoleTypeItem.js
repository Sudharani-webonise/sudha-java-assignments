import CONSTANTS from '../../../constants/app-constant';
import Utility from '../../../mixins/basicUtils';
import PermissionCheckbox from './PermissionCheckbox';

var RoleTypeItem = createReactClass({
  mixins: [Utility],
  showRolePermissions (permissions, rolePermissions) {
    return _.map(permissions, function (permission, key) {
      var allowed = _.contains(rolePermissions, permission.functionId);
      return (<PermissionCheckbox data={permission} allowed={allowed} key={key}/>);
    });
  },

  renderRolePermissions (rolePermissionData) {
    var permissionData = (<div>{CONSTANTS.NOT_AVAILABLE}</div>);
    var permissions = this.getVal(this, 'props.moduleData.myNmUserFunctionAccessBeans');
    if (_.isArray(permissions)) {
      permissionData = this.showRolePermissions(permissions, rolePermissionData);
    }
    return permissionData;
  },

  render() {
    let role = this.props.role;
    return (
      <div className="customerWrapper clearfix">
        <div className="contactTypeGrid roleTypeInfo pull-left">
          <div className="settingHead">{role.roleName || CONSTANTS.NOT_AVAILABLE}</div>
        </div>
        <div className="clearfix">
          {this.renderRolePermissions(role.roleRelatedFunctions)}
        </div>
      </div>
    );
  }
});

export default RoleTypeItem;
