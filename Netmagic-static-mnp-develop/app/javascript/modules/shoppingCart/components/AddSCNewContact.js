import { Link } from 'react-router';
import CONSTANTS from '../../../constants/app-constant';
import UI from '../../../mixins/ui';
import Utility from '../../../mixins/basicUtils';

var AddSCNewContact = createReactClass({

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

    showContacts() {
        let contacts = (<li className="clearfix">No Contacts Available</li>);
        let {contactList} = this.props;
        if(_.isArray(contactList) && contactList.length) {
            contacts = _.map(contactList, (contact, key) => {
                return (<li className="clearfix" key={key}>
                    <div className="userPic">
                        <i className="NMIcon-userGrey"></i>
                    </div>
                    <div className="userNameRoleWrap clearfix">
                        <div className="userNameEmail">
                            <span className="pinkText">{contact.first_name}</span>
                            <span className="userEmail">{contact.emailId || '-' }</span>
                        </div>
                        <div className="userRoles clearfix">
                            <ul className="clearfix">
                                {this.showContactTypes(contact)}
                            </ul>
                        </div>
                    </div>
                </li>)
            });
        }
        return contacts;
    },

    showContactTypes(contact) {
        let contactTypes = (null);
        const CONTACT_TYPES = CONSTANTS.SC_CONTACTS;
        if(_.isArray(CONTACT_TYPES) && CONTACT_TYPES.length) {
            contactTypes = _.map(CONTACT_TYPES, (data, key) => {
                var styling = contact[data.value] ? 'checkedContact' : '';
                return (<li className={styling} key={key} onClick={this.props.updateContactInformation.bind(null, contact, data)}>
                    <span className="checkBox"></span>
                    <span className="contactType">{data.label}</span>
                </li>)
            });
        }
        return contactTypes;
    },

    render() {
        return (<div>
            <div className={this.getParentClassSet()}>
                <div className="aboutProjectInfo pull-left">
                    <h5>Please define SOF contacts.</h5>
                    <p><span className="grayText">Before placing order you must have to define </span> Billing, IT Operations, SPOC and Authozied Signatory <span className="grayText">contact types.</span> <span className="mandatoryStar">*</span> </p>
                </div>
                <div className="addNewContacts pull-right">
                    <span className={this.getAccordianBtnClass()}onClick={this.toggle}><span></span></span>
                </div>
            </div>
            <div className={this.getToggleClassSet()}>
                <ul className="contactListing clearfix">
                    {this.showContacts()}
                </ul>
            </div>
        </div>
        )
    }
});

export default AddSCNewContact;