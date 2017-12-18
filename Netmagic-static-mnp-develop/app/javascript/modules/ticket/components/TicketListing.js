import CONSTANTS from '../../../constants/app-constant';
import Ticket from '../../../services/ticket';
import Time from '../../../mixins/time';
import Session from '../../../mixins/sessionUtils';
import Utility from '../../../mixins/basicUtils';
import UI from '../../../mixins/ui';
import User from '../../../services/user';
import Loader from 'react-loader';
import LoaderOptions from '../../../mixins/loader';
import { Link } from 'react-router';
import { Modal, ModalHeader, ModalTitle, ModalClose, ModalBody, ModalFooter} from 'react-modal-bootstrap';

var TicketListing = createReactClass({

    mixins: [Session, Utility, UI, User],
    contextTypes: {
        router: React.PropTypes.object
    },

    getInitialState() {
        var severities = this.getVal(this.props, 'location.query.severity');
        return {
            loaded: false,
            pageNumber: 1,
            tickets: [],
            severeValue: '',
            filters: {
                ticketSeverity: severities ? JSON.parse(severities) : [],
                ticketType: [],
                ticketStatus: []
            },
            configData: {
                severityTypes: [],
                ticketTypes: [],
                stateTypes: []
            },
            error: false
        };
    },

    componentWillMount() {
        this.redirectIfUnauthorized.call(this,
            CONSTANTS.PERMISSION_CODES.tickets.listing);
    },

    componentDidMount() {
        this.getTickets();
        $(document).on('scroll', this.showMoreVisible);
        Ticket.getConfigData()
            .then((result) => {
                this.setState({
                    configData: {
                        severityTypes: result.severityTypes || [],
                        ticketTypes: result.ticketTypes || [],
                        stateTypes: this.getFilteredStateTypes(result.stateTypes) || []
                    }
                });
            })
            .catch((error) => UI.notifyError( error));
        Session.set('splashScreen', true);
    },

    componentWillUnmount() {
        $(document).off('scroll');
    },

    showMoreVisible() {
        if (document.body.scrollHeight === document.body.scrollTop + window.innerHeight) {
            this.state.pageNumber++;
            this.getTickets(null, true);
        }
    },

    getTickets(options, appendList) {
        const uiMessages = CONSTANTS.UI_MESSAGES;
        var associatedCustomer = Session.get('associatedCustomer');
        var customerId = this.get('activeCustomerId') || associatedCustomer.cvCrmId;
        var mainCustomerId = _.isObject(this.getCustomerParams()) && this.getCustomerParams().customerId;
        var project = Session.get('project');
        var requestBody = _.extend({
            mainCustomerId: mainCustomerId,
            associateCustomerId: customerId,
            projectId: User.getProject('projectId') || '',
            pageNumber: options && options.pageNumber || this.state.pageNumber,
            perPageRecords: 20,
            sortByFieldName: 'ticketLastUpdatedDate'
        }, options && options.filters || this.state.filters);
        this.setState({ loaded: false });
        Ticket.getList(requestBody)
            .then((result) => {
                var tickets = appendList ? _.union(this.state.tickets, result) : result;
                this.setState({ tickets: tickets, loaded: true, error: false });
            })
            .catch((error) => {
                if (error === uiMessages.badRequest && this.state.pageNumber !== 1) {
                    $(document).off('scroll');
                } else if (error === uiMessages.unauthorized) {
                    // Do nothing
                } else {
                    UI.notifyError( uiMessages.dataNotFound);
                    this.setState({ loaded: true, error: true });
                }
            });
    },

    applyFilter(target) {
        var data = target.dataset;
        if (data.id === "Assigned/WIP") {
            this.state.filters[data.paramName] = CONSTANTS.TICKET_STATUS_TYPES;
        } else {
            this.state.filters[data.paramName] = [data.id];
        }
        this.state.pageNumber = 1;
        this.setState(this.state);
        $(document).on('scroll', this.showMoreVisible);
    },

    clearFilters() {
        var newFilters = {
            ticketSeverity: [],
            ticketType: [],
            ticketStatus: []
        };
        this.setState({ filters: newFilters, error: false, pageNumber: 1 });
        window.scrollTo(0, 0);
        $(document).on('scroll', this.showMoreVisible);
        this.getTickets({ filters: newFilters, pageNumber: 1 });
    },

    raiseTicketClassSet() {
        return this.classSet({
            'darkPinkBtn': true,
            'pull-right': true,
            'alignBtn': true,
            'hide': !this.canAccess(CONSTANTS.PERMISSION_CODES.tickets.raise)
        });
    },

    getFilteredStateTypes(stateTypes) {
        return _.chain(stateTypes)
            .without("Assigned", "Work in Progress", "Waiting on Vendor", "Monitoring")
            .union(["Assigned/WIP"])
            .value()
    },

    render() {
        let tickets = this.state.tickets;
        return (
            <section className="ticketListSection">
                <section className="breadCrumbSection">
                    <div className="container">
                        <div className="row">
                            <div className="col-xs-10 col-sm-10 col-md-10 ticketHeader">
                                <ol className="breadcrumb breadCrumbCustom clearfix">
                                    <li><Link to={User.getSplashScreenURL()}>Back to Home</Link></li>
                                    <li><Link to="dashboard">Back to Dashboard</Link></li>
                                    <li className="ticketListBread active">Ticket Listing</li>
                                </ol>
                                <h2 className="pageHeading">Tickets</h2>
                            </div>
                            <div className="col-xs-2 col-sm-2 col-md-2 ticketHeaderBtn">
                                <Link to="tickets/raise-ticket" activeClassName="" className={this.raiseTicketClassSet() }>raise ticket</Link>
                            </div>
                        </div>
                    </div>
                </section>
                <article className="container paddingZero">
                    <ul className="tickeListTable paddingTop125">
                        <TicketListViewHead super={this} data={this.state.configData} clearFilters={this.clearFilters}/>
                        <div className="ticketListItems">
                            <Loader loaded={this.state.loaded} top="50%" left="50%" options={LoaderOptions}>
                                {
                                    _.isArray(tickets) && tickets.length ? this.state.tickets.map((data, key) => {
                                        return (<TicketListView data={data} key={key} index={key} getTickets={this.getTickets}/>)
                                    }) : (<li className="rowCelltabelDataCell noDataBlock">
                                        {CONSTANTS.UI_MESSAGES.dataNotFound}
                                    </li>)
                                }
                            </Loader>
                            {
                                this.state.error ?
                                    (<li className="rowCelltabelDataCell text-center">
                                        {CONSTANTS.UI_MESSAGES.dataNotFound}
                                    </li>) : null
                            }
                        </div>
                    </ul>
                </article>
            </section>
        )
    }
});

var TicketListView = createReactClass({
    getInitialState() {
        return {
            isOpen: false,
            enable: false,
            ticketStatus: null,
            comment: null
        }
    },

    contextTypes: {
        router: React.PropTypes.object
    },

    ticketUpdate(status) {
        var user = Session.get('user');
        var params = {
            ticketNumber: this.props.data.ticketNumber,
            updateComment: this.state.comment,
            updatedByUser: user && user.loginUserName,
            changeStatus: status
        };
        Ticket.updateTicket(this.props.data.ticketNumber, params)
            .then((res) => {
                UI.notifySuccess(this.props.data.ticketNumber + ' ' + CONSTANTS.UI_MESSAGES.ticketStatus);
                this.props.getTickets();
                this.hideModal();
            })
            .catch((err) => {
                UI.notifyError( err)
                this.hideModal();
            });
    },

    showActionButtons(status) {
        var actionButton;
        if (!User.canAccess(CONSTANTS.PERMISSION_CODES.tickets.details)) {
            actionButton = null;
        } else if (status !== CONSTANTS.TICKET_STATUS.PENDING) {
            actionButton = (User.canAccess(CONSTANTS.PERMISSION_CODES.tickets.edit) ? <a onClick={this.redirectToDetails}
                className="provideInputBtn pull-left">provide input</a> : null);
        } else {
            actionButton = (<div className="actionBtnGrp clearfix">
                {
                    User.canAccess(CONSTANTS.PERMISSION_CODES.tickets.reopen) ?
                    (<div>
                        <a className="reOpenBtn pull-left" onClick={this.openModal.bind(null, 'reopen') }>reopen</a>
                        <a className="reOpenBtn closeBtn pull-left" onClick={this.openModal.bind(null, 'closed') }>close</a>
                    </div>) : null
                }
            </div>);
        }
        return actionButton;
    },

    redirectToDetails() {
        Session.set('ticketId', this.props.data.ticketNumber);
        this.context.router.push('/tickets/details');
    },

    ticketDetailLink() {
        var ticketNumber = this.props.data.ticketNumber;
        return User.canAccess(CONSTANTS.PERMISSION_CODES.tickets.details) ?
            (<a className="dataCell ticketNoCell" onClick={this.redirectToDetails}>{ticketNumber}</a>) :
            (<span className="dataCell ticketNoCell">{ticketNumber}</span>);
    },

    openModal(status, event) {
        event.stopPropagation();
        this.setState({
            isOpen: true,
            ticketStatus: status
        });
    },

    hideModal(event) {
        var state = { isOpen: false, enable: false, comment: null };
        setTimeout(() => this.setState(state), 250);
    },

    handleChange(event) {
        var comment = this.refs.closeComment.value;
        this.setState({ enable: comment && comment.trim() ? true : false, comment: event.currentTarget.value });
    },

    getBtnClassSet() {
        return UI.classSet({
            'darkPinkBtn': true,
            'pull-left': true,
            'disableLinkBtn': !this.state.enable
        });
    },

    render() {
        var ticketAction;
        var ticketSummary = this.props.data.ticketSummary.split("<br/>").join(" ");
        return (
            <li className="rowCell tabelDataCell ticketDataList">
                {this.ticketDetailLink() }
                <span className={this.props.data.ticketSeverity === 'S1' ? "dataCell serverListCell redText" : "dataCell serverListCell" }>
                    {this.props.data.ticketSeverity}
                </span>
                <span className="dataCell openSinceCell">
                    {Time.duration(this.props.data.ticketOpenSince) }
                </span>
                <span className="dataCell nonAuthCell summaryCell">
                    {ticketSummary}
                </span>
                <span className="dataCell typeCell">
                    {this.props.data.caseType}
                </span>
                <span className="dataCell statusCell">
                    {this.props.data.ticketStatus}
                </span>
                <span className="dataCell lastUpdatedCell">
                    {Time.format(this.props.data.ticketLastUpdatedDate, CONSTANTS.DATE_FORMAT) }
                </span>
                <span className="dataCell actionCell">
                    {this.showActionButtons(this.props.data.ticketStatus) }
                </span>
                <Modal className="customModalWrapper" isOpen={this.state.isOpen} onRequestHide={this.hideModal}>
                    <ModalHeader>
                        <ModalClose onClick={this.hideModal}/>
                        <ModalTitle>{this.props.data.ticketNumber}</ModalTitle>
                    </ModalHeader>
                    <ModalBody>
                        <div className="formElementWrap">
                            <textarea className="mandatory" onChange={this.handleChange} id="closeComment"
                                ref="closeComment" required="true" maxLength="250" placeholder="Please add comments"></textarea>
                        </div>
                    </ModalBody>
                    <ModalFooter>
                        <input type="submit" onClick= {this.ticketUpdate.bind(null, this.state.ticketStatus) }
                            className={this.getBtnClassSet() } value={this.state.ticketStatus === "closed" ? "Close" : "Reopen"}/>
                        <input type="submit" onClick={this.hideModal} className={this.getBtnClassSet() }
                            value="Cancel" />
                    </ModalFooter>
                </Modal>
            </li>
        )
    }
});

var TicketListViewHead = createReactClass({
    getInitialState() {
        return {
            pageNumber: 1,
            severityTypes: [],
            ticketTypes: [],
            stateTypes: []
        };
    },

    contextTypes: {
        router: React.PropTypes.object
    },

    componentWillReceiveProps(nextProps) {
        if (nextProps && nextProps.data) {
            this.setState(_.extend(this.state, nextProps.data));
        }
    },

    getFilteredTickets(event) {
        const uiMessages = CONSTANTS.UI_MESSAGES;
        var parentContext = this.props.super;
        parentContext.applyFilter(event.currentTarget);
        var customerId = Session.get('activeCustomerId');
        var mainCustomerId = _.isObject(User.getCustomerParams()) && User.getCustomerParams().customerId;
        var project = Session.get('project');
        var requestBody = _.extend({
            mainCustomerId: mainCustomerId,
            associateCustomerId: customerId,
            projectId: User.getProject('projectId') || '',
            pageNumber: this.state.pageNumber,
            perPageRecords: 20,
            sortByFieldName: 'ticketLastUpdatedDate',
        }, parentContext.state.filters);
        parentContext.setState({ loaded: false });
        Ticket.getList(requestBody)
            .then((result) => {
                parentContext.setState({ tickets: result, loaded: true, error: false });
            })
            .catch((error) => {
                UI.notifyError( error === uiMessages.badRequest ?
                    uiMessages.dataNotFound : error);
                parentContext.setState({ tickets: [], loaded: true, error: true });
            });
    },

    sortByField(event) {
        var currentTarget = event.currentTarget;
        var parentContext = this.props.super;
        var isDescending = _.contains(currentTarget.classList, 'sortArrow');
        var list = parentContext.state.tickets;
        list = Utility.getSortedList(list, isDescending, currentTarget.dataset.fieldName);
        parentContext.setState({ tickets: list });
    },

    getSeverityTypes() {
        var severityTypes = (<li>{CONSTANTS.UI_MESSAGES.dataNotFound}</li>);
        if (this.state.severityTypes) {
            severityTypes = this.state.severityTypes.map(
                function (data, key) {
                    return (<li data-id={data} data-param-name="ticketSeverity"
                        className={ data === 'S1' ? "redText" : ""}
                        onClick={this.getFilteredTickets } key={key}>{data}</li>);
                }.bind(this)
            );
        }
        return severityTypes;
    },

    getTicketTypes() {
        var ticketTypes = (<li>{CONSTANTS.UI_MESSAGES.dataNotFound}</li>);
        if (this.state.ticketTypes) {
            ticketTypes = this.state.ticketTypes.map(
                function (data, key) {
                    return (<li data-id={data} data-param-name="ticketType"
                        onClick={this.getFilteredTickets} key={key}>{data}</li>);
                }.bind(this)
            );
        }
        return ticketTypes;
    },

    getStateTypes() {
        if (this.state.stateTypes) {
            return _.map(this.state.stateTypes, (data, key) => {
                return (<li data-id={data} data-param-name="ticketStatus"
                    onClick={this.getFilteredTickets} key={key}>{data}</li>)
            });
        }
    },

    getFilterLabel(key) {
        var filters = Utility.getVal(this, 'props.super.state.filters');
        var filterlabel = filters[key];
        if (filters[key] === CONSTANTS.TICKET_STATUS_TYPES) {
            filterlabel = CONSTANTS.TICKET_STATUS['ASSIGNED'];
        }
        return filters && _.isArray(filters[key]) && filters[key].length ?
            filterlabel.toString() : CONSTANTS.TICKET_FILTERS[key];
    },

    render() {
        return (
            <li className="rowCell tableHeader">
                <div className="ticketListHeading innerTableWrap paddingZero">
                    <span className="dataCell ticketNoCell">
                        Ticket number
                    </span>
                    <span className="dataCell serverListCell dropDownWrapper severityDropDown">
                        <span className="filterLabel">{this.getFilterLabel('ticketSeverity') }</span>
                        <i className="dropDownArrow"></i>
                        <div className="customSelectGroup">
                            <ul className="customSelectList dropDownData dataCellDropDown severityDropDownList">
                                {
                                    this.getSeverityTypes.call(this)
                                }
                            </ul>
                        </div>
                    </span>
                    <span className="dataCell openSinceCell sortingCell" data-field-name="ticketOpenSince"
                        onClick={this.sortByField}>
                        Open<br/>Since
                        <i className="sortDownArrow hide"></i>
                    </span>
                    <span className="dataCell summaryCell">
                        Summary / Projects
                    </span>
                    <span className="dataCell typeCell dropDownWrapper">
                        <span className="filterLabel">{this.getFilterLabel('ticketType') }</span>
                        <i className="dropDownArrow"></i>
                        <div className="customSelectGroup">
                            <ul className="customSelectList dropDownData dataCellDropDown customDropDown typeDropDown">
                                {
                                    this.getTicketTypes.call(this)
                                }
                            </ul>
                        </div>
                    </span>
                    <span className="dataCell statusCell dropDownWrapper">
                        <span className="filterLabel">{this.getFilterLabel('ticketStatus') }</span>
                        <i className="dropDownArrow"></i>
                        <div className="customSelectGroup">
                            <ul className="customSelectList dropDownData dataCellDropDown customDropDown statusDropDown">
                                {
                                    this.getStateTypes.call(this)
                                }
                            </ul>
                        </div>
                    </span>
                    <span className="dataCell lastUpdatedCell sortingCell" data-field-name="ticketLastUpdatedDate"
                        onClick={this.sortByField}>
                        Last updated
                        <i className="sortDownArrow"></i>
                    </span>
                    <span className="dataCell actionCell">
                        <button className="btn btn-default pull-right" type="submit" onClick={this.props.clearFilters}>Clear Filters</button>
                    </span>
                </div>
            </li>
        )
    }
});

export default TicketListing;
