import { Link } from 'react-router';

var ChangeRequestModal = createReactClass({
  render() {
    return (
      <div className="changeRequestPopUp">
        <div className="topContent clearfix">
          <h4 className="pull-left">Create Business Change Request</h4>
          <div className="clearfix pull-right">
            <a href="javascript:void(0);" className="darkPinkBtn rippleBtn sendRequestBtn">Send Request</a>
            <a href="javascript:void(0);" className="blueLink closePop">Cancel</a>
          </div>
        </div>
        <div className="bottomContent">
          <div className="topSideContent">
            <span className="contractId clearfix">NMIT <span className="idCode blueLink">50015150</span></span>
            <span className="lineNumber clearfix">Line no: 02 <span className="whiteHilight statusId">Configuration Change</span></span>
            <h6>Dedicated Server</h6>
            <textarea rows="5" cols="74" name="comment" form="usrform" placeholder="Type details here"></textarea>
          </div>
          <div className="bottomSideContent clearfix">
            <div className="clearfix pull-left">
              <span className="contractId clearfix">NMIT <span className="idCode blueLink">50015150</span></span>
              <span className="lineNumber clearfix">Line no: 02 <span className="whiteHilight statusId">Quantity/Volume Change</span></span>
              <h6>Dedicated Server</h6>
            </div>
            <div className="manageServer pull-right ">
              <ul className="list-unstyled quantityDetail clearfix">
                <li>
                  <span className="nameQty grayText ">Old QTY</span>
                  <span className="totalQty balckText">10</span>
                </li>
                <li>
                  <span className="nameQty grayText">Updated QTY</span>
                  <span className="totalQty balckText pull-right"><input type="number"/></span>
                </li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    )
  }
});

export default ChangeRequestModal;
