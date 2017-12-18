import { Link } from 'react-router';
import CONSTANTS from '../../../constants/app-constant';
import Utility from '../../../mixins/basicUtils';
import RoleTypeHeader from './RoleTypeHeader';
import RoleTypeItem from './RoleTypeItem';
import Session from '../../../mixins/sessionUtils';
import NewRolePermissionCheckBox from '../../commons/NewRolePermissionCheckBox';

var ModuleNotificationData = createReactClass({
    mixins: [Utility],

    componentDidMount() {
        if(!Session.get('defaultPermissions')) {
            var permissions = Session.get('permissions');
            _.each(permissions, (permission) => {
                permission.isSelected = false;
            });
            Session.set('defaultPermissions', permissions);
        }
    },

    modulePermissions(moduleData) {
        var selectedModule = _.findWhere(this.props.data, { roleModuleName: moduleData.moduleId });
        var contactTypesList = (<div>{CONSTANTS.NOT_AVAILABLE}</div>);
        if (this.props.data && _.isArray(this.props.data)) {
            contactTypesList = _.map(this.props.data, function (role, key) {
                return (<RoleTypeItem role={role} moduleData={moduleData} key={key} />);
            });
        }
        return contactTypesList;
    },

    updateAllPermissions(checked, data) {
        var permissions = Session.get('defaultPermissions');
        var module = _.where(permissions, { functionId: data.functionId });
        _.first(module).isSelected = checked;
        Session.set('defaultPermissions', permissions);
    },

    updatePermission(checked, data) {
        this.updateAllPermissions(checked, data);
        var functionIds = this.props.functionIds;
        var permissions = Session.get(this.props.moduleData.moduleName);
        var permission = _.where(permissions, { functionId: data.functionId });
        _.first(permission).isSelected = checked ? true: false;
        Session.set(this.props.moduleData.moduleName, permissions);
    },

    showRolePermissions (permissions, rolePermissions) {
        if(!Session.get(rolePermissions.moduleName)) {
            _.each(permissions, (permission) => {
                permission.isSelected = false;
            });
            Session.set(rolePermissions.moduleName, permissions);
        }
        var permissions = Session.get(rolePermissions.moduleName);
        return _.map(permissions, (permission, key) => {
            return (<NewRolePermissionCheckBox checked={permission.isSelected} editable={true} data={permission} allowed={true} key={key}
                updatePermission={this.updatePermission}/>);
            }
        );
    },

    renderRolePermissions (rolePermissionData) {
        var permissionData = (<div>{CONSTANTS.NOT_AVAILABLE}</div>);
        var permissions = this.getVal(rolePermissionData, 'myNmUserFunctionAccessBeans');
        if (_.isArray(permissions)) {
            permissionData = this.showRolePermissions(permissions, rolePermissionData);
        }
        return permissionData;
    },

    updateRoleName(event) {
        var roleName = this.refs.roleName.value;
        this.props.updateRoleName(roleName);
    },

    render() {
        let moduleData = this.props.moduleData;
        return (
            <div id={moduleData.moduleName} className="settingTabWrap activeContentTab clearfix">
                <RoleTypeHeader permissions={moduleData.myNmUserFunctionAccessBeans}/>
                <div className="roleTypes clearfix">
                    { this.props.addRole ?
                        (
                        <div className="customerWrapper clearfix">
                            <div className="contactTypeGrid roleTypeInfo pull-left">
                                <input type="text" className="form-control"
                                    ref="roleName" onChange={this.updateRoleName}
                                    placeholder="Add Role Type"/>
                            </div>
                            <div className="clearfix">
                                {this.renderRolePermissions(moduleData)}
                            </div>
                        </div>
                        ): null
                    }
                    { this.modulePermissions(moduleData) }
                </div>
            </div>
        )
    }
});

export default ModuleNotificationData;
