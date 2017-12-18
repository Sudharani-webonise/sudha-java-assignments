import UI from '../../../mixins/ui';
import http from '../../../mixins/restutils';
import Session from '../../../mixins/sessionUtils';
import Utility from '../../../mixins/basicUtils';
import Ticket from '../../../services/ticket';
import Loader from '../../commons/Loader';
import CONSTANTS from '../../../constants/app-constant';
import User from '../../../services/user';
import { Link, Router, history } from 'react-router';
import Dropzone from 'react-dropzone';
import Promise from 'bluebird';
import PostRequestLoader from '../../commons/Loader';

var RaiseTicket = createReactClass({
    mixins: [UI, Utility, User, Ticket],
    contextTypes: {
        router: React.PropTypes.object
    },

    getInitialState() {
        return {
            ticketTypes: [],
            ticketType: {},
            toggleClass: 'radioButtons pull-left clearfix',
            ticketSubTypes: [],
            associatedCustomers: [],
            listOfSupportToCustomerInfo: [],
            associatedProjects: [],
            defaultSubType: CONSTANTS.DEFAULT_SELECTION.caseSubType,
            formData: {
                caseSubType: ''
            },
            associatedContacts: [],
            attachments: [],
            newlyAddedContacts: [],
            selectedCount: 0,
            showLoader: false,
        };
    },

    componentWillMount() {
        let userObj = User.getCustomerParams();
        this.redirectIfUnauthorized.call(this,
            CONSTANTS.PERMISSION_CODES.tickets.raise);
        this.getTicketTypes()
            .then((result) => {
                var defaultType = result && _.first(result);
                this.setState({
                    ticketTypes: result.splice && result.splice(CONSTANTS.ZERO, Ticket.typeCount),
                    ticketType: defaultType,
                    toggleClass: 'radioButtons pull-left clearfix toggleSlide',
                });
                this.callEsofMethod('getTicketSubTypes', 'ticketSubTypes', defaultType.caseTypeId);
            })
            .catch((error) => this.notifyError(error && error.statusText));
        this.callEsofMethod('getAssociatedCustomers',
            'listOfSupportToCustomerInfo');
        this.callEsofMethod('getContacts', 'associatedContacts', {
            id: userObj.customerId,
            associateCustomerId: Session.get('activeCustomerId')
        });
        Session.set('splashScreen', true);
    },

    callEsofMethod(methodName, stateKey, params) {
        this[methodName](params)
            .then((result) => {
                result = result ? this.toObject(result) : [];
                var state = this.getObject(stateKey, result && result[stateKey]
                    || result);
                this.setState(state);
            })
            .catch((error) => UI.notifyError( error));
    },

    get(key) {
        return this.getElement.call(this, key);
    },

    //Form submit action events
    createTicket(event) {
        event.preventDefault();
        let userObj = User.getCustomerParams();
        var getValidationErrorMessage = this.getValidationErrorMessage();
        if (getValidationErrorMessage) {
            return UI.notifyError( getValidationErrorMessage);
        }
        var ticketRequest = {
            customerIdHidden: userObj.customerId,
            supportToCustomerId: this.get('associatedCustomer').dataset.selectedItem || userObj.customerId,
            projectName: this.get('projectName').dataset.selectedItem,
            ticketType: this.state.ticketType.caseTypeId,
            ticketCategory: parseInt(this.get('caseSubType').dataset.selectedItem),
            ticketoriginator: Session.get('user').loginUserEmail,
            ticketSummary: this.get('summary').value,
            ticketDescription: this.get('description').value,
            additionalNotificationPersonTableList: this.getContactList(true) || [],
            listOfFilesInByte: [],
        };
        ticketRequest.ticketSummary = Utility.interpretNewLine(ticketRequest.ticketSummary)
        ticketRequest.ticketDescription = Utility.interpretNewLine(ticketRequest.ticketDescription)
        var fileCount = this.state.attachments.length;
        if (fileCount) {
            this.pushFilesAndCreateTicket(ticketRequest, fileCount);
        } else {
            this.createTicketRequest(ticketRequest);
        }
    },

    getContactList(returnSelected) {
        var result = _.chain(this.state.associatedContacts)
            .map((item) => {
                return {
                    userId: item.contactId,
                    isSelected: item.isSelected,
                    userName: item.contactName || (item.custContFname + ' ' + item.custContLname) || '',
                    userEmail: item.contactEmailId,
                    userMobile: item.contactMobileNo,
                    isNewlyAdded: false
                }
            })
            .union(this.state.newlyAddedContacts);
        if (returnSelected) {
            result = result
                .filter((item) => item.isSelected)
                .map((item) => _.omit(item, 'isSelected', 'userId'));
        }
        return result.value();
    },

    selectContact(id) {
        var contact = _.findWhere(this.state.associatedContacts, { contactId: id });
        if (contact) {
            contact.isSelected = !contact.isSelected;
            this.setState({ associatedContacts: this.state.associatedContacts });
        }
    },

    pushFilesAndCreateTicket(ticketRequest, fileCount) {
        var time = new Date().toString();
        Promise.delay(100)
            .then(function () {
                return _.map(this.state.attachments, (file) => this.readFileInBytes(file));
            }.bind(this))
            .all()
            .then((fileResults) => {
                if (_.isArray(fileResults)) {
                    ticketRequest.listOfFilesInByte = _.map(fileResults, (fileResult, index) => {
                        return {
                            'ticketAttachmentId': index,
                            'ticketAttachmentDatetime': time,
                            'ticketNumber': null,
                            'receivedfile': null,
                            'attachmentFilename': fileResult.file.name,
                            'attachmentFullpath': '',
                            'tick_atta_filesize': fileResult.file.size,
                            'fileExtension': fileResult.file.name.split('.').pop(),
                            'fileInBytes': fileResult.reader.result &&
                            fileResult.reader.result.replace(CONSTANTS.REGEX.BASE_64, '$2')
                        }
                    });
                }

                this.createTicketRequest(ticketRequest);
            })
            .catch((error) => this.notifyError(error));
    },

    createTicketRequest(ticketRequest) {
        this.setState({ showLoader: true });
        http.performPost(CONSTANTS.API_URLS.createTicket, ticketRequest)
            .then((result) => {
                this.notifySuccess(CONSTANTS.UI_MESSAGES.ticketCreated);
                this.context.router.push('/tickets');
                this.setState({ showLoader: false });
            }, (error) => {
                this.notifyError(error)
                this.setState({ showLoader: false });
            });
    },

    //Ticket type and sub type selection events
    handleChange(event) {
        var type = _.findWhere(this.state.ticketTypes,
            { caseTypeId: parseInt(event.currentTarget.value, 10) });
        this.get('caseSubType').dataset.selectedItem = "";
        this.setState({
            ticketType: type,
            toggleClass: `radioButtons pull-left clearfix ${type.caseTypeName === 'Incident' ? '' : 'toggleSlide'}`,
            formData: { caseSubType: this.state.defaultSubType }
        });
        this.callEsofMethod('getTicketSubTypes', 'ticketSubTypes', type.caseTypeId);
    },

    selectCaseSubType(data) {
        return function () {
            this.setState({
                formData: {
                    caseSubType: data.caseSubTypeName
                }
            });
        }.bind(this);
    },

    getProjectsAndContacts(associatedCustomerId) {
        let userObj = User.getCustomerParams();
        this.setState({ associatedContacts: [] });
        var params = {
            id: userObj.customerId,
            associateCustomerId: userObj.assoCustomerId
        };
        this.callEsofMethod('getProjects', 'associatedProjects', params);
        this.callEsofMethod('getContacts', 'associatedContacts', params);
        this.refs.selectedProject.innerText = '';
        this.setState({ newlyAddedContacts: [], selectedCount: 0, associatedProjects: [] });
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

    updateCount(status) {
        this.state.selectedCount = status ? this.state.selectedCount+1 : this.state.selectedCount-1;
        this.setState(this.state);
    },

    addContact() {
        var refs = this.refs;
        var contacts = _.where(this.state.newlyAddedContacts,
            { userEmail: (refs.email.value).toLowerCase() });
        var selectedContacts = _.where(this.state.associatedContacts,
            { contactEmailId: refs.email.value });
        if (!refs.name.value) {
            return UI.notifyError( CONSTANTS.UI_MESSAGES.emptyFields);
        } else if (!this.checkEmail(refs.email.value)) {
            return UI.notifyError( CONSTANTS.UI_MESSAGES.wrongEmail);
        } else if (!this.checkPhone(refs.phone.value)) {
            return UI.notifyError( CONSTANTS.UI_MESSAGES.wrongPhone);
        } else {
            if ((_.isArray(contacts) && contacts.length) ||
                (_.isArray(selectedContacts) && selectedContacts.length)) {
                return UI.notifyError( CONSTANTS.UI_MESSAGES.duplicateMailId);
            }
            this.state.newlyAddedContacts.push({
                userName: refs.name.value,
                userEmail: refs.email.value,
                userMobile: refs.phone.value,
                isNewlyAdded: false,
            });
            this.setState(this.state);
            this.refs.name.value = "";
            this.refs.email.value = "";
            this.refs.phone.value = "";
        }
    },

    checkIfValid(value, defaultValue) {
        return _.isEqual(value, defaultValue) || _.isUndefined(value) ? "" : value
    },

    getValidationErrorMessage() {
        const message = CONSTANTS.UI_MESSAGES;
        const defaultSelections = CONSTANTS.DEFAULT_SELECTION;
        var caseSubType = _.chain(this.state.ticketSubTypes)
            .where({ caseSubTypeId: Number(this.get('caseSubType').dataset.selectedItem) })
            .first()
            .value();
        var emptyFields = _.every(this.getValues('.mandatory'), (item) => {
            if (_.contains(item.classList, 'customer')) {
                if(User.getUserInfo('isInternalUser')) {
                    return this.checkIfValid(User.getMainCustomer('cvCrmId') || User.getAssociateCustomer('cvCrmId'), defaultSelections.associatedCustomer);
                } else {
                    return this.checkIfValid(this.get('associatedCustomer').dataset.selectedItem, defaultSelections.associatedCustomer);
                }
            } else if (_.contains(item.classList, 'subTypeList')) {
                return this.checkIfValid(caseSubType && caseSubType.caseSubTypeId, defaultSelections.caseSubType)
            } else {
                return item.value;
            }
        }) ? '' : message.emptyFields;
        var contacts = this.getContactList(true).length > CONSTANTS.MAX_CONTACTS ?
            message.addMaxContact : '';
        var summary = this.get('summary').value;
        var checkSummary = summary && summary.trim() ? '' : message.emptyFields;
        var description = this.get('description').value;
        var checkDescription = description && description.trim() ? '' : message.emptyFields;
        return emptyFields || checkSummary || checkDescription || contacts;
    },

    getValues(selector) {
        return this.refs.createTicketForm &&
            $(this.refs.createTicketForm).find(selector);
    },

    getAssociatedCustomerData(customers) {
        var customerName = CONSTANTS.DEFAULT_SELECTION.associatedCustomer;
        if(!(_.isArray(customers) && customers.length && customers.length != 1)) {
            customerName = User.getMainCustomer('cvName') || User.getMainCustomer('customerName');
        }
        return customerName;
    },

    dropDownClassSet(data) {
        return UI.classSet({
            'customSelectGroup': true,
            'pull-left': true,
            'disableDropDown': !(_.isArray(data) && data.length)
        });
    },

    render() {
        var handleChange = this.handleChange;
        var {listOfSupportToCustomerInfo, associatedProjects, ticketSubTypes, associatedContacts} = this.state;
        return (
            <div className="raiseElementWrap">
                <section className="breadCrumbSection">
                    <div className="container">
                        <div className="row">
                            <div className="col-xs-10">
                                <ol className="breadcrumb breadCrumbCustom clearfix">
                                    <li><Link to={User.getSplashScreenURL()}>Back to Home</Link></li>
                                    <li><Link to="dashboard">Back to Dashboard</Link></li>
                                    <li className="raiseLink active">Raise Ticket</li>
                                </ol>
                            </div>
                        </div>
                        <div className="row raiseTicketWrap">
                            <div className="col-xs-10">
                                <h2>
                                    raise ticket
                                </h2>
                            </div>
                        </div>
                    </div>
                </section>
                <PostRequestLoader show={this.state.showLoader} />
                <article className="container paddingZero">
                    <Loader />
                    <div className="alert customAlertMsg fade in">
                        <a href="javascript:void(0);" className="close" data-dismiss="alert" aria-label="close">&times; </a>
                        <strong className='message'></strong>
                    </div>

                    <div className="row raiseTicketSection">
                        <div className="col-xs-12">
                            <form type="POST" onSubmit={this.createTicket} className="formTypeInline raiseTicketForm form-inline" name="ticketDetailForm" ref="createTicketForm">
                                <div className="formInnerWrap">
                                    <div className="radioWrap clearfix marginBottom17">
                                        <span className="pull-left formCustomLabel">Choose ticket type: </span>
                                        <div className={this.state.toggleClass}>
                                            { _.map(this.state.ticketTypes, (data, key) => {
                                                return (<label key={key} className="customRadioLabel" htmlFor={data.caseTypeId === 3 ? "2" : "3"}>
                                                    <input type="radio" key={key} id={data.caseTypeId} value={data.caseTypeId} name={data.caseTypeName} checked={data.caseTypeName === "Request"} onChange={handleChange} />
                                                    {data.caseTypeName}
                                                </label>)
                                            })
                                            }
                                            <span className="toggleIcon"></span>
                                        </div>
                                    </div>
                                    <div className="selectBoxWrap clearfix marginBottom17">
                                        <div className={this.dropDownClassSet(listOfSupportToCustomerInfo)}>
                                            <label htmlFor="associatedCustomer">Associated Customer<span className="mandatoryStar">*</span></label>
                                            <ul className="customSelectList mandatory dropDownData ticketCustomList customer" id="associatedCustomer" ref="associatedCustomer">
                                                <li>Select Associated Customer</li>
                                                {
                                                    _.map(this.state.listOfSupportToCustomerInfo, (data, key) => {
                                                        return (
                                                            <li key={key} data-id= {data.cvCrmId} data-code={data.cvCode} data-generic-email={data.cvGenericEmail}
                                                                onClick={this.getProjectsAndContacts.bind(null, data.cvCrmId) }>
                                                                {data.cvName}
                                                            </li>
                                                        )
                                                    })
                                                }
                                            </ul>
                                            <p className="selectContent">{this.getAssociatedCustomerData(listOfSupportToCustomerInfo)}</p>
                                        </div>
                                        <div className={this.dropDownClassSet(associatedProjects)}>
                                            <label htmlFor="projectName">Project Name</label>
                                            <ul className="customSelectList dropDownData ticketCustomList project" id="projectName" ref="projectName">
                                                <li>Select Project Name</li>
                                                {
                                                    _.map(this.state.associatedProjects, (data, key) => {
                                                        return (
                                                            <li key={key} data-id={data.projectName}>
                                                                {data.projectName}
                                                            </li>
                                                        )
                                                    })
                                                }
                                            </ul>
                                            <p className="selectContent" ref="selectedProject"></p>
                                        </div>
                                        <div className="customSelectGroup pull-left">
                                            <label htmlFor="caseSubType">Case Sub Type<span className="mandatoryStar">*</span></label>
                                            <ul className="customSelectList mandatory ticketCustomList subTypeList" id="caseSubType" ref="caseSubType">
                                                {
                                                    _.map(this.state.ticketSubTypes, (data, key) => {
                                                        return (
                                                            <li onClick={this.selectCaseSubType(data) } data-id={data.caseSubTypeId} key={key}>
                                                                {data.caseSubTypeName}
                                                            </li>
                                                        )
                                                    })
                                                }
                                            </ul>
                                            <p className="selectContent">{this.state.formData.caseSubType}</p>
                                        </div>
                                    </div>
                                    <div className="sumDescWrap marginBottom17">
                                        <div className="formEditWrap">
                                            <label htmlFor="summary">Summary<span className="mandatoryStar">*</span></label>
                                            <textarea className="mandatory summary" id="summary" ref="summary" required="true" maxLength="200"></textarea>
                                        </div>
                                        <div className="formEditWrap">
                                            <label htmlFor="description">Description<span className="mandatoryStar">*</span></label>
                                            <textarea id="description" className="mandatory description" ref="description" required="true"></textarea>
                                        </div>
                                    </div>
                                    <div className="draggableWrap marginBottom17 clearfix dropZone">
                                        <ul className="clearfix">
                                            {this.showAttachments() }
                                        </ul>
                                        <Dropzone className="pull-left dropArea" onDrop={this.addAttachment}>
                                            <i className="NMIcon-attachFile pull-left"></i>
                                            <p className="pull-left">Drag &amp; Drop your files here, or <span>browse attachment</span></p>
                                        </Dropzone>
                                    </div>
                                </div>
                                <section className="additionalNotify">
                                    <div className="addNewContact">
                                        <h2>
                                            additional notifications
                                        </h2>
                                        <span className="formCustomLabel">Add new contact: </span>
                                        <div className="inputBoxesWrap marginBottom17 clearfix">
                                            <div className="customTextGroup pull-left">
                                                <label htmlFor="name">Name</label>
                                                <input type="text" id="name" ref="name" onKeyPress={UI.checkIfAlphabets}/>
                                            </div>
                                            <div className="customTextGroup pull-left">
                                                <label htmlFor="email">Email</label>
                                                <input type="email" id="email" ref="email" />
                                            </div>
                                            <div className="customTextGroup pull-left">
                                                <label htmlFor="phone">Contact number</label>
                                                <input type="text" id="phone" ref="phone"/>
                                            </div>
                                            <input type="button" value="Add Contact" id="submitForm" onClick={this.addContact} className="darkBlueBtn pull-left" />
                                        </div>
                                        <span className="formCustomLabel">Choose from contact list: </span> <span className="maxContactInfo">(You can select maximum 10 contacts)</span>
                                        <ul className="preContactList clearfix">
                                            {
                                                _.map(this.getContactList(), (data, key) => {
                                                    return (<li key={key}><AssociatedContacts checked={this.state.checked} data={data}
                                                        selectContact={this.selectContact} selectedCount={this.state.selectedCount}
                                                        updateCount = {this.updateCount} /></li>)
                                                })
                                            }
                                        </ul>
                                        <div className="raiseTicketBtns clearfix">
                                            <input type="submit" value="Raise Ticket" id="submitForm" className="darkPinkBtn pull-left"/>
                                            <Link to="tickets" id="cancelForm" className="pull-left customLinks">Cancel</Link>
                                        </div>
                                    </div>
                                </section>
                            </form>
                        </div>
                    </div>
                </article>
            </div>
        )
    }
});

var AssociatedContacts = createReactClass({
    getInitialState() {
        return {
            checked: false,
        }
    },

    checkedLabel() {
        if(!this.state.checked)
        {
            if(this.props.selectedCount < 10)
            {
                this.props.updateCount(true)
                this.setState({ checked: !this.state.checked });
            }
        }
        else{
            if(this.props.selectedCount)
            {
                this.props.updateCount(false)
                this.setState({ checked: !this.state.checked });
            }
        }
    },

    render() {
        var contact = this.props.data;
        return (
            <label className="contactListInner clearfix">
                <div className="customCheckBox otherBrowserSupport pull-left">
                    {contact && contact.isNewlyAdded ?
                        (<input type="checkbox" id={CONSTANTS.NEW_TICKET_ID} checked="checked" />) :
                        (<input type="checkbox"  id={contact && contact.userId} onClick={this.props.selectContact.bind(null, contact && contact.userId) }/>)
                    }
                    <label htmlFor={contact ? contact.userId : CONSTANTS.NEW_TICKET_ID}
                        onClick={this.checkedLabel} className={ this.state.checked ||
                            contact.isNewlyAdded ? "checked" : "unchecked"}></label>
                </div>
                <div className="contactInfoWrap">
                    <h4 data-name={contact.userName}>{contact.userName || '-'}</h4>
                    <p data-email={contact.userEmail}>{contact.userEmail || '-'}</p>
                    <p data-phone={contact.userMobile}>{contact.userMobile || '-'}</p>
                </div>
            </label>
        )
    }
});

export default RaiseTicket;
