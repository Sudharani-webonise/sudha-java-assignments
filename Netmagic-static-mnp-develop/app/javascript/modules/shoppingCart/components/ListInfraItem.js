import { Link } from 'react-router';
import CONSTANTS from '../../../constants/app-constant';
import Session from '../../../mixins/sessionUtils';
import Utility from '../../../mixins/basicUtils';
import UI from '../../../mixins/ui';
import { Modal, ModalHeader, ModalTitle, ModalClose, ModalBody, ModalFooter } from 'react-modal-bootstrap';
import BeforeRequestPricing from './BeforeRequestPricing';
import AfterRequestPricing from './AfterRequestPricing';

var ListInfraItem = createReactClass({

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

  render() {
    let product = this.props.product;
    let pricing = _.isEmpty(product.pricingObj) ? [] : _.first(_.first(product.pricingObj.billgroup).items)
    let i = this.props.index;

    return (
      <div className="listOfConfiguration">
        <li className="productPreviewContainer">
          <div className="cartUpperList">
            <div className="listDetails productType">
              <span>{product.location.product || 'NA'}</span>
              <span>{product.configuration.pname}</span>
            </div>
            <div className="listDetails contract">
              <span>{product.period.contractPeriod ?
                product.period.contractPeriod + ' Months' : 'NA'}</span>
            </div>
            <div className="listDetails location">
              <span>{product && product.location.selectedLocation || 'NA'}</span>
            </div>
            <div className="listDetails quantity">
              <span>{product && product.configuration.qty || 1}</span>
            </div>
            <div className="listDetails oneTime">
              <span>&#8377; {pricing && pricing.spotc_tax}</span>
            </div>
            <div className="listDetails monthly">
              <span>&#8377; {pricing && pricing.spmrc_tax}</span>
            </div>
            <span className="accordianBtn" onClick={this.toggle} className={this.getAccordianBtnClass()}>
              <span></span>
            </span>
          </div>
        </li>
        <div className={this.getToggleClassSet()}>
            <div className="clearfix">
                <div className="col-lg-6">
                  {this.props.showConfigurationsByTypeForListInfra(product.configuration.ptype, product.configuration.sprod)}
                </div>
                {
                  _.isEmpty(product.pricingObj) ?
                    <BeforeRequestPricing
                      getIndividualPricingDetails={this.props.getIndividualPricingDetails}
                      /> :
                    <AfterRequestPricing
                      editProduct={product}
                      />
                }
            </div>
            <div className="cntrlBtns">
              <span className="editBtn" onClick={this.props.enableEditForProduct.bind(null, i)}>Edit</span>
              <span className="removeLink" onClick={this.props.openModal.bind(null, i)}>Remove ?</span>
           </div>
        </div>
      </div>)
  }
});

export default ListInfraItem;
