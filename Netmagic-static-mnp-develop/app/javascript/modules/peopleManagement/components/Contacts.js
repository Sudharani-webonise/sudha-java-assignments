import { Link } from 'react-router';
import CONSTANTS from '../../../constants/app-constant';
import Loader from 'react-loader';
import LoaderOptions from '../../../mixins/loader';
import ContactsListHeader from './ContactsListHeader';
import ContactItem from './ContactItem';
import Contact from '../../../services/contact';
import User from '../../../services/user';
import Utility from '../../../mixins/basicUtils';
import Session from '../../../mixins/sessionUtils';
import http from '../../../mixins/restutils';
import UI from '../../../mixins/ui';

var Contacts = createReactClass({
    mixins: [User, Utility],
    contextTypes: {
        router: React.PropTypes.object
    },

    getInitialState() {
        return {
            loaded: false,
            pageNumber: 1,
            contacts: [],
            activeValue: this.props.location.query,
            types: []
        }
    },

    componentWillMount() {
        this.redirectIfUnauthorized.call(this,
            CONSTANTS.PERMISSION_CODES.contacts.listing);
        Contact.getTypes()
            .then((result) => this.setState({ types: result && result.contactTypeBeans }))
            .catch((err) => {
                UI.notifyError(err && err.statusText)
            });
    },

    componentDidMount() {
        this.getContactList();
        Session.removeItems(['contact', 'contactId', 'notificationsModulesList', 'notificationConfig']);
    },

    getContacts(url, params) {
        this.setState({ loaded: false, contacts: [] });
        Contact.getContactData(url, params)
            .then((result) => {
                this.state.contacts = result && result.listOfMynmContactHeaderBeans;
                this.state.loaded = true;
                this.setState(this.state);
            })
            .catch((err) => {
                this.setState({ loaded: true });
                UI.notifyError(err)
            })
    },

    getContactList() {
        var contactType = this.getVal(this.props, 'location.query.typeName');
        let userObj = User.getCustomerParams();
        var project = Session.get('project');
        var url = http.buildRestUrl(CONSTANTS.API_URLS.contacts.list,
            { customerId: userObj.customerId});
        var params = {
            associateCustomerId: userObj.assoCustomerId,
            projectId: User.getProject('projectId'),
            contactType: contactType || CONSTANTS.CONTACT_TYPE
        };
        this.getContacts(url, params);
        Session.set('contactType', params.contactType);
    },

    getContactTypes() {
        return _.map(this.state.types, (item, key) => {
            return (<li className={(this.state.activeValue.typeName || CONSTANTS.CONTACT_TYPE) === item.typeName ? "activeTab" : ""}
                key={item.typeId} onClick={this.activeTab.bind(null, item) }><span>{item.typeName}</span></li>)
        });
    },

    activeTab(contactType) {
        let userObj = User.getCustomerParams();
        var url = http.buildRestUrl(CONSTANTS.API_URLS.contacts.list,
            { customerId: userObj.customerId});
        var params = {
            associateCustomerId: userObj.assoCustomerId,
            contactType: contactType && contactType.typeName || CONSTANTS.CONTACT_TYPE
        };
        this.getContacts(url, params);
        this.setState({ activeValue: contactType || this.state.activeValue });
        Session.set('contactType', params.contactType);
    },

    redirectToContact(contactId) {
        Session.set('contactId', contactId);
        this.context.router.push('/contacts/details');
    },

    showContactsList(contacts) {
        var contactsList = (<li className="rowCelltabelDataCell">
            {CONSTANTS.UI_MESSAGES.dataNotFound}
            </li>);
        if(_.isArray(contacts) && contacts.length) {
            contactsList = _.map(contacts, (data, i) => {
                return (<ContactItem data={data} key={i}
                    redirectToContact={this.redirectToContact}
                    />)
            })
        }
        return contactsList;
    },

    render() {
        let contactList = Utility.getVal(this, 'state.contacts');
        return (
            <section className="ticketListSection outStandingWrap peopleWrap">
                <div className="blackOverlay"></div>
                <section className="midHeader">
                    <div className="container">
                        <div className="row">
                            <div className="col-xs-9">
                                <ol className="breadcrumb breadCrumbCustom clearfix">
                                    <li><Link to={User.getSplashScreenURL()}>Back to Home</Link></li>
                                    <li>
                                        <Link to="dashboard" className="blueLink">Back to Dashboard</Link>
                                    </li>
                                    <li className="active">Contact list</li>
                                </ol>
                                <h1 className="mainTitle">People Management</h1>
                            </div>
                            <div className="col-xs-3">
                            { this.canAccess(CONSTANTS.PERMISSION_CODES.contacts.add) ?
                                <Link to={'contacts/add'}  className="darkPinkBtn addNewBtn pull-right">
                                    Add New Contact
                                </Link> : null
                            }
                            </div>
                        </div>
                    </div>
                </section>
                <div className="container contactListContainer">
                    <div className="contactListWrap">
                        <div className="assetsHeader clearfix">
                            <ul className="assetsMenu pull-left clearfix">
                                {this.getContactTypes() }
                            </ul>
                        </div>
                        <div className="assetsContent">
                            <div className="virtualServerHeader clearfix">
                                <ContactsListHeader />
                            </div>
                            <article className="paddingZero listStyle">
                                <ul className="tickeListTable peopleListTable">
                                    <div className="contactListItems contactListData">
                                        <Loader loaded={this.state.loaded} top="50%" left="50%" options={LoaderOptions}>
                                            { this.showContactsList(contactList) }
                                        </Loader>
                                    </div>
                                </ul>
                            </article>
                        </div>
                    </div>
                </div>

            </section>
        )
    }
});

export default Contacts;
