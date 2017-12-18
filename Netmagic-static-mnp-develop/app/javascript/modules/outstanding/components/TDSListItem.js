import Time from '../../../mixins/time';
import CONSTANTS from '../../../constants/app-constant';
import Utility from '../../../mixins/basicUtils';
import Session from '../../../mixins/sessionUtils';

var TDSListItem = createReactClass({
  mixins: [Utility, Session],

  render (data) {
    var data = this.props.data;
    return (
      <li className="rowCell tabelDataCell">
        <div className="cellInWrap">
          <span className="dataCell forSpace"></span>
          <a href="javascript: void(0);" className="dataCell invoiceNoCell cellColor">
            {data && data.tdsId || CONSTANTS.NOT_AVAILABLE}
          </a>
          <span className="dataCell fromCell">
            {data && data.chargeFromDate || CONSTANTS.NOT_AVAILABLE}
          </span>
          <span className="dataCell toCell">
            {data && data.chargeToDate || CONSTANTS.NOT_AVAILABLE}
          </span>
          <span className="dataCell poCell">
            {data && data.poRef || CONSTANTS.NOT_AVAILABLE}
          </span>
          <span className="dataCell pendingSinceCell">
            <span>
              {data && data.days || CONSTANTS.NOT_AVAILABLE}
            </span>
          </span>
          <span className="dataCell invoiceValueCell text-right">
            {CONSTANTS.NOT_AVAILABLE}
          </span>
          <span className="dataCell outStatusCell">
            <span className="pinkText">{ CONSTANTS.INVOICE_STATUS.OPEN}</span>
          </span>
          <span className="dataCell amtOutCell text-right">
            {this.getFormattedAmt(this.get('currencyId'), data && data.balanceAmount) || CONSTANTS.NOT_AVAILABLE}
          </span>
          <span className="dataCell text-right arrowCell">
          </span>
        </div>
      </li>
    )
  },
});

export default TDSListItem;
