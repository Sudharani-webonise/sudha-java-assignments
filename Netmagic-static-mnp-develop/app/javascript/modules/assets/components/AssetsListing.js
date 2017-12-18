import { Link } from 'react-router';
import CONSTANTS from '../../../constants/app-constant';
import Time from '../../../mixins/time';
import Loader from 'react-loader';
import LoaderOptions from '../../../mixins/loader';
import http from '../../../mixins/restutils';
import Session from '../../../mixins/sessionUtils';
import Assets from '../../../services/assets';
import User from '../../../services/user';
import Utility from '../../../mixins/basicUtils';
import UI from '../../../mixins/ui';
import Select from 'react-select';

var AssetsListing = createReactClass({
    mixins: [User, Utility, Session, http, Time, UI, Assets],

    contextTypes: {
        router: React.PropTypes.object
    },

    getInitialState() {
        var queryParam = this.props.location.query;
        return {
            listOfCustomerAssets: [],
            listOfAssetTags: [],
            activeValue: !_.isEmpty(queryParam) && queryParam || 
                _.first(CONSTANTS.ASSET_TYPES),
            showRight: false,
            multiValue: [],
            loaded: false,
            pageNumber: 1,
            updateHeight: ''
        };
    },

    componentWillMount() {
        this.redirectIfUnauthorized.call(this,
            CONSTANTS.PERMISSION_CODES.assets.listing);
    },

    componentDidMount() {
        var params = {
            assetType: this.state.activeValue.type || _.first(CONSTANTS.ASSET_TYPES).type,
            pageNumber: this.state.pageNumber
        };
        this.callMethod('getList', 'listOfCustomerAssets', params);
        this.callMethod('getTagList', 'listOfAssetTags');
        $(document).on('scroll', this.showMoreVisible);
        Session.set('splashScreen', true);
    },

    componentWillUnmount() {
        $(document).off('scroll');
    },

    getAssets(options, appendList, params) {
        this.setState({ loaded: false, listOfCustomerAssets: [] });
        Assets.getList(params)
            .then((result) => {
                result = this.toObject(result);
                var state = this.getObject('listOfCustomerAssets', result && result['listOfCustomerAssets'] || []);
                var listOfCustomerAssets = appendList ? _.union(this.state.listOfCustomerAssets, result.listOfCustomerAssets)
                    : result.listOfCustomerAssets;
                this.setState({ 
                    listOfCustomerAssets: listOfCustomerAssets,
                    loaded: true
                });
            })
            .catch((err) => {
                this.setState({ loaded: true });
                // UI.notifyError(error && error.statusText) we do not want to display forbidden error
            })
    },

    callMethod(methodName, stateKey, params) {
        if (_.isFunction(this[methodName])) {
            this[methodName](params)
                .then((result) => {
                    result = this.toObject(result);
                    var state = this.getObject(stateKey, result && result[stateKey] || []);
                    this.state.loaded = true;
                    this.setState(state);
                })
                .catch((error) => {
                    this.setState({ loaded: true });
                    //UI.notifyError(error && error.statusText)
                });
        }
    },

    showMoreVisible() {
        if (document.body.scrollHeight === document.body.scrollTop + window.innerHeight) {
            this.state.pageNumber++;
            var params = {
                assetType: this.state.activeValue.type,
                pageNumber: this.state.pageNumber,
                tags: this.state.multiValue
            };
            this.getAssets(null, true, params);
        }
    },

    activeTab(tab, option) {
        var params = {
            assetType: tab && tab.type,
            pageNumber: 1,
            tags: this.state.multiValue,
        };
        setTimeout(() => this.getAssets(null, false, params), option);
        this.setState({
            activeValue: tab || this.state.activeValue,
            pageNumber: 1
        });
        window.scrollTo(0, 0);
    },

    getAssetTypes() {
        return _.map(CONSTANTS.ASSET_TYPES, (item, key) => {
            return (<li key={key} className={this.state.activeValue.type === item.type ? "activeTab" : ""}
                key={item.id} onClick={this.activeTab.bind(null, item) }><span>{item.type}</span></li>)
        });
    },

    getAsset(data) {
        var template = (<li>{CONSTANTS.UI_MESSAGES.dataNotFound}</li>);
        if (_.isArray(data) && data.length) {
            template = _.map(data, (item, key) => {
                return <AssetItem key={item.assetId}
                    callMethod={this.callMethod} activeTab={this.activeTab}
                    state={this.state} assetData={item} listItem={this.listItem}
                    tagList={this.state.listOfAssetTags}/>
            });
        }
        return template;
    },

    toggleDiv(event) {
        var searchBar = event.currentTarget.classList.contains('searchBtn');
        var dropDown = event.currentTarget.classList.contains('searchBarWrapper');
        if ((!this.state.showRight && searchBar) || dropDown) {
            this.setState({ showRight: true });
            event.stopPropagation();
            return;
        }
        if (!this.state.multiValue.length && this.state.showRight && !dropDown) {
            this.setState({ showRight: false });
        }
    },

    getToggleClassSet() {
        return this.classSet({
            'tagSearch': true,
            'clearfix': true,
            'expandedMenu': this.state.showRight
        });
    },

    changeSelection(tags) {
        var uniqTags = _.uniq(tags, (item) => item.label);
        var filteredTags = _.filter(uniqTags,
            (tag) => tag && tag.label && tag.label.trim());
        this.state.pageNumber = 1;
        var params = {
            tab: this.state.activeValue.type,
            tags: filteredTags,
            pageNumber: this.state.pageNumber
        };
        this.getAssets(null, false, params);
        this.setState({
            multiValue: filteredTags,
            pageNumber: this.state.pageNumber
        });

    },

    listItem(data) {
        return _.map(data, function (item) {
            return { label: item, value: item.charAt(0) }
        });
    },

    onSearch() {
        this.state.pageNumber = 1;
        var params = {
            tab: this.state.activeValue.type,
            tags: this.state.multiValue,
            pageNumber: this.state.pageNumber
        };
        $(document).on('scroll', this.showMoreVisible);
        this.getAssets(null, false, params);
    },

    searchedTags(tags) {
        return _.pluck(tags, 'label').join(', ');
    },

    changeHeight() {
        var height = this.refs.searchBox.offsetHeight;
        this.setState({ updateHeight: height });
    },

    render() {
        const { multiValue, listOfAssetTags, updateHeight } = this.state;
        let styleHeight = 101 + updateHeight + 'px';
        var styleMarginTop = { marginTop: styleHeight };
        var updateStlying = { top: styleHeight };

        return (
            <section className="ticketListSection" onClick={this.toggleDiv}>
                <section className="breadCrumbSection">
                    <div className="container">
                        <div className="row">
                            <div className="col-xs-10 col-sm-10 col-md-10">
                                <ol className="breadcrumb breadCrumbCustom clearfix">
                                    <li><Link to={User.getSplashScreenURL()}>Back to Home</Link></li>
                                    <li><Link to="dashboard">Back to Dashboard</Link></li>
                                    <li className="ticketListBread active">Asset List</li>
                                </ol>
                                <h2 className="pageHeading">Assets</h2>
                            </div>
                        </div>
                    </div>
                </section>
                <div className="assetsContainer">
                    <div className="assetsHeader clearfix">
                        <ul className="assetsMenu pull-left clearfix">
                            {this.getAssetTypes() }
                        </ul>
                    </div>
                    <div className="tagList clearfix">
                        <div className={this.getToggleClassSet() }>
                            <span className="searchBtn"
                                onClick={_.isArray(this.state.multiValue) && this.state.multiValue.length ?
                                    this.onSearch : this.toggleDiv}></span>
                            <div className="searchBarWrapper" ref="searchBox" onClick={this.toggleDiv}>
                                <Select allowCreate={false} multi={true} options={this.listItem(listOfAssetTags) }
                                    onChange={this.changeSelection} value={multiValue} searchable={true}
                                    matchPos="any" matchProp="any" ignoreCase={true}
                                    placeholder="Search by tags"/>
                            </div>
                        </div>
                    </div>
                    <AssetTypeHeader stylea={updateStlying}/>
                    <div className="assetsContent" style={styleMarginTop}>
                        <Loader loaded={this.state.loaded} top="50%" left="50%" options={LoaderOptions}>
                            <ul className="virtualServerList list-unstyled clearfix">
                                {this.getAsset(this.state.listOfCustomerAssets) }
                            </ul>
                        </Loader>
                    </div>
                    <div className="clear"></div>
                </div>
            </section>
        );
    }
});

var AssetTypeHeader = createReactClass({
    render() {
        return (
            <div style={this.props.stylea} className="virtualServerHeader clearfix">
                <div className="serialNum leftAlign">
                    <span>Serial No</span>
                </div>
                <div className="barCode leftAlign">
                    <span>Bar Code</span>
                </div>
                <div className="typeMake leftAlign">
                    <div className="typMakeHeading">
                        <span>Type Make</span>
                        <span>Model</span>
                    </div>
                </div>
                <div className="privateIP leftAlign">
                    <div className="privateIpHeading">
                        <span>Private IP/IP1/IP2</span>
                        <span>DB IP</span>
                    </div>
                </div>
                <div className="location leftAlign">
                    <span>Location</span>
                </div>
                <div className="rack leftAlign">
                    <div className="rackHeading">
                        <span>Rack from</span>
                        <span>U to U</span>
                    </div>
                </div>
                <div className="warranty leftAlign">
                    <span>Warranty</span>
                </div>
                <div className="clear"></div>
            </div>
        );
    }
});

var AssetItem = createReactClass({
    mixins: [Utility, UI, Assets],
    getInitialState() {
        return {
            collapse: false,
            show: false,
            multiValue: [],
            colorArray: [],
            tagsList:[]
        }
    },

    componentDidMount() {
        var tags = Utility.getVal(this, 'props.assetData.assetTag') || [];
        this.state.colorArray = _.map(_.range(tags.length),
            (index) => this.makeRandomColor());
        this.setState(this.state);
    },

    defaultParams() {
        var state = this.props.state;
        return {
            assetType: state.activeValue.type,
            pageNumber: 1,
            tags: state.multiValue
        }
    },

    getFormattedData(data) {
        return data && _.isString(data) ? data.split(',').length : null;
    },

    componentWillReceiveProps(nextProps) {
        var newTagLength = this.getFormattedData(nextProps.assetData.assetTag) - this.getFormattedData(this.props.assetData.assetTag);
        if (newTagLength > 0) {
            _.each(_.range(newTagLength),
                (index) => this.state.colorArray.push(this.makeRandomColor()))
        }
        this.setState(this.state);
    },

    addRemove(params, url, messageKey) {
        Assets.addRemoveTag(params, url)
            .then(function (result, response) {
                if (result.errCode === CONSTANTS.ERROR_CODE.emptyTag) {
                    this.notifyError(result.errMsg);
                } else if (result.errCode === CONSTANTS.ERROR_CODE.duplicateTag) {
                    this.notifyError(result.errMsg);
                } else {
                    this.notifySuccess(CONSTANTS.UI_MESSAGES[messageKey]);
                }
            }.bind(this))
            .catch(function (error) {
                if (error === 'OK') {
                    this.notifySuccess(CONSTANTS.UI_MESSAGES[messageKey]);
                } else {
                    this.notifyError(error);
                }
            }.bind(this));
        setTimeout(() => {
            this.props.callMethod('getTagList', 'listOfAssetTags');
        }, 3000);
        this.props.activeTab(this.props.state.activeValue, 1000);
    },

    changeSelection(tag) {
        if(!_.isEmpty(tag)) {
            const KEYTYPE = CONSTANTS.KEY_TYPE;
            if ((event.type === KEYTYPE.ENTER) || (event.type === KEYTYPE.MOUSE)) {
                var params = {
                    assetId: this.props.assetData.assetId,
                    tagName: tag.label
                };
                this.addRemove(params, CONSTANTS.API_URLS.assetTag, 'tagCreated');
            }
        }
    },

    removeTagItem(event) {
        var currentTarget = event.currentTarget.dataset;
        var params = {
            assetId: currentTarget.id,
            tagName: currentTarget.item
        };
        this.addRemove(params, CONSTANTS.API_URLS.assetTagRemove, 'tagRemoved');
    },

    getValue(data) {
        return data || CONSTANTS.NOT_AVAILABLE;
    },

    toggle() {
        this.setState({ collapse: !this.state.collapse })
    },

    getToggleClassSet() {
        return this.classSet({
            'accordianData': true,
            'clearfix': true,
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

    showDiv() {
        this.setState({ show: !this.state.show });
        this.props.callMethod('getTagList', 'listOfAssetTags');
    },

    getTags(data) {
        var tags = data.split(',');
        var tagTemplate = null;
        if (data !== CONSTANTS.NOT_AVAILABLE || data) {
            if (_.isArray(tags) && tags.length) {
                tagTemplate = _.map(tags, (item, key) => {
                    return <p key={key} className="label tag pull-left">
                        {item}
                        <span className="close" data-id={this.props.assetData.assetId} data-item={item} onClick={this.removeTagItem}>X</span>
                    </p>
                });
            }
        }
        return tagTemplate;
    },

    showTagsContainer(data) {
        var template = null;
        if (data && data !== CONSTANTS.NOT_AVAILABLE) {
            template = (<div className="tags clearfix">
                {this.getTags(this.getValue(data)) }</div>);
        }
        return template;
    },

    getData(firstValue, secondValue) {
        var template = <div>{CONSTANTS.NOT_AVAILABLE}</div>;
        if(firstValue && secondValue) {
            template = (<div>
                <span>{this.getValue(firstValue) } to</span>
                <span>{this.getValue(secondValue) }</span>
            </div>);
        }
        return template;
    },

    getRackData(firstValue, secondValue) {
        var template = <div>{CONSTANTS.NOT_AVAILABLE}</div>;
        if(firstValue && secondValue) {
            template = (
                <span>{this.getValue(firstValue) } to {this.getValue(secondValue) }</span>
            );
        }
        return template;
    },

    render() {
        let data = this.props.assetData;
        const { multiValue } = this.state;
        let placeholder = <span> Add Tags</span>;
        return (
            <li>
                <div className="accordianHeader clearfix">
                    <div className="serialNum leftAlign">
                        <a href="javascript:void(0)">{this.getValue(data && data.serialNumber) }</a>
                    </div>
                    <div className="barCode leftAlign">
                        <span>{this.getValue(data && data.barcode) }</span>
                    </div>
                    <div className="typeMake leftAlign">
                        <span>{this.getValue(data && data.assetMake) }</span>
                    </div>
                    <div className="privateIP leftAlign">
                        <span>
                            {this.getValue(data && data.privateIpAddress) } /
                            {this.getValue(data && data.publicIp1) }
                        </span>
                        <span>
                            {this.getValue(data && data.publicIp2) } /
                            {this.getValue(data && data.databaseIp) }
                        </span>
                    </div>
                    <div className="location leftAlign">
                        <span>{this.getValue(data && data.location) }</span>
                    </div>
                    <div className="rack leftAlign">
                        {this.getRackData(data && data.from_U, data && data.to_U)}
                    </div>
                    <div className="warranty leftAlign">
                        {this.getData(data && data.warrantyExpiryFrom, data && data.warrantyExpiryTo)}
                    </div>
                    <span onClick={this.toggle} className={this.getAccordianBtnClass() }>
                        <span></span>
                    </span>
                </div>
                <div className="clear"></div>
                <div className={this.getToggleClassSet() }>
                    <div className="hwId leftFloat">
                        <label>HW ID Sr.No</label>
                        <span>{this.getValue(data && data.assetHardwareId) }</span>
                    </div>
                    <div className="hostName leftFloat">
                        <label>Host Name</label>
                        <span>{this.getValue(data && data.hostName) }</span>
                    </div>
                    <div className="ownedBy leftFloat">
                        <label>Owned by / Bill No</label>
                        <span>{this.getVal(data, 'ownerToCustomerDetails.cvName') }</span>
                    </div>
                    <div className="assignTo leftFloat">
                        <label>Assign To</label>
                        <span>{this.getVal(data, 'assignToCustomerDetails.cvName') }</span>
                    </div>
                    <div className="osVersion leftFloat">
                        <label>OS Version</label>
                        <span>{this.getValue(data && data.osVersion) }</span>
                    </div>
                    <div className="clearfix">
                        <div className="tag leftFloat">
                            <label>Tag: </label>
                            <span className="actionBtn" onClick={this.showDiv}>Add Tags...</span>
                        </div>
                        <div className={this.state.show ? "leftFloat addTagComponent" : "hide"}>
                            <Select.Creatable multi={false} options={this.props.listItem(this.props.tagList) } onChange={this.changeSelection}
                                value={multiValue} openOnFocus={true}
                                searchable={true} placeholder={placeholder} />
                        </div>
                    </div>
                    {this.showTagsContainer(data && data.assetTag) }
                    <div className="clear"></div>
                </div>
            </li>
        );
    }
});

export default AssetsListing;
