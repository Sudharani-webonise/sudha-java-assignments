import Time from '../../../mixins/time';
import CONSTANTS from '../../../constants/app-constant';
import Utility from '../../../mixins/basicUtils';
import UI from '../../../mixins/ui';
import Session from '../../../mixins/sessionUtils';

var InvoiceItemDetails = createReactClass({
    mixins: [Time, Utility, Session, UI],

    getInitialState() {
        var balanceAmount = Number(this.props.data.balanceAmount);
        return {
            collapse: false,
            paymentCheck: false,
            amountOutstanding: balanceAmount,
            totalCreditAmt: 0,
            totalOutstandingAmt: 0,
            tds: 0,
            amountOutstandingTemp: balanceAmount,
            amountOutstandingTds : balanceAmount,
            amountConfirmed: false
        };
    },

    componentDidMount() {
        var data = this.props.data;
        data.lowTaxFlag === CONSTANTS.YES ? this.updateTds(Number(data.tdsPercentage)) : this.updateTds(Number(data.tdsPercentage));
        var invoices = this.props.invoices
        var targetInvoice = _.find(invoices,
            (item) => item && item.data && (item.data.invoiceId === this.props.data.invoiceId));
        targetInvoice.data.tdsPercentage = 0;
        targetInvoice.data.tdsAmount = (this.state.amountOutstandingTds*Number(0))/100;
        targetInvoice.data.amountOutstanding = targetInvoice.data.originalAmount - targetInvoice.data.tdsAmount;
        this.props.updateProp({ invoices: invoices });
    },

    toggle() {
        this.setState({ collapse: !this.state.collapse })
    },

    getAccordianBtnClass() {
        return this.classSet({
            'accordianBtn': true,
            'active': this.state.collapse
        });
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

    //TODO : deprecate
    selectInvoice(invoice, event) {
        this.setState({ checked: !this.state.checked});
        this.props.selectInvoice({index: this.props.index, data: invoice},
          event.currentTarget.checked);
    },

    //TODO : deprecate
    selectCreditNote() {
        this.setState({ paymentCheck: !this.state.paymentCheck});
    },

    getToggleClassSet() {
        return this.classSet({
            'accordianData': true,
            'paymentConfirmationDetails': true,
            'clearfix': true,
            'show': this.state.collapse,
            'hide': true
        });
    },

    addOrRemoveCreditNote(noteId, event) {
        var checked = event.currentTarget.checked;
        var creditNotes = this.props.creditNotes
        var note = _.findWhere(creditNotes, { creditNoteId: noteId });
        var noteIndex = _.indexOf(creditNotes, note);
        var invoice = this.props.data
        var invoiceIndex = _.indexOf(this.props.invoices, invoice);
        var usePartial = note.balanceAmount > this.state.amountOutstanding
        var amountUsed = usePartial ? this.state.amountOutstanding : note.balanceAmount
        if (checked && amountUsed) {
            invoice.creditNoteList.push({
                id: note.creditNoteId,
                amountUsed: amountUsed,
                status: usePartial ? 'PARTIAL' : 'USED',
                amountRemaining: usePartial ? note.balanceAmount - this.state.amountOutstanding : 0
            })
            if (usePartial) {
                note.balanceAmount = note.balanceAmount - this.state.amountOutstanding
            }
            else {
                note.balanceAmount = 0;
                note.used = true;
            }
            this.state.totalCreditAmt = (this.state.totalCreditAmt || 0) + amountUsed;
            this.state.amountOutstanding = this.state.amountOutstandingTemp - this.state.totalCreditAmt - this.state.totalOutstandingAmt - (this.state.amountOutstandingTds*Number(this.refs.tds.value))/100
        }
        else {
            var removeNote = _.findWhere(invoice.creditNoteList, { id: noteId });
            note.balanceAmount = note.balanceAmount + removeNote.amountUsed;
            note.used = false;
            this.state.totalCreditAmt = (this.state.totalCreditAmt || 0) - removeNote.amountUsed;
            this.state.amountOutstanding = this.state.amountOutstandingTemp - this.state.totalCreditAmt - this.state.totalOutstandingAmt - (this.state.amountOutstandingTds*Number(this.refs.tds.value))/100
            invoice.creditNoteList = _.reject(invoice.creditNoteList,
                (item) => item.id === noteId);
        }
        this.setState(this.state)
        invoice.amountOutstanding = this.state.amountOutstanding
        this.props.updateInvoiceNote(invoice, invoiceIndex, note, noteIndex);
    },

    addOrRemoveOnaccount(outstandingId, event) {
        var checked = event.currentTarget.checked;
        var onAccounts = this.props.onAccounts
        var note = _.findWhere(onAccounts, { onAccountPaymentId: outstandingId });
        var noteIndex = _.indexOf(onAccounts, note);
        var invoice = this.props.data
        var invoiceIndex = _.indexOf(this.props.invoices, invoice);
        var usePartial = note.balanceAmount > this.state.amountOutstanding
        var amountUsed = usePartial ? this.state.amountOutstanding : note.balanceAmount
        if (checked && amountUsed) {
            invoice.onAccountList.push({
                id: note.onAccountPaymentId,
                amountUsed: amountUsed,
                status: usePartial ? 'PARTIAL' : 'USED',
                amountRemaining: usePartial ? note.balanceAmount - this.state.amountOutstanding : 0
            })
            if (usePartial) {
                note.balanceAmount = note.balanceAmount - this.state.amountOutstanding
            }
            else {
                note.balanceAmount = 0;
                note.used = true;
            }
            this.state.totalOutstandingAmt = (this.state.totalOutstandingAmt || 0) + amountUsed;
            this.state.amountOutstanding = this.state.amountOutstandingTemp - this.state.totalCreditAmt - this.state.totalOutstandingAmt - (this.state.amountOutstandingTds*Number(this.refs.tds.value))/100
            }
        else {
            var removeNote = _.findWhere(invoice.onAccountList, { id: outstandingId });
            note.balanceAmount = note.balanceAmount + removeNote.amountUsed;
            note.used = false;
            this.state.totalOutstandingAmt = (this.state.totalOutstandingAmt || 0) - removeNote.amountUsed;
            this.state.amountOutstanding = this.state.amountOutstandingTemp - this.state.totalCreditAmt - this.state.totalOutstandingAmt - (this.state.amountOutstandingTds*Number(this.refs.tds.value))/100
            invoice.onAccountList = _.reject(invoice.onAccountList,
                (item) => item.id === outstandingId);
        }
        invoice.amountOutstanding = this.state.amountOutstanding
        this.setState(this.state)
        this.props.updateInvoiceAcc(invoice, invoiceIndex, note, noteIndex);
    },

    getTotalAmount(list) {
        return _.chain(list)
            .pluck('amountUsed')
            .reduce((res, item) => res + Number(item))
            .value();
    },

    //TODO : deprecare
    updateOutstanding(status) {
        var {totalOutstandingAmt, totalCreditAmt} = this.state;
        var tds = Number(this.refs.tds.value);
        this.state.amountOutstanding -= (totalOutstandingAmt + totalCreditAmt + tds);
        this.state.amountConfirmed = status;
        this.setState(this.state);
    },

    showOnAccounts() {
        return _.map(this.props.onAccounts, (data, key) => {
            var uniqId = "tickBox" + key;
            return (
                <li className={data.used ? 'creditList ' : 'creditList'} key={key} data-id={data.onAccountPaymentId}>
                    <label htmlFor={uniqId} className={this.state.checked ? "selectInvoiceBox checked" : "selectInvoiceBox"}></label>
                    <input type="checkbox" id={uniqId} onClick={this.addOrRemoveOnaccount.bind(null, data.onAccountPaymentId)} />
                    <span className="creditValue">{data.balanceAmount ? data.balanceAmount : "Used"}</span>
                    <label className="creditID">{data.onAccountPaymentId}</label>
                </li>
            )
        })
    },

    showCreditNotes() {
        return _.map(this.props.creditNotes, (data, key) => {
            var uniqId = "tickBox" + key;
            if (data) {
                return (
                    <li className={data.used ? 'creditList ' : 'creditList'} key={key} data-id={data.creditNoteId}>
                        <input type="checkbox" id={uniqId} onClick={this.addOrRemoveCreditNote.bind(null, data.creditNoteId)} />
                        <span className="creditValue">{data.balanceAmount ? data.balanceAmount : "Used"}</span>
                        <label className="creditID">{data.creditNoteId}</label>
                    </li>)
            }
        })
    },

    updateTds(val) {
        var tds;
        var temptds = this.refs.tds.value
        if(temptds.substr(temptds.length - 1) != '.') {
            val ? (tds = val) : (tds = this.refs.tds.value || 0)
            tds = Number(String(tds).replace(/[^0-9.]/g,''));
            tds > 10 ? tds = 10 : (null)
            this.state.amountOutstanding = this.state.amountOutstandingTemp -  (this.state.amountOutstandingTds*Number(tds))/100
            if(this.state.amountOutstanding < 0)
            {
                this.refs.tds.value = this.state.tds
                UI.notify( 'error', CONSTANTS.UI_MESSAGES.outstandingTdsCalcError);
            }
            else {
                this.state.tds = tds;
                this.refs.tds.value = tds;
                this.setState(this.state);
                var invoices = this.props.invoices
                var targetInvoice = _.find(invoices,
                    (item) => item && item.data && (item.data.invoiceId === this.props.data.invoiceId));
                targetInvoice.data.tdsPercentage = tds;
                targetInvoice.data.tdsAmount = (this.state.amountOutstandingTds*Number(tds))/100;
                targetInvoice.data.amountOutstanding = targetInvoice.data.originalAmount - targetInvoice.data.tdsAmount;
                this.props.updateProp({ invoices: invoices });
            }
        }
    },

    render(data) {
        var data = this.props.data;
        return (
            <li className="rowCell tabelDataCell selectedPaymentList">
                <div className="cellInWrap">
                    <span className="dataCell forSpace">
                        <label htmlFor="tickBox" className="hide"></label>
                        <input className="hide" type="checkbox" id="tickBox" onClick={this.selectInvoice.bind(null, data)} defaultChecked="checked" />
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
                        {CONSTANTS.NOT_AVAILABLE}
                    </span>
                    <span className="dataCell poCell">
                        {CONSTANTS.NOT_AVAILABLE}
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
                        <span className="pinkText">{this.props.data.status || CONSTANTS.INVOICE_STATUS.OPEN}</span>
                    </span>
                    <span className="dataCell tdsPercentCell">
                    {
                        data.lowTaxFlag === "Y" ?
                            (<input type="text" readOnly value={data.tdsPercentage} data-field="tds" ref="tds" className='form-control' placeholder="TDS" />) :
                            (<input type="text" min="0" max="10" onChange={this.updateTds.bind(null,false)} data-field="tds" ref="tds" className='form-control' placeholder="TDS" />)
                    }
                    </span>
                    <span className="dataCell amtOutCell text-right">
                        {this.getFormattedAmt(this.get('currencyId'), this.state.amountOutstanding) || CONSTANTS.NOT_AVAILABLE}
                    </span>
                    <span className="dataCell text-right arrowCell hide">
                        <span onClick={this.toggle} className={this.getAccordianBtnClass()}>
                            <span></span>
                        </span>
                    </span>
                </div>
                <div className="clear"></div>
                <div className={this.getToggleClassSet()}>
                    <label htmlFor="creditNotesInfo" className="paymentLabels">Credit Notes</label>
                    <div className="ownedBy creditBox" ref="creditNotesInfo">
                        <div className="customSelectGroup pull-left">
                            <label htmlFor="associatedCustomer">Select Credit Notes</label>
                            <ul className="customSelectList dropDownData" id="associatedCustomer" ref="associatedCustomer">
                                {this.state.amountConfirmed ? (null) : this.showCreditNotes()}
                            </ul>
                            <p className="selectContentBox">{this.state.totalCreditAmt || 'Select Credit Notes'}</p>
                        </div>
                    </div>
                    <label htmlFor="onAccountPayment" className="paymentLabels">On Account Payment</label>
                    <div className="assignTo creditBox" ref="onAccountPayment">
                        <div className="customSelectGroup pull-left">
                            <label htmlFor="associatedCustomer">Select on Account payment</label>
                            <ul className="customSelectList dropDownData" id="associatedCustomer" ref="associatedCustomer">
                                {this.state.amountConfirmed ? (null) : this.showOnAccounts()}
                            </ul>
                            <p className="selectContentBox">{this.state.totalOutstandingAmt < 0 ? 0 : (this.state.totalOutstandingAmt || 'Select on Account payment')}</p>
                        </div>
                    </div>
                </div>
            </li>
        )
    },
});

export default InvoiceItemDetails;
