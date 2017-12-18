import UnauthorizedWrapper from './components/UnauthorizedWrapper';
import UnauthorizedPage from './components/UnauthorizedPage';
import { Route, IndexRoute } from 'react-router';

const ErrorRoutes = (
  <Route name='UnauthorizedWrapper' path='/error' component={UnauthorizedWrapper}>
    <Route name='unauthorized' path='403' component={UnauthorizedPage} />
  </Route>
);

export default ErrorRoutes;
