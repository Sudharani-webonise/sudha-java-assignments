import CONSTANTS from '../constants/app-constant';
import Promise from 'bluebird';

var _getInnerValue = (propLevels, object) => {
  var isArray = /^\[(\d+)\]$/;
  propLevels.forEach(function(level) {
    object = object &&
    object[isArray.test(level) ? level.replace(isArray, '$1') : level];
  });
  return object;
};

var _hasSameElements = (array, field) => {
  var elements = _.chain(array).pluck(field);
  if(elements.first().isObject().value()) {
    elements = elements.pluck('value');
  }
  return elements
    .uniq()
    .value().length === 1;
};

var Utility = {
  /*
   * fetches the inner chained values of large objects
   * with undefined check
   * eg: to access a.b.c[0].d, call it as:
   * getVal(a, 'b.c.[0].d');
   *
   *@param object : target Object
   *@param: prop: String representing chained property to access
   *@return: [Object|undefined]
  */
  getVal: (object, prop) => {
    var propLevels = prop && prop.split && prop.split('.');
    return propLevels && _getInnerValue(propLevels, object);
  },

  getObject: (key, value) => {
    var obj = {};
    obj[key] = value;
    return obj;
  },

  interpretNewLine: (text) => {
        var newText = text.split('\n').join('<br/>');
        return newText;
  },

  toObject: (value) => {
    if(typeof value === 'string') {
      try {
        value = JSON.parse(value);
      } catch(ex) {
        this.notify('Unexpected exception occurred in parsing result');
      }
    }
    return value;
  },

  getSortedList: (list, isDescending, fieldName) => {
    list = _.sortBy(list, function (item) {
      var itemProperty = item[fieldName];
      return _.isObject(itemProperty) ? itemProperty.value : itemProperty;
    });
    return isDescending ? list.reverse() : list;
  },

  getFormattedAmt: (currencyId, val) => {
    let currency = CONSTANTS.CURRENCY;
    let amount = val.toLocaleString(currency.US.lang);
    if (currencyId === currency.IN.id) {
      amount = val.toLocaleString(currency.IN.lang);
    }
    return amount;
  },

  getNestedValue: (obj, keyName) => {
    var result;
    _.each(obj, function(val, key, object) {
    if(val && val[keyName]) {result = val[keyName];}
    });
    return result;
  },

  readFileInBytes: (file) => {
    return new Promise(function resolver(resolve, reject) {
      var reader = new FileReader();
      reader.onload = () => resolve({ file: file, reader: reader });
      if(file.size > CONSTANTS.MAX_FILE_SIZE) {
        reject(CONSTANTS.UI_MESSAGES.fileTooLarge);
      }
      reader.readAsDataURL(file);
    });
  },

  isFileTypeNotAllowed: (files) => {
    var allowedTypes = CONSTANTS.DROPZONE_FILE_TYPES;
    return _.some(files, (file) => {
      return !_.contains(allowedTypes, file.name.split('.').pop())
    });
  },

  isFileTypeNotAllowedSignUp: (files) => {
    var allowedTypes = CONSTANTS.KYC_FILE_TYPES;
    return _.some(files, (file) => {
      return !_.contains(allowedTypes, file.name.split('.').pop())
    });
  },

  isAnyFileTooLarge: (files) => {
    return _.some(files, (file) => file.size > CONSTANTS.MAX_FILE_SIZE);
  },

  isAnyFileTooLargeKYC: (files) => {
    return _.some(files, (file) => file.size > CONSTANTS.KYC_FILE_SIZE);
  },

  clone: (obj) => JSON.parse(JSON.stringify(obj)),

  checkEmail: (input) => input && CONSTANTS.REGEX.EMAIL.test(input.trim()),

  checkPhone: (input) => input && CONSTANTS.REGEX.PHONE.test(input.replace(' ', ''))
};


export default Utility;
