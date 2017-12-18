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
import Invoice from '../../../services/outstanding';

var PaymentOnAccount = createReactClass({
     getInitialState() {
        return {
            payByNetBanking: false,
            onAccountPayment: null,
            merchantRefNo: 'ON_ACC'+(new Date().getTime()).toString(36),
            paymentId: null,
            loaded: false
        }
    },

    componentDidMount() {
        var userData = Session.get('user');
        $('#reference_no').val("ON_ACC"+(new Date().getTime()).toString(36));
        $('#name').val(userData.loginUserName || 'unknown')
        $('#email').val(userData.loginUserEmail || 'unknown')
    },

    handleChange(event) {
        this.state.payByNetBanking = Number(event.currentTarget.value);
        this.setState(this.state);
    },

    paymentForAccount() {
        var outstandingType = Session.get('outstandingType') || CONSTANTS.TRANSACTION_DATA.defaultOutstandingOption
        let userObj = User.getCustomerParams();
        var requestBody = {
            CustID: userObj.customerId,
            paymentID: this.state.paymentId || null,
            TransactionID: this.state.merchantRefNo || null,
            BusinessUnit: outstandingType,
            Amount: this.state.payByNetBanking
        };
        this.setState({ loaded: false });
        Invoice.makePaymentRequest(CONSTANTS.API_URLS.outstanding.onAccount, { EBSOnAccountPaymentReq: requestBody })
            .then((res) => {
                if (res) {
                    Session.set('outstandingUpdate', true);
                    $('#ebsFormAuto').submit();
                }
                else {
                    Session.set('outstandingUpdate', false);
                    UI.notifyError(CONSTANTS.UI_MESSAGES.invoicePaymentError);
                    this.setState({ loaded: true });
                }
            })
            .catch((err) => {
                UI.notifyError(CONSTANTS.UI_MESSAGES.invoicePaymentError);
                this.setState({ loaded: true });
            })
    },

    render() {
        let userInfo = User.getCustomerParams();
        let actionUrl = CONSTANTS.API_URLS.outstanding.onAccountEbs+'?billToCustomer='+
            userInfo.customerSugarId;
        let userData = Session.get('user');
        return (
            <div className="ticketListSection outStandingWrap addContactWrap">
                <section className="breadCrumbSection dashboardBreadCrumb">
                    <div className="container">
                        <div className="row">
                            <div className="col-xs-10 col-sm-10 col-md-10">
                                <ol className="breadcrumb breadCrumbCustom clearfix">
                                    <li><Link to={User.getSplashScreenURL() }>Back to Home</Link></li>
                                    <li>
                                        <Link to="dashboard" className={"blueLink"}>Back to Dashboard</Link>
                                    </li>
                                    <li className="active">On Account Payment</li>
                                </ol>
                            </div>
                            <div className="col-xs-2 col-sm-2 col-md-2"> </div>
                        </div>
                    </div>
                </section>
                <div className="container paymentContainer">
                    <div className="row">
                        <div className="col-xs-6 col-xs-offset-3 pay_border pad20">
                            <div className="row pad_top20">
                                <div className="col-xs-10 col-xs-offset-1">
                                    <div className="row">
                                        <div className="col-xs-5 pad10">Customer Name: </div>
                                        <div className="col-xs-6 pad10 text-left">{userData && userData.loginUserName}</div>
                                    </div>
                                    <div className="row">
                                        <div className="col-xs-5 pad10 net_style">Amount Payable: </div>
                                        <div className="col-xs-6 pad10 text-right net_amount loginFormWrap margin0">
                                            <div className="formGroup">
                                                <input type="text" ref="" className="form-control customFormInput" id="inputAmount" required="true" onChange={this.handleChange}/>
                                                <label htmlFor="inputAmount" className="control-label customLabels">Enter Amount</label>
                                            </div>
                                        </div>
                                    </div>
                                    <div className="row pad_top20">
                                        <form action={actionUrl} method="post" name="frm" id="ebsFormAuto" autoComplete="off" className="pull-left">
                                            <input type="text" hidden id="reference_no" value={this.state.merchantRefNo} name="reference_no" />
                                            <input type="text" hidden id="amount" name="amount" value={this.state.payByNetBanking}/>
                                            <input id="name" hidden name="name" type="text" value={userInfo.customerName} />
                                            <input id="email" hidden name="email" type="text" value={userData.loginUserEmail} />
                                             {
                                                this.state.payByNetBanking ?
                                                <span className='darkPinkBtn' onClick={this.paymentForAccount}>PAY</span> :
                                                <span className="darkPinkBtn">PAY</span>
                                            }
                                        </form>
                                       <Link className="blueLink pull-left" to='/outstanding'>Cancel</Link>
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

export default PaymentOnAccount;