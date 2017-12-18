import { Link } from 'react-router';
import CONSTANTS from '../../../constants/app-constant';
import Session from '../../../mixins/sessionUtils';
import Utility from '../../../mixins/basicUtils';
import UI from '../../../mixins/ui';
import { Modal, ModalHeader, ModalTitle, ModalClose, ModalBody, ModalFooter} from 'react-modal-bootstrap';
import BeforeRequestPricing from './BeforeRequestPricing';
import AfterRequestPricing from './AfterRequestPricing';
import ListInfraItem from './ListInfraItem';


var ListInfraProduct = createReactClass({

  mixins: [Utility, UI],
    contextTypes: {
    router: React.PropTypes.object
  },

  getInitialState() {
    return {
      removeModalDisplay : false,
      index : ''
    }
  },

  openModal (i) {
    this.setState({
      removeModalDisplay: true,
      index : i
    });
  },

  hideModal (event) {
    this.setState({
       removeModalDisplay: false
    });
  },

  removeProductFromCart(index) {
    this.hideModal();
    this.props.removeProductFromCart(index);
  },

  enableEditForProduct(i){
      this.props.enableEditForProduct(i);
  },

  render() {

    return (
      <div className="row cartListContainer">
        <ul className="listHeader">
          <li className="productType"><span>Product Type</span><span>& Code</span></li>
          <li className="contract"><span>Contract</span></li>
          <li className="location"><span>Location</span></li>
          <li className="quantity"><span>Quantity</span></li>
          <li className="oneTime"><span>One Time Charges</span></li>
          <li className="monthly"><span>Monthly Charges</span></li>
        </ul>
        {_.map(this.props.products, (product, i) => {
          return (
            <ListInfraItem
            key = {i}
            product={product}
            enableEditForProduct = {this.enableEditForProduct}
            removeProductFromCart = {this.props.removeProductFromCart}
            getIndividualPricingDetails = {this.props.getIndividualPricingDetails}
            showConfigurationsByTypeForListInfra = {this.props.showConfigurationsByTypeForListInfra}
            hideModal = {this.hideModal}
            openModal = {this.openModal}
            index = {i}
            />
            )
        })}
        <Modal className="customModalWrapper" isOpen={this.state.removeModalDisplay} onRequestHide={this.hideModal}>
          <ModalHeader>
            <ModalClose onClick={this.hideModal} />
            <span className="productType">{ (this.state.index || this.state.index === 0) && this.state.removeModalDisplay  ? this.props.products[this.state.index || 0].configuration.pdesc || '' : (null)}</span>
            <span className="productCode">{ (this.state.index || this.state.index === 0) && this.state.removeModalDisplay ? this.props.products[this.state.index || 0].configuration.pname : (null)}</span>
          </ModalHeader>
          <ModalBody>
            <span className="questionIcon">?</span>
            <p className="warningMsg">Are you sure you want to permanently delete this item form the cart ?</p>
          </ModalBody>
          <ModalFooter>
            <input type="submit" onClick={this.removeProductFromCart.bind(null, this.state.index)}
              className="removeBtn pull-right" value="Remove" />
            <input type="submit" className="cancelLink pull-right"
              value="Cancel" onClick={this.hideModal} />
          </ModalFooter>
        </Modal>
      </div>
          )
        }
});

export default ListInfraProduct;
