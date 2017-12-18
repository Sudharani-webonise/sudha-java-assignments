import SplashScreenWrapper from './components/SplashScreenWrapper';
import SplashScreen from './components/SplashScreen';
import Authorization from '../../services/authorization';
import { Route, IndexRoute } from 'react-router';

const SplashScreenRoutes = (
  <Route name='SplashScreenWrapper' path='/splash-screen' component={SplashScreenWrapper} onEnter={Authorization.redirectIfLoggedOut}>
    <IndexRoute component={SplashScreen} />
  </Route>
);

export default SplashScreenRoutes;