import UI from '../../../mixins/ui';
import Header from '../../commons/Header';
import Session from '../../../mixins/sessionUtils';
import Alert from '../../commons/Alert';
import Footer from '../../commons/Footer';

var InternalUserWrapper = createReactClass({
    render() {
        UI.removeBodyClassName();
        return (
            <div>
                <Header isLoggedIn= {Session.get('user') } hideChicklet={true}/>
                <div className="pageData">
                    <Alert />
                    {this.props.children}
                </div>
                <Footer/>
            </div>
        );
    }
});

export default InternalUserWrapper;
