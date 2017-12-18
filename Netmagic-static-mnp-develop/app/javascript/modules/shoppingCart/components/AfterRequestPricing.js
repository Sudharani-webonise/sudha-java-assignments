import { Link } from 'react-router';

var AfterRequestPricing = createReactClass({
  render() {
    let pricing = _.first(_.first(this.props.editProduct.pricingObj.billgroup).items);
    return (
      <div className="col-lg-6 getProductPricing">
        <div className="pricingBlock finalPricing">
          <div className="clearfix">
            <span className="pull-left priceTitle">One time charges</span>
            <span className="pull-right priceTitle">&#8377; {pricing.spotc_tax}</span>
          </div>
          <div className="clearfix">
            <span className="pull-left priceTitle">Monthly charges</span>
            <span className="pull-right priceTitle">&#8377; {pricing.spmrc_tax}</span>
          </div>

          <span className="priceTitle">Excess billing charges</span>
          <ul className="priceDetails">
          { _.map(pricing.sprod, (sprod,key) => {
                if(sprod.spvc === "0" || sprod.spvc === 0){
                  return (null)
                }
                else{
                  return (
                    <li key={key} className="clearfix">
                      <div className="pull-left">
                        <span className="chargeType">{sprod.subproddesc}</span>
                        <span className="priceLabel">{sprod.spdesc_var}</span>
                      </div>
                      <span className="pull-right amt">  &#8377; {sprod.spvc}</span>
                    </li>
                  )
                }
            })}
            <li className="clearfix">
              <div className="pull-left">
                <span className="chargeType">Variable charges</span>
              </div>
              <span className="pull-right amt">&#8377; {pricing.spvc_tax}</span>
            </li>
          </ul>
        </div>
      </div>
    )
  }
});

export default AfterRequestPricing;
