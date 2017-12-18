var ContactsListHeader = createReactClass({
    render() {
        return (
            <li className="rowCell tableHeader contactsListHeader">
                <div className="container innerTableWrap paddingZero">
                    <span className="dataCell nameCell sortingCell">
                        Name/Contact Type
                    </span>
                    <span className="dataCell emailCell">
                        Email/Contact No.
                    </span>
                    <span className="dataCell associateCell sortingCell">
                        Associated Customer
                    </span>
                    <span className="dataCell projectCell sortingCell">
                        MNP User
                    </span>
                    <span className="dataCell mnpUserCell sortingCell hide">
                        MNP User
                    </span>
                </div>
            </li>
        )
    }
});

export default ContactsListHeader;
