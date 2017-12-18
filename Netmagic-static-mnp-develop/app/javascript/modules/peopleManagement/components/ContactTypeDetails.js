import { Link } from 'react-router';
import ModuleNotificationData from './ModuleNotificationData';
import Checkbox from '../../commons/Checkbox';
import CONSTANTS from '../../../constants/app-constant';
import Contact from '../../../services/contact';
import UI from '../../../mixins/ui';
import Session from '../../../mixins/sessionUtils';
import Utility from '../../../mixins/basicUtils';

var ContactTypeDetails = createReactClass({
    getInitialState() {
        return {
            subTypes: [],
            activeType: _.first(this.props.contactTypes)
        };
    },

    componentDidMount() {
        this.getSubTypes(_.first(this.props.contactTypes));
        var ticketModule = _.first(this.props.notifyData);
        Session.set('customerModuleData', ticketModule && ticketModule.contactTypesList || Session.get('customerModuleData'));
    },

    getNotificationData(data) {
        let notificationModule = (<div>{CONSTANTS.NOT_AVAILABLE}</div>);
        let editable = this.props.editable;
        if (data) {
            notificationModule = _.map(data, (data, key) => {
                return (<ModuleNotificationData moduleData={data} key={key} editable={editable} contactTypes={this.props.contactTypes}/>)
            });
        }
        return notificationModule;
    },

    getSubTypes(type) {
        this.setState({ activeType: type, subTypes: [] });
        Contact.getSubTypes(type.typeId)
            .then((result) => this.setState({ subTypes: result && result.contactSubTypeBeanList }))
            .catch((err) => UI.notifyError(CONSTANTS.UI_MESSAGES.dataNotFound));
    },

    typeExistInContact(data, subType) {
        var type = _.findWhere(data, { typeId: this.state.activeType.typeId });
        if (!type) {
            return false;
        } else if (type.contactSubTypeBeans.length) {
            return _.findWhere(type.contactSubTypeBeans, { subTypeId: subType.subTypeId })
        } else {
            return type.typeId === subType.typeId ? type : false;
        }
    },

    toggleSubType(event) {
        var checkboxSpan = $(event.currentTarget).closest('label').find('span');
        checkboxSpan && checkboxSpan.toggleClass('lightColorLabel');
    },

    addTypeSubType(subType) {
        var data = Session.get('contactTypeSubTypeBeansList') || this.props.data;
        const yes = CONSTANTS.YES;
        var activeType = this.state.activeType;
        var removedContactSubBeans = Session.get('removedContactTypeSubTypeBeansList') || [];
        if (subType.typeId) {
            data = data.filter((type) => type.typeId !== subType.typeId);
            data.push(_.extend(subType, { isActive: yes, contactSubTypeBeans: [] }));
            removedContactSubBeans = _.reject(data, { typeId: subType.typeId});
        } else {
            var existingType = _.findWhere(data, { typeId: activeType.typeId });
            if (existingType && existingType.contactSubTypeBeans) {
                existingType.contactSubTypeBeans = existingType.contactSubTypeBeans
                    .filter((item) => item.subTypeId !== subType.subTypeId);
                existingType.contactSubTypeBeans.push(_.extend(subType, { isActive: yes }));
            } else {
                var newType = Utility.clone(activeType);
                _.extend(subType, { isActive: yes });
                data.push(_.extend(newType, { isActive: yes, contactSubTypeBeans: [subType] }))
            }
        }
        Session.set('removedContactTypeSubTypeBeansList', removedContactSubBeans);
        Session.set('contactTypeSubTypeBeansList', data);
    },

    removeTypeSubType(subType) {
        var data = Session.get('contactTypeSubTypeBeansList') || this.props.data;
        const no = CONSTANTS.NO;
        var removedContactSubBeans = Session.get('removedContactTypeSubTypeBeansList') || [];
        if (subType.typeId) {
            var targetType = _.findWhere(data, { typeId: subType.typeId });
            if(targetType) {
                data = _.reject(data, { isActive: CONSTANTS.YES, typeId: subType.typeId, typeName: subType.typeName});
            }
            targetType.isActive = no;
            removedContactSubBeans = removedContactSubBeans.concat([targetType]);
        } else {
            var targetType = _.findWhere(data, { typeId: this.state.activeType.typeId });
            var exisitingType = _.findWhere(removedContactSubBeans, { typeId: this.state.activeType.typeId });
            var targetSubType = _.findWhere(targetType.contactSubTypeBeans,
                { subTypeId: subType.subTypeId });
            targetSubType.isActive = no;
            if(exisitingType) {
                removedContactSubBeans = _.reject(exisitingType)
                    .concat([targetType]);
            } else {
                removedContactSubBeans = removedContactSubBeans.concat([targetType]);
            }
        }
        Session.set('removedContactTypeSubTypeBeansList', removedContactSubBeans);
        Session.set('contactTypeSubTypeBeansList', data);
    },

    selectContactType(checked, subType) {
        var data = Session.get('contactTypeSubTypeBeansList') || this.props.data;
        this[checked ? 'addTypeSubType' : 'removeTypeSubType'](subType);
    },

    showSubTypes(data) {
        var types = this.state.subTypes.length ? this.state.subTypes : [this.state.activeType];
        _.each(types, (type) => {
            type.isActive = CONSTANTS.NO
        });
        return _.map(types, (subType, key) => {
            var checked = this.typeExistInContact(data, subType);
            return (<div className={this.props.editable ?
                'severityGrid pull-left editable' : 'severityGrid pull-left'} key={key}>
                <Checkbox key={key} checked={checked} editable={this.props.editable}
                    updateAction={this.selectContactType} data={subType}/>
                {subType.subTypeName || subType.typeName}
            </div>)
        })
    },

    getContactTypes(data) {
        var moduleName = (<div>{CONSTANTS.NOT_AVAILABLE}</div>);
        var data = this.props.contactTypes;
        if (data) {
            moduleName = _.map(data, (data, key) => (<li key={key} onClick={this.getSubTypes.bind(null, data) }>
                <a href="javascript:void(0);" className={data.typeId === this.state.activeType.typeId ? 'tabPeople  activeTab' : 'tabPeople'}>
                    {data.typeName}</a>
            </li>));
        }
        return moduleName;
    },

    render() {
        let data = Session.get('contactTypeSubTypeBeansList') || this.props.data;
        return (
            <div className="notificationWrap">
                <div className="tabContents activeTabContent">
                    <div className="settingTabs">
                        <ul className="clearfix">
                            {this.getContactTypes() }
                        </ul>
                        <div className="outerTabWrap">
                            <div className="settingTabWrap activeContentTab clearfix">
                                <div className="roleTypes clearfix">
                                    <div className="customerWrapper clearfix">
                                        <div className="clearfix">
                                            {this.showSubTypes(data) }
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
});

export default ContactTypeDetails;
