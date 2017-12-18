import { Link } from 'react-router';
import CONSTANTS from '../../../constants/app-constant';
import Time from '../../../mixins/time';
import Session from '../../../mixins/sessionUtils';
import UI from '../../../mixins/ui';
import Utility from '../../../mixins/basicUtils';
import User from '../../../services/user';

var CustomerWidget = createReactClass({
    mixins: [Session, UI, Utility, User],
    contextTypes: {
        router: React.PropTypes.object
    },

    getInitialState() {
        return {
            collapse: false
        }
    },

    setActiveCustomer(data, customUrl, event) {
        if (data) {
            Session.set('activeCustomerId', data.customerId);
            var projects = data.projects;
            Session.set('associatedCustomer', data);
            var a = _.findWhere(data.projects, { projectId: Number(event.currentTarget.dataset.id) })
            Session.set('project', a);
            this.setUserPermissions(customUrl);
        }
    },

    setUserPermissions(customUrl) {
        var userRequestParams = User.getCustomerParams();
        var params = {
            mainCustomerId: userRequestParams.customerId,
            associateCustomerId: userRequestParams.assoCustomerId || userRequestParams.customerId,
            projectId: userRequestParams.projectId
        };

        User.getPermissions(params)
            .then((result) => this.context.router.push(customUrl || '/dashboard'))
            .catch((error) => this.notifyError(CONSTANTS.UI_MESSAGES.userPermissionError));
    },

    customerLogo(logoUrl) {
        return logoUrl ?
            (<img src={require(logoUrl) } />) :
            (<p>LOGO</p>);
    },

    getCountBySeverity(typeCount, type) {
        var severity = _.findWhere(typeCount, { severityType: type });
        return this.showNumber(severity.ticketCount);
    },

    toggle() {
        this.setState({ collapse: !this.state.collapse })
    },

    getAccordianBtnClass() {
        return this.classSet({
            'accordianBtn': true,
            'active': this.state.collapse
        });
    },

    getToggleClassSet() {
        return this.classSet({
            'listAccordianData': true,
            'show': this.state.collapse,
            'hide': !this.state.collapse
        })
    },

    showProjects(projects) {
        var projectsList = (<span>{CONSTANTS.UI_MESSAGES.projectUnavailable}</span>);
        var data = this.props.data;
        if (_.isArray(projects) && projects.length) {
            projectsList = _.map(projects, (project, i) => {
                return (
                    <li key={i}>
                        <div className="customerDetails">
                            <i className="nonSelectedStar hide"></i>
                            <a data-id={project.projectId}onClick={this.setActiveCustomer.bind(null, data, '/dashboard') }>
                                {project.projectName}
                            </a>
                        </div>
                        <ul className="ticketsWrap clearfix">
                            <li className="ticketNum projectListDetails highlightedInfo" data-id={project.projectId}
                                onClick={this.setActiveCustomer.bind(null, data, '/tickets') }>
                                <span>{this.showNumber(project.allTicketsCount) }</span>
                            </li>
                            <li className="severityOne projectListDetails highlightedInfo">
                                <span>{this.getCountBySeverity(project.countBySeverityBeans, 'S1') }</span>
                            </li>
                            <li className="severityTwo projectListDetails highlightedInfo" onClick={this.setActiveCustomer.bind(null, data, '/tickets?severity=["S2"]') }>
                                <span>{this.getCountBySeverity(project.countBySeverityBeans, 'S2') }</span>
                            </li>
                            <li className="alerts projectListDetails hide">
                                <span>{this.showNumber(project.customerAlertsCount) }</span>
                            </li>
                            <li className="assets projectListDetails" onClick={this.setActiveCustomer.bind(null, data, '/assets') }>
                                <span>{this.showNumber(project.customerAssetsCount) }</span>
                            </li>
                        </ul>
                    </li>
                )
            });
        }
        return projectsList;
    },

    render() {
        let data = this.props.data;
        let key = this.props.data;
        const NA = CONSTANTS.NOT_AVAILABLE;
        return (
            <li className={data.isMainCustomer === CONSTANTS.YES ? "customerDataList mainCustomerList" : "customerDataList"} data-customer-id={data.customerId}>
                <div className="accordianHeader">
                    <div className="customerDetails">
                        <i className={data.isMainCustomer === CONSTANTS.YES ? "selectedStar hide" : "nonSelectedStar hide"}></i>
                        <a onClick={this.setActiveCustomer.bind(null, data, '/dashboard') }>
                            {data.customerName || NA}
                        </a>
                    </div>
                    <ul className="ticketsWrap clearfix">
                        <li className="ticketNum highlightedInfo"  onClick={this.setActiveCustomer.bind(null, data, '/tickets') }>
                            <span>{this.showNumber(data.allTicketsCount) }</span>
                        </li>
                        <li className="severityOne highlightedInfo" onClick={this.setActiveCustomer.bind(null, data, '/tickets?severity=["S1"]') }>
                            <span>{this.getCountBySeverity(data.countBySeverityBeans, 'S1') }</span>
                        </li>
                        <li className="severityTwo highlightedInfo" onClick={this.setActiveCustomer.bind(null, data, '/tickets?severity=["S2"]') }>
                            <span>{this.getCountBySeverity(data.countBySeverityBeans, 'S2') }</span>
                        </li>
                        <li className="alerts hide">
                            <span>{this.showNumber(data.customerAlertsCount) }</span>
                        </li>
                        <li className="assets" onClick={this.setActiveCustomer.bind(null, data, '/assets') }>
                            <span>{this.showNumber(data.customerAssetsCount) }</span>
                        </li>
                        <li className="outstanding hide" onClick={this.setActiveCustomer.bind(null, data, '/outstanding') }>
                            <span><i className="rupeeIcon">&#8377; </i> 6.31 <span className="amountUnit">Lacs</span></span>
                        </li>
                    </ul>
                    <span onClick={this.toggle} className={this.getAccordianBtnClass() }>
                        <span></span>
                    </span>

                    <div className="clear"></div>
                </div>
                <div className={this.getToggleClassSet() }>
                    <ul>
                        { this.showProjects(data.projects) }
                    </ul>
                </div>
            </li>
        );
    }
});

export default CustomerWidget;
