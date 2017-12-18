import { Link } from 'react-router';

var AddVM = createReactClass({
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
            <li className="active">Virtual Machine</li>
          </ol>
          <h1 className="mainTitle">Add Virtual Machine</h1>
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
                    <li>Select Terms</li>
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
                  <label for="serviceTerm">Select Plan</label>
                  <ul className="customSelectList mandatory dropDownData ticketCustomList customer">
                    <li>Select Plan</li>
                    <li>Fixed Plan</li>
                    <li>monthly Plan</li>
                    <li>Yearly Plan</li>
                  </ul>
                  <p className="selectContent"></p>
                </div>
                <div className="infopara pull-left clearfix">
                  <p>Fixed plans are ideal for customers who's requirements are more stable than Elastic Fixed plans provide stable monthly billing at a flat free. <a>Read More</a></p>
                </div>
              </div>
              <hr/>
              <p className="head1 marginBottom17 clearfix">Configure your plan</p>
              <div className="formElementWrap pull-left marginBottom10 clearfix">
                <div className="customSelectGroup pull-left">
                  <label for="availabilityZone">VM Provision from <span className="mandatoryStar">*</span></label>
                  <ul className="customSelectList mandatory dropDownData ticketCustomList customer">
                    <li>Select VM Provision from</li>
                    <li>Default Template1</li>
                    <li>Default Template2</li>
                    <li>Default Template3</li>
                    <li>Default Template4</li>
                    <li>Default Template5</li>
                  </ul>
                  <p className="selectContent"></p>
                </div>
                <div className="customSelectGroup pull-left">
                  <label for="availabilityZone">OS Type <span className="mandatoryStar">*</span></label>
                  <ul className="customSelectList mandatory dropDownData ticketCustomList customer">
                    <li>Select OS type</li>
                    <li>Linux - 64 Bit</li>
                    <li>Linux - 32 Bit</li>
                    <li>Windows - 64 Bit</li>
                    <li>Windows - 32 Bit</li>
                  </ul>
                  <p className="selectContent"></p>
                </div>
                <div className="customSelectGroup pull-left">
                  <label for="availabilityZone">OS Edition <span className="mandatoryStar">*</span></label>
                  <ul className="customSelectList mandatory dropDownData ticketCustomList customer">
                    <li>Select OS Edition</li>
                    <li>Cent OS 5.5 64 Bit</li>
                    <li>Cent OS 6.5 32 Bit</li>
                    <li>Cent OS 4.5 64 Bit</li>
                    <li>Cent OS 5.5 64 Bit</li>
                  </ul>
                  <p className="selectContent"></p>
                </div>
              </div>
              <hr/>
              <div className="planInfo clearfix addVMPlanInfo">
                <p className="configPlanHeadings">
                  <span className="radioBtnWrap">&nbsp;</span>
                  <span className="planName">Plan Name</span>
                  <span className="planCore">Core (s)</span>
                  <span className="planMemory">Memory</span>
                  <span className="planDisk">Disk</span>
                  <span className="planBandwidth">Bandwidth</span>
                  <span className="planStartingAt">Starting at</span>
                </p>
                <ul>
                  <li className="clearfix selectedPlan">
                    <input name="selectPlan" className="radioBtnWrap" checked type="radio"/>
                    <span className="planName">Small Fixed - GB</span>
                    <span className="planCore">1</span>
                    <span className="planMemory">1 GB</span>
                    <span className="planDisk">100 GB</span>
                    <span className="planBandwidth">10 GB</span>
                    <span className="planStartingAt">Rs. 3590/Mth</span>
                  </li>
                  <li className="clearfix">
                    <input name="selectPlan" className="radioBtnWrap" type="radio"/>
                    <span className="planName">Medium Fixed - GB</span>
                    <span className="planCore">1</span>
                    <span className="planMemory">1 GB</span>
                    <span className="planDisk">100 GB</span>
                    <span className="planBandwidth">10 GB</span>
                    <span className="planStartingAt">Rs. 3590/Mth</span>
                  </li>
                  <li className="clearfix">
                    <input name="selectPlan" className="radioBtnWrap" type="radio"/>
                    <span className="planName">Medium MI 16 Fixed - GB</span>
                    <span className="planCore">1</span>
                    <span className="planMemory">1 GB</span>
                    <span className="planDisk">100 GB</span>
                    <span className="planBandwidth">10 GB</span>
                    <span className="planStartingAt">Rs. 3590/Mth</span>
                  </li>
                  <li className="clearfix">
                    <input name="selectPlan" className="radioBtnWrap" type="radio"/>
                    <span className="planName">Large Fixed - GB</span>
                    <span className="planCore">1</span>
                    <span className="planMemory">1 GB</span>
                    <span className="planDisk">100 GB</span>
                    <span className="planBandwidth">10 GB</span>
                    <span className="planStartingAt">Rs. 3590/Mth</span>
                  </li>
                  <li className="clearfix">
                    <input name="selectPlan" className="radioBtnWrap" type="radio"/>
                    <span className="planName">Large MI 24 Fixed - GB</span>
                    <span className="planCore">1</span>
                    <span className="planMemory">1 GB</span>
                    <span className="planDisk">100 GB</span>
                    <span className="planBandwidth">10 GB</span>
                    <span className="planStartingAt">Rs. 3590/Mth</span>
                  </li>
                </ul>
              </div>
            </div>
          </div>
          <div className="col-md-6 buyBorder padTop20 clearfix addVmConfigWrap">
            <div className="formElementWrap pull-left marginBottom10 clearfix">
              <div className="customSelectGroup pull-left clearfix">
                <label for="availabilityZone">Disk Space (GB)</label>
                <ul className="customSelectList mandatory dropDownData ticketCustomList customer">
                  <li>Select Disk Space</li>
                  <li>100</li>
                  <li>200</li>
                  <li>300</li>
                </ul>
                <p className="selectContent"></p>
              </div>
              <div className="customSelectGroup pull-left clearfix">
                <label for="availabilityZone">Bandwidth</label>
                <ul className="customSelectList mandatory dropDownData ticketCustomList customer">
                  <li>Select Bandwidth</li>
                  <li>40</li>
                  <li>50</li>
                  <li>60</li>
                </ul>
                <p className="selectContent"></p>
              </div>
              <div className="leftSelectGroup customSelectGroup pull-left clearfix">
                <label for="availabilityZone">Managed By</label>
                <ul className="customSelectList mandatory dropDownData ticketCustomList customer">
                  <li>Select Manage By</li>
                  <li>Self</li>
                  <li>Netmagic</li>
                </ul>
                <p className="selectContent"></p>
              </div>
            </div>
            <div className="clearfix"></div>
            <p className="head1 marginBottom17 marTop15 clearfix">Configuration</p>
            <div className="formElementWrap pull-left marginBottom10 clearfix">
              <div className="customSelectGroup pull-left">
                <label for="availabilityZone">CPU (V Core)</label>
                <ul className="customSelectList mandatory dropDownData ticketCustomList customer">
                  <li>Select CPU</li>
                  <li>8</li>
                  <li>16</li>
                  <li>32</li>
                </ul>
                <p className="selectContent"></p>
              </div>
              <div className="customSelectGroup pull-left clearfix">
                <label for="availabilityZone">Memory (GB)</label>
                <ul className="customSelectList mandatory dropDownData ticketCustomList customer">
                  <li>Select Momory</li>
                  <li>40</li>
                  <li>50</li>
                  <li>60</li>
                </ul>
                <p className="selectContent"></p>
              </div>
              <div className="leftSelectGroup customSelectGroup pull-left clearfix">
                <label for="availabilityZone">Setup <span className="mandatoryStar">*</span></label>
                <ul className="customSelectList mandatory dropDownData ticketCustomList customer">
                  <li>Select Firewall</li>
                  <li>Self</li>
                  <li>Netmagic</li>
                </ul>
                <p className="selectContent"></p>
              </div>
            </div>
            <div className="clearfix"></div>
            <p className="head1 marginBottom17 marTop15 clearfix">Disk Partition(s) - <span className="subhead1">Total Disk: 100GB/Total IOPS:300</span></p>
            <div className="diskConfig clearfix">
              <div className="formInputWrap clearfix">
                <div className="formElementWrap pull-left clearfix">
                  <label>Swap - GB</label>
                  <input type="text" name="text1" value="4"/>
                  <span className="info_icon"></span> </div>
                <div className="formElementWrap pull-left clearfix">
                  <label>/tmp - GB</label>
                  <input type="text" name="text2" value="8"/>
                  <span className="info_icon"></span> </div>
                <div className="formElementWrap pull-left clearfix">
                  <label>/var/tmp - GB</label>
                  <input type="text" name="text3" value="8"/>
                  <span className="info_icon"></span> </div>
                <div className="formElementWrap pull-left clearfix">
                  <label>/- GB</label>
                  <input type="text" name="text4" value="80"/>
                  <span className="info_icon"></span> </div>
                <div className="formElementleft formElementWrap pull-left">
                  <label>IOPS</label>
                  <input type="text" name="text5" value="0"/>
                </div>
              </div>
              <div className="diskConfig marginBottom17 marTop10 clearfix">
                <div className="formInputWrap clearfix">
                  <div className="formElementWrap pull-left clearfix">
                    <label>/mmt/vol1</label>
                    <input type="text" name="text1" value="8"/>
                  </div>
                  <div className="formElementWrap pull-left clearfix">
                    <label>/mmt/vol2</label>
                    <input type="text" name="text2" value="80"/>
                  </div>
                  <div className="formElementWrap pull-left clearfix">
                    <label>/mmt/vol3</label>
                    <input type="text" name="text3" value="8"/>
                  </div>
                  <div className="formElementWrap pull-left clearfix">
                    <label>/mmt/vol4</label>
                    <input type="text" name="text4" value="80"/>
                  </div>
                  <div className="formElementleft formElementWrap pull-left">
                    <label>IOPS</label>
                    <input type="text" name="text5" value="0"/>
                  </div>
                </div>
              </div>
  			      <div className="clearfix"></div>
  			      <p className="head1 marginBottom17 marTop15 clearfix">Additional Details -
                <span className="subhead1">Install additional packages</span>
              </p>
  			      <button className="packBtn">+ Click to add packages</button>
              <div className="additonalQuantityWrap clearfix">
                <div className="additionalQauntity pull-left">
                  <label>Quantity</label>
                  <input type="text" value="1"/>
                </div>
                <div className='quantityWrap zoneNameWrap pull-right virtualMachineNameWrap'>
                  <div className='lbNameWrap'>
                    <div className='labelInputWrap'>
                      <label>Virtual Machine  Name<span className="mandatoryStar">*</span></label>
                      <input type='text' placeholder='Instance Name' />
                    </div>
                    <div className="applyVirtualNameWrap">
                      <div className="checkboxWrap pull-left">
                        <label className="cssCheckBox">
                          <input type="checkbox" />
                          <span className="NMIcon-checkbox lightColorLabel"></span>
                        </label>
                      </div>
                      <span>Apply name for Virtual Machine (s)</span>
                      <a href="javascript:void(0)" className="blueLink pull-right">Rename</a>
                    </div>
                  </div>
                </div>
  			      </div>
            </div>

          </div>
          <div className='productPricingWrap'>
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
                <label>One Time Charges</label>
                <span>&#8377; 4,454.05</span>
              </li>
              <li className='blackText'>
                <label>Total (without Tax.)</label>
                <span>&#8377; 4,454.05</span>
              </li>
            </ul>
            <div className="variableCharges clearfix hide">
              <h4>Variable Charges</h4>
              <ul className="clearfix">
                <li>
                  <label>Data Transfer </label>
                  <span>&#8377; 54 / GB</span>
                </li>
              </ul>
            </div>
          </div>
        </form>
      </div>
    </div>
  </section>
</div>
		)
	}
});

export default AddVM;
