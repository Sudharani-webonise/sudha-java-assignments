import { Link } from 'react-router';
import Loader from 'react-loader';
import LoaderOptions from '../../../mixins/loader';

var NewBeforeRequestPricing = createReactClass({
    render() {
        let {getIndividualPricingDetails, editProduct} = this.props;
        return (
            <Loader loaded={!this.props.loaded} top="50%" left="50%" options={LoaderOptions}>
                <div className='productPricingWrap'>
                    <p className="head1 marginBottom17 marTop15 clearfix">Product Pricing</p>
                    <ul className='clearfix'>
                        <li>
                            <a onClick={_.isEmpty(editProduct.configuration) ? '' : getIndividualPricingDetails} className={_.isEmpty(editProduct.configuration) ? 'redBorderBtn disableLinkBtn' 
                                : 'redBorderBtn'}>Calculate</a>
                        </li>
                    </ul>
                </div>
            </Loader>
        )
    }
});

export default NewBeforeRequestPricing;
