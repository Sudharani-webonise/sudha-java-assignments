import UI from '../../../mixins/ui';
import AddSCNewContact from './AddSCNewContact';
import AddSCNewAddress from './AddSCNewAddress';
import Utility from '../../../mixins/basicUtils';
import CONSTANTS from '../../../constants/app-constant';

var GroupedItems = createReactClass({
    getInitialState() {
        return {
            collapse: false,
            selectedCodes: {
                billing: false,
                operations: false,
                spoc: false,
                signatory: false,
            },
            selectedSuppAddress: {},
            selectedBillAddress: {},
            contactList: []
        }
    },

    componentWillReceiveProps(nextProps) {
        this.state.contactList = Utility.clone(nextProps.contactList);
        _.each(this.state.contactList, (contact) => {
            contact.billing = _.isEqual(contact.contactid, nextProps.billGroup.bill_cont);
            contact.operations = _.isEqual(contact.contactid, nextProps.billGroup.tech_cont);
            contact.signatory = _.isEqual(contact.contactid, nextProps.billGroup.auth_sign);
            contact.spoc = _.isEqual(contact.contactid, nextProps.billGroup.spoc_cont);
        });
        this.state.selectedBillAddress = _.findWhere(nextProps.billAddressList, { acc_ban_id: this.props.billGroup.billtoadd });
        this.state.selectedSuppAddress = _.findWhere(nextProps.suppAddressList, { acc_ban_id: this.props.billGroup.supptoadd });
        this.setState(this.state);
    },

    updateContactInformation(contact, code) {
        let {billGroup} = this.props;
        let {selectedCodes, contactList} = this.state;
        var selectedContact = _.findWhere(contactList, contact) || {} ;
        if(selectedContact[code.value]){
            contactList = _.map(contactList, (contact, key) => {
                if(_.isEqual(contact, selectedContact))
                {
                    contact[code.value] = false;
                    selectedCodes[code.value] = false;
                }
                return contact;
            });
        }
        else{
            contactList =  _.map(contactList, (contact, key) => {
                if(_.isEqual(contact, selectedContact))
                {
                    contact[code.value] = true;
                    selectedCodes[code.value] = contact.contactid;
                }
                else{
                    contact[code.value] = false;
                }
                return contact
            });
        }
        selectedCodes = selectedCodes;
        billGroup.auth_sign = selectedCodes.signatory || billGroup.auth_sign;
        billGroup.bill_cont = selectedCodes.billing || billGroup.bill_cont;
        billGroup.spoc_cont = selectedCodes.spoc || billGroup.spoc_cont;
        billGroup.tech_cont = selectedCodes.operations || billGroup.tech_cont;
        this.setState(this.state);
    },
    
    updateBillAddressInformation(address) {
        let state = this.state;
        let {billGroup} = this.props;
        _.isEqual(address, state.selectedBillAddress) ? state.selectedBillAddress = {} : state.selectedBillAddress = address;
        let selectedBillAddress = state.selectedBillAddress;
        billGroup.billtoadd = selectedBillAddress && selectedBillAddress.acc_ban_id || billGroup.billtoadd;
        this.setState(state);
    },

    updateSuppAddressInformation(address) {
        let state = this.state;
        let {billGroup} = this.props;
        _.isEqual(address, state.selectedSuppAddress) ? state.selectedSuppAddress = {} : state.selectedSuppAddress = address;
        let selectedSuppAddress = state.selectedSuppAddress;
        billGroup.supptoadd = selectedSuppAddress && selectedSuppAddress.acc_ban_id || billGroup.supptoadd;        
        this.setState(this.state);
    },

    getToggleClassSet() {
        return UI.classSet({
            'cartProjectInfoWrap': true,
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

    render() {
        let billGroup = this.props.billGroup;
        return (<li>
            <div className='projectNameWrap clearfix selected'>
                <h4 className="projectName">{billGroup && billGroup.projectname || CONSTANTS.NOT_AVAILABLE} 
                    <span className={this.getAccordianBtnClass()} onClick={this.toggle}><span></span>
                    </span>
                </h4>
                <p>Please provide us below details before you place your order.</p>
            </div>
            <div className={this.getToggleClassSet()}>
                <AddSCNewContact 
                    contactList={this.state.contactList}
                    updateContactInformation={this.updateContactInformation}
                />
                <AddSCNewAddress
                    billAddressList = {this.props.billAddressList}
                    suppAddressList = {this.props.billAddressList}
                    updateBillAddressInformation={this.updateBillAddressInformation}
                    selectedBillAddress={this.state.selectedBillAddress}
                    selectedSuppAddress={this.state.selectedSuppAddress}
                    updateSuppAddressInformation={this.updateSuppAddressInformation}
                />
            </div>
        </li>
        )
    }
});

export default GroupedItems;
