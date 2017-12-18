import { Link } from 'react-router';

var AddContactAddress = createReactClass({

    updateBillAddressInformation(address) {
        this.props.updateBillAddressInformation(address);
    },

    updateSuppAddressInformation(address) {
        this.props.updateSuppAddressInformation(address);
    },

    render(){
        return(
            <div className="addressBlock col-md-3 col-lg-3 col-xs-12">
                <h5>Please define billing address.</h5>
                <p>Before placing order you must have one of these
                    <span>*</span>
                </p>
                <ul className="billingAddressList">
                    {
                        _.map(this.props.billAddressList, (address, key) => {
                            let style = _.isEqual(address,this.props.selectedBillAddress) ? "checkedBox" : ""
                            return (
                                <li key={key} className={style} onClick={this.updateBillAddressInformation.bind(null,address)}>
                                    <i className="pull-left addressIcon"></i>
                                    <div className="pull-right addressDetails">
                                        <span>{address.name}</span>
                                        <span>+91-8769086548(constant)</span>
                                        <p>{address.address}</p>
                                    <span className="redCheck"></span>
                                    </div>
                                    <div className="clear"></div>
                                </li>
                            )
                        })
                    }
                </ul>
                <h5>Please define support to address.</h5>
                <p>Before placing order you must have one of these
                    <span>*</span>
                </p>
                <ul className="billingAddressList">
                    {
                        _.map(this.props.suppAddressList, (address, key) => {
                            let style = _.isEqual(address,this.props.selectedSuppAddress) ? "checkedBox" : ""
                            return (
                                <li key={key} className={style} onClick={this.updateSuppAddressInformation.bind(null,address)}>
                                    <i className="pull-left addressIcon"></i>
                                    <div className="pull-right addressDetails">
                                        <span>{address.name}</span>
                                        <span>+91-8769086548(constant)</span>
                                        <p>{address.address}</p>
                                        { _.isEqual(address,this.props.selectedSuppAddress) ? <span className="redCheck"></span> : (null) }
                                    </div>
                                    <div className="clear"></div>
                                </li>
                            )
                        })
                    }
                </ul>
            </div>
        )
    }
});

export default AddContactAddress;
