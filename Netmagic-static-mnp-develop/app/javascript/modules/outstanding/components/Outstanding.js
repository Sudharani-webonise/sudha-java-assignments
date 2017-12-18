import { Link } from 'react-router';
import CONSTANTS from '../../../constants/app-constant';
import Invoice from '../../../services/outstanding';
import Time from '../../../mixins/time';
import Loader from 'react-loader';
import LoaderOptions from '../../../mixins/loader';
import InvoiceListHeader from './InvoiceListHeader';
import InvoiceItem from './InvoiceItem';
import TDSListItem from './TDSListItem';
import http from '../../../mixins/restutils';
import Session from '../../../mixins/sessionUtils';
import User from '../../../services/user';
import Utility from '../../../mixins/basicUtils';
import UI from '../../../mixins/ui';

var Outstanding = createReactClass({

    mixins: [Time, http, Session, Utility, User, Invoice],
    contextTypes: {
        router: React.PropTypes.object
    },

    getInitialState() {
        return {
            loaded: false,
            pageNumber: 1,
            invoices: [],
            selectedInvoices: [],
            businessUnits: [],
            currentBusinessUnit: ''
        };
    },

    componentWillMount() {
        User.redirectIfUnauthorized.call(this,
            CONSTANTS.PERMISSION_CODES.outstanding.listing);
    },

    componentDidMount() {
        this.getInvoiceList();
        this.getBusinessUnits()
            .then((result) => {
                let businessUnits = this.getVal(result, 'data.transaction');
                Session.set('outstandingType', Utility.getVal(businessUnits,'[0].businessUnit.businessUnitName'));
                this.setState({ businessUnits: businessUnits || [] })
            })
            .catch((error) => UI.notifyError(error.statusText));
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

    fetchInvoiceList(url, businessUnit) {
        const uiMessages = CONSTANTS.UI_MESSAGES;
        this.setState({ loaded: false });
        this.getData(url, businessUnit)
            .then((result) => this.setState({ invoices: result, loaded: true }))
            .catch((error) => {
                this.setState({ loaded: true });
                if (error && error.statusText === uiMessages.unauthorized) {
                    UI.notifyError(uiMessages.unauthorized);
                } else {
                    UI.notifyError(error)
                }
            });
    },

    getInvoiceList() {
        var defaultBusinessUnit = this.getVal(_.first(CONSTANTS.BUSINESS_UNITS),
            'name');
        this.fetchInvoiceList(CONSTANTS.API_URLS.outstanding.invoices,
            defaultBusinessUnit);
        this.setState({ currentBusinessUnit: defaultBusinessUnit });
    },

    getFilteredInvoices(event) {
        Session.set('outstandingType',event.target.dataset.id)
        this.fetchInvoiceList(CONSTANTS.API_URLS.outstanding.invoices,
            event.target.dataset.id);
        this.setState({ currentBusinessUnit: event.target.dataset.id});
    },

    sortInvoiceList(fieldName, isDescending) {
        this.state.invoices.msgData.transaction.zzarout1VW.zzarout4VW =
            Utility.getSortedList(Utility.getVal(this.state,
                'invoices.msgData.transaction.zzarout1VW.zzarout4VW'),
                isDescending, fieldName);
        this.setState({ invoices: this.state.invoices });
    },

    saveSelectedInvoices() {
        var selectedInvoices = _.map(this.state.selectedInvoices, (item) => {
            _.extend(item.data, { creditNoteList: [], onAccountList: [] });
            return item;
        });
        Session.set('selectedInvoices', selectedInvoices);
        this.context.router.push('/outstanding/make-payment');
    },

    showBusinessUnits() {
        return _.map(this.state.businessUnits, (data, key) => {
            var data = this.getVal(data, 'businessUnit.businessUnitName');
            return (<li data-id={data}  data-param-name="businessUnit"
                onClick={this.getFilteredInvoices} key={key}>{data}</li>)
        });
    },

    showInvoiceList(invoices) {
        var invoiceList =(<li className="rowCelltabelDataCell">
            {CONSTANTS.UI_MESSAGES.dataNotFound}
        </li>);
        if(_.isArray(invoices) && invoices.length)
            invoiceList = _.map(invoices, (data, key) => {
                return (<InvoiceItem selectInvoice={this.selectInvoice} data={data} key={key}
                index={key}/>)
        });
        return invoiceList;
    },

    onAccountPayment() {
        this.context.router.push('/outstanding/payment-on-account');
    },

    render() {
        var items = this.getVal(this,
            'state.invoices.outstandingListData.transaction.outstandingListInfo');
        var {invoiceList, creditNoteTotal, onAccountPaymentTotal,
            openInvoicesAmount, tdsList, defaultTdsValue} = (items || {});
        Session.set('creditNotes', this.getVal(this,
            'state.invoices.outstandingListData.transaction.outstandingListInfo.creditNoteList'));
        Session.set('onAccountPaymentList', this.getVal(this,
            'state.invoices.outstandingListData.transaction.outstandingListInfo.onAccountPaymentList'));
        Session.set('defaultTdsValue', defaultTdsValue);
        return (
            <section className="ticketListSection outStandingWrap">
                <div className="blackOverlay"></div>
                <div className="fullscreenOverlay"></div>
                <section className="midHeader">
                    <div className="container">
                        <div className="row">
                            <div className="col-md-5 col-sm-5 col-xs-12 headBarLeft">
                                <div className="pull-left">
                                    <ol className="breadcrumb breadCrumbCustom clearfix">
                                        <li><Link to={User.getSplashScreenURL()}>Back to Home</Link></li>
                                        <li><Link to="dashboard">Back to Dashboard</Link></li>
                                        <li className="ticketListBread active">Invoice Listing</li>
                                    </ol>
                                </div>
                                <div className="pull-right customAccountType">
                                    <span>Choose account type to pay</span>
                                    <div className="customSelectGroup pull-left">
                                        <ul className="customSelectList accountTypeList" id="countryCode">
                                            { this.showBusinessUnits() }
                                        </ul>
                                        <p className="selectContent">{this.state.currentBusinessUnit}</p>
                                    </div>
                                </div>
                            </div>
                            <div className="col-md-7 col-sm-7 col-xs-12 noRightSpace outstandingHeaderWrap">
                                <div className="col1 pull-left text-right">
                                    <span className="CurrencyNote">All Currencies in &#8377; </span>
                                    <div className="pull-left text-right openInvo">
                                        <label className="colLabel">Open Invoices</label>
                                        <div className="clearfix">
                                            <span className="amountCnt pull-right">{this.getFormattedAmt(this.get('currencyId'), parseFloat(openInvoicesAmount)) }</span>
                                        </div>
                                    </div>
                                </div>
                                <div className="col2 pull-left text-right">
                                    <div className="pull-left text-right">
                                        <label className="colLabel unadjustLabel">Unadjusted credit notes</label>
                                        <span className="labelAmount">{this.getFormattedAmt(this.get('currencyId'), parseFloat(creditNoteTotal)) }</span>
                                        <label className="colLabel unadjustLabel">Unadjusted on Account Payment</label>
                                        <span className="labelAmount">{this.getFormattedAmt(this.get('currencyId'), parseFloat(onAccountPaymentTotal)) }</span>
                                    </div>
                                </div>
                                <div className="col1 pull-left text-right makePaymentBtnWrap paymentProcessBtns">
                                    <div className="pull-left text-right ">
                                        {User.canAccess(CONSTANTS.PERMISSION_CODES.outstanding.payment) ?
                                            this.state.selectedInvoices.length ?
                                            (<div>
                                                <span value="Payment For Selected Invoice"   className="pinkBordered                                                            wid250"
                                                onClick=    {this.saveSelectedInvoices}>Payment For Selected Invoice</span></div>)
                                         :
                                            (<div><input type="submit" value="Payment For Selected Invoice" id="submitForm"
                                                className="pinkBordered                                                            wid250"
                                                onClick=    {this.saveSelectedInvoices} disabled/></div>)
                                                : (null)
                                        }
                                        {
                                            User.canAccess(CONSTANTS.PERMISSION_CODES.outstanding.payment) ? <input type="submit" value="On Account Payment" id="submitForm"
                                        className="pinkBordered wid250"
                                        onClick={this.onAccountPayment}/> : (null) 
                                        }
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
                <article className="container paddingZero listStyle">
                    <ul className="ticketListTable outstandingTableWrap">
                        <InvoiceListHeader super={this} sortInvoiceList={this.sortInvoiceList}/>
                        <div className="invoiceListItems clearfix">
                            <Loader loaded={this.state.loaded} top="50%" left="50%" options={LoaderOptions}>
                                { this.showInvoiceList(invoiceList) }
                            </Loader>
                        </div>
                    </ul>
                </article>
            </section>
        );
    }
});

export default Outstanding;
