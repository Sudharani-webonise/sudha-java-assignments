import { Link } from 'react-router';

var RenewalModal = createReactClass({
  render() {
    return (
      <div className="dueforRenewalPopUp">
        <div className="topContent clearfix">
          <h4 className="pull-left">NMT 50015150</h4>
          <div className="clearfix pull-right">
            <a href="javascript:void(0);" className="darkPinkBtn rippleBtn sendRequestBtn">Renew All</a>
            <a href="javascript:void(0);" className="blueLink discontinueLink">Discontinue All</a>
            <a href="javascript:void(0);" className="blueLink closePop">Cancel</a>
          </div>
        </div>
        <div className="bottomContent">
          <ul className="renewalList">
            <li className="clearfix">
              <span className="lineBox">
                <span>Line no:01</span>
                <h6>Dedicated Server</h6>
              </span>
              <span className="orderedBox">
                <span>Ordered</span>
                <span className="subText">10 Qty</span>
              </span>
              <span className="dateBox">
                <span>Date</span>
                <span className="subText">15 Oct 215</span>
              </span>
              <span className="locationBox">
                <span>Location</span>
                <span className="subText">Dc 3 - Mumbai</span>
              </span>
              <span className="linkBox pull-left">
                <a href="javascript:void(0);" className="darkPinkBtn">Renew</a>
                <a href="javascript:void(0);" className="blueLink">Discontinue</a>
              </span>
            </li>
            <li className="clearfix">
              <span className="lineBox">
                <span>Line no:01</span>
                <h6>Dedicated Server</h6>
              </span>
              <span className="orderedBox">
                <span>Ordered</span>
                <span className="subText">10 Qty</span>
              </span>
              <span className="dateBox">
                <span>Date</span>
                <span className="subText">15 Oct 215</span>
              </span>
              <span className="locationBox">
                <span>Location</span>
                <span className="subText">Dc 3 - Mumbai</span>
              </span>
              <span className="linkBox pull-left">
                <a href="javascript:void(0);" className="darkPinkBtn">Renew</a>
                <a href="javascript:void(0);" className="blueLink">Discontinue</a>
              </span>
            </li>
            <li className="clearfix">
              <span className="lineBox">
                <span>Line no:01</span>
                <h6>Dedicated Server</h6>
              </span>
              <span className="orderedBox">
                <span>Ordered</span>
                <span className="subText">10 Qty</span>
              </span>
              <span className="dateBox">
                <span>Date</span>
                <span className="subText">15 Oct 215</span>
              </span>
              <span className="locationBox">
                <span>Location</span>
                <span className="subText">Dc 3 - Mumbai</span>
              </span>
              <span className="linkBox pull-left">
                <a href="javascript:void(0);" className="darkPinkBtn">Renew</a>
                <a href="javascript:void(0);" className="blueLink">Discontinue</a>
              </span>
            </li>
            <li className="clearfix">
              <span className="lineBox">
                <span>Line no:01</span>
                <h6>Dedicated Server</h6>
              </span>
              <span className="orderedBox">
                <span>Ordered</span>
                <span className="subText">10 Qty</span>
              </span>
              <span className="dateBox">
                <span>Date</span>
                <span className="subText">15 Oct 215</span>
              </span>
              <span className="locationBox">
                <span>Location</span>
                <span className="subText">Dc 3 - Mumbai</span>
              </span>
              <span className="linkBox pull-left">
                <a href="javascript:void(0);" className="darkPinkBtn">Renew</a>
                <a href="javascript:void(0);" className="blueLink">Discontinue</a>
              </span>
            </li>
          </ul>
        </div>
      </div>
    )
  }
});

export default RenewalModal;
