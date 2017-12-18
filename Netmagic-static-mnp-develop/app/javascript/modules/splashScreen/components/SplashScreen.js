import CONSTANTS from '../../../constants/app-constant';
import WidgetHead from '../../commons/WidgetHead';
import WidgetBody from '../../commons/WidgetBody';
import http from '../../../mixins/restutils';
import Session from '../../../mixins/sessionUtils';
import Utility from '../../../mixins/basicUtils';
import Permission from '../../../services/permission';
import { Link } from 'react-router';
import CustomerWidget from './CustomerWidget';
import Loader from 'react-loader';
import LoaderOptions from '../../../mixins/loader';
import UI from '../../../mixins/ui';
import User from '../../../services/user';

var SplashScreen = createReactClass({

    mixins: [Session, Utility],

    contextTypes: {
        router: React.PropTypes.object
    },

    getInitialState() {
        return {
            permissions: {},
            customerSplashPageDataBeans: [],
            loaded: false
        };
    },

    componentWillMount() {
        var customerBean = Session.get('customerBeans')
        if (_.isArray(customerBean) && customerBean.length) {
            Session.removeItems(['userPermissions', 'associatedCustomer', 'project']);
        }
        else {
            this.context.router.push('/dashboard');
        }
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
        if(User.getUserInfo('existingUser')) {
            Permission.fetchSplashData()
            .then((result) => {
                Session.set('splashData', this.sortCustomerData(result && result.customerSplashPageDataBeans));
                this.setState({
                    customerSplashPageDataBeans: this.sortCustomerData(result && result.customerSplashPageDataBeans),
                    loaded: true
                });
            })
            .catch((err) => UI.notifyError(err && err.statusText));
        }
    },

    getSplashData() {
        var loginCustomerData = this.sortCustomerData(this.get('user') && this.get('user').customerSplashPageDataBeans);
        var splashData = Session.get('splashData');
        return Session.get('splashScreen') ? splashData : loginCustomerData
    },

    sortCustomerData(customerData) {
        return _.sortBy(customerData, 'isMainCustomer').reverse();
    },

    render() {
        let customersData = this.getSplashData();
        return (
            <Loader loaded={this.state.loaded} top="50%" left="50%" options={LoaderOptions}>
                <div className="mainSplashScreen">
                    <div className="splashScreenNav">
                        <div className="upperList clearfix">
                            <ul>
                                <li className="ticketNum"><span>Tickets</span></li>
                                <li className="severityOne"><span>Sev 1</span></li>
                                <li className="severityTwo"><span>Sev 2</span></li>
                                <li className="alerts hide"><span>Alerts</span></li>
                                <li className="assets"><span>Assets</span></li>
                                <li className="outstanding hide"><span>Outstanding</span></li>
                            </ul>
                        </div>
                    </div>
                    <ul className="customerList">
                        {customersData && customersData.map(function (data, key) {
                            return (<CustomerWidget data={data} key={key} />)
                        })}
                    </ul>
                </div>
            </Loader>
        )
    }
});

export default SplashScreen;
