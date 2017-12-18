import { Link } from 'react-router';

var MakePayment = createReactClass({
	render () {
		return (
	<div className="peopleWrap myCartPageWrap">
    <section className="midHeader">
      <div className="container">
        <div className="row">
          <div className="col-xs-9">
            <ol className="breadcrumb breadCrumbCustom clearfix">
              <li><a href="javascript:void(0);">Back to Dashboard</a></li>
              <li><a href="javascript:void(0);">My Cart</a></li>
              <li className="active">Make Payment</li>
            </ol>
            <div className="paymentOrderIDWrap">
              <label>Order ID :</label>
              <span className="orderId">ABC9898</span>
            </div>
            <ul className="myCartPaymentProcess clearfix pull-left">
              <li><i>1</i> Add Infra</li>
              <li><i>2</i> SOF Details</li>
              <li className="currentCartStep"><i>3</i> Make Payment</li>
            </ul>
          </div>
        </div>
      </div>
    </section>
    <div className="container">
       <div className="paymentDetailsWrap">
          <ul>
            <li className="netPriceWrap">
              <label>Net Price</label>
              <span>&#8377; 33,400.00</span>
            </li>
            <li className="taxPayable">
              <label>Tax Payable</label>
              <span>&#8377; 4,837.35</span>
            </li>
            <li className="netPaybleAmount">
              <label>Net payable</label>
              <span>&#8377; 38,237.35</span>
              <ul>
                <li>
                  <input type="radio" name="paymentOption"/> Available Credit Limit
                  <span>&#8377; 10,000.00</span>
                  <i className="paymentOptionDivider">OR</i>
                </li>
                <li className="useInternetBanking">
                  <input type="radio" name="paymentOption"/> Use Net banking / Credit Card/ Debit Card
                </li>
              </ul>
            </li>
            <li className="paymentPayBtns clearfix">
                <a href="javascript:void(0)" className="blueLink">Cancel</a>
                <a href="javascript:void(0)" className="darkPinkBtn pull-right">Pay Now</a>
            </li>
          </ul>

       </div>
    </div>
</div>
		)
	}
});


export default MakePayment;
