import ContactsWrapper from './components/ContactsWrapper';
import Contacts from './components/Contacts';
import ContactDetails from './components/ContactDetails';
import ContactEdit from './components/ContactEdit';
import AddContact from './components/AddContact';
import { Route, IndexRoute } from 'react-router';
import Authorization from '../../services/authorization';

const ContactsRoutes = (
    <Route name='ContactsWrapper' path='/contacts' component={ContactsWrapper} onEnter={Authorization.redirectIfLoggedOut}>
        <IndexRoute component={Contacts} />
        <Route name='contactDetails' path='details' component={ContactDetails} />
        <Route name='contactEdit' path='edit' component={ContactEdit} />
        <Route name='contactAdd' path='add' component={AddContact} />
    </Route>
);

export default ContactsRoutes;
