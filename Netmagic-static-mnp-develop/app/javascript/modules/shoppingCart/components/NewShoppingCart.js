import { Link } from 'react-router';
import CONSTANTS from '../../../constants/app-constant';
import Loader from 'react-loader';
import LoaderOptions from '../../../mixins/loader';
import Contact from '../../../services/contact';
import User from '../../../services/user';
import Utility from '../../../mixins/basicUtils';
import Session from '../../../mixins/sessionUtils';
import http from '../../../mixins/restutils';
import UI from '../../../mixins/ui';
import AddInfra from './AddInfra';
import AssistanceBlock from'./AssistanceBlock';
import ListInfraProducts from './ListInfraProduct';
import EmptyCart from './EmptyCart';
import PaymentDone from './PaymentDone';
import shoppingCart from '../../../services/shoppingCart';
import Select from 'react-select';
import { Modal, ModalHeader, ModalTitle, ModalClose, ModalBody, ModalFooter} from 'react-modal-bootstrap';
import moment from 'moment';

var NewShoppingCart = createReactClass({
    mixins: [User, Utility, shoppingCart],
    contextTypes: {
        router: React.PropTypes.object
    },

    getInitialState() {
        var firstStep = _.last(CONSTANTS.SHOPPING_CART_PURCHASE_TABS);
        return {
            pageNumber: 1,
            totalPrice: {},
            products: [],
            editProduct: {
                period: {},
                location: {},
                planDetails: {},
                configuration: {},
                pricingGroup: {},
                pricingObj: {},
                projects: [],
            },
            globalData: {
                project: {
                    projectId: User.getProject('sugarProjectId') || '',
                    projectName: User.getProject('projectName') || '',
                    projectSugarId: User.getProject('sugarProjectId') || ''
                }
            },
            loaded: {
                productList: false,
                subSubCategories: false,
                pricing: false
            },
            defaultLocations: [],
            addInfraEnabled: false,
            activeStep: firstStep,
            maxStepNumber: firstStep.value,
            locations: [],
            subCategories: [],
            subSubCategories: [],
            productList: [],
            isOpen: false,
            isOpenBillGroup: false,
            billgroup: '',
            savePointData: {},
            error: {},
            pricingObjectsForProducts: [],
            subCategory: {},
            headerName: null,
            selectedPlan: null,
            subCatId: null,
            headerMenuCount: 0
        }
    },

    componentWillMount() {
        this.getLocations();
        this.getSubSubCategories(this.props.location.query.subcatid);
        this.state.subCatId = this.props.location.query.subcatid;
        this.getProjects();
        this.fetchUserDetails();
        this.state.loaded.productList = true;
        //delete below code after api for getCartData is ready
        this.state.products = Session.get('tempCartData') || []
        if (_.isEmpty(this.state.products)) {
            Session.remove('globalData');
        }
        var index = Session.get('tempCartIndex') || ''
        if (_.isNumber(index)) {
            this.enableEditForProduct(index)
        }
        Session.remove('tempCartIndex');
        this.state.editProduct.period.selectedProject = User.getProject('projectName') || '';
        this.state.editProduct.period.selectedProjectSugarId = User.getProject('sugarProjectId') || '';
        this.setState(this.state)
        this.reCalculateTotalPrice();
        //this.getCartData();
    },

    componentWillReceiveProps(nextProps) {
        this.getSubSubCategories(nextProps.location.query.subcatid);
        this.state.subCatId = nextProps.location.query.subcatid;        
        this.state.productList = [];
        this.state.subSubCategories = [];
        this.state.editProduct.planDetails = {};
        this.state.editProduct.configuration = {};
        this.state.editProduct.pricingObj = {};
        this.state.editProduct.period = {};
        this.state.editProduct.location = {};
        this.setState(this.state);
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
    openModal(nextStepNumber) {
        this.setState({ isOpen: true, nextStepNumber: nextStepNumber });
    },

    hideModal(event) {
        this.setState({ isOpen: false });
    },

    hideModalBillgroup(event) {
        this.setState({ isOpenBillGroup: false });
    },

    getProjects() {
        let userObj = User.getCustomerParams();
        var userParams = User.getCustomerParams();
        if (this.isLoggedIn()) {
            var params = {
                mainCustomerId: '',
                associateCustomerId: '',
                sugarMainCustomerId: userObj.customerSugarId,
                sugarAssociateCustomerId: userObj.assoCustomerSugarId,
                userEmail: User.getUserInfo('loginUserEmail')
            };
            if(User.getUserInfo('existingUser')) {
                params = {
                    mainCustomerId: userObj.customerId,
                    associateCustomerId: userObj.assoCustomerId,
                    sugarMainCustomerId: userObj.customerSugarId,
                    sugarAssociateCustomerId: userObj.assoCustomerSugarId,
                    userEmail: User.getUserInfo('loginUserEmail')
                };
            }
            shoppingCart.getProjectForAddInfra(params)
                .then((result) => {
                    var projects =
                        _.map(result, (project) => {
                            return { label: project.projectName, value: project.projectName, id: project.projectId, sugarId: project.projectSugarId };
                        });
                    this.state.editProduct.projects = projects;
                    this.setState(this.state)
                })
                .catch((err) => {
                    UI.notifyError(err && err.statusText);
                });
        }
    },

    // Function has been temporarily deprecated
    getLocations() {
        shoppingCart.getData(CONSTANTS.API_URLS.shoppingCart.locations)
            .then((result) => {
                this.setState({ defaultLocations: result.items })
            })
            .catch(function (error) {
                UI.notifyError(error && error.statusText)
            }.bind(this));
    },

    updateLocations(locationData) {
        this.setState({ locations: locationData })
    },

    getSubCategories() {
        shoppingCart.getData(CONSTANTS.API_URLS.shoppingCart.subCategories)
            .then((result) => {
                this.state.subCategories = result.items
                this.state.loaded.productList = true;
                this.setState(this.state)
            })
            .catch(function (error) {
                UI.notifyError(error && error.statusText)
            }.bind(this));
    },

    getSubSubCategories(subcatid) {
        const shoppingConst = CONSTANTS.SHOPPING_CART_VARIABLES;
        this.state.loaded.subSubCategories = true;
        this.setState(this.state)
        var url = http.buildRestUrl(CONSTANTS.API_URLS.shoppingCart.subSubCategories, { subCategoryId: subcatid })
        var billGroupResponse = Session.get('billGroupResponse');
        shoppingCart.getData(url)
            .then((result) => {
                this.state.loaded.subSubCategories = true;
                this.state.subSubCategories = result.items;
                this.state.subSubCategories = _.reject(this.state.subSubCategories, (item) =>  item.subsubcatid == shoppingConst.TEMPLATE
                );
                this.state.subSubCategories = _.reject(this.state.subSubCategories, (item) =>  item.subsubcatid == shoppingConst.SIMPLISCALE
                );
                let CAPPED = shoppingConst.CAPPED.indexOf(billGroupResponse &&billGroupResponse.rateBasedType) > -1;
                let BURSTABLE = shoppingConst.BURSTABLE.indexOf(billGroupResponse && billGroupResponse.rateBasedType) > -1;
                switch (true) {
                    case CAPPED:
                        this.state.subSubCategories = _.reject(this.state.subSubCategories, (item) =>  item.subsubcatid == shoppingConst.BURSTABLE_ID
                        );
                    break;
                    case BURSTABLE:
                        this.state.subSubCategories = _.reject(this.state.subSubCategories, (item) =>  item.subsubcatid == shoppingConst.CAPPED_ID
                        );
                    break;
                }
                this.setState(this.state)
            })
            .catch((error) => {
                UI.notifyError(error && error.statusText);
            });
    },

    checkIfCapped(planName) {
        const cartConst = CONSTANTS.SHOPPING_CART_VARIABLES;
        let billGroupResponse = Session.get('billGroupResponse');
        let isCapped = "";
        let plan = "";
        let cappedItem = (cartConst.CAPPED.indexOf(billGroupResponse && billGroupResponse.rateBasedType) > -1);
        let burstable = (cartConst.BURSTABLE.indexOf(billGroupResponse && billGroupResponse.rateBasedType) > -1);
        let volumeItem = (cartConst.VOLUME_BASED.indexOf(billGroupResponse && billGroupResponse.rateBasedType || billGroupResponse && billGroupResponse.billingGroupTypes) > -1);
        switch(true) {
            case cappedItem:
                isCapped = cartConst.YES;
                plan =  billGroupResponse && billGroupResponse.unitOfMeasurment;
                break;
            case burstable:
                isCapped = cartConst.NO;
                plan =  billGroupResponse && billGroupResponse.unitOfMeasurment;
                break;
            case volumeItem:
                isCapped = cartConst.NO;
                plan =  billGroupResponse && billGroupResponse.unitOfMeasurment;
                break;
            default:
                isCapped = "";
                plan = "";
                break;
        }
        return {
            isCapped: isCapped,
            plan: plan
        }
    },

    getLineItems(subSubCategoryIdParam, locationId) {
        var cappedVariables = this.checkIfCapped(this.state.editProduct.planDetails.plan);
        var isCapped = cappedVariables.isCapped || '';
        var plan = (this.state.editProduct.period.selectedProjectIsNew ? '' : cappedVariables.plan) || '';
        var url = http.buildRestUrl(CONSTANTS.API_URLS.shoppingCart.products, { subCategoryId: subSubCategoryIdParam })
        url = url + 'isCapped=' + isCapped;
        url = http.appendUrl(url, { plan: plan, location: locationId });
        this.state.loaded.productList = false;
        this.setState(this.state);
        shoppingCart.getData(url)
            .then((result) => {
                if (result.items) {
                    this.state.productList = result.items;
                    _.each(this.state.productList, (obj, i) => {
                        obj.clicked = false;
                    })
                    this.state.error.productList = "";
                }
                else {
                    this.state.error.productList = CONSTANTS.UI_MESSAGES.shoppingCart.productListError;
                }
                this.state.loaded.productList = true;
                this.setState(this.state)

            })
            .catch((error) => {
                this.state.loaded.productList = true;
                this.setState(this.state)
                if (error.status == CONSTANTS.ERROR_CODE.notFound) {
                    UI.notifyError(JSON.parse(error.responseText).message);
                } else {
                    UI.notifyError(JSON.parse(error.responseText).message);
                }
            });
    },

    getProducts() {
        var subSubCategoryIdParam = this.state.editProduct.planDetails.planid;
        var userObj = User.getCustomerParams();
        if (subSubCategoryIdParam) {
            var location = _.findWhere(this.state.defaultLocations,
                { locname: this.state.editProduct.location.selectedLocation });
            var locationId = (location && location.loc) || '';
            if(!locationId) {
                UI.notifyError(CONSTANTS.UI_MESSAGES.shoppingCart.mandatoryLocation);
                return
            }
            let billGroupResponse = Session.get('billGroupResponse');
            let project = this.state.editProduct.period.selectedProject || '';
            var customerDetails = User.getCustomerParams();
            var url = CONSTANTS.API_URLS.shoppingCart.billGroupFetch;
            var requestParams = {
                billToCustomer: userObj.customerSugarId,
                supportToCustomer: userObj.assoCustomerSugarId,
                projectName: this.state.editProduct.period.selectedProject,
                mainCustomerId: '',
                associateCustomerId: '',
                projectId: ''
            };
            if(User.getUserInfo('existingUser')) {
                requestParams = {
                    mainCustomerId: userObj.customerId,
                    associateCustomerId: userObj.assoCustomerId,
                    projectId: this.state.editProduct.period.selectedProjectSugarId,
                    billToCustomer: userObj.customerSugarId,
                    supportToCustomer: userObj.assoCustomerSugarId,
                    projectName: this.state.editProduct.period.selectedProject
                }
            }
            shoppingCart.fetchBillGroup(CONSTANTS.API_URLS.shoppingCart.billGroupFetch, requestParams)
                .then((res) => {
                    Session.set('billGroupResponse', _.omit(res, 'response', 'text'));
                    this.getLineItems(subSubCategoryIdParam, locationId);
                })
                .catch((err) => {
                    this.state.loaded.productList = true;
                    this.setState(this.state);
                    UI.notifyError(err);
                });
        }
        else {
            //UI.notifyError( CONSTANTS.UI_MESSAGES.shoppingCart.locationError) //TODO handleException
        }
    },

    generatePricingGroupArray(editProductObjects) {
        var pricingGroupArrayIdentifiers = []
        var pricingGroupArray = []
        var flag, i, temp;
        _.each(editProductObjects, (editProductObject) => {
            flag = false;
            i = 0;
            _.each(pricingGroupArrayIdentifiers, (pricingGroupElement) => {
                if (_.isEqual(editProductObject.pricingGroup, pricingGroupElement)) {
                    pricingGroupArray[i].push(editProductObject);
                    flag = true;
                }
                i++;
            });
            if (!flag) {
                pricingGroupArrayIdentifiers.push(editProductObject.pricingGroup)
                pricingGroupArray[i] = [];
                pricingGroupArray[i].push(editProductObject)
            }
        });
        return pricingGroupArray;
    },

    showDefaultProject(key) {
        let value = null;
        if(CONSTANTS.SHOPPING_CART_VARIABLES.CAPPED_VOLUME_PRODUCTS.indexOf(this.state.editProduct.planDetails.planid) > -1) {
                value = this.state.globalData.project[key]
        }
        return value;
    },

    getPricingObject(editProductObjects) {
        var default_option = _.first(editProductObjects);
        if (default_option) {
            var itemArray = [];
            _.each(editProductObjects, (editProductObject) => {
                itemArray.push(this.generateItemListArray(editProductObject))
            });
            var userData = User.getCustomerParams();
            var location = _.findWhere(this.state.defaultLocations,
                { locname: default_option.location.selectedLocation });
            var startDate = moment().format(CONSTANTS.STANDARD_FORMAT);
            var cntrPeriod = moment().add(default_option.period.contractPeriod, 'months');
            var endDate = cntrPeriod.subtract(1, 'day').format(CONSTANTS.STANDARD_FORMAT);
            var userDetails = Session.get('userDetails');
            return {
                acc: userData.customerSugarId || userDetails.kacid,
                suppto: userData.assoCustomerSugarId || userDetails.kacid,
                token: null,
                billgroup: [{
                    projectname: default_option.period.selectedProject || this.showDefaultProject('projectName') || '',
                    projectSugarId: default_option.period.selectedProjectSugarId ||this.showDefaultProject('projectSugarId'),
                    billtoadd: userDetails && userDetails.kaddress || null,
                    supptoadd: userDetails && userDetails.kaddress || null,
                    bill_cont: userDetails && userDetails.kcntid || null,
                    spoc_cont: userDetails && userDetails.kcntid || null,
                    tech_cont: userDetails && userDetails.kcntid || null,
                    auth_sign: userDetails && userDetails.kcntid || null,
                    items: itemArray,
                    cntrperiodunit: 'months',
                    cntrperiod: default_option.period.contractPeriod,
                    isExistingProject: !default_option.period.selectedProjectIsNew ? 'Yes' : 'No',
                    loc: location && location.loc,
                    start_date: startDate,
                    end_date: endDate,
                    projectid: default_option.period.selectedProjectSugarId ||this.showDefaultProject('projectSugarId')
                }]
            }
        }
        else {
            return [];
        }
    },

    deriveVariableProductUOM(configuration) {
        var type;
        type = false;
        var spuomArray = _.pick(configuration.sprod, 'spuom');
        if (_.isArray(configuration.sprod) || _.isObject(configuration.sprod)) {
            type = _.find(configuration.sprod, (sprod) => sprod.spuom === 'GB' || sprod.spuom === 'MB');
        }
        return type && type.spuom || false;
    },

    generateItemListArray(editProductObject) {
        var location = _.findWhere(this.state.defaultLocations,
            { locname: editProductObject.location.selectedLocation });
        var locationId = location && location.loc;
        var configuration = _.omit(editProductObject.configuration, 'subCategory', 'subSubCategory', 'locationDetails', 'isOnlineProduct', 'uom');
        var startDate = moment().format(CONSTANTS.STANDARD_FORMAT);
        var cntrPeriod = moment().add(this.state.editProduct.period.contractPeriod, 'months');
        var endDate = cntrPeriod.subtract(1, 'day').format(CONSTANTS.STANDARD_FORMAT);
        configuration.start_date = startDate;
        configuration.end_date = endDate;
        configuration.ServiceType = "New";
        var variableProductUom;
        if (!this.state.globalData.selectedProjectIsNew) {
            configuration.projectid = this.state.editProduct.period.selectedProjectSugarId ||
                this.state.globalData.project.selectedProjectSugarId
        }
        if (configuration.isVariableProduct === CONSTANTS.SHOPPING_CART_VARIABLES.YES) {
            configuration.VariableProductUOM = configuration.VariableProductUOM || this.deriveVariableProductUOM(configuration) || ''
        }
        return configuration;
    },

    getLocationId(selectedLocation) {
        var location = _.findWhere(this.state.defaultLocations,
            { locname: this.state.editProduct.location.selectedLocation });
        return (location && location.loc);
    },

    getIndividualPricingDetails() {
        this.state.loaded.pricing = true;
        this.setState(this.state)
        var pricingObject = this.getPricingObject([this.state.editProduct]);
        var url = CONSTANTS.API_URLS.shoppingCart.pricing;
        var userDetails = Session.get('userDetails');
        var userObj = User.getCustomerParams();
        shoppingCart.getPricing(url, pricingObject)
            .then((result) => {
                if (result.text === 'success') {
                    result.data.acc = userObj.customerSugarId ||userDetails && userDetails.kacid;
                    result.data.suppto = userObj.assoCustomerSugarId || userDetails && userDetails.kacid;
                    this.state.editProduct.pricingObj = result.data;
                    let billgroup = _.first(this.state.editProduct.pricingObj.billgroup);
                    billgroup.projectid = this.state.editProduct.period.selectedProjectSugarId ||
                        this.showDefaultProject('projectSugarId');
                    billgroup.projectname = this.state.editProduct.period.selectedProject ||
                        this.showDefaultProject('projectName') || '';
                    billgroup.projectSugarId = this.state.editProduct.period.selectedProjectSugarId ||this.showDefaultProject('projectSugarId');
                    let item = _.first(billgroup.items);
                    item.ServiceType = "New";
                    this.state.editProduct.pricingGroup.contractPeriod = this.state.editProduct.period.contractPeriod;
                    this.state.editProduct.pricingGroup.locationid = this.getLocationId(this.state.editProduct.location.selectedLocation);
                    this.state.editProduct.pricingGroup.project = this.state.editProduct.period.selectedProject;
                    this.state.error.pricing = "";
                }
                else {
                    this.state.error.pricing = CONSTANTS.UI_MESSAGES.shoppingCart.pricingError;
                }
                this.state.loaded.pricing = false;
                this.setState(this.state);
            })
            .catch(function (error) {
                UI.notifyError(CONSTANTS.UI_MESSAGES.shoppingCart.pricingError && error.statusText)
            }.bind(this));
    },

    reCalculateTotalPrice() {
        var pricingObject = this.getPricingObject(this.state.products);
        if (_.isEmpty(pricingObject)) {
            this.state.totalPrice = 0;
            this.setState(this.state)
        }
        else {
            var url = CONSTANTS.API_URLS.shoppingCart.pricing;
            shoppingCart.getPricing(url, pricingObject)
                .then((result) => {
                    if (result.status === 'Success') {
                        this.state.totalPrice = result.data;
                    }
                    else {
                        this.state.error.pricing = CONSTANTS.UI_MESSAGES.shoppingCart.pricingError;
                    }
                    this.setState(this.state)
                })
                .catch(function (error) {
                    UI.notifyError(CONSTANTS.UI_MESSAGES.shoppingCart.pricingError && error.statusText)
                }.bind(this));
        }

    },

    clearPricingObject() {
        this.state.editProduct.pricingObj = {};
        this.state.editProduct.pricingGroup = {};
        this.setState(this.state);
    },

    initiateCreateProject(status) {
        this.state.editProduct.period.selectedProjectIsNew = status;
        this.state.editProduct.period.selectedProject = "";
        this.state.editProduct.period.selectedProjectSugarId = "";
        this.state.productList = [];
        this.state.editProduct.configuration = {};
        this.setState(this.state)
    },

    activeStepFunc(step) {
        if (this.state.maxStepNumber < step.value) {
            if (!(_.isEqual(this.state.editProduct, this.state.savePointData)) && step.value === 3) {
                this.openModal(step.value);
            }
            else {
                UI.notifyError(CONSTANTS.UI_MESSAGES.shoppingCart.navigationError)
            }
        } else {
            this.setActiveStep(step.value);
        }
    },

    produceSavePointData() {
        this.state.savePointData = Utility.clone(this.state.editProduct);
        this.setState(this.state)
    },

    clearData() {
        var editProduct = this.state.editProduct;
        var stepNumber = this.state.activeStep.value;
        if (stepNumber === 2) {
            _.extend(editProduct, { planDetails: {}, configuration: {}, pricingGroup: {}, pricingObj: {} })
        }
        var step = _.findWhere(CONSTANTS.SHOPPING_CART_PURCHASE_TABS, { value: stepNumber });
        editProduct.location.product = null;
        this.setState({
            editProduct: editProduct,
            maxStepNumber: stepNumber,
            savePointData: editProduct,
            productList: [],
            error: {}
        });
        this.hideModal();
    },

    restoreData() {
        var stepNumber = this.state.activeStep.value;
        this.state.editProduct = Utility.clone(this.state.savePointData)
        this.setState(this.state);
        this.getProducts()
        this.hideModal();
    },

    handleNext(stepNumber) {
        // This code has been commented out cause Location data is now coming directly from Configuration object, keeping login for future reference
        // if(this.state.maxStepNumber >= stepNumber &&  !(_.isEqual(this.state.editProduct,this.state.savePointData)) && stepNumber==3) {
        //   this.openModal(stepNumber);
        // } else {
        this.produceSavePointData();
        this.setActiveStep(stepNumber);
        // }
    },

    handleBack(stepNumber) {
        this.setActiveStep(stepNumber);
    },

    setActiveStep(stepNumber) {
        var step = _.findWhere(CONSTANTS.SHOPPING_CART_PURCHASE_TABS, { value: stepNumber });
        var max;
        if (step.value > this.state.maxStepNumber) {
            max = step.value
        }
        else {
            max = this.state.maxStepNumber;
        }
        this.produceSavePointData();
        this.setState({
            activeStep: step,
            maxStepNumber: max
        });
    },

    setAddInfraEnabled() {
        this.state.addInfraEnabled = true;
        this.state.savePointData = Utility.clone(this.state.editProduct);
        this.setState(this.state);
    },

    setAddInfraDisabled() {
        this.state = this.refreshEditProduct(this.state)
        this.setState(this.state);
    },

    updateDropDown(key, subKey, value) {
        this.clearPricingObject();
        this.state.editProduct[key][subKey] = value;
        if (subKey === 'selectedLocation') {
            this.state.error = {};
            //On Change of Location, the below senario is no longer required
            //this.state.editProduct.planDetails.planid = "";
            //this.state.editProduct.planDetails.plan = "";
        }
        else if (subKey === 'selectedProject' && !this.state.editProduct.period.selectedProjectIsNew) {
            var projects = this.state.editProduct.projects
            var project = _.findWhere(projects,
                { label: value });
            var projectId = project && project.id;
            var projectSugarId = project && project.sugarId;
            this.state.editProduct.period.selectedProjectId = projectId
            this.state.editProduct.period.selectedProjectSugarId = projectSugarId
            this.state.globalData.selectedProjectSugarId = project && project.sugarId
            this.state.productList = [];
            this.state.editProduct.configuration = {};
        }
        this.setState(this.state);
    },

    addCart() {
        /**
         * Implements New Add To Cart Feature
         */
        let requestBody = {
            data: this.state.editProduct.pricingObj
        };
        if(_.isEmpty(requestBody.data)) {
            UI.notifyError('Please Calculate the Price for the Product');
            return;
        }
        let url;
        if(User.getUserInfo('existingUser')) {
            url = CONSTANTS.API_URLS.shoppingCart.addInfra + '?mainCustomerId='+User.getMainCustomer('customerId');
        } else {
            url = CONSTANTS.API_URLS.shoppingCart.addInfra + '?mainCustomerId=';
        }
        url = http.appendUrl(url, { associateCustomerId: User.getAssociateCustomer('customerId'), projectId: User.getProject('sugarProjectId') || this.state.editProduct.period.selectedProjectSugarId, emailId: User.getUserInfo('loginUserEmail')});
        shoppingCart.addInfra(url, requestBody)
            .then((res) => {
                this.context.router.push('/product-list');
            })
            .catch((err) => {
                if(err == 'OK') {
                    UI.notifySuccess(CONSTANTS.UI_MESSAGES.shoppingCart.addInfraSuccess);
                    this.context.router.push('/product-list');
                } else if(err == 'conflict') {
                    UI.notifyError(CONSTANTS.UI_MESSAGES.shoppingCart.billGroupError);
                } else {
                    UI.notifyError(err && err.message);
                }
            });
    },

    saveCartData(cartList) {
        Session.set('tempCartData', cartList);
        shoppingCart.saveCart(CONSTANTS.API_URLS.shoppingCart.saveCart, cartList)
            .then(function (res) {
                // TODO : api currently has a problem, assign received data to state.products []
            }.bind(this))
            .catch(function (err) {
                // TODO : api currently has a problem, assign received data to state.products []
            }.bind(this));
    },

    getCartData() {
        shoppingCart.getData(CONSTANTS.API_URLS.shoppingCart.getCart)
            .then((result) => console.log(result))// TODO : api currently has a problem, assign received data to state.products []
            .catch(function (error) {
                UI.notifyError(error && error.statusText)
            }.bind(this));
    },

    refreshEditProduct(state) {
        state.addInfraEnabled = false;
        var firstStep = _.first(CONSTANTS.SHOPPING_CART_PURCHASE_TABS);
        //Global Data impact is here... good coding deep
        state.editProduct = {
            period: {
                contractPeriod: this.state.globalData.contractPeriod || '',
                selectedProject: this.state.globalData.project || '',
                selectedProjectIsNew: this.state.globalData.selectedProjectIsNew
            },
            location: {},
            planDetails: {},
            configuration: {},
            pricingGroup: {},
            pricingObj: {},
        }
        state.activeStep = firstStep;
        state.maxStepNumber = firstStep.value
        return state;
    },

    onRadioProductSelect(obj) {
        this.clearPricingObject();
        this.getSubSubCategories(this.props.location.query.subcatid)
        if (_.isEmpty(this.state.products) || this.state.products.length === 0) {
            this.state.globalData = {};
        }
        this.state.editProduct.location.product = obj.subcatname;
        this.state.editProduct.location.productid = obj.subcatid;
        this.state.productList = [];
        this.state.error = {};

        this.setState(this.state)
    },

    getHeader() {
        let planName = this.state.editProduct.planDetails.plan;
        let headerName = null;
        const categories = CONSTANTS.CATEGORY_TYPE;
        let checkForVServer = _.contains(categories.virtualServer.items, planName);
        let checkForFirewall = _.contains(categories.virtualFireWall.items, planName);
        let checkForNetwork = _.contains(categories.virtualNetwork.items, planName);
        let checkForServices = _.contains(categories.services.items, planName);
        switch(true) {
            case checkForVServer:
                headerName = categories.virtualServer.label;
                break;
            case checkForFirewall:
                headerName = categories.virtualFireWall.label;
                break;
            case checkForNetwork:
                headerName = categories.virtualNetwork.label;
                break;
            case checkForServices:
                headerName = categories.services.label;
                break;
            default:
                headerName = null;
            break;
        }
        return headerName;
    },

    onRadioPlanSelect(obj) {
        this.clearPricingObject();
        this.state.editProduct.planDetails.plan = obj.subsubcatname;
        this.state.editProduct.planDetails.planid = obj.subsubcatid;
        this.state.editProduct.configuration = {};
        this.state.productList = [];
        this.state.error = {};
        this.setState(this.state);
        this.getProducts();
    },


    updateSystemConfigurations(obj) {
        this.clearPricingObject();
        this.state.editProduct.configuration = obj;
        this.state.globalData.VariableProductUOM = obj.VariableProductUOM;
        this.state.globalData.isCappedProduct = obj.isCappedProduct;
        this.state.locations = obj.locationDetails;// Location data now loaded from configuration object
        // added by deep, new design scene
        // var makeFalse = _.findWhere(this.state.productList,{clicked:true})
        // makeFalse ? makeFalse.clicked = false : (null)
        _.each(this.state.productList, (singleProduct) => {
            _.isEqual(_.first(singleProduct.sugar), obj) ? singleProduct.clicked = true : singleProduct.clicked = false
        })
        this.setState(this.state);
    },



    updateConfigurationOption(attributeKey, value) {
        this.clearPricingObject();
        this.state.editProduct.configuration.sprod[attributeKey].spqty = value;
        this.setState(this.state);
    },

    updateConfigurationAttribute(sprod) {
        this.clearPricingObject();
        this.state.editProduct.configuration.sprod = sprod;
        this.setState(this.state);
    },

    updateQuantity(quantity) {
        this.clearPricingObject();
        this.state.editProduct.configuration.qty = Number(quantity);
        this.setState(this.state);
    },

    enableEditForProduct(productIndex) {
        if (this.state.addInfraEnabled) {
            UI.notifyError(CONSTANTS.UI_MESSAGES.shoppingCart.openCartError)
        }
        else {
            this.state.editProduct = this.state.products[productIndex]
            this.state.locations = this.state.editProduct.configuration.locationDetails
            this.state.products.splice(productIndex, 1);
            this.handleGlobalData();
            var lastStep = _.last(CONSTANTS.SHOPPING_CART_PURCHASE_TABS);
            var firstStep = _.first(CONSTANTS.SHOPPING_CART_PURCHASE_TABS);
            this.state.activeStep = firstStep;
            this.state.maxStepNumber = lastStep.value;
            this.state.addInfraEnabled = true;
            this.getSubSubCategories(this.state.editProduct.location.productid);
            this.getProducts();
            this.state.savePointData = Utility.clone(this.state.editProduct);
            this.setState(this.state);
        }
    },

    handleGlobalData() {
        var products = this.state.products;
        if (_.isEmpty(products || products.length === 1)) {
            this.state.globalData = {}
            Session.remove('globalData');
            this.setState(this.state)
        }
        else {
            var variableProductUOM = '';
            this.state.products.forEach(function (product, i) {
                if (product.configuration.VariableProductUOM === 'GB' || product.configuration.VariableProductUOM === 'MB') {
                    variableProductUOM = product.configuration.VariableProductUOM
                }
            });
            this.state.globalData.VariableProductUOM = variableProductUOM;
            this.setState(this.state.globalData)
            Session.set('globalData', this.state.globalData);
        }
    },

    removeProductFromCart(productIndex) {
        this.state.products.splice(productIndex, 1);
        Session.set('tempCartData', this.state.products)
        this.handleGlobalData();
        this.reCalculateTotalPrice();
        this.setState(this.state)
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
            <span>
                {_.map(attributes && attributes.atts, (data, outerIndex) => {
                    return (
                        <div key={outerIndex} className="listDetails">
                            <label>{data.attname}</label>
                            <span>{_.findWhere(data.attvals, { isdef: 'Yes' })
                                && _.findWhere(data.attvals, { isdef: 'Yes' }).attval}
                                {data.spuom}
                            </span>
                        </div>
                    )
                }) }
            </span>
        );
    },

    showBundledConfigForListInfra(subProduct) {
        return (<div >
            {_.map(subProduct, (data, outerIndex) => {
                //if config attribute is a nested attribute
                if (data.atts) {
                    var attribute = _.first(_.values(data.atts));
                    var defaultValueId = attribute.defid;
                    var chosenOne = attribute.attvals[defaultValueId];
                    //data.spdesc --> label : chosenOne --> value
                    return (
                        <div key={outerIndex} className="listDetails">
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
                    )
                } else {
                    return (
                        <div className="listDetails">
                            <label>{data.spdesc}</label>
                            <span>
                                {data.spqty}
                                {data.spuom}
                            </span>
                        </div>
                    )
                }
            }) }
        </div>)
    },

    purchaseCart() {
        if (!this.isLoggedIn()) {
            this.context.router.push('/MyNetmagic');
            return;
        }
        //check session--> open Sign iup
        this.state.pricingObjectsForProducts = []
        var groupedProducts = this.generatePricingGroupArray(this.state.products);
        var i = 0;
        var urlbill = CONSTANTS.API_URLS.shoppingCart.billingGroup
        var sessionData = User.getCustomerParams()
        var params = {
            customerId: sessionData.customerId,
            assoCustomerId: sessionData.assoCustomerId,
            projectId: this.state.globalData.projectId,
        }
        shoppingCart.getBillingGroup(urlbill, params)
            .then((result) => {
                if (result.billingGroupNumber) {
                    this.state.globalData.selectedProjectIsNew = 'Yes'
                }
                else {
                    this.state.globalData.selectedProjectIsNew = 'No'
                }
                this.setState(this.state)
                result.unitOfMeasurment = result.unitOfMeasurment || ''
                if (result.unitOfMeasurment === this.state.globalData.VariableProductUOM || result.unitOfMeasurment === "") {
                    _.each(groupedProducts, (groupedProduct) => {
                        var pricingObject = this.getPricingObject(groupedProduct);
                        var url = CONSTANTS.API_URLS.shoppingCart.pricing;
                        shoppingCart.getPricing(url, pricingObject)
                            .then((result) => {
                                if (result.status === 'Success') {
                                    //this is the array of objects to be sent for contract creation
                                    _.first(result.data.billgroup).projectid = this.state.globalData.selectedProjectSugarId //*****
                                    _.first(result.data.billgroup).isExistingProject = this.state.globalData.selectedProjectIsNew
                                    this.state.pricingObjectsForProducts[i] = result.data;
                                    i++;
                                    Session.set('ShoppingCartPricingDetails', this.state.pricingObjectsForProducts)
                                    location.hash = "#/shopping-cart/add-contact";
                                    this.setState(this.state)
                                }
                                else {
                                    //Error in pricing API for grouping, yet to be discussed
                                }
                            })
                            .catch(function (error) {
                                UI.notifyError(CONSTANTS.UI_MESSAGES.shoppingCart.pricingError && error.statusText)
                            }.bind(this));
                    });
                }
                else {
                    this.state.isOpenBillGroup = true;
                    this.state.billgroup = result.billingGroupTypes
                    this.setState(this.state)
                }
            });
    },

    render() {
        var mainCustomer = Session.get('mainCustomer')
        var quckLinkClass = _.isEmpty(mainCustomer) ? 'hide' : '';
        return (
            <div className="ticketListSection outStandingWrap peopleWrap shoppingCartWrap">
                <div className="blackOverlay"></div>
                <section className="midHeader">
                    <div className="container">
                        <div className="row">
                            <div className="col-xs-8 col-sm-8 col-md-8">
                                <ol className={"breadcrumb breadCrumbCustom clearfix " + quckLinkClass}>
                                    <li><Link to={User.getSplashScreenURL() }>Back to Home</Link></li>
                                    <li>
                                        <Link to="dashboard" className={"blueLink"}>Back to Dashboard</Link>
                                    </li>
                                    <li className="active">Shopping Cart</li>
                                </ol>
                                <h2 className="pageHeading">{'Add New ' + ( this.getHeader() ||
                                    'Infra')}</h2>
                            </div>
                            <div className="col-xs-4 col-sm-4 col-md-4 pull-right text-right actionBtnsWrap">
                                <span onClick={this.addCart} className="addInfraBtn">Add To Cart</span>
                                <Link to="/product-list" className="placeOrderBtn">Cancel</Link>
                            </div>
                        </div>
                    </div>
                </section>
                <section className="buyOptionWrap addFormWrapper addLoadBalancer">
                    <div className="container shoppingCartContainer">
                        <div className="row">
                            <AddInfra
                                globalData = {this.state.globalData}
                                updateLocations = {this.updateLocations}
                                updateDropDown = {this.updateDropDown}
                                updateQuantity = {this.updateQuantity}
                                location={this.state.locations}
                                handleNext={this.handleNext}
                                handleBack={this.handleBack}
                                activeStepFunc={this.activeStepFunc}
                                activeStep={this.state.activeStep}
                                editProduct={this.state.editProduct}
                                setAddInfraDisabled = {this.setAddInfraDisabled}
                                subCategories = {this.state.subCategories}
                                onRadioProductSelect = {this.onRadioProductSelect}
                                subSubCategories = {this.state.subSubCategories}
                                onRadioPlanSelect = {this.onRadioPlanSelect}
                                getProducts = {this.getProducts}
                                productList = {this.state.productList}
                                updateSystemConfigurations = {this.updateSystemConfigurations}
                                updateConfigurationOption = {this.updateConfigurationOption}
                                produceSavePointData = {this.produceSavePointData}
                                savePointData = {this.state.savePointData}
                                activeStep = {this.state.activeStep}
                                updateConfigurationAttribute = {this.updateConfigurationAttribute}
                                locations = {this.state.defaultLocations}
                                error = {this.state.error}
                                loaded = {this.state.loaded}
                                getIndividualPricingDetails = {this.getIndividualPricingDetails}
                                initiateCreateProject = {this.initiateCreateProject }
                                clearPricingObject = {this.clearPricingObject}
                                getProjects = {this.getProjects}
                                isOpenBillGroup = {this.state.isOpenBillGroup}
                                hideModalBillgroup = {this.hideModalBillgroup}
                                products = {this.state.products}
                                maxStepNumber = {this.state.maxStepNumber}
                                subCategory = {this.state.subCategory}
                                selectedPlan={this.state.selectedPlan}
                                subCatId = {this.state.subCatId}/>
                            {
                                this.state.products.length > 0 || this.state.addInfraEnabled ?
                                    <AssistanceBlock
                                        product = {this.state.products}
                                        totalPrice = {this.state.totalPrice}
                                        /> : (null)
                            }

                            {/*
                 this.state.products.length ?
                 <div className="col-md-9 col-lg-9">
                    <ListInfraProducts
                    products = {this.state.products}
                    enableEditForProduct = {this.enableEditForProduct}
                    removeProductFromCart = {this.removeProductFromCart}
                    getIndividualPricingDetails = {this.getIndividualPricingDetails}
                    showConfigurationsByTypeForListInfra = {this.showConfigurationsByTypeForListInfra}
                    />
                 </div> : (null)
                */}

                            {
                                this.state.products.length || this.state.addInfraEnabled ?
                                    (null) :
                                    <div className="col-md-12 col-lg-12">
                                        {/*<EmptyCart setAddInfraEnabled={this.setAddInfraEnabled}/>*/}
                                    </div>
                            }

                            <Modal className="customModalWrapper" isOpen={this.state.isOpen} onRequestHide={this.hideModal}>
                                <ModalHeader>
                                    <ModalClose onClick={this.hideModal}/>
                                    <ModalTitle>Warning</ModalTitle>
                                </ModalHeader>
                                <ModalBody>
                                    <h3>If you change this step's data, the subsequent step's data will be erased</h3>
                                </ModalBody>
                                <ModalFooter>
                                    <input type="submit" onClick= {this.clearData}
                                        className="darkPinkBtn pull-left" value="Yes, Erase my data "/>
                                    <input type="submit" className="darkPinkBtn pull-left"
                                        value="Cancel, Restore my data" onClick={this.restoreData}/>
                                </ModalFooter>
                            </Modal>

                            <Modal className="customModalWrapper" isOpen={this.state.isOpenBillGroup} onRequestHide={this.hideModalBillgroup}>
                                <ModalHeader>
                                    <ModalClose onClick={this.hideModalBillgroup}/>
                                    <ModalTitle>Warning</ModalTitle>
                                </ModalHeader>
                                <ModalBody>
                                    <h3>Please select all products that are {this.state.billgroup}, or change the selected project</h3>
                                </ModalBody>
                                <ModalFooter>
                                    <input type="submit" className="darkPinkBtn pull-left"
                                        value="Ok" onClick={this.hideModalBillgroup}/>
                                </ModalFooter>
                            </Modal>
                        </div>
                    </div>
                </section>
            </div>
        )
    }
});

export default NewShoppingCart;
