import Session from '../../../mixins/sessionUtils';
import UI from '../../../mixins/ui';
import http from '../../../mixins/restutils';
import Authorization from '../../../services/authorization';
import User from '../../../services/user';
import CONSTANTS from '../../../constants/app-constant';
import { Link } from 'react-router';
import PostRequestLoader from '../../commons/Loader';

var ResetPassword = createReactClass({
    getInitialState: function () {
        return {
            showLoader: false,
            error: '',
            showBackToLogin: false,
            passwordError: '',
            link:'',
        };
    },

    componentDidMount() {
        Authorization.clearToken();
    },

    mixins: [UI, Session, Authorization, http, User],

    contextTypes: {
        router: React.PropTypes.object
    },

    validatePassword() {
        var newPassword = this.getElement.call(this, 'newPassword').value;
        var re = CONSTANTS.REGEX.PASSWORD;
        if (newPassword === "") {
            this.state.passwordError = ""
        }
        else if (!(re.test(newPassword))) {
            this.state.passwordError = CONSTANTS.UI_MESSAGES.resetPassword.passwordError
        }
        else {
            this.state.passwordError = ""
        }
        this.setState(this.state);
    },

    handleSubmit(event) {
        event.preventDefault();
        var newPassword = this.getElement.call(this, 'newPassword').value;
        var confirmPassword = this.getElement.call(this, 'confirmPassword').value
        var re = CONSTANTS.REGEX.PASSWORD;
        if (newPassword === confirmPassword && re.test(newPassword)) {
            Session.removeAll();
            this.state.error = ""
            this.state.showLoader = true;
            this.setState(this.state);
            var params = {};
             var url;
            if(this.getUrlVars()["token"]) {
                params = {
                    newPassword: newPassword,
                    confirmPassword: confirmPassword,
                    token: this.getUrlVars()["token"],
                    userEmail: this.getUrlVars()["emailId"]
                };
                url = CONSTANTS.API_URLS.resetPassword;
            } else {
                params = {
                    newPassword: newPassword,
                    confirmPassword: confirmPassword,
                    userEmail: atob(this.getUrlVars()["emailId"])
                };
                url = CONSTANTS.API_URLS.signup.tempUser;
            }
            User.resetPassword(url, params)
                .then((result) => {
                    var status = result.status
                    this.state.error = ''
                    this.state.link = ''
                    if (status.toUpperCase() === 'SUCCESS') {
                        this.state.showBackToLogin = true;
                        UI.notifySuccess( CONSTANTS.UI_MESSAGES.resetPassword.successMessage, 300000);
                        this.context.router.push('/MyNetmagic');
                    }
                    else if (status.toUpperCase() === 'FAIL') {
                            if(!result.message.toUpperCase().includes('TOKEN')) {
                                this.state.error = "Reason : "+result.message;
                                this.state.link = (
                                <Link to='regenerate-password' className="formSubLink regenerateLink blueLink">Kindly click here to regenerate your password</Link>
                                )
                            }
                            else {
                                this.state.link = (
                                <Link to='regenerate-password' className="formSubLink regenerateLink blueLink">An error occured , kindly click here to regenerate your password</Link>
                                )
                            }
                    }
                    this.state.showLoader = false;
                    this.setState(this.state);
                })
                .catch((error) => {
                    var errorResponse = JSON.parse(error.responseText);
                    UI.notifyError(error && error.responseText && errorResponse.message);
                    this.state.showLoader = false;
                    this.setState(this.state);
                });
        }
        else {
            this.state.error = "Entered passwords do not match"
            this.setState(this.state);
        }
    },

    getUrlVars() {
        var vars = {};
        var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function (m, key, value) {
            vars[key] = value;
        });
        return vars;
    },

    onSuccess(result) {
        this.context.router.push('/MyNetmagic');
    },

    onError(error) {
        UI.notifyError( CONSTANTS.UI_MESSAGES.resetPassword.error);
    },

    render() {
        return (<div className="resetPassword loginColumn regenerateFormWrap">
            <h1 className="regenerateHeading">Reset Password?</h1>

            <form  className="loginFormWrap loginFormBox">
                <p>According to our policies your password will expire in every 30 Days.</p>
                <PostRequestLoader show={this.state.showLoader} />

                <div className="formGroup firstChild">
                    <input type="password" ref="newPassword" onBlur={this.validatePassword} className="form-control customFormInput" id="newPassword" required="true" />
                    <label htmlFor="newPassword" className="control-label customLabels">New Password</label>
                </div>

                <div className="formGroup secondChild">
                    <input type="password" ref="confirmPassword" className="form-control customFormInput" id="confirmPassword" required="true" />
                    <label htmlFor="confirmPassword" className="control-label customLabels">Confirm Password</label>
                </div>
                <span className='portalMsg'>{this.state.passwordError}</span>
                <span className='portalMsg'>{this.state.error}</span>
                { this.state.link }
                <p className="error redText" ref="errorMessage" id="errorMessage"></p>
                {
                    this.state.showBackToLogin ?
                        (<Link to="/MyNetmagic">Back to Login</Link>)
                        :
                        (<input type="button" onClick={this.handleSubmit} ref="submit" className="btn btn-default customSubmitBtn" name="Login" value="Change Password" />)
                }
                <br/>
                {
                    this.state.showBackToLogin ?
                        (<span>Your password has been reset, please re-login to continue</span>)
                        :
                        (null)
                }

            </form>
        </div>
        )
    }
});

export default ResetPassword;