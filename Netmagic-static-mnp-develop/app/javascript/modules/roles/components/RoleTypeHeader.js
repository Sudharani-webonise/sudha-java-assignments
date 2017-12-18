import CONSTANTS from '../../../constants/app-constant';

var RoleTypeHeader = createReactClass({
  getPermissions () {
    return _.map(this.props.permissions, (data, key) => {
      return (<div key={key} className="severityGrid pull-left">
        <div className="settingHead" data-permission-name={data.functionCode}>
          {data.functionName}
        </div>
      </div>)
    });
  },

  render () {
    return (
      <div className="clearfix">
        <div className="contactTypeGrid pull-left">
          <div className="settingHead">Role Type</div>
        </div>
        <div className="clearfix">
          {this.getPermissions()}
        </div>
      </div>
    )
  }
});

export default RoleTypeHeader;
