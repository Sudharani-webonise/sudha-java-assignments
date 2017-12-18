import CONSTANTS from '../../../constants/app-constant';
import Session from '../../../mixins/sessionUtils';

var PermissionHeader = createReactClass({
  getInitialState() {
    return { activeModule: _.first(Session.get('modules')) };
  },

  showPermissionLabels() {
    let permissions = this.props.data;
    return _.isArray(permissions) && _.map(permissions, (item, key) => {
      return (<div className="severityGrid pull-left" key={key}>
          <div className="settingHead" data-permission-id={item.functionId}>{item.functionName}</div>
        </div>)
    })
  },

  render () {
    return (
      <div className="clearfix">
        <div className="contactTypeGrid pull-left">
          <div className="settingHead">Role Type</div>
        </div>
        <div className="clearfix">
          {this.showPermissionLabels()}
        </div>
      </div>
    )
  }
});

export default PermissionHeader;
