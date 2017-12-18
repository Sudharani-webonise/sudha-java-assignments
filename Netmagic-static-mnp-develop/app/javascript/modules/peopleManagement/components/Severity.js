import CONSTANTS from '../../../constants/app-constant';
import RolesCheckbox from '../../commons/RolesCheckbox';

var Severity = createReactClass({
    render() {
        var {data, editable, checkboxData} = this.props;
        const sendNotification = CONSTANTS.YES;
        return (
            <div className={editable ? 'severityGrid pull-left editable' : 'severityGrid pull-left'}>
                <RolesCheckbox checked={data.sendEmailNotification === sendNotification} editable={editable}
                    updateAction={this.props.updateNotification} data={_.extend({}, checkboxData, { type: 'sendEmailNotification' }) }/>
                <RolesCheckbox checked={data.sendSmsNotification === sendNotification} editable={editable}
                    updateAction={this.props.updateNotification} data={_.extend({}, checkboxData, { type: 'sendSmsNotification' }) }/>
            </div>
        )
    }
});

export default Severity;
