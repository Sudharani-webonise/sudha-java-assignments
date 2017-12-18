import http from '../mixins/restutils';
import UI from '../mixins/ui';
import Utility from '../mixins/basicUtils';
import CONSTANTS from '../constants/app-constant';
import Session from '../mixins/sessionUtils';
import User from './user';

const Ticket = {
  typeCount: 2,
  perPageRecord: 20,
  getList: function (body) {
    return http.performPost(CONSTANTS.API_URLS.tickets, body);
  },

  getDetails: (id) => {
    var url = http.buildRestUrl(CONSTANTS.API_URLS.ticketDetails,
      { ticketId: id });
    return http.performGet(url);
  },

  getConfigData: function () {
    return http.performGet(CONSTANTS.API_URLS.ticketsConfig);
  },

  getTicketTypes: () => {
    return http.performGet(CONSTANTS.API_URLS.ticketTypes);
  },

  getTicketSubTypes: (typeId) => {
    return http.performGet(CONSTANTS.API_URLS.ticketSubTypes + typeId);
  },

  getAssociatedCustomers: () => {
    var url = '';
    let userObj = User.getCustomerParams();
    var param = {customerId: userObj.customerId}
    if(User.getUserInfo('isInternalUser')) {
      url = http.buildRestUrl(CONSTANTS.API_URLS.internalUser.associatedCustomers, param);
    }
    else {
      url = http.buildRestUrl(CONSTANTS.API_URLS.associatedCustomers, param);
    }
    return http.performGet(url)
  },

  getProjects: (params) => {
    var url = User.getUserInfo('isInternalUser') ? CONSTANTS.API_URLS.internalUser.projects : CONSTANTS.API_URLS.associatedProjects
    return http.performGet(http.buildRestUrl(url, params));
  },

  getContacts: (params) => {
    return http.performGet(http.buildRestUrl(CONSTANTS.API_URLS.associatedContacts, params));
  },

  downloadAttachment: (id) => {
    if(id) {
      return http.performGet(CONSTANTS.API_URLS.downloadTicketAttachment,
        { attachmentId: id }, { contentType: 'application/octet-stream'});
    }
  },

  updateTicketWorklog: (id, updateRequest) => {
    var url = http.buildRestUrl(CONSTANTS.API_URLS.ticketUpdateWorklog,
      { ticketNumber: id });
    return http.performPut(url, updateRequest, { dataType: 'text' });
  },

  downloadLink: (id) => {
    var baseUrl = CONSTANTS.APP.apiBaseUrl.concat(CONSTANTS.API_URLS.downloadTicketAttachment);
    return http.buildRestUrl(baseUrl, { id: id });
  },

  verifyAndFetchAttachments: (files, existingFiles) => {
    var updatedFiles = _.union(existingFiles, files);
    var messages = CONSTANTS.UI_MESSAGES;
    var error = '';
    if(Utility.isFileTypeNotAllowed(files)) {
      error = messages.fileTypeNotAllowed;
    } else if(Utility.isAnyFileTooLarge(files)) {
      error = messages.fileTooLarge;
    } else if(updatedFiles.length > CONSTANTS.MAX_WORKLOG_ATTACHMENTS) {
      error = messages.maxAttachments;
    }
    return error && !UI.notifyError( error) ? existingFiles : updatedFiles;
  },

  updateTicket: (id, updateRequest) => {
    var baseUrl = http.buildRestUrl(CONSTANTS.API_URLS.ticketStatusUpdate,
      { ticketNumber: id });
    return http.performPut(baseUrl, updateRequest);
  }
};

export default Ticket;
