import { Link } from 'react-router';

var FirewallSummary = createReactClass({
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
            <h1 className="mainTitle">Firewall - Summary</h1>
          </div>
          <div className="col-xs-3"> <a href="javascript:void(0);" className="whiteCancelBtn pull-right">Cancel</a> <a href="javascript:void(0);" className="darkPinkBtn pull-right">Confirm</a> </div>
        </div>
      </div>
    </section>
  <section className="buyOptionWrap">
    <div className="container">
      <div className="row">
        <form className="buyOptionForm addFormWrapper firewallSummary">
          <div className="col-md-8 leftColumn">
            <div className='whiteBoxBorder'>
              <h4>Product Details</h4>
              <ul className='productDetails clearfix'>
                <li>
                  <label>Availability Zone</label>
                  <span>Mumbai DC 5</span>
                </li>
                <li>
                  <label>Service Terms</label>
                  <span>12 Months</span>
                </li>
                <li>
                  <label>Firewall Type</label>
                  <span>VFA</span>
                </li>
                <li>
                  <label>Billing Type</label>
                  <span>GB</span>
                </li>
                <li className='wid50'>
                  <label>Plan</label>
                  <span>Medium</span>
                </li>
                <li className='wid50'>
                  <label>HA</label>
                  <span>Enabled</span>
                </li>
              </ul>
            </div>
            <div className='whiteBoxBorder'>
              <h4>Configuration Details</h4>
              <ul className='productDetails configurationDetails clearfix'>
                <li>
                  <label>No. of Zone (s)</label>
                  <span>2</span>
                </li>
                <li className='configIpType'>
                  <label>IP Type</label>
                  <span>Dual Stack (IPv4 + IPv6)</span>
                </li>
                <li className="configPublcIP">
                  <label>Public IPv4 Owner</label>
                  <span>Self</span>
                </li>
                <li className='configIpAddRange'>
                  <label>IP Address Range</label>
                  <span>180.179.206.0</span>
                  <span className='grayText'>To</span>
                  <span>180.179.206.255</span>
                </li>
              </ul>
            </div>
            <div className='whiteBoxBorder clearfix'>
              <a href='javascript:void(0)'>Zone Details - App Zone</a>
              <span className="accordianBtn pull-right active"><span></span></span>
              <div className="appZoneDetails">
                  <ul>
                    <li>
                      <p className="pinkText">IP Address Range</p>
                      <p className="marginBottom20">Small (/24 - 256 IP's)</p>
                      <p className="ipAddressWrap">
                        <span className="pinkText">Internet NIC IPv4 - </span>
                        <span>10.10.10.0 <span className="twoIpsDivider"> To </span> 10.10.10.255</span>
                      </p>
                      <p>
                        <span className="pinkText">Internet NIC IPv6 - </span>
                        <span>2001:db80:0000:0000:0000:0000:0000:0000
                        <span className="twoIpsDivider"> To </span>2001:db80:0000:0000:0000:0000:0000:0000
                        </span>
                      </p>
                    </li>
                    <li>
                      <p className="ipAddressWrap">
                        <span className="pinkText">Private NIC IPv4 - </span>
                        <span>10.10.10.0 <span className="twoIpsDivider"> To </span> 10.10.10.255</span>
                      </p>
                      <p>
                        <span className="pinkText">Private NIC IPv6 - </span>
                        <span>2001:db80:0000:0000:0000:0000:0000:0000
                        <span className="twoIpsDivider"> To </span>2001:db80:0000:0000:0000:0000:0000:0000
                        </span>
                      </p>
                    </li>
                    <li>
                      <p className="ipAddressWrap">
                        <span className="pinkText">Storage NIC IPv4 - </span>
                        <span>10.10.10.0 <span className="twoIpsDivider"> To </span> 10.10.10.255</span>
                      </p>
                      <p>
                        <span className="pinkText">Storage NIC IPv6 - </span>
                        <span>2001:db80:0000:0000:0000:0000:0000:0000
                        <span className="twoIpsDivider"> To </span>2001:db80:0000:0000:0000:0000:0000:0000
                        </span>
                      </p>
                    </li>
                    <li>
                      <p className="ipAddressWrap">
                        <span className="pinkText">Backup NIC IPv4 - </span>
                        <span>10.10.10.0 <span className="twoIpsDivider"> To </span> 10.10.10.255</span>
                      </p>
                      <p>
                        <span className="pinkText">Backup NIC IPv6 - </span>
                        <span>2001:db80:0000:0000:0000:0000:0000:0000
                        <span className="twoIpsDivider"> To </span>2001:db80:0000:0000:0000:0000:0000:0000
                        </span>
                      </p>
                    </li>
                  </ul>
              </div>
            </div>
             <div className='whiteBoxBorder clearfix'>
                <a href='javascript:void(0)'>Zone Details - Zone 2</a>
                <span className="accordianBtn pull-right"><span></span></span>
              </div>
          </div>
          <div className="col-md-4">
            <div className="productQuantityWrap rightSideBox">
              <span className="grayText">Quantity - </span> <span>1</span>
            </div>
            <div className="firewallNames rightSideBox">
              <label className="sectionHeading">Firewall Name <span className="accordianBtn pull-right active"><span></span></span> </label>
              <ul className="firewallNamesListing">
                <li>VFW2</li>
              </ul>
            </div>
            <div className="productPricingWrap rightSideBox">
              <label className="sectionHeading">Product Pricing</label>
              <ul className="clearfix">
                <li>
                  <label>One Time Charges</label>
                  <span>&#8377; 4,454.05</span>
                </li>
                <li>
                  <label>Monthly Charges</label>
                  <span>&#8377; 4,454.05</span>
                </li>
                <li className="totalPricing">
                  <label>Total</label>
                  <span>&#8377; 4,454.05</span>
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


export default FirewallSummary;
