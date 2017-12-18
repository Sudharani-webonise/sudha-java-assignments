import http from '../mixins/restutils';
import UI from '../mixins/ui';
import Utility from '../mixins/basicUtils';
import CONSTANTS from '../constants/app-constant';

const DashboardService = {
  typeCount: 2,

  getWidgetModuleData: function(apiURL, queryParams) {
    return http.performGet(http.buildRestUrl(apiURL, queryParams));
  },

  getOutstandingWidget: (params) => {
    return http.performPost(CONSTANTS.API_URLS.outstanding.widget, params);
  }
};

export default DashboardService;
