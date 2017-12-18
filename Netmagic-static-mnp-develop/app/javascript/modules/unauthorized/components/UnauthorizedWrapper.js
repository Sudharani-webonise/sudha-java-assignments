import UI from '../../../mixins/ui';
import Header from '../../commons/Header';
import ChildComponent from '../../commons/ChildComponent';
import Alert from '../../commons/Alert';
import Footer from '../../commons/Footer';
import CONSTANTS from '../../../constants/app-constant';
import Session from '../../../mixins/sessionUtils';

var DashboardWrapper = createReactClass({
  mixins: [UI, Session],

  getInitialState() {
    return {reload: true};
  },

  setParentLoadState(set) {
    this.setState({ reload: Boolean(set) });
  },

  render() {
    this.removeBodyClassName();
    return <ChildComponent reload={this.state.reload} setParentLoadState={this.setParentLoadState} children={this.props.children} assocCustomers={this.get('customerBeans')}/>;
  },
});

export default DashboardWrapper;
