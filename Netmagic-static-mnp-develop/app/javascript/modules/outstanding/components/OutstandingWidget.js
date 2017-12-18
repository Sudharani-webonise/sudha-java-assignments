import CONSTANTS from '../../../constants/app-constant';
import Utility from '../../../mixins/basicUtils';
import UI from '../../../mixins/ui';
import WidgetBody from '../../commons/WidgetBody';

var OutstandingWidget = createReactClass({

  mixins: [ UI, Utility ],

  valueConversion (val) {
    if(val >= 100000) {
      while(val / 10 > 1) {
        val /= 10;
      }
    }
    return val.toFixed(2);
  },

  getLabel (val) {
    var result = '';
    const amountUnit = CONSTANTS.NUMBER_DENOMINATION;
    var res = _.find(amountUnit, function(obj) { return val >= obj.val });
    return res && res.unit || '';
  },

  convertAmount (val) {
    var unit = this.getLabel(val);
    let value = val;

    switch(unit) {
 
      case "Thousand" :
      value = val/1000;
      break;

      case "Lacs" :
      value = val/100000;
      break;

      case "Crores" :
      value = val/10000000;
      break;

      default :
      value = val;
    }  
    return value.toFixed(2);
  },

  render () {
    let amounts = this.getVal(this.props, 'data.outstandingWidgetData.transaction.widgetInfo');
    return (
      amounts ? (
        <div>
          <div className="widgetDataCount clearfix">
            <i className="rupeeIcon">&#8377;</i>
            <p className="pull-left">{amounts.totalOutstandingAmount}</p>
            <span className="outstandingUnit hide">
              {this.getLabel(amounts.totalOutstandingAmount)}
            </span>
          </div>
          <div className="widgetDataCount outstanding clearfix hide">
            <div className="pull-left creditData">
              <label className="widgetLabel">Credit Limit</label>
              <div>
                <i className="rupeeIcon-credit">&#8377;</i>
                <span className="widgetSpan pull-left">{amounts && amounts.creditLimit}</span>
                <span className="outstandingUnit hide">
                  {this.getLabel(amounts && amounts.creditLimit)}
                </span>
              </div>
            </div>
            <div className="pull-left creditData">
              <label className="widgetLabel">Credit Unbilled</label>
              <div>
                <i className="rupeeIcon-credit">&#8377;</i>
                <span className="widgetSpan pull-left">{amounts && amounts.creditUnbilled}</span>
                <span className="outstandingUnit hide">
                  {this.getLabel(amounts && amounts.creditUnbilled)}
                </span>
              </div>
            </div>
          </div>
          <div className="highLightPrices clearfix">
            <ul className="list-unstyled clearfix">
              <li>
                <span className="pinkHilight maxDays">&#62; 90 Days</span>
                <p className="dayAmount">{this.getFormattedAmt(amounts.currency, amounts.outstandingSinceNinty)}</p>
              </li>
              <li>
                <span className="lightHilight maxDays">60 Days</span>
                <p className="dayAmount">{this.getFormattedAmt(amounts.currency, amounts.outstandingSinceSixty)}</p>
              </li>
              <li>
                <span className="blueHilight maxDays"> &#60; 30 Days</span>
                <p className="dayAmount">{this.getFormattedAmt(amounts.currency, amounts.outstandingSinceThirty)}</p>
              </li>
            </ul>
          </div>
        </div>
      ) : ( <WidgetBody iconClass="DBicon-alert widgetInfoIcon" infoText={CONSTANTS.UI_MESSAGES.widgetNoData}/>)
    )
  }
});

export default OutstandingWidget;
