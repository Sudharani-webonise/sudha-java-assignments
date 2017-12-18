var TicketDataCount = createReactClass({
  render() {
    return (
        (this.props.data !== null ? (<div className="widgetDataCount dataCountBrdrBtm clearfix">
          <p className="pull-left">{this.props.data.openTicketsCount}</p>
        </div>) : null)
    )
  }
});

export default TicketDataCount;
