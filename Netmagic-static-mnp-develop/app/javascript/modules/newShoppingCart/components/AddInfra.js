import { Link } from 'react-router';

var AddInfra = createReactClass({
	render () {
		return (
	<div className="peopleWrap myCartPageWrap">
    <section className="midHeader">
      <div className="container">
        <div className="row">
          <div className="col-xs-9">
            <ol className="breadcrumb breadCrumbCustom clearfix">
              <li><a href="javascript:void(0);">Back to Dashboard</a></li>
              <li className="active">My Cart</li>
            </ol>
            <h1 className="mainTitle pull-left">My Cart <span className="myCartCount">5</span> </h1>
            <ul className="myCartPaymentProcess clearfix pull-left">
              <li className="currentCartStep"><i>1</i> Add Infra</li>
              <li><i>2</i> SOF Details</li>
              <li><i>3</i> Make Payment</li>
            </ul>
          </div>
          <div className="col-xs-3 paymentProcessBtns">
            <a href="javascript:void(0);" className="pinkBordered">Add new Infra</a>
            <a href="javascript:void(0);" className="darkPinkBtn pull-right">Confirm</a>
          </div>
        </div>
      </div>
    </section>
    <div className="container">
      <div className="row">
        <div className="col-md-9 myCartProjectListing">
          <div className="projectHeadings clearfix">
            <span className="pull-left">Project Name / Type / Contract / Location</span>
            <span className="pull-right monthlyCharge">Monthly Charges</span>
            <span className="pull-right oneTimeCharge">One Time Charges</span>
          </div>
          <ul>
            <li>
                <div className="projectNameWrap clearfix selected">
                  <div className="projectNameDuration">
                    <div className="checkboxWrap pull-left">
                      <label className="cssCheckBox">
                        <input type="checkbox" checked />
                        <span className="NMIcon-checkbox lightColorLabel"></span>
                      </label>
                    </div>
                    <h4>Project 1 - UAT <a href="#"><i className="iconEdit"></i></a></h4>
                    <div className="projectDuration">
                      <span>GBPS</span>
                      <span>12 Months</span>
                      <span>Mumbai DC-5</span>
                    </div>
                  </div>
                  <div className="projectCost">
                    <label className="oneTimeCharge">&#8377; 15,000</label>
                    <label className="monthlyCharge">&#8377; 12,000</label>
                    <span className="accordianBtn active pull-right"><span></span></span>
                  </div>
                </div>
                <div className="cartProjectInfoWrap">
                  <div className="projectInfoBox clearfix infraListing selected">
                    <div className="infraName">
                      <div className="checkboxWrap pull-left">
                        <label className="cssCheckBox">
                          <input type="checkbox" checked />
                          <span className="NMIcon-checkbox lightColorLabel"></span>
                        </label>
                      </div>
                      <h4>Infra Name - VM <span className="smallTag green">New</span></h4>
                      <div className="infraCode">
                        NSBSPCVSRS-ELGB
                      </div>
                    </div>
                    <div className="infraQuantityPrice">
                      <div className="infraQunatity">
                        <span>Qty. </span> <input type="text" value="1" />
                      </div>
                      <label className="oneTimeCharge">&#8377; 15,000</label>
                      <label className="monthlyCharge">&#8377; 12,000</label>
                      <span className="accordianBtn active pull-right"><span></span></span>
                    </div>
                  </div>
                  <div className="projectInfoData clearfix">
                    <div className="infraDetailsInfo">
                      <ul>
                        <li className="clearfix">
                          <div className="planType">
                            <label>Plan Type</label>
                            <span>Elastic</span>
                          </div>
                          <div className="basePlanSelected">
                            <label>Base Plan Selected</label>
                            <span>SimpliCompute Medium Elastic - GB</span>
                          </div>
                          <div className="osType">
                            <label>OS Type</label>
                            <span>Windows</span>
                          </div>
                        </li>
                        <li className="clearfix">
                          <div className="planType infraMemory">
                            <label>Memory</label>
                            <span>1 GB</span>
                          </div>
                          <div className="basePlanSelected">
                            <div className="infraCPU">
                              <label>CPU</label>
                              <span>1 Core</span>
                            </div>
                            <div className="infraDiskSpace">
                              <label>Disk Space</label>
                              <span>100 GB</span>
                            </div>
                          </div>
                          <div className="infraDataTransfer osType">
                            <label>Data Transfer</label>
                            <span>10 GB</span>
                          </div>
                        </li>
                        <li className="clearfix">
                          <div className="planType">
                            <label>OS Mgmt.</label>
                            <span>Yes</span>
                          </div>
                          <div className="basePlanSelected">
                            <div className="infraCPU">
                              <label>IP Version</label>
                              <span>IPV4</span>
                            </div>
                            <div className="infraDiskSpace">
                              <label>IP Attribute Qty</label>
                              <span>1</span>
                            </div>
                          </div>
                        </li>
                        <li className="editRemoveInfra clearfix">
                            <a href="#" className="btn blueBorderd">Edit</a>
                            <a href="#" className="redLink">Remove ?</a>
                        </li>
                      </ul>
                    </div>
                    <div className="excessBillingCharges">
                      <label className="pull-left">excess  billing charges</label>
                      <label className="pull-right">Rate Card Price</label>
                      <ul className="clearfix">
                        <li className="clearfix">
                            <label className="pull-left">Variable Charges</label>
                            <span className="pull-right rateCardPrice">&#8377; 6.00</span>
                        </li>
                        <li className="clearfix">
                            <label className="pull-left">Virtual CPU
                              <span>Hourly usage charges</span>
                            </label>
                            <span className="pull-right rateCardPrice">&#8377; 6.00</span>
                        </li>
                        <li className="clearfix">
                            <label className="pull-left">Virtual RAM
                              <span>Hourly usage charges</span>
                            </label>
                            <span className="pull-right rateCardPrice">&#8377; 4.00</span>
                        </li>
                        <li className="clearfix">
                            <label className="pull-left">Rate Based Unit
                              <span>Overage charges for rate based unit</span>
                            </label>
                            <span className="pull-right rateCardPrice">&#8377; 25.00</span>
                        </li>
                      </ul>
                    </div>
                  </div>

                  <div className="projectInfoBox clearfix infraListing closed">
                    <div className="infraName">
                      <div className="checkboxWrap pull-left">
                        <label className="cssCheckBox">
                          <input type="checkbox" />
                          <span className="NMIcon-checkbox lightColorLabel"></span>
                        </label>
                      </div>
                      <h4>Infra Name - VM <span className="smallTag blue">Ammendment</span></h4>
                      <div className="infraCode">
                        NSBSPCVSRS-ELGB
                      </div>
                    </div>
                    <div className="infraQuantityPrice">
                      <div className="infraQunatity">
                        <span>Qty. </span> <input type="text" value="1" />
                      </div>
                      <label className="oneTimeCharge">&#8377; 15,000</label>
                      <label className="monthlyCharge">&#8377; 12,000</label>
                      <span className="accordianBtn pull-right"><span></span></span>
                    </div>
                  </div>

                  <div className="projectInfoBox clearfix infraListing closed">
                    <div className="infraName">
                      <div className="checkboxWrap pull-left">
                        <label className="cssCheckBox">
                          <input type="checkbox" />
                          <span className="NMIcon-checkbox lightColorLabel"></span>
                        </label>
                      </div>
                      <h4>Infra Name - VM <span className="smallTag red">Due for Renewal</span></h4>
                      <div className="infraCode">
                        NSBSPCVSRS-ELGB
                      </div>
                    </div>
                    <div className="infraQuantityPrice">
                      <div className="infraQunatity">
                        <span>Qty. </span> <input type="text" value="1" />
                      </div>
                      <label className="oneTimeCharge">&#8377; 15,000</label>
                      <label className="monthlyCharge">&#8377; 12,000</label>
                      <span className="accordianBtn pull-right"><span></span></span>
                    </div>
                  </div>
                </div>
            </li>
            <li>
                <div className="projectNameWrap clearfix selected">
                  <div className="projectNameDuration">
                    <div className="checkboxWrap pull-left">
                      <label className="cssCheckBox">
                        <input type="checkbox" checked />
                        <span className="NMIcon-checkbox lightColorLabel"></span>
                      </label>
                    </div>
                    <h4>Project 2 - UAT <a href="#"><i className="iconEdit"></i></a></h4>
                    <div className="projectDuration">
                      <span>GBPS</span>
                      <span>12 Months</span>
                      <span>Mumbai DC-5</span>
                    </div>
                  </div>
                  <div className="projectCost">
                    <label className="oneTimeCharge">&#8377; 15,000</label>
                    <label className="monthlyCharge">&#8377; 12,000</label>
                    <span className="accordianBtn active pull-right"><span></span></span>
                  </div>
                </div>
                <div className="cartProjectInfoWrap">
                  <div className="projectInfoBox clearfix infraListing closed selected">
                    <div className="infraName">
                      <div className="checkboxWrap pull-left">
                        <label className="cssCheckBox">
                          <input type="checkbox" checked />
                          <span className="NMIcon-checkbox lightColorLabel"></span>
                        </label>
                      </div>
                      <h4>Infra Name - VM <span className="smallTag green">New</span></h4>
                      <div className="infraCode">
                        NSBSPCVSRS-ELGB
                      </div>
                    </div>
                    <div className="infraQuantityPrice">
                      <div className="infraQunatity">
                        <span>Qty. </span> <input type="text" />
                      </div>
                      <label className="oneTimeCharge">&#8377; 15,000</label>
                      <label className="monthlyCharge">&#8377; 12,000</label>
                      <span className="accordianBtn pull-right"><span></span></span>
                    </div>
                  </div>
                  <div className="projectInfoBox clearfix infraListing closed">
                    <div className="infraName">
                      <div className="checkboxWrap pull-left">
                        <label className="cssCheckBox">
                          <input type="checkbox"/>
                          <span className="NMIcon-checkbox lightColorLabel"></span>
                        </label>
                      </div>
                      <h4>Infra Name - VM <span className="smallTag blue">Ammendment</span></h4>
                      <div className="infraCode">
                        NSBSPCVSRS-ELGB
                      </div>
                    </div>
                    <div className="infraQuantityPrice">
                      <div className="infraQunatity">
                        <span>Qty. </span> <input type="text" value="1" />
                      </div>
                      <label className="oneTimeCharge">&#8377; 15,000</label>
                      <label className="monthlyCharge">&#8377; 12,000</label>
                      <span className="accordianBtn pull-right"><span></span></span>
                    </div>
                  </div>
                  <div className="projectInfoBox clearfix infraListing closed">
                    <div className="infraName">
                      <div className="checkboxWrap pull-left">
                        <label className="cssCheckBox">
                          <input type="checkbox" />
                          <span className="NMIcon-checkbox lightColorLabel"></span>
                        </label>
                      </div>
                      <h4>Infra Name - VM <span className="smallTag red">Due for Renewal</span></h4>
                      <div className="infraCode">
                        NSBSPCVSRS-ELGB
                      </div>
                    </div>
                    <div className="infraQuantityPrice">
                      <div className="infraQunatity">
                        <span>Qty. </span> <input type="text" value="1" />
                      </div>
                      <label className="oneTimeCharge">&#8377; 15,000</label>
                      <label className="monthlyCharge">&#8377; 12,000</label>
                      <span className="accordianBtn pull-right"><span></span></span>
                    </div>
                  </div>
                </div>
            </li>
            <li>
              <div className="projectNameWrap clearfix selected">
                <div className="projectNameDuration">
                  <div className="checkboxWrap pull-left">
                    <label className="cssCheckBox">
                      <input type="checkbox" checked />
                      <span className="NMIcon-checkbox lightColorLabel"></span>
                    </label>
                  </div>
                  <h4>Project 3 - UAT <a href="#"><i className="iconEdit"></i></a></h4>
                  <div className="projectDuration">
                    <span>GBPS</span>
                    <span>12 Months</span>
                    <span>Mumbai DC-5</span>
                  </div>
                </div>
                <div className="projectCost">
                  <label className="oneTimeCharge">&#8377; 15,000</label>
                  <label className="monthlyCharge">&#8377; 12,000</label>
                  <span className="accordianBtn pull-right"><span></span></span>
                </div>
              </div>
            </li>
            <li>
              <div className="projectNameWrap clearfix">
                <div className="projectNameDuration">
                  <div className="checkboxWrap pull-left">
                    <label className="cssCheckBox">
                      <input type="checkbox" />
                      <span className="NMIcon-checkbox lightColorLabel"></span>
                    </label>
                  </div>
                  <h4>Project 4 - UAT <a href="#"><i className="iconEdit"></i></a></h4>
                  <div className="projectDuration">
                    <span>GBPS</span>
                    <span>12 Months</span>
                    <span>Mumbai DC-5</span>
                  </div>
                </div>
                <div className="projectCost">
                  <label className="oneTimeCharge">&#8377; 15,000</label>
                  <label className="monthlyCharge">&#8377; 12,000</label>
                  <span className="accordianBtn pull-right"><span></span></span>
                </div>
              </div>
            </li>
          </ul>
        </div>
        <div className="col-md-3">
          <div className="cartTotalPriceWrap">
              <h3>Total Price</h3>
              <ul>
                <li>
                   <span className="totalCharges">&#8377; 25,000.00</span>
                   <span className="chargesInfo">Total One Time Charges</span>
                </li>
                <li>
                   <span className="totalCharges">&#8377; 8,400.00</span>
                   <span className="chargesInfo">Total Monthly Charges</span>
                </li>
                <li className="excessChargInfo">
                  <p className="grayText"><span className="mandatoryStar">*</span> The excess billing charges will be charge based on actual utilization.</p>
                </li>
              </ul>
          </div>
        </div>
      </div>
    </div>
</div>
		)
	}
});


export default AddInfra;
