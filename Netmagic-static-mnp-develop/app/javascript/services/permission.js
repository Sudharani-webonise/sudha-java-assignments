import CONSTANTS from '../constants/app-constant';
import http from '../mixins/restutils';

const Permission = {
  fetchAll: function() {
    return http.performGet(CONSTANTS.API_URLS.allPermissions);
  },

  fetchSplashData: function() {
    return http.performGet(CONSTANTS.API_URLS.splashScreen);
  }
};

export default Permission;
