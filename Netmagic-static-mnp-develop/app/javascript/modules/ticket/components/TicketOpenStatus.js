var TicketOpenStatus = createReactClass({
  render() {
    return (
      <div className="widgetOpenStatus clearfix">
        <div>
          {this.props.data ? (
            <div className="openSinceWrap pull-left clearfix">
              <label className="widgetLabel">Open since</label>
              {this.props.data.ticketsCountByTimeDelay.map((row, key) => {
                return (
                  <div className="tcktTxtDetails clearfix" key={key}>
                    <p className="pull-left">{row.timeDelayUnit}</p>
                    <span className="pull-right">{row.ticketCount}</span>
                  </div>
                )
              })}
            </div>
          ) : ''}
        </div>
        <div>
          {this.props.data ? (
            <div className="statusWrap pull-left clearfix">
              <label className="widgetLabel">Status</label>
              {this.props.data.ticketsCountByStatus.map((row,key) => {
                return (
                  <div className="tcktTxtDetails clearfix" key={key}>
                    <p className="pull-left">{row.ticketStatus === 'Resolved Pending Closure' ? 'Resolved' : row.ticketStatus}</p>
                    <span className="pull-right">{row.ticketCount}</span>
                  </div>
                )
              })}
            </div>
          ) : ''}
        </div>
      </div>
    )
  }
});

export default TicketOpenStatus;
