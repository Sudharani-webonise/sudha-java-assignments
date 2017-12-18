import { Link } from 'react-router';
import Session from '../../../mixins/sessionUtils';
import User from '../../../services/user';
import CONSTANTS from '../../../constants/app-constant';
import shoppingCart from '../../../services/shoppingCart';
import Loader from 'react-loader';
import LoaderOptions from '../../../mixins/loader';
import http from '../../../mixins/restutils';
import UI from '../../../mixins/ui';
import Invoice from '../../../services/outstanding';

var PaymentSuccess = createReactClass({

    getInitialState() {
        return {
            data: Session.get('ShoppingCartPricingDetails') || [],
            sofId: '',
            dontStop: true,
            loaded: true,
            refnum: '',
            amountPaid: '',
            transactionId: '',
            transactionTime: '',
            sofs: []
        }
    },

    contextTypes: {
        router: React.PropTypes.object
    },

    componentWillMount() {
        if (this.state.dontStop) {
            this.readGetVariables();
        }
    },

    componentWillUnmount() {
        /**
         * To clear ebs response url
         */
        window.location.search = '';
    },

    readGetVariables() {
        var status = this.getUrlVars()["ResponseMessage"];
        let state = this.state;
        state.refnum = this.getUrlVars()["MerchantRefNo"];
        state.amountPaid = this.getUrlVars()["Amount"];
        state.transactionId = this.getUrlVars()["TransactionID"];
        state.transactionTime = this.decodeItems(this.getUrlVars()["DateCreated"]);
        state.paymentId = this.getUrlVars()["PaymentID"];
        if (status === "Transaction+Successful") {
            // Do nothing
        }
        else {
            this.state.sofId = "Transaction was not complete";
        }
        this.setState(this.state)
    },

    decodeItems(time) {
        return decodeURIComponent(time).replace('+', ' ')
    },

    getUrlVars() {
        var vars = {};
        var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function (m, key, value) {
            vars[key] = value;
        });
        return vars;
    },

    render() {
        var status =  Session.get('outstandingUpdate')
        return (
            <div className="ticketListSection outStandingWrap peopleWrap">
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
                                    <li className="active">Payment Successful</li>
                                </ol>
                                <h1 className="mainTitle">Payment Successful </h1>
                            </div>
                            <div className="col-xs-2 col-sm-2 col-md-2">
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
                                            <div className="col-xs-6 text-right info_style">{this.state.refnum}</div>
                                        </div>
                                        <div className="row">
                                            <div className="col-xs-6 head_style">Transaction Date & Time:</div>
                                            <div className="col-xs-6 text-right info_style">{this.state.transactionTime}</div>
                                        </div>
                                        <div className="row">
                                            <div className="col-xs-6 head_style">Amount Paid:</div>
                                            <div className="col-xs-6 text-right info_style"><i class="rupeeIcon">₹</i> {Number(this.state.amountPaid).toFixed(2)}</div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </Loader>
            </div>
        )
    }
});


export default PaymentSuccess;
