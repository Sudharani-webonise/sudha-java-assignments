import { Link } from 'react-router';
import CONSTANTS from '../../../constants/app-constant';
import WidgetHead from '../../commons/WidgetHead';
import WidgetBody from '../../commons/WidgetBody';
import SofWidget from './SofWidget';
import LineItem from './LineItem';
import ChangeRequestModal from './ChangeRequestModal';
import RenewalModal from './RenewalModal';
import RequestMailSent from './RequestMailSent';
import http from '../../../mixins/restutils';
import UI from '../../../mixins/ui';
import Session from '../../../mixins/sessionUtils';
import Service from '../../../services/service';
import Loader from 'react-loader';
import LoaderOptions from '../../../mixins/loader';
import User from '../../../services/user';
import PostLoader from '../../commons/Loader';
import Utility from '../../../mixins/basicUtils';

var ServiceLedger = createReactClass({
  mixins: [http, UI, Session, Service, User],

  contextTypes: {
    router: React.PropTypes.object
  },

  getInitialState () {
    return {
      serviceStatus: this._getStatus(),
      SOFList: [],
      SOFLineItems: [],
      firstSOFId: '',
      loaded: false,
      showLoader: false
    };
  },

  componentWillMount() {
    this.redirectIfUnauthorized.call(this,
      CONSTANTS.PERMISSION_CODES.services.listing);
  },

  componentDidMount () {
    this.getSOFListItems();
  },

  _getStatus() {
    var statusList = CONSTANTS.SERVICE_STATUS;
    var status = _.findWhere(statusList,
      {value: this.props.location.query.status});
    return status ? status.value : _.first(statusList).value;
  },

  getSOFListItems() {
    this.setState({ showLoader: true });
    var userRequestParams = User.getCustomerParams();
    var params = {
      customerId: userRequestParams.customerId,
      associateCustomerId: userRequestParams.assoCustomerId,
      projectId: userRequestParams.sugarProjectId
    };
    Service.getSOFList(params, this.state.serviceStatus)
      .then((result) => {
        var firstSofId = result && result[0] && result[0].SOFId;
        this.setState({
          SOFList: result || [],
          activeSOFId: firstSofId,
          loaded: true,
          showLoader: false
        });
        if(firstSofId) {
          this.fetchSOFLineItems(firstSofId, this.state.serviceStatus);
        } else {
          this.setState({ SOFLineItems: [], loaded: true, showLoader: false });
        }
      })
      .catch((error) => {
        this.setState({ loaded: true, showLoader: false });
        UI.notifyError(error && error.statusText)
      });
  },

  fetchSOFLineItems(sofNumber, serviceStatus) {
    this.setState({ showLoader: true });
    this.getSOFLineItems(sofNumber, serviceStatus)
      .then((result) => {
          if(result && result.length) {
            _.first(result).ServiceAttribute = Utility.interpretNewLine(_.first(result).ServiceAttribute)
            _.first(result).ServiceAttributeDetail = Utility.interpretNewLine(_.first(result).ServiceAttributeDetail)
            this.setState({ SOFLineItems: result, loaded: true, showLoader: false})
          }
      })
      .catch((error) => {
        this.setState({ loaded: true, showLoader: false });
        UI.notifyError(error && error.statusText)
      });
  },

  getFilteredServices (event) {
    event.stopPropagation();
    var target = event.currentTarget;
    this.toggleActiveListItem(target);
    this.state.serviceStatus = target.dataset.value
      || CONSTANTS.SERVICE_STATUS[0].value;
    this.state.SOFList = [];
    this.state.SOFLineItems = [];
    this.setState(this.state);
    this.getSOFListItems();
  },

  getLineItems (event) {
    var sofNumber = event.currentTarget.dataset.sofNumber;
    this.setState({ activeSOFId: sofNumber });
    this.fetchSOFLineItems(sofNumber, this.state.serviceStatus);
  },

  getSofList (data) {
    var sofList = null;
    if(_.isArray(data) && data.length) {
      sofList = _.map(data, function (item, key) {
        return (
          <li className={item.SOFId === this.state.activeSOFId ? 'active' : ''} key={key}
            onClick={this.getLineItems} data-sof-number={item.SOFId}>
            <SofWidget data={item} />
          </li>
        )
      }.bind(this));
    }
    return sofList;
  },

  getSofLineItem (data) {
    var sofLineItem = (<div className='activesTab text-center'>{CONSTANTS.UI_MESSAGES.dataNotFound}</div>);
    if (_.isArray(data) && data.length) {
      sofLineItem = _.map(data, function (item, key) {
        return (<LineItem data={item} key={key}/>)
      });
    }
    return sofLineItem;
  },

  getServiceStatuses (data) {
    var serviceStatuses = null;
    if (_.isArray(data) && data.length) {
      serviceStatuses = _.map(data, function (item, key) {
          return ( <li data-value={item.value}
            className={item.value === this.state.serviceStatus ? 'active' : ''}
            onClick={this.getFilteredServices} key={key}>
            <a href="javascript:void(0);" className="blueLink">{item.title}</a>
          </li>)
        }.bind(this)
      );
    }
    return serviceStatuses;
  },

  render() {
    var _this = this;
    return (
      <section className="ticketListSection serviceLedgerInner">
        <div className="blackOverlay"></div>
        <div className="fullscreenOverlay"></div>
        <section className="midHeader">
          <div className="container">
            <div className="row">
              <div className="col-md-4 col-sm-4 col-xs-12 headBarLeft clear">
                <div className="pull-left">
                 <ol className="breadcrumb breadCrumbCustom clearfix">
                    <li><Link to={User.getSplashScreenURL()}>Back to Home</Link></li>
                    <li><Link to="dashboard">Back to Dashboard</Link></li>
                    <li className="ticketListBread active">SOF List</li>
                  </ol>
                </div>
              </div>
            </div>
            <div className="row">
              <div className="col-md-9 col-sm-9 col-md-offset-3 headBarRight clearfix">
                <div className="clearfix pull-right servicelevelChange">
                  {/*@TODO: Need to integrate dynamic data*/}
                </div>
                <Loader loaded={this.state.loaded} top="50%" left="50%" options={LoaderOptions}>
                  <div className="clearfix serviceTabs pull-left">
                    <ul className="list-unstyled clearfix" ref="serviceStatus">
                      {this.getServiceStatuses(CONSTANTS.SERVICE_STATUS)}
                    </ul>
                  </div>
                </Loader>
              </div>
            </div>
          </div>
        </section>

        <section className="container seviceContentWrap">
          <Loader loaded={this.state.loaded} top="50%" left="50%" options={LoaderOptions}>
            <div className="leftTabs pull-left">
              <ul className="list-unstyled">
                {this.getSofList(this.state.SOFList)}
              </ul>
            </div>
            <div className="rightContentTabs pull-left">
              <PostLoader show={this.state.showLoader}/>
              <div className="ledgerTabs" id="actives">
                {this.getSofLineItem(this.state.SOFLineItems)}
              </div>
            </div>
            <ChangeRequestModal />
            <RequestMailSent />
            <RenewalModal />
          </Loader>
        </section>
      </section>
    );
  },
});

export default ServiceLedger;
