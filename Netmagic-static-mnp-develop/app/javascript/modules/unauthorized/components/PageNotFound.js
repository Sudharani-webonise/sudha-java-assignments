import Loader from 'react-loader';
import { Link } from 'react-router';
import User from '../../../services/user';

var PageNotFound = createReactClass({
    render() {
      return(
        <div className="unauthorizedContainer">
          <i className="DBicon-alert"></i>
          <h3>404: Page Not Found</h3>
          <p>The page you are looking for does not exist</p>
          <Link to="javascript:void(0)">Learn more</Link>
          <Link className="back" to="/dashboard">Back to Dashboard</Link>
          <Link className="request" to={User.getSplashScreenURL()}>Back to Home</Link>
        </div>
      );
    }
});

export default PageNotFound;
