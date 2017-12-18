import NewSCWrapper from './components/NewSCWrapper';
import AddVM from './components/AddVM';
import AddLB from './components/AddLB';
import AddFirewall from './components/AddFirewall';
import FirewallSummary from './components/FirewallSummary';
import MyCart from './components/MyCart';
import AddInfra from './components/AddInfra';
import MakePayment from './components/MakePayment';
import AddBandwidth from './components/AddBandwidth';
import AddSimpliScale from './components/AddSimpliScale';
import AddSimpliScaleBasic from './components/AddSimpliScaleBasic';
import Authorization from '../../services/authorization';
import { Route, IndexRoute } from 'react-router';

const NewShoppingRoutes = (
  <Route name='NewSCWrapper' path='/new-shopping-cart' component={NewSCWrapper} >
    <IndexRoute component={AddVM} />
    <Route component={AddLB} name='AddLB' path='/new-load-balancer'/>
    <Route component={AddFirewall} name='AddFirewall' path='/add-firewall'/>
    <Route component={FirewallSummary} name='FirewallSummary' path='/firewall-summary'/>
    <Route component={MyCart} name='MyCart' path='/my-cart'/>
    <Route component={AddInfra} name='AddInfra' path='/add-infra'/>
    <Route component={AddBandwidth} name='AddBandwidth' path='/add-bandwidth'/>
    <Route component={AddSimpliScale} name='AddSimpliScale' path='/add-simpliscale'/>
    <Route component={AddSimpliScaleBasic} name='AddSimpliScale' path='/add-simpliscale-basic'/>
    <Route component={MakePayment} name='MakePayment' path='/make-payment'/>
  </Route>
);

export default NewShoppingRoutes;
