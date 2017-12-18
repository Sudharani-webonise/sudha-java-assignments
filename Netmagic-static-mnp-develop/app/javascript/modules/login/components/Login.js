import LoginForm from './LoginForm';
import { Link } from 'react-router';

var Login = createReactClass({
    render() {
        return (
            <div className="paddingBottom170">
                <div className="container paddingZero">
                    <div className="row">
                        <div className="col-sm-12 col-md-12 loginColumn">
                            <div className="loginFlowWrapper">
                                <h1>Welcome to myNetmagic Beta</h1>
                                <div className="col-md-6 text-right padtop">
                                    <div className="col-xs-12 pad10">
                                        <Link className="dis_login pull-right">Login for Existing User</Link>
                                    </div>
                                    <div className="col-xs-12 pad10">
                                        <Link to="/sign-up" className="btn_signup pull-right">Sign Up for New User</Link>
                                    </div>
                                </div>
                                <div className="col-md-6 borderLeft pad80">
                                    <LoginForm />
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
});

export default Login;