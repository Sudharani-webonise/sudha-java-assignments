import SignupForm from './SignupForm';
import { Link } from 'react-router';
import User from '../../../services/user';
import CONSTANTS from '../../../constants/app-constant';
import Loader from 'react-loader';
import LoaderOptions from '../../../mixins/loader';
import Session from '../../../mixins/sessionUtils';
import UI from '../../../mixins/ui';
import Authorization from '../../../services/authorization';

var SignUp = createReactClass({

    mixins: [UI, Session, User],

    contextTypes: {
        router: React.PropTypes.object
    },

    getInitialState() {
        return {
            loaded: true,
            kacid: '',
            kcntid: '',
            koid: '',
            acid: '',
            oid: '',
            countryCodes: [{ label: 'India', value: 'India', id: 'id' }, { label: 'USA', value: 'USA', id: 'USA' }, { label: 'Japan', value: 'Japan', id: 'JPN' }],
            stateCodes: [{ label: 'Maharashtra', value: 'Maharashtra', id: 'MAH' }, { label: 'Tamil Nadu', value: 'Tamil Nadu', id: 'TN' }, { label: 'Punjab', value: 'Punjab', id: 'PNG' }],
            cityCodes: [{ label: 'Mumbai', value: 'Mumbai', id: 'MAH' }, { label: 'Delhi', value: 'Delhi', id: 'DE' }],
            documentCodes: {
                company: [{ label: 'PAN', value: 'PAN', id: 'PAN' }, { label: 'DUNS', value: 'DUNS', id: 'DUNS' }, { label: 'TAN', value: 'TAN', id: 'TAN' }],
                individual: [{ label: 'PAN', value: 'PAN', id: 'PAN' }, { label: 'PASSPORT', value: 'PASSPORT', id: 'PASSPORT' }, { label: 'AADHAR', value: 'AADHAR', id: 'AADHAR' }],
            },
            formData: {
                userType: 'company',
                token: '',
                emailId: '',
                firstName: '',
                lastName: '',
                password: '',
                confirmPassword: '',
                signUpAs: '',
                companyName: '',
                companyRegisteredAs: '',
                //companyDunsNumber: '',
                govtIdType: 'PAN',
                govtIdNo: '',
                projectName: '',
                address: '',
                state: '',
                stateCode: '',
                city: '',
                countryCode: '',
                country: '',
                currency: 'INR',
                pincode: '',
                mobile: '',
                landline: '',
                fileUploadRequest: {
                    token: '',
                    accountid: '',
                    attachedFile: []    
                }
            },
            error: {
                emailId: '',
                firstName: '',
                lastName: '',
                password: '',
                confirmPassword: '',
                signUpAs: '',
                companyName: '',
                companyRegisteredAs: '',
                //companyDunsNumber: '',
                govtIdType: '',
                govtIdNo: '',
                projectName: '',
                address: '',
                state: '',
                city: '',
                country: '',
                // countryCode: '',
                currency: '',
                pincode: '',
                mobile: '',
                landline: '',
                attachedFile: ''
            },
            checkTermsAndConditions: false,
            attachments: []
        };
    },

    handleChange(event) {
        var name = event.target.id;
        var value = event.target.value;
        this.state.formData[name] = (['address', 'companyName'].indexOf(name) > -1) ? value : value && value.trim();
        this.setState(this.state);
    },

    handleSwitch(value) {
        this.state.formData.userType = value;
        this.setState(this.state)
    },

    updateCountryCode(data) {
        this.state.formData.country = data && data.DESCRSHORT;
        this.state.formData.countryCode = data && data.COUNTRY;
        this.setState(this.state);
    },

    updateStateCode(value) {
        this.state.formData.state = value.STATE;
        this.state.formData.stateCode = value.DESCR;
        this.setState(this.state)
    },

    updateGovIdType(value) {
        this.state.formData.govtIdType = value;
        this.setState(this.state)
    },

    updateCityCode(value) {
        var cityCode = _.findWhere(this.state.cityCodes,
            { label: value });
        var cityCode = cityCode && cityCode.id;
        this.state.formData.citylabel = value;
        this.state.formData.city = cityCode;
        this.setState(this.state)
    },

    checkCorrectPassword(status) {
        status ? this.state.error.password = CONSTANTS.UI_MESSAGES.signup.validatePassword : this.state.error.password = ''
        this.setState(this.state);
    },

    updatePasswordError(status) {
        status ? this.state.error.confirmPassword = CONSTANTS.UI_MESSAGES.signup.password : this.state.error.confirmPassword = ''
        this.setState(this.state);
    },

    verifyUserData() {
        this.state.formData.signUpAs = this.state.formData.userType
        var error = {}
        if (this.state.error.emailId || this.state.error.govtIdNo || this.state.error.password || this.state.error.mobile || this.state.error.online || this.state.error.companyName ||
            this.state.error.attachedFile) {
            return
        }
        var formData = _.omit(this.state.formData, 'userType', 'token', 'companyRegisteredAs', 'statelabel', 'citylabel', 'countrylabel', 'landline', 'projectName');
        if (this.state.formData.userType != 'company') {
            formData = _.omit(formData, 'companyName');
        }
        for (var key in formData) {
            if (formData.hasOwnProperty(key) && !formData[key]) {
                error[key] = CONSTANTS.UI_MESSAGES.signup.compulsary;
            }
        }
        if (_.isEmpty(error)) {
            this.signUpRequest()
        }
        else {
            this.state.error = error
            this.setState(this.state)
        }

    },

    readGetVariables() {
        let pid = this.getUrlVars()['pid'];
        let loc = this.getUrlVars()['loc'];
        let corporateData = Session.get('corporateData');
        let queryParams = {
            pid,
            loc
        };
        if (!_.isEmpty(corporateData)) {
            Session.set('corporateData', corporateData);
        } else {
            Session.set('corporateData', queryParams);
        }
    },

    getUrlVars() {
        var vars = {};
        var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function (m, key, value) {
            vars[key] = value;
        });
        return vars;
    },

    signUpRequest() {
         var fileCount = this.state.attachments.length;
         if (fileCount) {
            this.state.error.attachedFile = '';
            var base64 = CONSTANTS.REGEX.BASE_64;
            var count = 0;
            _.each(this.state.attachments, (file, index) => {
                var reader = new FileReader();
                reader.onload = () => {
                    this.state.formData.fileUploadRequest.attachedFile.push({
                        fileName: file.name,
                        fileSize: file.size,
                        fileExtension: file.name.split('.').pop(),
                        docType: 'addressproof',
                        fileInBytes: reader.result && reader.result.replace(base64, '$2')
                    });
                    count++;
                    if (count === fileCount) {
                        this.createUser();
                    }
                }
                reader.readAsDataURL(file);
            });
        } else {
            this.state.error.attachedFile = CONSTANTS.UI_MESSAGES.signup.kycFiles;
            UI.notifyError(CONSTANTS.UI_MESSAGES.signup.kycFiles);
        }
        this.setState(this.state);
    },

    createUser() {
        this.state.loaded = false;
        this.setState(this.state);
        var params = this.state.formData;
        params = _.omit(params, 'stateCode');
        params.emailId = params.emailId.toLowerCase();
        params.govtIdNo = params.govtIdNo.toUpperCase();
        if (params.userType != 'company') {
            params.companyName = params.firstName;
        }
        var requestParams = {
            signUpUserDetails: _.omit(params, 'fileUploadRequest'),
            fileUploadRequest: params.fileUploadRequest
        }
        User.postData(CONSTANTS.API_URLS.signup.createLead, requestParams)
            .then((result) => {
                if (result) {
                    Session.set('signUpInfo', params);
                    this.context.router.push('/otp');
                    this.state.loaded = true;
                } else {
                    this.state.loaded = true;
                }
                this.readGetVariables();
                this.setState(this.state);
            })
            .catch((error) => {
                UI.notifyError(error && error.statusText || error);
                this.state.loaded = true;
                this.setState(this.state);
            });
    },

    validate(id) {
        var params = {}
        var formData = this.state.formData;
        formData.govtIdNo = formData.govtIdNo.toUpperCase();
        var error = {};
        var re;
        switch (id) {
            case "EMAIL":
                re = CONSTANTS.REGEX.EMAIL;
                if (!re.test(formData.emailId)) {
                    error.emailId = CONSTANTS.UI_MESSAGES.signup.invalid
                }
                params = {
                    idType: id,
                    idDetails: formData.emailId
                }
                break;
            case "DUNS":
                re = CONSTANTS.REGEX.DUNS;
                if (!re.test(formData.govtIdNo)) {
                    error.govtIdNo = CONSTANTS.UI_MESSAGES.signup.duns
                }
                params = {
                    idType: id,
                    idDetails: formData.govtIdNo
                }
                break;
            case "PAN":
                re = CONSTANTS.REGEX.PAN;
                if (!re.test(formData.govtIdNo)) {
                    error.govtIdNo = CONSTANTS.UI_MESSAGES.signup.pan
                }
                params = {
                    idType: id,
                    idDetails: formData.govtIdNo.toUpperCase()
                }
                break;
            case "AADHAR":
                re = CONSTANTS.REGEX.ADHAR;
                if (!re.test(formData.govtIdNo)) {
                    error.govtIdNo = CONSTANTS.UI_MESSAGES.signup.adhar
                }
                params = {
                    idType: id,
                    idDetails: formData.govtIdNo.toUpperCase()
                }
                break;
            case "PASSPORT":
                re = CONSTANTS.REGEX.PASSPORT;
                if (!re.test(formData.govtIdNo)) {
                    error.govtIdNo = CONSTANTS.UI_MESSAGES.signup.passport
                }
                params = {
                    idType: id,
                    idDetails: formData.govtIdNo.toUpperCase()
                }
                break;
            case "companyName":
                if (!formData.companyName) {
                    error.companyName = CONSTANTS.UI_MESSAGES.signup.companyName
                }
                params = {
                    idType: id,
                    idDetails: formData.companyName
                }
                break;
            case "MOBILE":
                re = CONSTANTS.REGEX.PHONE;
                if (!re.test(formData.mobile)) {
                    error.mobile = CONSTANTS.UI_MESSAGES.signup.mobile
                }
                params = {
                    idType: id,
                    idDetails: formData.mobile
                }
                break;
            default:
                params = {};
        }

        if (_.isEmpty(error)) {
            this.performValidation(params, id)
        }
        else {
            this.state.error = error;
            this.setState(this.state);
        }
    },

    verifyNumber(id) {
        if (id === 'MOBILE') {
            CONSTANTS.REGEX.PHONE.test(this.state.formData.mobile) ? this.state.error.mobile = '' : this.state.error.mobile = 'Enter a valid mobile number';
            this.validate(id);
        }
        else {
            CONSTANTS.REGEX.LANDLINE.test(this.state.formData.landline) ? this.state.error.landline = '' : this.state.error.landline = 'Enter a valid landline number'
        }
        this.setState(this.state)
    },

    validateName(id) {
        CONSTANTS.REGEX.NAME.test(this.state.formData[id]) ? this.state.error[id] = '' : this.state.error[id] = CONSTANTS.UI_MESSAGES.signup.name;
        this.setState(this.state);
    },

    resetAll() {
        this.setState({
            formData: {
                userType: this.state.formData.userType,
                token: '',
                emailId: '',
                firstName: '',
                lastName: '',
                password: '',
                confirmPassword: '',
                signUpAs: '',
                companyName: '',
                companyRegisteredAs: '',
                govtIdType: this.state.formData.govtIdType,
                govtIdNo: '',
                projectName: '',
                address: '',
                state: '',
                city: '',
                countryCode: '',
                country: '',
                currency: 'INR',
                pincode: '',
                mobile: '',
                landline: '',
            }
        })
    },

    performValidation(params, id) {
        var error = this.state.error;
        if (params.idType == "EMAIL") {
            let url = `${CONSTANTS.API_URLS.signup.userEmailVerify}?emailId=${params.idDetails}`;
            User.emailVerify(url)
                .then((result) => {
                    if (result) {
                        error.emailId = CONSTANTS.UI_MESSAGES.signup.alreadyExists.email
                        this.state.error = error;
                    } else {
                        error.emailId = "";
                        this.state.error = error;
                    }
                    this.setState(error);
                })
                .catch((err) => {
                    UI.notifyError(error && error.statusText);
                })
        } else {
            User.postData(CONSTANTS.API_URLS.signup.verify, params)
                .then((result) => {
                    if (result === "Already exist!") {
                        switch (id) {
                            case "DUNS":
                                error.govtIdNo = CONSTANTS.UI_MESSAGES.signup.alreadyExists.duns
                                break;
                            case "PAN":
                                error.govtIdNo = CONSTANTS.UI_MESSAGES.signup.alreadyExists.pan
                                break;
                            case "AADHAR":
                                error.govtIdNo = CONSTANTS.UI_MESSAGES.signup.alreadyExists.adhar
                                break;
                            case "PASSPORT":
                                error.govtIdNo = CONSTANTS.UI_MESSAGES.signup.alreadyExists.passport
                                break;
                            case "TAN":
                                error.govtIdNo = CONSTANTS.UI_MESSAGES.signup.alreadyExists.tan
                                break;
                            case "companyName":
                                error.companyName =
                                    CONSTANTS.UI_MESSAGES.signup.alreadyExists.companyName
                                break;
                            case "MOBILE":
                                error.mobile =
                                    CONSTANTS.UI_MESSAGES.signup.alreadyExists.mobile
                                break;
                            default: break;
                        }
                    } else {
                        switch (id) {
                            case "DUNS":
                                error.govtIdNo = ""
                                break;
                            case "PAN":
                                error.govtIdNo = ""
                                break;
                            case "AADHAR":
                                error.govtIdNo = ""
                                break;
                            case "PASSPORT":
                                error.govtIdNo = ""
                                break;
                            case "TAN":
                                error.govtIdNo = ""
                                break;
                            case "companyName":
                                error.companyName = ""
                            case "MOBILE":
                                error.mobile = ""
                            default: break;
                        }
                    }
                    this.state.error = error;
                    this.setState(error)
                })
                .catch(function (error) {
                    UI.notifyError(error && error.statusText)
                    return false;
                });
        }
    },

    updateAttachments(attachments) {
        this.state.attachments = attachments;
        this.state.error.attachedFile = '';
        this.setState(this.state);
    },

    render() {
        return (
            <div className="paddingBottom170">
                <div className="container paddingZero">
                    <div className="row">
                        <div className="col-sm-12 col-md-12 loginColumn">
                            <div className="loginFlowWrapper">
                                <h1>Welcome to myNetmagic Beta</h1>
                                <div className="col-md-6 text-right padtop">
                                    <div className="col-xs-12 pad10">
                                        <Link to="/MyNetmagic" className="btn_login pull-right">Login for Existing User</Link>
                                    </div>
                                    <div className="col-xs-12 pad10">
                                        <Link className="dis_signup pull-right">Sign Up for New User</Link>
                                    </div>
                                </div>
                                <div className="col-md-6 borderLeft">
                                    <SignupForm
                                        data={this.state.formData}
                                        error={this.state.error}
                                        handleChange={this.handleChange}
                                        handleSwitch={this.handleSwitch}
                                        verifyUserData={this.verifyUserData}
                                        validate={this.validate}
                                        finalSubmit={this.finalSubmit}
                                        countryCodes={this.state.countryCodes}
                                        stateCodes={this.state.stateCodes}
                                        cityCodes={this.state.cityCodes}
                                        documentCodes={this.state.documentCodes}
                                        updateCountryCode={this.updateCountryCode}
                                        updateStateCode={this.updateStateCode}
                                        updateCityCode={this.updateCityCode}
                                        updateGovIdType={this.updateGovIdType}
                                        updatePasswordError={this.updatePasswordError}
                                        checkCorrectPassword={this.checkCorrectPassword}
                                        loaded={this.state.loaded}
                                        verifyNumber={this.verifyNumber}
                                        validateName={this.validateName}
                                        resetAll={this.resetAll}
                                        checkTermsAndConditions={this.state.checkTermsAndConditions}
                                        attachments={this.state.attachments}
                                        updateAttachments={this.updateAttachments}
                                        />
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
});

export default SignUp;
