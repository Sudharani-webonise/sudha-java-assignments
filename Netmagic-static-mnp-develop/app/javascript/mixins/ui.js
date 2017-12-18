import CONSTANTS from '../constants/app-constant';
import Session from './sessionUtils';

var _setAlertBox = (alertBox, className, alert, timeOutDelay) => {
  alertBox.addClass(className);
  alertBox.css('display', 'block');
  setTimeout(() => {
    alertBox.css('display', 'none');
    alertBox.removeClass(className);
  }, timeOutDelay || alert.displayTime);
}

var UI = {
  getBodyClassName: (isLoggedIn) => isLoggedIn ? '' : 'blackTheme paddingBottom174',

  setBodyClassName: (addClassName) => $('body').attr('class', addClassName),

  removeBodyClassName: () => $('body').attr('class', ''),

  getElement: function(refValue) {
   return this && this.refs && this.refs[refValue];
  },

  toggleActiveListItem: (target) => {
    target = $(target);
    target.closest('ul').find('.active').removeClass('active');
    target.addClass('active');
  },

  validateForm: (formObject) => {
    if (!formObject.jquery) {
      formObject = $(formObject);
    }
    var requiredElements = formObject.find('.mandatory');
    requiredElements.on('input change focusout click', function(event) {
      var allValid = true;
      requiredElements.each(function () {
        var element = $(this);
        if (element.hasClass('customSelectList')) {
          allValid = allValid && element[0].dataset && element[0].dataset.selectedItem;
        } else {
          allValid = allValid && element.val();
        }
      });
      var submit = formObject.find(':submit');

      submit.prop('disabled', !allValid);
      submit[allValid ? 'removeClass' : 'addClass']('disableLinkBtn');
    });
  },

  notify: (type, message) => {
    var alertBox = $('.customAlertMsg');
    var alert = CONSTANTS.ALERT;
    var className = alert.typeClass[type] || alert.typeClass.info;
    alertBox.find('.message').prop('textContent', message);
    alertBox.addClass(className);
    alertBox.css('display', 'block');
    setTimeout(() => {
      alertBox.css('display', 'none');
      alertBox.removeClass(className);

    }, alert.displayTime);
  },

  notifyError:(message, timeOutDelay) => {
    var alertBox = $('.customAlertMsg');
    var alert = CONSTANTS.ALERT;
    var className = alert.typeClass.error || alert.typeClass.info;
    alertBox.find('.message').prop('textContent', message);
    _setAlertBox(alertBox, className, alert, timeOutDelay);
  },

  notifySuccess:(message, timeOutDelay) => {
    var alertBox = $('.customAlertMsg');
    var alert = CONSTANTS.ALERT;
    var className = alert.typeClass.success || alert.typeClass.info;
    alertBox.find('.message').prop('textContent', message);
    _setAlertBox(alertBox, className, alert, timeOutDelay);
  },

  classSet: (classObject) => {
    return _.isObject(classObject) &&
    _.chain(classObject).map(function(flag, className) {
      return flag && className ? className : '';
    }).compact().join(' ').value() || '';
  },

  showNumber: (number) => {
    return _.isNumber(parseInt(number))   && !_.isNaN(parseInt(number)) ? number : CONSTANTS.NOT_AVAILABLE;
  },

  makeRandomColor: () => {
    const colors = CONSTANTS.TAG_COLORS;
    return colors[Math.floor(Math.random() * colors.length)] || _.first(colors);
  },

  checkIfAlphabets: (event) => {
    const regex = CONSTANTS.REGEX.NAME;
    if(!regex.test(event.key)) {
      event.preventDefault();
    }
  },

  navigationClass:() => {
    let mainCustomer = Session.get('mainCustomer');
    return UI.classSet({
      'breadcrumb': true,
      'breadCrumbCustom': true,
      'clearfix': true,
      'hide': _.isEmpty(mainCustomer)
    })
  }
};

export default UI;