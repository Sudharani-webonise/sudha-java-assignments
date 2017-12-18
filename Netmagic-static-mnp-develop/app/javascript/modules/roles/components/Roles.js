import UI from '../../../mixins/ui';
import Session from '../../../mixins/sessionUtils';
import Role from '../../../services/role';
import CONSTANTS from '../../../constants/app-constant';
import RolePermissionDetails from './RolePermissionDetails';
import {Link} from 'react-router';
import User from '../../../services/user';

var Roles = createReactClass({
    mixins: [UI, Session, Role],

    getInitialState() {
        return {
            rolesModuleBeans: []
        };
    },

    componentDidMount() {
        var userRequestParams = User.getCustomerParams();
        var params = {
            mainCustomerId: userRequestParams.customerId,
            associateCustomerId: userRequestParams.assoCustomerId || userRequestParams.customerId,
            projectId: userRequestParams.projectId
        };
        this.fetchRoleDetails(User.getMainCustomer('customerId') || User.getMainCustomer('cvCrmId'))
            .then((result) => this.setState(result))
            .catch((error) => UI.notifyError(error));
        User.getPermissions(params)
            .then((result) => {
                //do nothing
            })
            .catch((error) => this.notifyError(CONSTANTS.UI_MESSAGES.userPermissionError));
    },

    tabClassSet(index) {
        return this.classSet({
            'tabPeople': true,
            'activeTab': index === 0
        });
    },

    render() {
        var modulePermissions = CONSTANTS.MODULE_PERMISSIONS;
        var permissions = CONSTANTS.PERMISSION_CODES;
        return (
            <section className="ticketListSection">
                <section className="breadCrumbSection">
                    <div className="container">
                        <div className="row">
                            <div className="col-xs-9 col-sm-9 col-md-9">
                                <ol className="breadcrumb breadCrumbCustom clearfix">
                                    <li><Link to={User.getSplashScreenURL()}>Back to Home</Link></li>
                                    <li><Link to="dashboard">Back to Dashboard</Link></li>
                                    <li className="ticketListBread active">Roles Management</li>
                                </ol>
                                <h2 className="pageHeading">Roles Management</h2>
                            </div>
                            <div className="col-xs-3 col-sm-3 col-md-3">
                                <Link className="darkPinkBtn pull-right" to="/roles/add-role">Add Role Type</Link>
                            </div>
                        </div>
                    </div>
                </section>
                <article className="container paddingTop125 listStyle">
                    <RolePermissionDetails data={this.state.rolePermissionBean}
                        functionIds={this.state.functionIds}/>
                </article>
            </section>
        );
    },
});

export default Roles;
