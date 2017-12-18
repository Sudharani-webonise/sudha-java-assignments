import CONSTANTS from '../../constants/app-constant';
import Alert from './Alert';
import Header from './Header';
import Footer from './Footer';
import UI from '../../mixins/ui';
import Session from '../../mixins/sessionUtils';

var ChildComponent = createReactClass({
  mixins: [UI, Session],

  render() {
		var childrenWithProps = React.Children.map(this.props.children, (child) => {
			return React.cloneElement(child, {
				setParentLoadState: this.props.setParentLoadState
			})
		});
		return(<div>
			<Header ref="header" isLoggedIn= {this.get('user')}
			assocCustomer={this.get('associatedCustomer')} project={this.get('project')}
			setParentLoadState = {this.props.setParentLoadState} assocCustomers={this.props.assocCustomers}
			isShoppingCart={this.props.isShoppingCart}/>
			<div className="pageData">
				<Alert />
				{this.props.reload ? childrenWithProps :
				(<span className="flashMessage">{CONSTANTS.UI_MESSAGES.fetchingPermissions}
				</span>)}
			</div>
			<Footer isFooter={this.props.isFooter}/>
		</div>
		);
  },
});

export default ChildComponent;
