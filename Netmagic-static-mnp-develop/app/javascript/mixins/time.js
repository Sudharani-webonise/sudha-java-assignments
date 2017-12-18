import CONSTANTS from '../constants/app-constant';
import moment from 'moment';

var Time = {
  format: (date, format, inputFormat) => {
    var date = moment(date, inputFormat);
    return date.isValid() ? date.format(format) : CONSTANTS.NOT_AVAILABLE;
  },

  duration: (timeString) => timeString && timeString.split(', ')[0]
};

export default Time;
