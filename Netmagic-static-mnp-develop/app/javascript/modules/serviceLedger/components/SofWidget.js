import { Link } from 'react-router';
import CONSTANTS from '../../../constants/app-constant';
import Time from '../../../mixins/time';

var SofWidget = createReactClass({
  render() {
    return (
      <div className="tabInnerContent">
        <span className="checkActive pull-left">
          <label className="cssCheckBox">
            <input type="checkbox" />
            <span className="NMIcon-checkbox"></span>
          </label>
        </span>
        <span className="contractId">{CONSTANTS.DUMMY_SOF[0].contractCode}
          <span className="idCode">{this.props.data.SOFId}
            <SofWidgetTooltip data = {this.props.data} />
          </span>
        </span>
        <p className="contractMonths">{this.props.data.ContractPeriod || CONSTANTS.NOT_AVAILABLE} {this.props.data.ContractPeriodUnit || CONSTANTS.NOT_AVAILABLE} contract</p>
        <span className="highLightContract greenHilight">{this.props.data.ContractType} Contract</span>
      </div>
    )
  }
});

var SofWidgetTooltip = createReactClass({
  formatDate(dateString) {
    return Time.format(dateString, CONSTANTS.SOF_DATE_FORMAT, CONSTANTS.INPUT_DATE_FORMAT);
  },

  render() {
    return (
      <div className="toolTipBox contractToolTip">
        <h6 className="pinkText hide">{this.props.data.CustomerName}</h6>
        <span className="grayText companyCode hide">Company Code: {this.props.data.companyCode || CONSTANTS.NOT_AVAILABLE}</span>
        <h6 className="pinkText">{this.props.data.CustomerName}: { this.props.data.companyCode || CONSTANTS.NOT_AVAILABLE }</h6>
        <div className="clearfix">
          <span className="grayText pull-left">Start Date: {this.formatDate(this.props.data.ContractStartDate)}</span>
          <span className="grayText pull-right">End Date: {this.formatDate(this.props.data.ContractEndDate)}</span>
        </div>
      </div>
    )
  }
});

export default SofWidget;
