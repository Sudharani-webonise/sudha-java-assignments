import { Link } from 'react-router';
import User from '../../../services/user';
import CONSTANTS from '../../../constants/app-constant';
import Loader from 'react-loader';
import LoaderOptions from '../../../mixins/loader';
import Session from '../../../mixins/sessionUtils';
import UI from '../../../mixins/ui';
import Authorization from '../../../services/authorization';

var Otp = createReactClass({

    contextTypes: {
        router: React.PropTypes.object
    },

    getInitialState() {
        return {
            otp: '',
            kacid: '',
            kcntid: '',
            koid: '',
            acid: '',
            oid: '',
            loaded: true
        }
    },

    componentDidMount() {
        UI.notifySuccess(CONSTANTS.UI_MESSAGES.otpSentSuccess);
    },

    setOtp(event) {
        this.setState({ otp: event.target.value });
    },

    signupOtpVerify(userInfo) {
        User.verifyingOTP(CONSTANTS.API_URLS.signup.otpVerify, { emailId: userInfo.emailId, mobile: userInfo.mobile, otp: this.state.otp})
            .then(result => {
                this.state.loaded = true;
                this.state.kacid = result.kacid;
                this.state.kcntid = result.kcntid;
                this.state.koid = result.koid;
                this.state.acid = result.acid;
                this.state.oid = result.oid;
                this.setState(this.state);
                User.login({ userEmail: userInfo.emailId, userPassword: userInfo.password})
                    .then((result) => {
                        Session.set("user", _.omit(result, 'response', 'text'));
                        var customerBeans = Session.get('user') && Session.get('user').customerSplashPageDataBeans;
                        var mainCustomer = _.findWhere(customerBeans, { isMainCustomer: CONSTANTS.YES });
                        var customerBeanData = customerBeans;
                        customerBeans = _.chain(customerBeans).where({ isMainCustomer: CONSTANTS.NO }).value();
                        Session.set('customerBeans', customerBeans);
                        Authorization.setToken(result && result.response);
                        this.context.router.push('/product-list');
                    })
                    .catch((error) => {
                        UI.notifyError(error && error.statusText);
                    });
            })
            .catch(error => {
                var errorResponse = JSON.parse(error.responseText);
                UI.notifyError(error && error.responseText && errorResponse.message);
                this.state.loaded = true;
                this.setState(this.state);
            })
    },

    fetchUserDetails() {
        let userEmail = User.getUserInfo('loginUserEmail');
        let url = `${CONSTANTS.API_URLS.signup.newUserDetails}?userEmail=${userEmail}`;
        User.fetchNewUserDetails(url)
            .then((res) => {
                Session.set('userDetails', res);
                this.context.router.push('/product-list');
            })
            .catch((err) => {
                UI.notifyError('Error in fetching User details');
            });

    },

    goToDashBoard(mainCustomer) {
        Session.set('activeCustomerId', User.getMainCustomer('customerId'));
        Session.set('associatedCustomer', mainCustomer);
        Session.set('project', {});
        this.context.router.push('/dashboard')
    },

    loginOtpVerify(userInfo) {
        User.verifyingOTP(CONSTANTS.API_URLS.loginOtpVerify, { emailId: userInfo.loginUserEmail, mobile: userInfo.loginUserMobile, otp: this.state.otp})
            .then(result => {
                Session.removeAll();
                Session.set("user", _.omit(result, 'response', 'text'));
                var customerBeans = Session.get('user') && Session.get('user').customerSplashPageDataBeans;
                var mainCustomer = _.findWhere(customerBeans, { isMainCustomer: CONSTANTS.YES });
                var customerBeanData = customerBeans;
                customerBeans = _.chain(customerBeans).where({ isMainCustomer: CONSTANTS.NO }).value();
                Session.set('customerBeans', customerBeans);
                if (!(_.isArray(customerBeans) && customerBeans.length)) {
                        Session.set('mainCustomer', mainCustomer);
                        var params = {
                            mainCustomerId: User.getMainCustomer('customerId'),
                            associateCustomerId: User.getMainCustomer('customerId'),
                            projectId: User.getProject('projectId') || ''
                        };
                        if (User.getUserInfo('existingUser')) {
                            User.getPermissions(params)
                                .then((res) => {
                                    var safeCheck = Session.get('userPermissions');
                                    safeCheck ? this.goToDashBoard(mainCustomer) : (null)
                                })
                                .catch((error) => this.notifyError(CONSTANTS.UI_MESSAGES.userPermissionError));
                        } else {
                            this.fetchUserDetails();
                        }
                    }
                    else {
                        this.context.router.push('/splash-screen');
                    }
            })
            .catch(error => {
                var errorResponse = JSON.parse(error.responseText);
                UI.notifyError(error && error.responseText && errorResponse.message);
                this.state.loaded = true;
                this.setState(this.state);
            })
    },

    verifyOTP() {
        var userInfo = Session.get('signUpInfo') || Session.get('userLoginDetails');
        this.state.loaded = false;
        this.setState(this.state);
        if(userInfo.duelAuth) {
            this.loginOtpVerify(userInfo);
        } else {
            this.signupOtpVerify(userInfo);            
        }
    },

    regenerateOTP() {
        var userInfo = Session.get('signUpInfo') || Session.get('userLoginDetails');
        var url = CONSTANTS.API_URLS.signup.otpResend;
        if(userInfo.duelAuth) {
            url = CONSTANTS.API_URLS.loginOtpResend;
        }
        User.regenerateOTP(url, { emailId: userInfo.emailId || userInfo.loginUserEmail })
            .then(res => {
                UI.notifySuccess(CONSTANTS.UI_MESSAGES.otpSentSuccess);
            })
            .catch(err => {
                UI.notifyError(error && error.statusText)
            })
    },

    render() {
        return (
            <div className="col-sm-12 col-md-12 loginColumn">
                <Loader loaded={this.state.loaded} top="50%" left="50%" options={LoaderOptions}>
                </Loader>
                <div className="loginFlowWrapper">
                    <h1>Enter OTP</h1>
                    <div className="loginFormContainer">
                        <form name="OTPPwd" action="" className="loginFormWrap">
                            <p className="infoLabels" id="otpLabel">{CONSTANTS.UI_MESSAGES.otpInfo}</p>
                            <div className="formGroup firstChild">
                                <input type="email" className="form-control customFormInput" id="inputEmail" onChange={this.setOtp}/>
                                <label for="inputEmail" className="control-label customLabels">Enter OTP</label>
                            </div>
                            {
                                this.state.otp.trim() ?
                                 <Link className="btn btn-default customSubmitBtn" onClick={this.verifyOTP}>Submit</Link> :
                                  <Link className="btn btn-default customSubmitBtn">Submit</Link>
                            }
                            <div className="subLinkWrap clearfix">
                                <a className="formSubLink pull-left" onClick={this.regenerateOTP}>Regenerate OTP</a>
                                <p className="signUpWrap pull-right hide">
                                    <Link to="forgotPassword">Cancel</Link>
                                </p>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        )
    }
});

export default Otp;
