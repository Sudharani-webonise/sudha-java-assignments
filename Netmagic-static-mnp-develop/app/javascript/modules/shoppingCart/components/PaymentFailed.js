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
var AddVM = createReactClass({
	render () {
		return (

		
 <section className="ticketListSection outStandingWrap peopleWrap">
  <div className="blackOverlay"></div>
  <section className="midHeader">
    <div className="container">
      <div className="row">
        <div className="col-xs-4 col-sm-4 col-md-4">
          <ol className="breadcrumb breadCrumbCustom clearfix">
            <li>
              <Link to="dashboard" className="blueLink">
              Back to Dashboard
              </Link>
            </li>
            <li>
              <Link to="shopping-cart" className="blueLink">
              My Cart
              </Link>
            </li>
            <li className="active">Make Payment</li>
          </ol>
          <p className="order_style">Order ID:</p>
          <h2 className="order_title">ABC9898 </h2>
        </div>
        <div className="primaryConfirmationHeader">
          <ul>
            <li> <span className="greenCircle">1</span> <span className="font14">Make Payment</span> </li>
            <li> <span className="greyCircle">2</span> <span className="font14">Proceed for final payment</span> </li>
          </ul>
        </div>
      </div>
    </div>
  </section>
  <div className="container payfailContainer">
    <div className="row">
      <div className="col-xs-6 col-xs-offset-3 pay_border pad20">
        <div className="text-center">
          <p className="payment_failed"></p>
        </div>
        <p className="pay_fail">Your Transaction has been Declined</p>
        <div className="row pad_top20">
          <div className="col-xs-10 col-xs-offset-1">
            <div className="row">
              <div className="col-xs-6 pad10 amount_style">Net Price</div>
              <div className="col-xs-6 text-right pad10 amount_style"><i class="rupeeIcon">₹</i> 33,400.00</div>
            </div>
            <div className="row">
              <div className="col-xs-6 pad10 tax_style">Tax Payable</div>
              <div className="col-xs-6 text-right pad10"><i class="rupeeIcon">₹</i> 4,837</div>
            </div>
            <div className="row">
              <div className="col-xs-6 pad10 net_style">Net Payable</div>
              <div className="col-xs-6 text-right pad10 net_amount"><i class="rupeeIcon">₹</i> 38,237.00</div>
            </div>
            <div className="row">
              <div className="col-xs-6 pad10"><label className="radio_text"><input className="select_radio" type="radio" name="payment"/> Available Credit Limit</label></div>
              <div className="col-xs-6 text-right pad10 credit_amount"><i class="rupeeIcon">₹</i> 10,000.00</div>
            </div>
            <p className="text-center or_font">OR</p>
            <div className="row">
              <div className="col-xs-12 pad10"><label className="radio_text"><input className="select_radio" type="radio" name="payment"/> Use Net banking / Credit Card / Debit Card </label></div>
            </div>
			<div className="row pad_top20">
              <div className="col-xs-6 btn_cancel"><a className="blue">Cancel</a></div>
              <div className="col-xs-6 text-right pad10"><input type="submit" value="Pay Now" className="darkPinkBtn btn_pad"/></div>
            </div>

						
			
          </div>
        </div>
      </div>
    </div>
  </div>
</section>
				
		
				
		)
	}
});

export default AddVM;