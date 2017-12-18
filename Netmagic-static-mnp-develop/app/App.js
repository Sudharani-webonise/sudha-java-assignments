import LoginRoutes from './javascript/modules/login/routes'
import Session from './javascript/mixins/sessionUtils';
import UI from './javascript/mixins/ui';

var App = React.createClass({
  getInitialState() {
    return {
      isLoggedIn: Session.get('user')
    }
  },

  render() {
    return <div>{this.props.children}</div>
  }
});

export default App;
