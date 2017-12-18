import { Link } from 'react-router';

var AddLB = createReactClass({
	render () {
		return (
			<div className="ticketListSection outStandingWrap peopleWrap addContactWrap">
  <section className="midHeader">
    <div className="container">
      <div className="row">
        <div className="col-xs-9">
          <ol className="breadcrumb breadCrumbCustom clearfix">
            <li><a href="javascript:void(0);">Back to Dashboard</a></li>
            <li><a href="javascript:void(0);">Cloud Portal</a></li>
            <li><a href="javascript:void(0);">Add New Infra</a></li>
            <li className="active">Virtual Machine</li>
          </ol>
          <h1 className="mainTitle">Add Virtual Machine</h1>
        </div>
        <div className="col-xs-3"> <a href="javascript:void(0);" className="whiteCancelBtn pull-right">Cancel</a> <a href="javascript:void(0);" className="darkPinkBtn pull-right">Confirm</a> </div>
      </div>
    </div>
  </section>
  <section className="buyOptionWrap">
    <div className="container">
      <div className="row">
        <form className="buyOptionForm addFormWrapper addLoadBalancer">
          <div className="col-md-6">
            <div className="formInnerWrap">
              <div className="formElementWrap clearfix marginBottom17 pull-left">
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
                <div className="customSelectGroup pull-left">
                  <label for="availabilityZone">Load Balancer Type</label>
                  <ul className="customSelectList mandatory dropDownData ticketCustomList customer">
                    <li>VLB</li>
                  </ul>
                  <p className="selectContent"></p>
                </div>
              </div>
              <hr/>
              <div className="planInfo clearfix">
                <ul>
                  <li className="formTHead clearfix">
                    <span className="wid30">Plan Name</span> <span className="wid10">HTTP</span> <span className="wid10">TCP</span> <span className="wid10">SSL</span> <span className="wid13">Conn / Sec</span> <span>Starting at</span>                    
                    </li>
                    <li className="clearfix active">
                    <input name="selectPlan" type="radio"/>
                    <span className="wid30">Small</span> <span className="wid10">Yes</span> <span className="wid10">Yes</span> <span className="wid10">100 GB</span> <span className="wid13">10 GB</span> <span>Rs. 3590/Mth</span> <span className="accordianBtn active"><span></span></span>
                    <div className='accordianContentWrap'>
	                    <hr />
	                    <div className="checkboxWrap">
	                    	<label className="cssCheckBox">
		                    	<input type="checkbox" />
		                    	<span className="NMIcon-checkbox lightColorLabel"></span>
	                    	</label>
                    	</div>
	                    <label>Enable HA</label>
                    </div>
                    </li>
                  <li className="clearfix">
                    <input name="selectPlan" type="radio"/>
                    <span className="wid30">Medium</span> <span className="wid10">Yes</span> <span className="wid10">Yes</span> <span className="wid10">200 GB</span> <span className="wid13">20 GB</span> <span>Rs. 5690/Mth</span>
                  	<div className='accordianContentWrap'>
	                    <hr />
	                    <div className="checkboxWrap">
	                    	<label className="cssCheckBox">
		                    	<input type="checkbox" />
		                    	<span className="NMIcon-checkbox lightColorLabel"></span>
	                    	</label>
                    	</div>
	                    <label>Enable HA</label>
                    </div>
                  </li>
                  <li className="clearfix">
                    <input name="selectPlan" type="radio"/>
                    <span className="wid30">Large</span> <span className="wid10">Yes</span> <span className="wid10">Yes</span> <span className="wid10">100 GB</span> <span className="wid13">20 GB</span> <span>Rs. 11290/Mth</span>
                  	<div className='accordianContentWrap'>
	                    <hr />
	                    <div className="checkboxWrap">
	                    	<label className="cssCheckBox">
		                    	<input type="checkbox" />
		                    	<span className="NMIcon-checkbox lightColorLabel"></span>
	                    	</label>
                    	</div>
	                    <label>Enable HA</label>
                    </div>
                  </li>
                  <li className="clearfix">
                    <input name="selectPlan" type="radio"/>
                    <span className="wid30">XL</span> <span className="wid10">Yes</span> <span className="wid10">Yes</span> <span className="wid10">100 GB</span> <span className="wid13">40 GB</span> <span>Rs. 9590/Mth</span>
                  	<div className='accordianContentWrap'>
	                    <hr />
	                    <div className="checkboxWrap">
	                    	<label className="cssCheckBox">
		                    	<input type="checkbox" />
		                    	<span className="NMIcon-checkbox lightColorLabel"></span>
	                    	</label>
                    	</div>
	                    <label>Enable HA</label>
                    </div>
               	  </li>
                  <li className="clearfix">
                    <input name="selectPlan" type="radio"/>
                    <span className="wid30">2XL</span> <span className="wid10">Yes</span> <span className="wid10">Yes</span> <span className="wid10">100 GB</span> <span className="wid13">40 GB</span> <span>Rs. 17590/Mth</span>
                    <div className='accordianContentWrap'>
	                    <hr />
	                    <div className="checkboxWrap">
	                    	<label className="cssCheckBox">
		                    	<input type="checkbox" />
		                    	<span className="NMIcon-checkbox lightColorLabel"></span>
	                    	</label>
                    	</div>
	                    <label>Enable HA</label>
                    </div>
                  </li>
                </ul>
              </div>
            </div>
          </div>
          <div className="col-md-6 buyBorder padTop20 clearfix whiteBg">
            <p className="head1 marginBottom17 marTop15 clearfix">Configuration</p>
            <div className="formElementWrap pull-left marginBottom10 clearfix">
              <div className="customSelectGroup pull-left">
                <label for="availabilityZone">Setup <span className="mandatoryStar">*</span></label>
                <ul className="customSelectList mandatory dropDownData ticketCustomList customer">
                  <li>Select Firewall</li>
                  <li>8</li>
                  <li>16</li>
                  <li>32</li>
                </ul>
                <p className="selectContent"></p>
              </div>
            </div>
            <div className="clearfix"></div>
            <div className='quantityWrap'>
            <hr />
            	<div className='quantityDiv pull-left'>
            		<label>Quantity</label>
            		<input type='text' placeholder='1'/>
            	</div>
            	<div className='lbNameWrap pull-right'>
            		<div className='labelInputWrap'>
            			<label>Load Balancer Name <span className="mandatoryStar">*</span></label>
            			<input type='text' placeholder='Instance Name' />
            		</div>
            		<div className="checkboxWrap">
                    	<label className="cssCheckBox">
	                    	<input type="checkbox" />
	                    	<span className="NMIcon-checkbox lightColorLabel"></span>
                    	</label>
                	</div>
            		<label>Apply name for Load Balancer (s)</label>
            		<a href='#' className='whiteCancelBtn pull-right'>Rename</a>
            		<div className='clearfix'></div>
            	</div>
            </div>
            <div className='clearfix'></div>
          </div>
          <div className='productPricingWrap'>
          	<p className="head1 marginBottom17 marTop15 clearfix">Product Pricing</p>
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
          			<label>Total (Inc.Tax.)</label>
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


export default AddLB;