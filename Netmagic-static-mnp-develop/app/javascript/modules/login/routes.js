import LoginWrapper from './components/LoginWrapper';
import ForgotPassword from './components/ForgotPassword';
import SignUp from './components/SignUp';
import Otp from './components/Otp';
import Authorization from '../../services/authorization';
import Congrats from './components/Congrats';
import Login from './components/Login';
import UpdatePassword from './components/UpdatePassword';
import ResetPassword from './components/ResetPassword';
import RegeneratePassword from './components/RegeneratePassword';
import InternalUserLogin from './components/InternalUserLogin';
import { IndexRedirect, Router, Route } from 'react-router';

const LoginRoutes = (
    <Route name='login' path='/' component={LoginWrapper} onEnter={Authorization.redirectIfLoggedIn}>
        <IndexRedirect to="/MyNetmagic"/>
        <Route name='Login' path='/MyNetmagic' component={Login} />
        <Route name='forgotPassword' path='/forgot-password' component={ForgotPassword} />
        <Route name='signup' path='/sign-up' component={SignUp} />
        <Route name='otp' path='/otp' component={Otp} />
        <Route name='RegeneratePassword' path='/regenerate-password' component={RegeneratePassword} />
        <Route name='ResetPassword' path='/reset-password' component={ResetPassword} />
        <Route name='congrats' path='/congrats' component={Congrats} />
        <Route name='InternalUserLogin' path='/NMInternalUser' component={InternalUserLogin} />
    </Route>
);

export default LoginRoutes;