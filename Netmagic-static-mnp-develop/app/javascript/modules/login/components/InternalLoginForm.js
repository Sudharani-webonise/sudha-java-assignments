import Session from '../../../mixins/sessionUtils';
import UI from '../../../mixins/ui';
import http from '../../../mixins/restutils';
import Authorization from '../../../services/authorization';
import User from '../../../services/user';
import CONSTANTS from '../../../constants/app-constant';
import { Link } from 'react-router';
import PostRequestLoader from '../../commons/Loader';
import Utility from '../../../mixins/basicUtils';

var InternalLoginForm = createReactClass({
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

    handleSubmit (event=new Event('')) {
        this.getElement.call(this, 'errorMessage').textContent = "";
        this.state.successMessage = '';
        this.state.showLoader = true;
        event.preventDefault();
        var credentials = {
            userEmail: this.getElement.call(this, 'email').value,
            userPassword: this.getElement.call(this, 'password').value
        };
        this.setState(this.state);
        User.internalLogin(credentials)
            .then((result) => {
                Session.removeAll();
                result.loginUserName = result.userFirstName + ' ' + result.userLastName;
                this.set("user", _.omit(result, 'response', 'text'));
                var permissionBean = Utility.getVal(result, 'userRoleModulePermissionsBean.mynmUserModuleAccessBean');
                var permissions = _.chain(permissionBean)
                    .pluck('myNmUserFunctionAccessBeans')
                    .flatten()
                    .compact()
                    .value();
                Session.set('userPermissions', permissions);
                Session.set('modulePermissions', permissionBean);
                var mainCustomerList = this.get('user') && this.get('user').customerBasicHeaderBeanList;
                this.set('mainCustomerList', mainCustomerList);
                this.setToken(result && result.response);
                this.context.router.push('/splash-screen-internal');
            })
            .catch((error) => {
                if (error.toUpperCase() == "FORBIDDEN") {
                    this.getElement.call(this, 'errorMessage').textContent =
                        CONSTANTS.UI_MESSAGES.invalidCredentials;
                }
                this.setState({ showLoader: false });
            });
    },

    render() {
        return (
            <div>
                <form onSubmit={this.handleSubmit} className="loginFormWrap loginFormBox">
                    <PostRequestLoader show={this.state.showLoader} />
                    <div className="formGroup firstChild">
                        <input type="text" ref="email" className="form-control customFormInput" id="inputEmail" required="true"/>
                        <label htmlFor="inputEmail" className="control-label customLabels">Domain Name</label>
                        <span className="errorMsg">{this.state.regenerateError}</span>
                    </div>
                    <div className="formGroup secondChild">
                        <input type="password" ref="password" className="form-control customFormInput" id="inputPassword" required="true" />
                        <label htmlFor="inputPassword" className="control-label customLabels">Password</label>
                    </div>
                    <p className="error redText" ref="errorMessage" id="errorMessage"></p>
                    <p className="error redText">{this.state.successMessage}</p>
                    <input type="submit" ref="submit" className="btn btn-default customSubmitBtn" name="Login" value="Login" />
                </form>
            </div>
        )
    }
});

export default InternalLoginForm;
