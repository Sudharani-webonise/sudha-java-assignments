import { Link } from 'react-router';
import CONSTANTS from '../../../constants/app-constant';

var ServiceWidgetData = createReactClass({
  render () {
    return (
      this.props.data ? (<div>
        <div className="widgetDataCount clearfix">
          <Link to='/service-ledger'>
            <p className="pull-left">
              {this.props.data.totalService || CONSTANTS.ZERO}
            </p>
          </Link>
          <label className="widgetLabel"></label>
        </div>
        <div className="widgetDataCount outstanding clearfix">
          <div className="pull-left creditData">
            <Link to={`/service-ledger?status=Not_delivered`}>
              <label className="widgetLabel">To be Delivered</label>
              <span className="widgetSpan">
                {this.props.data.servicesToBeDelivered || CONSTANTS.ZERO}
              </span>
            </Link>
          </div>
          <div className="pull-left creditData">
            {/*TODO: Need to make the data dynamic once proper data comes*/}
            <Link to={`/service-ledger`}>
              <label className="widgetLabel">To be Renewed</label>
              <span className="widgetSpan">
                {this.props.data.servicesToBeRenewed || CONSTANTS.ZERO}
              </span>
            </Link>
          </div>
        </div>
        <div className="outstanding clearfix">
          <div className="tcktTxtDetails clearfix">
            <Link to={`/service-ledger?status=Active`}>
              <p className="pull-left">Active</p>
              <span className="pull-right">{this.props.data.servicesActive || CONSTANTS.ZERO}</span>
            </Link>
          </div>
          <div className="tcktTxtDetails clearfix">
            <Link to={`/service-ledger?status=Under_cancellation`}>
              <p className="pull-left">Under Cancellation</p>
              <span className="pull-right">{this.props.data.servicesUnderCancellation || CONSTANTS.ZERO}</span>
            </Link>
          </div>
          <div className="tcktTxtDetails clearfix">
            <Link to={`/service-ledger?status=Under_deactivation`}>
              <p className="pull-left">Under Deactivation</p>
              <span className="pull-right">{this.props.data.servicesUnderDeactivation || CONSTANTS.ZERO}</span>
            </Link>
          </div>
        </div>
      </div>
    ) : null
    )
  }
});

export default ServiceWidgetData;
