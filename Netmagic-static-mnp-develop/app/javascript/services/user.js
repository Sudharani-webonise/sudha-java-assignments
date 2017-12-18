import http from '../mixins/restutils';
import UI from '../mixins/ui';
import CONSTANTS from '../constants/app-constant';
import Session from '../mixins/sessionUtils';
import Utility from '../mixins/basicUtils';
import Cookies from '../mixins/cookies';
import Promise from 'bluebird';

var _checkAccess = (permissionCode) => _.findWhere(Session.get('userPermissions'), {functionCode: permissionCode});

const UNAUTHORIZED_PAGE = '/error/403';

const User = {

  postData: (apiURL, requestBody) => http.performPost(apiURL, requestBody),

  getPermissions: function(queryParams, successCallback) {
    return new Promise(function resolver(resolve, reject) {
      if(User.getUserInfo('isInternalUser')) {
        Session.set('userPermissions', Session.get('userPermissions'));
        let result = Session.get('userPermissions')
        resolve(result);
      } else {
        http.performGet(CONSTANTS.API_URLS.user.permissions, queryParams)
          .then((result) => {
            var permissions = result && _.chain(result.myNmUserModuleAccessBeans)
              .pluck('myNmUserFunctionAccessBeans')
              .flatten()
              .compact()
              .value();
              Session.set('userPermissions', permissions);
              resolve(result);
          })
          .catch((error) => reject(error));
      }
    });
  },

  canAccess: function(permissionCode) {
    return _checkAccess(permissionCode);
  },

  login: (credentials) => {
    Session.set('updateTimestamp', _.now());
    return http.performPost(CONSTANTS.API_URLS.login, credentials);
  },

  internalLogin: (credentials) => {
    Session.set('updateTimestamp', _.now());
    return http.performPost(CONSTANTS.API_URLS.internalUser.login, credentials);
  },

  isLoggedIn: () => Boolean(Session.get('user') && Cookies.get('auth-token')),

  redirectIfUnauthorized: function(permissionCode) {
    if(!_checkAccess(permissionCode)) {
      var push = Utility.getVal(this, 'context.router.push');
      _.isFunction(push) && push("/dashboard");
    }
  },

  getCustomerParams: function() {
    var mainCustomer = Session.get('mainCustomer');
    var associatedCustomer = Session.get('associatedCustomer');
    var project = Session.get('project');
    var newUserDetails = Session.get('userDetails');
    var mainCustomerId = mainCustomer && mainCustomer.customerId || mainCustomer
      && mainCustomer.cvCrmId || newUserDetails && newUserDetails.kacid;
    var mainCustomerSugarId = mainCustomer && mainCustomer.customerSugarId || newUserDetails && newUserDetails.kacid || mainCustomer && mainCustomer.cvSugarId;
    var mainCustomerCombinationId = mainCustomer && mainCustomer.combinationId
    var params = {
      customerId: mainCustomerId,
      customerSugarId: mainCustomerSugarId,
      customerCombinationId : mainCustomerCombinationId,
      customerName : mainCustomer && mainCustomer.customerName || mainCustomer && mainCustomer.cvName || ''
    };
    params.assoCustomerId = associatedCustomer && associatedCustomer.customerId ||
      associatedCustomer && associatedCustomer.cvCrmId || mainCustomerId ||
      newUserDetails && newUserDetails.kacid;
    params.assoCustomerSugarId = associatedCustomer &&
      associatedCustomer.customerSugarId || newUserDetails &&
      newUserDetails.kacid || associatedCustomer &&
      associatedCustomer.cvSugarId || mainCustomerSugarId;
    params.assoCustomerCombinationId = associatedCustomer ? associatedCustomer.combinationId : mainCustomerCombinationId;
    params.associteCustomerName = User.getAssociateCustomer('customerName') ||
      User.getMainCustomer('customerName') || User.getAssociateCustomer('cvName') || '';

    var project = Session.get('project');
    params.projectId = User.getProject('projectId') || '';
    params.projectName = User.getProject('projectName') || '';
    params.sugarProjectId = User.getProject('sugarProjectId') || User.getProject('projectSugarId') || '';
    return params;
  },

  resetPassword: function(url, params) {
    return http.performPost(url, params);
  },

  getMainCustomer: (key) => {
    var mainCustomer = Session.get('mainCustomer');
    return mainCustomer && mainCustomer[key];
  },

  getAssociateCustomer: (key) => {
    var associatedCustomer = Session.get('associatedCustomer');
    return associatedCustomer && associatedCustomer[key];
  },

  getProject: (key) => {
    var project = Session.get('project');
    return project && project[key];
  },

  getUserInfo: (key) => {
    var user = Session.get('user');
    return user && user[key];
  },

  getSplashScreenURL() {
    return User.getUserInfo('isInternalUser') ? 'splash-screen-internal'
      : 'splash-screen';
  },

  fetchNewUserDetails: (url) => {
    return http.performGet(url);
  },

  makeSuperAdmin: (url, requestBody) => {
    return http.performPost(url, requestBody);
  },

  emailVerify: (url) => {
    return http.performGet(url);
  },

  verifyingOTP: (url, params) => {
    return http.performGet(url, params);
  },

  regenerateOTP: (url, params) => {
    return http.performGet(url, params)
  },

  verifyAndFetchAttachmentsSignup: (files, existingFiles) => {
    var updatedFiles = _.union(existingFiles, files);
    var messages = CONSTANTS.UI_MESSAGES;
    var error = '';
    if(Utility.isFileTypeNotAllowedSignUp(files)) {
      error = messages.kycFileTypeAllowed;
    } else if(Utility.isAnyFileTooLargeKYC(files)) {
      error = messages.kycFileTooLarge;
    } else if(updatedFiles.length > CONSTANTS.MAX_WORKLOG_ATTACHMENTS) {
      error = messages.maxAttachments;
    }
    return error && !UI.notifyError( error) ? existingFiles : updatedFiles;
  },
};

export default User;
