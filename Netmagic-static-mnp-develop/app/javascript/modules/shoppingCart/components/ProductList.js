import { Link } from 'react-router';
import CONSTANTS from '../../../constants/app-constant';
import Session from '../../../mixins/sessionUtils';
import Utility from '../../../mixins/basicUtils';
import UI from '../../../mixins/ui';
import { Modal, ModalHeader, ModalTitle, ModalClose, ModalBody, ModalFooter} from 'react-modal-bootstrap';
import shoppingCart from '../../../services/shoppingCart';
import http from '../../../mixins/restutils';
import User from '../../../services/user';
import BillGroup from './BillGroup';
import Loader from 'react-loader';
import LoaderOptions from '../../../mixins/loader';
import moment from 'moment';
import UpdateCloudItem from './UpdateCloudItem';

var ProductList = createReactClass({

    mixins: [Utility, UI],
    contextTypes: {
        router: React.PropTypes.object
    },

    getInitialState() {
        return {
            billGroups: [],
            addInfraEnabled: false,
            editProduct: {
                pricingObj: {}
            },
            savePointData: {},
            requestData: {},
            loaded: false,
            updatedConfigItem: false,
            defaultLocations: [],
            billGroupItems: [],
            errorMessage: null,
            isOpen: false
        }
    },

    componentWillMount() {
        let user = Session.get('user');
        this.fetchUserDetails();
        if(!_.isEmpty(Session.get('corporateData'))) {
            this.getProductItem();
        } else {
            this.getInfraProducts();
        }
    },

    componentDidMount() {
        this.getLocations();
        Session.remove('CloudParams');
    },

    fetchUserDetails() {
        let userEmail = User.getUserInfo('loginUserEmail');
        let url = `${CONSTANTS.API_URLS.signup.newUserDetails}?userEmail=${userEmail}`;
        User.fetchNewUserDetails(url)
            .then((res) => {
                Session.set('userDetails', res);
            })
            .catch((err) => {
                UI.notifyError('Error in fetching User details');
            });

    },

    getProductItem() {
        let corporateData = Session.get('corporateData');
        let url = http.buildRestUrl(CONSTANTS.API_URLS.shoppingCart.getProduct,
                { productId: corporateData && corporateData.pid });
        this.state.loaded = false;
        shoppingCart.getCorporateItem(url)
            .then((res) => {
                res = Utility.getVal(res, 'items.[0].sugar.[0]');
                this.state.editProduct = res;
                this.getIndividualPricingDetails();
            })
            .catch((err) => {
                //Error handling
            })
    },

    getInfraProducts() {
        let userDetails = Session.get('userDetails');
        let userObj = User.getCustomerParams();
        let url = http.buildRestUrl(CONSTANTS.API_URLS.shoppingCart.getCart, { mainCustomerId: userObj.customerSugarId || userDetails && userDetails.kacid, associateCustomerId: userObj.assoCustomerSugarId || userDetails && userDetails.kacid });
        url = `${url}?emailId=${User.getUserInfo('loginUserEmail')}`;
        this.state.loaded = false;
        shoppingCart.getProductList(url)
            .then((res) => {
                this.state.requestData = res;
                this.state.billGroups = res && res.billgroup || [];
                this.state.loaded = true;
                this.state.addInfraEnabled = false;
                this.setState(this.state);
            })
            .catch((err) => {
                UI.notifyError( err && err.responseJSON.message);
                this.state.loaded = true;
                this.setState(this.state);
            });
    },

    getLocations() {
        shoppingCart.getData(CONSTANTS.API_URLS.shoppingCart.locations)
            .then((result) => {
                this.setState({ defaultLocations: result.items || [] })
            })
            .catch((error) => {
                UI.notifyError(error && error.statusText)
            });
    },

    findLocationFromLocId(locationId) {
        let location = _.find(this.state.defaultLocations, (item) => item.loc == locationId);
        return location && location.locname;
    },

    showBillGroups() {
        let billGroupTemplate = (<li>
            <div className='projectNameWrap clearfix selected'>
                <div className="projectNameDuration">
                    <h4>{CONSTANTS.UI_MESSAGES.shoppingCart.emptyInfraItems}</h4>
                </div>
            </div></li>);
        let billGroupItems = this.state.billGroups;
        if(_.isArray(billGroupItems) && billGroupItems.length) {
            billGroupTemplate = _.map(billGroupItems, (data, key) => {
            return <BillGroup
                billGroup={data}
                key={key}
                showConfigurationsByTypeForListInfra ={this.showConfigurationsByTypeForListInfra}
                showConfigurationsByType={this.showConfigurationsByType}
                removeInfraFromList={this.removeInfraFromList}
                addInfraEnabled={this.state.addInfraEnabled}
                enableEditForProduct={this.enableEditForProduct}
                getIndividualPricingDetails={this.getIndividualPricingDetails}
                updateCart={this.updateCart}
                cancelUpdate={this.cancelUpdate}
                updateQuantity={this.updateQuantity}
                updatedConfigItem={this.state.updatedConfigItem}
                findLocationFromLocId={this.findLocationFromLocId}/>
            });
        }
        return billGroupTemplate;
    },

    showConfigurationsByTypeForListInfra(type, subProduct) {
        var configView = (null);
        const types = CONSTANTS.PRODUCT_TYPES;
        switch (type) {
            case types.bundled:
                configView = this.showBundledConfigForListInfra(subProduct);
                break;
            case types.withAttributes:
                configView = this.showWithAttributesConfigForListInfra(subProduct)
                break;
            case types.standard:
                configView = (null);
                break;
        }
        return configView;
    },

    showWithAttributesConfigForListInfra(subProduct) {
        var attributes = _.first(_.values(subProduct));
        return (
            <div className="clearfix">
                {_.map(attributes && attributes.atts, (data, outerIndex) => {
                    return (
                        <li key={outerIndex}>
                            <div className="planType">
                                <label>{data.attname}</label>
                                <span>{_.findWhere(data.attvals, { isdef: 'Yes' })
                                    && _.findWhere(data.attvals, { isdef: 'Yes' }).attval}
                                    {data.spuom}
                                </span>
                            </div>
                        </li>
                    )
                }) }
            </div>
        );
    },

    showBundledConfigForListInfra(subProduct) {
        return (<div className="clearfix">
            {_.map(subProduct, (data, outerIndex) => {
                //if config attribute is a nested attribute
                if (data.atts) {
                    var attribute = _.first(_.values(data.atts));
                    var defaultValueId = attribute.defid;
                    var chosenOne = attribute.attvals[defaultValueId];
                    //data.spdesc --> label : chosenOne --> value
                    return (
                        <li key={outerIndex}>
                            <div className="planType">
                                <label>{data.spdesc}</label>
                                <span>
                                    {chosenOne && chosenOne.attval} &nbsp;
                                    {
                                        (chosenOne.appqty && (chosenOne.appqty.length > 1)) ?
                                            (chosenOne.selqty)
                                            : (null)
                                    }
                                </span>
                            </div>
                        </li>
                    )
                } else {
                    return (
                        <li>
                            <div className="planType">
                                <label>{data.spdesc}</label>
                                <span>
                                    {data.spqty}
                                </span>
                            </div>
                        </li>
                    )
                }
            }) }
        </div>)
    },

    calculateTotalPrice(type) {
        return _.reduce(this.state.billGroups, (memo, i) =>  memo + i[type], 0);
    },

    removeInfraFromList(product, billGroup) {
        let userDetails = Session.get('userDetails');
        let userObj = User.getCustomerParams();
        var params = {
            lineItemToken: product.lineItemToken,
            productId: product.pid,
            mainCustomerId: userObj.customerSugarId || userDetails && userDetails.kacid,
            associateCustomerId: userObj.assoCustomerSugarId || userDetails && userDetails.kacid,
            projectName: billGroup.projectname,
            contractPeriod: billGroup.cntrperiod,
            location: billGroup.loc,
            userEmailId: User.getUserInfo('loginUserEmail')
        };
        shoppingCart.deleteProductItem(params)
            .then((res) => {
                if(res) {
                    UI.notifySuccess(CONSTANTS.UI_MESSAGES.shoppingCart.removeInfraSuccess);
                    this.getInfraProducts();
                    this.props.setParentLoadState(true)
                }
            })
            .catch((err) => {
                 UI.notifyError(CONSTANTS.UI_MESSAGES.shoppingCart.removeInfraError)
            });
    },


    enableEditForProduct(product, billgroup) {
        if(this.state.addInfraEnabled) {
            UI.notifyError( CONSTANTS.UI_MESSAGES.shoppingCart.openCartError)
        } else {
            if (product.lineItemToken) {
                if((/mnp-/i).test(product.lineItemToken)) {
                    this.state.addInfraEnabled = true;
                    this.state.editProduct = product;
                    this.state.savePointData =  Utility.clone(product);
                    this.setState(this.state);
                } else {
                    var loc = _.findWhere(this.state.defaultLocations,
                        { loc: billgroup.loc });
                    var params = {
                        source: 'O',
                        token_id: product.lineItemToken,
                        product_code: product.pname,
                        zone_name: loc.locname
                    }
                    Session.set('CloudParams', params);
                    this.context.router.push('/edit-cloud-infra');
                }
            }
        }
    },

    getSlicedName(projectName){
        projectName = projectName;
        if(projectName){
        return ( projectName.length > CONSTANTS.LABEL_NAME_LIMIT ?
            projectName.slice(0 , CONSTANTS.LABEL_NAME_SUBSTR) + '...' : projectName );
        }
    },

    showNameOnHover(projectName) {
        projectName = projectName;
        var isNameLong = (projectName.length > CONSTANTS.LABEL_NAME_LIMIT);
        return this.classSet({
            labelHoverData: isNameLong,
            hide: !isNameLong
        });
    },

    showConfigurationsByType(type, subProduct) {
        var configView = (null);
        const types = CONSTANTS.PRODUCT_TYPES;
        switch (type) {
        case types.bundled:
            configView = this.showBundledConfig(subProduct);
            break;
        case types.withAttributes:
            configView = this.showWithAttributesConfig(subProduct)
            break;
        case types.standard:
            configView = (null);
            break;
        }
        return configView;
    },

    showBundledConfig(subProduct) {
        return (<li className="configDetailsBlock">
        <div className = "formElementWrap marginBottom10 clearfix">
        {_.map(subProduct, (data, outerIndex) => {
            // NOTE :if config attribute is a nested attribute
            if(data.atts) {
            var attribute = _.first(_.values(data.atts));
            var defaultValueId = attribute.defid;
            var chosenOne = attribute.attvals[defaultValueId];
            return (<div key={outerIndex}>
                        <div className="customSelectGroup pull-left clearfix">
                            <label className="labelData">{this.getSlicedName(data.spdesc) }</label>
                            <label className={this.showNameOnHover(data.spdesc) }>{data.spdesc}</label>
                            <ul className="customSelectList accountTypeList">
                                { _.map(attribute.attvals, (value, index) => (<li key={index}
                                    onClick={this.updateNestedConfigurationOption.bind(null, outerIndex, value.attvid, index) }
                                    value={value}>{value && value.attval}</li>))
                                }
                            </ul>
                            <p className="selectContent">{chosenOne && chosenOne.attval}</p>
                        </div>
                        {/*if value chosen from the first drop down have sub-attributes*/}
                        {chosenOne.appqty && (chosenOne.appqty.length > 1) ?
                            (<div className="customSelectGroup pull-left clearfix">
                                <ul className="customSelectList accountTypeList">
                                    { _.map(chosenOne.appqty, (value, index) => (<li key={index}
                                        onClick={this.updateInnerNestedConfigurationOption.bind(null, outerIndex, value) }
                                        value={value}>{value} {data.spuom}</li>))
                                    }
                                </ul>
                                <p className="selectContent">{chosenOne && chosenOne.selqty}</p>
                            </div>) :
                            (null)
                        }
                    </div>)
                } else {
                    return (<div className="customSelectGroup pull-left clearfix">
                        <label className="labelData">{this.getSlicedName(data.spdesc) }</label>
                        <label className={this.showNameOnHover(data.spdesc) }>{data.spdesc} in {data.spuom}</label>
                        <ul className="customSelectList accountTypeList">
                            { _.map(data.appqty, (value, index) => (<li key={index}
                                onClick={this.updateConfigurationOption.bind(null, outerIndex, value) }
                                value={value}>{value}</li>))
                            }
                        </ul>
                        <p className="selectContent">{data.spqty}</p>
                    </div>)
                }
            }) }</div>
        </li>)

    },

    updateConfigurationOption(attributeKey, value) {
        this.clearPricingObject();
        this.state.editProduct.sprod[attributeKey].spqty = value;
        this.setState(this.state);
    },

    updateNestedConfigurationOptionWithAttribute(index, newDefId, innerIndex) {
        this.clearPricingObject();
        var sprod = this.state.editProduct.sprod;
        sprod[_.first(Object.keys(sprod))].atts[index].defid = newDefId.attvid;
        var attVals = sprod[_.first(Object.keys(sprod))].atts[index].attvals
        var attvalToUpdate = _.findWhere(attVals, { attvid: newDefId });
        _.each(attVals, (value, key, obj) => {
            value.isdef = value.attvid === newDefId.attvid ? CONSTANTS.SHOPPING_CART_VARIABLES.YES : CONSTANTS.SHOPPING_CART_VARIABLES.NO;
        });
        this.updateConfigurationAttribute(sprod);
    },

    updateNestedConfigurationOption(index, newDefId, innerIndex) {
        this.clearPricingObject();
        var sprod = this.state.editProduct.sprod;
        _.first(_.values(sprod[index].atts)).defid = newDefId;
        var attVals = Utility.getNestedValue(sprod[index].atts, 'attvals');
        var attvalToUpdate = _.findWhere(attVals, { attvid: newDefId });
        _.each(attVals, (value, key, obj) => {
            value.isdef = value.attvid === newDefId ? CONSTANTS.SHOPPING_CART_VARIABLES.YES : CONSTANTS.SHOPPING_CART_VARIABLES.NO;
        });
        this.updateConfigurationAttribute(sprod);
    },

    updateInnerNestedConfigurationOption(index, newQty) {
        this.clearPricingObject();
        var sprod = this.state.editProduct.sprod;
        var attr = _.first(_.values(sprod[index].atts));
        var optionToUpdate = attr.attvals[attr.defid];
        optionToUpdate.selqty = newQty;
        this.updateConfigurationAttribute(sprod);
    },

    updateConfigurationAttribute(sprod) {
        this.clearPricingObject();
        this.state.editProduct.sprod = sprod;
        this.setState(this.state);
    },

    showWithAttributesConfig(subProduct) {
        var attributes = _.first(_.values(subProduct));
        return (<li className=" configDetailsBlock">
        <div className ="formElementWrap marginBottom10 clearfix" >
            { _.map(attributes && attributes.atts, (data, outerIndex) => {
                return (<div data-attribute-id={data.attid} key={outerIndex}>
                    <div className="customSelectGroup pull-left clearfix">
                        <label className="labelData">{this.getSlicedName(data.attname) }</label>
                        <label className={this.showNameOnHover(data.attname, data.spuom) }>{data.attname}</label>
                        <ul className="customSelectList accountTypeList">
                            { _.map(data.attvals, (value, index) => (<li key={index}
                                onClick={this.updateNestedConfigurationOptionWithAttribute.bind(null, outerIndex, value, index) }
                                value={value}>{value.attval}</li>))
                            }
                        </ul>
                        <p className="selectContent">{_.findWhere(data.attvals, { isdef: CONSTANTS.SHOPPING_CART_VARIABLES.YES })
                            && _.findWhere(data.attvals, { isdef: CONSTANTS.SHOPPING_CART_VARIABLES.YES }).attval}</p>
                    </div>
                </div>)
            })
            }</div>
        </li>);
    },

    getProductsPost() {
        setTimeout(() => {
            this.getInfraProducts();
            this.state.loaded = true;
        }, 3000);
    },

    saveCMSProduct(url, data) {
        this.state.loaded = false;
        shoppingCart.saveCart(url, data)
            .then((res) => {
                this.state.loaded = true;
                this.getInfraProducts();
                this.context.router.push('/product-list');
            })
            .catch((err) => {
                if(err == 'OK') {
                    // this.state.loaded = true;
                    UI.notifySuccess(CONSTANTS.UI_MESSAGES.shoppingCart.addInfraSuccess);
                    setTimeout(() => {
                        this.state.loaded = true;
                        this.getInfraProducts();
                    }, 3000);
                    this.context.router.push('/product-list');
                    this.setState(this.state);
                    Session.remove('corporateData');
                } else {
                    UI.notifyError(err && err.message);
                    setTimeout(() => {
                        this.state.loaded = true;
                        this.getInfraProducts();
                    }, 3000);
                    this.setState(this.state);
                    Session.remove('corporateData');
                }
            });
    },

    getIndividualPricingDetails(billGroup) {
        var pricingObject = this.getPricingObject([this.state.editProduct], billGroup);
        var url = CONSTANTS.API_URLS.shoppingCart.pricing;
        var userDetails = Session.get('userDetails');
        var userObj = User.getCustomerParams();
        if(pricingObject) {
            shoppingCart.getPricing(url, pricingObject)
            .then((result) => {
                if (result.text === 'success') {
                    result.data.acc = userObj.customerSugarId || userDetails && userDetails.kacid;
                    result.data.suppto = userObj.assoCustomerSugarId || userDetails && userDetails.kacid;
                    let item = _.first(_.first(result.data.billgroup).items);
                    item.ServiceType = "New";
                    this.state.editProduct.pricingObj = result.data;
                    let mainCustomerId = User.getMainCustomer('customerId') || null;
                    if(!_.isEmpty(Session.get('corporateData'))) {
                        let url;
                        if(User.getUserInfo('existingUser')) {
                            url = CONSTANTS.API_URLS.shoppingCart.addInfra + '?mainCustomerId='+mainCustomerId;
                        } else {
                            url = CONSTANTS.API_URLS.shoppingCart.addInfra + '?mainCustomerId=';
                        }
                        url = http.appendUrl(url, { associateCustomerId: User.getAssociateCustomer('customerId'), emailId: User.getUserInfo('loginUserEmail')});
                        let data = {
                            data: this.state.editProduct.pricingObj
                        }
                        this.saveCMSProduct(url, data);
                    }
                    Session.remove('corporateData');
                    this.state.updatedConfigItem = false;
                } else {
                    this.state.loaded = true;
                    this.state.error.pricing = CONSTANTS.UI_MESSAGES.shoppingCart.pricingError;
                }
                this.setState(this.state);
            })
            .catch((error) => {
                this.state.loaded = true;
                UI.notifyError(CONSTANTS.UI_MESSAGES.shoppingCart.pricingError && error.statusText);
                this.setState(this.state);
            });
        } else {
            this.context.router.push('/splash-screen');
        }
    },

    getPricingObject(editProductObjects, billGroup) {
        var default_option = _.first(editProductObjects);
        if (default_option) {
            var itemArray = [];
            _.each(editProductObjects, (editProductObject) => {
                itemArray.push(this.generateItemListArray(editProductObject))
            });
            var userData = User.getCustomerParams();
            var userDetails = Session.get('userDetails');
            var corporateData = Session.get('corporateData');
            var userObj = User.getCustomerParams();
            return {
                acc: userObj.customerSugarId || userDetails.kacid,
                suppto: userObj.assoCustomerSugarId || userDetails.kacid,
                token: null,
                billgroup: [{
                    projectname: billGroup && billGroup.projectname || '',
                    projectSugarId: '',
                    billtoadd: billGroup && billGroup.billtoadd || userDetails && userDetails.kaddress || '',
                    supptoadd: billGroup && billGroup.supptoadd || userDetails && userDetails.kaddress || '',
                    bill_cont: billGroup && billGroup.bill_cont || userDetails && userDetails.kcntid || '',
                    spoc_cont: billGroup && billGroup.spoc_cont || userDetails && userDetails.kcntid || '',
                    tech_cont: billGroup && billGroup.tech_cont || userDetails && userDetails.kcntid || '',
                    auth_sign: billGroup && billGroup.auth_sign || userDetails && userDetails.kcntid || '',
                    items: itemArray,
                    cntrperiodunit: billGroup && billGroup.cntrperiodunit || 'months',
                    cntrperiod: billGroup && billGroup.cntrperiod || CONSTANTS.ONE,
                    isExistingProject: billGroup && billGroup.isExistingProject || 'New',
                    loc: billGroup && billGroup.loc || corporateData &&corporateData.loc,
                    start_date: moment().format(CONSTANTS.STANDARD_FORMAT),
                    end_date: moment().add(billGroup && billGroup.cntrperiod || CONSTANTS.ONE, 'months').format(CONSTANTS.STANDARD_FORMAT)
                }]
            }
        }
        else {
            return [];
        }
    },

    generateItemListArray(editProductObject) {
        var configuration = _.omit(editProductObject, 'subCategory', 'subSubCategory', 'locationDetails', 'isOnlineProduct', 'uom');
        var variableProductUom;
        configuration.ServiceType = "New";
        var startDate = moment().format(CONSTANTS.STANDARD_FORMAT);
        var cntrPeriod = moment().add(CONSTANTS.ONE, 'months');
        var endDate = cntrPeriod.subtract(1, 'day').format(CONSTANTS.STANDARD_FORMAT);
        configuration.start_date = startDate;
        configuration.end_date = endDate;
        if (configuration.isVariableProduct === CONSTANTS.SHOPPING_CART_VARIABLES.YES) {
            configuration.VariableProductUOM = configuration.VariableProductUOM || this.deriveVariableProductUOM(configuration)
        }
        return configuration;
    },

    deriveVariableProductUOM(configuration) {
        var type;
        type = false;
        var spuomArray = _.pick(configuration.sprod, 'spuom');
        if (_.isArray(configuration.sprod) || _.isObject(configuration.sprod)) {
            type = _.find(configuration.sprod, (sprod) => sprod.spuom === 'GB' || sprod.spuom === 'MB');
        }
        return type.spuom || false;
    },

    updateCart() {
        let requestBody = {};
        if(_.isUndefined(this.state.editProduct.pricingObj)) {
            requestBody = {
                data: this.state.requestData
            }
        } else {
            requestBody = {
                data: this.state.editProduct.pricingObj
            }
           if(_.isEmpty(requestBody.data)) {
                UI.notifyError('Please Calculate the Price for the Product');
                return;
            }
        }
        let url = `${CONSTANTS.API_URLS.shoppingCart.updateInfra}?emailId=${User.getUserInfo('loginUserEmail')}`
        shoppingCart.addInfra(url, requestBody)
            .then((res) => {
                this.context.router.push('/product-list');
                this.getInfraProducts();
            })
            .catch((err) => {
                if(err == 'OK') {
                    UI.notifySuccess(CONSTANTS.UI_MESSAGES.shoppingCart.addInfraSuccess);
                    this.context.router.push('/product-list');
                }
            });
    },

    cancelUpdate(product, billGroup) {
        this.state.addInfraEnabled = false;
        this.state.updatedConfigItem = false;
        if(!_.isEqual(this.state.editProduct, this.state.savePointData)) {
            billGroup.items = _.reject(billGroup.items, (i) => i.lineItemToken === this.state.editProduct.lineItemToken )
                .concat(this.state.savePointData);
        }
        this.setState(this.state);
    },

    updateQuantity(event) {
        this.clearPricingObject();
        var target = event.currentTarget;
        var value = Number(target.value) || CONSTANTS.ONE;
        value = parseInt(Math.max(1, value), 10) || '';
        value = parseInt(Math.min(target.max, value), 10) || '';
        this.state.editProduct.qty = Number(value);
        this.setState(this.state);
    },

    clearPricingObject() {
        this.state.updatedConfigItem = true;
        this.state.editProduct.pricingObj = {};
        this.setState(this.state);
    },

    gotoSofDetails() {
        this.state.billGroupItems = _.chain(this.state.billGroups)
            .pluck('items')
            .flatten()
            .value();
        this.setState(this.state);
        let currentDate = moment().format(CONSTANTS.STANDARD_FORMAT);
        let confirm = _.every(this.state.billGroupItems, (item) => {
            this.state.errorMessage = null;
            if(item.salesEndDate < currentDate) {
                this.state.errorMessage = `Please remove the Product - ${item.pdesc} from your cart as it is expired.`;
                return false;
            } else {
                return true;
            }
            this.setState(this.state);
        })
        if(confirm) {
            this.context.router.push('/sof-details');
        } else {
            UI.notifyError(this.state.errorMessage);
            return;
        }
    },

    openModal(){
        this.setState({
            isOpen: true
        });        
    },

    hideModal(){
        this.setState({
            isOpen: false
        });
        this.context.router.push('/sof-details');            
    },

    render() {
        var mainCustomer = Session.get('mainCustomer');
        var quckLinkClass = _.isEmpty(mainCustomer) ? 'hide' : '';
        var billGroups = this.state.billGroups;
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
                                    <li className="currentCartStep"><i>1</i> Add Infra</li>
                                    <li><i>2</i> SOF Details</li>
                                    <li><i>3</i> Proceed for final payment</li>
                                </ul>
                            </div>
                            <div className="col-xs-3 paymentProcessBtns">
                                {
                                    billGroups.length ?
                                    <Link onClick={this.openModal} className="darkPinkBtn pull-right">Confirm</Link> :
                                    <span className="darkPinkBtn pull-right">
                                    Confirm</span>
                                }
                            </div>
                        </div>
                    </div>
                </section>
                <div className="container">
                    <div className="row">
                        <Loader loaded={this.state.loaded} top="50%" left="50%" options={LoaderOptions}>
                            <div className="col-md-9 myCartProjectListing">
                                <div className="projectHeadings clearfix">
                                    <span className="grayText pull-left">Project Name / Type / Contract / Location</span>
                                    <div className="pull-right chargesTypeWrap">
                                        <span className="grayText oneTimeCharge">One Time Charges</span>
                                        <span className="grayText monthlyCharge">Total Charges</span>
                                    </div>
                                </div>
                                <ul>
                                    {this.showBillGroups()}
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
                        <Modal className="termsConditions1 customModalWrapper provisionModal" isOpen={this.state.isOpen} onRequestHide={this.hideModal}>
                            <ModalHeader>
                                <ModalClose onClick={this.hideModal} />
                                <ModalTitle className="text_left">Important Information</ModalTitle>
                            </ModalHeader>
                            <ModalBody>
                                <ol>
								<li>All virtual machines require to be deployed behind a firewall</li>
                                <li>If you have an existing firewall, please select it at the point of provisioning else
                                Purchase a new firewall</li>
								</ol>
                            </ModalBody>
                           <ModalFooter></ModalFooter>
                        </Modal>
                    </div>
                </div>
            </div>
        )
    }
});

export default ProductList;
