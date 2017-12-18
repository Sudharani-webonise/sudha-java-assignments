import CONSTANTS from '../../../constants/app-constant';
import { Link } from 'react-router';

var TicketServerInfo = createReactClass({

  severityData (code, field) {
    var severity = CONSTANTS.TICKET_SEVERITIES[code];
    return severity && severity[field]  || CONSTANTS.NOT_AVAILABLE;
  },

  getCountBySeverity (tickets) {
    return _.map(tickets, (row, key) => {
      var severities = this.severityData(row.severityType, 'severities');
      return (<Link to={`/tickets?severity=${severities && JSON.stringify(severities)}`} key={key}>
        <div className={row.severityType === 'S1' ? "pull-left serverDataWidget severityHigh" : "pull-left serverDataWidget"} key={key}>
          <label className="widgetLabel">{this.severityData(row.severityType, 'label')}</label>
          <span className="widgetSpan">{row.ticketCount}</span>
        </div>
      </Link>)
    })
  },

  render() {
    var data = this.props.data;
    return (
        (data !== null ? (<div className="widgetServerInfo clearfix">
          {this.getCountBySeverity(data.ticketsCountBySeverity)}
        </div>) : null)
    )
  }
});

export default TicketServerInfo;
