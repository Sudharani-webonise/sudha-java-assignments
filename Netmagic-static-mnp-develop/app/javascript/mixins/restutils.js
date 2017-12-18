import UI from './ui';
import CONSTANTS from '../constants/app-constant';
import Session from './sessionUtils';
import Authorization from '../services/authorization';
import Router from 'react-router';
import { browserHistory } from 'react-router';
import Promise from 'bluebird';

var http = (function () {

  var _defaultHandler = (error) => UI.notifyError(error.statusText)


  var checkSession = (function () {
     var currentTime = _.now();
    if (!Session.get('updateTimestamp')) {
        Session.set('updateTimestamp', currentTime);
    }
    else
    {
        var shouldBeLessThan = parseInt(Session.get('updateTimestamp')) + CONSTANTS.TIME.THREE_HOURS_IN_SECONDS;
        if(currentTime < shouldBeLessThan)
        {
            Session.set('updateTimestamp', currentTime);
        }
        else
        {
            Session.removeAll();
            UI.notify( 'error', CONSTANTS.UI_MESSAGES.sessionExpired);
            location.hash = "#/MyNetmagic";
        }
    }
  });

  var _requestObject = (url, body, successHandler, errorHandler) => {
    checkSession();
    return {
      beforeSend: (request) => {
        request.setRequestHeader('x-auth-token', Authorization.getToken());
      },
      url: CONSTANTS.APP.apiBaseUrl + url,
      header: {  },
      data: JSON.stringify(body || {}),
      dataType: 'json',
      contentType: 'application/json',
      success: (result, textStatus, request) => {
        successHandler && successHandler(result, textStatus, request);
        $('.loaderOverlay').hide();
      },
      error: (error) => {
        errorHandler ? errorHandler(error.statusText) : _defaultHandler(error);
      },
    }
  }


  return {
    performPost: (url, requestBody) => {
      return new Promise(function resolver(resolve, reject) {
        var request = _.extend(_requestObject(url, requestBody, resolve, reject), {
          type: 'POST',
          success: (result, text, response) => {
            resolve && resolve(_.extend(result, {
              text: text,
              response: response
            }));
          },
        });
        $.ajax(request);
      });
    },

    performPut: (url, requestBody, customOptions) => {
      return new Promise(function resolver(resolve, reject) {
        $('.loaderOverlay').show();
        $.ajax(_.extend(_requestObject(url, requestBody, resolve, reject),
          { type: 'PUT' }, customOptions || {}));
      });
    },

    performGet: (url, queryParams, customOptions) => {
      checkSession();
      return new Promise(function resolver(resolve, reject) {
        $.ajax(_.extend({
          type: 'GET',
          beforeSend: (request) => {
            request.setRequestHeader('x-auth-token', Authorization.getToken());
          },
          data: queryParams || {},
          url: CONSTANTS.APP.apiBaseUrl + url,
          async: true,
          crossDomain: true,
          contentType: 'application/json',
          success: (result) => resolve(result),
          error: (err) => reject ? reject(err) : _defaultHandler(err)
        }, customOptions || {}));
      });
    },

    performDelete: (url, requestBody) => {
      return new Promise(function resolver(resolve, reject) {
        var request = _.extend(_requestObject(url, requestBody, resolve, reject), {
          type: 'DELETE',
          success: (result, text, response) => {
            resolve && resolve(_.extend(result, {
              text: text,
              response: response
            }));
          },
        });
        $.ajax(request);
      });
    },

    buildUrl: (string, properties) => {
      return string.replace(/{([^{}]*)}/g, (subString, index) => {
        var current = properties[index];
        return /string|number/.test(typeof current) ? current : subString;
      });
    },

    appendUrl: (string, properties) => {
      var url = string;
      for (var key in properties) {
        if (properties.hasOwnProperty(key)) {
          url = url + "&" + key + "=" + (properties[key] || '');
        }
      }
      return url
    },

    buildRestUrl: (string, properties) => {
      var url = string;
      var restAPIRegex = CONSTANTS.REGEX.REST_API;
      if (restAPIRegex.test(url)) {
        url = string.replace(/{([^{}]*)}/g, (subString, index) => {
          var current = properties[index];
          return /string|number/.test(typeof current) ? current : subString;
        });
      } else {
        url = `/${url}/${properties[Object.keys(properties)[0]]}?`;
        delete properties[Object.keys(properties)[0]];
        for (var key in properties) {
          if (properties.hasOwnProperty(key)) {
            url = `${url}&${key}=${(properties[key] || '')}`;
          }
        }
      }
      return url;
    }
  };
})();

export default http;
