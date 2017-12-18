import { Link } from 'react-router';
import CONSTANTS from '../../../constants/app-constant';
import Session from '../../../mixins/sessionUtils';
import Utility from '../../../mixins/basicUtils';
import UI from '../../../mixins/ui';

var ListCartItemForHeader = createReactClass({

  mixins: [Utility, UI],
  contextTypes: {
    router: React.PropTypes.object
  },

  getInitialState() {
    return {
      collapse: false
    }
  },

  editCart(index) {
    if (_.isNumber(index)){
      Session.set('tempCartIndex',index);
      location.hash = "#/shopping-cart";
    }
  },

  render() {
    let product=this.props.product;
    let billgroup = _.first(product.pricingObj.billgroup)
    let index = this.props.index;
    // TODO :
    // billgroup.potc_tax
    // billgroup.pmrc_tax -- these values are to be totalled

    return (
      <div className="cartListingData">
            <div className="productName listingBlock">
              <span>{product.location.product || 'NA'}</span>
              <span>{product.configuration.pname || 'NA'}</span>
            </div>
            <div className="quantity  listingBlock">
              <span>{product.configuration.qty}</span>
            </div>
            <div className="contract  listingBlock">
              <span>{product.period.contractPeriod ?
                product.period.contractPeriod + ' Months' : 'NA'}</span>
            </div>
            <div className="location  listingBlock">
              <span>{product.location.selectedLocation || 'NA'}</span>
            </div>
            <span className="editBtn listingBlock" onClick={this.editCart.bind(null,index)}>Edit</span>
      </div>)
  }
});

export default ListCartItemForHeader;
