import { Link } from 'react-router';

var AddFirewall = createReactClass({
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
            <li className="active">Firewall</li>
          </ol>
          <h1 className="mainTitle">Add Firewall</h1>
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
                  <label for="availabilityZone">Firewall Type</label>
                  <ul className="customSelectList mandatory dropDownData ticketCustomList customer">
                    <li>VFA</li>
                  </ul>
                  <p className="selectContent"></p>
                </div>
              </div>
              <p className="billingTypeWrap"><label>Billing Type</label> GB</p>
              <div className="planInfo clearfix">
                <ul>
                  <li className="formTHead clearfix">
                    <span className="planName">Plan Name</span>
                    <span className="haInfo">H.A.</span>
                    <span className="ipsecVpn">IPSEC VPN</span>
                    <span>Starting at</span>
                  </li>
                  <li className="clearfix active">
                    <input name="selectPlan" type="radio"/>
                    <span className="planName">Small</span>
                    <span className="haInfo">Not Available</span>
                    <span className="ipsecVpn">Not Available</span>
                    <span>Rs. 5690 / Mth</span>
                    <span className="accordianBtn active"><span></span></span>
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
                    <span className="planName">Medium</span>
                    <span className="haInfo">Not Available</span>
                    <span className="ipsecVpn">Not Available</span>
                    <span>Rs. 5690 / Mth</span>
                    <span className="accordianBtn"><span></span></span>
                    <div className='accordianContentWrap hide'>
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
                    <span className="planName">Large</span>
                    <span className="haInfo">Yes</span>
                    <span className="ipsecVpn">Available</span>
                    <span>Rs. 5690 / Mth</span>
                    <span className="accordianBtn"><span></span></span>
                    <div className='accordianContentWrap hide'>
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
                    <span className="planName">XL</span>
                    <span className="haInfo">Not Available</span>
                    <span className="ipsecVpn">Not Available</span>
                    <span>Rs. 5690 / Mth</span>
                    <span className="accordianBtn"><span></span></span>
                    <div className='accordianContentWrap hide'>
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
                    <span className="planName">2 XL</span>
                    <span className="haInfo">Yes</span>
                    <span className="ipsecVpn">Available</span>
                    <span>Rs. 5690 / Mth</span>
                    <span className="accordianBtn"><span></span></span>
                    <div className='accordianContentWrap hide'>
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
             <div className="formElementWrap clearfix marginBottom17 pull-left">
                <div className="customSelectGroup pull-left">
                  <label for="availabilityZone">No. of  Zone (s) <span className="mandatoryStar">*</span></label>
                  <ul className="customSelectList mandatory dropDownData ticketCustomList customer">
                    <li>1</li>
                    <li>2</li>
                    <li>3</li>
                    <li>4</li>
                  </ul>
                  <p className="selectContent"></p>
                </div>
                <div className="customSelectGroup pull-left">
                  <label for="serviceTerm">IP Type <span className="mandatoryStar">*</span></label>
                  <ul className="customSelectList mandatory dropDownData ticketCustomList customer">
                    <li>IPv6</li>
                  </ul>
                  <p className="selectContent"></p>
                </div>
                <div className="customSelectGroup pull-left">
                  <label for="availabilityZone">IP Address Config <span className="mandatoryStar">*</span></label>
                  <ul className="customSelectList mandatory dropDownData ticketCustomList customer">
                    <li>Standard</li>
                  </ul>
                  <p className="selectContent"></p>
                </div>
              </div>
            <div className="clearfix"></div>
            <div className="zoneDetailsWrap">
              <p className="head1">Zone Details</p>
              <ul className="list-unstyled zoneListing">
                <li className="clearfix active">
                    <a href="javascript:void(0)">Zone 1
                      <span className="accordianBtn active pull-right"><span></span></span>
                      </a>
                    <div className="clearfix"></div>
                    <div className="zoneInfoWrap clearfix">
                      <div className='quantityWrap zoneNameWrap pull-left'>
                        <div className='lbNameWrap'>
                          <div className='labelInputWrap'>
                            <label>Zone Name<span className="mandatoryStar">*</span></label>
                            <input type='text' placeholder='App Zone' />
                          </div>
                        </div>
                      </div>
                      <div className="customSelectGroup pull-left ipAddressRange">
                        <label for="ipAddressRange">IP Address Range<span className="mandatoryStar">*</span></label>
                        <ul className="customSelectList mandatory dropDownData ticketCustomList customer">
                          <li>Small (/24 - 256 IP’s)</li>
                        </ul>
                        <p className="selectContent"></p>
                      </div>
                    </div>
                    <div className="zoneDropdownInfoWrap createIpsWrap clearfix">
                      <div className="createIPVWrap hide">
                        <ul className="clearfix">
                          <li>
                            <label>VM NIC - Internet</label>
                            <div className="formElementWrap clearfix marginBottom17 pull-left">
                              <div className="customSelectGroup pull-left">
                                <label for="ipAddressSpace">IP Address Space</label>
                                <ul className="customSelectList mandatory dropDownData ticketCustomList customer">
                                  <li>10.0.0.0</li>
                                  <li>10.0.0.1</li>
                                  <li>10.0.0.2</li>
                                  <li>10.0.0.3</li>
                                </ul>
                                <p className="selectContent"></p>
                              </div>
                              <div className="attachedSecondColom">
                                  <label>Start IP Address</label>
                                  <input type="text" placeholder="10.1.1.0"/>
                              </div>
                              <div className="attachedSecondColom attachedThirdColom">
                                  <label>Range</label>
                                  <input type="text" placeholder="10.1.1.0-10.1.1.255"/>
                              </div>
                            </div>
                          </li>
                          <li>
                            <label>VM NIC - Private</label>
                            <div className="formElementWrap clearfix marginBottom17 pull-left">
                              <div className="customSelectGroup pull-left">
                                <label for="ipAddressSpace">IP Address Space</label>
                                <ul className="customSelectList mandatory dropDownData ticketCustomList customer">
                                  <li>10.0.0.0</li>
                                  <li>10.0.0.1</li>
                                  <li>10.0.0.2</li>
                                  <li>10.0.0.3</li>
                                </ul>
                                <p className="selectContent"></p>
                              </div>
                              <div className="attachedSecondColom">
                                  <label>Start IP Address</label>
                                  <input type="text" placeholder="10.1.1.0"/>
                              </div>
                              <div className="attachedSecondColom attachedThirdColom">
                                  <label>Range</label>
                                  <input type="text" placeholder="10.1.1.0-10.1.1.255"/>
                              </div>
                            </div>
                          </li>
                          <li>
                            <label>VM NIC - Storage</label>
                            <div className="formElementWrap clearfix marginBottom17 pull-left">
                              <div className="customSelectGroup pull-left">
                                <label for="ipAddressSpace">IP Address Space</label>
                                <ul className="customSelectList mandatory dropDownData ticketCustomList customer">
                                  <li>10.0.0.0</li>
                                  <li>10.0.0.1</li>
                                  <li>10.0.0.2</li>
                                  <li>10.0.0.3</li>
                                </ul>
                                <p className="selectContent"></p>
                              </div>
                              <div className="attachedSecondColom">
                                  <label>Start IP Address</label>
                                  <input type="text" placeholder="10.1.1.0"/>
                              </div>
                              <div className="attachedSecondColom attachedThirdColom">
                                  <label>Range</label>
                                  <input type="text" placeholder="10.1.1.0-10.1.1.255"/>
                              </div>
                            </div>
                          </li>
                          <li>
                            <label>VM NIC - Backup</label>
                            <div className="formElementWrap clearfix marginBottom17 pull-left">
                              <div className="customSelectGroup pull-left">
                                <label for="ipAddressSpace">IP Address Space</label>
                                <ul className="customSelectList mandatory dropDownData ticketCustomList customer">
                                  <li>10.0.0.0</li>
                                  <li>10.0.0.1</li>
                                  <li>10.0.0.2</li>
                                  <li>10.0.0.3</li>
                                </ul>
                                <p className="selectContent"></p>
                              </div>
                              <div className="attachedSecondColom">
                                  <label>Start IP Address</label>
                                  <input type="text" placeholder="10.1.1.0"/>
                              </div>
                              <div className="attachedSecondColom attachedThirdColom">
                                  <label>Range</label>
                                  <input type="text" placeholder="10.1.1.0-10.1.1.255"/>
                              </div>
                            </div>
                          </li>
                        </ul>
                      </div>

                      <div className="dualStackTabs">
                        <ul className="clearfix dualTabs">
                          <li className="active"><a href="javascript:void(0)">IPv4</a></li>
                          <li><a href="javascript:void(0)">IPv6</a></li>
                        </ul>
                        <div className="ipvData hide">
                          <ul>
                            <li>
                              <label>VM NIC - Internet</label>
                              <div className="formElementWrap clearfix">
                                <div className="customSelectGroup pull-left">
                                  <label for="ipAddressSpace">IP Address Space</label>
                                  <ul className="customSelectList mandatory dropDownData ticketCustomList customer">
                                    <li>10.0.0.0</li>
                                    <li>10.0.0.1</li>
                                    <li>10.0.0.2</li>
                                    <li>10.0.0.3</li>
                                  </ul>
                                  <p className="selectContent"></p>
                                </div>
                                <div className="attachedSecondColom">
                                    <label>Start IP Address</label>
                                    <input type="text" placeholder="10.1.1.0"/>
                                </div>
                                <div className="attachedSecondColom attachedThirdColom">
                                    <label>Range</label>
                                    <input type="text" placeholder="10.1.1.0-10.1.1.255"/>
                                </div>
                              </div>
                            </li>
                            <li>
                              <label>VM NIC - Private</label>
                              <div className="formElementWrap clearfix marginBottom17 pull-left">
                                <div className="customSelectGroup pull-left">
                                  <label for="ipAddressSpace">IP Address Space</label>
                                  <ul className="customSelectList mandatory dropDownData ticketCustomList customer">
                                    <li>10.0.0.0</li>
                                    <li>10.0.0.1</li>
                                    <li>10.0.0.2</li>
                                    <li>10.0.0.3</li>
                                  </ul>
                                  <p className="selectContent"></p>
                                </div>
                                <div className="attachedSecondColom">
                                    <label>Start IP Address</label>
                                    <input type="text" placeholder="10.1.1.0"/>
                                </div>
                                <div className="attachedSecondColom attachedThirdColom">
                                    <label>Range</label>
                                    <input type="text" placeholder="10.1.1.0-10.1.1.255"/>
                                </div>
                              </div>
                            </li>
                            <li>
                              <label>VM NIC - Storage</label>
                              <div className="formElementWrap clearfix marginBottom17 pull-left">
                                <div className="customSelectGroup pull-left">
                                  <label for="ipAddressSpace">IP Address Space</label>
                                  <ul className="customSelectList mandatory dropDownData ticketCustomList customer">
                                    <li>10.0.0.0</li>
                                    <li>10.0.0.1</li>
                                    <li>10.0.0.2</li>
                                    <li>10.0.0.3</li>
                                  </ul>
                                  <p className="selectContent"></p>
                                </div>
                                <div className="attachedSecondColom">
                                    <label>Start IP Address</label>
                                    <input type="text" placeholder="10.1.1.0"/>
                                </div>
                                <div className="attachedSecondColom attachedThirdColom">
                                    <label>Range</label>
                                    <input type="text" placeholder="10.1.1.0-10.1.1.255"/>
                                </div>
                              </div>
                            </li>
                            <li>
                              <label>VM NIC - Backup</label>
                              <div className="formElementWrap clearfix marginBottom17 pull-left">
                                <div className="customSelectGroup pull-left">
                                  <label for="ipAddressSpace">IP Address Space</label>
                                  <ul className="customSelectList mandatory dropDownData ticketCustomList customer">
                                    <li>10.0.0.0</li>
                                    <li>10.0.0.1</li>
                                    <li>10.0.0.2</li>
                                    <li>10.0.0.3</li>
                                  </ul>
                                  <p className="selectContent"></p>
                                </div>
                                <div className="attachedSecondColom">
                                    <label>Start IP Address</label>
                                    <input type="text" placeholder="10.1.1.0"/>
                                </div>
                                <div className="attachedSecondColom attachedThirdColom">
                                    <label>Range</label>
                                    <input type="text" placeholder="10.1.1.0-10.1.1.255"/>
                                </div>
                              </div>
                            </li>
                        </ul>
                        </div>
                        <div className="ipvData ipvSixDataWrap">
                          <ul>
                            <li>
                              <label>VM NIC - Internet</label>
                              <div className="formElementWrap clearfix">
                                <div className="customSelectGroup pull-left">
                                  <label for="ipAddressSpace">Public IPv6 Owner</label>
                                  <ul className="customSelectList mandatory dropDownData ticketCustomList customer">
                                    <li>Self</li>
                                  </ul>
                                  <p className="selectContent"></p>
                                </div>
                                <div className="attachedSecondColom bigIpAddressWrap">
                                    <label>Start IP Address</label>
                                    <input type="text" placeholder="2001:db80:0000:0000:0000:0000:0000"/>
                                </div>
                              </div>
                            </li>
                            <li>
                              <label>VM NIC - Private</label>
                              <div className="formElementWrap clearfix">
                                <div className="attachedSecondColom bigIpAddressWrap privateStartIPAddress">
                                    <label>Start IP Address</label>
                                    <input type="text" placeholder="fc00:0000:0000:0000:0000:0000:0000:0000"/>
                                </div>
                              </div>
                            </li>
                            <li>
                              <label>VM NIC - Storage</label>
                              <div className="formElementWrap clearfix">
                                <div className="attachedSecondColom bigIpAddressWrap privateStartIPAddress">
                                    <label>Start IP Address</label>
                                    <input type="text" placeholder="fc00:0000:0000:0000:0000:0000:0000:0000"/>
                                </div>
                              </div>
                            </li>
                            <li>
                              <label>VM NIC - Backup</label>
                              <div className="formElementWrap clearfix">
                                <div className="attachedSecondColom bigIpAddressWrap privateStartIPAddress">
                                    <label>Start IP Address</label>
                                    <input type="text" placeholder="fc00:0000:0000:0000:0000:0000:0000:0000"/>
                                </div>
                              </div>
                            </li>
                          </ul>
                        </div>
                      </div>
                    </div>
                </li>
                <li>
                    <a href="javascript:void(0)">Zone 2
                      <span className="accordianBtn pull-right"><span></span></span>
                    </a>
                </li>
              </ul>
            </div>

            <div className="firewallNameWrap">
              <div className='quantityWrap zoneNameWrap pull-left'>
                <div className='lbNameWrap'>
                  <div className='labelInputWrap'>
                    <label>Firewall Name<span className="mandatoryStar">*</span></label>
                    <input type='text' placeholder='Instance Name' />
                  </div>
                </div>
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


export default AddFirewall;
