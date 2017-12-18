import OutstandingWrapper from './components/OutstandingWrapper';
import Outstanding from './components/Outstanding';
import MakePayment from './components/MakePayment';
import FinalPayment from './components/FinalPayment';
import PaymentSuccess from './components/PaymentSuccess';
import { Route, IndexRoute } from 'react-router';
import PaymentOnAccount from './components/PaymentOnAccount';
import PaymentOnAccountSuccess from './components/PaymentOnAccountSuccess';
import PaymentFailed from './components/PaymentFailed';
import Authorization from '../../services/authorization';

const OutstandingRoutes = (
  <Route name='OutstandingWrapper' path='/outstanding' component={OutstandingWrapper} onEnter={Authorization.redirectIfLoggedOut}>
    <IndexRoute component={Outstanding} />
    <Route name='makePayment' path='make-payment' component={MakePayment} />
    <Route name='finalPayment' path='final-payment' component={FinalPayment} />
    <Route name='paymentSuccess' path='payment-success' component={PaymentSuccess} />
    <Route name='paymentOnAccountSuccess' path='payment-on-account-success' component={PaymentOnAccountSuccess} />
    <Route name='paymentFailed' path='payment-failed' component={PaymentFailed} />
    <Route component={PaymentOnAccount} name='PaymentOnAccount' path='payment-on-account'/>
  </Route>
);

export default OutstandingRoutes;
