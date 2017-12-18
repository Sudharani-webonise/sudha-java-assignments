import { Router, Route } from 'react-router';
import Session from '../../../mixins/sessionUtils';

var Congrats = createReactClass({

  handleSubmit(event) {
    event.preventDefault();
    Session.set("isLoggedIn",true);
    this.context.router.transitionTo('dashboard');
  },

  render() {
    return (
      <div className="paymentConfirmMsg singupCongrats">
        <span className="icon-tickMark"></span>
        <h3>Congratulations</h3>
        <p>Your Registration has been completed successfully.</p>
        <button className="darkPinkBtn" onClick={this.handleSubmit}>Continue to Dashboard</button>
      </div>
    )
  }
});

export default Congrats;
