import { Link } from 'react-router';
import SSOService from '../../../services/sso';
import CONSTANTS from '../../../constants/app-constant';
import Loader from 'react-loader';
import LoaderOptions from '../../../mixins/loader';
import Session from '../../../mixins/sessionUtils';

var SsoCmpScreen = createReactClass({

  getDefaultProps () {
    return {
      loginHandler: CONSTANTS.API_URLS.CMPSSO.loginHandler,
      redirectToCmp: CONSTANTS.API_URLS.CMPSSO.redirectToCmp,
      password: CONSTANTS.CMP_USER.password,
      email_id: '',
      destination: CONSTANTS.CMP_USER.destination
    }
  },

  getInitialState () {
    return {
      loaded: false
    }
  },

  componentDidMount() {
    this.loadForms();
  },

  componentDidUpdate() {
    this.loadForms();
  },

  loadForms() {
    $(this.refs.ssoCmpForm).submit();
    this.state.loaded = true;
    setTimeout(function(){
      $('#ssoCmpRedirectForm').submit();
        location.hash = "#/dashboard";
    }, 1000);
  },

  render(){
    let emailId = Session.get('user').loginUserEmail;
    return(
    <div>
      <Loader loaded={this.state.loaded} options={LoaderOptions}></Loader>
       <iframe name='iframe' id='iframe' className='ssoIframeContainer hide'></iframe>
          <form method='POST' action={ this.props.loginHandler } ref='ssoCmpForm' target='iframe'>
            <input type='hidden'  name='username' ref='username' value={ emailId } />
            <input type='hidden'  name='password' ref='password' value={ this.props.password } />
            <input type='hidden'  name='destination' ref='destination' value={ this.props.destination } />
          </form>

        <form  method='GET' target='_blank' id='ssoCmpRedirectForm' action={ this.props.redirectToCmp }
          name='ssoCmpRedirectForm'></form>
    </div>
  )
  }
});

export default SsoCmpScreen;
