import { Link } from 'react-router';

var AddSimpliScaleBasic = createReactClass({
	render () {
		return (

<div className="ticketListSection outStandingWrap addContactWrap">
  <section className="midHeader">
    <div className="container">
      <div className="row">
        <div className="col-xs-9">
          <ol className="breadcrumb breadCrumbCustom clearfix">
            <li><a href="javascript:void(0);">Back to Dashboard</a></li>
            <li><a href="javascript:void(0);">Cloud Portal</a></li>
            <li><a href="javascript:void(0);">Add New Infra</a></li>
            <li className="active">SimpliScale</li>
          </ol>
          <h1 className="mainTitle">Add SimpliScale</h1>
        </div>
        <div className="col-xs-3"> <a href="javascript:void(0);" className="whiteCancelBtn pull-right">Cancel</a> <a href="javascript:void(0);" className="darkPinkBtn pull-right">Confirm</a> </div>
      </div>
    </div>
  </section>
  <section className="buyOptionWrap addFormWrapper">
    <div className="container">
      <div className="row">
        <form className="buyOptionForm">
          <div className="col-md-6">
            <div className="formInnerWrap">
              <div className="formElementWrap clearfix marginBottom17 pull-left">
                <div className="customSelectGroup pull-left">
                  <label for="availabilityZone">Availability Zone</label>
                  <ul className="customSelectList mandatory dropDownData ticketCustomList customer">
                    <li>Select Zone</li>
                    <li>Mumbai DC5</li>
                    <li>Mumbai DC4</li>
                    <li>Mumbai DC3</li>
                    <li>Mumbai DC2</li>
                  </ul>
                  <p className="selectContent"></p>
                </div>
                <div className="customSelectGroup pull-left">
                  <label for="serviceTerm">Service Terms</label>
                  <ul className="customSelectList mandatory dropDownData ticketCustomList customer">
                    <li>Select Term</li>
                    <li>12 Month</li>
                    <li>24 Month</li>
                    <li>36 Month</li>
                    <li>48 Month</li>
                    <li>60 Month</li>
                    <li>72 Month</li>
                  </ul>
                  <p className="selectContent"></p>
                </div>
              </div>
              <hr/>
              <p className="head1 marginBottom17">Select Plan</p>
              <div className="planWrap clearfix pull-left">
                <div className="icon clearfix pull-left">
                  <div className="plan_icon"></div>
                </div>
                <div className="customSelectGroup clearfix pull-left">
                  <label for="serviceTerm">Service Type</label>
                  <ul className="customSelectList mandatory dropDownData ticketCustomList customer">
                    <li>Advance</li>
                    <li>Service Type 2</li>
                    <li>Service Type 3</li>
                    <li>Service Type 4</li>
                  </ul>
                  <p className="selectContent"></p>
                </div>
                <div className="infopara pull-left clearfix">
                  <p>With SimpliScale, you now have a powerful, trigger-based mechanism to ramp-up or scale down hardware resources, depending on the load, without any human intervention.</p>
                </div>
                <div className='quantityWrap simpliScaleServiceName'>
                  <div className='lbNameWrap'>
                    <div className='labelInputWrap'>
                      <label>SimpliScale Service Name<span className="mandatoryStar">*</span></label>
                      <input type='text' placeholder='SimpliScaleTest1' />
                    </div>
                  </div>
                </div>
              </div>
              <hr/>
              <p className="head1 marginBottom17 clearfix">Configuration</p>
              <div className="formElementWrap pull-left marginBottom10 clearfix">
                <div className="customSelectGroup pull-left">
                  <label for="availabilityZone">Setup<span className="mandatoryStar">*</span></label>
                  <ul className="customSelectList mandatory dropDownData ticketCustomList customer">
                    <li>Select Firewall</li>
                    <li>Firewall 1</li>
                    <li>Firewall 2</li>
                    <li>Firewall 3</li>
                    <li>Firewall 4</li>
                    <li>Firewall 5</li>
                  </ul>
                  <p className="selectContent"></p>
                </div>
                <div className="customSelectGroup pull-left">
                  <label for="availabilityZone">Load Balancer <span className="mandatoryStar">*</span></label>
                  <ul className="customSelectList mandatory dropDownData ticketCustomList customer">
                    <li>Select Load Balancer</li>
                    <li>Test-LB</li>
                  </ul>
                  <p className="selectContent"></p>
                </div>
                <div className="customSelectGroup pull-left">
                  <label for="availabilityZone">Load Balancer Group<span className="mandatoryStar">*</span></label>
                  <ul className="customSelectList mandatory dropDownData ticketCustomList customer">
                    <li>Select Load Balancer Group</li>
                    <li>LBGrp1</li>
                  </ul>
                  <p className="selectContent"></p>
                </div>
              </div>
            </div>
            <div className="simplyScaleMembersListWrap">
              <h4 className="sectionHeading">Member List</h4>
              <ul className="clearfix">
                <li className="memberListHeadingWrap clearfix">
                  <div className="vmName">VM Name</div>
                  <div className="vmType">VM Name</div>
                </li>
                <li className="clearfix">
                  <div className="vmName">
                      <div className="checkboxWrap">
                        <label className="cssCheckBox">
                          <input type="checkbox" />
                          <span className="NMIcon-checkbox lightColorLabel"></span>
                        </label>
                      </div>
                      <label>RKVM1</label>
                  </div>
                  <div className="vmType">Fixed</div>
                </li>
                <li className="clearfix">
                  <div className="vmName">
                      <div className="checkboxWrap">
                        <label className="cssCheckBox">
                          <input type="checkbox" />
                          <span className="NMIcon-checkbox lightColorLabel"></span>
                        </label>
                      </div>
                      <label>VM2</label>
                  </div>
                  <div className="vmType">Elastic</div>
                </li>
              </ul>
            </div>
          </div>
          <div className="col-md-6 buyBorder padTop20 clearfix addVmConfigWrap">
            <p className="head1 marginBottom17 marTop15 clearfix">SimpliScale -  Scale - up Configuration</p>
            <div className="maxDynamicWrap clearfix">
              <div className="customSelectGroup clearfix pull-left">
                <label for="serviceTerm">Average out minutes</label>
                <ul className="customSelectList mandatory dropDownData ticketCustomList customer">
                  <li>1</li>
                  <li>2</li>
                  <li>3</li>
                  <li>4</li>
                </ul>
                <p className="selectContent"></p>
              </div>
              <div className="infopara pull-left clearfix">
                <p>This value selected is the "no. of minutes" over which the samples would be averaged and compared to the Thresholds before triggering the Scale-up / Scale-down process.</p>
              </div>
            </div>
          </div>
          <div className='productPricingWrap addSimpliscalePricing'>
            <p className="head1 marginBottom17 panelHeading">Product Pricing</p>
            <ul className='clearfix'>
              <li>
                <a href='#' className='redBorderBtn'>Calculate</a>
              </li>
              <li>
                <label>One Time Charges</label>
                <span>&#8377; 4,454.05</span>
              </li>
              <li>
                <label>Monthly Charges</label>
                <span>&#8377; 4,454.05</span>
              </li>
              <li className='blackText'>
                <label>Total</label>
                <span>&#8377; 4,454.05</span>
              </li>
            </ul>
          </div>
        </form>
      </div>
    </div>
  </section>
</div>
		)
	}
});

export default AddSimpliScaleBasic;
