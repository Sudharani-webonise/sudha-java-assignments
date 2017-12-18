import { Link } from 'react-router';
import CONSTANTS from '../../../constants/app-constant';
import http from '../../../mixins/restutils';
import SSOService from '../../../services/sso';
import Session from '../../../mixins/sessionUtils';
import Utility from '../../../mixins/basicUtils';
import Loader from 'react-loader';
import LoaderOptions from '../../../mixins/loader';
import UI from '../../../mixins/ui';
import User from '../../../services/user';
import { Modal, ModalHeader, ModalTitle, ModalClose, ModalBody, ModalFooter } from 'react-modal-bootstrap';

var SsoClassicMnp = createReactClass({

    mixins: [Session, UI],
    contextTypes: {
        router: React.PropTypes.object
    },

    getInitialState() {
        return {
            result: '',
            moduleMnpUrl: '',
            loaded: false,
            modalState: false,
        }
    },

    componentWillMount() {
        this.getSsoMnpData();
    },

    getSsoMnpData() {
        SSOService.fetchSsoMnpPage(CONSTANTS.API_URLS.mnpSso)
            .then((result) => {
                this.state.moduleMnpUrl = result && result.responseText;
                this.setState(this.state);
            })
            .catch((error) => {
                if (error && error.status === CONSTANTS.ERROR_CODE.unauthorized) {
                    UI.notifyError(CONSTANTS.UI_MESSAGES.ssoInvalidUser);
                    this.context.router.push('/error/403');
                }
                else if (error && error.status === CONSTANTS.ERROR_CODE.forbidden) {
                    this.state.modalState = true
                    this.state.routePath =User.getUserInfo('isInternalUser') ? 'splash-screen-internal' : 'splash-screen'
                }
                else {
                    this.state.moduleMnpUrl = error && error.responseText;
                }
                this.setState(this.state);
            });
    },

    hideModal() {
        this.state.modalState = false;
        this.setState(this.state)
        this.context.router.push(this.state.routePath);
    },

    render() {
        return (
            <div>
                <div>
                    <section className="breadCrumbSection dashboardBreadCrumb">
                        <Modal isOpen={this.state.modalState} onRequestHide={this.hideModal}>
                            <ModalHeader>
                                <ModalClose onClick={this.hideModal} />
                                <ModalTitle>Access Denied</ModalTitle>
                            </ModalHeader>
                            <ModalBody>
                                <p className="warningMsg">{CONSTANTS.UI_MESSAGES.ssoInvalidUser}</p>
                            </ModalBody>
                            <ModalFooter>
                                <input type="submit" className="btn btn-primary pull-right" value="OK" onClick={this.hideModal} />
                            </ModalFooter>
                        </Modal>
                        <div className="container">
                            <div className="row">
                                <div className="col-xs-10 col-sm-10 col-md-10">
                                    <ol className="breadcrumb breadCrumbCustom clearfix">
                                        <li className="hide"><Link to={User.getSplashScreenURL()}>Back to Home</Link></li>
                                        <li className="ticketListBread active hide"></li>
                                    </ol>
                                </div>
                                <div className="col-xs-2 col-sm-2 col-md-2">
                                </div>
                            </div>
                        </div>
                    </section>
                </div>
                {
                    this.state.moduleMnpUrl.search('<') < 0 ? <iframe src={this.state.moduleMnpUrl} name='iframeSSOMnpFORM' id='iframeSSOMnpFORM' className='ssoIframeContainer'></iframe> : (null)
                }
            </div>
        )
    }
});

export default SsoClassicMnp;
