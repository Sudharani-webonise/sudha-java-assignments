import { Link } from 'react-router';
import User from '../../../services/user';

var UnauthorizedPage = createReactClass({
  render() {
    return (
    <div>
        <section className="breadCrumbSection dashboardBreadCrumb">
          <div className="container">
            <div className="row">
              <div className="col-xs-10 col-sm-10 col-md-10">
                <ol className="breadcrumb breadCrumbCustom clearfix">
                  <li><Link to="/dashboard">Back to Dashboard</Link></li>
                  <li className="ticketListBread active">Unauthorized</li>
                </ol>
              </div>
              <div className="col-xs-2 col-sm-2 col-md-2"></div>
            </div>
          </div>
        </section>
        <div className="container dashBoardContainer">
          <h2>403: Unauthorized Page</h2>
          <p>You are Unauthorized to view this page.</p>
          <Link to={User.getSplashScreenURL()} className="dashboardLink">Go back to Home</Link>
        </div>
      </div>
    );
  }
});

export default UnauthorizedPage;
