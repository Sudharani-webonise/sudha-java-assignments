import { Link } from 'react-router';

const MAX_ICONS = 3;

var ContactsWidget = createReactClass({

  showUserIcons(count) {
    return _.map(_.range(Math.min(count, MAX_ICONS)), (item, index) => {
      return (<div className="userProfilePic pull-left" key={index}>
        <i className="NMIcon-userGrey"></i>
        <img src="" alt="user image"/>
      </div>)
    });
  },

  showExtraDots(count) {
    //TODO: change the class of this icon to make background '..'
    return count > MAX_ICONS ? (<div className="userProfilePic pull-left">
      <span>..</span>
    </div>) : (null)
  },

  getContactType (data) {
    var contactTypes = null;
    if (_.isArray(data) && data.length) {
      contactTypes = _.map(data, (item, key) => {
        return (
          <Link to={`/contacts?typeName=${item.contactType}`} key={key} className="tcktTxtDetails clearfix">
            <p className="pull-left">{item.contactType}</p>
            <div className="contactTypeWrapper pull-right clearfix">
            {this.showUserIcons(item.contactTypeCount)}
            {this.showExtraDots(item.contactTypeCount)}
            <span className="contactTypeCount pull-right">
              {item.contactTypeCount}
            </span>
            </div>
          </Link>
        );
      });
    }
    return contactTypes;
  },

  render () {
    var profileImage = require('../../../../theme/default/images/user-sample-pic.png');
    return (
      this.props.data ? ( <div className="contactsWidget">
        <div className="widgetDataCount clearfix">
          <Link to='/contacts'>
            <p className="pull-left">
              {this.props.data.totalContactsCount || CONSTANTS.NOT_AVAILABLE}
            </p>
          </Link>
          <label className="widgetLabel"></label>
        </div>
        <div className="contactsCount clearfix">
          {this.getContactType(this.props.data.listOfContactsByTypeBean)}
        </div>
      </div>) : null
    )
  }
});

export default ContactsWidget;
