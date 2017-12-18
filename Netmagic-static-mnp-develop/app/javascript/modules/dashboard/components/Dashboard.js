import CONSTANTS from '../../../constants/app-constant';
import TicketDataCount from '../../ticket/components/TicketDataCount';
import TicketServerInfo from '../../ticket/components/TicketServerInfo';
import TicketOpenStatus from '../../ticket/components/TicketOpenStatus';
import ServiceWidgetData from '../../serviceLedger/components/ServiceWidgetData';
import OutstandingWidget from '../../outstanding/components/OutstandingWidget';
import ContactsWidget from '../../peopleManagement/components/ContactsWidget';
import AssetsWidgetData from '../../assets/components/AssetsWidgetData';
import Loader from 'react-loader';
import LoaderOptions from '../../../mixins/loader';
import WidgetHead from '../../commons/WidgetHead';
import WidgetBody from '../../commons/WidgetBody';
import http from '../../../mixins/restutils';
import Session from '../../../mixins/sessionUtils';
import User from '../../../services/user';
import { Link } from 'react-router';
import Utility from '../../../mixins/basicUtils';
import DashboardService from '../../../services/dashboard';
import UI from '../../../mixins/ui';
import Invoice from '../../../services/outstanding';
import Permission from '../../../services/permission';

var TicketWidgetWrapper = createReactClass({
    render() {
        return User.canAccess(CONSTANTS.PERMISSION_CODES.tickets.widget) ?
            (<Loader loaded={this.props.loaded} top="0" left="0" options={LoaderOptions}>
                <TicketDataCount data={this.props.ticket}/>
                <TicketServerInfo data={this.props.ticket}/>
                <TicketOpenStatus data={this.props.ticket}/>
            </Loader>) :
            (<WidgetBody iconClass="DBicon-alert widgetInfoIcon" infoText="Unauthorized module"/>);
    }
});

var OutstandingWidgetWrapper = createReactClass({
    render() {
        return User.canAccess(CONSTANTS.PERMISSION_CODES.outstanding.widget) ?
            (<Loader loaded={this.props.loaded} top="0" left="0" options={LoaderOptions}>
                <OutstandingWidget data={this.props.outstanding}/>
            </Loader>) :
            (<WidgetBody iconClass="DBicon-outstanding widgetInfoIcon" infoText="Unauthorized module"/>);
    }
});

var AssetsWidgetWrapper = createReactClass({
    render() {
        return User.canAccess(CONSTANTS.PERMISSION_CODES.assets.widget) ?
            (<Loader loaded={this.props.loaded} top="0" left="0" options={LoaderOptions}>
                <AssetsWidgetData data={this.props.assets} />
            </Loader>) :
            (<WidgetBody iconClass="DBicon-assets widgetInfoIcon" infoText="Unauthorized module"/>);
    }
});

var ContactsWidgetWrapper = createReactClass({
    render() {
        return User.canAccess(CONSTANTS.PERMISSION_CODES.contacts.widget) ?
            (<Loader loaded={this.props.loaded} top="0" left="0" options={LoaderOptions}>
                <ContactsWidget data={this.props.contacts} />
            </Loader>) :
            (<WidgetBody iconClass="DBicon-contacts widgetInfoIcon" infoText="Unauthorized module"/>);
    }
});

var ServicesWidgetWrapper = createReactClass({
    render() {
        return User.canAccess(CONSTANTS.PERMISSION_CODES.services.widget) ?
            (<Loader loaded={this.props.loaded} top="0" left="0" options={LoaderOptions}>
                <ServiceWidgetData data={this.props.services} />
            </Loader>) :
            (<WidgetBody iconClass="DBicon-services widgetInfoIcon" infoText="Unauthorized module"/>);
    }
});

var Dashboard = createReactClass({
    mixins: [Utility, Session, http, User],
    getInitialState() {
        return {
            loaded: {
                ticket: false,
                service: false,
                outstanding: false,
                contacts: false,
                assets: false,
            },
            widget: {
                ticket: {},
                service: {},
                outstanding: {},
                contacts: {},
                assets: {},
            },
        };
    },

    componentWillMount() {
        var customerBean = Session.get('customerBeans')
        if (!(_.isArray(customerBean) && customerBean.length)) {
            Permission.fetchAll()
                .then((result) => {
                    var permissions = _.chain(result)
                        .pluck('myNmUserFunctionAccessBeans')
                        .flatten()
                        .compact()
                        .value();
                    this.set('permissions', permissions);
                    this.set('modulePermissions', result);
                })
                .catch((error) => UI.notifyError(CONSTANTS.UI_MESSAGES.userPermissionError));
        }
    },

    contextTypes: {
        router: React.PropTypes.object
    },

    componentDidMount() {
        this.getWidget('tickets', this.getTicketCount);
        this.getWidget('outstanding', this.getOutstandingData);
        this.getWidget('contacts', this.getContactsCount);
        this.getWidget('assets', this.getAssetsCount);
        this.getWidget('services', this.getSofCount);
        Session.set('splashScreen', true);
        if(_.isEmpty(Session.get('corporateData'))) {
            this.context.router.push('/dashboard');
        } else {
            this.context.router.push('/product-list');  
        }
    },

    getWidget(module, method) {
        let permissions = CONSTANTS.PERMISSION_CODES[module];
        if (permissions && this.canAccess(permissions.widget) && _.isFunction(method)) {
            method.call(this);
        }
    },

    getWidgetData(url, params, stateKey) {
        DashboardService.getWidgetModuleData(url, params)
            .then((result) => {
                this.state.widget[stateKey] = result;
                this.state.loaded[stateKey] = true;
                this.setState(this.state);
            })
            .catch((error) => {
                this.state.loaded[stateKey] = true;
                UI.notifyError(error && error.responseText);
            });
    },

    getRequestParams(requestParams) {
        return {
            customerId: requestParams.customerId,
            associateCustomerId: requestParams.assoCustomerId,
            projectId: requestParams.projectId
        }
    },

    getTicketCount() {
        var params = this.getRequestParams(User.getCustomerParams());
        this.getWidgetData(CONSTANTS.API_URLS.ticketWidget, params, 'ticket');
    },

    getSofCount() {
        var requestParams = User.getCustomerParams();
        this.getWidgetData(CONSTANTS.API_URLS.serviceWidget, {
            customerId: requestParams.customerId,
            associateCustomerId: requestParams.assoCustomerId,
            projectId: requestParams.sugarProjectId
        }, 'service');
    },

    getOutstandingData() {
        var state = this.state;
        Invoice.getData(CONSTANTS.API_URLS.outstanding.widget)
            .then((result) => {
                state.widget.outstanding = result;
                state.loaded.outstanding = true;
                this.setState(state);
            })
            .catch((error) => {
                state.loaded.outstanding = true;
                this.setState(state);
                UI.notifyError(error.responseText);
            });
    },

    getContactsCount() {
        var requestParams = User.getCustomerParams();
        var params = {
            customerId: requestParams.customerId,
            associateCustomerId: requestParams.assoCustomerId,
            projectName: requestParams.projectName
        };
        this.getWidgetData(CONSTANTS.API_URLS.contactsWidget, params, 'contacts');
    },

    getAssetsCount() {
        var params = _.omit(this.getRequestParams(User.getCustomerParams()), 'projectId');
        this.getWidgetData(CONSTANTS.API_URLS.assetsWidget, params, 'assets');
    },

    showSSOModules(permissions) {
        return _.map(CONSTANTS.SSO_MODULE, (data, key) => {
            return (
                this.canAccess(permissions[data.label].view) ?
                    <li key = {key}>
                        <Link
                            to={`/sso-page?moduleName=${data.value}`} className="newModules">{data.label}
                        </Link>
                    </li> :
                    (null)

            )
        });
    },

    render() {
        let permissions = CONSTANTS.PERMISSION_CODES;
        return (
            <div>
                <section className="breadCrumbSection dashboardBreadCrumb">
                    <div className="container">
                        <div className="row">
                            <div className="col-xs-10 col-sm-10 col-md-10">
                                <ol className="breadcrumb breadCrumbCustom clearfix">
                                    <li><Link to={User.getSplashScreenURL()}>Back to Home</Link></li>
                                    <li className="ticketListBread active">Dashboard</li>
                                </ol>
                            </div>
                            <div className="col-xs-2 col-sm-2 col-md-2">
                            </div>
                        </div>
                    </div>
                </section>
                <div className="container dashBoardContainer">
                    <ul className="widgetContainer">
                        <li className="widgetCommonProp linkPresent">
                            <div className="widgetHead clearfix">
                                <h3 className="pull-left">tickets</h3>
                                {this.canAccess(permissions.tickets.raise) ?
                                    (<Link to="tickets/raise-ticket" title="tickets" className="reOpenBtn pull-right darkPinkBtn rippleBtn">Create Ticket</Link>)
                                    : (null)
                                }
                            </div>
                            {this.canAccess(permissions.tickets.listing) ?
                                (<div>
                                    <span>
                                        <TicketWidgetWrapper loaded = {this.state.loaded.ticket} ticket= {this.state.widget.ticket} />
                                    </span>
                                    <Link to="/tickets" title="view all" className="viewAll">View All</Link>
                                </div>) :
                                (<TicketWidgetWrapper loaded = {this.state.loaded.ticket} ticket= {this.state.widget.ticket} />)
                            }
                        </li>
                        <li className="widgetCommonProp linkPresent outStandWidget">
                            <div className="widgetHead clearfix">
                                <h3 className="pull-left">total outstanding</h3>
                            </div>
                            {this.canAccess(permissions.outstanding.listing) ?
                                (<div>
                                    <Link to="/outstanding" activeClassName="">
                                        <OutstandingWidgetWrapper loaded={this.state.loaded.outstanding} outstanding={this.state.widget.outstanding} />
                                    </Link>
                                    <Link to="/outstanding" title="view all" className="viewAll">View All</Link>
                                </div>) :
                                (<OutstandingWidgetWrapper loaded={this.state.loaded.outstanding} outstanding={this.state.widget.outstanding} />)
                            }
                        </li>
                        <li className="widgetCommonProp serviceWidget lightBlueBorder">
                            <div className="widgetHead clearfix">
                                <h3 className="pull-left">assets</h3>
                            </div>
                            {this.canAccess(permissions.assets.listing) ?
                                (<div>
                                    <AssetsWidgetWrapper loaded={this.state.loaded.assets} assets={this.state.widget.assets} />
                                    <Link to="/assets" title="view all" className="viewAll">View All</Link>
                                </div>) :
                                (<AssetsWidgetWrapper loaded={this.state.loaded.assets} assets={this.state.widget.assets} />)
                            }
                        </li>
                        <li className="widgetCommonProp serviceWidget lightPinkBorder">
                            <div className="widgetHead clearfix">
                                <h3 className="pull-left">services</h3>
                                <Link to="tickets/raise-ticket" title="tickets" className="reOpenBtn pull-right darkPinkBtn rippleBtn hide">Cheange of Services</Link>
                            </div>
                            {this.canAccess(permissions.services.listing) ?
                                (<div>
                                    <span>
                                        <ServicesWidgetWrapper loaded={this.state.loaded.service} services={this.state.widget.service}/>
                                    </span>
                                    <Link to="/service-ledger" title="view all" className="viewAll">View All</Link>
                                </div>) :
                                (<ServicesWidgetWrapper loaded={this.state.loaded.service} services={this.state.widget.service}/>)
                            }
                        </li>
                        <li className="widgetCommonProp lightBlueBorder linkPresent">
                            <div className="widgetHead clearfix">
                                <h3 className="pull-left">contacts</h3>{this.canAccess(permissions.contacts.add) ?
                                    (<Link to={'contacts/add'} className="pull-right">
                                        <input type="submit" value="Add Contact" id="submitForm" className="reOpenBtn pull-right darkPinkBtn rippleBtn" />
                                    </Link>)
                                    : (null)
                                }
                            </div>
                            {this.canAccess(permissions.contacts.listing) ?
                                (<div>
                                    <span>
                                        <ContactsWidgetWrapper loaded={this.state.loaded.contacts} contacts={this.state.widget.contacts}/>
                                    </span>
                                    <Link to="/contacts" title="view all" className="viewAll">View All</Link>
                                </div>) :
                                (<ContactsWidgetWrapper loaded={this.state.loaded.contacts} contacts={this.state.widget.contacts}/>)
                            }
                        </li>
                        <li className="widgetCommonProp lightBlueBorder hide">
                            <WidgetHead title="Alert" />
                            <WidgetBody iconClass="DBicon-alert widgetInfoIcon" infoText={CONSTANTS.UI_MESSAGES.widgetNoData}/>
                        </li>
                        <li className="widgetCommonProp lightBlueBorder">
                            {
                                this.canAccess(permissions.utilization.view) ?
                                    (<div>
                                        <div className="widgetHead clearfix">
                                            <Link className="ssoLinks pull-left"
                                                to='/sso-page?moduleName=DBU'> UTILIZATION
                                            </Link>
                                        </div>
                                        <WidgetBody iconClass="SSO-DB_Icon widgetInfoIcon" />
                                    </div>) :
                                    (<div>
                                        <div className="widgetHead clearfix">
                                            <h3 className="pull-left">UTILIZATION</h3>
                                        </div>
                                        <WidgetBody iconClass="DBicon-contacts widgetInfoIcon" infoText="Unauthorized module"/>
                                    </div>
                                    )
                            }
                        </li>

                    </ul>
                    <ul className="ssoWidgetList">
                        { this.showSSOModules(permissions) }
                        {
                            this.canAccess(permissions.cmp.view) ?
                                <li>
                                    <a href='https://cmp.netmagicsolutions.com/glass/login'
                                        target='_blank'>CMP
                                    </a>
                                </li> : (null)
                        }
                    </ul>
                </div>
            </div>
        );
    },
});

export default Dashboard;
