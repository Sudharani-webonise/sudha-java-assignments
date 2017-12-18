var _getKeyRegex = (key) => key && new RegExp(key + '=([\\w\\W]+)');

var _getPastExpiryDate = () => '=;expires=' + new Date().setTime(-1);

const Cookies = {
  set: (key, value) => document.cookie = key + '=' + value,

  get: (key) =>  {
    var value = _.find(document.cookie.split(';'), (pair) => {
      return pair.match(_getKeyRegex(key));
    });
    return value && value.split('=').length && value.split('=')[1];
  },

  remove: (key) => document.cookie = key + _getPastExpiryDate()
};

export default Cookies;
