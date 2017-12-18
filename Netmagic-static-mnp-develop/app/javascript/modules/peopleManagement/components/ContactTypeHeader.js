import CONSTANTS from '../../../constants/app-constant';

var ContactTypeHeader = createReactClass({
    getSeverityTypes() {
        return _.range(1, this.props.maxSeverityTypes + 1).map((index, key) => {
            return (
                <div className="severityGrid pull-left" key={key}>
                    <div className={index === CONSTANTS.ONE ? "redText settingHead" : "settingHead"} key={key}>
                        Severity {index}
                    </div>
                    <div className="checkboxWrap">
                        Email
                    </div>
                    <div className="checkboxWrap">
                        SMS
                    </div>
                </div>
            )
        })
    },

    render() {
        return (
            <div className="clearfix">
                <div className="contactTypeGrid pull-left">
                    <div className="settingHead">Contact Type</div>
                </div>
                <div className="clearfix">
                    {this.getSeverityTypes() }
                </div>
            </div>
        )
    }
});

export default ContactTypeHeader;
