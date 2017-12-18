import http from '../mixins/restutils';
import UI from '../mixins/ui';
import CONSTANTS from '../constants/app-constant';

const Service = {
  getSOFLineItems: function(sofNumber, serviceStatus) {
    var url = http.buildRestUrl(CONSTANTS.API_URLS.serviceSOFLineItems,
    { contractnumber: sofNumber });
    return http.performGet(url, { statusCode: serviceStatus, page: 1 });
  },

  getSOFList: function(params, serviceStatus) {
    return http.performGet(http.buildRestUrl(CONSTANTS.API_URLS.serviceSOFList,
      params), { statusCode: serviceStatus, page: 1 });
  }
};

export default Service;
