import Time from '../../../mixins/time';
import CONSTANTS from '../../../constants/app-constant';
import Utility from '../../../mixins/basicUtils';
import Session from '../../../mixins/sessionUtils';

var InvoiceItem = createReactClass({
    mixins: [Time, Utility, Session],

    getInitialState() {
        return {
            checked: false
        }
    },

    getPendingDays(pendingDate) {
        var dateBgColor = 'remainDays';
        if (pendingDate >= CONSTANTS.PENDING_DAYS.MAX) {
            dateBgColor = 'remainDays pinkHilight';
        } else if (pendingDate > CONSTANTS.PENDING_DAYS.MIN && pendingDate < CONSTANTS.PENDING_DAYS.MAX) {
            dateBgColor = 'remainDays lightHilight';
        } else if (pendingDate <= CONSTANTS.PENDING_DAYS.MIN) {
            dateBgColor = 'remainDays blueHilight';
        }
        return dateBgColor;
    },

    selectInvoice(invoice, event) {
        this.setState({ checked: !this.state.checked });
        this.props.selectInvoice({ index: this.props.index, data: invoice },
            event.currentTarget.checked);
    },


    render(data) {
        var data = this.props.data;
        var uniqId = "tickBox" + this.props.index;
        return (
            <li className="rowCell tabelDataCell invoiceInfo">
                <div className="cellInWrap">
                    <span className="dataCell forSpace">
                        <label htmlFor={uniqId} className={this.state.checked ? "selectInvoiceBox checked" : "selectInvoiceBox"}></label>
                        <input className="" type="checkbox" id={uniqId} onClick={this.selectInvoice.bind(null, data)} />
                    </span>
                    <a href="javascript: void(0);" className="dataCell invoiceNoCell cellColor">
                        {data.invoiceId || CONSTANTS.NOT_AVAILABLE}
                    </a>
                    <span className="dataCell fromCell">
                        {data.chargeFromDate}
                    </span>
                    <span className="dataCell toCell">
                        {data.chargeToDate}
                    </span>
                    <span className="dataCell sofCell">
                        {data.contractNumber || CONSTANTS.NOT_AVAILABLE}
                    </span>
                    <span className="dataCell poCell">
                        {data.poRef || CONSTANTS.NOT_AVAILABLE}
                    </span>
                    <span className="dataCell pendingSinceCell">
                        <span className={this.getPendingDays(data.days)}>
                            {data.days || CONSTANTS.NOT_AVAILABLE}
                        </span>
                    </span>
                    <span className="dataCell invoiceValueCell text-right">
                        {this.getFormattedAmt(this.get('currencyId'), data.originalAmount) || CONSTANTS.NOT_AVAILABLE}
                    </span>
                    <span className="dataCell outStatusCell">
                        <span className="pinkText">{CONSTANTS.INVOICE_STATUS.OPEN}</span>
                    </span>
                    <span className="dataCell tdsPercentCell">
                        {data.lowTaxFlag === 'Y' || data.lowTaxFlag === 'y' ? data.tdsPercentage : CONSTANTS.DEFAULT_TDS}
                    </span>
                    <span className="dataCell amtOutCell text-right">
                        {this.getFormattedAmt(this.get('currencyId'), data.balanceAmount) || CONSTANTS.NOT_AVAILABLE}
                    </span>
                    <span className="dataCell text-right arrowCell">
                    </span>
                </div>
            </li>
        )
    }
});

export default InvoiceItem;
