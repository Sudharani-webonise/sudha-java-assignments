import InternalLoginForm from './InternalLoginForm';

var Login = createReactClass({
    render() {
        return (
            <div className="paddingBottom170">
                <div className="container paddingZero">
                    <div className="row">
                        <div className="col-sm-12 col-md-12 loginColumn">
                            <div className="loginFlowWrapper">
                                <h1>Welcome to myNetmagic Beta</h1>
                                <InternalLoginForm />
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
});

export default Login;
