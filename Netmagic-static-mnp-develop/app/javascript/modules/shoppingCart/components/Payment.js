import { Link } from 'react-router';
import CONSTANTS from '../../../constants/app-constant';
import Time from '../../../mixins/time';
import Loader from 'react-loader';
import LoaderOptions from '../../../mixins/loader';
import http from '../../../mixins/restutils';
import Session from '../../../mixins/sessionUtils';
import User from '../../../services/user';
import Utility from '../../../mixins/basicUtils';
import UI from '../../../mixins/ui';
import shoppingCart from '../../../services/shoppingCart';

var Payment = createReactClass({

    mixins: [Time, http, Utility, User],

    getInitialState() {
        return {
            sofId: '',
            loaded: false,
            dontStop: true,
            sofs: [],
        }
    },

    contextTypes: {
        router: React.PropTypes.object
    },

    componentDidMount() {
        this.sofsCreation();
        if (this.state.dontStop) {
            this.interval = setInterval(()=> {
                this.getSofs();
            }, 15000);
            setTimeout(() => {
                this.state.loaded = true;
            }, 30000);
        }
    },

    componentWillUnmount() {
        clearTimeout(this.interval);
        Session.remove('creditLimitTemp');
        /**
         * To clear ebs response url
         */
        window.location.search = '';
    },

    sofsCreation() {
        let creditLimitTemp = Session.get('creditLimitTemp');
        let url = http.buildRestUrl(CONSTANTS.API_URLS.shoppingCart.createSof, {
            merchantRefNumber: creditLimitTemp && creditLimitTemp.merchantRefNumber
        });
        shoppingCart.createSofs(url)
            .then((res) => {
                //Do nothing since result is true or false
                if(res) {
                    this.interval;
                }
            })
            .catch((err) => {
                //Do nothing since result is true or false
            })
    },

    getSofs() {
        let creditLimitTemp = Session.get('creditLimitTemp');
        let url = http.buildRestUrl(CONSTANTS.API_URLS.shoppingCart.sofs, { transactionNumber: creditLimitTemp && creditLimitTemp.merchantRefNumber });
        shoppingCart.fetchSofs(url)
            .then((res) => {
                this.state.sofs = res;
                this.setState(this.state);
            })
            .catch((err) => {
                this.state.loaded = true;
                this.setState(this.state);
            });
    },

     showSofItems(sofItems) {
        let sofs = (<tr>
            <td>No Sof Items</td>
        </tr>);
        const NA = CONSTANTS.NOT_AVAILABLE;
        let userDetails = Session.get('userDetails');
        if(_.isArray(sofItems) && sofItems.length) {
            sofs = _.map(sofItems, (data, key) => {
                return (
                    <tr>
                        <td>{data.sofNo || NA}</td>
                        <td>{data.billToCustomer || User.getMainCustomer('customerName') || `${userDetails && userDetails.firstName} ${userDetails && userDetails.lastName}` ||NA}</td>
                        <td>{data.supportToCustomer || User.getAssociateCustomer('customerName') || `${userDetails && userDetails.firstName} ${userDetails && userDetails.lastName}` || NA}</td>
                        <td>{data.projectName || NA}</td>
                        <td>{data.contractPeriod || NA} months</td>
                        <td>{data.serviceType || NA}</td>
                    </tr>
                )
            })
        }
        return sofs;
    },

    goToProvision(){
        this.context.router.push('/provision');
        window.location.search = '';
    },

    render() {
        let creditLimitTemp = Session.get('creditLimitTemp');
        return (
            <section className="ticketListSection outStandingWrap peopleWrap">
                <div className="blackOverlay"></div>
                <section className="midHeader">
                    <div className="container">
                        <div className="row">
                            <div className="col-xs-4 col-sm-4 col-md-4">
                                <ol className="breadcrumb breadCrumbCustom clearfix">
                                    <li><Link to={User.getSplashScreenURL()}>Back to Home</Link></li>
                                    <li>
                                        <Link to="dashboard" className="blueLink">Back to Dashboard</Link>
                                    </li>
                                    <li>
                                        <Link to="shopping-cart" className="blueLink">Shopping Cart</Link>
                                    </li>
                                    <li className="active">Payment</li>
                                </ol>
                                <h1 className="mainTitle">Order Details </h1>
                            </div>
                            <div className="primaryConfirmationHeader">
                                <ul>
                                    <li>
                                        <span className="greenCircle">1</span>
                                        <span>Payment Success</span>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </section>
                <Loader loaded={this.state.loaded} top="50%" left="50%" options={LoaderOptions}>
                    <div className="container paymentContainer">
                        <div className="row">
                            <div className="col-xs-10 col-xs-offset-1 pay_border pad20">
                                <div className="text-center"><p className="payment_done"></p></div>
                                <p className="pay_done">Payment Successful</p>
                                <div className="row pad_top20">
                                    <div className="col-xs-6 col-xs-offset-3">
                                        <div className="row">
                                            <div className="col-xs-6 head_style">Transaction ID:</div>
                                            <div className="col-xs-6 text-right info_style">{creditLimitTemp && creditLimitTemp.merchantRefNumber}</div>
                                        </div>
                                    </div>
                                </div>

                                <div className="row pad_15">
                                    <table className="table font13">
                                        <thead>
                                            <tr>
                                            <th className="tb_head">SOF No.</th>
                                            <th className="tb_head">Bill to Customer</th>
                                            <th className="tb_head">Support to Customer</th>
                                            <th className="tb_head">Project</th>
                                            <th className="tb_head">Period</th>
                                            <th className="tb_head">Type</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            {this.showSofItems(this.state.sofs)}
                                        </tbody>
                                    </table>
                                    <hr/>
                                </div>
                                <div className="row text-center">
                                    <p className="font14 pad_top20">
                                        {this.state.sofs.length ? <Link 
                                            onClick={this.goToProvision} className="red_head">Click here to open Provisioning Cart</Link>
                                        : (null)} | Those product for which technical details have been completed will be delivered automatically.</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </Loader>
            </section>
        );
    }
});

export default Payment;
