import { Link } from 'react-router';
import CONSTANTS from '../../../constants/app-constant';
import Invoice from '../../../services/outstanding';
import Time from '../../../mixins/time';
import Loader from 'react-loader';
import LoaderOptions from '../../../mixins/loader';
import InvoiceListHeader from './InvoiceListHeader';
import InvoiceItemDetails from './InvoiceItemDetails';
import http from '../../../mixins/restutils';
import Session from '../../../mixins/sessionUtils';
import User from '../../../services/user';
import Utility from '../../../mixins/basicUtils';
import UI from '../../../mixins/ui';
import Outstanding from '../../../services/outstanding';

var FinalPayment = createReactClass({

    mixins: [Time, http, Session, Utility, User],
    contextTypes: {
        router: React.PropTypes.object
    },

    getInitialState() {
        return {
            data: Session.get('paymentInfo') || {},
            loaded: true,
            refnum: 'INVOICE_'+(new Date().getTime()).toString(36),
            payId: '',
            creditLimit: 0,
            netPayable: 0,
            netPrice: 0,
            taxPayable: 0,
            payByCredits: false,
            payByNetBanking: false,
            transactionId: null
        }
    },

    showAmount(value) {
        return this.getFormattedAmt(this.get('currencyId'), value || 0);
    },

    componentDidMount() {
        var info = Session.get('paymentInfo');
        if (info && info.netPayable) {
            var status = this.getUrlVars()["ResponseMessage"]
            this.state.payId = this.getUrlVars()["PaymentID"]
            Session.set('OutstandingTxnId', this.state.refnum)
            this.setState(this.state);
        }
        else {
            this.makeDefaultRequest()
        }
        this.getNetPayableAmt();
    },

    getNetPayableAmt() {
        let data = this.state.data;
        this.state.netPrice = data.totalOutstanding;
        this.state.taxPayable = data.totalTds;
        this.state.netPayable = data.netPayable;
        this.setState(this.state);
    },

    getUrlVars() {
        var vars = {};
        var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function (m, key, value) {
            vars[key] = value;
        });
        return vars;
    },

    makeDefaultRequest() {
        var outstandingType = Session.get('outstandingType') || CONSTANTS.TRANSACTION_DATA.defaultOutstandingOption
        var uniqueTxnId = outstandingType + CONSTANTS.TRANSACTION_DATA.outstanding + Date.now();
        this.makePaymentRequest("NOebs" + Date.now(), uniqueTxnId)
    },

    makeRequest() {
        this.makePaymentRequest(this.state.payId, this.state.refnum)
    },

    payByCreditNotes() {
        let params = {
            credits: 'credits',
            merchantRefNumber: `MNP_CART${(new Date().getTime()).toString(36)}`
        };
        var outstandingType = Session.get('outstandingType') || CONSTANTS.TRANSACTION_DATA.defaultOutstandingOption
        let userObj = User.getCustomerParams(); 
        var requestBody = {
            billToCustomerId: userObj.customerId,
            supportToCustomerId: userObj.assoCustomerId,
            userId: Session.get('user') && Session.get('user').loginUserId,
            paymentId: paymentId || null,
            transactionId: transactionId || null,
            accountType: outstandingType,
            invoices: this.getInvoices()
        };
        let url = http.buildRestUrl(CONSTANTS.API_URLS.outstanding.payment, params);
        Outstanding.makePaymentRequest(url, requestBody)
            .then((res) => {
                this.state.transactionId = params.merchantRefNumber;
                this.setState(this.state);
                Session.remove('shoppingCartData');
                this.context.router.push('/thank-you');
            })
            .catch((err) => {
                UI.notifyError( CONSTANTS.UI_MESSAGES.shoppingCart.pricingError && error.statusText);
            })
    },

    makePaymentRequest(paymentId, transactionId) {
        this.state.loaded = false;
        this.setState(this.state)
        var outstandingType = Session.get('outstandingType') || CONSTANTS.TRANSACTION_DATA.defaultOutstandingOption
        let userObj = User.getCustomerParams();
        var mainCustomer = Session.get('mainCustomer');
        var associated = Session.get('associatedCustomer');
        var request = {
            billToCustomerId: userObj.customerId,
            supportToCustomerId: userObj.assoCustomerId,
            userId: Session.get('user') && Session.get('user').loginUserId,
            paymentId: paymentId || null,
            transactionId: transactionId || null,
            accountType: outstandingType,
            invoices: this.getInvoices()
        };
        Invoice.makePaymentRequest({PaymentRequest: request})
            .then((res) => {
                if(res.PaymentResponse.paymentStatus === 'SUCCESS')
                {
                    UI.notifySuccess( CONSTANTS.UI_MESSAGES.invoicePaymentSuccess);
                    this.state.loaded = false;
                    this.setState(this.state)
                    Session.set('outstandingUpdate',true);
                    this.context.router.push('outstanding/payment-success');
                }
                else {
                    Session.set('outstandingUpdate',false);
                    UI.notifyError(CONSTANTS.UI_MESSAGES.invoicePaymentError);
                    this.context.router.push('outstanding/payment-success');
                }
            })
            .catch((err) => {
                UI.notifyError(CONSTANTS.UI_MESSAGES.invoicePaymentError);
                this.state.loaded = false;
                this.setState(this.state)
                this.context.router.push('outstanding/payment-error');
            })
    },

    getInvoices() {
        var invoices = Session.get('finalInvoices');
        return _.map(invoices, (item) => {
            return {
                invoiceId: item.data.invoiceId,
                value: item.data.balanceAmount,
                tdsAmount: item.data.tdsAmount,
                amountOutstanding: item.data.amountOutstanding
            }
        });
    },

    goToShoppingCart() {
        this.context.router.push('/outstanding/make-payment')
    },

    goForCredit(event) {
        this.setState({
            payByCredits: event.currentTarget.checked,
            payByNetBanking: !event.currentTarget.checked
        });
    },

    goForNetBank(event) {
        this.setState({
            payByNetBanking: event.currentTarget.checked,
            payByCredits: !event.currentTarget.checked
        });
    },

    paymentForInvoices() {
        var outstandingType = Session.get('outstandingType') || CONSTANTS.TRANSACTION_DATA.defaultOutstandingOption
        let userObj = User.getCustomerParams();
        var requestBody = {
            billToCustomerId: userObj.customerId,
            supportToCustomerId: userObj.assoCustomerId,
            userId: Session.get('user') && Session.get('user').loginUserId,
            paymentId: this.state.paymentId || null,
            transactionId: this.state.refnum || null,
            accountType: outstandingType,
            invoices: this.getInvoices()
        };
        var params = {
            ebs: 'ebs',
            merchantRefNumber: this.state.refnum
        }
        let url = http.buildRestUrl(CONSTANTS.API_URLS.outstanding.payment, params);
        Invoice.makePaymentRequest(url, {PaymentRequest: requestBody})
            .then((res) => {
                if(res) {
                    this.state.loaded = true;
                    this.setState(this.state)
                    Session.set('outstandingUpdate',true);
                    $('#ebsFormAuto').submit();
                }
                else {
                    Session.set('outstandingUpdate',false);
                    UI.notifyError(CONSTANTS.UI_MESSAGES.invoicePaymentError);
                }
            })
            .catch((err) => {
                UI.notifyError(CONSTANTS.UI_MESSAGES.invoicePaymentError);
                this.state.loaded = true;
                this.setState(this.state);      
            })
    },

    render() {
        let userInfo = User.getCustomerParams();
        let selectedInvoices = _.first(Session.get('finalInvoices'));
        let businessUnit = Utility.getVal(selectedInvoices, 'data.businessUnit');
        var info = Session.get('paymentInfo');
        var outstandingType = Session.get('outstandingType') || CONSTANTS.TRANSACTION_DATA.defaultOutstandingOption
        var actionUrl = CONSTANTS.API_URLS.outstanding.invoiceEbs+'/'+businessUnit+'?billToCustomer='+
            userInfo.customerSugarId;
        var uniqueTxnId = outstandingType + CONSTANTS.TRANSACTION_DATA.outstanding + Date.now();
        var amountToPay = (info.totalOutstanding - info.totalCreditAmount - info.totalOnAccount - info.totalTds) || 0;
        let userData = Session.get('user');
        return (
            <div className="peopleWrap myCartPageWrap">
                <section className="midHeader">
                    <div className="container">
                        <div className="row">
                            <div className="col-xs-9">
                                <ol className="breadcrumb breadCrumbCustom clearfix">
                                    <li>
                                        <Link to="dashboard" className=
                                            {"blueLink"}>Back to Dashboard</Link>
                                    </li>
                                    <li>
                                        <Link to="outstanding" className=
                                            {"blueLink"}>Outstanding</Link>
                                    </li>
                                    <li className="active">Make Payment</li>
                                </ol>
                                <div className="paymentOrderIDWrap hide">
                                    <label>Order ID: </label>
                                    <span className="orderId">ABC9898</span>
                                </div>
                                <ul className="myCartPaymentProcess clearfix pull-left">
                                    <li className="processCompleted"><i>1</i> Outstanding Listing</li>
                                    <li className="currentCartStep"><i>2</i> Make Payment</li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </section>
                <Loader loaded={this.state.loaded} top="50%" left="50%" options={LoaderOptions}>
                    <div className="container">
                        <div className="paymentDetailsWrap">
                            <ul>
                                <li className="netPriceWrap">
                                    {/**Total Sales price */}
                                    <label>Net Price</label>
                                    <span>&#8377; {Number(this.state.netPrice).toFixed(2)}</span>
                                </li>
                                <li className="taxPayable">
                                    {/**Total Taxable price - Total Sales price */}
                                    <label>Tax Payable</label>
                                    <span>&#8377; {Number(this.state.taxPayable).toFixed(2)}</span>
                                </li>
                                <li className="netPaybleAmount">
                                    {/**Total Taxable price */}
                                    <label>Net payable</label>
                                    <span>&#8377; {Number(this.state.netPayable).toFixed(2)}</span>
                                    <ul>
                                        <li className="hide">
                                            <input type="radio" name="paymentOption"
                                                checked={this.state.payByCredits}
                                                onChange={this.goForCredit}
                                                ref="payByCredits"/> Available Credit Limit
                                            <span>&#8377; {this.state.creditLimit}</span>
                                            <i className="paymentOptionDivider">OR</i>
                                        </li>                                                                           <li className="useInternetBanking">
                                            <input type="radio" name="paymentOption" checked={this.state.payByNetBanking}
                                            onChange={this.goForNetBank}/> Use Net banking / Credit Card/ Debit Card
                                        </li>
                                    </ul>
                                </li>
                                <div className="clearfix cntrlBtns">
                                    {
                                    this.state.payByCredits ? <div className="buttonWrapper clearfix"><input onClick={this.payByCreditNotes} className='darkPinkBtn pull-left' name="submit" value="PAY" /><span className="blueLink pull-left" onClick={this.goToShoppingCart}>Cancel</span></div> :
                                        <div className="buttonWrapper clearfix"><form action={actionUrl} method="post" name="frm" id="ebsFormAuto" autoComplete="off" className="pull-left" onSubmit={this.paymentForInvoices}>
                                            <input type="text" hidden id="reference_no" value={this.state.refnum} name="reference_no" />
                                            <input type="text" hidden id="amount" name="amount" value={Number(this.state.netPayable).toFixed(2)}/>
                                            <input id="name" hidden name="name" type="text" value={userInfo.customerName} />
                                            <input id="email" hidden name="email" type="text" value={userData.loginUserEmail} />
                                            {
                                                this.state.payByNetBanking ?
                                                <input id="submit" className='darkPinkBtn'
                                                name="submit" value="PAY" type="submit" /> :
                                                <span className="darkPinkBtn">PAY</span>
                                            }
                                        </form>
                                        <span className="blueLink pull-left" onClick={this.goToShoppingCart}>Cancel</span>
                                        </div>
                                    }
                                </div>
                            </ul>
                        </div>
                    </div>
                </Loader>
            </div>
        );
    }
});

export default FinalPayment;
