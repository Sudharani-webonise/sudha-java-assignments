import ReactDOM from 'react-dom';
import { useRouterHistory, IndexRoute, IndexRedirect, Router, Route } from 'react-router';
import { Link } from 'react-router';
import Select from 'react-select';
import CONSTANTS from '../../../constants/app-constant';
import Loader from 'react-loader';
import Dropzone from 'react-dropzone';
import LoaderOptions from '../../../mixins/loader';
import countryList from '../../../data/country';
import { Modal, ModalHeader, ModalTitle, ModalClose, ModalBody, ModalFooter } from 'react-modal-bootstrap';
import MsaDoc from './MsaDoc';
import Ticket from '../../../services/ticket';
import User from '../../../services/user';

var SignupForm = createReactClass({

    getInitialState() {
        return {
            countries: countryList,
            makeEditable: false,
            states: [],
            isOpen: false,
            attachments: []
        }
    },

    handleChange(event) {
        this.props.handleChange(event);
    },

    handleSwitch(value) {
        this.props.handleSwitch(value);
    },

    verifyUserData() {
        this.props.verifyUserData();
    },

    validate(id) {
        this.props.validate(id);
    },

    validateCorrectPassword() {
        var re = CONSTANTS.REGEX.PASSWORD;
        re.test(this.props.data.password) ? this.props.checkCorrectPassword(false) : this.props.checkCorrectPassword(true);
    },

    validatePassword() {
        this.props.data.password === this.props.data.confirmPassword ? this.props.updatePasswordError(false) : this.props.updatePasswordError(true);
    },

    verifyNumber(id) {
        this.props.verifyNumber(id);
    },

    updateCountryCode(value) {
        var countryCode = value;
        this.state.states = _.chain(this.state.countries)
            .where({ DESCRSHORT: countryCode })
            .value() || _.where(this.state.countries, { DESCRSHORT: _.first(this.state.countries).DESCRSHORT });
        this.setState(this.state);
        var countryData = _.find(this.state.countries, { DESCRSHORT: countryCode });
        this.props.updateCountryCode(countryData);
    },

    updateStateCode(value) {
        var stateCode = value;
        var stateData = _.find(this.state.states, { DESCR: stateCode });
        this.props.updateStateCode(stateData);
    },

    updateGovIdType(value) {
        this.props.updateGovIdType(value);
        this.props.data.govtIdNo = '';
    },

    addAttachment(files) {
        var updatedFiles = User.verifyAndFetchAttachmentsSignup(files, this.props.attachments);
        if (updatedFiles.length) {
            this.props.updateAttachments(updatedFiles);
        }
    },

    removeAttachment(event) {
        var attachments = this.props.attachments;
        attachments.splice(event.currentTarget.dataset.index, 1);
        this.props.updateAttachments(attachments);
    },

    showAttachments() {
        return _.map(this.props.attachments, (data, index) => {
            return (<li className="clearfix fileAttachment" key={index}>
                <i className="NMIcon-attachment pull-left"></i>
                <i className="cancel" data-index={index} onClick={this.removeAttachment}>X</i>
                <p className="pull-left">{data.name || 'File ' + index }</p>
            </li>)
        });
    },

    gethtmlBaseType() {
        var userTypeHtml = {}
        if (this.props.data.userType === 'company') {
            userTypeHtml.toggleClassName = "radioButtons pull-right clearfix";
            userTypeHtml.data = (
                <div>
                    <div className="formGroup firstChild companyPanNum panTanWrap">
                        <input type="text" className="form-control customFormInput govtIdNo" value={this.props.data.govtIdNo} onBlur={this.validate.bind(null, this.props.data.govtIdType) } onChange={this.handleChange} id="govtIdNo" />
                        <label htmlFor="pantan" className="control-label customLabels">Govt Id Number</label>
                        <div className="infoLabel">
                            <span className="infoIlabel">i</span>
                            <p className="toolTipWrap">
                                Only Passport or Adhar Card or PAN Card ID will be accepted.
                            </p>
                        </div>
                    </div>
                    <span className="errorMsg">{this.props.error.govtIdNo}</span>

                    <div className="formGroup firstChild marginTop20">
                        <input type="text" className="form-control customFormInput" onBlur={this.validate.bind(null, 'companyName') } value={this.props.data.companyName} onChange={this.handleChange} id="companyName" />
                        <label htmlFor="companyName" className="control-label customLabels">Company name</label>
                    </div>
                    <span className="errorMsg">{this.props.error.companyName}</span>



                    <div className="formGroup firstChild companyPanNum panTanWrap">
                        <input type="text" hidden className="form-control customFormInput" value="" onChange={this.handleChange} id="projectName" />
                    </div>


                </div>)
        }
        else {
            userTypeHtml.toggleClassName = "radioButtons pull-right clearfix toggleSlide";
            userTypeHtml.data =
                (
                    <div>
                        <div className="formGroup firstChild indiviualPanNum panTanWrap">
                            <input type="text" className="form-control customFormInput govtIdNo" value={this.props.data.govtIdNo} onBlur={this.validate.bind(null, this.props.data.govtIdType) } onChange={this.handleChange} id="govtIdNo"/>
                            <label htmlFor="govtIdNo" className="control-label customLabels">Govt Id Number</label>
                            <span className="errorMsg">{this.props.error.govtIdNo}</span>
                            <div className="infoLabel">
                                <span className="infoIlabel">i</span>
                                <p className="toolTipWrap">
                                    Only Passport or Adhar Card or PAN Card ID will be accepted.
                                </p>
                            </div>
                        </div>
                    </div>
                )
        }
        return userTypeHtml;
    },

    formatCountries() {
        let temp = this.props.countryCodes
        return _.each(temp, (obj) => {
            obj.value = obj.label;
            return obj
        })
    },

    formatStates() {
        let temp = this.props.stateCodes
        return _.each(temp, (obj) => {
            obj.value = obj.label;
            return obj
        })
    },

    getStates(event) {
        this.state.states = _.chain(this.state.countries)
            .where({ DESCRSHORT: event.currentTarget.dataset.id })
            .value() || _.where(this.state.countries, { DESCRSHORT: _.first(this.state.countries).DESCRSHORT });
        this.setState(this.state);
    },

    showCountryCodes() {
        return _.map(_.chain(this.state.countries).pluck("DESCRSHORT").uniq().value(), (data, key) => {
            return (<li key={key} data-id={data} onClick={this.updateCountryCode} >
                {data}
            </li>)
        });
    },

    showStates() {
        return _.map(this.state.states, (data, key) => {
            return (<li key={key} data-id={data.DESCR} onClick={this.updateStateCode}>
                {data.DESCR}
            </li>)
        });
    },

    hideModal() {
        this.setState({ isOpen: false });
    },

    openTermsandConditions() {
        this.setState({
            isOpen: true
        });
    },

    render() {
        var userTypeHtml = this.gethtmlBaseType();
        let countries = this.props.countryCodes
        let states = this.props.stateCodes
        let cities = this.props.cityCodes
        let documentCodes = this.props.data.userType === 'company' ? this.props.documentCodes.company : this.props.documentCodes.individual
        let countryList = _.map(_.chain(this.state.countries).pluck("DESCRSHORT").uniq().value(), (i) => {
            return {
                'label': i,
                'value': i
            };
        });
        let stateList = _.map(this.state.states, (i) => {
            return {
                'label': i.DESCR,
                'value': i.DESCR
            };
        });
        return (
            <form action="" name="signupForm" ref="signupForm" className="loginFormWrap signupFormWrap padleft80">
                <h4 className="loginhead">Create your new account</h4>
                <Loader loaded={this.props.loaded} top="50%" left="50%" options={LoaderOptions}>
                </Loader>

                <p className="clearfix">
                    <span className="pull-right mandatoryStar">*</span>
                    <span className="pull-right mandatoryField">
                        All fields are mandatory
                    </span>
                </p>
                <div className="formGroup firstChild">
                    <input type="email" className="form-control customFormInput" value={ this.props.data.emailId} onBlur={this.validate.bind(null, 'EMAIL') } onChange={this.handleChange} id="emailId" />
                    <label htmlFor="inputEmail" className="control-label customLabels">Email Id</label>
                    <span className="errorMsg">{this.props.error.emailId}</span>
                    <div className="infoLabel">
                        <span className="infoIlabel">i</span>
                        <p className="toolTipWrap">
                            This email ID will be used for all kind of communication
                        </p>
                    </div>
                </div>

                <div className="horzGroupWrap clearfix nameWrap">
                    <div className="formGroup firstChild pull-left">
                        <input type="text" className="form-control customFormInput" value={ this.props.data.firstName} onChange={this.handleChange}  id="firstName" onBlur={this.props.validateName.bind(null, 'firstName') }/>
                        <label htmlFor="firstName" className="control-label customLabels">First name</label>
                        <span className="errorMsg">{this.props.error.firstName}</span>
                    </div>
                    <div className="formGroup secondChild margin_less pull-left">
                        <input type="text" className="form-control customFormInput" value={ this.props.data.lastName} onChange={this.handleChange} id="lastName" onBlur={this.props.validateName.bind(null, 'lastName') }/>
                        <label htmlFor="lastName" className="control-label customLabels">Last name</label>
                        <span className="errorMsg">{this.props.error.lastName}</span>
                    </div>
                </div>
                <div className="horzGroupWrap clearfix passwordWrap">
                    <div className="formGroup firstChild pull-left">
                        <input type="password" className="form-control customFormInput" value={ this.props.data.password} onBlur={this.validateCorrectPassword} onChange={this.handleChange} id="password" />
                        <label htmlFor="password" className="control-label customLabels">Password</label>
                        <span className="errorMsg">{this.props.error.password}</span>
                    </div>
                    <div className="formGroup secondChild margin_less pull-left">
                        <input type="password" className="form-control customFormInput" value={ this.props.data.confirmPassword} onBlur={this.validatePassword} onChange={this.handleChange} id="confirmPassword" />
                        <label htmlFor="confirmPwd" className="control-label customLabels">Confirm password</label>
                        <span className="errorMsg">{this.props.error.confirmPassword}</span>
                        <div className="infoLabel">
                            <span className="infoIlabel">i</span>
                            <p className="toolTipWrap">
                                Minimum 8 characters: one lowercase, Uppercase, number &amp; special character ! @#$
                            </p>
                        </div>
                    </div>
                </div>
                <div className="formGroup radioChoose clearfix">
                    <p className="pull-left">Sign Up as: </p>
                    <div className={userTypeHtml.toggleClassName}>
                        <label className="customRadioLabel" htmlFor="company" onClick={this.handleSwitch.bind(null, 'company') }>
                            <input type="radio" name="userType" value="company" id="company" checked={ this.props.data.userType === "company"} />
                            Individual
                        </label>
                        <label className="customRadioLabel" htmlFor="indiviual" onClick={this.handleSwitch.bind(null, 'indiviual') }>
                            <input type="radio" name="userType" value="individual" id="indiviual" checked={ this.props.data.userType === "indiviual"} />
                            Company
                        </label>
                        <span className="toggleIcon"></span>
                    </div>
                </div>
                <div className="marginTop20">
                    <div className="customSelectGroup govtIdTypeForm">
                        <label className="signUpLabel">Govt ID Type</label>
                        <Select autofocus={false} clearable={false} ref="govtIdType" autofocus options={documentCodes}
                            simpleValue  name="country" value={this.props.data.govtIdType}
                            searchable={false} onChange={this.updateGovIdType} placeholder=""/>
                    </div>
                    <span className="errorMsg">{this.props.error.govtIdType}</span>
                    {userTypeHtml.data}

                    <div className="formGroup firstChild addressFieldWrap">
                        <textarea className="form-control customFormInput" value={ this.props.data.address} onChange={this.handleChange} id="address"/>
                        <label htmlFor="address" className="control-label customLabels">
                            {this.props.data.userType === 'company' ? 'Company Address' : 'Personal Address'}
                        </label>
                        <span className="errorMsg">{this.props.error.address}</span>
                    </div>

                    <div className="selectBoxWrap clearfix">

                        <div className="customSelectGroup reactSelectRow margin_less pull-left">
                            <label htmlFor="countryCode" className="signUpLabel">Country Code</label>
                            <Select autofocus={false} clearable={false} ref="countryCode"  options={countryList}
                                simpleValue  name="selected-country" value={this.props.data.country}
                                searchable={true} onChange={this.updateCountryCode}
                                placeholder="Select a Country"/>
                        </div>
                        <div className="customSelectGroup reactSelectRow  margin_less  pull-left">
                            <label htmlFor="stateList" className="signUpLabel">State</label>
                            <Select autofocus={false} clearable={false} ref="stateList" options={stateList}
                                simpleValue  name="selected-state" value={this.props.data.stateCode}
                                searchable={true} onChange={this.updateStateCode}
                                placeholder="Select a State"/>
                        </div>

                    </div>
                    <div className="selectBoxWrap horzGroupWrap clearfix">
                        <div className="formGroup customSelectGroup pull-left">
                            <input type="text" className="form-control customFormInput" value={ this.props.data.city} onChange={this.handleChange} id="city" />
                            <label htmlFor="city" className="control-label customLabels">City</label>
                            <span className="errorMsg">{this.props.error.city}</span>
                        </div>
                        <div className="formGroup secondChild margin_less pull-left">
                            <input type="text" maxLength="6" className="form-control customFormInput" value={ this.props.data.pincode} onChange={this.handleChange} id="pincode" />
                            <label htmlFor="pincode" className="control-label customLabels">Pincode</label>
                            <span className="errorMsg">{this.props.error.pincode}</span>
                        </div>
                    </div>
                    <div className="horzGroupWrap clearfix contactWrap">
                        <div className="formGroup firstChild pull-left">
                            <input maxLength="10" minLength="10" type="text" className="form-control customFormInput" value={ this.props.data.mobile} onBlur={this.verifyNumber.bind(null, 'MOBILE') } onChange={this.handleChange} id="mobile" />
                            <label htmlFor="mobile" className="control-label customLabels">Mobile</label>
                            <span className="errorMsg">{this.props.error.mobile}</span>
                        </div>

                        <div className="formGroup secondChild margin_less pull-left">
                            <input maxLength="13" minLength="9" type="text" className="form-control customFormInput" value={ this.props.data.landline} onBlur={this.verifyNumber.bind(null, 'landline') } onChange={this.handleChange} id="landline" />
                            <label htmlFor="landline" className="control-label customLabels">Landline</label>
                            <span className="errorMsg">{this.props.error.landline}</span>
                        </div>
                    </div>
                    <div className="horzGroupWrap faxTimeWrap clearfix">
                        <div className="draggableWrap marginBottom17 clearfix">
                            <ul className="clearfix">
                                {this.showAttachments() }
                            </ul>
                            <Dropzone className="pull-left dropArea" onDrop={this.addAttachment}>
                                <i className="NMIcon-attachFile pull-left right5"></i>
                                <p className="DragPara">Drag &amp; Drop KYC Document here, or <span>browse attachment</span></p>
                            </Dropzone>
                        </div>
                        <span className="errorMsg">{this.props.error.attachedFile}</span>
                    </div>
                </div>
                <div className="formGroup policiesWrap marginTop20 clearfix">
                    <span className="checkBoxCell pull-left">
                        <label className="cssCheckBox">
                            <input type="checkbox" checked="checked" readOnly="true"/>
                            <span className="NMIcon-checkbox"></span>
                        </label>
                    </span>
                    <p className="pull-left">By selecting this, you agree to our <Link className='regenerateLink' onClick={this.openTermsandConditions} title="Terms">Terms</Link> and that you have<br/>read our Data Policy.</p>
                </div>
                <span onClick={this.verifyUserData} className="btn btn-default customSubmitBtn">Proceed</span>
                <div className="subLinkWrap clearfix">
                    <span className="formSubLink pull-left" onClick={this.props.resetAll}>Reset All</span>
                    <p className="signUpWrap pull-right hide">
                        Already have an account?
                        <Link to="/MyNetmagic">Login</Link>
                    </p>
                </div>
                <Modal className="termsConditions customModalWrapper provisionModal" isOpen={this.state.isOpen} onRequestHide={this.hideModal}>
                    <ModalHeader>
                        <ModalClose onClick={this.hideModal} />
                        <ModalTitle>Terms and Conditions</ModalTitle>
                    </ModalHeader>
                    <ModalBody>
                        <MsaDoc/>
                    </ModalBody>
                    <ModalFooter>
                        <span className="darkPinkBtn pull-left"
                            onClick={this.hideModal}>Close</span>
                    </ModalFooter>
                </Modal>
            </form>
        )
    }
});

export default SignupForm;
