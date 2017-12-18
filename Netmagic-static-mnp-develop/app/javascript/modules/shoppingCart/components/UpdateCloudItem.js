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
import { Modal, ModalHeader, ModalTitle, ModalClose, ModalBody, ModalFooter} from 'react-modal-bootstrap';

var UpdateCloudItem = createReactClass({

    mixins: [Session, UI],
    contextTypes: {
        router: React.PropTypes.object
    },

    getInitialState() {
        var moduleName = Utility.getVal(this.props, 'location.query.moduleName');
        let userObj = User.getCustomerParams();
        var moduleCategory = '';
        var status, combinationId;
        var email = '';
        var project = Session.get('project');
        combinationId = project && project.combinationId || userObj.assoCustomerId || userObj.customerId || '';
        return {
            email: email,
            moduleCategory: moduleCategory,
            status:status,
            result: '',
            moduleUrl: '',
            moduleName: moduleName,
            mainCustomerId: userObj.customerId,
            associatedCustomerId: userObj.assoCustomerId,
            combinationId: combinationId,
            projectId: User.getProject('projectId') || '',
            loaded: false,
            modalState: false,
            routePath: "/dashboard",
            productCode: '',
            zoneName: ''
        }
    },

    componentWillMount() {
        this.getSsoData();
    },

    componentDidUpdate() {
        this.submitSSOForm()
    },

    getSsoData() {
        SSOService.fetchSsoPage(CONSTANTS.API_URLS.sso)
            .then((result) => {
                this.state.moduleUrl = result && result.responseText;
                this.setState(this.state);
            })
            .catch((error) => {
                if (error && error.status === CONSTANTS.ERROR_CODE.unauthorized) {
                    this.state.modalState = true
                    this.state.status = false
                    this.state.routePath = "/dashboard";
                }  else {
                    this.state.moduleUrl = error && error.responseText;
                    this.state.status = true
                }
                this.setState(this.state);
            });
    },

    hideModal() {
        this.state.modalState = false;
        this.setState(this.state)
        this.context.router.push(this.state.routePath);
    },

    submitSSOForm() {
        $(this.refs.ssoForm).attr('action', this.state.moduleUrl)
        $(this.refs.ssoForm).submit()
    },

    render() {
        let params = Session.get('CloudParams');
        return (
            <div>
                <Modal isOpen={this.state.modalState} onRequestHide={this.hideModal}>
                    <ModalHeader>
                        <ModalClose onClick={this.hideModal}/>
                        <ModalTitle>Access Denied</ModalTitle>
                    </ModalHeader>
                    <ModalBody>
                        <p className="warningMsg">{CONSTANTS.UI_MESSAGES.ssoInvalidUser}</p>
                    </ModalBody>
                    <ModalFooter>
                        <input type="submit" className="btn btn-primary pull-right" value="OK" onClick={this.hideModal}/>
                    </ModalFooter>
                </Modal>
                <div>
                    <section className="breadCrumbSection dashboardBreadCrumb">
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

                <div>
                <iframe name='iframeSSOFORM' id='iframeSSOFORM' className='ssoIframeContainer'></iframe>
                <form hide method='POST' target='iframeSSOFORM' ref='ssoForm'>
                    <input type='hidden' ref='source' name='source'  value="O"/>
                    <input type='hidden' ref='token_id' name='token_id'  value={params.token_id} />
                    <input type='hidden' ref='cv_crm_id' name='cv_crm_id'  value={this.state.associatedCustomerId} />
                    <input type='hidden' ref='bill_to_cv_crm_id' name='bill_to_cv_crm_id'  value={this.state.mainCustomerId} />
                    <input type='hidden' ref='combinationId' name='combinationId' value={this.state.combinationId}/>
                    <input type='hidden' ref='projectId' name='projectId' value={this.state.projectId} />
                    <input type='hidden' ref='product_code' name='product_code' value={params.product_code} />
                    <input type='hidden' ref='zone_name' name='zone_name' value={params.zone_name} />
                </form></div>
            </div>
        )
    }
});

export default UpdateCloudItem;
