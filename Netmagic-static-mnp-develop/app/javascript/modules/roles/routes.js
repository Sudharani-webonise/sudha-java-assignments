import RoleWrapper from './components/RoleWrapper';
import Roles from './components/Roles';
import AddRole from './components/AddRole';
import { Route, IndexRoute } from 'react-router';
import Authorization from '../../services/authorization';

const RoleRoutes = (
    <Route name='RoleWrapper' path='/roles' component={RoleWrapper} onEnter={Authorization.redirectIfLoggedOut}>
        <IndexRoute component={Roles} />
        <Route name='addRole' path='add-role' component={AddRole} />
    </Route>
);

export default RoleRoutes;
