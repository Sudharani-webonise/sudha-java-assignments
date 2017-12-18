import CONSTANTS from '../../constants/app-constant';

var RolesCheckbox = createReactClass({
    componentWillReceiveProps(nextProps) {
        var {checked, data} = nextProps;
        var wrapper = $(this.refs.checkboxWrapper);
        wrapper.find('input[type="checkbox"]').prop('checked', checked);
        wrapper.find('span')[checked ? 'removeClass' : 'addClass']('lightColorLabel');
    },

    toggleCheckbox(event) {
        var checkboxSpan = $(event.currentTarget).closest('label').find('span');
        var methodName = event.currentTarget.checked ? 'removeClass' : 'addClass';
        checkboxSpan && checkboxSpan[methodName]('lightColorLabel');
        this.props.updateAction(event.currentTarget.checked, this.props.data);
    },

    render() {
        var checked = this.props.checked;
        var data = this.props.data;
        const y = CONSTANTS.YES;
        return (<div className="checkboxWrap" ref="checkboxWrapper">
            {
                checked ?
                    (<label className="cssCheckBox">
                        {this.props.editable ?
                            (<input type="checkbox" defaultChecked="checked" onChange={this.toggleCheckbox}/>) :
                            (<input type="checkbox" checked="checked" readOnly="true" onClick={this.props.notifyForCheckBox}/>)
                        }
                        <span className="NMIcon-checkbox"></span>
                    </label>) :
                    (<label className="cssCheckBox">
                        {this.props.editable ?
                            (<input type="checkbox" onChange={this.toggleCheckbox}/>) :
                            (<input type="checkbox" readOnly="true" onClick={this.props.notifyForCheckBox}/>)
                        }
                        <span className="NMIcon-checkbox lightColorLabel"></span>
                    </label>)
            }
        </div>)
    }
});

export default RolesCheckbox;
