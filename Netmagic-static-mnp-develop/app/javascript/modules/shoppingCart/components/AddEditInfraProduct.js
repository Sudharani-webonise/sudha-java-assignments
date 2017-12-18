import { Link } from 'react-router';
import CONSTANTS from '../../../constants/app-constant';
import Session from '../../../mixins/sessionUtils';
import Utility from '../../../mixins/basicUtils';
import UI from '../../../mixins/ui';
import Select from 'react-select';
import { Modal, ModalHeader, ModalTitle, ModalClose, ModalBody, ModalFooter} from 'react-modal-bootstrap';
import Loader from 'react-loader';
import LoaderOptions from '../../../mixins/loader';
import BeforeRequestPricing from './BeforeRequestPricing';
import AfterRequestPricing from './AfterRequestPricing';

var AddEditInfraProduct = createReactClass({
  mixins: [Utility, UI],
  contextTypes: {
    router: React.PropTypes.object
  },

  getInitialState() {
    return {
      selectValue: null,
      error: {},
      checkedStatus: false,
      cancelModalDisplay: false,
    }
  },

  activeStepFunc(step) {
    this.props.activeStepFunc(step)
  },

  setAddInfraDisabled(){
    this.props.setAddInfraDisabled();
  },

  showStepContent() {
    var resultStep = (null);
    var activeStep = this.props.activeStep;
    var mapper= {
      1: 'renderPeriod',
      2: 'renderLocation',
      3: 'renderPlanDetails',
      4: 'renderConfiguration',
      5: 'renderProductPreviewStep'
    };
    return this[mapper[activeStep.value]]();
  },

  handlePeriod() {
    var {contractPeriod} = this.props.editProduct.period;
    var updatedError = {};
    var flag = true;
    if (!contractPeriod) {
      updatedError.contractPeriod = CONSTANTS.UI_MESSAGES.emptyFields;
    }
    this.setState({ error: updatedError });
    if (updatedError.contractPeriod) {
      return false
    }
    return true
    //   this.props.handleNext(2);
    // } else {
    //   this.props.handleNext(1);
    // }
  },

  updateContractPeriod(value) {
    this.props.updateDropDown('period', 'contractPeriod', value)
  },

  updateSelectProject (value) {
    this.props.updateDropDown('period', 'selectedProject', value)
  },

  updateNewProject(e)  {
    this.props.updateDropDown('period', 'selectedProject', e.target.value)
  },

  getMonths() {
    return _.map(_.range(1,CONSTANTS.MAX_MONTHS), (rangeValue)=> {
      return{ label: rangeValue, value: rangeValue};
    });
  },

  initiateCreateProject() {
    this.props.initiateCreateProject(true);
  },

  unInitiateCreateProject() {
    this.props.initiateCreateProject(false);
  },

  animateToTopofEditCart() {
      $('html, body').animate({
          scrollTop: $("#scrollTo").offset().top
      }, 1000);
  },

  renderPeriod() {
    let months = this.getMonths();
    return (
      <div className="periodSelectionContainer">
        <div className="iconSection pull-left">
          <i className="periodIcon"></i>
          <div className="shoppingMsg">
            <h3>Lorem Ipsum dolor sit amet</h3>
            <p>Lorem Ipsum dolor sit amet Lorem Ipsum dolor sit amet Lorem Ipsum dolor sit amet</p>
          </div>
        </div>

        <div className="clear"></div>
        <div className="navigationBtns">
          <span className="cancelBtn pull-left" onClick={this.openModal}>Cancel</span>
          <span className="nextBtn pull-right" onClick={this.handlePeriod.bind(null, true)}>Next ></span>
          <div className="clear"></div>
        </div>
      </div>
    )
  },

  handleLocation() {
    //this.animateToTopofEditCart();
    var {product} = this.props.editProduct.location;
    var updatedError = {};
    var flag = true;
    if (!product) {
      updatedError.product = CONSTANTS.UI_MESSAGES.emptyFields;
    }
    this.setState({ error: updatedError });
    if (updatedError.product) {
      return false
    }
    return true;
    //     this.props.handleNext(3);
    // } else {
    //     this.props.handleBack(1);
    // }
  },

  onChangeSelectedLocation(value) {
      this.props.updateDropDown('location', 'selectedLocation', value)
  },

  onRadioProductSelect(obj) {
    this.props.onRadioProductSelect(obj)
  },

  getProductClassSet(condition) {
    return condition ? 'productType checkedType text-center': 'productType uncheckedType text-center';
  },

  getProductIcon(productType){
    var iconClassName = _.findWhere(CONSTANTS.PRODUCT_TYPE,{ label: productType })
    var updatedClass = iconClassName.value || 'virtualServerIcon' ;
    return(
        <i className={ updatedClass + ' ' + 'productIcon'} ></i>
    )
  },

  renderLocation() {

    var editProduct = this.props.editProduct;
    return (
      <div className="locationSelectionContainer">
        <div className="locationListing">
           <div className="clear"></div>
           <div className="selectProducts">
                <h3>Select your Product</h3>
                <span className="errorMsg">{this.state.error.product}</span>
                  <div className="productList">
                      { _.map(this.props.subCategories, (obj,i) => {
                        return (<div key={i} className={this.getProductClassSet(editProduct.location.product ===  obj.subcatname)}
                            onClick={this.onRadioProductSelect.bind(null, obj)}>
                            {this.getProductIcon(obj.subcatname)}
                          <span className="productName">{obj.subcatname}</span>
                          <span className="redCheck"></span>
                        </div>)
                      })}
                  </div>
           </div>
           {
             /**
              <div className="navigationBtns">
                <span className="cancelBtn pull-left" onClick={this.openModal}>Cancel</span>
                <span className="nextBtn  pull-right" onClick={this.handleLocation.bind(null, true)}>Next ></span>
                <div className="clear"></div>
              </div>
              */
           }

       </div>
    </div>


    )
  },

  handlePlanDetails() {
    //this.animateToTopofEditCart();
    var updatedError = {};
    var flag = true;
    if (!this.props.editProduct.configuration.pdesc)
    {
        updatedError.configuration = CONSTANTS.UI_MESSAGES.emptyFields;
    }
    if(!this.props.editProduct.planDetails.plan)
    {
        updatedError.planDetails = CONSTANTS.UI_MESSAGES.emptyFields;;
    }
    this.setState({error: updatedError});
    if(updatedError.configuration || updatedError.planDetails)
    {
        return false;
    }
    return true;
    // this.props.handleNext(4);
    // } else {
    //     this.props.handleBack(2);
    // }
  },


  onRadioPlanSelect(obj) {
    this.props.onRadioPlanSelect(obj)
  },

  updateSystemConfigurations(obj, checkedStatus) {
    this.props.updateSystemConfigurations(obj);
  },

  updateSelectedListOfConfiguration(obj) {
    this.props.updateClickedForConfigurationItem(obj)
  },

  getListOfConfiguration() {
     return ( _.map(this.props.productList, (obj, i) => {
        return (
           <Product
           updateSelectedListOfConfiguration = {this.updateSelectedListOfConfiguration}
           clicked = {obj.clicked}
           key={i}
           showConfigurationsByTypeForPreview={this.showConfigurationsByTypeForPreview}
           editProduct = {this.props.editProduct}
           updateSystemConfigurations={this.updateSystemConfigurations}
           renderConfiguration = {this.renderConfiguration}
           obj={obj} key={i}/>
          )
      }))
  },

  getPlanClassSet(condition) {
    return condition ? 'planType checkedType text-center': 'planType uncheckedType text-center';
  },

  showConfigurationsByTypeForPreview(type, subProduct) {
    var configView = (null);
    const types = CONSTANTS.PRODUCT_TYPES;
    switch (type) {
      case types.bundled:
        configView = this.showBundledConfigForPreview(subProduct);
        break;
      case types.withAttributes:
        configView = this.showWithAttributesConfigForPreview(subProduct)
        break;
      case types.standard:
        configView = (null);
        break;
    }
    return configView;
  },

  showWithAttributesConfigForPreview(subProduct) {
    var attributes = _.first(_.values(subProduct));
      return (
        <div>
        {_.map(attributes && attributes.atts, (data, outerIndex) => {
          return (
            <div key={outerIndex} className="listDetails">
                <label>{data.attname}</label>
                <span>{_.findWhere(data.attvals, {isdef: CONSTANTS.SHOPPING_CART_VARIABLES.YES})
                  && _.findWhere(data.attvals, {isdef: CONSTANTS.SHOPPING_CART_VARIABLES.YES}).attval}
                      {data.spuom}
                </span>
              </div>
              )
          })}
        </div>
        );
  },

  showBundledConfigForPreview(subProduct) {
    return (<div >
      {_.map(subProduct, (data, outerIndex) => {
        //NOTE : if config attribute is a nested attribute
        if(data.atts) {
          var attribute = _.first(_.values(data.atts));
          var defaultValueId = attribute.defid;
          var chosenOne = attribute.attvals[defaultValueId];
          //NOTE : data.spdesc --> label : chosenOne --> value
          return (
                <div key={outerIndex} className="listDetails">
                  <label>{data.spdesc}</label>
                  <span>{chosenOne && chosenOne.attval} :
                        {
                          (chosenOne.appqty && (chosenOne.appqty.length > 1)) ?
                          chosenOne && chosenOne.selqty :(null)
                        }
                  </span>
                </div>
          )
        } else {
          return (
            <div className="listDetails">
              <label>{data.spdesc}</label>
              <span>{data.spqty}</span>
            </div>
          )
        }
      })}
    </div>)
  },

  renderPlanDetails() {
    let editProduct =this.props.editProduct;
    let productList = this.props.productList;
    let product = editProduct.location.product;
    return (
       <div className="planSelectionContainer">
       {
         /**
             <div className="planUpperList">
                 <div className="listDetails">
                   <label>Product Type</label>
                   <span>{product ? product : 'NA'}</span>
                 </div>
             </div>
          */
       }
            <div className="planListing">
               <h3>Choose your plan</h3>
               <span className="errorMsg">{this.state.error.planDetails}</span>
               <div className="planTypeList">
                    { _.map(this.props.subSubCategories, (obj,i) => {
                       return (<div key={i} className={this.getPlanClassSet(obj.subsubcatid === editProduct.planDetails.planid)}
                       onClick={this.onRadioPlanSelect.bind(null, obj)}>
                         <i className="planIcon"></i>
                         <span className="planName">{obj.subsubcatname}</span>
                         <span className="redCheck"></span>
                       </div>)
                    })}
               </div>
               <h3>Choose System Configuration</h3>
                <div className="systemConfigurationsList">
                  <span className="errorMsg">{this.state.error.configuration}</span>
                    <ul className="" id="listOfConfiguration">
                      {_.isArray(productList) && productList.length ? this.getListOfConfiguration() : <span> {CONSTANTS.UI_MESSAGES.shoppingCart.configurationListError} </span>}
                    </ul>
                </div>
                {
                  /**
                    <div className="navigationBtns">
                      <span className="cancelBtn pull-left" onClick={this.openModal}>Cancel</span>
                      <span className="nextBtn  pull-right" onClick={this.handlePlanDetails.bind(null, true)}>Next ></span>
                      <span className=" backBtn pull-right" onClick={this.handlePlanDetails.bind(null, false)}>Back </span>
                      <div className="clear"></div>
                    </div>
                   */
                }
            </div>
        </div>
    )
  },

  handleConfiguration() {
    //this.animateToTopofEditCart();
    var flag = true;
    var updatedError = {};

    if(_.isEmpty(this.props.editProduct.pricingObj))
    {
        updatedError.pricingObj = CONSTANTS.UI_MESSAGES.shoppingCart.pricingObjError;
        this.setState({error: updatedError});
        return false
    }
    // if( updatedError.pricingObj)
    // {
    // }
//else {
//         this.props.handleNext(5);
//     }
// } else {
//   this.props.handleBack(3);
    return true;
  },


  updateConfigurationOption(index,value) {
    this.props.updateConfigurationOption(index,value);
  },

  updateNestedConfigurationOptionWithAttribute(index, newDefId, innerIndex) {
    var sprod = this.props.editProduct.configuration.sprod;
    sprod[_.first(Object.keys(sprod))].atts[index].defid = newDefId.attvid;
    var attVals = sprod[_.first(Object.keys(sprod))].atts[index].attvals
    var attvalToUpdate = _.findWhere(attVals, {attvid: newDefId});
    _.each(attVals, (value, key, obj) => {
      value.isdef = value.attvid===newDefId.attvid ? CONSTANTS.SHOPPING_CART_VARIABLES.YES : CONSTANTS.SHOPPING_CART_VARIABLES.NO;
    });
    this.props.updateConfigurationAttribute(sprod);
  },

  updateNestedConfigurationOption(index, newDefId,innerIndex) {
    var sprod = this.props.editProduct.configuration.sprod;
    _.first(_.values(sprod[index].atts)).defid = newDefId;
    var attVals = Utility.getNestedValue(sprod[index].atts, 'attvals');
    var attvalToUpdate = _.findWhere(attVals, {attvid: newDefId});
    _.each(attVals, (value, key, obj) => {
    value.isdef = value.attvid===newDefId ? CONSTANTS.SHOPPING_CART_VARIABLES.YES : CONSTANTS.SHOPPING_CART_VARIABLES.NO;
    });
    this.props.updateConfigurationAttribute(sprod);
  },

  updateInnerNestedConfigurationOption(index, newQty) {
    var sprod = this.props.editProduct.configuration.sprod;
    var attr = _.first(_.values(sprod[index].atts));
    var optionToUpdate = attr.attvals[attr.defid];
    optionToUpdate.selqty = newQty;
    this.props.updateConfigurationAttribute(sprod);
  },

  getSlicedName(projectName,uom){
    projectName = projectName + uom;
    if(projectName){
      return ( projectName.length > CONSTANTS.LABEL_NAME_LIMIT ?
        projectName.slice(0 , CONSTANTS.LABEL_NAME_SUBSTR) + '...' : projectName );
    }
  },

  showNameOnHover(projectName, uom){
    projectName = projectName + uom;
    var isNameLong =  ( projectName.length > CONSTANTS.LABEL_NAME_LIMIT);
    return this.classSet({
      labelHoverData : isNameLong,
      hide: !isNameLong
    });
  },

  showBundledConfig(subProduct) {
    return (<div className="configDetailsBlock">
      {_.map(subProduct, (data, outerIndex) => {
        // NOTE :if config attribute is a nested attribute
        if(data.atts) {
          var attribute = _.first(_.values(data.atts));
          var defaultValueId = attribute.defid;
          var chosenOne = attribute.attvals[defaultValueId];
          return (<div key={outerIndex} className = "col-lg-6 configurationDetails">
            <div className="customSelectGroup">
              <label className="labelData">{this.getSlicedName(data.spdesc,data.spuom)}</label>
              <label className={this.showNameOnHover(data.spdesc,data.spuom)}>{data.spdesc} in {data.spuom}</label>
              <ul className="customSelectList accountTypeList">
                { _.map(attribute.attvals, (value, index) => (<li key={index}
                  onClick={this.updateNestedConfigurationOption.bind(null, outerIndex, value.attvid,index)}
                  value={value}>{value && value.attval}</li>))
                }
              </ul>
              <p className="selectContent">{chosenOne && chosenOne.attval}</p>
            </div>
            {/*if value chosen from the first drop down have sub-attributes*/}
            {chosenOne.appqty && (chosenOne.appqty.length > 1) ?
              (<div className="customSelectGroup">
                <ul className="customSelectList accountTypeList">
                  { _.map(chosenOne.appqty, (value, index) => (<li key={index}
                    onClick={this.updateInnerNestedConfigurationOption.bind(null,outerIndex, value)}
                    value={value}>{value} {data.spuom}</li>))
                  }
                </ul>
                <p className="selectContent">{chosenOne && chosenOne.selqty} {data.spuom}</p>
              </div>):
              (null)
            }
          </div>)
        } else {
          return (<div className = "col-lg-6 configurationDetails">
            <div className="customSelectGroup">
              <label className="labelData">{this.getSlicedName(data.spdesc,data.spuom)}</label>
              <label className={this.showNameOnHover(data.spdesc,data.spuom)}>{data.spdesc} in {data.spuom}</label>
              <ul className="customSelectList accountTypeList">
                { _.map(data.appqty, (value,index) => (<li key={index}
                  onClick={this.updateConfigurationOption.bind(null,outerIndex,value)}
                  value={value}>{value}</li>))
                }
              </ul>
              <p className="selectContent">{data.spqty} {data.spuom}</p>
            </div>
          </div>)
        }
      })}
    </div>)

  },

  showWithAttributesConfig(subProduct) {
    var attributes = _.first(_.values(subProduct));
      return (<div className=" configDetailsBlock">
        { _.map(attributes && attributes.atts, (data, outerIndex) => {
          return (<div data-attribute-id={data.attid} className ="col-lg-6 configurationDetails" key={outerIndex}>
            <div className="customSelectGroup pull-left">
              <label className="labelData">{this.getSlicedName(data.attname,data.spuom)}</label>
              <label className={this.showNameOnHover(data.attname,data.spuom)}>{data.attname} in {data.spuom}</label>
              <ul className="customSelectList accountTypeList">
                { _.map(data.attvals, (value, index) => (<li key={index}
                  onClick={this.updateNestedConfigurationOptionWithAttribute.bind(null,outerIndex,value,index)}
                  value={value}>{value.attval}</li>))
                }
              </ul>
              <p className="selectContent">{_.findWhere(data.attvals, {isdef: CONSTANTS.SHOPPING_CART_VARIABLES.YES})
                && _.findWhere(data.attvals, {isdef: CONSTANTS.SHOPPING_CART_VARIABLES.YES}).attval} {data.spuom}</p>
            </div>
          </div>)
          })
        }
      </div>);
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

  changeProductQuantity(event) {
    clearPricingObject();
    this.props.updateQuantity(event.currentTarget.value);
  },

  increaseProductQuantity(status) {
    this.clearPricingObject();
    var quantity = this.props.editProduct.configuration.qty || 1;
    status ? quantity++ : quantity--
    if(quantity === 0){ quantity++ }
    this.props.updateQuantity(quantity);
  },

  updateNewQuantity(event) {
    this.props.updateQuantity(event.target.value);
  },


  getIndividualPricingDetails()
  {
      var selectedLocation = this.props.editProduct.location;
      var contractPeriod = this.props.editProduct.period.contractPeriod;

      var flag = true;
      var updatedError = {}
      if (!selectedLocation) {
          updatedError.selectedLocation = CONSTANTS.UI_MESSAGES.emptyFields;
      }
      if (!contractPeriod) {
          updatedError.contractPeriod = CONSTANTS.UI_MESSAGES.emptyFields;
      }
      if (_.isEmpty(updatedError)) {
          this.setState({ error: updatedError });
          this.props.getIndividualPricingDetails();
      }
      else {
          this.setState({ error: updatedError });
      }
  },

  clearPricingObject()
  {
    this.props.clearPricingObject();
  },

  renderConfiguration() {
    var locationOptions = []
    // if(this.props.globalData.selectedLocation)
    // {
    //   locationOptions = [{label:this.props.globalData.selectedLocation,value:this.props.globalData.selectedLocation}]
    // }
    // else{
      locationOptions = _.map(this.props.location,(i) => {
        return {
          'label':i.locname,
          'value':i.locname
        };
      });
    //}
    let months = this.getMonths()
    // NOTE: let projects = this.props.getProjects();
    let projects = this.props.editProduct.projects
    var configuration = this.props.editProduct.configuration;
    let editProduct = this.props.editProduct;
    var {selectedLocation} = this.props.editProduct.location;
    var {contractPeriod} = this.props.editProduct.period.contractPeriod || '';
    return (
      <div className="configSelectionContainer">
      {
        /**
          <div className="configUpperList">
              <div className="listDetails">
                <label>Product Type</label>
                <span>{editProduct.location.product || 'NA'}</span>
              </div>
              <div className="listDetails">
                <label>Plan Type</label>
                <span>{editProduct.planDetails.plan || 'NA'}</span>
              </div>
              <div className="listDetails">
                <label>Base Plan Selected</label>
                <span>{configuration.pdesc || 'NA'}</span>
              </div>
          </div>
        */
      }
      <div className="configListing">
      <div className="contractSelection clearfix">
          <div className="customSelectGroup ">
            <label htmlFor="selectLocation">Select Location</label>
            <Select autofocus options={locationOptions}
              simpleValue ref="selectLocation" name="selected-state"
              value={editProduct.location.selectedLocation}
              searchable={true} onChange={this.onChangeSelectedLocation} />
             <span className="errorMsg">{this.state.error.selectedLocation}</span>
          </div>

          {this.props.globalData.contractPeriod ? (
            <div className="customSelectGroup contractPeriodBlock">
              <label>Contract Period</label>
              <span>{this.props.globalData.contractPeriod}</span>
            </div>
          )
          :
          (<div className="customSelectGroup">
            <label htmlFor="selectPeriod">Contract Period</label>
            <Select ref="contractPeriod" autofocus options={months}
              simpleValue  name="contractPeriod" value={this.props.editProduct.period.contractPeriod}
              searchable={true} onChange={this.updateContractPeriod} placeholder=""/>
            <span className="errorMsg">{this.state.error.contractPeriod}</span>
          </div>)
          }



          <div className="selectProject">
            <div className="customSelectGroup selectProjectDropDown">
              <label htmlFor="selectProject">Project</label>
            {
              this.props.editProduct.period.selectedProjectIsNew ?
                this.props.globalData.selectedProjectIsNew ? (
                  <div className="createProject">
                    <input type='text' value={this.props.globalData.project} readOnly />
                  </div>
                )
                :
                  (<div className="createProject">
                    <input type='text' value={this.props.editProduct.period.selectedProject} onChange={this.updateNewProject} />
                  </div>)
              : this.props.globalData.project ? (<div className="createProject">
                    <input type='text' value={this.props.globalData.project} />
                  </div>) :
              <Select ref="selectedProject" autofocus options={projects}
                simpleValue  name="selectProject" value={this.props.editProduct.period.selectedProject}
                onChange={this.updateSelectProject} />
            }
            </div>

            {
              this.props.editProduct.period.selectedProjectIsNew ?
                this.props.globalData.selectedProjectIsNew ? (null) :
                  <span className="createNew" onClick={this.unInitiateCreateProject}>Remove</span>
              : this.props.globalData.project ? (null) :
              <span className="createNew" onClick={this.initiateCreateProject}>Create New</span>
            }
            <div className="clear"></div>
          </div>


      </div>
        <div className="quantityDetails">
          <div className="pull-left productLabel">
             <span>{configuration.pdesc || 'NA'}</span>
             <span>{configuration.pname || 'NA'}</span>
          </div>
          <div className="pull-right productQuantity">
            <span>Product Quantity</span>
            <span className = 'decreaseBtn' onClick={this.increaseProductQuantity.bind(null,false)}></span>
            <div className="createProject">
                <input type='text' value={this.props.editProduct.configuration.qty} onChange={this.updateNewQuantity} />
            </div>
            <span className = 'increaseBtn' onClick={this.increaseProductQuantity.bind(null,true)}></span>
          </div>
          <div className="clear"></div>
        </div>
        <div className="row configurationBlock">
          <div className="col-lg-6">
            {this.showConfigurationsByType(configuration.ptype, configuration.sprod)}
          </div>

          {
            _.isEmpty(this.props.editProduct.pricingObj) ?

              <BeforeRequestPricing
                  getIndividualPricingDetails = {this.getIndividualPricingDetails}
                  loaded = {this.props.loaded.pricing}
              />
            :
            <AfterRequestPricing
                 editProduct = {this.props.editProduct}
            />
          }
          <span className="errorMsg">{this.state.error.pricingObj}</span>
        </div>
        <span className="nextBtn btn btn-primary pull-right" onClick={this.addToCart}>Add to Cart </span>
        {
          /**
            <div className="navigationBtns">
              <span className="cancelBtn pull-left" onClick={this.openModal}>Cancel</span>
              <span className="nextBtn  pull-right" onClick={this.handleConfiguration.bind(null, true)}>Next ></span>
              <span className=" backBtn pull-right" onClick={this.handleConfiguration.bind(null, false)}>Back </span>
              <div className="clear"></div>
            </div>
           */
        }
      </div>
    </div>
    )
  },

  handlePreview() {
      //this.animateToTopofEditCart();
      return true;
      //this.props.handleNext(4);
  },

  addToCart() {
    if(this.handleLocation() && this.handleConfiguration() && this.handlePlanDetails() && this.handlePreview()) {
      this.props.addToCart();
    }
  },

  showConfigurationProperty() {
    var {ptype, sprod} = this.props.editProduct.configuration;
    var configView = (null);
    const types = CONSTANTS.PRODUCT_TYPES;
    switch (ptype) {
      case types.bundled:
        configView =(<div className="previewListBlock">
          { _.map(sprod, (data, outerIndex) => {
              if(data.atts) {
          var attribute = _.first(_.values(data.atts));
          var defaultValueId = attribute.defid;
          var chosenOne = attribute.attvals[defaultValueId];
          // NOTE : data.spdesc --> label : chosenOne --> value
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
          })
          }
        </div>);
        break;
      case types.withAttributes:
        var attributes = _.first(_.values(sprod));
        configView = (<div className="previewListBlock">
          { _.map(attributes && attributes.atts, (data, outerIndex) => (<div
            data-attribute-id={data.attid} className ="listDetails" key={outerIndex}>
              <label>{data.attname} {data.spuom}</label>
              <span >{_.findWhere(data.attvals, {isdef: CONSTANTS.SHOPPING_CART_VARIABLES.YES})
                  && _.findWhere(data.attvals, {isdef: CONSTANTS.SHOPPING_CART_VARIABLES.YES}).attval} </span>
            </div>))
          }
        </div>);
        break;
      case types.standard:
        configView = (null);
        break;
    }
    return configView;
  },

  renderProductPreviewStep() {
    var editProduct = this.props.editProduct;
    var configuration = this.props.editProduct.configuration;
    return (
      <div className="productPreviewContainer">
          <div className="reviewUpperList">
            <div className="listDetails">
              <label>Contract Period</label>
              <span>{editProduct.period.contractPeriod ?
               editProduct.period.contractPeriod + 'Months' : 'NA'}</span>
            </div>
            <div className="listDetails">
              <label>Project Name</label>
              <span>{editProduct.period.selectedProject || 'NA'}</span>
            </div>
            <div className="listDetails">
              <label>Location</label>
              <span>{editProduct.location.selectedLocation || 'NA'}</span>
            </div>
            <div className="listDetails">
              <label>Project Type</label>
              <span>{editProduct.location.product || 'NA'}</span>
            </div>
            <div className="listDetails">
              <label>Plan Type</label>
              <span>{editProduct.planDetails.plan || 'NA'}</span>
            </div>
            <div className="listDetails">
              <label>Base Plan Selected</label>
              <span>{configuration.pdesc || 'NA'}</span>
            </div>
          </div>
          <div className="reviewList">
          <div className="row previewBlock">
              <div className="col-lg-6">
                <div className="quantityDetails">
                  <div className="pull-left productLabel">
                    <span>{configuration.pdesc || 'NA'}</span>
                    <span>{configuration.pname || 'NA'}</span>
                  </div>
                  <div className="pull-right productQuantity">
                    <span>Product Quantity</span>
                    <span className="quantityNum">{configuration.qty}</span>
                  </div>
                  <div className="clear"></div>
                </div>
                {this.showConfigurationProperty()}
              </div>
              {
                _.isEmpty(this.props.editProduct.pricingObj) ?
                <div className="col-lg-6 getProductPricing">
                    <div className="pricingBlock getPricing ">
                        <i className="pricingIcon"></i>
                        <span className="pricingBtn" onClick={this.handlePreview.bind(null, false)}>
                          Kindly Go back to previous step, and request for pricing information.
                        </span>
                    </div>
                </div> :
                <AfterRequestPricing
                     editProduct = {this.props.editProduct}
                />
              }
            </div>
            <div className="navigationBtns">
              <span className="cancelBtn pull-left" onClick={this.openModal}>Cancel</span>
              {
                //<span className="nextBtn  pull-right" onClick={this.addToCart}>Add to Cart </span>
              }
              <span className=" backBtn pull-right" onClick={this.handlePreview.bind(null, false)}>Back </span>
              <div className="clear"></div>
            </div>
          </div>

      </div>
    )
  },


  openModal () {
    this.setState({
      cancelModalDisplay: true
    });
  },

  hideModal (event) {
    this.setState({ cancelModalDisplay: false});
  },

  render() {
    let maxStep = this.props.maxStepNumber
    let currentStep = this.props.activeStep.value
    return (
      <div id="scrollTo">
        <ul className="cartHeaderTabs">
        {_.map(CONSTANTS.SHOPPING_CART_PURCHASE_TABS, (step,i) => {
          return (
            <li key={i} className={ currentStep === step.value ? 'currentStep' : (null) } onClick={this.activeStepFunc.bind(null, step)}>
            <span className={ maxStep > step.value ? 'numCircle alreadyChecked' : 'numCircle' }>{ maxStep > step.value ? (null) : step.value-1}</span><span className="visited">{step.title}</span>
            </li>
          )
        })}
        </ul>

        {this.renderLocation()}
        {this.renderPlanDetails()}
        {
          //this.renderConfiguration()
        }
        {
          //this.renderProductPreviewStep()
        }

         <Modal className="customModalWrapper" isOpen={this.state.cancelModalDisplay} onRequestHide={this.hideModal}>
                  <ModalHeader>
                    <ModalClose onClick={this.hideModal}/>
                    <ModalTitle>Warning</ModalTitle>
                  </ModalHeader>
                  <ModalBody>
                    <span className="questionIcon">?</span>
                    <p className="warningMsg">On confirming cancel, all selected
                     data will be permanently lost.</p>
                  </ModalBody>
                  <ModalFooter>
                    <input type="submit" onClick={this.setAddInfraDisabled}
                      className="removeBtn pull-right" value="Yes, Delete this Item "/>
                    <input type="submit" className="cancelLink pull-right"
                      value="Cancel" onClick={this.hideModal}/>
                  </ModalFooter>
         </Modal>

      </div>
    )
  }
});

var Product = createReactClass({

    updateSystemConfigurations(obj) {
      this.props.updateSystemConfigurations(obj);
    },

    render() {
      let obj = _.first(this.props.obj.sugar); //added by deep as of now
      let pdesc = obj.pdesc;
      let pid = obj.pid;
      let propPid = this.props.editProduct.configuration.pid;
      let config = pid == propPid ? this.props.editProduct.configuration : _.first(this.props.obj.sugar)
      return (
        <li className={pid == propPid ? 'configSelected' : ''}
        onClick={this.updateSystemConfigurations.bind(null, config)}>
          <input type="radio" checked={pid == propPid}/>
          <span>{pdesc}</span>
          { this.props.showConfigurationsByTypeForPreview(config.ptype,config.sprod) }

          { this.props.clicked || (pid == propPid) ? this.props.renderConfiguration() : null }
        </li>
      )
    }
});

export default AddEditInfraProduct;
