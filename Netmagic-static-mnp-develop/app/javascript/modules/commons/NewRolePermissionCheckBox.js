var NewRolePermissionCheckBox = createReactClass({

  componentWillReceiveProps(nextProps) {
    var {checked} = nextProps;
    var wrapper = $(this.refs.checkboxWrapper);
    wrapper.find('input[type="checkbox"]').prop('checked', checked);
    wrapper.find('span')[checked ? 'removeClass' : 'addClass']('lightColorLabel');
  },

  toggleCheckBox(event) {
    var checkboxSpan = $(event.currentTarget).closest('label').find('span');
    var methodName = event.currentTarget.checked ? 'removeClass' : 'addClass';
    checkboxSpan && checkboxSpan[methodName]('lightColorLabel');
    this.props.updatePermission(event.currentTarget.checked, this.props.data)
  },

  render () {
    let allowed = this.props.allowed;
    let editable = this.props.editable;
    let data = this.props.data;
    return (
      <div className="severityGrid pull-left">
        <div className="checkboxWrap" ref="checkboxWrapper">
          {
            data.isSelected ? (
              <label className="cssCheckBox">
                {
                  editable ?
                  <input type="checkbox" defaultChecked="checked" onChange={this.toggleCheckBox}/> :
                  <input type="checkbox" checked="checked" readOnly="true"/>
                }
                <span className="NMIcon-checkbox"></span>
              </label>
            ) :
            (
              <label className="cssCheckBox">
                {
                  editable ?
                  <input type="checkbox" onChange={this.toggleCheckBox}/> :
                  <input type="checkbox"/>
                }
                <span className="NMIcon-checkbox lightColorLabel"></span>
              </label>
            )
          }
        </div>
      </div>
    )
  }
});

export default NewRolePermissionCheckBox;
