import CONSTANTS from '../constants/app-constant';
import http from '../mixins/restutils';
import UI from '../mixins/ui';

const Role = {
    fetchRoleDetails: function (customerId) {
        var url = http.buildRestUrl(CONSTANTS.API_URLS.role.details,
            { customerId: customerId });
        return http.performGet(url);
    },

    addRole: function (params) {
        return http.performPost(CONSTANTS.API_URLS.role.add, params);
    }
};

export default Role;
