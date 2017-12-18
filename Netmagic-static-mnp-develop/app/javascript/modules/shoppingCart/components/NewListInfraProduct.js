import { Link } from 'react-router';
import CONSTANTS from '../../../constants/app-constant';
import Session from '../../../mixins/sessionUtils';
import Utility from '../../../mixins/basicUtils';
import UI from '../../../mixins/ui';
import { Modal, ModalHeader, ModalTitle, ModalClose, ModalBody, ModalFooter} from 'react-modal-bootstrap';
import NewListInfraItem from './NewListInfraItem';


var NewListInfraProduct = createReactClass({

    mixins: [Utility, UI],
    contextTypes: {
        router: React.PropTypes.object
    },

    getInitialState() {
        return {
            removeModalDisplay: false,
            index: ''
        }
    },

    openModal(i) {
        this.setState({
            removeModalDisplay: true,
            index: i
        });
    },

    hideModal(event) {
        this.setState({
            removeModalDisplay: false
        });
    },

    render() {

        return (
            <div className="row cartListContainer">
                {_.map(this.props.products, (product, i) => {
                    return (
                        <NewListInfraItem
                            key = {i}
                            product={product}
                            hideModal = {this.hideModal}
                            openModal = {this.openModal}
                            index = {i}
                            showConfigurationsByTypeForListInfra={this.props.showConfigurationsByTypeForListInfra}
                            />
                    )
                }) }
            </div>
        )
    }
});

export default NewListInfraProduct;
