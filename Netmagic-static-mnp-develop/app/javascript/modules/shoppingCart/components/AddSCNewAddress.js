import { Link } from 'react-router';
import UI from '../../../mixins/ui';
import NewBillToAddress from './NewBillToAddress';
import NewSuppToAddress from './NewSuppToAddress';

var AddSCNewAddress = createReactClass({

    render() {
        let {billAddressList, suppAddressList} = this.props;
        return (<div>
            <NewBillToAddress 
            billAddressList={billAddressList}
            showBillToAddresses={this.showBillToAddresses}
            updateBillAddressInformation={this.props.updateBillAddressInformation}
            selectedBillAddress={this.props.selectedBillAddress}
            />
            <NewSuppToAddress
            suppAddressList={suppAddressList}
            showSuppToAddresses={this.showSuppToAddresses}
            updateSuppAddressInformation={this.props.updateSuppAddressInformation}
            selectedSuppAddress={this.props.selectedSuppAddress}
            />
        </div>
        )
    }
});

export default AddSCNewAddress;