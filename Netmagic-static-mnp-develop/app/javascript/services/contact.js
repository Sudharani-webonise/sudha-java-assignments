import http from '../mixins/restutils';
import UI from '../mixins/ui';
import CONSTANTS from '../constants/app-constant';
import Session from '../mixins/sessionUtils';

const CONTACT_URLS = CONSTANTS.API_URLS.contacts;
const Contact = {
  getContactData: (apiUrl, queryParams) => {
    return http.performGet(apiUrl, queryParams || {});
  },

  updateInfo: (newDetails) => {
    return http.performPut(CONTACT_URLS.update, newDetails)
  },

  getCallingConfigData: () => http.performGet(CONTACT_URLS.config),

  getTypes: () => http.performGet(CONTACT_URLS.types),

  getSubTypes: (typeId) => http.performGet(CONTACT_URLS.subTypes, {typeId: typeId}),

  getNotificationData: (customerId) => {
    var url = http.buildRestUrl(CONTACT_URLS.notification, { customerId: customerId });
    return http.performGet(url);
  },

  createNew: (contactInfo) => http.performPost(CONTACT_URLS.create, contactInfo)
};

export default Contact;
