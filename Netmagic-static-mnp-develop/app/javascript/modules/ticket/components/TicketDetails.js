import { Link } from 'react-router';
import Session from '../../../mixins/sessionUtils';
import CONSTANTS from '../../../constants/app-constant';
import Ticket from '../../../services/ticket';
import User from '../../../services/user';
import http from '../../../mixins/restutils';
import Utility from '../../../mixins/basicUtils';
import Time from '../../../mixins/time';
import Loader from 'react-loader';
import LoaderOptions from '../../../mixins/loader';
import UI from '../../../mixins/ui';
import Dropzone from 'react-dropzone';

var TicketDetails = createReactClass({

    mixins: [User, Session, Ticket, Utility, UI],
    contextTypes: {
        router: React.PropTypes.object
    },

    getInitialState() {
        var user = this.get('user');
        return {
            loaded: false,
            attachments: [],
            ticketDetail: {
                ticketInformationBean: {
                    assignedToGroup: "",
                    ticketOwner: "",
                    ticketProject: "",
                    ticketSeverity: "",
                    caseType: "",
                    caseSubType: "",
                    ticketStatus: "",
                    ticketsummary: "",
                    ticketCustomerDesc: "",
                    ticketOpenSince: "",
                    ticketRaisedBy: "",
                    ticketCreatedDate: "",
                    ticketLastUpdatedDate: ""
                },
                ticketWorklogUpdatesBeans: []
            },
            enable: false,
            showToolTipBox: false,
            collapse: false
        };
    },

    componentWillMount() {
        User.redirectIfUnauthorized.call(this,
            CONSTANTS.PERMISSION_CODES.tickets.details);
    },

    componentDidMount() {
        return this.getTicketDetails();
    },

    componentWillUnmount() {
        Session.remove('ticketId');
    },

    getTicketDetails() {
        const uiMessages = CONSTANTS.UI_MESSAGES;
        this.getDetails(Session.get('ticketId'))
            .then((res) => {
                this.state.loaded = true;
                if (res.ticketInformationBean && res.ticketWorklogUpdatesBeans) {
                    this.state.ticketDetail.ticketInformationBean = res.ticketInformationBean;
                    this.state.ticketDetail.ticketWorklogUpdatesBeans = res.ticketWorklogUpdatesBeans;
                } else {
                    UI.notifyError( uiMessages.noTicketFound);
                }
                this.setState(this.state);
            })
            .catch((err) => {
                if (err && err.statusText === uiMessages.unauthorized) {
                    UI.notifyError( uiMessages.unauthorized);
                } else {
                    UI.notifyError( err.statusText);
                }
            });
    },

    updateWorklog(event) {
        event.preventDefault();
        var comment = this.refs.comment;
        var time = new Date().toString();
        var ticketNumber = Session.get('ticketId');
        var fileCount = this.state.attachments.length;
        var user = Session.get('user');
        var loginUserName = user && user.loginUserName;
        var loginEmailId = user && user.loginUserEmail;
        var worklogUpdateRequest = {
            ticketNumber: ticketNumber,
            worklogComment: comment.value,
            worklogUserName: loginUserName + ' ' + '(' + loginEmailId + ')',
            uploadFilesList: [],
        };
        if (comment && !comment.value.trim() && !fileCount) {
            return UI.notifyError( CONSTANTS.UI_MESSAGES.emptyComment)
        }
        if (fileCount) {
            var base64 = CONSTANTS.REGEX.BASE_64;
            var count = 0;
            _.each(this.state.attachments, (file, index) => {
                var reader = new FileReader();
                reader.onload = function () {
                    worklogUpdateRequest.uploadFilesList.push({
                        ticketAttachmentId: index,
                        ticketAttachmentDatetime: time,
                        ticketNumber: ticketNumber,
                        receivedfile: null,
                        attachmentFilename: file.name,
                        attachmentFullpath: '',
                        tick_atta_filesize: file.size,
                        fileExtension: file.name.split('.').pop(),
                        fileInBytes: reader.result && reader.result.replace(base64, '$2')
                    });
                    count++;
                    if (count === fileCount) {
                        this.callUpdateWorklog(worklogUpdateRequest);
                    }
                }.bind(this);

                reader.readAsDataURL(file);
            });
        } else {
            this.callUpdateWorklog(worklogUpdateRequest);
        }
    },

    addAttachment(files) {
        var updatedFiles = this.verifyAndFetchAttachments(files, this.state.attachments);
        this.setState({ attachments: updatedFiles });
    },

    removeAttachment(event) {
        var attachments = this.state.attachments;
        attachments.splice(event.currentTarget.dataset.index, 1);
        this.setState({ attachments: attachments });
    },

    showAttachments() {
        return _.map(this.state.attachments, (data, index) => {
            return (<li key={index}>
                <i className="NMIcon-attachment pull-left"></i>
                <i className="cancel" data-index={index} onClick={this.removeAttachment}>X</i>
                <p className="pull-left">{data.name || 'File ' + index }</p>
            </li>)
        });
    },

    showSeverityName(severityName) {
        if (severityName === CONSTANTS.TICKET_SEVERITIE_S1) {
            return (<span className="severityTextColor labelData">{severityName}</span>);
        } else {
            return (<span className="labelData">{severityName}</span>);
        }
    },

    callUpdateWorklog(worklogUpdateRequest) {
        Ticket.updateTicketWorklog(Session.get('ticketId'), worklogUpdateRequest)
            .then(this.successCallback)
            .catch(this.errorCallback);
    },

    successCallback(result) {
        UI.notifySuccess( CONSTANTS.UI_MESSAGES.worklogUpdated);
        this.setState({ attachments: [] });
        this.getTicketDetails();
        this.refs.comment.value = '';
    },

    errorCallback(error) {
        //NOTE: temporary fix. Success message also coming as error from NM side
        if (error === 'OK') {
            return this.successCallback();
        }
        UI.notifyError( CONSTANTS.UI_MESSAGES.worklogError);
    },

    showAttachmentCount() {
        var ticket = this.state.ticketDetail;
        return _.chain(ticket && ticket.ticketWorklogUpdatesBeans).pluck('listOfTicketAttachmentBeans').flatten().value().length;
    },

    ticketUpdate(status, event) {
        event.preventDefault();
        var comment = this.refs.closeComment.value;
        var user = Session.get('user');
        var ticketNumber = Session.get('ticketId');
        var params = {
            ticketNumber: ticketNumber,
            updateComment: comment,
            updatedByUser: user && user.loginUserName,
            changeStatus: status
        };
        Ticket.updateTicket(ticketNumber, params)
            .then((res) => {
                UI.notifySuccess(
                    ticketNumber + ' ' + CONSTANTS.UI_MESSAGES.ticketStatus);
                this.getTicketDetails();
            })
            .catch((err) => UI.notifyError( err));
    },

    handleChange(event) {
        var comment = this.refs.closeComment.value;
        this.setState({ enable: comment && comment.trim() ? true : false });
    },

    getBtnClassSet() {
        return UI.classSet({
            'darkPinkBtn': true,
            'pull-left': true,
            'disableLinkBtn': !this.state.enable
        });
    },

    showUpdateStatusForm() {
        var status = Utility.getVal(this, 'state.ticketDetail.ticketInformationBean.ticketStatus');
        return (status === CONSTANTS.TICKET_STATUS.PENDING) ?
            (<div>
                <h2>Update Actions</h2>
                <form className="detailsPostComment clearfix">
                    {
                        User.canAccess(CONSTANTS.PERMISSION_CODES.tickets.edit) ?
                        <input type="text" name="comment" id="postComment" ref="closeComment" placeholder="Comments for action..."
                        className="postText pull-left" onChange={this.handleChange}/> : null
                    }
                    {
                        User.canAccess(CONSTANTS.PERMISSION_CODES.tickets.reopen) ?
                        <div>
                            <input type="submit" onClick= {this.ticketUpdate.bind(null, 'reopen') } className={this.getBtnClassSet() } value="Reopen" />
                            <input type="submit" onClick= {this.ticketUpdate.bind(null, 'closed') } className={this.getBtnClassSet() } value="Close" />
                        </div> : null
                    }
                </form>
            </div>) :
            (null)
    },

    getAllAttachments(attachments) {
        return _.chain(attachments)
            .pluck('listOfTicketAttachmentBeans')
            .flatten()
            .value();
    },

    showAllAttachments() {
        var attachments = _.map(this.getAllAttachments(this.state.ticketDetail.ticketWorklogUpdatesBeans),
            (attachment, key) => {
                return (
                    <a key={key} className="fileAttachment clearfix" href={Ticket.downloadLink(attachment.ticketAttachmentId) }>
                        <span className="downloadIcon"></span>
                        <span className="downloadText">{attachment.attachmentFilename}</span>
                    </a>
                )
            });
        return attachments;
    },

    showToolTipBox() {
        this.setState({ showToolTipBox: !this.state.showToolTipBox });
    },

    getAccordianBtnClass() {
        return this.classSet({
            'toggleUpDown': true,
            'IconDoubleArrow': true,
            'pull-right': true,
            'rotateArrow': this.state.collapse
        });
    },

    getToggleClassSet() {
        return this.classSet({
            'listsDetailsView': true,
            'clearfix': true,
            'show': this.state.collapse,
            'hide': !this.state.collapse
        })
    },

    toggle() {
        this.setState({ collapse: !this.state.collapse })
    },

    render() {
        var ticket = this.state.ticketDetail.ticketInformationBean;
        var attachments = this.getAllAttachments(this.state.ticketDetail.ticketWorklogUpdatesBeans);
        return (
            <section className="ticketDetailsSection">
                <section className="breadCrumbSection">
                    <div className="container">
                        <div className="row">
                            <div className="col-xs-10 col-sm-10 col-md-10">
                                <ol className="breadcrumb breadCrumbCustom clearfix">
                                    <li><Link to={User.getSplashScreenURL()}>Back to Home</Link></li>
                                    <li><Link to="dashboard" activeClassName="">Back to Dashboard</Link></li>
                                    <li className="ticketListBread"><Link to="tickets" activeClassName="">Ticket Listing</Link></li>
                                    <li className="detailsBread2 active">{Session.get('ticketId')}</li>
                                </ol>
                            </div>
                        </div>
                    </div>
                </section>
                <Loader loaded={this.state.loaded} top="50%" left="50%" options={LoaderOptions}>
                    <article className="container paddingTop103 ticketHeadDetailsWrap">
                        <div className="row">
                            <div className="col-sm-3 col-md-3">
                                <div className="assignedToWrap cleafix pull-left">
                                    <span className="NMIcon-assignedUserIcon pull-left"></span>
                                    <p className="pull-left userGroupLabel">
                                        <span className="lightTextColor">Assigned to</span><br/>
                                        <span className="labelData boldFont">{ticket && ticket.assignedToGroup}</span>
                                    </p>
                                </div>
                            </div>
                            <div className="col-sm-2 col-md-2">
                                <p className="pull-left userGroupLabel">
                                    <span className="lightTextColor">Project</span><br/>
                                    <span className="labelData">{ticket && ticket.ticketProject}</span>
                                </p>
                            </div>
                            <div className="col-sm-6 col-md-6">
                                <div className="listsDetailsView clearfix ">
                                    <p className="pull-left userGroupLabel">
                                        <span className="lightTextColor">Severity</span><br/>
                                        {ticket && this.showSeverityName(ticket.ticketSeverity) }
                                    </p>
                                    <p className="pull-left userGroupLabel">
                                        <span className="lightTextColor">Case Type </span><br/>
                                        <span className="labelData">{ticket && ticket.caseType}</span>
                                    </p>

                                    <p className="pull-left userGroupLabel">
                                        <span className="lightTextColor">Status</span><br/>
                                        <span className="labelData">{ticket && ticket.ticketStatus}</span>
                                    </p>
                                    <p className="pull-left userGroupLabel">
                                        <span className="lightTextColor">Open Since</span><br/>
                                        <span className="labelData">{ticket && Time.duration(ticket.ticketOpenSince) }</span>
                                    </p>
                                </div>
                                <div className={this.getToggleClassSet() }>
                                    <p className="pull-left userGroupLabel">
                                        <span className="lightTextColor">Sub Type </span><br/>
                                        <span className="labelData">{ticket && ticket.caseSubType ? ticket.caseSubType : "N/A"}</span>
                                    </p>
                                    <p className="pull-left userGroupLabel textOverflow">
                                        <span className="lightTextColor">Raised by</span><br/>
                                        <span className="labelData">{ticket && ticket.ticketRaisedBy}</span>
                                    </p>
                                    <p className="pull-left userGroupLabel">
                                        <span className="lightTextColor">Created</span><br/>
                                        <span className="labelData">{ticket && Time.format(ticket.ticketCreatedDate, CONSTANTS.DATE_FORMAT) }</span>
                                    </p>
                                    <p className="pull-left userGroupLabel">
                                        <span className="lightTextColor">Last Updated</span><br/>
                                        <span className="labelData">{ticket && Time.format(ticket.ticketLastUpdatedDate, CONSTANTS.DATE_FORMAT) }</span>
                                    </p>
                                </div>
                            </div>
                            <div className="col-sm-1 col-md-1">
                                <span onClick={this.toggle } className={this.getAccordianBtnClass() }>
                                    <i className="NMIcon-doubleArrowUp"></i>
                                </span>
                            </div>
                        </div>
                    </article>
                    <section className="bigMessageInfo bgGreyWrap">
                        <section className="container paddingZero">
                            <h3 id="ticketSummary" dangerouslySetInnerHTML={{__html: ticket.ticketSummary}} ></h3>
                            <p id="ticketCustomerDesc" dangerouslySetInnerHTML={{__html: ticket.ticketCustomerDesc}} ></p>
                        </section>
                    </section>
                    <section className="workLogNotification container">
                        {this.showUpdateStatusForm() }
                        <div className="marginTop8"></div>
                        <h2>
                            Worklog
                            <span className="mesgLabel mesgLabel-danger" onClick={this.showToolTipBox}>Attachments: {this.showAttachmentCount() }</span>
                            { this.state.showToolTipBox ? <div className="tipBox">
                                <h2>Download Attachments</h2>
                                { this.showAllAttachments() }
                            </div> : null }
                        </h2>
                        {
                            User.canAccess(CONSTANTS.PERMISSION_CODES.tickets.edit) ?
                            <form onSubmit={this.updateWorklog} className="detailsPostComment clearfix" method="POST">
                                <input type="text" name="comment" id="postComment" ref="comment" placeholder="Post a comment"
                                    className="postText pull-left" />
                                <input type="submit" className="darkPinkBtn pull-left" value="Send" />
                                <div className="draggableWrap marginBottom17 clearfix dropZone ticketDropZone">
                                    <ul className="clearfix">
                                        {this.showAttachments() }
                                    </ul>
                                    <Dropzone className="pull-left dropArea" onDrop={this.addAttachment}>
                                        <i className="NMIcon-attachFile pull-left"></i>
                                        <p className="pull-left">Drag &amp; Drop your files here, or <span>browse attachment</span></p>
                                    </Dropzone>
                                </div>
                            </form> : null
                        }

                        {this.state.ticketDetail ? this.state.ticketDetail.ticketWorklogUpdatesBeans.map(function (data, key) {
                            return (<TicketWorkLog data={data} key={key} />)
                        }) : (<div>{CONSTANTS.UI_MESSAGES.dataNotFound}</div>) }
                    </section>
                </Loader>

            </section>

        )
    }
});

var TicketWorkLog = createReactClass({
    commentClassList(source) {
        var classList = 'commentCommonProp ';
        if (source && source.match(CONSTANTS.REGEX.NETMAGIC)) {
            return classList += 'greenCommentSection brdrRadiusLeftNone';
        }
        return classList += 'pinkCommentSection brdrRadiusRightNone';
    },

    downloadAttachmentFile(id) {
        Ticket.downloadAttachment(id)
            .then((res) => UI.notifySuccess( CONSTANTS.UI_MESSAGES.downloadSuccess))
            .catch((err) => UI.notifyError( CONSTANTS.UI_MESSAGES.downloadFailure));
    },

    getCommentFragment(index) {
        const commentWithAuthorRegex = /([\W|\w]*)\( Commented by :([\W|\w]*) \(/;
        var commentSplit = commentWithAuthorRegex.exec(this.props.data.worklogCustomerComment);
        return _.isArray(commentSplit) ? commentSplit[index] : this.props.data.worklogCustomerComment;
    },

    getUserName() {
        const userNameRegex = /([\W|\w]*)\(([\W|\w]*)\)/;
        var user = this.props.data.worklogUpdatedByUser;
        if (userNameRegex.test(user)) {
            user = userNameRegex.exec(user)[1];
        }
        return user;
    },

    getCommentTime() {
        var commentTime = this.props.data.workLogUpdatetime;
        return commentTime && Time.format(this.props.data.workLogUpdatetime,
            'DD MMM YYYY HH:mm:ss', 'YYYY-MM-DD HH:mm:ss') || '-';
    },

    render() {
        var data = this.props.data;
        return (
            <div className="viewCommentingSection clearfix">
                <div className="userProfilePic pull-left">
                    <span className="NMIcon-userPink userPinkIcon"></span>
                </div>
                <div className="commentWrapper pull-right">
                    <div className={this.commentClassList(data.worklogSource) }>
                        <div className="clearfix">
                            <h4 className="userName pull-left">{this.getUserName() }</h4>
                            <span className="commentTime pull-right">{this.getCommentTime() }</span>
                        </div>
                        <p className="userReply">{this.getCommentFragment(1) }</p>
                        <ul>
                            {
                                data.listOfTicketAttachmentBeans.map((attachment, key) => {
                                    return (<li className="clearfix" key={key}>
                                        <a className="attachmentLink clearfix" href={Ticket.downloadLink(attachment.ticketAttachmentId) }>
                                            <i className="NMIcon-download pull-left"></i>
                                            <p className="pull-left downloadFile">{attachment.attachmentFilename || 'File ' + key }</p>
                                        </a>
                                    </li>)
                                })
                            }
                        </ul>
                    </div>
                </div>
            </div>
        )
    }
});

export default TicketDetails;
