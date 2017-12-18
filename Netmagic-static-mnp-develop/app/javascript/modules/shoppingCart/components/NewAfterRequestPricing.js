import { Link } from 'react-router';

var AfterRequestPricing = createReactClass({
    render() {
        let billGroup = _.first(this.props.editProduct.pricingObj.billgroup);
        let pricing = _.first(billGroup.items) || {};
        return (
            <div className='productPricingWrap'>
                <p className="head1 marginBottom17 marTop15 clearfix">Product Pricing</p>
                <ul className='clearfix'>
                    <li>
                        <label>One Time Charges</label>
                        <span>&#8377; {billGroup.potc_sale || 0}</span>
                    </li>
                    <li className='hide'>
                        <label>Monthly charges</label>
                        <span>&#8377; {pricing.spmrc_sale || 0}</span>
                    </li>
                    <li className='blackText'>
                        <label>Total</label>
                        <span>&#8377; {billGroup.pmrc_sale || 0} </span>
                    </li>
                </ul>
            </div>
        )
    }
});

export default AfterRequestPricing;
