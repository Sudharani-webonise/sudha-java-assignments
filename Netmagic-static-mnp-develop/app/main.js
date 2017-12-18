require('./theme/default/stylesheets/all.sass');
require('./javascript/frontend/custom.js');
require('react-select/dist/react-select.css');
import ReactDOM from 'react-dom';
import { useRouterHistory, Router, Route } from 'react-router';
import { createHashHistory } from 'history';
import Header from './javascript/modules/commons/Header';
import Alert from './javascript/modules/commons/Alert';
import Footer from './javascript/modules/commons/Footer';
import LoginRoutes from './javascript/modules/login/routes';
import DashboardRoutes from './javascript/modules/dashboard/routes';
import TicketRoutes from './javascript/modules/ticket/routes';
import ServiceLedgerRoutes from './javascript/modules/serviceLedger/routes';
import OutstandingRoutes from './javascript/modules/outstanding/routes';
import ContactsRoutes from './javascript/modules/peopleManagement/routes';
import RoleRoutes from './javascript/modules/roles/routes';
import SplashScreenRoutes from './javascript/modules/splashScreen/routes';
import AssetsRoutes from './javascript/modules/assets/routes';
import ErrorRoutes from './javascript/modules/unauthorized/routes';
import PageNotFound from './javascript/modules/unauthorized/components/PageNotFound';
import ShoppingRoutes from './javascript/modules/shoppingCart/routes';
import NewShoppingRoutes from './javascript/modules/newShoppingCart/routes';
import SsoRoutes from './javascript/modules/sso/routes';
import InternalSplashScreenRoutes from './javascript/modules/internalSplashScreen/routes';

const history = useRouterHistory(createHashHistory)({ queryKey: false });

const ForgotPassword = React.createClass({
  render: function () {
    return (
      <h1>Forgot me</h1>
    )
  }
});

const Root = React.createClass({
  render() {
    return (
      <div id='mainContainer'>
        <Router onUpdate={() => window.scrollTo(0, 0)} history = {history}>
          {LoginRoutes}
          {DashboardRoutes}
          {SplashScreenRoutes}
          {TicketRoutes}
          {ServiceLedgerRoutes}
          {OutstandingRoutes}
          {ContactsRoutes}
          {RoleRoutes}
          {AssetsRoutes}
          {ErrorRoutes}
          {ShoppingRoutes}
          {SsoRoutes}
          {NewShoppingRoutes}
          {InternalSplashScreenRoutes}
          <Route path="*" component={PageNotFound}/>
        </Router>
      </div>
    );
  },
});

ReactDOM.render(<Root />, document.getElementById('app'));
