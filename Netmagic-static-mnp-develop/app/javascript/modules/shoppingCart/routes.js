import ShoppingCartWrapper from './components/ShoppingCartWrapper';
import ShoppingCart from './components/ShoppingCart';
import EmptyCart from './components/EmptyCart';
import PaymentDone from './components/PaymentDone';
import ProvisionSC from './components/ProvisionSC';
import AddContactSC from './components/AddContactSC';
import Payment from './components/Payment';
import NewShoppingCart from './components/NewShoppingCart';
import ProductList from './components/ProductList';
import SofDetails from './components/SofDetails';
import Authorization from '../../services/authorization';
import { Route, IndexRoute } from 'react-router';
import MakePayment from './components/MakePayment';
import PaymentSucessful from './components/PaymentSucessful';
import PaymentFailed from './components/PaymentFailed';
import PaymentProcess from './components/PaymentProcess';
import UpdateCloudItem from './components/UpdateCloudItem';

const ShoppingRoutes = (
  <Route name='ShoppingCartWrapper' path='/shopping-cart' component={ShoppingCartWrapper} onEnter={Authorization.redirectIfLoggedOut}>
    <IndexRoute component={NewShoppingCart} />
    <Route name='Payment' path='payment-successful' component={Payment} />
    <Route name='NewShoppingCart' path='/new-shopping' component={NewShoppingCart} />
    <Route name='ThankYouPayment' path='thank-you' component={PaymentDone} />
    <Route name='Provision' path='/provision' component={ProvisionSC} />
    <Route name='AddContactSC' path='/add-contact' component={AddContactSC} />
    <Route name='ProductList' path='/product-list' component={ProductList} />
    <Route name='SofDetails' path='/sof-details' component={SofDetails} />
    <Route name='PaymentProcess' path='/payment-process' component={PaymentProcess} />
	  <Route name='PaymentFailed' path='/payment-failed' component={PaymentFailed} />
	  <Route name='PaymentSucessful' path='/payment-success' component={PaymentSucessful} />
    <Route component={MakePayment} name='MakePayment' path='/make-payment'/>
    <Route component={UpdateCloudItem} name='UpdateCloudItem' path='/edit-cloud-infra'/>
  </Route>
);

export default ShoppingRoutes;
