var WidgetBody = createReactClass({
  render() {
    return (<div>
      <span className={this.props.iconClass}></span>
      <p>{ this.props.infoText}</p>
    </div>)
  }
});

export default WidgetBody;
