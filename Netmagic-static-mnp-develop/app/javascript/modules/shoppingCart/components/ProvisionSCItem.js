import { Link } from 'react-router';
import CONSTANTS from '../../../constants/app-constant';
import Session from '../../../mixins/sessionUtils';
import Utility from '../../../mixins/basicUtils';
import UI from '../../../mixins/ui';
import { Modal, ModalHeader, ModalTitle, ModalClose, ModalBody, ModalFooter } from 'react-modal-bootstrap';
import Loader from 'react-loader';
import LoaderOptions from '../../../mixins/loader';
import User from '../../../services/user';
import http from '../../../mixins/restutils';
import shoppingCart from '../../../services/shoppingCart';

var ProvisionSCItem = createReactClass({

    mixins: [Utility, UI],
    contextTypes: {
        router: React.PropTypes.object
    },

    getInitialState() {
        return {
            collapse: false,
            selectedLineItem: {},
            formData: {},
            loaded: false
        }
    },

    toggle(item) {
        this.setState({ collapse: !this.state.collapse, loaded: false })
    },

    getToggleClassSet() {
        return this.classSet({
            'provisionData': true,
            'show': this.state.collapse,
            'hide': !this.state.collapse
        });

    },

    getAccordianBtnClass() {
        return this.classSet({
            'accordianBtn': true,
            'active': this.state.collapse
        });
    },

    getLineItemDetails(item) {
        this.state.loaded = false;
        this.setState(this.state)
        var userData = User.getCustomerParams()
        var item = this.props.item
        var params = {
            lineItemNumber: item.lineItemNumber,
            sofNumber: item.sofNumber,
        }
        var url = CONSTANTS.API_URLS.provision.lineItemDetails;
        shoppingCart.getData(url, params)
            .then((result) => {
                this.setState({ selectedLineItem: result, loaded: true })
                this.props.getSsoDataURL(result, item);
            })
            .catch(function (error) {
                UI.notifyError( error && error.statusText)
            });
    },

    render() {
        let index = this.props.index;
        let item = this.props.item;
        var pipe = item.productDesc.indexOf('|')
        let productDesc = pipe > 0 ? item.productDesc.substr(0, pipe) : item.productDesc;
        let locationDC = item.location.substr(0, item.location.indexOf('-'))
        let locationName = item.location.substr(item.location.indexOf('-'))
        return (
            <div className="provisionListContainer">
                <ul>
                    <li>
                        <div className="upperList">
                            <span className="sofId">
                                <span>{item.sofNumber}</span>
                            </span>
                            <span className="lineItem">
                                <span>{item.lineItemNumber}</span>
                            </span>
                            <span className="code">
                                <span>{item.productCode}</span>
                            </span>
                            <span className="productName">
                                <span>{productDesc}</span>
                            </span>
                            <span className="contract">
                                <span>{item.contractedPeriod}</span>
                            </span>
                            <span className="location">
                                <span>{locationDC}{locationName}</span>
                            </span>
                            <span className="quantity">
                                <span>{item.quantity}</span>
                            </span>
                            <span className="provisionBtn" onClick={this.getLineItemDetails.bind(null, item)}>Provision now</span>
                            <span className="accordianBtn" onClick={this.toggle.bind(null, item)} className={this.getAccordianBtnClass()}>
                                <span></span>
                            </span>
                        </div>
                        <div className={this.getToggleClassSet()}>
                            {_.map(item.serviceContractDetailsBean, (data, index) => {
                                return (
                                    <div key={index} className="listDetails">
                                        <label>{data.serviceDesc}</label>
                                        <span>{data.serviceContractedQty}</span>
                                    </div>
                                )
                            })}
                        </div>
                    </li>
                </ul>
            </div>
        )
    }
});

export default ProvisionSCItem;
