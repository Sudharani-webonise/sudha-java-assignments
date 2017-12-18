import { Link } from 'react-router';
import ContactEdit from './ContactEdit';
import CONSTANTS from '../../../constants/app-constant';
import Utility from '../../../mixins/basicUtils';
import Session from '../../../mixins/sessionUtils';
import User from '../../../services/user';

var BasicDetails = createReactClass({
    associatedCustomerNames(contact) {
        var roleTypeBeans = Utility.getVal(contact, 'mynmRoleFunctionsBeanList');
        return roleTypeBeans &&
            _.pluck(roleTypeBeans, 'associteCustomerName').join(', ').toString();
    },

    getProjects(contact) {
        var roleTypeBeans = Utility.getVal(contact, 'mynmRoleFunctionsBeanList');
        return roleTypeBeans && 
        _.chain(roleTypeBeans)
            .pluck('projectName')
            .compact()
            .value().toString() || CONSTANTS.NOT_AVAILABLE;
    },

    getFullName(contact) {
        return contact.firstName || contact.lastName ?
            (contact.firstName || '') + ' ' + (contact.lastName || '') :
            CONSTANTS.HYPHEN;
    },

    render() {
        let contact = this.props.data;
        const NA = CONSTANTS.NOT_AVAILABLE;
        Session.set('contact', this.props.data);
        return (
            <ul className="tickeListTable peopleListTable peopleDetailsTable">
                <li className="rowCell tabelDataCell">
                    <span className="dataCell nameCell">
                        <div className="userImgWrap pull-left">
                            <i className="NMIcon-userGrey"></i>
                            <img src="" alt="user image" className="hide" />
                        </div>
                        <div className="userDetails pull-left">
                            <h4 className="pinkText">{this.getFullName(contact) }</h4>
                            <h5>{ Session.get('contactType') || Utility.getVal(contact, 'contactTypeSubTypeBeansList.[0].typeName') || NA}</h5>
                        </div>
                    </span>
                    <span className="dataCell associateCell">
                        <div className="userDetails pull-left">
                            <span className="subTypeLabel">Associated Customer</span>
                            <p className="subType marginBottom25">{this.associatedCustomerNames(contact) || NA}</p>
                        </div>
                    </span>
                    <span className="dataCell associateCell">
                        <div className="userDetails pull-left">
                            <span className="subTypeLabel">Projects</span>
                            <p className="subType marginBottom25">{this.getProjects(contact) }</p>
                        </div>
                    </span>
                    <span className="dataCell projectCell">
                        <div className="userDetails">
                            <div className="headActionWrap clearfix">
                                <div className="buttonControlWrap pull-right clearfix">
                                     { User.canAccess(CONSTANTS.PERMISSION_CODES.contacts.edit) ?
                                        <Link to={`contacts/edit`} activeClassName="" className="provideInputBtn pull-left">
                                        Edit
                                        </Link> : null 
                                    }
                                </div>
                            </div>
                        </div>
                    </span>
                </li>
                <li className="rowCell tabelDataCell extraDetailCell">
                    <span className="dataCell nameCell">
                        <div className="userDetails">
                            <span className="subTypeLabel">Designation</span>
                            <p className="subType marginBottom25">{contact.designation || NA}</p>
                            <span className="subTypeLabel">Email</span>
                            <p className="subType">{contact.primaryEmailId || NA}</p>
                        </div>
                    </span>
                    <span className="dataCell contactTypeCell">
                        <div className="userDetails">
                            <span className="subTypeLabel">Contact number</span>
                            <p className="subType">{Utility.getVal(contact, 'contactNumbersInfoList.[0].contactNumber') || NA}</p>
                            <span className="subTypeLabel">Calling Hrs</span>
                            <p className="subType">{contact.callingPreferredTime || NA}</p>
                        </div>
                    </span>
                </li>
            </ul>
        )
    }
});

export default BasicDetails;
