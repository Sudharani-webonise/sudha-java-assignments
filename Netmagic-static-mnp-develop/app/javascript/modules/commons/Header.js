import Session from '../../mixins/sessionUtils';
import Authorization from '../../services/authorization';
import UI from '../../mixins/ui';
import ReactDOM from 'react-dom';
import {Link} from 'react-router';
import http from '../../mixins/restutils';
import CONSTANTS from '../../constants/app-constant';
import User from '../../services/user';
import shoppingCart from '../../services/shoppingCart';
import ListCartItemForHeader from '../shoppingCart/components/ListCartItemForHeader';
import Ticket from '../../services/ticket';

var Header = createReactClass({
    render() {
        return User.isLoggedIn() ?
            (<HeaderV2 assocCustomer={this.props.assocCustomer} setParentLoadState={this.props.setParentLoadState} assocCustomers={this.props.assocCustomers}
                hideChicklet={this.props.hideChicklet} isShoppingCart={this.props.isShoppingCart}
                cloudPortal={this.props.cloudPortal}/>) :
            (<HeaderV1 routeName={this.props.routeName}/>);
    },
});

var HeaderV1 = createReactClass({
    getInternalUserDomain() {
        var route = '/MyNetmagic';
        if(this.props.routeName === '/NMInternalUser') {
            route = '/NMInternalUser';
        }
        return route;
    },

    render() {
        return (
            <div className="blackThemeHeader clearfix">
                <header className="clearfix">
                    <div className="container paddingZero text-center">
                        <Link to={this.getInternalUserDomain()} className="headerNttLogo">
                            <img src={require('../../../theme/default/images/NTT_loginLogo.png') } />
                        </Link>
                        <Link to={this.getInternalUserDomain()} className="headerNttLogo">
                            <img src={require('../../../theme/default/images/NM_loginLogo.png') } />
                        </Link>
                        <div>
                            { this.props.routeName === 'signup' ? (<HaveAccount />) : ('') }
                        </div>
                    </div>
                </header>
            </div>
        )
    }
});

const HaveAccount = () => <p className="pull-right loginLinkWrap"> Already have an account?<Link to='/MyNetmagic' className="loginLink">Login</Link> </p>

var HeaderV2 = createReactClass({

    mixins: [Session, User, UI],

    getInitialState() {
        return {
            show: {
                associated: true,
                currentProject: true,
                cartData: [],
                showSCToolTipBox: false
            },
            loaded: {
                productList: false,
                subSubCategories: false,
                pricing: false
            },
            subCategories: [],
            projects: [],
            billGroups: [],
            provision: []
        }
    },

    componentWillMount() {
        if(!this.props.hideChicklet) {
            this.getProvisionData();
            this.getInfraProducts();
            this.fetchProjects();
            this.getSubCategories();
        }
    },

    componentDidMount() {
        if(!this.props.hideChicklet) {
            this.getBillGroup();
        }
    },

    componentWillReceiveProps(nextProps) {
        if(!nextProps.hideChicklet) {
            this.getProvisionData();
            this.getInfraProducts();
            this.getBillGroup();
            this.fetchProjects();
        }
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
                this.setState({ provision: result });
            })
            .catch(function (error) {
                // do nothing
            });
    },

    getInfraProducts() {
        let userDetails = Session.get('userDetails');
        let userObj = User.getCustomerParams();
        let url = http.buildRestUrl(CONSTANTS.API_URLS.shoppingCart.getCart, { mainCustomerId:
                userObj.customerSugarId || userDetails && userDetails.kacid,
                associateCustomerId: userObj.assoCustomerSugarId || userDetails && userDetails.kacid });
        url = `${url}?emailId=${User.getUserInfo('loginUserEmail')}`;
        this.state.loaded = false;
        shoppingCart.getProductList(url)
            .then((res) => {
                this.state.requestData = res;
                this.state.billGroups = res && res.billgroup || [];
                this.setState(this.state);
            })
            .catch((err) => {
                // do nothing
            });
    },

    fetchProjects() {
        let userObj = User.getCustomerParams();
        Ticket.getProjects({
                id: userObj.customerId,
                associateCustomerId: userObj.assoCustomerId
            })
            .then((res) => {
                _.each(res, (project) => {
                    project.label = project.projectName;
                    project.value = project.projectName;
                });
                this.setState({ projects: res });
            })
            .catch((err) => {
                // Do nothing
            })
    },

    getBillGroup() {
        const shoppingConst = CONSTANTS.SHOPPING_CART_VARIABLES;
        var params = {
            customerId: User.getMainCustomer('customerId') || User.getMainCustomer('cvCrmId'),
            associateCustomerId: User.getAssociateCustomer('customerId') ||
                User.getAssociateCustomer('cvCrmId') || User.getMainCustomer('customerId') || User.getMainCustomer('cvCrmId'),
            projectId: User.getProject('sugarProjectId') || ''
        };
        if(User.getUserInfo('existingUser')) {
            shoppingCart.getBillingGroup(CONSTANTS.API_URLS.shoppingCart.billingGroup, params)
                .then((result) => {
                    Session.set('billGroupResponse', result);
                    let isMBGB = shoppingConst.MBPS.indexOf(result.unitOfMeasurment) > -1;
                    let isGB = shoppingConst.GB.indexOf(result.unitOfMeasurment) > -1;
                    switch(true) {
                        case isGB:
                            this.state.subCategories = _.reject(this.state.subCategories, { subcatid: shoppingConst.RATE_BASE_CAT_ID });
                            break;
                        case isMBGB:
                            this.state.subCategories = _.reject(this.state.subCategories, { subcatid: shoppingConst.VOLUME_BASE_CAT_ID });
                            break;
                        default:
                            this.state.subCategories = _.reject(this.state.subCategories,
                                (item) => item.subcatid == shoppingConst.VOLUME_BASE_CAT_ID);
                            this.state.subCategories = _.reject(this.state.subCategories,
                                (item) => item.subcatid == shoppingConst.RATE_BASE_CAT_ID);
                            break;
                    }
                    this.setState(this.state);
                })
                .catch((err) => {
                    // UI.notifyError(err);
                });
        } else {
            this.state.subCategories = _.reject(this.state.subCategories,
                (item) => item.subcatid == shoppingConst.VOLUME_BASE_CAT_ID);
            this.state.subCategories = _.reject(this.state.subCategories,
                (item) => item.subcatid == shoppingConst.RATE_BASE_CAT_ID);
            this.setState(this.state);
        }
    },

    getSubCategories() {
        shoppingCart.getData(CONSTANTS.API_URLS.shoppingCart.subCategories)
        .then((result) => {
            this.state.subCategories = result.items;
            this.getBillGroup();
            this.setState(this.state);
        })
        .catch((error) => {
            UI.notifyError( error && error.statusText)
        });
    },

    contextTypes: {
        router: React.PropTypes.object
    },

    logout() {
        Authorization.clearToken();
        this.removeItems(['user', 'activeCustomerId', 'mainCustomer',
            'associatedCustomer', 'userPermissions', 'modulePermissions',
            'permissions', 'customerBeans', 'splashScreen', 'updateTimestamp',
            'splashData', 'project', 'ShoppingCartPricingDetails',
            'tempCartData', 'globalData', 'defaultPermissions',
            'customerModuleData', 'notificationPropsData', 'notificationConfig',
            'contactId', 'contactType', 'mainCustomerList', 'contact',
            'existingData', 'shoppingCartData', 'userDetails', 'corporateData',
            'billGroupResponse', 'signUpInfo', 'selectedInvoices',
            'paymentInfo', 'onAccountPaymentList', 'defaultTdsValue',
            'creditNotes', 'OutstandingTxnId', 'finalInvoices']);
    },

    fetchNewPermissions(mainCustomerId, assocCustomerId, projectId) {
        this.props.setParentLoadState(false);
        this.getPermissions({
            mainCustomerId: mainCustomerId,
            associateCustomerId: assocCustomerId ? assocCustomerId : mainCustomerId || '',
            projectId: projectId || ''
        }).then((result) => {
                this.props.setParentLoadState(true)
            })
            .catch((error) => {
                //this.notifyError(CONSTANTS.UI_MESSAGES.userPermissionError);
            })
    },

    removeAssociate() {
        var mainCustomer = this.get('mainCustomer');
        var userObj = User.getCustomerParams();
        if (mainCustomer) {
            var mainCustomerId = userObj.customerId;
            this.removeItems(['associatedCustomer', 'project']);
            this.set('activeCustomerId', userObj.customerId);
            this.setState({
                show: {
                    associated: false,
                    currentProject: false
                }
            });
            this.fetchNewPermissions(mainCustomerId);
        }
    },

    removeProject() {
        var mainCustomer = this.get('mainCustomer') || {};
        var associatedCustomer = this.get('associatedCustomer');
        var userObj = User.getCustomerParams();

        if (mainCustomer && associatedCustomer) {
            this.remove('project');
            var mainCustomerId = userObj.customerId;
            this.set('activeCustomerId',
                userObj.customerId || userObj.assoCustomerId);
            this.setState({
                show: {
                    associated: true,
                    currentProject: false
                }
            });
            this.fetchNewPermissions(mainCustomerId, this.get('activeCustomerId'));
            this.context.router.push('/dashboard')
        }
    },

    getSlicedName(name) {
        if (name) {
            return (name.length > CONSTANTS.NAME_LIMIT ? name.slice(0, CONSTANTS.SUB_STRING_LIMIT) + '...' : name);
        }
    },

    showNameOnHover(name = "") {
        var isNameLong = (name.length > CONSTANTS.NAME_LIMIT);
        return this.classSet({
            associateHoverName: isNameLong,
            hide: !isNameLong
        });
    },

    addAssociate(event) {
        var mainCustomer = this.get('mainCustomer') || {};
        var selectedCustomer = {};
        if(User.getUserInfo('isInternalUser')) {
            selectedCustomer = _.find(this.get('customerBeans'), function (user) { return user.cvName === event.currentTarget.value });
        } else {
            selectedCustomer = _.find(this.get('customerBeans'), function (user) { return user.customerName === event.currentTarget.value });
        }
        this.set('associatedCustomer', selectedCustomer);
        var assocCustomer = this.get('associatedCustomer');
        event.currentTarget.value = '';
        var userObj = User.getCustomerParams();
        if (mainCustomer && assocCustomer) {
            this.set('activeCustomerId', userObj.assoCustomerId);
            this.setState({
                show: {
                    associated: true,
                    currentProject: false
                }
            });
            this.fetchNewPermissions(userObj.customerId, this.get('activeCustomerId'), this.get('project'));
        }
    },

    addProject(event) {
        var associatedCustomer = this.get('associatedCustomer');
        var mainCustomer = this.get('mainCustomer') || {};
        var projects = associatedCustomer && associatedCustomer.projects || this.state.projects;
        var selectedProject = _.find(projects, (project) => {
            return project.projectName === event.currentTarget.value
        });
        this.set('project', selectedProject);
        var project = this.get('project');
        event.currentTarget.value = '';
        var userObj = User.getCustomerParams();
        if (mainCustomer && associatedCustomer && project) {
            this.set('activeCustomerId', userObj.assoCustomerId);
            this.setState({
                show: {
                    associated: true,
                    currentProject: true
                }
            });
            this.fetchNewPermissions(userObj.customerId, this.get('activeCustomerId'), userObj.projectId);
        }
    },

    getProjects() {
        var associatedCustomer = Session.get('associatedCustomer');
        var projects = [];
        if(User.getUserInfo('isInternalUser')) {
            projects =  this.state.projects || [];
        } else {
            projects = associatedCustomer && associatedCustomer.projects || [];
        }
        return projects
    },

    showSCToolTipBox() {
        this.setState({ showSCToolTipBox: !this.state.showSCToolTipBox });
    },

    getCartData() {
        var cartData = Session.get('tempCartData')//this.state.cartData;
        return (
            <div>
                <div className="cartHeader">
                    <span className="cartCount">my cart</span>
                    <div className="clear"></div>
                </div>
                <div className="cartListing">
                    <ul className="listingHeader">
                        <li className="productName"><span>Product Name</span></li>
                        <li className="quantity"><span>Qty</span></li>
                        <li className="contract"><span>Contract</span></li>
                        <li className="location"><span>Location</span></li>
                    </ul>
                    { (cartData, (cartItem, i) => {
                        return (
                            <ListCartItemForHeader product={cartItem} index = {i}/>
                        )
                    }) }
                </div>
            </div>
        )
    },

    getCartCount() {
        var cartData = Session.get('tempCartData')
        return cartData.length;
    },

    redirectToSplash(moduleRoute) {
        var user = Session.get('user');
        var customersData = user && user.customerSplashPageDataBeans || !(user && user.existingCustomer);
        return customersData ? moduleRoute : '/new-shopping';
    },

    showAssociateCustomer() {
        var assocCustomers = this.props.assocCustomers || Session.get('customerBeans')
        return _.map(assocCustomers, (data, key) => {
            return <option key={key} data-id={data.customerId || data.cvCrmId} value={data.customerName || data.cvName} onChange={this.addAssociate}></option>
        });
    },

    showProjects() {
        return _.map(this.getProjects(Session.get('associatedCustomer')), (data, key) => {
            return <option key={key} data-id={data.projectId} value={data.projectName} onChange={this.addProject}></option>
        });
    },

    showSubCategories(subCategories) {
        var listItem = null;
        if(_.isArray(subCategories) && subCategories.length) {
            listItem = _.map(subCategories, (data, key) => {
                var url = { pathname: '/new-shopping', query: { subcatid: data.subcatid } };
                return (
                    <li key={key}><Link to={url}>{data.subcatname}</Link></li>
                )
            })
        }
        return listItem;
    },

    render() {
        let userData = Session.get('user');
        let customersData = Session.get('user') &&
            Session.get('user').customerSplashPageDataBeans;
        let mainCustomer = _.findWhere(customersData, { isMainCustomer: CONSTANTS.YES }) || {};
        Session.set('mainCustomer', Session.get('mainCustomer') || mainCustomer);
        let associatedCustomer = Session.get('associatedCustomer');
        let projectCheck = Session.get('project')
        let project = _.isEmpty(projectCheck) ? '' : projectCheck;
        let {associated, currentProject } = this.state.show;
        let hideChicklet = this.props.hideChicklet;
        let userObj = User.getCustomerParams();
        let productListItems = _.chain(this.state.billGroups)
            .pluck('items')
            .flatten()
            .value();
        return (
            <header className="clearfix headerWrap navbar-fixed-top row">
                <div className="leftMenuWrap clearfix col-xs-3">
                    <Link to={this.getUserInfo('isInternalUser') ? '/splash-screen-internal' : this.redirectToSplash('/splash-screen') } className="netMagicMainlogo">
                        <img className="img-responsive" src={require('../../../theme/default/images/NTT_logo-header.png') } />
                    </Link>
                    <Link to={this.getUserInfo('isInternalUser') ? '/splash-screen-internal' : this.redirectToSplash('/splash-screen') } className="netMagiclogo">
                        <span>
                            <img className="img-responsive" src={require('../../../theme/default/images/net-logo-black.png') } />
                        </span>
                    </Link>
                </div>

                <div className="searchWrapper headerSearchBox col-xs-6">
                    {
                        User.getUserInfo('existingUser') ||
                        User.getUserInfo('isInternalUser')?
                        <div className="clearfix">
                            { hideChicklet ? (null) :
                                (<div className="customerName">
                                    <span>
                                        {this.getSlicedName(userObj.customerName) }
                                    </span>
                                    <label className={this.showNameOnHover(userObj.customerName) }>
                                        {userObj.customerName}
                                    </label>
                                    <span className="idNum">&nbsp; -{userObj.customerId}</span>
                                </div>
                                )
                            }
                            {
                                associatedCustomer && (userObj.customerId != userObj.assoCustomerId) ?
                                    (<div className={associated && !hideChicklet ? "assocCustomerName" : "assocCustomerName hide"}>
                                        <span ref="associatedLabel">
                                            {this.getSlicedName(userObj.associteCustomerName) }
                                        </span>
                                        <label className={this.showNameOnHover(userObj.associteCustomerName) }>
                                            { userObj.associteCustomerName}
                                        </label>
                                        <span className="idNum">-{ userObj.assoCustomerId }
                                        </span>
                                        <i className="removeIcon" onClick={this.removeAssociate}>x</i>
                                    </div>) : null
                            }
                            {
                                associatedCustomer && project ?
                                    (<div className={currentProject && !hideChicklet ? "assocCustomerName projectName" : "assocCustomerName hide"}>
                                        <span ref="projectLabel">
                                            {this.getSlicedName(userObj.projectName) }
                                        </span>
                                        <label className={this.showNameOnHover(userObj.projectName) }>
                                            {userObj.projectName}
                                        </label>
                                        <span className="idNum">-{ userObj.projectId}
                                        </span>
                                        <i className="removeIcon" onClick={this.removeProject}>x</i>
                                    </div>)
                                    : null
                            }
                            {
                                _.isObject(associatedCustomer) && associatedCustomer ? null :
                                    (<div className={ hideChicklet ? "searchBar  hide" : "searchBar"}>
                                        <input list="customers" name="browser" onChange={this.addAssociate} placeholder="Select Associated Customer..."/>
                                        <datalist className="dataSelector" id="customers">
                                            {this.showAssociateCustomer()}
                                        </datalist>
                                    </div>
                                    )
                            }
                            {
                                _.isObject(associatedCustomer) && associatedCustomer && !project ?
                                    (
                                        <div className={ hideChicklet ? "searchBar hide" : "searchBar"}>
                                            <input list="customers" name="browser" placeholder="Select Project ..." onChange={this.addProject}/>
                                            <datalist className="dataSelector" id="customers">
                                                {this.showProjects()}
                                            </datalist>
                                        </div>
                                    ) : null
                            }
                            <input type="text" name="searchField" className="searchBox noHover hide" placeholder="" />
                            <a href="javascript:void(0);" className="slideSearchLeft hide">
                                <input type="submit" className="submitSearch NMIcon-pinkSearch" value="" />
                            </a>
                        </div>: (null)
                    }
                </div>
                <div className="rightMenuPanel col-xs-3">
                    <a href="javascript:void(0);" className="headRightMenus hide">
                        <i className="toolTipBox">Notification</i>
                        <span className="NMIcon-pinkBell pinkBellWrap"></span>
                        <span className="notifyNumber hide">8</span>
                    </a>

				    <a href="javascript:void(0);" id="userInfoLink" className="userInfoWrap pull-right">
                        <span className="NMIcon-userPink userPinkIcon"></span>
                    </a>
                    <ul className="settingsDropDown">
                        <li>
                            <a href="javascript:void(0)">
                                <span className="userNameInfo" title={this.getUserInfo('loginUserName')}>
                                    {this.getUserInfo('loginUserName')}
                                </span>
                            </a>
                        </li>
                        <li className='hide'><a href="javascript:void(0)">Configure Dashboard</a></li>
                        <li className='hide'><a href="javascript:void(0)">Notification Settings</a></li>
                        <li className='hide'><Link to='roles'>Roles Management</Link></li>
                        <li><Link to={this.getUserInfo('isInternalUser') ? '/NMInternalUser' : '/MyNetmagic'} className="logoutLink" onClick={this.logout}>Logout</Link></li>
                    </ul>
                    {
                        _.some([hideChicklet, this.props.cloudPortal, User.getUserInfo('isInternalUser')]) ? (null) :(<div>
                            <a href="javascript:void(0)" className="pull-right buyingLink" id="buyOption">
                                <span>Buy Now</span>
                            </a>
                            <ul className="buyDropDown">
                                {this.showSubCategories(this.state.subCategories)}
                            </ul>
                        </div>)
                    }
                    {
                        hideChicklet ? (null) :
                        <div>
                            <Link className="headRightMenus pull-right cartLink"
                            to='/product-list'>
                                <span className="NMIcon-cartPink pinkCartWrap"></span>
                                <span className="notifyNumber">
                                    {productListItems.length}
                                </span>
                            </Link>
                            <Link to={this.redirectToSplash('/provision') } className="headRightMenus pull-right provisionLink">
                                <i></i>
                                <span className="notifyNumber">
                                    {this.state.provision.length}
                                </span>
                            </Link>
                        </div>
                    }
                </div>
            </header>
        );
    }
});

export default Header;
