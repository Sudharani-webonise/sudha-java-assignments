import { Link } from 'react-router';
import Utility from '../../../mixins/basicUtils';
import CONSTANTS from '../../../constants/app-constant';

var LineItem = createReactClass({
  mixins: [Utility],

  getActivatedCount (data) {
    return this.getVal(data, 'PartialDeliveryDetails.[0].ActivatedQty') ||
      CONSTANTS.NOT_AVAILABLE;
  },

  render() {
    var data = this.props.data;
    return (
      <div className="activesTab active">
        <div className="activeContent clearfix">
          <div className="clearfix firstRow">
            <span className="activeCheck pull-left">
              <label className="cssCheckBox">
                <input type="checkbox"/>
                <span className="NMIcon-checkbox"></span>
              </label>
            </span>
            <div className="serverData clearfix">
              <div className="clearfix pull-left">
                <span className="lineNumber">Line no: {data.LineNumber}</span>
              </div>
              <div className="ordersDetail pull-left">
                <ul className="list-unstyled">
                  <li>
                    <span className="title">ordered</span>
                    <span className="qty">{data.OrderedQty}</span>
                    <span className="qty">&nbsp;Qty</span>
                  </li>
                  <li>
                    <span className="title">Activated</span>
                    <span className="qty">{this.getActivatedCount(data)}</span>
                    <span className="qty">&nbsp;Qty</span>
                  </li>
                  <li>
                    <span className="title">Location</span>
                    <span className="qty">{this.props.data.Location}</span>
                  </li>
                </ul>
              </div>
            </div>
          </div>
          <div className="manageServer clearfix">
            <h5 className="balckText" dangerouslySetInnerHTML={{__html: this.props.data.ServerName}}></h5>
            <span className="balckText" dangerouslySetInnerHTML={{__html: this.props.data.ServiceAttribute}}></span>
          </div>
        </div>
      </div>
    );
  }
});

export default LineItem;
