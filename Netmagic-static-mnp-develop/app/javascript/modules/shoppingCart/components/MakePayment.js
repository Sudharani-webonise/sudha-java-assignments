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
import Outstanding from '../../../services/outstanding';
import PostLoader from '../../commons/Loader';

var MakePayment = createReactClass({
    getInitialState() {
        return {
            data: Session.get('shoppingCartData') || [],
            loaded: true,
            creditLimit: 0,
            netPayable: 0,
            netPrice: 0,
            taxPayable: 0,
            payByCredits: false,
            payByNetBanking: false,
            transactionId: null,
            showLoader: false,
            merchantRefNumber: 'MNP_CART'+(new Date().getTime()).toString(36),
            actionUrl: null
        }
    },

    contextTypes: {
        router: React.PropTypes.object
    },

    componentDidMount() {
        let user = Session.get('user');
        Outstanding.getCreditLimits()
            .then((res) => {
                this.state.loaded = true;
                this.state.creditLimit = Utility.getVal(res, 'onlineCreditMsgdata.creditLimitTrasaction.onlineCredit.onlineCreditLimit');
                if(this.state.creditLimit < Number(this.state.netPayable)) {
                    this.refs.payByCredits.disabled = true;
                    this.state.payByNetBanking = true;
                }
                this.setState(this.state);
            })
            .catch((err) => {
                this.refs.payByCredits.disabled = true;
                this.state.payByNetBanking = true;
                this.state.loaded = true;
                this.setState(this.state);
            });
        this.getNetPayableAmt('potc_tax', 'pmrc_tax');
        this.getNetPayableAmt('potc_sale', 'pmrc_sale');
        $('#amount').val(this.getPrices());
        $('#reference_no').val("MNP_CART"+(new Date().getTime()).toString(36));
        var userData = Session.get('user');
        $('#name').val(userData.loginUserName || 'unknown')
        $('#email').val(userData.loginUserEmail || 'unknown')
    },

   getPrices() {
        var priceObj, totalOneTime = 0, totalMonthWise = 0;
        let data = this.state.data.billgroup;
        if (data != {}) {
            _.each(data, (pricingObj) => {
                totalOneTime += pricingObj.potc_tax
                totalMonthWise += pricingObj.pmrc_tax
            });
        }
        return Number(totalOneTime + totalMonthWise).toFixed(2)
    },

    goToShoppingCart() {
        this.context.router.push('/sof-details');
    },

    getNetPayableAmt(potc, pmrc) {
        var priceObj, totalOneTime = 0, totalMonthWise = 0;
        let data = this.state.data.billgroup;
        if (data != {}) {
            _.each(data, (pricingObj) => {
                totalOneTime += pricingObj[potc]
                totalMonthWise += pricingObj[pmrc]
            });
        }
        if (pmrc == 'pmrc_tax' && potc == 'potc_tax') {
            this.state.netPayable = (totalOneTime + totalMonthWise).toFixed(2);
        } else {
            this.state.netPrice = (totalOneTime + totalMonthWise).toFixed(2);
        }
        this.setState(this.state);
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

    goToEbs() {
        $('#ebsFormAuto').submit();
    },

    payByCreditNotes() {
        let request = {
            credits: 'credits',
            mainCustomerId: User.getMainCustomer('customerId'),
            merchantRefNumber: this.state.merchantRefNumber,
            transactionNumber: `${(new Date().getTime()).toString(36)}`
        };
        let url = http.buildRestUrl(CONSTANTS.API_URLS.shoppingCart.payment, request);
        this.setState({ showLoader: true });
        shoppingCart.sofCreation(url, Session.get('shoppingCartData'))
            .then((res) => {
                this.setState({
                    transactionId: request.merchantRefNumber,
                    showLoader: false
                });
                Session.remove('shoppingCartData');
                Session.set('creditLimitTemp', request);
                this.context.router.push('/shopping-cart/payment-successful');
            })
            .catch((err) => {
                this.setState({ showLoader: false });
                UI.notifyError( CONSTANTS.UI_MESSAGES.shoppingCart.pricingError && err.statusText);
            })
    },

    paidBy(e) {
        let userDetails = Session.get('userDetails');
        let url = http.buildRestUrl(CONSTANTS.API_URLS.shoppingCart.payment, {
            ebs: 'ebs',
            mainCustomerId: User.getMainCustomer('customerId') || userDetails && userDetails.kacid,
            merchantRefNumber: this.state.merchantRefNumber,
            transactionNumber: this.state.transactionId
        });
        let userInfo = User.getCustomerParams();
        this.setState({ showLoader: true });
        shoppingCart.sofCreation(url, Session.get('shoppingCartData'))
            .then((res) => {
                if(res) {
                    $('#ebsFormAuto').submit();                    
                } else {
                    this.setState({ showLoader: false });
                }
            })
            .catch((err) => {
                this.setState({ showLoader: false });
            })
    },

  render() {
        let data = Session.get('shoppingCartData');
        let billGroups = data && data.billgroup;
        let userInfo = User.getCustomerParams();
        let actionUrl = CONSTANTS.API_URLS.shoppingCart.ebsPay+'?billToCustomer='+
            userInfo.customerSugarId+'&supportToCustomer='+
            userInfo.assoCustomerSugarId
        let userData = Session.get('user');
        return (
            <div className="peopleWrap myCartPageWrap">
                <section className="midHeader">
                    <div className="container">
                        <div className="row">
                            <div className="col-xs-9">
                                <ol className="breadcrumb breadCrumbCustom clearfix">
                                    <li><Link to={User.getSplashScreenURL() }>Back to Home</Link></li>
                                    <li>
                                        <Link to="dashboard" className={"blueLink"}>Back to Dashboard</Link>
                                    </li>
                                    <li className="active">Make Payment</li>
                                </ol>
                                <div className="paymentOrderIDWrap">
                                    <label>Order ID: </label>
                                    <span className="orderId">ABC9898</span>
                                </div>
                                <ul className="myCartPaymentProcess clearfix pull-left">
                                    <li className="processCompleted"><i>1</i> Add Infra</li>
                                    <li className="processCompleted"><i>2</i> SOF Details</li>
                                    <li className="currentCartStep"><i>3</i> Make Payment</li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </section>
                <PostLoader show={this.state.showLoader}/>
                <Loader loaded={this.state.loaded} top="50%" left="50%" options={LoaderOptions}>
                    <div className="container">
                        <div className="paymentDetailsWrap">
                            <ul>
                                <li className="netPriceWrap">
                                    {/**Total Sales price */}
                                    <label>Net Price</label>
                                    <span>&#8377; {Number(this.state.netPrice)}</span>
                                </li>
                                <li className="taxPayable">
                                    {/**Total Taxable price - Total Sales price */}
                                    <label>Tax Payable</label>
                                    <span>&#8377; {(Number(this.state.netPayable) - Number(this.state.netPrice)).toFixed(2)}</span>
                                </li>
                                <li className="netPaybleAmount">
                                    {/**Total Taxable price */}
                                    <label>Net payable</label>
                                    <span>&#8377; {Number(this.state.netPayable)}</span>
                                    <ul>
                                        <li>
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
                                        <div className="buttonWrapper clearfix">
                                            <form action={actionUrl} method="post" name="frm" id="ebsFormAuto" autoComplete="off" className="pull-left" ref="ebsFormAuto">
                                                <input type="text" hidden id="reference_no" value={this.state.merchantRefNumber} name="reference_no" />
                                                <input type="text" hidden id="amount" name="amount" value={Number(this.getPrices()).toFixed(2)}/>
                                                <input id="name" hidden name="name" type="text" value={userInfo.customerName || userData.loginUserName} />
                                                <input id="email" hidden name="email" type="text" value={userData.loginUserEmail} />
                                                {
                                                    this.state.payByNetBanking ?
                                                    <span className='darkPinkBtn' onClick={this.paidBy}>PAY</span> :
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
        )
    }
});


export default MakePayment;
