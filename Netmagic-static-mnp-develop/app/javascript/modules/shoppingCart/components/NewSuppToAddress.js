import UI from '../../../mixins/ui';

var NewBillToAddress = createReactClass({
    getInitialState() {
        return {
            collapse: false
        }
    },

    getToggleClassSet() {
        return UI.classSet({
            'projectInfoData': true,
            'show': this.state.collapse,
            'hide': !this.state.collapse
        });
    },

    getAccordianBtnClass() {
        return UI.classSet({
            'accordianBtn': true,
            'active': this.state.collapse,
            "pull-right": true
        });
    },

    toggle() {
        this.setState({ collapse: !this.state.collapse })
    },

    getParentClassSet() {
        return UI.classSet({
            'projectInfoBox': true,
            'clearfix': true,
            'closed': !this.state.collapse,
        });
    },

     showSuppToAddresses(addressList) {
        let billToAdd = null;
        if(_.isArray(addressList) && addressList.length) {
            billToAdd = _.map(addressList, (address, key) => {
                return (
                    <li className="clearfix" key={key} onClick={this.props.updateSuppAddressInformation.bind(null,address)}>
                        <div className="selectCompany">
                            <input type="radio" checked={_.isEqual(address,this.props.selectedSuppAddress)}/>
                        </div>
                        <div className="userNameRoleWrap clearfix">
                            <div className="companyNamePhone">
                                <span className="pinkText">{address.name}</span>
                                <span className="companyPhone">{'-'}</span>
                            </div>
                            <div className="companyAddress clearfix">
                                <p>{address.address}
                                </p>
                            </div>
                        </div>
                    </li>
                )
            });
        }
        return billToAdd;
    },

    render() {
        let {suppAddressList} = this.props;
        return (<div>
            <div className={this.getParentClassSet()}>
                <div className="aboutProjectInfo pull-left">
                    <h5>Please define support to address.</h5>
                    <p><span className="grayText">Before placing order you must have to define one of these.*</span></p>
                </div>
                <div className="addNewContacts pull-right">
                    <span className={this.getAccordianBtnClass()} onClick={this.toggle}><span></span></span>
                </div>
            </div>
            <div className={this.getToggleClassSet()}>
                <ul className="contactListing clearfix">
                    {this.showSuppToAddresses(suppAddressList) }
                </ul>
            </div>
        </div>
        )
    }
});

export default NewBillToAddress;