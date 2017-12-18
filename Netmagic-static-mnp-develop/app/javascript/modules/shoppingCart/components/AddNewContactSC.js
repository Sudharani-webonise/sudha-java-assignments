import { Link } from 'react-router';
import CONSTANTS from '../../../constants/app-constant';
import Select from 'react-select';

var AddNewContactSC = createReactClass({

    updateContactInformation(contact,data) {
        this.props.updateContactInformation(contact,data)
    },

    updateSelectedProject(value) {
        this.props.updateSelectedProject(value)
    },

    updateSelectedContactType(value) {
        this.props.updateSelectedContactType(value)
    },

    saveAddContact() {
        var firstName = this.getElement.call(this, 'firstName').value;
        var lastName = this.getElement.call(this, 'lastName').value;
        var emailID = this.getElement.call(this, 'emailID').value;
        var contactNumber = this.getElement.call(this, 'ContactNumber').value;
        var saveAddContactObj = {
            firstName: firstName,
            lastName: lastName,
            emailID: emailID,
            contactNumber: contactNumber,
            saveAddContactObj: saveAddContactObj
        }
        this.props.saveAddContact(saveAddContactObj);
    },

    render(){
        let projects = this.props.projects
        return(
            <div className="newContactBlock col-md-9 col-lg-9 col-xs-12">
                <div className="pull-left sofDetails">
                    <span>Please define SOF contacts</span>
                    <p>Before placing order you must have to define
                        <span>
                            &nbsp; Billing, IT Operations, SPOC and Authorized Signatory
                        </span>
                        <span>&nbsp; contact types</span>
                        <span className="mandatoryStar">*</span>
                    </p>
                </div>
                { this.props.showAddNewContact ?
                <span className="pull-right addContactBtn hide" onClick={this.props.showHideAddNewContact}>Cancel Add Contact</span>
                :
                <span className="pull-right addContactBtn hide" onClick={this.props.showHideAddNewContact}>Add New Contact</span>
                }
                <div className="clear"></div>

                <div className="addContactInfo">
                    { this.props.showAddNewContact ?
                    <span>
                        <h4>Add New Contact :</h4>
                        <div className="infoBlock clearfix">
                            <div className="customInputBox pull-left">
                                <label htmlFor="">First Name</label>
                                <input type="text" name="firstName" />
                            </div>
                            <div className="customInputBox pull-left">
                                <label htmlFor="">Last Name</label>
                                <input type="text" name="LastName"/>
                            </div>
                            <div className="customSelectGroup pull-right">
                                <label htmlFor="selectProject" className="projectLabel">Project Name</label>
                                <Select ref="selectProject" autofocus onChange={this.updateSelectedProject}
                                    simpleValue name="selectProject" value="" options = {projects}
                                    searchable={true} placeholder="Select Project" />
                            </div>
                        </div>
                        <div className="infoBlock clearfix editableBlock">
                            <div className="customInputBox pull-left">
                                <label htmlFor="emailID">Email ID</label>
                                <input type="email" name="emailID"/>
                            </div>
                            <div className="customInputBox pull-left">
                                <label htmlFor="ContactNumber">Contact Number</label>
                                <input type="text" name="ContactNumber"/>
                            </div>

                            <span className="saveBtn pull-right">Save</span>
                            <div className="customSelectGroup pull-right">
                                <label htmlFor="contactType">Contact Type</label>
                                <Select ref="contactType" autofocus
                                    simpleValue name="contactType" value=""
                                    searchable={true} placeholder="Select Type" />
                            </div>
                        </div>
                    </span>
                    : (null) }

                </div>
                <ul className="contactSelectionList">
                    {
                        _.map(this.props.contactList, (contact, key) => {
                            return (
                                <li key={key} className="clearfix">
                                    <i className="userPinkIcon pull-left"></i>
                                    <div className="contactDetails pull-right">
                                        <span className="userName">{contact.first_name}</span>
                                        <span className="userEmail">{contact.emailId || '-' }</span>
                                        <ul className="contactTypeList">
                                            {
                                                _.map(CONSTANTS.SC_CONTACTS, (data, key) => {
                                                    var styling = contact[data.value] ? 'checkedContact' : '';
                                                    return (
                                                        <li key={key} className={styling} onClick={this.updateContactInformation.bind(null,contact,data)}>
                                                            <span className="checkBox"></span>
                                                            <span className="contactType">{data.label}</span>
                                                        </li>
                                                    )
                                                })
                                            }
                                        </ul>
                                    </div>
                                </li>
                            )
                        })
                    }
                </ul>
            </div>
        )
    }
});

export default AddNewContactSC;
