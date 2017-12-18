import Session from '../../../mixins/sessionUtils';
import UI from '../../../mixins/ui';
import http from '../../../mixins/restutils';
import Authorization from '../../../services/authorization';
import User from '../../../services/user';
import CONSTANTS from '../../../constants/app-constant';
import { Link } from 'react-router';
import PostRequestLoader from '../../commons/Loader';

var UpdatePassword = createReactClass({

  getInitialState: function() {
    return { showLoader: false};
  },

  componentDidMount() {
    Authorization.clearToken();
  },

  mixins: [ UI, Session, Authorization, http, User],
  contextTypes: {
    router: React.PropTypes.object
  },

  handleSubmit(event) {
    event.preventDefault();
    var credentials = {
      userEmail: this.getElement.call(this, 'email').value,
      userPassword: this.getElement.call(this, 'password').value
    };
    this.setState({ showLoader: true });
    User.login(credentials)
      .then(this.onSuccess)
      .catch(this.onError);
  },

  onSuccess(result) {
    this.set("user", _.omit(result, 'response', 'text'));
    var customerBeans = this.get('user') && this.get('user').customerSplashPageDataBeans;
    this.set('customerBeans', _.chain(customerBeans).where({ isMainCustomer: CONSTANTS.NO}).value());
    this.setToken(result && result.response);
    this.context.router.push('/splash-screen');
  },

  onError(error) {
    const invalid = CONSTANTS.UI_MESSAGES.invalidData;
    this.setState({ showLoader: false });
    this.getElement.call(this, 'errorMessage').textContent =
      CONSTANTS.UI_MESSAGES.invalidCredentials;
  },

  render() {
    return (
      <form onSubmit={this.handleSubmit} className="loginFormWrap">
        <PostRequestLoader show={this.state.showLoader} />
        <div className="formGroup firstChild">
          <input type="password" ref="password" className="form-control customFormInput" id="password" required="true" />
          <label htmlFor="password" className="control-label customLabels">Password</label>
        </div>
        <div className="formGroup secondChild">
          <input type="password" ref="password" className="form-control customFormInput" id="confirmPassword" required="true" />
          <label htmlFor="confirmPassword" className="control-label customLabels">Confirm Password</label>
        </div>
        <p className="error redText" ref="errorMessage" id="errorMessage"></p>
        <input type="submit" ref="submit" className="btn btn-default customSubmitBtn" name="Login" value="Login" />
        <div className="subLinkWrap clearfix">
          <p className="signUpWrap pull-right">
            New to myNetmagic,
            <Link to="/sign-up"> Sign Up</Link>
          </p>
        </div>
      </form>
    )
  }
});

export default UpdatePassword;
