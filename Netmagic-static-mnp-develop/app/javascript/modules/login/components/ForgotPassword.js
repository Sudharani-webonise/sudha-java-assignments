import { Link } from 'react-router';
import http from '../../../mixins/restutils';
import Session from '../../../mixins/sessionUtils';
import CONSTANTS from '../../../constants/app-constant';

var ForgotPassword = createReactClass({
    contextTypes: {
      router: React.PropTypes.object
    },

    getInitialState: function() {
      return {
          showLoader: false,
          email: '',
          error: ''
      };
    },

    handleEmailChange(e){
        this.setState({
            email: e.target.value
        });
    },

    handleSubmit(event){
        event.preventDefault();
        var userEmailObj= {
            email : this.state.email
        }
        var url = CONSTANTS.API_URLS.updatePassword;
        http.performPost(url, userEmailObj)
        .then((result) => {
            if(result.status === 'Success'){
                this.context.router.push('/update-password');
            }
            else {
                this.state.error = CONSTANTS.UI_MESSAGES.wrongEmail;
            }
        })
        .catch(this.onError);
    },

    onError: function (error) {
      const invalid = CONSTANTS.UI_MESSAGES.invalidData;
      this.setState({ showLoader: false });
      this.getElement.call(this, 'errorMessage').textContent =
        CONSTANTS.UI_MESSAGES.invalidCredentials;
    },

  render() {
    return (
      <div className="container paddingZero">
        <div className="row">
          <div className="col-sm-12 col-md-12 loginColumn">
            <div className="loginFlowWrapper">
              <h1>Forgot Passwords?</h1>
              <div className="loginFormContainer">
                <form name="forgotPwd" onSubmit={this.handleSubmit} className="loginFormWrap">
                  <p className="infoLabels">Enter your email address, and we will send <br/>OTP to reset your password.</p>
                  <div className="formGroup firstChild">
                    <input type="email" ref="email" className="form-control customFormInput"
                    onChange={this.handleEmailChange} id="inputEmail"/>
                    <label htmlFor="inputEmail" className="control-label customLabels">Enter your email address</label>
                    <span className="validInvalidLabel hide">valid</span>
                  </div>
                  <input type="submit" ref="submit" className="btn btn-default customSubmitBtn"
                   name="submit" value="Submit" />
                  <div className="subLinkWrap clearfix">
                    <Link to="otp" className="formSubLink pull-left">Back to login</Link>
                    <p className="signUpWrap pull-right">
                      <a href="javascript: void(0);">Clear</a>
                    </p>
                  </div>
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
    )
  }
});

export default ForgotPassword;
