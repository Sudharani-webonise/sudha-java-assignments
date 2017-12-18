import { Link } from 'react-router';
import Session from '../../../mixins/sessionUtils';
import User from '../../../services/user';
import CONSTANTS from '../../../constants/app-constant';
import shoppingCart from '../../../services/shoppingCart';
import Loader from 'react-loader';
import LoaderOptions from '../../../mixins/loader';
import http from '../../../mixins/restutils';
import UI from '../../../mixins/ui';

var PaymentDone = createReactClass({

    getInitialState() {
        return {
            data: Session.get('ShoppingCartPricingDetails') || [],
            sofId: '',
            dontStop: true,
            loaded: false,
            refnum: '',
            amountPaid: '',
            transactionId: '',
            transactionTime: '',
            sofs: []
        }
    },

    contextTypes: {
        router: React.PropTypes.object
    },

    componentDidMount() {
        if (this.state.dontStop) {
            this.readGetVariables();
            this.interval = setInterval(()=> {
                this.getSofs();
            }, 15000);
            setTimeout(() => {
                this.state.loaded = true;
            }, 30000);
        }
    },

    componentWillUnmount() {
        clearTimeout(this.interval);
        /**
         * To clear ebs response url
         */
        window.location.search = '';
    },

    sofsCreation() {
        let url = http.buildRestUrl(CONSTANTS.API_URLS.shoppingCart.createSof, {
            merchantRefNumber: this.state.refnum
        })
        shoppingCart.createSofs(url)
            .then((res) => {
                //Do nothing since result is true or false
            })
            .catch((err) => {
                //Do nothing since result is true or false
            })
    },

    makeSuperAdmin() {
        let userDetails = Session.get('userDetails');
        let url = CONSTANTS.API_URLS.signup.superAdmin+'?merchantRefNumber='+this.state.refnum+'&emailId='+userDetails.emailId
        let requestBody = {
            mainCustomerId: userDetails && userDetails.kacid,
            associateCustomerId: userDetails && userDetails.kacid,
            projectId: User.getProject('projectId') || null,
            contactSugarrId: userDetails && userDetails.kcntid,
            password: userDetails.password
        };
        User.makeSuperAdmin(url, requestBody)
            .then((res) => {
                if(res) {
                    UI.notifySuccess(CONSTANTS.UI_MESSAGES.superAdminCreated, 60000);
                }
            })
            .catch((err) => {
                // Do nothing
            });
    },

    getSofs() {
        let url = http.buildRestUrl(CONSTANTS.API_URLS.shoppingCart.sofs, { transactionNumber: this.state.refnum });
        this.setState({ loaded: false });
        shoppingCart.fetchSofs(url)
            .then((res) => {
                this.state.sofs = res;
                if(this.state.sofs.length) {
                    this.state.loaded = true;
                }
                if(Session.get('userDetails')) {
                    this.makeSuperAdmin();
                }
                this.setState(this.state);
            })
            .catch((err) => {
                this.state.loaded = true;
                this.setState(this.state);
            });
    },

    readGetVariables() {
        var status = this.getUrlVars()["ResponseMessage"];
        let state = this.state;
        state.refnum = this.getUrlVars()["MerchantRefNo"];
        state.amountPaid = this.getUrlVars()["Amount"];
        state.transactionId = this.getUrlVars()["TransactionID"];
        state.transactionTime = this.decodeItems(this.getUrlVars()["DateCreated"]);
        if (status === "Transaction+Successful") {
             this.sofsCreation();
        }
        else {
            this.state.sofId = "Transaction was not complete";
        }
        this.setState(this.state)
    },

    decodeItems(time) {
        return decodeURIComponent(time).replace('+', ' ')
    },

    getUrlVars() {
        var vars = {};
        var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function (m, key, value) {
            vars[key] = value;
        });
        return vars;
    },

    getPrices() {
        var priceObj, totalOneTime = 0, totalMonthWise = 0;
        if (this.state.data != {}) {
            _.each(this.state.data, (pricingObj) => {
                priceObj = (_.first(pricingObj.billgroup))
                totalOneTime += priceObj.potc_tax
                totalMonthWise += priceObj.pmrc_tax
            });
        }
        return (totalOneTime + totalMonthWise)
    },

    showSofItems(sofItems) {
        let sofs = (<tr>
            <td>No Sof Items</td>
        </tr>);
        const NA = CONSTANTS.NOT_AVAILABLE;
        let userDetails = Session.get('userDetails');
        if(_.isArray(sofItems) && sofItems.length) {
            sofs = _.map(sofItems, (data, key) => {
                return (
                    <tr>
                        <td>{data.sofNo || NA}</td>
                        <td>{data.billToCustomer || User.getMainCustomer('customerName') || `${userDetails && userDetails.firstName} ${userDetails && userDetails.lastName}` ||NA}</td>
                        <td>{data.supportToCustomer || User.getAssociateCustomer('customerName') || `${userDetails && userDetails.firstName} ${userDetails && userDetails.lastName}` || NA}</td>
                        <td>{data.projectName || NA}</td>
                        <td>{data.contractPeriod || NA} months</td>
                        <td>{data.serviceType || NA}</td>
                    </tr>
                )
            })
        }
        return sofs;
    },

    goToProvision(){
        this.context.router.push('/provision');
        window.location.search = '';
    },

    render() {
        return (
            <div className="ticketListSection outStandingWrap peopleWrap">
                <div className="blackOverlay"></div>
                <section className="midHeader">
                    <div className="container">
                        <div className="row">
                            <div className="col-xs-4 col-sm-4 col-md-4">
                                <ol className="breadcrumb breadCrumbCustom clearfix">
                                    <li><Link to={User.getSplashScreenURL()}>Back to Home</Link></li>
                                    <li>
                                        <Link to="dashboard" className="blueLink">Back to Dashboard</Link>
                                    </li>
                                    <li className="active">Payment Successful</li>
                                </ol>
                                <h1 className="mainTitle">Payment Successful </h1>
                            </div>
                            <div className="col-xs-2 col-sm-2 col-md-2">
                            </div>
                        </div>
                    </div>
                </section>
                <Loader loaded={this.state.loaded} top="50%" left="50%" options={LoaderOptions}>
                    <div className="container paymentContainer">
                        <div className="row">
                            <div className="col-xs-10 col-xs-offset-1 pay_border pad20">
                                <div className="text-center"><p className="payment_done"></p></div>
                                <p className="pay_done">Payment Successful</p>
                                <div className="row pad_top20">
                                    <div className="col-xs-6 col-xs-offset-3">
                                        <div className="row">
                                            <div className="col-xs-6 head_style">Transaction ID:</div>
                                            <div className="col-xs-6 text-right info_style">{this.state.refnum}</div>
                                        </div>
                                        <div className="row">
                                            <div className="col-xs-6 head_style">Transaction Date & Time:</div>
                                            <div className="col-xs-6 text-right info_style">{this.state.transactionTime}</div>
                                        </div>
                                        <div className="row">
                                            <div className="col-xs-6 head_style">Amount Paid:</div>
                                            <div className="col-xs-6 text-right info_style"><i className="rupeeIcon">₹</i> {Number(this.state.amountPaid).toFixed(2)}</div>
                                        </div>
                                    </div>
                                </div>

                                <div className="row pad_15">
                                    <table className="table font13">
                                        <thead>
                                            <tr>
                                            <th className="tb_head">SOF No.</th>
                                            <th className="tb_head">Bill to Customer</th>
                                            <th className="tb_head">Support to Customer</th>
                                            <th className="tb_head">Project</th>
                                            <th className="tb_head">Period</th>
                                            <th className="tb_head">Type</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            {this.showSofItems(this.state.sofs)}
                                        </tbody>
                                    </table>
                                    <hr/>
                                </div>
                                <div className="row text-center">
                                    <p className="font14 pad_top20">
                                        {this.state.sofs.length ? <Link onClick={this.goToProvision} className="red_head">Click here to open Provisioning Cart</Link>
                                        : (null)} | Those product for which technical details have been completed will be delivered automatically.</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </Loader>
            </div>
        )
    }
});


export default PaymentDone;
