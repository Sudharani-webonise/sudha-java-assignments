import { Link } from 'react-router';
import User from '../../services/user';

var Footer = createReactClass({
    render(){
        return User.isLoggedIn() ? (<FooterV1 hideChicklet={this.props.hideChicklet}/>) : null;
    }
});

// NOTE: Footer v1 is used for after login views
var FooterV1 = createReactClass({
    downloadEscalationMatrix() {
        window.open('/theme/default/pdf/NM_PM_escalationMatrix.pdf', '_blank');
    },

    render(){
        return(
            <div className={this.props.isFooter ? "footerMainWrap hide" : "footerMainWrap"}>
                 <footer>
                  <p>Welcome to the new myNetmagic Portal. This is currently a Beta release, incase you wish to add new infra or login to the older site please click on <a href="https://classicmnp.netmagicsolutions.com/MyNetmagic" target="_blank" className="pinkText">classic-mnp</a></p>
                  <a className="pinkText" href="#" onClick={this.downloadEscalationMatrix}>Download Escalation Matrix</a>
                </footer>
                <Link to="/sso-mnp" className="classicmnpLink">classic mnp/add infra</Link>
            </div>
        )
    }
});

export default Footer;
