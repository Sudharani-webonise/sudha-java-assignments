import DashboardWrapper from './components/DashboardWrapper';
import Dashboard from './components/Dashboard';
import Authorization from '../../services/authorization';
import UnauthorizedPage from './components/UnauthorizedPage';
import { Route, IndexRoute } from 'react-router';

const DashboardRoutes = (
  <Route name='DashboardWrapper' path='/dashboard' component={DashboardWrapper} onEnter={Authorization.redirectIfLoggedOut}>
    <IndexRoute component={Dashboard} />
    <Route name='unauthorized' path='error/403' component={UnauthorizedPage} />
  </Route>
);

export default DashboardRoutes;
