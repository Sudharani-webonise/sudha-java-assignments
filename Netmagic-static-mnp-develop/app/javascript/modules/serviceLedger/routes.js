import ServiceLedgerWrapper from './components/ServiceLedgerWrapper';
import ServiceLedger from './components/ServiceLedger';
import { Route, IndexRoute } from 'react-router';
import Authorization from '../../services/authorization';

const ServiceLedgerRoutes = (
  <Route name='ServiceLedgerWrapper' path='/service-ledger' component={ServiceLedgerWrapper} onEnter={Authorization.redirectIfLoggedOut}>
    <IndexRoute component={ServiceLedger} />
  </Route>
);

export default ServiceLedgerRoutes;
