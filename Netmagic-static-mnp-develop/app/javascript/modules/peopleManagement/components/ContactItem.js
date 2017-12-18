import CONSTANTS from '../../../constants/app-constant';
import User from '../../../services/user';
import { Link } from 'react-router';

var ContactItem = createReactClass({
    getInitialState() {
        return {
            condition: true
        }
    },

    contextTypes: {
        router: React.PropTypes.object
    },

    toggleDiv() {
        this.setState({
            condition: false,
        });
    },

    getListItem(data) {
        let listItem = (<div className="headActionWrap clearfix">
            <h4 className="pull-left">{CONSTANTS.NOT_AVAILABLE}</h4></div>);
        if (data.listOfProjects && data.listOfProjects.length) {
            listItem = data.listOfProjects.map(function (data, key) {
                (<h4 className="pull-left" key={key}>{data}</h4>);
            });
        }
        return listItem;
    },

    showMoreClass(projects) {
        return projects.toString().length >
            CONSTANTS.MAX_CHAR_LIMIT ? 'show pull-right' : 'hide'
    },

    contactDetailsLink(data) {
        var contact = (<h4 className="ticketNoCell pinkText">{data.firstName || '-'} {data.lastName || '-'}</h4>)
        if (User.canAccess(CONSTANTS.PERMISSION_CODES.contacts.details)) {
            contact = (<a className="ticketNoCell" onClick={this.props.redirectToContact.bind(null, data.contactId) }>
                <h4 className="pinkText">{data.firstName || '-'} {data.lastName || '-'}</h4>
            </a>);
        }
        return contact;
    },

    render() {
        let data = this.props.data;
        let projects = data.listOfProjects;
        const shoppingCart = CONSTANTS.SHOPPING_CART_VARIABLES;
        return (
            <li className="rowCell tabelDataCell">
                <span className="dataCell nameCell">
                    <div className="userImgWrap pull-left">
                        <i className="NMIcon-userGrey"></i>
                        <img src="" alt="user image" className="hide" />
                    </div>
                    <div className="userDetails pull-left">
                        {this.contactDetailsLink(data) }
                        <h5>{data.contactType || CONSTANTS.NOT_AVAILABLE}</h5>
                    </div>
                </span>
                <span className="dataCell emailCell">
                    <div className="userDetails pull-left">
                        <h4>{data.emailAddress || CONSTANTS.NOT_AVAILABLE}</h4>
                        <h5>{data.contactNumber || CONSTANTS.NOT_AVAILABLE}</h5>
                    </div>
                </span>
                <span className="dataCell associateCell">
                    <div className="userDetails pull-left">
                        <h4>{data.associateCustomerName || '-'}</h4>
                    </div>
                </span>
                <span className="dataCell projectCell">
                    <div className="userDetails pull-left">
                        <h4>{data.mnpuser ? shoppingCart.YES : shoppingCart.NO}</h4>
                    </div>
                </span>
                <span className="dataCell mnpUserCell hide">
                    <div className="userDetails pull-left">
                        <h4>{data.mnpuser ? shoppingCart.YES : shoppingCart.NO}</h4>
                    </div>
                </span>
            </li>
        );
    }
});

export default ContactItem;
