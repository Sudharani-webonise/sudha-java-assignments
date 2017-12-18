import { Link } from 'react-router';

var RequestMailSent = createReactClass({
  render() {
    return (
      <div className="requestMailSentPopUp">
        <div className="wrappPop">
          <h5 className="balckText">Request for 'Business Change Request' has sent via email.</h5>
          <a href="javascript:void(0);" className="darkPinkBtn rippleBtn okBtn">Ok</a>
        </div>
      </div>
    )
  }
});

export default RequestMailSent;
