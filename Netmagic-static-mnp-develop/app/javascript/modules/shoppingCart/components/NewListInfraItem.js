import { Link } from 'react-router';
import CONSTANTS from '../../../constants/app-constant';
import Session from '../../../mixins/sessionUtils';
import Utility from '../../../mixins/basicUtils';
import UI from '../../../mixins/ui';
import { Modal, ModalHeader, ModalTitle, ModalClose, ModalBody, ModalFooter } from 'react-modal-bootstrap';
import BeforeRequestPricing from './BeforeRequestPricing';
import AfterRequestPricing from './AfterRequestPricing';
import moment from 'moment';

var NewListInfraItem = createReactClass({

    mixins: [Utility, UI],
    contextTypes: {
        router: React.PropTypes.object
    },

    getInitialState() {
        return {
            collapse: false
        }
    },

    toggle() {
        this.setState({ collapse: !this.state.collapse })
    },

    getToggleClassSet() {
        return this.classSet({
            'cartDetailsBlock': true,
            'show': this.state.collapse,
            'hide': !this.state.collapse
        });
    },

    getAccordianBtnClass() {
        return this.classSet({
            'accordianBtn': true,
            'active': this.state.collapse
        });
    },

    getParentClassSet() {
        return UI.classSet({
            'projectInfoBox': true,
            'clearfix': true,
            'infraListing': true,
            'selected': true,
            'closed': !this.state.collapse,
        });
    },

    checkFireWallClassSet(pdesc) {
        return UI.classSet({
            'visibilityHidden': CONSTANTS.REGEX.FIREWALL_REGEX.test(pdesc)
        });
    },

    render() {
        let product = this.props.product;
        let currentDate = moment().format(CONSTANTS.STANDARD_FORMAT);
        let expiryDate = product.salesEndDate || '';
        return (<div key={this.props.index}>
            <div className="listOfConfiguration">
                <div className={this.getParentClassSet()}>
                    <div className="infraName">
                        <div className="checkboxWrap pull-left">
                            <label className="cssCheckBox hide">
                                <input type="checkbox" checked />
                                <span className="NMIcon-checkbox lightColorLabel"></span>
                            </label>
                        </div>
                        <h4>{product && product.pdesc}<span className="smallTag green">{product && product.ServiceType}</span>
                            {
                                !_.isEmpty(expiryDate) && (expiryDate < currentDate) ? <span className="smallTag red">Expired</span> : (null)
                            }
                        </h4>
                        <div className="infraCode">
                            {product && product.pname}
                        </div>
                    </div>
                    <div className="infraQuantityPrice">
                        <div className="infraQunatity">
                            {this.props.addInfraEnabled ? <span className={this.checkFireWallClassSet(product && product.pdesc)}><span>Qty. </span><input type='text' min='1' max='1000' value={product.qty} onChange={this.props.updateQuantity}/></span>
                                : <span className={this.checkFireWallClassSet(product && product.pdesc)}><span>Qty. </span> <input type="text" value={product && product.qty || 1}/></span>
                            }
                        </div>
                        <label className="oneTimeCharge">&#8377; {product && product.spotc_sale}</label>
                        <label className="monthlyCharge">&#8377; {product && product.spmrc_sale} <span className="perUnitLabel">Per Unit</span></label>
                        <span className="accordianBtn" onClick={this.toggle} className={this.getAccordianBtnClass() }>
                            <span></span>
                        </span>
                    </div>
                </div>

                <div className={this.getToggleClassSet() }>
                    <div className="projectInfoData clearfix">
                        <div className="infraDetailsInfo">
                            <ul>
                                {this.props.addInfraEnabled ?  this.props.showConfigurationsByType(product.ptype, product.sprod): this.props.showConfigurationsByTypeForListInfra(product.ptype, product.sprod) }
                                {this.props.addInfraEnabled ?  
                                    <li className="editRemoveInfra clearfix">
                                        <span className="btn blueBorderd" onClick={this.props.updateCart}>Save</span>
                                        <span className="redLink" onClick={this.props.cancelUpdate.bind(null, product, this.props.billGroup)}>Cancel ?</span>
                                    </li> : <li className="editRemoveInfra clearfix">
                                    {
                                        _.isEmpty(expiryDate) ?
                                        <span className="btn blueBorderd" 
                                        onClick=        
                                            {this.props.enableEditForProduct.bind
                                                (null, product, 
                                                this.props.billGroup)}
                                                >Edit</span> :
                                        (expiryDate < currentDate) ?
                                        (null) :
                                        <span className="btn blueBorderd" 
                                        onClick=        
                                            {this.props.enableEditForProduct.bind
                                                (null, product, 
                                                this.props.billGroup)}
                                                >Edit</span>
                                    }
                                    <span className="redLink" onClick={this.props.removeInfraFromList.bind(null, product, this.props.billGroup)}>Remove ?</span>
                                </li>}
                            </ul>
                        </div>
                        <div className="excessBillingCharges">
                            <label className="pull-left">excess  billing charges</label>
                            <label className="pull-right">Price</label>
                            {this.props.updatedConfigItem ? 
                                    <ul className='clearfix'>
                                        <li className="paymentProcessBtns">
                                            <span onClick={this.props.getIndividualPricingDetails.bind(null, this.props.billGroup)} className='redBorderBtn'>Calculate</span>
                                        </li>
                                    </ul>
                                : <ul className="clearfix">
                                <li className="clearfix">
                                    <label className="pull-left">Variable Charges</label>
                                    <span className="pull-right rateCardPrice">&#8377; {product && product.spvc_sale}</span>
                                </li>
                            </ul>}
                        </div>
                    </div>
                </div>
            </div>
        </div>)
    }
});

export default NewListInfraItem;
