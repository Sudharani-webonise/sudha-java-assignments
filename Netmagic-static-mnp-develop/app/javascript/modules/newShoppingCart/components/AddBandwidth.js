import { Link } from 'react-router';

var AddBandwidth = createReactClass({
	render () {
		return (
	<div className="addBandwidthWrap">
    <section className="midHeader">
      <div className="container">
        <div className="row">
          <div className="col-xs-9">
            <ol className="breadcrumb breadCrumbCustom clearfix">
              <li><a href="javascript:void(0);">Back to Dashboard</a></li>
              <li><a href="javascript:void(0);">Shopping Cart</a></li>
              <li className="active">Add Bandwidth</li>
            </ol>
            <h1 className="mainTitle">Add Bandwidth</h1>
          </div>
          <div className="col-xs-3">  <a href="javascript:void(0);" className="darkPinkBtn pull-right">Add to Cart</a> </div>
        </div>
      </div>
    </section>
    <section className="buyOptionWrap">
      <div className="container">
        <div className="row">
          <form className="buyOptionForm addFormWrapper">
            <div className="col-md-6">
              <div className="formInnerWrap">
                <div className="formElementWrap clearfix marginBottom17">
                  <div className="customSelectGroup pull-left">
                    <label for="availabilityZone">Availability Zone</label>
                    <ul className="customSelectList mandatory dropDownData ticketCustomList customer">
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
                      <li>12 Month</li>
                      <li>24 Month</li>
                      <li>36 Month</li>
                      <li>48 Month</li>
                      <li>60 Month</li>
                      <li>72 Month</li>
                    </ul>
                    <p className="selectContent"></p>
                  </div>
                  <div className='prevContractWrap pull-left'>
                      <label>Previous Contract</label>
                      <span>120 GB</span>
                  </div>
                </div>
                <div className="planWrap clearfix selectPlanWrap">
                  <h4>Select Plan</h4>
                  <div className="icon clearfix pull-left">
                    <div className="plan_icon"></div>
                  </div>
                  <div className="customSelectGroup clearfix pull-left">
                    <label for="serviceTerm">Select Product</label>
                    <ul className="customSelectList mandatory dropDownData ticketCustomList customer">
                      <li>Select Product</li>
                      <li>Rate Based Bandwidth</li>
                    </ul>
                    <p className="selectContent"></p>
                  </div>
                  <div className="customSelectGroup clearfix pull-left">
                    <label for="serviceTerm">Select Plan</label>
                    <ul className="customSelectList mandatory dropDownData ticketCustomList customer">
                      <li>Select Plan</li>
                      <li>Burstable</li>
                    </ul>
                    <p className="selectContent"></p>
                  </div>
                </div>
                <div className="clearfix"></div>
                <div className="planInfo clearfix configurePlanWrap">
                  <h4>Configure Your Plan</h4>
                  <ul>
                    <li className="clearfix active">
                      <input name="selectPlan" type="radio" checked/>
                      <a href="javascript:void(0)" className="blueLink">Brustable - GB</a>
                    </li>
                  </ul>
                </div>
              </div>
            </div>
            <div className="col-md-6 bandwidthConfigrationWrap">
              <h4 className="panelHeading">Configuration</h4>
              <div className="boostableBandwidth">
                <label>Burstable Bandwidth</label>
                <span>NSSRBBBSTIBB - UoM - GB</span>
              </div>
              <div className="baseValueAttrWrap clearfix">
                <div className="baseValueWrap pull-left">
                  <label>Base Value</label>
                  <input type="text" value="1"/>
                </div>
                <div className="attrNameWrap pull-left">
                  <label>Attribute Name</label>
                  <span>Burstability</span>
                </div>
                <div className="customSelectGroup clearfix pull-left">
                  <label for="serviceTerm">Attribute Value</label>
                  <ul className="customSelectList mandatory dropDownData ticketCustomList customer">
                    <li>Burst Upto Mbps</li>
                  </ul>
                  <p className="selectContent"></p>
                </div>
              </div>
            </div>
            <div className='productPricingWrap'>
            	<p className="head1 marginBottom17 panelHeading">Product Pricing <span>Per Quantity</span></p>
            	<ul className='clearfix'>
                <li>
                  <a href='#' className='redBorderBtn'>Calculate</a>
                </li>
                <li>
                  <label>One Time Charges</label>
                  <span>&#8377; 4,454.05</span>
                </li>
                <li>
                  <label>One Time Charges</label>
                  <span>&#8377; 4,454.05</span>
                </li>
                <li className='blackText'>
                  <label>Total (Without Tax)</label>
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


export default AddBandwidth;
