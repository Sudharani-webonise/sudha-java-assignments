import Session from '../../../mixins/sessionUtils';
import UI from '../../../mixins/ui';
import http from '../../../mixins/restutils';
import Authorization from '../../../services/authorization';
import User from '../../../services/user';
import CONSTANTS from '../../../constants/app-constant';
import { Link } from 'react-router';
import PostRequestLoader from '../../commons/Loader';

var RegeneratePassword = createReactClass({
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
                    this.state.successMessage = CONSTANTS.UI_MESSAGES.emailSent
                    UI.notifySuccess( CONSTANTS.UI_MESSAGES.emailSent, 300000);
                } else {
                    if(result.errCode && result.errCode === "5006") {
                        this.state.successMessage = result.errMsg;
                    }
                    else {
                        this.state.successMessage = CONSTANTS.UI_MESSAGES.oops;
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
    },

    regeneratePassword() {
        var re = CONSTANTS.REGEX.EMAIL;
        re.test(this.getElement.call(this, 'email').value) ?
            this.callgeneratePassword() : this.state.regenerateError = CONSTANTS.UI_MESSAGES.wrongEmail;
        this.setState(this.state);
    },

    render: function () {
        return (

            <div className="loginColumn regenerateFormWrap">
                <h1 className="regenerateHeading">Regenerate Password?</h1>
                <form onSubmit={this.handleSubmit} className="loginFormWrap ">
                    <p>Enter your email address and we will send a link to regenerate your password.</p>
                    <PostRequestLoader show={this.state.showLoader} />
                    <div className="formGroup firstChild">
                        <input type="text" ref="email" className="form-control customFormInput" id="inputEmail" required="true"/>
                        <label htmlFor="inputEmail" className="control-label customLabels">Enter your email address</label>
                        <span className="errorMsg">{this.state.regenerateError}</span>
                    </div>
                    <div className="subLinkWrap signUpWrap">
                        <input type="button" ref="btn1" onClick={this.regeneratePassword}
                            className="btn btn-default customSubmitBtn" name="RegeneratePassword" value="Submit" />
                        <Link to='/MyNetmagic' className="pull-left marginTop8  loginLink">Back To Login</Link>
                        <div className="clear"></div>
                        <p>{this.state.successMessage}</p>
                    </div>
                </form>
            </div>
        )
    }
});

export default RegeneratePassword;
