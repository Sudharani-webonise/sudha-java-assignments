import { Link } from 'react-router';
import Session from '../../../mixins/sessionUtils';

var EmptyCart =  createReactClass({

  render (){
    let user = Session.get('user')
    let customersData =  user && user.loginUserName || "";
    return (
      <div className="emptyCartSection">
        <div className="emptyCartDetails">
          <i className="emptyIcon"></i>
          <p className="emptyCartMsg">Hi {customersData}, it looks like you have not purchased anything yet.</p>
          <p className="chooseMessage">Choose from our wide range of cloud and dedicated servers to get started.</p>
          <input type="button" value="Add Infra" onClick={this.props.setAddInfraEnabled} className="darkPinkBtn" />
        </div>
      </div>
    )
  }
});

export default EmptyCart;
