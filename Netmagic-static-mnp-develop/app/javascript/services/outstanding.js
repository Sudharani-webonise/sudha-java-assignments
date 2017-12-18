import http from '../mixins/restutils';
import UI from '../mixins/ui';
import Utility from '../mixins/basicUtils';
import CONSTANTS from '../constants/app-constant';
import Session from '../mixins/sessionUtils';
import User from './user';

const Invoice = {
    typeCount: 2,
    getData: function (url, businessUnit) {
        let userObj = User.getCustomerParams();
        var mainCustomerId = userObj.customerId;
        var project = Session.get('project');
        var requestParams = {
            OutstandingAPIinput: {
                billToCustomer: mainCustomerId,
                businessUnit: businessUnit || CONSTANTS.DUMMY_BUSINESS_UNIT,
                suppportToCustomers: {
                    suppportToCustomerId: Session.get('activeCustomerId'),
                    projectList: {
                        Project: 'ALL'
                    }
                }
            }
        };
        return http.performPost(url, requestParams);
    },

    makePaymentRequest: (url, request) => http.performPost(url, request),

    getBusinessUnits: () => http.performGet(CONSTANTS.API_URLS.outstanding.businessUnit),

    getCreditLimits: () => {
        var url = http.buildRestUrl(CONSTANTS.API_URLS.outstanding.availableCreditNotes, { customerId: User.getMainCustomer('customerId') });
        return http.performGet(url);
    }

};

export default Invoice;
