import { Link } from 'react-router';

var AssistanceBlock = createReactClass({
  render() {
    let totalPrice = _.first(this.props.totalPrice.billgroup);
    return (
      <div className="col-md-3 col-lg-3 pull-right">
      <div className="assistanceBlock hide">
        <ul>
          <li>
            <i className="shareIcon"></i><span>Share this Cart</span>
          </li>
          <li>
            <i className="callIcon"></i><span>Call us at - <Link>1800 103 3130</Link></span>
          </li>
          <li>
            <i className="chatIcon"></i><span>Chat for assistance</span>
          </li>
        </ul>
      </div>
      <div className="totalPricingBlock">
        <h1>Total Price</h1>
        <ul className="pricingList">
          <li>
            <span>One Time Charges</span>
            <span>&#8377; { totalPrice ?  totalPrice.potc_tax : 0}</span>
            <div className="clear"></div>
          </li>
          <li>
            <span>Monthly Charges</span>
            <span>&#8377; { totalPrice ? totalPrice.pmrc_tax : 0}</span>
            <div className="clear"></div>
          </li>
        </ul>
      </div>
    </div>
    )
  }
});

export default AssistanceBlock;
