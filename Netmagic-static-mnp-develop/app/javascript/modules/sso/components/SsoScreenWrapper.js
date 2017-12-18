import UI from '../../../mixins/ui';
import Header from '../../commons/Header';
import Session from '../../../mixins/sessionUtils';
import Alert from '../../commons/Alert';
import Footer from '../../commons/Footer';

var SsoScreenWrapper = createReactClass({
  render() {
    UI.removeBodyClassName();
    return (
      <div>
        <Header isLoggedIn= {Session.get('user')} cloudPortal={true}/>
        <Alert />
        {this.props.children}
      </div>
    );
  }
});

export default SsoScreenWrapper;
