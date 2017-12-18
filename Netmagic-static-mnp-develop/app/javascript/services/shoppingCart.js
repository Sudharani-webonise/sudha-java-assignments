import http from '../mixins/restutils';
import UI from '../mixins/ui';
import Utility from '../mixins/basicUtils';
import CONSTANTS from '../constants/app-constant';

const ShoppingCartService = {
  getData: (apiURL, queryParams) => http.performGet(apiURL, queryParams),
  getBillingGroup: (apiURL, queryParams) => http.performGet(apiURL, queryParams),
  saveCart: (apiURL, requestBody) => http.performPost(apiURL, requestBody),
  getPricing: (apiURL, requestBody) => http.performPost(apiURL, requestBody),
  getContract: (apiURL, requestBody) => http.performPost(apiURL, requestBody),
  getAddresses: (apiURL, requestBody) => http.performPost(apiURL, requestBody),

  getProjectsFromAPI: (params) => {
    return http.performGet(http.buildRestUrl(CONSTANTS.API_URLS.associatedProjects,
      params));
  },
  addInfra: (apiUrl, requestBody) => http.performPost(apiUrl, requestBody),
  getProductList: (apiUrl) => http.performGet(apiUrl),
  deleteProductItem: (queryParams) => {
    return http.performDelete(CONSTANTS.API_URLS.shoppingCart.deleteInfra, queryParams);
  },

  sofCreation: (url, requestBody) => {
    return http.performPost(url, requestBody);
  },

  fetchSofs: (url) => {
    return http.performGet(url);
  },

  getCorporateItem: (url) => {
    return http.performGet(url);
  },

  getProjectForAddInfra: (params) => {
    return http.performPost(CONSTANTS.API_URLS.projectList, params);
  },

  fetchBillGroup: (url, requestParams) => {
    return http.performPost(url, requestParams);
  },

  createSofs: (url) => {
    return http.performGet(url);
  }
};

export default ShoppingCartService;
