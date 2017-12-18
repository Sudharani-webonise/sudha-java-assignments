import { Link } from 'react-router';
import ModuleNotificationData from './ModuleNotificationData';
import PermissionHeader from './PermissionHeader';
import CONSTANTS from '../../../constants/app-constant';
import Session from '../../../mixins/sessionUtils';
import Utility from '../../../mixins/basicUtils';
import RolesCheckbox from '../../commons/RolesCheckbox';
import Checkbox from '../../commons/Checkbox';
import UI from '../../../mixins/ui';
import User from '../../../services/user';

var _getRolePermissions = function () {
    var updated = Session.get('updatedRolePermissions');
    return _.map(updated, (item) => {
        return { customerId: item.associteCustomerID,
            permissions: item.functionIds,
            projectId: item.projectId || ''
        }
    });
};

var RolePermissions = createReactClass({
    getInitialState() {
        return {
            activeModule: _.first(Session.get('modules')),
            rolePermissions: _getRolePermissions()
        };
    },

    componentDidMount() {
        Session.set('customerModuleData', this.props.selectedCustomers || Session.get('customerModuleData'));
    },

    notifyForCheckBox(event) {
        UI.notifyError(CONSTANTS.UI_MESSAGES.selectRole);
        var target = event.currentTarget;
        target.checked = !target.checked;
    },

    renderSSOHtml(permissionCode, tickCheckbox, customer) {
        var {editable, editContact} = this.props;
        return (editable && tickCheckbox && permissionCode && editContact) ?
            <Link to={{ pathname: '/sso-page', query: { moduleName: 'USR', aci: customer.associteCustomerID, p: customer.projectId, moduleCategory: permissionCode.value } }} className="newModules blackText">{permissionCode.label}
            </Link>
            : (null)
    },

    showCheckboxes(userPermissions,customer) {
        var permissions = this.props.activeModule && this.props.activeModule.myNmUserFunctionAccessBeans;
        userPermissions = _.flatten(userPermissions);
        return _.map(permissions, (permission, key) => {
            var tickCheckbox = _.contains(userPermissions, permission.functionId);
            var permissionCode = _.findWhere(CONSTANTS.SSO_PERMISSION_CODES, { code: permission.functionCode })
            return (<div key={key}>
                <div className="severityGrid pull-left">
                    <RolesCheckbox notifyForCheckBox={this.notifyForCheckBox} checked={tickCheckbox} updateAction={this.props.updateNotification} />
                </div>
                {
                    this.renderSSOHtml(permissionCode, tickCheckbox, customer)
                }
            </div>
            )
        })
    },

    setNewCustomer(role, customer) {
        return {
            roleName: role.roleName || '',
            roleId: role.roleId || '',
            functionIds: role.roleRelatedFunctions,
            associteCustomerID: customer.associteCustomerID || customer.cvCrmId || '',
            associteCustomerName: customer.associteCustomerName || customer.cvName || '',
            projectId: customer.projectId || '',
            projectName: customer.projectName || '',
            isActive: CONSTANTS.YES
        };
    },

    setRolePermissions(role, associateCustomerId, projectId) {
        if (role && _.isArray(role.roleRelatedFunctions)) {
            var permissions = _.reject(this.state.rolePermissions,
                (item) => item.customerId == associateCustomerId)
                .concat([{ permissions: role.roleRelatedFunctions, customerId: associateCustomerId, projectId: projectId }]);
            this.setState({ rolePermissions: permissions });
            var updatedPermissions = Session.get('updatedRolePermissions') || [];
            var contact = Session.get('contact');
            var existingCustomers = Session.get('existingCustomers') || [];
            var customer = _.findWhere(updatedPermissions || contact.mynmRoleFunctionsBeanList, { associteCustomerID: associateCustomerId, projectId: projectId, isActive: CONSTANTS.NO }) ||
                _.findWhere(existingCustomers, { associteCustomerID: associateCustomerId, projectId: projectId, isActive: CONSTANTS.NO }) ||
                _.findWhere(updatedPermissions || contact.mynmRoleFunctionsBeanList, { associteCustomerID: associateCustomerId, projectId: projectId, isActive: CONSTANTS.NO });
            var newCustomer = {};
            if (customer) {
                var existingNewCustomer = _.findWhere(updatedPermissions,
                    {
                        associteCustomerID: associateCustomerId,
                        projectId: projectId,
                        isActive: CONSTANTS.YES
                    }
                );
                if(existingNewCustomer) {
                    newCustomer = this.setNewCustomer(role, customer);
                }
                updatedPermissions = _.chain(updatedPermissions)
                    .reject(existingNewCustomer)
                    .push(newCustomer)
                    .value();
                updatedPermissions = _.chain(updatedPermissions)
                    .reject(customer)
                    .value();
            } else {
                if(!_.findWhere(updatedPermissions, { associteCustomerID: associateCustomerId, projectId: projectId, isActive: CONSTANTS.YES, roleId: role.roleId })) {
                    customer = _.findWhere(updatedPermissions, { associteCustomerID: associateCustomerId, projectId: projectId, isActive: CONSTANTS.YES }) ||
                        _.findWhere(contact.mynmRoleFunctionsBeanList, { associteCustomerID: associateCustomerId, projectId: projectId, isActive: CONSTANTS.YES }) ||
                        _.findWhere(updatedPermissions, { associteCustomerID: associateCustomerId, projectId: projectId }) ||
                        _.findWhere(updatedPermissions, { cvCrmId: associateCustomerId }) ||
                        _.findWhere(this.props.selectedCustomers,
                            { cvCrmId: associateCustomerId, projectId: projectId });
                    if(customer) {
                        customer.isActive = CONSTANTS.NO;
                        newCustomer = this.setNewCustomer(role, customer);
                    } else {
                        newCustomer = {
                            roleName: role.roleName || '',
                            roleId: role.roleId || '',
                            functionIds: role.roleRelatedFunctions,
                            associteCustomerID: associateCustomerId,
                            associteCustomerName: customer && customer.cvName || User.getMainCustomer('customerName') ||'',
                            projectId: customer && customer.projectId || User.getProject('projectId') || '',
                            isActive: CONSTANTS.YES,
                            projectName: customer && customer.projectName || User.getProject('projectName') || ''
                                }
                    }
                    updatedPermissions = updatedPermissions.concat(newCustomer);
                    if(customer && customer.roleId) {
                        Session.set('existingCustomers', existingCustomers.concat([customer]));
                    }
                    if(customer) {
                        updatedPermissions = _.chain(updatedPermissions)
                        .reject(customer)
                        .value();
                    }
                }
            }
            Session.set('updatedRolePermissions', updatedPermissions);
        }
    },

    setCustomerRolePermissions(role, associateCustomerId, projectId) {
        var existingPermission;
        if (role && _.isArray(role.roleRelatedFunctions)) {
            if (associateCustomerId && projectId) {
                existingPermission = _.findWhere(this.state.rolePermissions, { customerId: associateCustomerId, projectId: projectId });
                if (!existingPermission) {
                    this.state.rolePermissions.push({ permissions: role.roleRelatedFunctions, customerId: associateCustomerId, projectId: projectId })
                } else {
                    this.checkForExistingCustomer(existingPermission, role, associateCustomerId, projectId);
                }
                this.setState({ rolePermissions: this.state.rolePermissions });
                var updatedPermissions = Session.get('updatedRolePermissions') || [];
                var targetCustomer = _.findWhere(this.props.selectedCustomers,
                    { cvCrmId: associateCustomerId, projectId: projectId });
                var customer = {
                    roleName: role.roleName || '',
                    roleId: role.roleId || '',
                    functionIds: role.roleRelatedFunctions,
                    associteCustomerID: associateCustomerId,
                    associteCustomerName: targetCustomer && targetCustomer.cvName || '',
                    projectId: targetCustomer && targetCustomer.projectId || '',
                    isActive: CONSTANTS.YES,
                    projectName: targetCustomer && targetCustomer.projectName || ''
                };
                if (!_.findWhere(updatedPermissions,
                    { associteCustomerID: associateCustomerId, projectId: projectId })) {
                    updatedPermissions.push(customer);
                } else {
                    updatedPermissions = _.chain(updatedPermissions)
                        .reject((customer) => customer.projectId === projectId)
                        .push(customer)
                        .value();
                }
                Session.set('updatedRolePermissions', updatedPermissions);
            } else {
                existingPermission = _.findWhere(this.state.rolePermissions, { customerId: associateCustomerId, projectId: projectId });
                if (!existingPermission) {
                    this.state.rolePermissions.push({
                        permissions: role.roleRelatedFunctions,
                        customerId: associateCustomerId,
                        projectId: projectId,
                    })
                } else {
                    this.checkForExistingCustomer(existingPermission, role, associateCustomerId, projectId);
                }
                this.setState({ rolePermissions: this.state.rolePermissions });
                var updatedPermissions = Session.get('updatedRolePermissions') || [];
                var targetCustomer = _.findWhere(this.props.selectedCustomers,
                    { cvCrmId: associateCustomerId, projectId: projectId });
                var customer_new = {
                    roleName: role.roleName || '',
                    roleId: role.roleId || '',
                    functionIds: role.roleRelatedFunctions,
                    associteCustomerID: associateCustomerId,
                    associteCustomerName: targetCustomer && targetCustomer.cvName || '',
                    projectId: targetCustomer && targetCustomer.projectId || '',
                    isActive: CONSTANTS.YES,
                    projectName: targetCustomer && targetCustomer.projectName || ''
                };
                if (!_.findWhere(updatedPermissions, { associteCustomerID: associateCustomerId, projectId : customer_new.projectId  })) {
                    customer_new.projectId = (this.props.projects === 'NA' ? '' : this.props.projects || targetCustomer && targetCustomer.projectId) || ''
                    updatedPermissions.push(customer_new);
                }
                else{
                    var permission = _.findWhere(updatedPermissions, { associteCustomerID: associateCustomerId, projectId : customer_new.projectId  });
                    updatedPermissions = _.chain(updatedPermissions)
                        .reject(permission)
                        .push(customer_new)
                        .value();
                }
                Session.set('updatedRolePermissions', updatedPermissions);
            }
        }
    },

    checkForExistingCustomer(existingPermission, role, associateCustomerId, projectId) {
        if(existingPermission) {
            this.state.rolePermissions = _.reject(this.state.rolePermissions, {
                customerId: associateCustomerId,
                projectId: projectId
            });
            this.state.rolePermissions.push({ permissions: role.roleRelatedFunctions, customerId: associateCustomerId, projectId: projectId });
        }
    },

    showRoles(associateCustomerId, projectId) {
        var roles = this.props.roleDetails && this.props.roleDetails.rolePermissionBean;
        var methodToCall = this.props.selectedCustomers ? 'setCustomerRolePermissions' : 'setRolePermissions';
        return _.map(roles, (role, key) => {
            return (<li key={key} data-id={role.roleId}
                onClick={this[methodToCall].bind(null, role, associateCustomerId, projectId) }>{role.roleName}</li>)
        });
    },

    getSelectedRole(id, projectId) {
        var updatedPermissions = Session.get('updatedRolePermissions') || [];
        var roleData = _.findWhere(updatedPermissions, { associteCustomerID: id, projectId: projectId, isActive: CONSTANTS.YES });
        return roleData && roleData.roleName || CONSTANTS.NOT_AVAILABLE;
    },

    showPermissionMatrix() {
        const NA = CONSTANTS.NOT_AVAILABLE;
        let userObj = User.getCustomerParams();
        var selectedCustomers = this.props.selectedCustomers;
        var customerModuleData = selectedCustomers ||
            this.props.data;
        selectedCustomers = selectedCustomers || [];
        var contact = Session.get('contact');
        Session.set('customerModuleData', customerModuleData);
        var rolePermissions = this.state.rolePermissions;
        if (selectedCustomers && !_.findWhere(customerModuleData, { cvCrmId: userObj.customerId })) {
            if(!_.findWhere(customerModuleData, { associteCustomerID: userObj.customerId })) {
                customerModuleData.push({
                    cvCrmId: userObj.customerId,
                    cvName: userObj.customerName,
                    projectId: User.getProject('projectId') || '',
                });
            }
        }
        Session.set('contact', contact);
        return _.map(customerModuleData, (customer, key) => {
            var customerId = customer.associteCustomerID || customer.cvCrmId;
            var projectId = (customer.projectId === "NA" ? "" : customer.projectId) || "";
            var customPermission = _.findWhere(rolePermissions,
                { customerId: customerId, projectId: projectId }) || customer.functionIds;
            var projectName = customer.projectName;
            var updatedCustomer = _.findWhere(Session.get('updatedRolePermissions'), {
                associteCustomerID: customer.associteCustomerID,
                projectId: customer.projectId
            });
            if(updatedCustomer) {
                customerId = updatedCustomer.associteCustomerID;
                projectId = (updatedCustomer.projectId === "NA" ? "" : updatedCustomer.projectId) || "";
                customPermission = _.findWhere(rolePermissions,
                { customerId: customerId, projectId: projectId }) || updatedCustomer.functionIds;
                projectName = updatedCustomer.projectName;
                customer.roleName = updatedCustomer.roleName;
            }
            return (<div className="customerWrapper clearfix" key={key}>
                <div className="contactTypeGrid pull-left">
                    <div className="settingHead">{customer.associteCustomerName || customer.cvName }</div>
                    <div className="">{ projectName } </div>
                    {this.props.editable ?
                        (<div className="customSelectGroup roleTypeDropDown pull-left" data-id="contactCategory">
                            <label for="countryCode">Select Role</label>
                            <ul className="customSelectList">
                                <li></li>
                                {this.showRoles(customerId, projectId) }
                            </ul>
                            <p className="selectContent">{customer.roleName || this.getSelectedRole(customerId, projectId) }</p>
                        </div>) :
                        (<div className="settingTitle">{customer.roleName || NA}</div>)
                    }

                </div>
                <div className="clearfix">
                    {this.showCheckboxes(customPermission && customPermission.permissions || [customer.functionIds],customer) }
                </div>
            </div>)
        })
    },

    render() {
        var {activeModule, selectedCustomers} = this.props;
        return (
            <div className="notificationWrap">
                <div className="settingTabWrap activeContentTab clearfix">
                    <PermissionHeader data = {activeModule.myNmUserFunctionAccessBeans} />
                    <div className="roleTypes clearfix">
                        {this.showPermissionMatrix() }
                    </div>
                </div>
            </div>
        )
    }
});

export default RolePermissions;
