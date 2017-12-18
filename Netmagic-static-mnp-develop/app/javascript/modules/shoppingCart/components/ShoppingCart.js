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
import AddEditInfraProduct from './AddEditInfraProduct';
import AssistanceBlock from'./AssistanceBlock';
import ListInfraProducts from './ListInfraProduct';
import EmptyCart from './EmptyCart';
import PaymentDone from './PaymentDone';
import shoppingCart from '../../../services/shoppingCart';
import Select from 'react-select';
import { Modal, ModalHeader, ModalTitle, ModalClose, ModalBody, ModalFooter} from 'react-modal-bootstrap';

var ShoppingCart = createReactClass({
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
        pricingObj : {},
        projects : [],
      },
      globalData : Session.get('globalData') || {},
      loaded: {
        productList: false,
        subSubCategories: false,
        pricing: false
      },
      defaultLocations : [],
      addInfraEnabled : false,
      activeStep: firstStep,
      maxStepNumber: firstStep.value,
      locations: [],
      subCategories: [],
      subSubCategories: [],
      productList: [],
      isOpen: false,
      isOpenBillGroup: false,
      billgroup: '',
      savePointData : {},
      error : {},
      pricingObjectsForProducts : []
    }
  },

  componentWillMount() {
    this.getLocations();
    this.getSubCategories();
    this.getProjects();
    this.state.loaded.productList = false;
    //delete below code after api for getCartData is ready
    this.state.products = Session.get('tempCartData') || []
    if(_.isEmpty(this.state.products)){
      Session.remove('globalData');
    }
    var index = Session.get('tempCartIndex') || ''
    if(_.isNumber(index))
    {
      this.enableEditForProduct(index)
    }
    Session.remove('tempCartIndex')
    this.setState(this.state)
        this.reCalculateTotalPrice();
    //this.getCartData();
  },

  componentDidMount() {

  },

  openModal (nextStepNumber) {
    this.setState({isOpen: true, nextStepNumber: nextStepNumber});
  },

  hideModal (event) {
    this.setState({ isOpen: false});
  },

  hideModalBillgroup (event){
     this.setState({ isOpenBillGroup: false});
  },

  getProjects() {
    var customer = Session.get('mainCustomer');
    var associated = Session.get('associatedCustomer');

    if(this.isLoggedIn() && !_.isEmpty(customer)) {
        var params = {
          id: customer && customer.customerId,
          associateCustomerId: associated ? associated.customerId : customer.customerId,
        };
        shoppingCart.getProjectsFromAPI(params)
          .then((result) => {
          var projects =
              _.map(result, (project)=> {
                  return { label: project.projectName, value: project.projectName, id : project.projectId, sugarId : project.projectSugarId};
              });
          this.state.editProduct.projects = projects;
          this.setState(this.state)
        })
        .catch((err) => {
            UI.notifyError( err && err.statusText);
        });
    }


    //////// Code for reference , Below code would fetch projects from Local Session Data
    // var associatedCustomer = Session.get('associatedCustomer');
    // if(associatedCustomer && associatedCustomer.projects) {
    //   var projects = associatedCustomer.projects
    //     return _.map(projects, (project)=> {
    //         return { label: project.projectName, value: project.projectName, id : project.projectId};
    //   });
    // }
    // else {
    //   return [];
    // }
  },

  // Function has been temporarily deprecated
  getLocations() {
    shoppingCart.getData(CONSTANTS.API_URLS.shoppingCart.locations)
    .then((result) => this.setState({ defaultLocations: result.items }))
    .catch(function(error) {
      UI.notifyError( error && error.statusText)
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
    .catch(function(error) {
      UI.notifyError( error && error.statusText)
    }.bind(this));
  },

  getSubSubCategories(subCategoryIdParam) {
    this.state.loaded.subSubCategories = true;
    this.setState(this.state)
    var url = http.buildRestUrl(CONSTANTS.API_URLS.shoppingCart.subSubCategories, { subCategoryId:subCategoryIdParam })
    shoppingCart.getData(url)
    .then((result) => {
      this.state.loaded.subSubCategories = true;
      this.state.subSubCategories = result.items;
      this.setState(this.state)
    })
    .catch(function(error) {
      UI.notifyError( error && error.statusText)
    }.bind(this));
  },

  getProducts() {
    var subSubCategoryIdParam = this.state.editProduct.planDetails.planid;
    if(subSubCategoryIdParam)
    {
      this.state.loaded.productList = false
      this.setState(this.state)
      var location =  _.findWhere(this.state.defaultLocations,
      { locname: this.state.globalData.selectedLocation });
      var locationId = (location && location.loc);
      //this.getLocationId(this.state.globalData.selectedLocation);
      var isCapped = this.state.globalData.isCappedProduct || ''
      var plan = this.state.globalData.VariableProductUOM || ''
      var url = http.buildRestUrl(CONSTANTS.API_URLS.shoppingCart.products, { subCategoryId:subSubCategoryIdParam })
      url = url + '?isCapped='+isCapped;
      url = http.appendUrl(url,{plan : plan, location:locationId})
      shoppingCart.getData(url)
      .then((result) => {
        if(result.items)
        {
          this.state.productList = result.items
          //added deep newDesignScene
          _.each(this.state.productList, (obj, i) => {
            obj.clicked = false;
          })
          this.state.error.productList = "";
        }
        else{
          this.state.error.productList = CONSTANTS.UI_MESSAGES.shoppingCart.productListError;
        }
        this.state.loaded.productList = true;
        this.setState(this.state)

      })
      .catch(function(error) {
        this.state.loaded.productList = true;
        this.setState(this.state)
        UI.notifyError( error && error.statusText)
      }.bind(this));
    }
    else
    {
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
            if(_.isEqual(editProductObject.pricingGroup,pricingGroupElement)) {
              pricingGroupArray[i].push(editProductObject);
              flag = true;
            }
            i++;
        });
        if(!flag)
        {
          pricingGroupArrayIdentifiers.push(editProductObject.pricingGroup)
          pricingGroupArray[i] = [];
          pricingGroupArray[i].push(editProductObject)
        }
    });
    //pricingGroupArrayIdentifiers --> holds the group parameters
    return pricingGroupArray;
  },

  getPricingObject(editProductObjects)  {
    var default_option = _.first(editProductObjects);
    if(default_option)
    {
      var itemArray=[];
      _.each(editProductObjects, (editProductObject) => {
        itemArray.push(this.generateItemListArray(editProductObject))
      });
      var userData = User.getCustomerParams()
      return {
        acc : '1072',
        cntrperiodunit : 'months',
        suppto : 'a3740cd8-2c34-db77-9a19-51a362c20341',
        token : null,
        cntrperiod : default_option.period.contractPeriod,
        billgroup : [{
          projectname : default_option.period.selectedProject || '',
          projectSugarId : this.state.globalData.selectedProjectSugarId || '',
          billtoadd : 'c0164e26-090a-2ac3-2267-542a8e1ef53d',//userData.customerSugarId
          supptoadd : 'be6198de-e5c2-e3f0-b461-542a8e56e423',//userData.assoCustomerSugarId
          bill_cont : '19f005c7-9058-aa73-4feb-5444b6ab4268',
          spoc_cont : '19f005c7-9058-aa73-4feb-5444b6ab4268',
          tech_cont : '2444404a-4c50-ae58-5ea3-51baf56042b6',
          auth_sign : '2444404a-4c50-ae58-5ea3-51baf56042b6',
          items : itemArray
        }]
      }
    }
    else
    {
      return [];
    }
  },

  deriveVariableProductUOM(configuration) {
     var type;
     type = false;
     var spuomArray = _.pick(configuration.sprod, 'spuom');
     if (_.isArray(configuration.sprod)) {
       type = _.find(configuration.sprod, (sprod) => sprod.spuom === 'GB' || sprod.spuom === 'MB');
     }
     return type.spuom || false;
  },

  generateItemListArray(editProductObject) {
    var location = _.findWhere(this.state.defaultLocations,
      { locname: editProductObject.location.selectedLocation });
    var locationId = location && location.loc;
    var configuration = _.omit(editProductObject.configuration,'subCategory','subSubCategory','locationDetails','isOnlineProduct','uom') ;
    var variableProductUom;
    configuration.isExistingProject =   this.state.globalData.selectedProjectIsNew ? 'Yes' : 'No' ; //CONSTANTS.SHOPPING_CART_VARIABLES.NO;
    if(!this.state.globalData.selectedProjectIsNew){
        configuration.projectid = this.state.editProduct.period.selectedProjectId
    }
    if (configuration.isVariableProduct === CONSTANTS.SHOPPING_CART_VARIABLES.YES){
        configuration.VariableProductUOM = this.deriveVariableProductUOM(configuration)
    }
    configuration.loc = locationId;
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
     shoppingCart.getPricing(url, pricingObject)
     .then((result) => {
        if(result.text === 'success')
        {
          this.state.editProduct.pricingObj = result.data;
          this.state.editProduct.pricingGroup.contractPeriod = this.state.editProduct.period.contractPeriod;
          this.state.editProduct.pricingGroup.locationid = this.getLocationId(this.state.editProduct.location.selectedLocation);
          this.state.editProduct.pricingGroup.project = this.state.editProduct.period.selectedProject;
          this.state.error.pricing = "";
        }
        else{
          this.state.error.pricing = CONSTANTS.UI_MESSAGES.shoppingCart.pricingError;
        }
        this.state.loaded.pricing = false;
        this.setState(this.state)
      })
      .catch(function(error) {
        UI.notifyError( CONSTANTS.UI_MESSAGES.shoppingCart.pricingError && error.statusText)
      }.bind(this));
  },

  reCalculateTotalPrice() {
     var pricingObject = this.getPricingObject(this.state.products);
     if(_.isEmpty(pricingObject))
     {
       this.state.totalPrice = 0;
       this.setState(this.state)
     }
     else{
        var url = CONSTANTS.API_URLS.shoppingCart.pricing;
        shoppingCart.getPricing(url, pricingObject)
        .then((result) => {
            if(result.status === 'Success')
            {
              this.state.totalPrice = result.data;
            }
            else{
              this.state.error.pricing = CONSTANTS.UI_MESSAGES.shoppingCart.pricingError;
            }
            this.setState(this.state)
        })
        .catch(function(error) {
          UI.notifyError( CONSTANTS.UI_MESSAGES.shoppingCart.pricingError && error.statusText)
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
    this.setState(this.state)
  },

  activeStepFunc(step) {
    if(this.state.maxStepNumber < step.value)
    {
      if(!(_.isEqual(this.state.editProduct,this.state.savePointData)) && step.value === 3)
      {
        this.openModal(step.value);
      }
      else{
        UI.notifyError( CONSTANTS.UI_MESSAGES.shoppingCart.navigationError)
      }
    } else {
      this.setActiveStep(step.value);
    }
  },

  produceSavePointData()  {
      this.state.savePointData = Utility.clone(this.state.editProduct);
      this.setState(this.state)
  },

  clearData() {
    var editProduct = this.state.editProduct;
    var stepNumber = this.state.activeStep.value;
    if(stepNumber === 2) {
      _.extend(editProduct, { planDetails: {}, configuration: {}, pricingGroup: {}, pricingObj: {} })
    }
    var step = _.findWhere(CONSTANTS.SHOPPING_CART_PURCHASE_TABS, { value: stepNumber });
    editProduct.location.product = null;
    this.setState({ editProduct: editProduct ,
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
    if(step.value > this.state.maxStepNumber){
       max= step.value
    }
    else {
      max = this.state.maxStepNumber;
    }
    this.produceSavePointData();
    this.setState({
      activeStep: step ,
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
    if(subKey === 'selectedLocation') {
      this.state.error = {};
      //On Change of Location, the below senario is no longer required
      //this.state.editProduct.planDetails.planid = "";
      //this.state.editProduct.planDetails.plan = "";
    }
    else if(subKey === 'selectedProject' && !this.state.editProduct.period.selectedProjectIsNew){
      //var projects = this.getProjects()
      var projects = this.state.editProduct.projects
      var project = _.findWhere(projects,
      { label: value });
      var projectId = project && project.id;
      this.state.editProduct.period.selectedProjectId = projectId
      this.state.globalData.selectedProjectSugarId = project && project.sugarId
    }
    this.setState(this.state);
  },

  addToCart() {
      this.state.products.push(this.state.editProduct);
      //setting Global Data ContractPeriod
      this.state.globalData.contractPeriod = this.state.editProduct.period.contractPeriod;
      //setting Global Data Location
      this.state.globalData.selectedLocation = this.state.editProduct.location.selectedLocation
      //setting Global Data Project
      var projects = this.state.editProduct.projects
      var project = _.findWhere(projects,
      { label: this.state.editProduct.period.selectedProject });
      var projectId = project && project.id;
      this.state.globalData.selectedProjectIsNew = this.state.editProduct.period.selectedProjectIsNew;
      this.state.globalData.project = this.state.editProduct.period.selectedProject;
      this.state.globalData.projectId = projectId
      Session.set('globalData',this.state.globalData);
      Session.set('tempCartData',this.state.products)//work around for now
      this.saveCartData(this.state.products);
      this.state = this.refreshEditProduct(this.state)
      this.state.subSubCategories = [];
      this.state.productList = [];
      this.state.error = {};
      this.state.savePointData = {};
      this.setState(this.state)
      this.handleGlobalData();
      this.reCalculateTotalPrice();
  },

  saveCartData(cartList)  {
    Session.set('tempCartData',cartList);
    shoppingCart.saveCart(CONSTANTS.API_URLS.shoppingCart.saveCart, cartList)
      .then(function(res) {
        // TODO : api currently has a problem, assign received data to state.products []
      }.bind(this))
      .catch(function(err) {
        // TODO : api currently has a problem, assign received data to state.products []
      }.bind(this));
  },

  getCartData() {
    shoppingCart.getData(CONSTANTS.API_URLS.shoppingCart.getCart)
    .then((result) => console.log(result))// TODO : api currently has a problem, assign received data to state.products []
    .catch(function(error) {
      UI.notifyError( error && error.statusText)
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
        pricingObj : {},
      }
      state.activeStep = firstStep;
      state.maxStepNumber = firstStep.value
      return state;
  },

  onRadioProductSelect(obj) {
      this.clearPricingObject();
      this.getSubSubCategories(obj.subcatid)
      if(_.isEmpty(this.state.products) || this.state.products.length === 0) {
        this.state.globalData = {};
      }
      this.state.editProduct.location.product =  obj.subcatname;
      this.state.editProduct.location.productid =  obj.subcatid;
      this.state.productList = [];
      this.state.error = {};
      this.setState(this.state)
  },

  onRadioPlanSelect(obj) {
      this.clearPricingObject();
      this.state.editProduct.planDetails.plan =  obj.subsubcatname;
      this.state.editProduct.planDetails.planid =  obj.subsubcatid;
      this.state.editProduct.configuration = {};
      this.state.productList = [];
      this.state.error = {};
      this.setState(this.state)
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
      _.each(this.state.productList,(singleProduct) => {
        _.isEqual(_.first(singleProduct.sugar),obj) ? singleProduct.clicked = true : singleProduct.clicked = false  
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
      if(this.state.addInfraEnabled)
      {
          UI.notifyError( CONSTANTS.UI_MESSAGES.shoppingCart.openCartError)
      }
      else{
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

  handleGlobalData(){
    var products = this.state.products;
    if (_.isEmpty(products || products.length === 1)) {
          this.state.globalData = {}
          Session.remove('globalData');
          this.setState(this.state)
    }
    else {
      var variableProductUOM='';
      this.state.products.forEach(function (product, i) {
          if (product.configuration.VariableProductUOM === 'GB' || product.configuration.VariableProductUOM ==='MB')
          {
            variableProductUOM = product.configuration.VariableProductUOM
          }
      });
      this.state.globalData.VariableProductUOM = variableProductUOM;
      this.setState(this.state.globalData)
      Session.set('globalData',this.state.globalData);
    }
  },

  removeProductFromCart(productIndex) {
      this.state.products.splice(productIndex, 1);
      Session.set('tempCartData',this.state.products)
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
                <span>{_.findWhere(data.attvals, {isdef: 'Yes'})
                  && _.findWhere(data.attvals, {isdef: 'Yes'}).attval}
                    {data.spuom}
                </span>
              </div>
              )
          })}
        </span>
        );
  },

  showBundledConfigForListInfra(subProduct) {
    return (<div >
      {_.map(subProduct, (data, outerIndex) => {
        //if config attribute is a nested attribute
        if(data.atts) {
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
                          ( chosenOne.selqty )
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
      })}
    </div>)
  },

  purchaseCart() {
    if(!this.isLoggedIn()) {
        this.context.router.push('/MyNetmagic');
        return;
    }
    //check session--> open Sign iup
    this.state.pricingObjectsForProducts = []
    var groupedProducts = this.generatePricingGroupArray(this.state.products);
    var i = 0;
    var urlbill=CONSTANTS.API_URLS.shoppingCart.billingGroup
    var sessionData = User.getCustomerParams()
    var params = {
      customerId:sessionData.customerId,
      assoCustomerId:sessionData.assoCustomerId,
      projectId:this.state.globalData.projectId,
    }
    shoppingCart.getBillingGroup(urlbill, params)
      .then((result) => {
        if(result.billingGroupNumber) {
          this.state.globalData.selectedProjectIsNew = 'Yes'
        }
        else {
          this.state.globalData.selectedProjectIsNew = 'No'
        }
        this.setState(this.state)
        result.unitOfMeasurment = result.unitOfMeasurment || ''
        if(result.unitOfMeasurment === this.state.globalData.VariableProductUOM || result.unitOfMeasurment === "" )        {
          _.each(groupedProducts, (groupedProduct) => {
            var pricingObject = this.getPricingObject(groupedProduct);
            var url = CONSTANTS.API_URLS.shoppingCart.pricing;
            shoppingCart.getPricing(url, pricingObject)
              .then((result) => {
                if (result.text === 'success') {
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
                UI.notifyError( CONSTANTS.UI_MESSAGES.shoppingCart.pricingError && error.statusText)
              }.bind(this));
          });
        }
        else
        {
          this.state.isOpenBillGroup = true;
          this.state.billgroup = result.billingGroupTypes
          this.setState(this.state)
        }
    });
  },
  
  render () {
    var mainCustomer = Session.get('mainCustomer')
    var quckLinkClass = _.isEmpty(mainCustomer) ? 'hide' : '';
    return (
      <section className="ticketListSection outStandingWrap peopleWrap shoppingCartWrap">
        <div className="blackOverlay"></div>
        <section className="midHeader shoppingCartHeader">
          <div className="container">
            <div className="row">
              <div className="col-xs-8 col-sm-8 col-md-8">
                <ol className={"breadcrumb breadCrumbCustom clearfix "+ quckLinkClass}>
                  <li><Link to={User.getSplashScreenURL()}>Back to Home</Link></li>
                  <li>
                    <Link to="dashboard" className={"blueLink"}>Back to Dashboard</Link>
                  </li>
                  <li className="active">Shopping Cart</li>
                </ol>
                <h1 className="mainTitle">Shopping Cart</h1>
              </div>
              {
                  (this.state.products.length > 0 && !this.state.addInfraEnabled ) ?
                  <div className="col-xs-4 col-sm-4 col-md-4 pull-right">
                    <span onClick={this.setAddInfraEnabled} className="addInfraBtn">Add new Infra</span>
                    <span onClick={this.purchaseCart} className="placeOrderBtn ">Place Order</span>
                  </div>
                  : (null)

                }
            </div>
          </div>
        </section>

        <div className="container shoppingCartContainer">
          <div className="row">
                {this.state.addInfraEnabled ? (
                  <div className="col-md-9 col-lg-9 purchaseBlock">
                    <AddEditInfraProduct
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
                    addToCart = {this.addToCart}
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
                    locations = {this.state.locations}
                    error = {this.state.error}
                    loaded = {this.state.loaded}
                    getIndividualPricingDetails = {this.getIndividualPricingDetails}
                    initiateCreateProject = {this.initiateCreateProject }
                    clearPricingObject = {this.clearPricingObject}
                    getProjects = {this.getProjects}
                    isOpenBillGroup = {this.state.isOpenBillGroup}
                    hideModalBillgroup = {this.hideModalBillgroup}
                    products = {this.state.products}
                    maxStepNumber = {this.state.maxStepNumber} />
                  </div>
                  ) : (null)
                }

                {
                 this.state.products.length > 0 || this.state.addInfraEnabled ?
                 <AssistanceBlock
                 product = {this.state.products}
                 totalPrice = {this.state.totalPrice}
                 /> : (null)
                }

                {
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
                }

                {
                 this.state.products.length || this.state.addInfraEnabled ?
                 (null) :
                 <div className="col-md-12 col-lg-12">
                    <EmptyCart setAddInfraEnabled={this.setAddInfraEnabled}/>
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
    )
  }
});

export default ShoppingCart;
