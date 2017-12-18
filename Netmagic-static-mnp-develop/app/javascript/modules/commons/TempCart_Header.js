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

var TempCart_Header = createReactClass({
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
        }
    },

    componentWillMount() {
        if(!this.props.hideChicklet) {
            this.getSubCategories();
        }
    },

    getSubCategories() {
        shoppingCart.getData(CONSTANTS.API_URLS.shoppingCart.subCategories)
        .then((result) => {
            this.state.subCategories = result.items
            this.state.loaded.productList = true;
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
            'splashData', 'project', 'ShoppingCartPricingDetails', 'tempCartData',
            'globalData', 'defaultPermissions', 'customerModuleData',
            'notificationPropsData', 'notificationConfig', 'contactId',
            'contactType', 'mainCustomerList', 'contact', 'existingData']);
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
        if (mainCustomer) {
            var mainCustomerId = mainCustomer.customerId;
            this.removeItems(['associatedCustomer', 'project']);
            this.set('activeCustomerId', User.getMainCustomer('customerId'));
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
        if (mainCustomer && associatedCustomer) {
            this.remove('project');
            var mainCustomerId = mainCustomer.customerId;
            this.set('activeCustomerId',
                associatedCustomer && associatedCustomer.customerId);
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
        var selectedCustomer = _.find(this.get('customerBeans'), function (user) { return user.customerName === event.currentTarget.value });
        this.set('associatedCustomer', selectedCustomer);
        var assocCustomer = this.get('associatedCustomer');
        event.currentTarget.value = '';
        if (mainCustomer && assocCustomer) {
            this.set('activeCustomerId', assocCustomer.customerId);
            this.setState({
                show: {
                    associated: true,
                    currentProject: false
                }
            });
            this.fetchNewPermissions(mainCustomer.customerId, this.get('activeCustomerId'), this.get('project'));
        }
    },

    addProject(event) {
        var associatedCustomer = this.get('associatedCustomer');
        var mainCustomer = this.get('mainCustomer') || {};
        var selectedProject = _.find(associatedCustomer && associatedCustomer.projects, (project) => {
            return project.projectName === event.currentTarget.value
        });
        this.set('project', selectedProject);
        var project = this.get('project');
        event.currentTarget.value = '';
        if (mainCustomer && associatedCustomer && project) {
            this.set('activeCustomerId', associatedCustomer.customerId);
            this.setState({
                show: {
                    associated: true,
                    currentProject: true
                }
            });
            this.fetchNewPermissions(mainCustomer.customerId, this.get('activeCustomerId'), User.getProject('projectId'));
        }
    },

    getProjects() {
        var associatedCustomer = Session.get('associatedCustomer');
        return associatedCustomer && associatedCustomer.projects || [];
    },

    showSCToolTipBox() {
        this.setState({ showSCToolTipBox: !this.state.showSCToolTipBox });
    },

    fetchExistingCart() {
        shoppingCart.getData(CONSTANTS.API_URLS.shoppingCart.getCart)
            .then((result) => {
                // set this.state.cartData
            })
            .catch(function (error) {
                //UI.notifyError(error && error.statusText)
            }.bind(this));
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
        var customersData = user && user.customerSplashPageDataBeans;
        return customersData ? moduleRoute : '/shopping-cart';
    },

    showAssociateCustomer() {
        return _.map(this.props.assocCustomers, (data, key) => {
            return <option key={key} data-id={data.customerId} value={data.customerName} onChange={this.addAssociate}></option>
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
                
                <div className="rightMenuPanel col-xs-9">
                    

				    
                                        {
                        hideChicklet || this.props.cloudPortal ? (null) :(<div>
                            <a href="javascript:void(0)" className="pull-right buyingLink" id="buyOption">
                                <span>Buy Now</span>
                            </a>
                           
                        </div>)
                    }
                    
                    
                </div>
            </header>
        );
    },
});

export default TempCart_Header;
