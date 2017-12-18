import UI from '../../../mixins/ui';
import Session from '../../../mixins/sessionUtils';
import Role from '../../../services/role';
import CONSTANTS from '../../../constants/app-constant';
import RolePermissionDetails from './RolePermissionDetails';
import {Link} from 'react-router';
import PostRequestLoader from '../../commons/Loader';
import User from '../../../services/user';

var AddRole = createReactClass({
    mixins: [UI, Session, Role],

    contextTypes: {
        router: React.PropTypes.object
    },

    getInitialState() {
        return {
            rolesModuleBeans: [],
            addRole: true,
            functionIds: [],
            roleName: '',
            showLoader: false
        };
    },

    componentDidMount() {
        var customer = this.get('mainCustomer');
        this.fetchRoleDetails(customer && customer.customerId)
            .then((result) => this.setState(result))
            .catch((error) => UI.notifyError(error));
    },

    componentWillUnmount() {
        Session.removeItems(['Ticket', 'Contacts',
          'Outstanding', 'Assets', 'Services', 'Cloud', 'NMON', 'Utilization',
          'defaultPermissions']);
    },

    updateRoleName(roleName) {
      this.setState({ roleName: roleName && roleName.trim() });
    },

    createRole() {
      var user = Session.get('user');
      let userObj = User.getCustomerParams();
      var validateError = this.getValidationErrorMessage();
      if(validateError) {
        UI.notifyError(validateError);
        return;
      }
      var params = {
        roleName: this.state.roleName,
        customerId: Number(userObj.customerId),
        creatorId: Number(user && user.loginUserId),
        functionIds: this.getFunctionIds() || []
      };
      this.setState({ showLoader: true });
      Role.addRole(params)
        .then((result) => {
            if(result.errCode === CONSTANTS.STATUS_CODES.duplicateRole) {
                UI.notifyError(result.errMsg);
            }
            this.setState({ showLoader: false });
        })
        .catch((error) => {
          if(error === "OK") {
            UI.notifySuccess( CONSTANTS.UI_MESSAGES.role.success);
            this.context.router.push('/roles');
          } else {
            UI.notifyError(error.errMsg);
          }
          this.setState({ showLoader: false });
        })
    },

    getValidationErrorMessage() {
        const message = CONSTANTS.UI_MESSAGES;
        var roleName = !this.state.roleName ? message.role.roleType : "";
        var functionIds = this.getFunctionIds();
        var mandatoryCheck = _.isArray(functionIds) && functionIds.length ?
            '' : message.moduleCheck;
        return roleName || mandatoryCheck;
    },

    getFunctionIds() {
        return _.chain(Session.get('defaultPermissions'))
          .where({ isSelected: true})
          .pluck('functionId')
          .value();
    },

    render() {
        var modulePermissions = CONSTANTS.MODULE_PERMISSIONS;
        return (
            <section className="ticketListSection">
                <section className="breadCrumbSection">
                    <div className="container">
                        <div className="row">
                            <div className="col-xs-9 col-sm-9 col-md-9">
                                <ol className="breadcrumb breadCrumbCustom clearfix">
                                    <li><Link to={User.getSplashScreenURL()}>Back to Home</Link></li>
                                    <li><Link to="dashboard">Back to Dashboard</Link></li>
                                    <li><Link to="roles">Roles Management</Link></li>
                                    <li className="ticketListBread active">Add Role</li>
                                </ol>
                                <h2 className="pageHeading">Roles Management</h2>
                            </div>
                            <div className="col-xs-3 col-sm-3 col-md-3"><div>
                              <Link className="cancel darkPinkBtn pull-right" to="/roles">Cancel</Link>
                              <a className="save darkPinkBtn pull-right" onClick={this.createRole}>Save </a>
                            </div>
                            </div>
                        </div>
                    </div>
                </section>
                <PostRequestLoader show={this.state.showLoader} />
                <article className="container paddingTop125 listStyle">
                    <RolePermissionDetails addRole={this.state.addRole} data={this.state.rolePermissionBean}
                        functionIds={this.state.functionIds}
                      updateRoleName={this.updateRoleName}/>
                </article>
            </section>
        );
    },
});

export default AddRole;
