import { Link } from 'react-router';

var WidgetHead = createReactClass({
  render() {
    var link = null;
    if(this.props.url !== undefined && this.props.url.length !== 0 ) {
      link = <Link to={this.props.url} addClass="reOpenBtn pull-right darkPinkBtn rippleBtn">{this.props.linkName}</Link>
    }

    return (<div className="widgetHead clearfix">
      <h3 className="pull-left">{this.props.title}</h3>
      {link}
    </div>);
  }
});

export default WidgetHead;
