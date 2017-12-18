import CONSTANTS from '../../../constants/app-constant';
import WidgetHead from '../../commons/WidgetHead';
import WidgetBody from '../../commons/WidgetBody';
import http from '../../../mixins/restutils';
import Session from '../../../mixins/sessionUtils';
import Utility from '../../../mixins/basicUtils';
import Permission from '../../../services/permission';
import { Link } from 'react-router';
import Loader from 'react-loader';
import LoaderOptions from '../../../mixins/loader';
import UI from '../../../mixins/ui';
import Select from 'react-select';
import Ticket from '../../../services/ticket';
import User from '../../../services/user';

var InternalSplashScreen = createReactClass({

    contextTypes: {
        router: React.PropTypes.object
    },

    getInitialState() {
        return {
            mainCustomer: {},
            associatedCustomer: {},
            project: {},
            mainCustomersList: [],
            associatedCustomers: [],
            projects: []
        }
    },

    componentDidMount() {
        this.getMainCustomers();
        Session.removeItems(['mainCustomer', 'associatedCustomer', 'project']);
    },

    getMainCustomers() {
        let mainCustomers = Session.get('mainCustomerList');
        _.each(mainCustomers, (customer) => {
            customer.label = customer.cvName;
            customer.value = customer.cvName;
        });
        this.setState({ mainCustomersList: mainCustomers });
    },

    onChangeSelectedMainCustomer(value) {
        if (value) {
            var state = this.state;
            var selectedMainCustomer = _.findWhere(state.mainCustomersList,
                { value: value });
            state.mainCustomer = selectedMainCustomer || '';
            this.setState(state);
            Session.set('mainCustomer', selectedMainCustomer);
            Session.set('associatedCustomer', selectedMainCustomer);
            Ticket.getAssociatedCustomers()
                .then((res) => {
                    var res = res && res.listOfSupportToCustomerInfo;
                    _.each(res, (customer) => {
                        customer.label = customer.cvName;
                        customer.value = customer.cvName;
                    });
                    Session.set('customerBeans', res);
                    this.setState({ associatedCustomers: res });
                })
                .catch((err) => {
                    UI.notifyError(err && err.message);
                })
            var params = {
                id: User.getMainCustomer('cvCrmId'),
                associateCustomerId: User.getMainCustomer('cvCrmId')
            }
            Ticket.getProjects(params)
                .then((res) => {
                    _.each(res, (project) => {
                        project.label = project.projectName;
                        project.value = project.projectName;
                    });
                    this.setState({ projects: res });
                })
                .catch((err) => {
                    UI.notifyError(err && err.message);
                })
        } else {
            this.setState({ associatedCustomer: {}, project: {}, associatedCustomers: [], projects: [], mainCustomer: {} });
        }
    },

    onChangeSelectedAssCustomer(value) {
        if (value) {
            var state = this.state;
            var selectedAssoCustomer = _.findWhere(state.associatedCustomers,
                { value: value });
            state.associatedCustomer = selectedAssoCustomer || '';
            this.setState(state);
            Session.set('associatedCustomer', selectedAssoCustomer);
            var params = {
                id: User.getMainCustomer('cvCrmId'),
                associateCustomerId: User.getAssociateCustomer('cvCrmId')
            }
            Ticket.getProjects(params)
                .then((res) => {
                    _.each(res, (project) => {
                        project.label = project.projectName;
                        project.value = project.projectName;
                    });
                    this.setState({ projects: res });
                })
                .catch((err) => {
                    UI.notifyError(err && err.message);
                })
        } else {
            this.setState({ project: {}, projects: [], associatedCustomer: {} });
        }
    },

    onChangeSelectedProject(value) {
        if(value) {
            var state = this.state;
            var project = _.findWhere(state.projects,
                { value: value });
            state.project = project;
            this.setState(state);
            Session.set('project', project);
        } else {
            this.setState({ project: {} });
        }
    },

    redirectToDashboard() {
        var route = '/splash-screen-internal';
        Session.set('activeCustomerId', User.getAssociateCustomer('cvCrmId') ||
            User.getMainCustomer('cvCrmId'));
        if (_.has(this.state.mainCustomer, 'cvCrmId')) {
            route = '/dashboard';
        }
        return route;
    },

    render() {
        let {mainCustomersList, mainCustomer, associatedCustomer,
            associatedCustomers, projects, project} = this.state;
        return (
            <div className="mainSplashScreen internalSplashScreen">
                <div className="container">
                    <div className="row">
                        <div className="col-xs-6 col-xs-offset-3">
                            <div className="customerSelectWrap">
                                <span className="DBicon-customer"></span>
                                <h3>
                                    Hi {User.getUserInfo('loginUserName')}, Please select your customer.
                                </h3>
                                <div className="customerSelectForm">
                                    <div className="reactSelectRow">
                                        <label htmlFor="selectMainCustomer">Main Customer <span className="mandatoryStar">*</span></label>
                                        <Select autofocus options={mainCustomersList}
                                            simpleValue ref="selectMainCustomer" name="selected-main-customer"
                                            value={mainCustomer.cvName} onChange={this.onChangeSelectedMainCustomer}
                                            searchable={true} clearable={true} placeholder="Select Main Customer" />
                                    </div>
                                    <div className="reactSelectRow">
                                        <label htmlFor="selectAssoCustomer">Associated Customer</label>
                                        <Select autofocus options={associatedCustomers}
                                            simpleValue ref="selectAssoCustomer" name="selected-assoc-customer"
                                            value={associatedCustomer.cvName} onChange={this.onChangeSelectedAssCustomer}
                                            searchable={true} clearable={true} placeholder="Select Associated Customer" />
                                    </div>
                                    <div className="reactSelectRow">
                                        <label htmlFor="selectProject">Projects</label>
                                        <Select autofocus options={projects}
                                            simpleValue ref="selectProject" name="selected-project"
                                            value={project.projectName} onChange={this.onChangeSelectedProject}
                                            searchable={true} clearable={true} placeholder="Select Projects" />
                                    </div>
                                    <div className="customerSelectBtn">
                                        <Link to={this.redirectToDashboard()} className="darkPinkBtn">Go To Dashboard</Link>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
});

export default InternalSplashScreen;
