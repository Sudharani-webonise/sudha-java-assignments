import CONSTANTS from '../../constants/app-constant';

var CheckBox = createReactClass({
    contextTypes: {
        router: React.PropTypes.object
    },

    getInitialState() {
        return {reload: true};
    },

    componentWillReceiveProps(nextProps) {
        var {checked, data} = nextProps;
        const y = CONSTANTS.YES;
        var isChecked = (data && (data.isActive === y) ||
            checked && (checked.isActive === y));
        var wrapper = $(this.refs.checkboxWrapper);
        wrapper.find('input[type="checkbox"]').prop('checked', isChecked);
        wrapper.find('span')[isChecked ? 'removeClass' : 'addClass']('lightColorLabel');
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
                data && data.isActive === y ||
                    (checked && checked.isActive === y) ?
                    (<label className="cssCheckBox">
                        {this.props.editable ?
                            (<input type="checkbox" defaultChecked="checked" onChange={this.toggleCheckbox}/>) :
                            (<input type="checkbox" checked="checked" readOnly="true"/>)
                        }
                        <span className="NMIcon-checkbox"></span>
                    </label>) :
                    (<label className="cssCheckBox">
                        {this.props.editable ?
                            (<input type="checkbox" onChange={this.toggleCheckbox}/>) :
                            (<input type="checkbox" />)
                        }
                        <span className="NMIcon-checkbox lightColorLabel"></span>
                    </label>)
            }
        </div>)
    }
});

export default CheckBox;
