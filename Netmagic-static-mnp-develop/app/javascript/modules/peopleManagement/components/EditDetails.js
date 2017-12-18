import { Link } from 'react-router';
import CONSTANTS from '../../../constants/app-constant';
import Utility from '../../../mixins/basicUtils';
import Session from '../../../mixins/sessionUtils';
import Select from 'react-select';
import http from '../../../mixins/restutils';
import CountryInfo from './CountryInfo';
import UI from '../../../mixins/ui';

var EditDetails = createReactClass({

    getInitialState() {
        var contact = this.props.data;
        return {
            contactSubTypeBeans: [],
            contactTypes: [],
            existingContactNumbers: contact && contact.contactNumbersInfoList || [],
            newContactNumbers: [{
                contactNumber: null,
                contactNumberCategory: null,
                countryCode: null
            }],
            nmUser: false
        };
    },

    componentWillReceiveProps(nextProps) {
        this.setState({
            contactTypes:
            nextProps.configData && nextProps.configData.mynmContactNumberTypeBeans || []
        })
    },

    setContactSubType(data) {
        return function () {
            this.setState({
                contactSubTypeBeans: data.contactSubTypeBeans || []
            });
        }.bind(this);
    },

    addContactNumber() {
        this.state.newContactNumbers.push({
            contactNumber: null,
            contactNumberCategory: null,
            countryCode: null
        });
        this.setState(this.state);
    },

    setAssociatedCustomer(data, event) {
        if (data) {
            this.props.getProjects(data, event.currentTarget.checked);
        }
    },

    getProjects() {
        var projects = Utility.getVal(this.state, 'associatedCustomer.projectList') || [];
        return _.map(projects, (item, key) => {
            return { value: key, label: item };
        });
    },

    showCallingDays() {
        var callingDays = Utility.getVal(this.props, 'configData.mynmContactCallingTypeBeans') || [];
        return _.map(callingDays, (item, key) => {
            return (<li key={key} data-id={item.contactCallingTypeId}>{item.contactCallingTypeName}</li>);
        });
    },

    showCallingHours() {
        var callingHours = Utility.getVal(this.props, 'configData.mynmContactHoursTypeBeans') || [];
        return _.map(callingHours, (item, key) => {
            return (<li key={key} data-id={item}>{item}</li>);
        });
    },

    addTypeToContact(typeName, isExisting, index) {
        var contacts = this.state[isExisting ? 'existingContactNumbers' : 'newContactNumbers'];
        contacts[index].contactNumberCategory = typeName;
        if (isExisting) {
            var contact = Session.get('contact');
            contact.contactNumbersInfoList[index].contactNumberCategory = typeName;
            Session.set('contact', contact);
            this.setState({ existingContactNumbers: [] });
        }
        this.setState(this.state);
    },

    removeType(typeName, isExisting, index) {
        this.state.contactTypes = _.reject(this.state.contactTypes,
            (type) => type.contactNumberTypeName === typeName);
        this.addTypeToContact(typeName, isExisting, index);
        setTimeout(() => this.setState(this.state), 250);
    },

    getAvailableTypes() {
        var existingTypes = _.pluck(this.state.existingContactNumbers, 'contactNumberCategory');
        var newTypes = _.pluck(this.state.newContactNumbers, 'contactNumberCategory');
        return _.reject(this.state.contactTypes, (type) =>
            _.contains(_.union(existingTypes, newTypes), type.contactNumberTypeName));
    },

    showContactNumberTypes(isExisting, index) {
        return _.map(this.getAvailableTypes(), (item, key) => (<li key={key}
            onClick={this.removeType.bind(null, item.contactNumberTypeName, isExisting, index) }
            data-id={item.contactNumberTypeId}>{item.contactNumberTypeName}</li>)
        );
    },

    showContactNumberInfo(type, key) {
        var data = _.findWhere(this.state.existingContactNumbers, { contactNumberCategory: type });
        return (
            <div className="formInputWrap marginBottom10 clearfix contactNumberInfo" key={key}>
                <div className="customSelectGroup pull-left">
                    <label for="countryCode">Contact Category</label>
                    <input type="text" data-id="contactCategory" value={type} readOnly="true"/>
                </div>
                <CountryInfo data={data}/>
                <div className="customSelectGroup pull-left">
                    <label for="countryCode">
                        Contact Number
                        {type === 'Mobile' ? (<span className="mandatoryStar">*</span>) : (null) }
                    </label>
                    <input type="text" maxLength="10" className={type === 'Mobile' ? 'phone mandatory' : 'phone'} data-id="contactNumber" defaultValue={data && data.contactNumber}/>
                </div>
            </div>
        )
    },

    showCustomers() {
        var customers = this.props.customers || this.props.data.mynmRoleFunctionsBeanList;
        return _.map(customers, (data, key) =>
            (<li data-id={data.associteCustomerID || data.cvCrmId} key={key} className="clearfix">
                <label>
                    <div className="pull-left"> {data.associteCustomerName || data.cvName}</div>
                    <input type="checkbox" className="pull-right" onClick={this.setAssociatedCustomer.bind(this, data) } />
                </label>
            </li>))
    },

    showProjects() {
        return _.isArray(this.props.projects) && this.props.projects.map((data, key) => {
            return (<li data-id={data.projectId} key={key} className="clearfix">
                <label>
                    <div className="pull-left"> {data.projectName} </div>
                    <input type="checkbox" className="pull-right" onClick={this.props.addOrRemoveProject.bind(null, data) }/>
                </label>
            </li>)
        })
    },

    getMyNMUser(contact) {
        var template = null;
        if (contact) {
            if (contact.isMynmUser === CONSTANTS.YES && !this.props.nmUser) {
                template = null
            } else if ((contact.isMynmUser === CONSTANTS.NO) && !this.props.nmUser) {
                template = (<div className="clearfix pull-right">
                    <span className="switchBtn pull-left" data-id={this.props.data.isMynmUser}
                        onClick={this.props.updateIsMyUser}>MNP User</span>
                    <span className="hide pull-left" data-id={this.props.data.isMynmUser}
                        onClick={this.props.updateIsMyUser}>Cancel</span>
                </div>)
            } else if ((contact.isMynmUser === CONSTANTS.YES) && this.props.nmUser) {
                template = (<div className="clearfix pull-right">
                    <span className="disableSwitch pull-left"
                        data-id={this.props.data.isMynmUser}
                        onClick={this.props.updateIsMyUser}>MNP User</span>
                    <span className="show whiteCancelBtn pull-left" data-id={this.props.data.isMynmUser}
                        onClick={this.props.updateIsMyUser}>Cancel</span>
                </div>)
            }
        }
        return template;
    },

    dropDownClassSet(data) {
        return UI.classSet({
            'customSelectGroup': true,
            'contactTypeSelect': true,
            'customSelectCheckbox': true,
            'pull-left': true,
            'disableDropDown': !(_.isArray(data) && data.length)
        });
    },
    
    render() {
        let contact = this.props.data;
        let associatedCustomer = Utility.getVal(contact, 'rolesModulesList.[0].rolesTypeBeans') || [];
        let {customers, projects} = this.props;
        const NA = CONSTANTS.NOT_AVAILABLE;
        return (
            <div className="editDetailsForm">
                <form type="POST" action="#" className="formTypeInline raiseTicketForm form-inline" name="ticketDetailForm">
                    <div className="formInnerWrap">
                        <div className="rowContact">
                            <div className="formInputWrap clearfix">
                                <div className={this.dropDownClassSet(customers)}>
                                    <label for="contactType">Associate Customer</label>
                                    <ul className="customSelectList timeDropDowns" id="contactType">
                                        <li>Select Associate Customer</li>
                                        {this.showCustomers() }
                                    </ul>
                                    <p className="selectContent"></p>
                                </div>
                                <div className={this.dropDownClassSet(projects)}>
                                    <label for="contactType">Project Name</label>
                                    <ul className="customSelectList timeDropDowns" id="contactType">
                                        <li>Select Project Name</li>
                                        {this.showProjects() }
                                    </ul>
                                    <p className="selectContent"></p>
                                </div>
                                {this.getMyNMUser(contact) }
                            </div>
                        </div>

                        <div className="rowContact">
                            <h4 className="rowContactTitle">Basic Info</h4>
                            <div className="formInputWrap clearfix">
                                <div className="formElementWrap pull-left">
                                    <label for="firstname">First name<span className="mandatoryStar">*</span></label>
                                    <input type="text" id="firstname" ref="firstName" className="mandatory" defaultValue={contact.firstName} onKeyPress={UI.checkIfAlphabets}/>
                                </div>
                                <div className="formElementWrap pull-left">
                                    <label for="lastname">Last name<span className="mandatoryStar">*</span></label>
                                    <input type="text" id="lastname" ref="lastName" className="mandatory" defaultValue={contact.lastName} onKeyPress={UI.checkIfAlphabets}/>
                                </div>
                                <div className="formElementWrap pull-left">
                                    <label for="designation">Designation<span className="mandatoryStar">*</span></label>
                                    <input type="text" id="designation" ref="designation" className="mandatory" defaultValue={contact.designation}/>
                                </div>
                            </div>
                        </div>
                        <div className="rowContact clearfix">
                            <h4 className="rowContactTitle">Contact Info
                            </h4>
                            <div className="">
                                <div className="contactInfo clearfix">
                                    <div className="emailWrapper">
                                        <div className="formElementWrap pull-left">
                                            <label for="email">Primary Email ID<span className="mandatoryStar">*</span></label>
                                            {
                                                this.props.editable ?
                                                <input type="text" id="email" ref="email" className="email mandatory prefilledField" readOnly="true" defaultValue={contact.primaryEmailId}/> :
                                                <input type="text" id="email" ref="email" className="email mandatory" defaultValue={contact.primaryEmailId}/>
                                            }
                                        </div>
                                        <div className="formElementWrap pull-left">
                                            <label for="email">Secondary Email ID</label>
                                            <input type="text" id="secondaryEmail" ref="secondaryEmail" className="email" defaultValue={contact.secondaryEmailId || ''}/>
                                        </div>
                                    </div>
                                    <div className="contactWrapper">
                                        <div className="customSelectGroup contactTypeSelect pull-left">
                                            <label for="contactType">Calling Hours</label>
                                            <ul className="customSelectList timeDropDowns" id="callingHours" ref="callingHours">
                                                <li>Select Calling Hours</li>
                                                {this.showCallingHours() }
                                            </ul>
                                            <p className="selectContent">{contact && contact.callingPreferredTime}</p>
                                        </div>
                                        <div className="customSelectGroup callTo pull-left" data-id="days">
                                            <label for="countryCode">Days</label>
                                            <ul className="customSelectList timeDropDowns" id="callingDays" ref="callingDays">
                                                <li>Select Calling Days</li>
                                                {this.showCallingDays() }
                                            </ul>
                                            <p className="selectContent">{contact && contact.callingPreferredDaysTypeName}</p>
                                        </div>
                                    </div>
                                </div>
                                <div className="phoneWrap clearfix">
                                    <div className="formElementWrap pull-left clearfix">
                                        {
                                            this.state.contactTypes.map((data, key) => {
                                                return this.showContactNumberInfo(data.contactNumberTypeName, key)
                                            })
                                        }
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        );
    }
});

export default EditDetails;
