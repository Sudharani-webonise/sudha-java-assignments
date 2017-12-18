import Loader from 'react-loader';
import { Link } from 'react-router';
import Session from '../../../mixins/sessionUtils';
import User from '../../../services/user';

var UnauthorizedPage = createReactClass({
    getInitialState() {
      return { showInfo: false}
    },

    userEmail() {
      var user = Session.get('user');
      return user && user.loginUserEmail;
    },

    toggleDetails() {
      this.setState({ showInfo: !this.state.showInfo });
    },

    render() {
      return(
        <div className="unauthorizedContainer">
          <i className="DBicon-alert"></i>
          <h3> You need permission to access this page</h3>
          {this.state.showInfo ?
            (<p>You are signed in as <span>{this.userEmail()}</span> but you don't' have permission to access this
            page. You can go back to dashboard or request access from the account manager</p>) :
            (null)
          }
          <Link to="/error/403" onClick={this.toggleDetails}>Learn more</Link>
          <Link className="back" to="/dashboard">Back to Dashboard</Link>
          <Link className="request" to={User.getSplashScreenURL()}>Back to Home</Link>
        </div>
      );
    }
});

export default UnauthorizedPage
