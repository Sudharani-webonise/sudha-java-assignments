import { Link } from 'react-router';
import CONSTANTS from '../../../constants/app-constant';
import Loader from 'react-loader';
import LoaderOptions from '../../../mixins/loader';
import Contact from '../../../services/contact';
import User from '../../../services/user';
import Utility from '../../../mixins/basicUtils';
import Session from '../../../mixins/sessionUtils';
import http from '../../../mixins/restutils';
import UI from '../../../mixins/ui';
import PaymentDone from './PaymentDone';
import shoppingCart from '../../../services/shoppingCart';
import Select from 'react-select';
import ProvisionSCItem from './ProvisionSCItem';
import SSOIframe from './SSOIframe';
import { Modal, ModalHeader, ModalTitle, ModalClose, ModalBody, ModalFooter } from 'react-modal-bootstrap';
import SSOService from '../../../services/sso';


var ProvisionSC = createReactClass({
    mixins: [User, Utility, shoppingCart, http],
    contextTypes: {
        router: React.PropTypes.object
    },

    getInitialState() {
        return {
            provision: [],
            loaded: false,
            formData: {},
            moduleUrl: '',
            result: '',
            isOpen: false
        }
    },

    componentDidMount() {
        this.getProvisionData();
    },

    getProvisionData() {
        var userData = User.getCustomerParams()
        var project = Session.get('project');
        var projectName = User.getProject('projectName') || '';
        var params = {
            billToCustomer: userData.customerId,
            supportToCustomer: userData.assoCustomerId,
            projectName: projectName
        }
        var url = CONSTANTS.API_URLS.provision.sofDetails;
        shoppingCart.getData(url, params)
            .then((result) => {
                this.setState({ loaded: true, provision: result })
            })
            .catch(function (error) {
                this.setState({loaded: true})
                UI.notifyError( error && error.statusText)
            });
    },

    renderProvisionItems() {
        var provisionData = (<div className="provisionListContainer">
                <ul>
                    <li>
                        <div className="upperList">
                            <span>No Provision Item Found</span>
                        </div>
                    </li>
                </ul>
            </div>)
        if(_.isArray(this.state.provision) && this.state.provision.length) {
            provisionData = _.map(this.state.provision, (provisionItem, index) => {
                return (
                    <ProvisionSCItem key={index}
                        item={provisionItem}
                        index={index + 1}
                        provisionNow={this.provisionNow}
                        getSsoDataURL={this.getSsoDataURL}
                        />
                )
            })
        }
        return provisionData;
    },

    getSsoDataURL(extraItem, item) {
        this.state.loaded = false;
        this.setState(this.state);
        var customersData = Session.get('user') &&
          Session.get('user').customerSplashPageDataBeans;
        SSOService.fetchSsoPage(CONSTANTS.API_URLS.provision.sso)
            .then((result) => {
                this.state.moduleUrl = result && result.responseText;
                this.provisionNow(extraItem,item);
                this.setState(this.state);
            })
            .catch((error) => {
                if (error && error.statusText.toLowerCase() === CONSTANTS.UI_MESSAGES.badGateway || error && error.status === CONSTANTS.ERROR_CODE.unauthorized) {
                    if (customersData && error) {
                        UI.notifyError( CONSTANTS.UI_MESSAGES.ssoInvalidUser);
                        this.context.router.push('/error/403');
                    } else {
                        UI.notifyError( CONSTANTS.UI_MESSAGES.shoppingCart.purchaseCart);
                        this.context.router.push('/shopping-cart');
                    }

                } else {
                    this.state.moduleUrl = error && error.responseText;
                    this.provisionNow(extraItem,item);
                }
                this.setState(this.state);
            });
    },

    provisionNow(extraItem, item) {
        var userData = User.getCustomerParams();
        var project = Session.get('project');
        var combinationId = User.getProject('combinationId') || userData.assoCustomerCombinationId;
        if(extraItem) {
            var formData = {
                cv_crm_id: userData.assoCustomerId,
                bill_to_cv_crm_id: userData.customerId,
                projectId: userData.projectId ? userData.projectId : '',
                combinationId: combinationId,
                source: 'O',
                module: '',
                sof_number: extraItem.sofNumber,
                item_number: extraItem.lineItemNumber,
                vm_name: extraItem.vmName,
                irtask_number: extraItem.taskNumber,
                qty: extraItem.quantity,
                ip_address: extraItem.ipAddress,
                iptype: extraItem.ipType,
                serviceterm_month: extraItem.serviceTermMonth,
                zone_name: extraItem.location,
                token: extraItem.token,
                serviceterm_days: extraItem.serviceTermDays,
                serviceContractDetailsBean: extraItem.productInfoBeans,
                product_code: extraItem.lineItemProductCode,
                url: this.state.moduleUrl
            }
            this.state.isOpen = true;
            this.state.formData = formData;
        }
        this.state.loaded = true;
        this.setState(this.state)
    },

    hideModal() {
        this.state.isOpen = false;
        this.setState(this.state);
        this.props.setParentLoadState(true);
    },

    render() {
        return (
            <section className="ticketListSection outStandingWrap peopleWrap">
                <div className="blackOverlay"></div>
                <section className="midHeader">
                    <div className="container">
                        <div className="row">
                            <div className="col-xs-8 col-sm-8 col-md-8">
                                <ol className="breadcrumb breadCrumbCustom clearfix">
                                    <li><Link to={User.getSplashScreenURL()}>Back to Home</Link></li>
                                    <li>
                                        <Link to="dashboard" className="blueLink">Back to Dashboard</Link>
                                    </li>
                                    <li className="active">Provision</li>
                                </ol>
                                <h1 className="mainTitle">Provision</h1>
                            </div>
                        </div>
                    </div>
                </section>
                <div className="provisionCart">
                    <div className="provisionHeader">
                        <ul>
                            <li className="sofId">
                                <span>SOF Id</span>
                                <i className="sortDownArrow hide"></i>
                            </li>
                            <li className="lineItem">
                                <span>Line Item</span>
                                <i className="sortDownArrow hide"></i>
                            </li>
                            <li className="code">
                                <span>Code</span>
                                <i className="sortDownArrow hide"></i>
                            </li>
                            <li className="productName">
                                <span>Product Name</span>
                                <i className="sortDownArrow hide"></i>
                            </li>
                            <li className="contract">
                                <span>Contract</span>
                                <i className="sortDownArrow hide"></i>
                            </li>
                            <li className="location">
                                <span>Location</span>
                                <i className="sortDownArrow hide"></i>
                            </li>
                            <li className="quantity">
                                <span>Quantity</span>
                                <i className="sortDownArrow hide"></i>
                            </li>
                        </ul>
                    </div>
                    <Modal className="customModalWrapper provisionModal" isOpen={this.state.isOpen} onRequestHide={this.hideModal}>
                        <ModalHeader>
                            <ModalClose onClick={this.hideModal} />
                            <ModalTitle>Provision</ModalTitle>
                        </ModalHeader>
                        <ModalBody>
                            {
                                _.isEmpty(this.state.formData) ? (null)
                                    : <SSOIframe
                                        formData={this.state.formData}
                                        moduleUrl={this.state.moduleUrl}
                                        />
                            }
                        </ModalBody>
                        <ModalFooter>		
                        </ModalFooter>
                    </Modal>
                    <Loader loaded={this.state.loaded} top="50%" left="50%" options={LoaderOptions}>
                       {this.renderProvisionItems()}
                    </Loader>
                </div>
            </section>
        )
    }
});

export default ProvisionSC;
