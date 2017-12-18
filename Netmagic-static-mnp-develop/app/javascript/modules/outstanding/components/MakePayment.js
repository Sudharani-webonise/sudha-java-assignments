import { Link } from 'react-router';
import CONSTANTS from '../../../constants/app-constant';
import Invoice from '../../../services/outstanding';
import Time from '../../../mixins/time';
import Loader from 'react-loader';
import LoaderOptions from '../../../mixins/loader';
import InvoiceListHeader from './InvoiceListHeader';
import InvoiceItemDetails from './InvoiceItemDetails';
import http from '../../../mixins/restutils';
import Session from '../../../mixins/sessionUtils';
import User from '../../../services/user';
import Utility from '../../../mixins/basicUtils';
import UI from '../../../mixins/ui';

var MakePayment = createReactClass({

    mixins: [Time, http, Session, Utility, User, Invoice],
    contextTypes: {
        router: React.PropTypes.object
    },

    getInitialState() {
        var selectedInvoices = Session.get('selectedInvoices');
        return {
            invoices: selectedInvoices,
            updatedCreditNotes: Session.get('creditNotes'),
            updatedOnAccounts: Session.get('onAccountPaymentList'),
            initialOutstanding: this.getTotalOutstanding(selectedInvoices) || 0,
        };
    },

    componentWillMount() {
        User.redirectIfUnauthorized.call(this,
            CONSTANTS.PERMISSION_CODES.outstanding.payment);
    },

    sortInvoiceList(fieldName, isDescending) {
        this.state.invoices.msgData.transaction.zzarout1VW.zzarout4VW =
            Utility.getSortedList(Utility.getVal(this.state,
                'invoices.msgData.transaction.zzarout1VW.zzarout4VW'),
                isDescending, fieldName);
        this.setState({ invoices: this.state.invoices });
    },

    selectInvoice(data, checked) {
        if (checked) {
            this.state.selectedInvoices.push(data);
        } else {
            this.state.selectedInvoices = _.reject(this.state.selectedInvoices,
                (item) => item.index === data.index);
        }
        this.setState(this.state);
    },

    updateProp(updatedObj) {
        this.setState(updatedObj);
    },

    proceedToPayment() {
        var totalCreditAmount = this.getTotalAmount('creditNoteList')
        var totalOnAccount = this.getTotalAmount('onAccountList')
        var totalTds = this.getTotalTds()
        var totalOutstanding = this.state.initialOutstanding
        var netPayable = totalOutstanding - totalTds;
        if (netPayable < 0) {
            UI.notify('error', CONSTANTS.UI_MESSAGES.outstandingCalcualtionError);
        }
        else {
            Session.set('finalInvoices', this.state.invoices);
            Session.set('paymentInfo', {
                totalCreditAmount: totalCreditAmount,
                totalOnAccount: totalOnAccount,
                totalTds: totalTds,
                totalOutstanding: totalOutstanding,
                invoices: this.state.invoices,
                netPayable: totalOutstanding - totalTds
            });
            this.context.router.push('outstanding/final-payment');
        }
    },

    getTotalAmount(fieldName) {
        return _.chain(this.state.invoices)
            .pluck('data').pluck(fieldName)
            .flatten().compact()
            .pluck('amountUsed')
            .reduce((res, item) => res + Number(item))
            .value() || 0;
    },

    getTotalTds() {
        var tdsObjects = this.state.invoices
        var tdsFinalAmount = 0;
        tdsObjects.forEach(function (obj) {
            tdsFinalAmount = tdsFinalAmount + Number(obj.data.balanceAmount) * Number(obj.data.tdsPercentage) / 100
        });
        return tdsFinalAmount
    },

    getUsedCreditOnAccountAmt() {
        var totalTds = this.getTotalTds()
        return (this.getTotalAmount('creditNoteList') +
            this.getTotalAmount('onAccountList') +
            totalTds) || 0;
    },

    getTotalOutstanding(invoices) {
        return _.chain(invoices || this.state.invoices)
            .pluck('data').compact()
            .map((item) => item.amountOutstanding || item.balanceAmount || 0)
            .reduce((res, item) => res + Number(item))
            .value() || 0;
    },

    updateInvoiceNote(invoice, invoiceIndex, note, noteIndex) {
        this.state.invoices[invoiceIndex] = invoice;
        this.state.updatedCreditNotes[noteIndex] = note;
        this.setState(this.state)
    },

    updateInvoiceAcc(invoice, invoiceIndex, note, noteIndex) {
        this.state.invoices[invoiceIndex] = invoice;
        this.state.updatedOnAccounts[noteIndex] = note;
        this.setState(this.state)
    },

    render() {
        let invoiceItems = _.pluck(this.state.invoices, 'data') || [];
        var totalAmount = this.getFormattedAmt(this.get('currencyId'), this.state.initialOutstanding)
        var totalSubtractAmount = this.getFormattedAmt(this.get('currencyId'), this.getUsedCreditOnAccountAmt())
        var toPay = this.getFormattedAmt(this.get('currencyId'), (this.state.initialOutstanding - this.getUsedCreditOnAccountAmt()));
        return (
            <section className="ticketListSection outStandingWrap">
                <div className="blackOverlay"></div>
                <div className="fullscreenOverlay"></div>
                <section className="midHeader">
                    <div className="container">
                        <div className="row">
                            <div className="col-md-5 col-sm-5 col-xs-12 headBarLeft ">
                                <ol className="breadcrumb breadCrumbCustom clearfix">
                                    <li><Link to={User.getSplashScreenURL() }>Back to Home</Link></li>
                                    <li><Link to="dashboard">Back to Dashboard</Link></li>
                                    <li><Link to="outstanding">Outstanding List</Link></li>
                                    <li className="ticketListBread active">Make Payment</li>
                                </ol>
                            </div>
                            <div className="clearfix"></div>
                            <div className="col-md-7 col-sm-7 col-xs-12 noRightSpace clearfix primaryConfirmationHeader outStandingHeader">
                                <ul>
                                    <li><span className="greenCircle">1</span><span>Make Payment</span></li>
                                    <li><span className="greyCircle">2</span><span>Proceed for final payment</span></li>
                                </ul>
                                <div className="pull-right confirmBtns clearfix">
                                    {
                                        toPay == 0 ? <input type="submit"   
                                            value="Confirm" id="submitForm" 
                                            className="darkPinkBtn"/> :  <input 
                                            type="submit" value="Confirm" 
                                            id="submitForm" 
                                            className="darkPinkBtn" onClick=
                                            {this.proceedToPayment} />
                                    }
                                   
                                    <Link to="outstanding" className="blueLink">Cancel</Link>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
                <article className="container paddingZero listStyle outstandingPaymentWrap">
                    <ul className="ticketListTable paymentList outstandingTableWrap">
                        <InvoiceListHeader sortInvoiceList={this.sortInvoiceList} showTds={true} />
                        <div className="invoiceListItems" ref="invoiceItems">
                            {
                                invoiceItems ? invoiceItems.map((data, key) => {
                                    return (<InvoiceItemDetails
                                        data={data}
                                        key={key}
                                        onAccounts={this.state.updatedOnAccounts}
                                        creditNotes={this.state.updatedCreditNotes}
                                        invoices={this.state.invoices}
                                        updateInvoiceNote={this.updateInvoiceNote}
                                        updateInvoiceAcc={this.updateInvoiceAcc}
                                        updateProp={this.updateProp} />)
                                }) : (<li className="rowCelltabelDataCell">
                                    {CONSTANTS.UI_MESSAGES.dataNotFound}
                                </li>)
                            }
                        </div>
                    </ul>
                    <div className="totalPaymentBox">
                        <span>Total Payment</span>
                        <span className="totalAmt">&#8377; {totalAmount}</span>
                        <span className="subtractAmt">&#8377; {totalSubtractAmount}</span>
                        <span className="netPayment">&#8377; {toPay}</span>
                    </div>
                </article>
            </section>
        );
    }
});

export default MakePayment;
