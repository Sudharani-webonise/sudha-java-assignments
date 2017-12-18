var Session = {
  set: (key, value) => localStorage.setItem(key, JSON.stringify(value)),

  get: (key) =>  {
    var target = localStorage.getItem(key);
    return target !== 'undefined' && $.parseJSON(target);
  },

  remove: (key) => localStorage.removeItem(key),

  removeItems: (keys) => {
    for (var index in keys) {
      localStorage.removeItem(keys[index]);
    }
  },

  removeAll: (keys) => localStorage.clear(),

  doGetAuthToken: (key) => {
    let user = JSON.parse(localStorage.getItem(key));
    return user && user.userId ? true : false;
  },

  authenticate: (key) => {
    let user = JSON.parse(localStorage.getItem(key));
    if (user) {
      if (user.userId) {
        return true;
      } else {
        return false;
      }
    } else {
      return false;
    }
  }
};

export default Session;
