import InternalUserWrapper from './components/InternalUserWrapper';
import InternalSplashScreen from './components/InternalSplashScreen';
import Authorization from '../../services/authorization';
import { Route, IndexRoute } from 'react-router';

const InternalSplashScreenRoutes = (
    <Route name='InternalUserWrapper' path='/splash-screen-internal' component={InternalUserWrapper} onEnter={Authorization.redirectIfLoggedOut}>
        <IndexRoute component={InternalSplashScreen} />
    </Route>
);

export default InternalSplashScreenRoutes;
