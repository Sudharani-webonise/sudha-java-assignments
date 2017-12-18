import { Link } from 'react-router';
import CONSTANTS from '../../../constants/app-constant';
import Session from '../../../mixins/sessionUtils';
import Utility from '../../../mixins/basicUtils';
import UI from '../../../mixins/ui';
import { Modal, ModalHeader, ModalTitle, ModalClose, ModalBody, ModalFooter} from 'react-modal-bootstrap';
import shoppingCart from '../../../services/shoppingCart';
import http from '../../../mixins/restutils';
import User from '../../../services/user';
import Loader from 'react-loader';
import LoaderOptions from '../../../mixins/loader';
import GroupedItems from './GroupedItems';

var SofDetails = createReactClass({
    mixins: [Utility, UI],
    contextTypes: {
        router: React.PropTypes.object
    },

    getInitialState() {
        return {
            billGroups: [],
            loaded: false,
            showAddNewContact: false,
            projectName: '',
            contactList: [],
            billAddressList: [],
            suppAddressList: [],
            selectedCodes: {
                billing: false,
                operations: false,
                spoc: false,
                signatory: false,
            },
            selectedSuppAddress: {},
            selectedBillAddress: {},
            projects: [],
            errorMessage: null
        }
    },

    componentDidMount() {
        this.getLocations();
        this.getInfraProducts();
        if(!User.getUserInfo('existingUser')) {
            let userDetails = Session.get('userDetails');
            this.state.contactList = [{
                contactid: userDetails.kcntid,
                emailId: userDetails.emailId,
                first_name: userDetails.firstName,
                last_name: userDetails.lastName,
                mobileCountryCode: userDetails.countryCode,
                mobilephone: userDetails.mobile
            }];
            let address = {
                acc_ban_id: userDetails.kaddress,
                address: userDetails.address,
                address_type: '',
                name: userDetails.companyName
            };
            this.state.billAddressList = [address];
            this.state.suppAddressList = [address];
            this.setState(this.state);
        } else {
            this.getContactInformation();
            this.getBillAddressInformation();
            this.getSuppAddressInformation();
        }
    },

    getContactInformation() {
        this.state.loaded = false;
        this.setState(this.state)
        var userData = User.getCustomerParams();
        var url = http.buildRestUrl(CONSTANTS.API_URLS.shoppingCart.contactsSC, { customerId: userData.customerSugarId });
        shoppingCart.getData(url)
            .then((result) => {
                this.state.loaded = true;
                if (result.status === 'Failure') {
                    this.state.contactList = [];
                }
                else if (result.status === 'Success') {
                    var data = _.map(result.data, function(contact){
                        contact.billing = false;
                        contact.operations = false;
                        contact.spoc = false;
                        contact.signatory = false;
                        return contact;
                    });
                    this.state.contactList = data
                }
                this.setState(this.state)
            })
            .catch((err) => {
                UI.notifyError(err && err.statusText);
            });
    },

     getBillAddressInformation() {
        this.state.loaded = false;
        this.setState(this.state)
        var userData = User.getCustomerParams()
        var params = {
            acc : userData.customerSugarId,
            addressType : CONSTANTS.SHOPPING_CART_VARIABLES.BillingAddressType,
        }
        var url = CONSTANTS.API_URLS.shoppingCart.addressSC;
        shoppingCart.getAddresses(url,params)
            .then((result) => {
                this.state.loaded = true;
                this.setState(this.state)
                if (result.status === CONSTANTS.SHOPPING_CART_VARIABLES.Fail) {
                    this.state.billAddressList = [];
                }
                else if (result.status === CONSTANTS.SHOPPING_CART_VARIABLES.Success) {
                    this.state.billAddressList = result.data
                }
                this.setState(this.state)
            })
            .catch((err) => {
                UI.notifyError(err && err.statusText);
            });
    },

    getSuppAddressInformation() {
        this.state.loaded = false;
        this.setState(this.state)
        var userData = User.getCustomerParams()
        var params = {
            acc : userData.assoCustomerSugarId,
            addressType : CONSTANTS.SHOPPING_CART_VARIABLES.ShippingAddressType,
        }
        var url = CONSTANTS.API_URLS.shoppingCart.addressSC;
        shoppingCart.getAddresses(url,params)
            .then((result) => {
                this.state.loaded = true;
                this.setState(this.state)
                if (result.status === CONSTANTS.SHOPPING_CART_VARIABLES.Fail) {
                    this.state.suppAddressList = [];
                }
                else if (result.status === CONSTANTS.SHOPPING_CART_VARIABLES.Success) {
                    this.state.suppAddressList = result.data
                }
                this.setState(this.state)
            })
            .catch((err) => {
                UI.notifyError(err && err.statusText);
            });
    },

    showHideAddNewContact() {
        this.state.showAddNewContact = !this.state.showAddNewContact;
        this.setState(this.state)
    },

    getInfraProducts() {
        let userDetails = Session.get('userDetails');
        var userObj = User.getCustomerParams();
        let url = http.buildRestUrl(CONSTANTS.API_URLS.shoppingCart.getCart, { mainCustomerId: 
                userObj.customerSugarId
             || userDetails.kacid, associateCustomerId: userObj.assoCustomerSugarId || userDetails.kacid });
        url = `${url}?emailId=${User.getUserInfo('loginUserEmail')}`;
        this.state.loaded = false;
        shoppingCart.getProductList(url)
            .then((res) => {
                this.state.requestData = res;
                this.state.billGroups = res && res.billgroup || [];
                this.state.loaded = true;
                this.setState(this.state);
            })
            .catch((err) => {
                UI.notifyError( err && err.statusText);
                this.state.loaded = true;
                this.setState(this.state);
            });
    },

    getLocations() {
        shoppingCart.getData(CONSTANTS.API_URLS.shoppingCart.locations)
            .then((result) => {
                this.setState({ defaultLocations: result.items || [] });
            })
            .catch((error) => {
                UI.notifyError(error && error.statusText)
            });
    },

    showGroupItems() {
        let billGroupTemplate = (<li>
            <div className='projectNameWrap clearfix selected'>
                <div className="projectNameDuration">
                    <h4>{CONSTANTS.UI_MESSAGES.shoppingCart.emptyInfraItems}</h4>
                </div>
            </div></li>);
        let billGroupItems = this.state.billGroups;
        if(_.isArray(billGroupItems) && billGroupItems.length) {
            billGroupTemplate = _.map(billGroupItems, (data, key) => {
            return <GroupedItems
                billGroup={data}
                billAddressList = {this.state.billAddressList}
                suppAddressList = {this.state.billAddressList}
                selectedBillAddress = {this.state.selectedBillAddress}
                selectedSuppAddress = {this.state.selectedSuppAddress}
                showAddNewContact = {this.state.showAddNewContact}
                projects = {this.state.projects}
                selectedProject = {this.state.selectedProject}
                selectedContactType = {this.state.selectedContactType}
                contactList = {this.state.contactList}/>
            });
        }
        return billGroupTemplate;
    },

    calculateTotalPrice(type) {
        return _.reduce(this.state.billGroups, (memo, i) =>  memo + i[type], 0);
    },

    payNow() {
        let billGroups = this.state.billGroups;
        let requestData = this.state.requestData;
        let checkForPayment = _.every(billGroups, (billGroup) => {
            this.state.errorMessage = null;
            if (billGroup.auth_sign && billGroup.bill_cont && billGroup.spoc_cont && billGroup.tech_cont) {
                if(billGroup.billtoadd && billGroup.supptoadd) {
                    return true;
                } else {
                    this.state.errorMessage = `Please select Bill To Address and Support to Address for the Project: ${billGroup.projectname}.`;
                    return false;
                }
            } else {
                this.state.errorMessage = `Please select all mandatory contact types for the Project: ${billGroup.projectname}.`
                return false;
            }
            this.setState(this.state);
        });

        if(checkForPayment) {
            Session.set('shoppingCartData', requestData);
            this.saveCMSProduct(requestData);
            this.context.router.push('/make-payment');
        } else {
            UI.notifyError(this.state.errorMessage);
            return;
        }
    },

    saveCMSProduct(data) {
        let url;
        if(User.getUserInfo('existingUser')) {
            url = CONSTANTS.API_URLS.shoppingCart.saveCart + '?mainCustomerId='+User.getMainCustomer('customerId');
        } else {
            url = CONSTANTS.API_URLS.shoppingCart.saveCart + '?mainCustomerId=';
        }
        url = http.appendUrl(url, { associateCustomerId: User.getAssociateCustomer('customerId'), emailId: User.getUserInfo('loginUserEmail')});
        let reqObj = {
            data: data
        };
        shoppingCart.saveCart(url, reqObj)
            .then((res) => {
                this.state.loaded = true;
            })
            .catch((err) => {
                if(err == 'OK') {
                    this.state.loaded = true;
                    UI.notifySuccess(CONSTANTS.UI_MESSAGES.shoppingCart.addInfraSuccess);
                }
            });
    },

    goToMyCart() {
        let requestData = this.state.requestData;
        this.saveCMSProduct(requestData);
        this.context.router.push('/product-list');        
    },

    checkIfAmountIsZero() {
        return ((Number(this.calculateTotalPrice('potc')) + Number(this.calculateTotalPrice('pmrc'))) == CONSTANTS.ZERO) ? true : false
    },

    render() {
        var mainCustomer = Session.get('mainCustomer');
        var quckLinkClass = _.isEmpty(mainCustomer) ? 'hide' : '';
        var checkIfAmount = this.checkIfAmountIsZero();
        return (
            <div className="peopleWrap myCartPageWrap">
                <div className="blackOverlay"></div>
                <section className="midHeader">
                    <div className="container">
                        <div className="row">
                            <div className="col-xs-9">
                                <ol className={"breadcrumb breadCrumbCustom clearfix " + quckLinkClass}>
                                    <li><Link to={User.getSplashScreenURL() }>Back to Home</Link></li>
                                    <li>
                                        <Link to="dashboard" className={"blueLink"}>Back to Dashboard</Link>
                                    </li>
                                    <li className="active">My Cart</li>
                                </ol>
                                <h1 className="mainTitle pull-left">My Cart </h1>
                                <ul className="myCartPaymentProcess clearfix pull-right">
                                    <li className="processCompleted"><i>1</i> Add Infra</li>
                                    <li className="currentCartStep"><i>2</i> SOF Details</li>
                                    <li><i>3</i> Proceed for final payment</li>
                                </ul>
                            </div>
                             <div className="col-xs-3 clearfix actionBtnsWrap text-right">
                                <span className="darkPinkBtn" onClick={this.goToMyCart}>Back</span>
                                {
                                    checkIfAmount ?
                                    <span className="darkPinkBtn firstBtn" >Pay Now</span> : 
                                    <span className="darkPinkBtn firstBtn" onClick={this.payNow}>Pay Now</span>
                                }
                            </div>
                        </div>
                    </div>
                </section>
                <div className="container">
                    <div className="row">
                        <Loader loaded={this.state.loaded} top="50%" left="50%" options={LoaderOptions}>
                            <div className="col-md-9 myCartProjectListing">
                                <h6>Project Name</h6>
                                <ul>
                                    {this.showGroupItems()}
                                </ul>
                            </div>
                            <div className="col-md-3">
                                <div className="cartTotalPriceWrap">
                                    <h3>Total Price</h3>
                                    <ul>
                                        <li>
                                        <span className="totalCharges">&#8377;{Number(this.calculateTotalPrice('potc')).toFixed(2)}</span>
                                        <span className="chargesInfo">Total One Time Charges</span>
                                        </li>
                                        <li>
                                        <span className="totalCharges">&#8377; {Number(this.calculateTotalPrice('pmrc')).toFixed(2)}</span>
                                        <span className="chargesInfo">Total Charges</span>
                                        </li>
                                        <li className="excessChargInfo">
                                        <p className="grayText"><span className="mandatoryStar">*</span> The excess billing charges will be charge based on actual utilization.</p>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </Loader>
                    </div>
                </div>
            </div>
        );
    }
});

export default SofDetails;
