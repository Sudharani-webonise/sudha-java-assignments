import SsoScreenWrapper from './components/SsoScreenWrapper';
import SsoScreen from './components/SsoScreen';
import SsoCmpScreen from './components/SsoCmpScreen';
import SsoClassicMnp from './components/SsoClassicMnp';
import Authorization from '../../services/authorization';
import { Route, IndexRoute } from 'react-router';

const SsoRoutes = (
  <Route name='SsoScreenWrapper' path='/sso-page' component={SsoScreenWrapper}
    onEnter={Authorization.redirectIfLoggedOut}>
    <IndexRoute component={SsoScreen} />
    <Route name='SsoCmp' path='/sso-cmp' component={SsoCmpScreen} />
    <Route name='SsoMnp' path='/sso-mnp' component={SsoClassicMnp} />
  </Route>
);

export default SsoRoutes;
