var PermissionCheckbox = createReactClass({
  render () {
    let allowed = this.props.allowed;
    return (
      <div className="severityGrid pull-left">
        <div className="checkboxWrap">
          <label className="cssCheckBox">
            <input type="checkbox" checked={allowed ? "checked" : ""} readOnly="readOnly"/>
            <span className={allowed ? "NMIcon-checkbox" : "NMIcon-checkbox lightColorLabel"}></span>
          </label>
        </div>
      </div>
    )
  }
});

export default PermissionCheckbox;
