import { Link } from 'react-router';
import AddNewContactSC from './AddNewContactSC';
import AddContactAddress from './AddContactAddress';
import CONSTANTS from '../../../constants/app-constant';
import User from '../../../services/user';
import http from '../../../mixins/restutils';
import shoppingCart from '../../../services/shoppingCart';
import UI from '../../../mixins/ui';
import Loader from 'react-loader';
import LoaderOptions from '../../../mixins/loader';
import Session from '../../../mixins/sessionUtils';

var AddContactSC =  createReactClass({

    contextTypes: {
        router: React.PropTypes.object
    },

    getInitialState() {
        return {
            showAddNewContact: false,
            loaded: false,
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
            projects: []
        }
    },

    componentDidMount() {
        this.getContactInformation();
        this.getBillAddressInformation();
        this.getSuppAddressInformation();
        this.getProjects();
        this.getProjectName();
    },

    getProjects() {
        let userObj = User.getCustomerParams();
        var associated = Session.get('associatedCustomer');

        var params = {
            id: userObj.customerId,
            associateCustomerId: userObj.assoCustomerId,
        };
        shoppingCart.getProjectsFromAPI(params)
            .then((result) => {
            var projects =
                _.map(result, (project)=> {
                    return { label: project.projectName, value: project.projectName, id : project.projectId, sugarId : project.projectSugarId};
                });
            this.state.projects = projects;
            this.setState(this.state)
        })
        .catch((err) => {
            UI.notifyError(err && err.statusText);
        });
    },

    getProjectName() {
        var projectData = Session.get('globalData');
        this.state.projectName = projectData.project;
        this.setState(this.state);
    },

    showHideAddNewContact() {
        this.state.showAddNewContact = !this.state.showAddNewContact;
        this.setState(this.state)
    },

    getContactInformation() {
        this.state.loaded = false;
        this.setState(this.state)
        var userData = User.getCustomerParams()
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

    updateBillAddressInformation(address) {
        _.isEqual(address,this.state.selectedBillAddress) ? this.state.selectedBillAddress = {} : this.state.selectedBillAddress = address;
        this.setState(this.state)
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

    updateSuppAddressInformation(address) {
        _.isEqual(address,this.state.selectedSuppAddress) ? this.state.selectedSuppAddress = {} : this.state.selectedSuppAddress = address;
        this.setState(this.state)
    },

    updateContactInformation(contact,code) {
        var selectedCodes = this.state.selectedCodes;
        var selectedContact = _.findWhere(this.state.contactList, contact) || {} ;
        if(selectedContact[code.value]){
            this.state.contactList = _.map(this.state.contactList, (contact, key) => {
                if(_.isEqual(contact, selectedContact))
                {
                    contact[code.value] = false;
                    selectedCodes[code.value] = false;
                }
                return contact;
            });
        }
        else{
            this.state.contactList =  _.map(this.state.contactList, (contact, key) => {
                if(_.isEqual(contact, selectedContact))
                {
                    contact[code.value] = true;
                    selectedCodes[code.value] = contact.contactid;
                }
                else{
                    contact[code.value] = false;
                }
                return contact
            });
        }
        this.state.selectedCodes = selectedCodes;
        this.setState(this.state);
    },

    goToMakePayment() {
       var selectedCodes = this.state.selectedCodes;
       if(selectedCodes.billing && selectedCodes.operations && selectedCodes.spoc && selectedCodes.signatory) {
           var pricingObject = Session.get('ShoppingCartPricingDetails');
           var billAccBanId = this.state.selectedBillAddress.acc_ban_id;
           var suppAccBanId = this.state.selectedSuppAddress.acc_ban_id;
           if(billAccBanId && suppAccBanId) {
                var temp = _.first(_.first(pricingObject).billgroup)
                temp.auth_sign = selectedCodes.signatory || ''
                temp.bill_cont = selectedCodes.billing || ''
                temp.billtoadd = billAccBanId
                temp.spoc_cont = selectedCodes.spoc || ''
                temp.supptoadd = suppAccBanId
                temp.tech_cont = selectedCodes.operations || ''
                pricingObject[0].billgroup[0] = temp;
                Session.set('ShoppingCartPricingDetails', pricingObject)
                this.context.router.push('/shopping-cart/payment');
           }
           else {
                UI.notifyError(CONSTANTS.UI_MESSAGES.shoppingCart.contactSelectionError)
           }
       }
       else {
           UI.notifyError(CONSTANTS.UI_MESSAGES.shoppingCart.addressSelectionError)
       }
    },

    updateSelectedProject(value) {
        this.state.selectedProject = _.findWhere(this.state.projects,{value: value})
        this.setState(this.state)
    },

    updateSelectedContactType(value) {
        this.state.selectedContactType = value
        this.setState(this.state)
    },

    saveAddContact(obj) {
        console.log(obj)
    },

    render(){
        return(
            <div className="addContactContainer">
                <Loader loaded={this.state.loaded} top="50%" left="50%" options={LoaderOptions}>
                </Loader>
                <div className="contactHeader">
                    <div className="container">
                        <div className="breadCrumbBox pull-left">
                            <ul>
                                <li><Link to={User.getSplashScreenURL()}>Back to Home</Link></li>
                                <li><Link to="/shopping-cart">My Cart</Link></li>
                                <li className="active"><span>Order Details</span></li>
                            </ul>
                            <h3>Order Details</h3>
                            <Link to="javascript:void(0)">Click for detail view</Link>
                        </div>
                        <div className="headerBtns pull-right">
                            <span onClick={this.goToMakePayment} className="payNowBtn">Pay Now</span>

                        </div>
                        <div className="clear"></div>
                    </div>
                </div>
                <div className="addContactDetails">
                    <div className="container">
                        <div className="projectDetails">
                            <span className="projectName">{this.state.projectName}</span>
                            <span className="projectType hide">Project Type</span>
                            <p>Please provide us below details before you place your order</p>
                        </div>
                        <AddNewContactSC
                            showAddNewContact = {this.state.showAddNewContact}
                            showHideAddNewContact = {this.showHideAddNewContact}
                            saveAddContact = {this.saveAddContact}
                            contactList = {this.state.contactList}
                            updateContactInformation = {this.updateContactInformation}
                            projects = {this.state.projects}
                            selectedProject = {this.state.selectedProject}
                            updateSelectedProject = {this.updateSelectedProject}
                            selectedContactType = {this.state.selectedContactType}
                            updateSelectedContactType = {this.updateSelectedContactType}
                        />
                        <AddContactAddress
                            billAddressList = {this.state.billAddressList}
                            suppAddressList = {this.state.billAddressList}
                            selectedBillAddress = {this.state.selectedBillAddress}
                            selectedSuppAddress = {this.state.selectedSuppAddress}
                            updateBillAddressInformation = {this.updateBillAddressInformation}
                            updateSuppAddressInformation = {this.updateSuppAddressInformation}
                        />
                    </div>
                </div>
            </div>
        )}
});

export default AddContactSC;
