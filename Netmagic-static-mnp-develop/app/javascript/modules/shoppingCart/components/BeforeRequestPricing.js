import { Link } from 'react-router';
import Loader from 'react-loader';
import LoaderOptions from '../../../mixins/loader';

var BeforeRequestPricing = createReactClass({
  render() {
    return (
      <Loader loaded={!this.props.loaded} top="50%" left="50%" options={LoaderOptions}>
        <div className="col-lg-6 getProductPricing">
            <div className="pricingBlock getPricing ">
                <i className="pricingIcon"></i>
                <span className="pricingBtn" onClick={this.props.getIndividualPricingDetails}>
                  Get Product Pricing
                </span>
            </div>
        </div>
      </Loader>
    )
  }
});

export default BeforeRequestPricing;
