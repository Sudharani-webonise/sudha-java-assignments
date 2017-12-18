import Invoice from '../../../services/outstanding';
import Utility from '../../../mixins/basicUtils';



var InvoiceListHeader = createReactClass({
  mixins: [Invoice],

  sortByField (event) {
    var parentContext = this.props.super;
    var isDescending = _.contains(event.currentTarget.classList, 'sortArrow');
    parentContext.state.invoices.outstandingListData.transaction.outstandingListInfo.invoiceList =
      Utility.getSortedList(Utility.getVal(parentContext.state,
      'invoices.outstandingListData.transaction.outstandingListInfo.invoiceList'),
      isDescending, event.currentTarget.dataset.fieldName);
    parentContext.setState({ invoices: parentContext.state.invoices });
  },

  render () {
    return (
      <li className="rowCell tableHeader">
        <div className="container innerTableWrap paddingZero">
          <span className="dataCell forSpace"></span>
          <span className="dataCell invoiceNoCell sortingCell" onClick={this.sortByField} data-field-name="invoiceId">
            Invoice No. <i className="sortArw"></i>
          </span>
          <span className="dataCell fromCell sortingCell" onClick={this.sortByField} data-field-name="chargeFromDate">
            From <i className="sortArw"></i>
          </span>
          <span className="dataCell toCell sortingCell" onClick={this.sortByField} data-field-name="chargeToDate">
            To <i className="sortArw"></i>
          </span>
          <span className="dataCell sofCell sortingCell" onClick={this.sortByField} data-field-name="poRef">
            SOF <i className="sortArw"></i>
          </span>
          <span className="dataCell poCell sortingCell" onClick={this.sortByField} data-field-name="poRef">
            PO <i className="sortArw"></i>
          </span>
          <span className="dataCell pendingSinceCell sortingCell" onClick={this.sortByField} data-field-name="days">
            <span className="doulbleLineText">Pending <br/>since (Days)</span>
            <i className="sortArw"></i>
          </span>
          <span className="dataCell invoiceValueCell sortingCell" onClick={this.sortByField} data-field-name="originalAmount">
            Invoice value <i className="sortArw"></i>
          </span>
          <span className="dataCell outStatusCell sortingCell" onClick={this.sortByField} data-field-name="status">
            Status <i className="sortArw"></i>
          </span>
          <span className="dataCell tdsPercentCell dropDownWrapper sortingCell" >
            TDS %
          </span>
          <span className="dataCell amtOutCell sortingCell" onClick={this.sortByField} data-field-name="balamt">
            <span className="doulbleLineText">Amount <br/>Outstanding</span>
            <i className="sortArw"></i>
          </span>
          <span className="dataCell arrowCell"></span>
        </div>
      </li>
    )
  }
});

export default InvoiceListHeader;
