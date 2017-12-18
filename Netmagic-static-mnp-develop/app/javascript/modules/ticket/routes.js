
import TicketListing from './components/TicketListing';
import TicketDetails from './components/TicketDetails';
import TicketWrapper from './components/TicketWrapper';
import RaiseTicket from './components/RaiseTicket';
import User from '../../services/user';
import Authorization from '../../services/authorization';
import CONSTANTS from '../../constants/app-constant';
import { useRouterHistory, IndexRoute, IndexRedirect, Router, Route, DefaultRoute } from 'react-router';

const TicketRoutes = (
  <Route name='ticketListing' path='/tickets' component={TicketWrapper} onEnter={Authorization.redirectIfLoggedOut}>
    <IndexRoute component={TicketListing} />
    <Route name='ticketDetails' path='details' component={TicketDetails} />
    <Route name='raiseTicket' path='raise-ticket' component={RaiseTicket} />
  </Route>
);

export default TicketRoutes;
