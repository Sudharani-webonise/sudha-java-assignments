import CONSTANTS from '../constants/app-constant';
import http from '../mixins/restutils';

const SSO = {
    fetchSsoPage: () => {
        return http.performGet(CONSTANTS.API_URLS.sso);
    },

    fetchSsoMnpPage: () => {
        return http.performGet(CONSTANTS.API_URLS.mnpSso);
    },

    getCombinationId: (url) => {
        return http.performGet(url);
    }
};

export default SSO;
