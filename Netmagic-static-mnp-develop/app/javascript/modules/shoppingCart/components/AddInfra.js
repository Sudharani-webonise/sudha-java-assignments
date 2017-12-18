import { Link } from 'react-router';
import CONSTANTS from '../../../constants/app-constant';
import Session from '../../../mixins/sessionUtils';
import Utility from '../../../mixins/basicUtils';
import UI from '../../../mixins/ui';
import Select from 'react-select';
import { Modal, ModalHeader, ModalTitle, ModalClose, ModalBody, ModalFooter} from 'react-modal-bootstrap';
import Loader from 'react-loader';
import LoaderOptions from '../../../mixins/loader';
import NewBeforeRequestPricing from './NewBeforeRequestPricing';
import NewAfterRequestPricing from './NewAfterRequestPricing';
import User from '../../../services/user';

var AddInfra = createReactClass({
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
            headerMenuCount: 4
        }
    },

    activeStepFunc(step) {
        this.props.activeStepFunc(step)
    },

    setAddInfraDisabled() {
        this.props.setAddInfraDisabled();
    },

    showStepContent() {
        var resultStep = (null);
        var activeStep = this.props.activeStep;
        var mapper = {
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
    },

    updateContractPeriod(value) {
        this.props.updateDropDown('period', 'contractPeriod', value)
    },

    updateSelectProject(value) {
        this.props.updateDropDown('period', 'selectedProject', value)
    },

    updateNewProject(e) {
        this.props.updateDropDown('period', 'selectedProject', e.target.value)
    },

    getMonths() {
        return _.map(_.range(1, CONSTANTS.MAX_MONTHS), (rangeValue) => {
            return { label: rangeValue, value: rangeValue };
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
                    <span className="nextBtn pull-right" onClick={this.handlePeriod.bind(null, true) }>Next ></span>
                    <div className="clear"></div>
                </div>
            </div>
        )
    },

    handleLocation() {
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
    },

    onChangeSelectedLocation(value) {
        this.props.updateDropDown('location', 'selectedLocation', value)
    },

    onRadioProductSelect(obj) {
        this.props.onRadioProductSelect(obj)
    },

    getProductClassSet(condition) {
        return condition ? 'productType checkedType text-center' : 'productType uncheckedType text-center';
    },

    getProductIcon(productType) {
        var iconClassName = _.findWhere(CONSTANTS.PRODUCT_TYPE, { label: productType })
        var updatedClass = iconClassName.value || 'virtualServerIcon';
        return (
            <i className={ updatedClass + ' ' + 'productIcon'} ></i>
        )
    },

    renderLocation() {

        var editProduct = this.props.editProduct;
        return (
            <div className="locationSelectionContainer">
                <div className="locationListing">
                    <div className="clear"></div>
                </div>
            </div>
        )
    },

    handlePlanDetails() {
        var updatedError = {};
        var flag = true;
        if (!this.props.editProduct.configuration.pdesc) {
            updatedError.configuration = CONSTANTS.UI_MESSAGES.emptyFields;
        }
        if (!this.props.editProduct.planDetails.plan) {
            updatedError.planDetails = CONSTANTS.UI_MESSAGES.emptyFields;;
        }
        this.setState({ error: updatedError });
        if (updatedError.configuration || updatedError.planDetails) {
            return false;
        }
        return true;
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
        return (_.map(this.props.productList, (obj, i) => {
            return (
                <Product
                    updateSelectedListOfConfiguration = {this.updateSelectedListOfConfiguration}
                    clicked = {obj.clicked}
                    key={i}
                    showSummarisedConfigsByTypeForReview={this.showSummarisedConfigsByTypeForReview}
                    editProduct = {this.props.editProduct}
                    updateSystemConfigurations={this.updateSystemConfigurations}
                    renderConfiguration = {this.renderConfiguration}
                    obj={obj} key={i}
                    getSlicedProdName={this.getSlicedProdName}
                    showProdNameOnHover={this.showProdNameOnHover}/>
            )
        }))
    },

    getPlanClassSet(condition) {
        return condition ? 'planType checkedType text-center' : 'planType uncheckedType text-center';
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

    showSummarisedConfigsByTypeForReview(type, subProduct) {
        var configView = (null);
        const types = CONSTANTS.PRODUCT_TYPES;
        switch (type) {
            case types.bundled:
                configView = this.showSummarisedBundledConfigForPreview(subProduct);
                break;
            case types.withAttributes:
                configView = this.showSummarisedWithAttributesConfigForPreview(subProduct)
                break;
            case types.standard:
                configView = this.showSummarisedStandardProduct(subProduct);
                break;
        }
        return configView;
    },

    showSummarisedStandardProduct(subProduct) {
        return (<div>{_.map(_.range(this.state.headerMenuCount), (item) => {
            return (<span className="wid10">{CONSTANTS.HYPHEN}</span>)
        })}</div>);
    },

    showStaticSprod(remainingLength) {
        if(remainingLength) {
            return _.map(_.range(remainingLength), (data, i) => {
                return (<span key={i} className="wid10">{CONSTANTS.HYPHEN}</span>)
            });
        }
    },

    getSpuomUnit(spdesc) {
        let unit = "EA";
        if(CONSTANTS.VIRTUAL_VM_CPU_SSD.indexOf(spdesc) > -1) {
            unit = CONSTANTS.SHOPPING_CART_VARIABLES.GB
        }
        return unit;
    },

    showSummarisedBundledConfigForPreview(subProduct) {
        let noOfProducts = _.range(this.state.headerMenuCount);
        let subProductItems = _.chain(subProduct)
            .keys()
            .value();
        let subProductLength = subProductItems.length;
        let remainingLength = noOfProducts.length - subProductLength;
        return (<div>{_.map(subProduct, (data, outerIndex) => {
                if((/ram|RAM/).test(data.spdesc)) {
                    return (<span key={outerIndex} className="wid10">{Number(data.spqty)} {data.spuom}</span>)
                }
            })}
            {_.map(subProduct, (data, outerIndex) => {
                if((/cpu/i).test(data.spdesc)) {
                    return (<span key={outerIndex} className="wid10">{Number(data.spqty)} {data.spuom}</span>)
                }
            })}
            {_.map(subProduct, (data, outerIndex) => {
                if((/bandwidth/i).test(data.spdesc)) {
                    return (<span key={outerIndex} className="wid10">{Number(data.spqty)} {data.spuom}</span>)
                }
            })}
            {_.map(subProduct, (data, outerIndex) => {
                let attunit = '';
                _.each(data.atts, (i) => {
                    attunit = i.attunit
                })
                if((/ssd|hdd|storage/i).test(data.spdesc)) {
                    return (<span key={outerIndex} className="wid10">{Number(data.spqty)} {data.spuom == 'EA' ? attunit : data.spuom}</span>)
                }
            })}
            {this.showStaticSprod(remainingLength)}
        </div>)
    },

    showSummarisedWithAttributesConfigForPreview(subProduct) {
        let template;
        var attributes = _.first(_.values(subProduct));
        let noOfProducts = _.range(this.state.headerMenuCount);
        let subProductItems = _.chain(attributes && attributes.atts)
            .keys()
            .value();
        let subProductLength = subProductItems.length;
        let remainingLength = noOfProducts.length - subProductLength;
        return (<div>{_.map(attributes && attributes.atts, (data, outerIndex) => {
                return (
                    <span className="wid10">{_.findWhere(data.attvals, { isdef: CONSTANTS.SHOPPING_CART_VARIABLES.YES })
                        && _.findWhere(data.attvals, { isdef: CONSTANTS.SHOPPING_CART_VARIABLES.YES }).attval}
                        {data.spuom}
                    </span>
                )
            })}
            {this.showStaticSprod(remainingLength)}
        </div>);
    },

    showWithAttributesConfigForPreview(subProduct) {
        var attributes = _.first(_.values(subProduct));
        return (
            <div>
                {_.map(attributes && attributes.atts, (data, outerIndex) => {
                    return (
                        <div key={outerIndex} className="listDetails">
                            <label>{data.attname}</label>
                            <span>{_.findWhere(data.attvals, { isdef: CONSTANTS.SHOPPING_CART_VARIABLES.YES })
                                && _.findWhere(data.attvals, { isdef: CONSTANTS.SHOPPING_CART_VARIABLES.YES }).attval}
                                {data.spuom}
                            </span>
                        </div>
                    )
                }) }
            </div>
        );
    },

    showBundledConfigForPreview(subProduct) {
        return (<div >
            {_.map(subProduct, (data, outerIndex) => {
                //NOTE : if config attribute is a nested attribute
                if (data.atts) {
                    var attribute = _.first(_.values(data.atts));
                    var defaultValueId = attribute.defid;
                    var chosenOne = attribute.attvals[defaultValueId];
                    //NOTE : data.spdesc --> label : chosenOne --> value
                    return (
                        <div key={outerIndex} className="listDetails">
                            <label>{data.spdesc}</label>
                            <span>{chosenOne && chosenOne.attval}:
                                {
                                    (chosenOne.appqty && (chosenOne.appqty.length > 1)) ?
                                        chosenOne && chosenOne.selqty : (null)
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
            }) }
        </div>)
    },

    renderPlanDetails() {
        let editProduct = this.props.editProduct;
        let productList = this.props.productList;
        let product = editProduct.location.product;
        return (<div className="clearfix pull-left">
            <div className="planSelectionContainer clearfix marginBottom17 borderBtm">
                <div className="planListing clearfix">
                    <p className="head1 marginBottom17">Select Plan</p>
                    <span className="errorMsg">{this.state.error.planDetails}</span>
                    <div className="planWrap clearfix pull-left">
                        <div className="icon clearfix pull-left">
                            <div className="plan_icon"></div>
                        </div>
                        <div className="customSelectGroup clearfix pull-left">
                            <label for="serviceTerm">Select Plan</label>
                            <ul className="customSelectList mandatory dropDownData ticketCustomList customer">
                                { _.map(this.props.subSubCategories, (obj, i) => {
                                    return (<li key={i} onClick={this.onRadioPlanSelect.bind(null, obj) }>
                                        <span className="planName">{obj.subsubcatname}</span>
                                    </li>)
                                }) }
                            </ul>
                            <p className="selectContent">{this.props.editProduct.planDetails.plan || 'Select Plan'}</p>
                        </div>
                        <div className="infopara pull-left clearfix">
                            {(this.props.editProduct.planDetails.plan == "Fixed") ? <p>Fixed plans are ideal for customers whos requirements are more stable than elastic. Fixed Plans provide stable monthly billing at a flat fee. Simply choose and configure the Plan you need and add additional cores / memory / disk as needed. Once activated, your plan is metered at a flat free. Upgrades are activated at the next billing cycle.</p> : (null)
                            }
                            {(this.props.editProduct.planDetails.plan == "Elastic") ? <p>Elastic Plans are usage-based plans that are ideal for customers who may not need continuous 24x7, 'always-on' instances. The Elastic Plans offer maximum flexibility through an hourly charge for the duration that your instances are 'On'. Choose an hourly plan and you can start and stop your instances using this interface - billing will only occur when your instances are 'On'. You can also add additional CPU cores and Memory on an hourly basis.</p> : (null)
                            }
                        </div>
                    </div>
                </div>
            </div>
            <div className="planSelectionContainer">
                <div className="planListing clearfix">
                    <p className="head1 marginBottom17 clearfix">Configure your plan</p>
                    <div className="planWrap clearfix">
                        <div className="systemConfigurationsList planInfo">
                            <span className="errorMsg">{this.state.error.configuration}</span>
                            <Loader loaded={this.props.loaded.productList} top="50%" left="50%" options={LoaderOptions}>
                            {_.isArray(productList) && productList.length ?
                                <ul className="" id="listOfConfiguration">
                                    {this.showStaticHeaders()}
                                    {this.getListOfConfiguration() }
                                </ul> : <span> {CONSTANTS.UI_MESSAGES.shoppingCart.configurationListError} </span>
                            }
                            </Loader>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        )
    },

    handleConfiguration() {
        var flag = true;
        var updatedError = {};

        if (_.isEmpty(this.props.editProduct.pricingObj)) {
            updatedError.pricingObj = CONSTANTS.UI_MESSAGES.shoppingCart.pricingObjError;
            this.setState({ error: updatedError });
            return false
        }
        return true;
    },


    updateConfigurationOption(index, value) {
        this.props.updateConfigurationOption(index, value);
    },

    updateNestedConfigurationOptionWithAttribute(index, newDefId, innerIndex) {
        var sprod = this.props.editProduct.configuration.sprod;
        sprod[_.first(Object.keys(sprod))].atts[index].defid = newDefId.attvid;
        var attVals = sprod[_.first(Object.keys(sprod))].atts[index].attvals
        var attvalToUpdate = _.findWhere(attVals, { attvid: newDefId.attvid });
        _.each(attVals, (value, key, obj) => {
            value.isdef = value.attvid === newDefId.attvid ? CONSTANTS.SHOPPING_CART_VARIABLES.YES : CONSTANTS.SHOPPING_CART_VARIABLES.NO;
        });
        this.props.updateConfigurationAttribute(sprod);
    },

    updateNestedConfigurationOption(index, newDefId, innerIndex) {
        var sprod = this.props.editProduct.configuration.sprod;
        _.first(_.values(sprod[index].atts)).defid = newDefId;
        var attVals = Utility.getNestedValue(sprod[index].atts, 'attvals');
        var attvalToUpdate = _.findWhere(attVals, { attvid: newDefId });
        _.each(attVals, (value, key, obj) => {
            value.isdef = value.attvid === newDefId ? CONSTANTS.SHOPPING_CART_VARIABLES.YES : CONSTANTS.SHOPPING_CART_VARIABLES.NO;
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

    getSlicedName(projectName) {
        projectName = projectName;
        if (projectName) {
            return (projectName.length > CONSTANTS.LABEL_NAME_LIMIT ?
                projectName.slice(0, CONSTANTS.LABEL_NAME_SUBSTR) + '...' : projectName);
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

    getSlicedProdName(pname) {
        if (pname) {
            return (pname.length > CONSTANTS.LABEL_NAME_LIMIT ?
                pname.slice(0, CONSTANTS.LABEL_NAME_SUBSTR) + '...' : pname);
        }
    },

    showProdNameOnHover(pname) {
        var isNameLong = (pname.length > CONSTANTS.LABEL_NAME_LIMIT);
        return this.classSet({
            labelHoverData: isNameLong,
            hide: !isNameLong
        });
    },

    showBundledConfig(subProduct) {
        return (<div className="configDetailsBlock clearfix">
            <div className = "formElementWrap pull-left marginBottom10 clearfix">
            {_.map(subProduct, (data, outerIndex) => {
                // NOTE :if config attribute is a nested attribute
                if (data.atts) {
                    var attribute = _.first(_.values(data.atts));
                    var defaultValueId = attribute.defid;
                    var chosenOne = attribute.attvals[defaultValueId];
                    return (<div key={outerIndex}>
                        <div className="customSelectGroup pull-left clearfix">
                            <label className="labelData">{this.getSlicedName(data.spdesc) }</label>
                            <label className={this.showNameOnHover(data.spdesc) }>{data.spdesc} in {data.spuom || ''}</label>
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
                    return (<div>
                        <div className="customSelectGroup pull-left clearfix">
                            <label className="labelData">{this.getSlicedName(data.spdesc) }</label>
                            <label className={this.showNameOnHover(data.spdesc) }>{data.spdesc} in {data.spuom || ''}</label>
                            <ul className="customSelectList accountTypeList">
                                { _.map(data.appqty, (value, index) => (<li key={index}
                                    onClick={this.updateConfigurationOption.bind(null, outerIndex, value) }
                                    value={value}>{value}</li>))
                                }
                            </ul>
                            <p className="selectContent">{data && data.spqty}</p>
                        </div>
                    </div>)
                }
            }) }</div>
        </div>)

    },

    showWithAttributesConfig(subProduct) {
        var attributes = _.first(_.values(subProduct));
        return (<div className=" configDetailsBlock clearfix">
            <div className ="formElementWrap pull-left marginBottom10 clearfix">
            { _.map(attributes && attributes.atts, (data, outerIndex) => {
                var attribute = _.findWhere(data.attvals, { isdef: CONSTANTS.SHOPPING_CART_VARIABLES.YES });
                var attval = attribute && attribute.attval;
                return (<div data-attribute-id={data.attid} key={outerIndex}>
                    <div className="customSelectGroup pull-left clearfix">
                        <label className="labelData">{this.getSlicedName(data.attname) }</label>
                        <label className={this.showNameOnHover(data.attname) }>{data.attname} in {data.spuom || ''}</label>
                        <ul className="customSelectList accountTypeList">
                            { _.map(data.attvals, (value, index) => (<li key={index}
                                onClick={this.updateNestedConfigurationOptionWithAttribute.bind(null, outerIndex, value, index) }
                                value={value}>{value.attval}</li>))
                            }
                        </ul>
                        <p className="selectContent">{attval}</p>
                    </div>
                </div>)
            })
            }</div>
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
        if (quantity === 0) { quantity++ }
        this.props.updateQuantity(quantity);
    },

    updateNewQuantity(event) {
        var target = event.currentTarget;
        var value = Number(target.value) || CONSTANTS.ONE;
        value = parseInt(Math.max(1, value), 10) || '';
        value = parseInt(Math.min(target.max, value), 10) || '';
        this.props.updateQuantity(value);
    },

    getIndividualPricingDetails() {
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

    clearPricingObject() {
        this.props.clearPricingObject();
    },

    renderConfiguration() {
        var locationOptions = [];
        locationOptions = _.map(this.props.locations, (i) => {
            return {
                'label': i.locname,
                'value': i.locname
            };
        });
        let months = this.getMonths()
        // NOTE: let projects = this.props.getProjects();
        let projects = this.props.editProduct.projects
        var configuration = this.props.editProduct.configuration;
        let editProduct = this.props.editProduct;
        var {selectedLocation} = this.props.editProduct.location;
        var {contractPeriod} = this.props.editProduct.period.contractPeriod || '';
        return (_.isEmpty(configuration) ?
            <div className="configSelectionContainer">
                <p className="head1 marginBottom17">Select a Plan</p>
            </div> : <div>
                <div className="">
                    {this.showConfigurationsByType(configuration.ptype, configuration.sprod) }
                    {this.showStaticData()}
                </div>
            </div>
        )
    },

    showStaticHeaders() {
        let planName = this.props.editProduct.planDetails.plan;
        const categories = CONSTANTS.CATEGORY_TYPE;
        let headerName = (null);
        let checkForVServer = _.contains(categories.virtualServer.items, planName);
        let checkForFirewall = _.contains(categories.virtualFireWall.items, planName);
        let checkForNetwork = _.contains(categories.virtualNetwork.items, planName);
        let checkForServices = _.contains(categories.services.items, planName);
        let checkForBandwidth = _.contains(categories.bandwidth.items, planName);
        switch(true) {
            case checkForFirewall:
                headerName = (
                    <li className="formTHead clearfix">
                        <span className="radioBtnWrap">&nbsp;</span>
                        <span className="planName">Plan Name</span>
                        <span className="haInfo wid10">H.A.</span>
                        <span className="ipsecVpn wid10">IPSEC VPN</span>
                        <span className="planStartingAt pull-right">Starting at</span>
                    </li>
                );
                this.state.headerMenuCount = 2;
            break;
            case checkForVServer:
                headerName = (<li className="formTHead clearfix">
                    <span className="radioBtnWrap">&nbsp;</span>
                    <span className="planName">Plan Name</span>
                    <span className="planMemory wid10">Memory</span>
                    <span className="planCore wid10">Core (s)</span>                  
                    <span className="planBandwidth wid20">Bandwidth</span>
                    <span className="planDisk wid10">Disk</span>
                    <span className="planStartingAt pull-right">Starting at</span>
                </li>
                );
                this.state.headerMenuCount = 4;
            break;
            case checkForNetwork:
                headerName = (<li className="formTHead clearfix">
                    <span className="radioBtnWrap">&nbsp;</span>
                    <span className="wid30">Plan Name</span>
                    <span className="wid10">HTTP</span>
                    <span className="wid10">TCP</span>
                    <span className="wid10">SSL</span>
                    <span className="wid13">Conn / Sec</span>
                    <span className="planStartingAt pull-right">Starting at</span>
                </li>
                );
                this.state.headerMenuCount = 4;
            break;
            case checkForServices: 
                 headerName = (<li className="formTHead clearfix">
                    <span className="radioBtnWrap">&nbsp;</span>
                    <span className="planName">Plan Name</span>
                    <span className="planMemory wid10">Memory</span>
                    <span className="planCore wid10">Core (s)</span>                  
                    <span className="planBandwidth wid20">Bandwidth</span>
                    <span className="planDisk wid10">Disk</span>
                    <span className="planStartingAt pull-right">Starting at</span>
                </li>
                );
                this.state.headerMenuCount = 4;
            break;
            case checkForBandwidth: 
                 headerName = (<li className="formTHead clearfix">
                    <span className="radioBtnWrap">&nbsp;</span>
                    <span className="planName">Plan Name</span>
                    <span className="planStartingAt pull-right">Starting at</span>
                </li>
                );
                this.state.headerMenuCount = 0;
            break;
        }
        // this.setState(this.state);
        return headerName;
    },

    showStaticData() {
        let planName = this.props.editProduct.planDetails.plan;
        const categories = CONSTANTS.CATEGORY_TYPE;
        let headerName = (null);
        let checkForVServer = _.contains(categories.virtualServer.items, planName);
        let checkForFirewall = _.contains(categories.virtualFireWall.items, planName);
        let checkForNetwork = _.contains(categories.virtualNetwork.items, planName);
        let checkForServices = _.contains(categories.services.items, planName);
        let checkForBandwidth = _.contains(categories.bandwidth.items, planName);
        switch(true) {
            case checkForVServer:
                headerName = (
                 <div><div className="diskPartition hide">
                    <hr/>
                    <p className="head1 marginBottom17 marTop15 clearfix">Disk Partition(s) - <span className="subhead1">Total Disk: 100GB/Total IOPS: 300</span></p>
                    <div className="diskConfig clearfix">
                        <div className="formInputWrap clearfix">
                            <div className="formElementWrap pull-left clearfix">
                                <label>Swap - GB</label>
                                <input type="text" name="text1" value="4"/>
                                <span className="info_icon"></span> </div>
                            <div className="formElementWrap pull-left clearfix">
                                <label>/tmp - GB</label>
                                <input type="text" name="text2" value="8"/>
                                <span className="info_icon"></span> </div>
                            <div className="formElementWrap pull-left clearfix">
                                <label>/var /tmp - GB</label>
                                <input type="text" name="text3" value="8"/>
                                <span className="info_icon"></span> </div>
                            <div className="formElementWrap pull-left clearfix">
                                <label>/- GB</label>
                                <input type="text" name="text4" value="80"/>
                                <span className="info_icon"></span> </div>
                            <div className="formElementleft formElementWrap pull-left">
                                <label>IOPS</label>
                                <input type="text" name="text5" value="0"/>
                            </div>
                        </div>
                        <div className="diskConfig marginBottom17 marTop10 clearfix">
                            <div className="formInputWrap clearfix">
                                <div className="formElementWrap pull-left clearfix">
                                    <label>/mmt/vol1</label>
                                    <input type="text" name="text1" value="8"/>
                                </div>
                                <div className="formElementWrap pull-left clearfix">
                                    <label>/mmt/vol2</label>
                                    <input type="text" name="text2" value="80"/>
                                </div>
                                <div className="formElementWrap pull-left clearfix">
                                    <label>/mmt/vol3</label>
                                    <input type="text" name="text3" value="8"/>
                                </div>
                                <div className="formElementWrap pull-left clearfix">
                                    <label>/mmt/vol4</label>
                                    <input type="text" name="text4" value="80"/>
                                </div>
                                <div className="formElementleft formElementWrap pull-left">
                                    <label>IOPS</label>
                                    <input type="text" name="text5" value="0"/>
                                </div>
                            </div>
                        </div>
                        <div className="clearfix"></div>
                        <p className="head1 marginBottom17 marTop15 clearfix">Additional Details -
                            <span className="subhead1">Install additional packages</span>
                        </p>
                        <button className="packBtn">+ Click to add packages</button>
                    </div>
                    </div>
                    <div className='quantityWrap clearfix'>
                        <hr/>
                        <div className='quantityDiv pull-left'>
                            <label>Quantity</label>
                            <input type='text' min='1' max='1000' value={this.props.editProduct.configuration.qty} onChange={this.updateNewQuantity}/>
                        </div>
                        <div className='lbNameWrap pull-right hide'>
                            <div className='labelInputWrap'>
                                <label>VM Name <span className="mandatoryStar">*</span></label>
                                <input type='text' placeholder='Instance Name' />
                            </div>
                            <div className="checkboxWrap">
                                <label className="cssCheckBox">
                                    <input type="checkbox" />
                                    <span className="NMIcon-checkbox lightColorLabel"></span>
                                </label>
                            </div>
                            <label>Apply name for VM (s) </label>
                            <a href='#' className='whiteCancelBtn pull-right'>Rename</a>
                            <div className='clearfix'></div>
                        </div>
                    </div>
                </div>
                );
            break;

            case checkForFirewall:
                headerName = (<div className="diskPartition">
                <hr className="hide"/>
                <div className="zoneDetailsWrap hide">
                    <p className="head1">Zone Details</p>
                    <ul className="list-unstyled zoneListing">
                        <li className="clearfix active">
                            <a href="javascript:void(0)">Zone 1
                                <span className="accordianBtn active pull-right"><span></span></span>
                            </a>
                            <div className="clearfix"></div>
                            <div className="zoneInfoWrap clearfix">
                                <div className='quantityWrap zoneNameWrap pull-left clearfix'>
                                    <div className='lbNameWrap hide'>
                                        <div className='labelInputWrap'>
                                            <label>Zone Name<span className="mandatoryStar">*</span></label>
                                            <input type='text' placeholder='App Zone' />
                                        </div>
                                    </div>
                                </div>
                                <div className="customSelectGroup pull-left ipAddressRange">
                                    <label for="ipAddressRange">IP Address Range<span className="mandatoryStar">*</span></label>
                                    <ul className="customSelectList mandatory dropDownData ticketCustomList customer">
                                        <li>Small (/24 - 256 IP’s) </li>
                                    </ul>
                                    <p className="selectContent"></p>
                                </div>
                            </div>
                            <div className="zoneDropdownInfoWrap createIpsWrap clearfix">
                                <div className="createIPVWrap hide">
                                    <ul className="clearfix">
                                        <li>
                                            <label>VM NIC - Internet</label>
                                            <div className="formElementWrap clearfix marginBottom17 pull-left">
                                                <div className="customSelectGroup pull-left">
                                                    <label for="ipAddressSpace">IP Address Space</label>
                                                    <ul className="customSelectList mandatory dropDownData ticketCustomList customer">
                                                        <li>10.0.0.0</li>
                                                        <li>10.0.0.1</li>
                                                        <li>10.0.0.2</li>
                                                        <li>10.0.0.3</li>
                                                    </ul>
                                                    <p className="selectContent"></p>
                                                </div>
                                                <div className="attachedSecondColom">
                                                    <label>Start IP Address</label>
                                                    <input type="text" placeholder="10.1.1.0"/>
                                                </div>
                                                <div className="attachedSecondColom attachedThirdColom">
                                                    <label>Range</label>
                                                    <input type="text" placeholder="10.1.1.0-10.1.1.255"/>
                                                </div>
                                            </div>
                                        </li>
                                        <li>
                                            <label>VM NIC - Private</label>
                                            <div className="formElementWrap clearfix marginBottom17 pull-left">
                                                <div className="customSelectGroup pull-left">
                                                    <label for="ipAddressSpace">IP Address Space</label>
                                                    <ul className="customSelectList mandatory dropDownData ticketCustomList customer">
                                                        <li>10.0.0.0</li>
                                                        <li>10.0.0.1</li>
                                                        <li>10.0.0.2</li>
                                                        <li>10.0.0.3</li>
                                                    </ul>
                                                    <p className="selectContent"></p>
                                                </div>
                                                <div className="attachedSecondColom">
                                                    <label>Start IP Address</label>
                                                    <input type="text" placeholder="10.1.1.0"/>
                                                </div>
                                                <div className="attachedSecondColom attachedThirdColom">
                                                    <label>Range</label>
                                                    <input type="text" placeholder="10.1.1.0-10.1.1.255"/>
                                                </div>
                                            </div>
                                        </li>
                                        <li>
                                            <label>VM NIC - Storage</label>
                                            <div className="formElementWrap clearfix marginBottom17 pull-left">
                                                <div className="customSelectGroup pull-left">
                                                    <label for="ipAddressSpace">IP Address Space</label>
                                                    <ul className="customSelectList mandatory dropDownData ticketCustomList customer">
                                                        <li>10.0.0.0</li>
                                                        <li>10.0.0.1</li>
                                                        <li>10.0.0.2</li>
                                                        <li>10.0.0.3</li>
                                                    </ul>
                                                    <p className="selectContent"></p>
                                                </div>
                                                <div className="attachedSecondColom">
                                                    <label>Start IP Address</label>
                                                    <input type="text" placeholder="10.1.1.0"/>
                                                </div>
                                                <div className="attachedSecondColom attachedThirdColom">
                                                    <label>Range</label>
                                                    <input type="text" placeholder="10.1.1.0-10.1.1.255"/>
                                                </div>
                                            </div>
                                        </li>
                                        <li>
                                            <label>VM NIC - Backup</label>
                                            <div className="formElementWrap clearfix marginBottom17 pull-left">
                                                <div className="customSelectGroup pull-left">
                                                    <label for="ipAddressSpace">IP Address Space</label>
                                                    <ul className="customSelectList mandatory dropDownData ticketCustomList customer">
                                                        <li>10.0.0.0</li>
                                                        <li>10.0.0.1</li>
                                                        <li>10.0.0.2</li>
                                                        <li>10.0.0.3</li>
                                                    </ul>
                                                    <p className="selectContent"></p>
                                                </div>
                                                <div className="attachedSecondColom">
                                                    <label>Start IP Address</label>
                                                    <input type="text" placeholder="10.1.1.0"/>
                                                </div>
                                                <div className="attachedSecondColom attachedThirdColom">
                                                    <label>Range</label>
                                                    <input type="text" placeholder="10.1.1.0-10.1.1.255"/>
                                                </div>
                                            </div>
                                        </li>
                                    </ul>
                                </div>

                                <div className="dualStackTabs">
                                    <ul className="clearfix dualTabs">
                                        <li className="active"><a href="javascript:void(0)">IPv4</a></li>
                                        <li><a href="javascript:void(0)">IPv6</a></li>
                                    </ul>
                                    <div className="ipvData hide">
                                        <ul>
                                            <li>
                                                <label>VM NIC - Internet</label>
                                                <div className="formElementWrap clearfix">
                                                    <div className="customSelectGroup pull-left">
                                                        <label for="ipAddressSpace">IP Address Space</label>
                                                        <ul className="customSelectList mandatory dropDownData ticketCustomList customer">
                                                            <li>10.0.0.0</li>
                                                            <li>10.0.0.1</li>
                                                            <li>10.0.0.2</li>
                                                            <li>10.0.0.3</li>
                                                        </ul>
                                                        <p className="selectContent"></p>
                                                    </div>
                                                    <div className="attachedSecondColom">
                                                        <label>Start IP Address</label>
                                                        <input type="text" placeholder="10.1.1.0"/>
                                                    </div>
                                                    <div className="attachedSecondColom attachedThirdColom">
                                                        <label>Range</label>
                                                        <input type="text" placeholder="10.1.1.0-10.1.1.255"/>
                                                    </div>
                                                </div>
                                            </li>
                                            <li>
                                                <label>VM NIC - Private</label>
                                                <div className="formElementWrap clearfix marginBottom17 pull-left">
                                                    <div className="customSelectGroup pull-left">
                                                        <label for="ipAddressSpace">IP Address Space</label>
                                                        <ul className="customSelectList mandatory dropDownData ticketCustomList customer">
                                                            <li>10.0.0.0</li>
                                                            <li>10.0.0.1</li>
                                                            <li>10.0.0.2</li>
                                                            <li>10.0.0.3</li>
                                                        </ul>
                                                        <p className="selectContent"></p>
                                                    </div>
                                                    <div className="attachedSecondColom">
                                                        <label>Start IP Address</label>
                                                        <input type="text" placeholder="10.1.1.0"/>
                                                    </div>
                                                    <div className="attachedSecondColom attachedThirdColom">
                                                        <label>Range</label>
                                                        <input type="text" placeholder="10.1.1.0-10.1.1.255"/>
                                                    </div>
                                                </div>
                                            </li>
                                            <li>
                                                <label>VM NIC - Storage</label>
                                                <div className="formElementWrap clearfix marginBottom17 pull-left">
                                                    <div className="customSelectGroup pull-left">
                                                        <label for="ipAddressSpace">IP Address Space</label>
                                                        <ul className="customSelectList mandatory dropDownData ticketCustomList customer">
                                                            <li>10.0.0.0</li>
                                                            <li>10.0.0.1</li>
                                                            <li>10.0.0.2</li>
                                                            <li>10.0.0.3</li>
                                                        </ul>
                                                        <p className="selectContent"></p>
                                                    </div>
                                                    <div className="attachedSecondColom">
                                                        <label>Start IP Address</label>
                                                        <input type="text" placeholder="10.1.1.0"/>
                                                    </div>
                                                    <div className="attachedSecondColom attachedThirdColom">
                                                        <label>Range</label>
                                                        <input type="text" placeholder="10.1.1.0-10.1.1.255"/>
                                                    </div>
                                                </div>
                                            </li>
                                            <li>
                                                <label>VM NIC - Backup</label>
                                                <div className="formElementWrap clearfix marginBottom17 pull-left">
                                                    <div className="customSelectGroup pull-left">
                                                        <label for="ipAddressSpace">IP Address Space</label>
                                                        <ul className="customSelectList mandatory dropDownData ticketCustomList customer">
                                                            <li>10.0.0.0</li>
                                                            <li>10.0.0.1</li>
                                                            <li>10.0.0.2</li>
                                                            <li>10.0.0.3</li>
                                                        </ul>
                                                        <p className="selectContent"></p>
                                                    </div>
                                                    <div className="attachedSecondColom">
                                                        <label>Start IP Address</label>
                                                        <input type="text" placeholder="10.1.1.0"/>
                                                    </div>
                                                    <div className="attachedSecondColom attachedThirdColom">
                                                        <label>Range</label>
                                                        <input type="text" placeholder="10.1.1.0-10.1.1.255"/>
                                                    </div>
                                                </div>
                                            </li>
                                        </ul>
                                    </div>
                                    <div className="ipvData ipvSixDataWrap">
                                        <ul>
                                            <li>
                                                <label>VM NIC - Internet</label>
                                                <div className="formElementWrap clearfix">
                                                    <div className="customSelectGroup pull-left">
                                                        <label for="ipAddressSpace">Public IPv6 Owner</label>
                                                        <ul className="customSelectList mandatory dropDownData ticketCustomList customer">
                                                            <li>Self</li>
                                                        </ul>
                                                        <p className="selectContent"></p>
                                                    </div>
                                                    <div className="attachedSecondColom bigIpAddressWrap">
                                                        <label>Start IP Address</label>
                                                        <input type="text" placeholder="2001:db80:0000:0000:0000:0000:0000"/>
                                                    </div>
                                                </div>
                                            </li>
                                            <li>
                                                <label>VM NIC - Private</label>
                                                <div className="formElementWrap clearfix">
                                                    <div className="attachedSecondColom bigIpAddressWrap privateStartIPAddress">
                                                        <label>Start IP Address</label>
                                                        <input type="text" placeholder="fc00:0000:0000:0000:0000:0000:0000:0000"/>
                                                    </div>
                                                </div>
                                            </li>
                                            <li>
                                                <label>VM NIC - Storage</label>
                                                <div className="formElementWrap clearfix">
                                                    <div className="attachedSecondColom bigIpAddressWrap privateStartIPAddress">
                                                        <label>Start IP Address</label>
                                                        <input type="text" placeholder="fc00:0000:0000:0000:0000:0000:0000:0000"/>
                                                    </div>
                                                </div>
                                            </li>
                                            <li>
                                                <label>VM NIC - Backup</label>
                                                <div className="formElementWrap clearfix">
                                                    <div className="attachedSecondColom bigIpAddressWrap privateStartIPAddress">
                                                        <label>Start IP Address</label>
                                                        <input type="text" placeholder="fc00:0000:0000:0000:0000:0000:0000:0000"/>
                                                    </div>
                                                </div>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </li>
                        <li>
                            <a href="javascript:void(0)">Zone 2
                                <span className="accordianBtn pull-right"><span></span></span>
                            </a>
                        </li>
                    </ul>
                </div>

                <div className="firewallNameWrap hide">
                    <div className='quantityWrap zoneNameWrap clearfix'>
                        <div className='quantityDiv pull-left clearfix'>
                            <label>Quantity</label>
                            <input type='text' min='1' max='1000' value={this.props.editProduct.configuration.qty} onChange={this.updateNewQuantity}/>
                        </div>
                        <div className='lbNameWrap pull-left hide'>
                            <div className='labelInputWrap pull-left'>
                                <label>Firewall Name<span className="mandatoryStar">*</span></label>
                                <input type='text' placeholder='Instance Name' />
                            </div>
                        </div>
                    </div>
                </div>
                </div>);
            break;

            case checkForNetwork:
                headerName = (<div className="quantityWrap clearfix">
                <div className='quantityDiv pull-left'>
                    <label>Quantity</label>
                    <input type='text' min='1' max='1000' value={this.props.editProduct.configuration.qty} onChange={this.updateNewQuantity}/>
                </div>
                <div className='lbNameWrap pull-right hide'>
                    <div className='labelInputWrap'>
                        <label>Load Balancer Name <span className="mandatoryStar">*</span></label>
                        <input type='text' placeholder='Instance Name' />
                    </div>
                    <div className="checkboxWrap">
                        <label className="cssCheckBox">
                            <input type="checkbox" />
                            <span className="NMIcon-checkbox lightColorLabel"></span>
                        </label>
                    </div>
                    <label>Apply name for Load Balancer (s) </label>
                    <a href='#' className='whiteCancelBtn pull-right'>Rename</a>
                    <div className='clearfix'></div>
                </div>
                </div>
                );
            break;

            case checkForServices:
                headerName = (<div className="quantityWrap clearfix">
                <div className='quantityDiv pull-left'>
                    <label>Quantity</label>
                    <input type='text' min='1' max='1000' value={this.props.editProduct.configuration.qty} onChange={this.updateNewQuantity}/>
                </div>
                <div className='lbNameWrap pull-right hide'>
                    <div className='labelInputWrap'>
                        <label>Load Balancer Name <span className="mandatoryStar">*</span></label>
                        <input type='text' placeholder='Instance Name' />
                    </div>
                    <div className="checkboxWrap">
                        <label className="cssCheckBox">
                            <input type="checkbox" />
                            <span className="NMIcon-checkbox lightColorLabel"></span>
                        </label>
                    </div>
                    <label>Apply name for Load Balancer (s) </label>
                    <a href='#' className='whiteCancelBtn pull-right'>Rename</a>
                    <div className='clearfix'></div>
                </div>
                </div>
                );
            break;
            case checkForBandwidth:
                headerName = (<div className="quantityWrap clearfix">
                <div className='quantityDiv pull-left'>
                    <label>Quantity</label>
                    <input type='text' min='1' max='1000' value={this.props.editProduct.configuration.qty} onChange={this.updateNewQuantity}/>
                </div>
                <div className='lbNameWrap pull-right hide'>
                    <div className='labelInputWrap'>
                        <label>Load Balancer Name <span className="mandatoryStar">*</span></label>
                        <input type='text' placeholder='Instance Name' />
                    </div>
                    <div className="checkboxWrap">
                        <label className="cssCheckBox">
                            <input type="checkbox" />
                            <span className="NMIcon-checkbox lightColorLabel"></span>
                        </label>
                    </div>
                    <label>Apply name for Load Balancer (s) </label>
                    <a href='#' className='whiteCancelBtn pull-right'>Rename</a>
                    <div className='clearfix'></div>
                </div>
                </div>
                );
            break;
            default:
                headerName = (null);
            break;
        }
        return headerName;
    },

    handlePreview() {
        return true;
    },

    showConfigurationProperty() {
        var {ptype, sprod} = this.props.editProduct.configuration;
        var configView = (null);
        const types = CONSTANTS.PRODUCT_TYPES;
        switch (ptype) {
            case types.bundled:
                configView = (<div className="previewListBlock">
                    { _.map(sprod, (data, outerIndex) => {
                        if (data.atts) {
                            var attribute = _.first(_.values(data.atts));
                            var defaultValueId = attribute.defid;
                            var chosenOne = attribute.attvals[defaultValueId];
                            // NOTE : data.spdesc --> label : chosenOne --> value
                            return (
                                <div key={outerIndex} className="">
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
                                <div className="">
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
                        data-attribute-id={data.attid} className ="" key={outerIndex}>
                        <span >{_.findWhere(data.attvals, { isdef: CONSTANTS.SHOPPING_CART_VARIABLES.YES })
                            && _.findWhere(data.attvals, { isdef: CONSTANTS.SHOPPING_CART_VARIABLES.YES }).attval} </span>
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
                            {this.showConfigurationProperty() }
                        </div>
                        {
                            _.isEmpty(this.props.editProduct.pricingObj) ?
                                <div className="col-lg-6 getProductPricing">
                                    <div className="pricingBlock getPricing ">
                                        <i className="pricingIcon"></i>
                                        <span className="pricingBtn" onClick={this.handlePreview.bind(null, false) }>
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
                        <span className=" backBtn pull-right" onClick={this.handlePreview.bind(null, false) }>Back </span>
                        <div className="clear"></div>
                    </div>
                </div>

            </div>
        )
    },


    openModal() {
        this.setState({
            cancelModalDisplay: true
        });
    },

    hideModal(event) {
        this.setState({ cancelModalDisplay: false });
    },

    renderAvailableZone() {
        var locationOptions = [];
        locationOptions = _.map(this.props.locations, (i) => {
            return {
                'label': i.locname,
                'value': i.locname
            };
        });
        let months = this.getMonths();
        let projects = this.props.editProduct.projects
        var configuration = this.props.editProduct.configuration;
        let editProduct = this.props.editProduct;
        var {selectedLocation} = this.props.editProduct.location;
        var {contractPeriod} = this.props.editProduct.period.contractPeriod || '';
        var project = User.getProject('projectName');
        return (
            <div className="formElementWrap clearfix marginBottom17 pull-left">
                <div className="configSelectionContainer">
                    <div className="contractSelection clearfix">
                        <div className="reactSelectRow pull-left">
                            <label htmlFor="selectLocation">Availability Zone</label>
                            <Select autofocus options={locationOptions}
                                simpleValue ref="selectLocation" name="selected-state"
                                value={editProduct.location.selectedLocation}
                                searchable={true} onChange={this.onChangeSelectedLocation} clearable={true}
                                placeholder="Select Zone"/>
                            <span className="errorMsg">{this.state.error.selectedLocation}</span>
                        </div>

                        {this.props.globalData.contractPeriod ? (
                            <div className="reactSelectRow contractPeriodBlock pull-left">
                                <label>Service Term</label>
                                <span>{this.props.globalData.contractPeriod}</span>
                            </div>
                        )
                            :
                            (<div className="reactSelectRow pull-left">
                                <label htmlFor="selectPeriod">Service Term (Months) </label>
                                <Select ref="contractPeriod" options={months}
                                    simpleValue  name="contractPeriod" value={this.props.editProduct.period.contractPeriod}
                                    searchable={true} clearable={true} onChange={this.updateContractPeriod} placeholder="Select Service Term"/>
                                <span className="errorMsg">{this.state.error.contractPeriod}</span>
                            </div>)
                        }
                        <div className="selectProject pull-left">
                            <div className="reactSelectRow selectProjectDropDown pull-left">
                                <label htmlFor="selectProject">Project</label>
                                {
                                    (CONSTANTS.BANDWIDTH_IDS.indexOf(this.props.subCatId) > -1 ) ?
                                    (
                                        <div className="createProject">
                                            <input type='text' value={Utility.getVal(this, 'props.globalData.project.projectName')} readonly/>
                                        </div>
                                    ):
                                    (
                                        this.props.editProduct.period.selectedProjectIsNew ? 
                                        <div className="createProject">
                                                <input type='text' defaultValue={this.props.editProduct.period.selectedProject} onChange={this.updateNewProject} />
                                            </div> :
                                            <Select autofocus={false} clearable={false} searchable={true} ref="selectedProject" options={projects} simpleValue  name="selectProject" value={this.props.editProduct.period.selectedProject}
                                                onChange={this.updateSelectProject} placeholder="Select Project"/>
                                    )
                                }
                            </div>

                            {
                                this.props.editProduct.period.selectedProjectIsNew ?
                                    this.props.globalData.selectedProjectIsNew ? (null) :
                                        <span className="createNew" onClick={this.unInitiateCreateProject}>Remove</span>
                                    : (CONSTANTS.BANDWIDTH_IDS.indexOf(this.props.subCatId) > -1 )? (null) :
                                        <span className="createNew" onClick={this.initiateCreateProject}>Create New</span>
                            }
                            <div className="clear"></div>
                        </div>
                    </div>
                </div>
            </div>
        )
    },

    render() {
        let maxStep = this.props.maxStepNumber
        let currentStep = this.props.activeStep.value
        let productList = this.props.productList;
        return (
            <form className="buyOptionForm" ref="addInfra">
                <div className="col-md-6">
                    <div className="formInnerWrap">
                        {this.renderAvailableZone() }
                        {this.renderPlanDetails() }
                    </div>
                </div>
                <div className="col-md-6 buyBorder padTop20 clearfix whiteBg">
                    <p className="head1 marginBottom17 marTop15 clearfix">Configuration</p>
                    {this.renderConfiguration() }
                </div>
                {
                    _.isEmpty(this.props.editProduct.pricingObj) ?

                        <NewBeforeRequestPricing
                            getIndividualPricingDetails = {this.getIndividualPricingDetails}
                            loaded = {this.props.loaded.pricing}
                            editProduct={this.props.editProduct}
                            />
                        :
                        <NewAfterRequestPricing
                            editProduct = {this.props.editProduct}
                            />
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
            </form>
        )
    }
});


var Product = createReactClass({

    updateSystemConfigurations(obj) {
        this.props.updateSystemConfigurations(obj);
    },

    render() {
        let obj = _.first(this.props.obj.sugar); //added by deep as of now
        let cmsObj = Utility.getVal(this, 'props.obj.cms.pname');
        let pdesc = obj.pdesc;
        let pid = obj.pid;
        let propPid = this.props.editProduct.configuration.pid;
        let config = pid == propPid ? this.props.editProduct.configuration : _.first(this.props.obj.sugar)
        let pname = cmsObj || pdesc;
        return (
            <li className={pid == propPid ? 'configSelected clearfix hoverData' : 'hoverData clearfix'}
                onClick={this.updateSystemConfigurations.bind(null, config) }>
                <input type="radio" checked={pid == propPid}/>
                <span className="labelData planName">{this.props.getSlicedProdName(pname)}</span>
                <label className={this.props.showProdNameOnHover(pname) }>{pname}</label>
                { this.props.showSummarisedConfigsByTypeForReview(config.ptype, config.sprod) }
                <span className="startingPrice">&#8377;{obj.mrc}</span>
            </li>
        )
    }
});

export default AddInfra;
