import Session from '../../../mixins/sessionUtils';
import UI from '../../../mixins/ui';
import http from '../../../mixins/restutils';
import Authorization from '../../../services/authorization';
import User from '../../../services/user';
import CONSTANTS from '../../../constants/app-constant';
import { Link } from 'react-router';
import PostRequestLoader from '../../commons/Loader';

var LoginForm = createReactClass({
    getInitialState: function () {
        return {
            showLoader: false,
            regenerateError: '',
        };
    },

    componentDidMount() {
        Authorization.clearToken();
    },

    mixins: [UI, Session, Authorization, http, User],

    contextTypes: {
        router: React.PropTypes.object
    },

    readGetVariables() {
        let pid = this.getUrlVars()['pid'];
        let loc = this.getUrlVars()['loc'];
        let queryParams = {
            pid,
            loc
        };
        Session.set('corporateData', queryParams);
    },

    getUrlVars() {
        var vars = {};
        var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function (m, key, value) {
            vars[key] = value;
        });
        return vars;
    },

    goToDashBoard(mainCustomer) {
        Session.set('activeCustomerId', User.getMainCustomer('customerId'));
        Session.set('associatedCustomer', mainCustomer);
        Session.set('project', {});
        this.context.router.push('/dashboard')
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

    handleSubmit: function (event) {
        this.getElement.call(this, 'errorMessage').textContent = "";
        this.state.successMessage = '';
        this.state.showLoader = true;
        event.preventDefault();
        var credentials = {
            userEmail: this.getElement.call(this, 'email').value,
            userPassword: this.getElement.call(this, 'password').value
        };
        this.setState(this.state)
        User.login(credentials)
            .then((result) => {
                Session.removeAll();
                this.setToken(result && result.response);
                if(result.duelAuth) {
                    Session.set('userLoginDetails', _.omit(result, 'response', 'text'));
                    Session.set('userCreds', credentials);
                    this.context.router.push('/otp');
                } else {
                    this.readGetVariables();
                    this.set("user", _.omit(result, 'response', 'text'));
                    var customerBeans = this.get('user') && this.get('user').customerSplashPageDataBeans;
                    var mainCustomer = _.findWhere(customerBeans, { isMainCustomer: CONSTANTS.YES });
                    var customerBeanData = customerBeans;
                    customerBeans = _.chain(customerBeans).where({ isMainCustomer: CONSTANTS.NO }).value();
                    this.set('customerBeans', customerBeans);
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
                }
            })
            .catch((error) => {
                if (error && error.toUpperCase() === "LOCKED") {
                    this.callgeneratePassword()
                }
                else if (error && error.toUpperCase() == "FORBIDDEN") {
                    this.getElement.call(this, 'errorMessage').textContent =
                        CONSTANTS.UI_MESSAGES.invalidCredentials;
                }
                else {
                    if (error && error.errCode === '2015') {
                        this.context.router.push('/reset-password');
                    }
                    this.getElement.call(this, 'errorMessage').textContent =
                        CONSTANTS.UI_MESSAGES.timeoutError
                }
                this.setState({ showLoader: false });
            });
    },

    callgeneratePassword() {
        this.state.showLoader = true;
        this.setState(this.state);
        var params = {
            userEmail: this.getElement.call(this, 'email').value,
            otpThrough: 'Email',
            requestFor: 'reset',
        }
        var url = CONSTANTS.API_URLS.regenPassword
        User.resetPassword(url, params)
            .then((result) => {
                if (result.userEmail) {
                    this.state.successMessage = CONSTANTS.UI_MESSAGES.forceResetPassword
                }
                this.state.showLoader = false;
                this.setState(this.state);
            })
            .catch((error) => {
                this.state.successMessage = CONSTANTS.UI_MESSAGES.oops
                this.state.showLoader = false;
                this.setState(this.state);
            });
    },

    gotoSignUp() {
        this.readGetVariables();
        this.context.router.push('/sign-up');
    },

    render: function () {
        return (
            <div>
                <form onSubmit={this.handleSubmit} className="loginFormWrap loginFormBox padleft80">
                    <h4 className="loginhead padbtm10">Login in to your account</h4>
                    <PostRequestLoader show={this.state.showLoader} />
                    <div className="formGroup firstChild">
                        <input type="text" ref="email" className="form-control customFormInput" id="inputEmail" required="true" />
                        <label htmlFor="inputEmail" className="control-label customLabels">Email Address</label>
                        <span className="errorMsg">{this.state.regenerateError}</span>
                    </div>
                    <div className="formGroup secondChild">
                        <input type="password" ref="password" className="form-control customFormInput" id="inputPassword" required="true" />
                        <label htmlFor="inputPassword" className="control-label customLabels">Password</label>
                    </div>
                    <p className="error redText" ref="errorMessage" id="errorMessage"></p>
                    <p className="error redText">{this.state.successMessage}</p>
                    <input type="submit" ref="submit" className="btn btn-default customSubmitBtn" name="Login" value="Login" />
                    <div className="subLinkWrap clearfix text-center">
                        <div className="clearfix validate">
                            <Link to='regenerate-password' className="formSubLink regenerateLink">Regenerate Password?</Link>
                            <p className="signUpWrap hide">
                                | <span className="balckText">New to myNetmagic?</span>
                                <Link to="/sign-up"> Sign Up</Link>
                            </p>
                        </div>
                    </div>
                </form>
            </div>
        )
    }
});

export default LoginForm;