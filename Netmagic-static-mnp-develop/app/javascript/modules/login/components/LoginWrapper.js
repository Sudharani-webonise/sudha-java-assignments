import Session from '../../../mixins/sessionUtils';
import UI from '../../../mixins/ui';
import Header from '../../commons/Header';
import Alert from '../../commons/Alert';
import { useRouterHistory, IndexRoute, IndexRedirect, Router, Route } from 'react-router';

var LoginWrapper = createReactClass({
  addClass() {
    var classSet = 'blackTheme loginBackground';
    if(this.props.location.pathname === '/sign-up') {
      classSet = 'blackTheme';
    }
    return classSet;
  },

  render() {
    return (
      <div className={this.addClass()} >
        <Header routeName={this.props.location.pathname}/>
        <Alert />
        {this.props.children}
      </div>
    )
  }
});

export default LoginWrapper;
