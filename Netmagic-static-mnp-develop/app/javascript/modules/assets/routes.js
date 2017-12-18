import AssetsWrapper from './components/AssetsWrapper';
import AssetsListing from './components/AssetsListing';
import { Route, IndexRoute } from 'react-router';
import Authorization from '../../services/authorization';

const AssetsRoutes = (
  <Route name='AssetsWrapper' path='/assets' component={AssetsWrapper} onEnter={Authorization.redirectIfLoggedOut}>
    <IndexRoute component={AssetsListing} />
  </Route>
);

export default AssetsRoutes;
