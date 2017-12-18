import { Link } from 'react-router';

var MyCart = createReactClass({
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
            <ul className="myCartPaymentProcess clearfix pull-right">
              <li><i>1</i> Add Infra</li>
              <li className="processCompleted"><i>2</i> SOF Details</li>
              <li><i>3</i> Make Payment</li>
            </ul>
          </div>
          <div className="col-xs-3"> <a href="javascript:void(0);" className="darkPinkBtn pull-right">Pay Now</a> </div>
        </div>
      </div>
    </section>
    <div className="container">
      <div className="row">
        <div className="col-md-9 myCartProjectListing">
          <h6>Project Name</h6>
          <ul>
            <li>
                <div className="projectNameWrap">
                  <h4>Project 1 - UAT  <span className="accordianBtn active pull-right"><span></span></span> </h4>
                  <p>Please provide us below details before you place your order.</p>
                </div>
                <div className="cartProjectInfoWrap">
                  <div className="projectInfoBox clearfix">
                    <div className="aboutProjectInfo pull-left">
                      <h5>Please define SOF contacts.</h5>
                      <p><span className="grayText">Before placing order you must have to define </span> Billing, IT Operations, SPOC and Authozied Signatory <span className="grayText">contact types.</span> <span className="mandatoryStar">*</span> </p>
                    </div>
                    <div className="addNewContacts pull-right">
                        <a href="#" className="blackBtn">Add New Contact</a> <span className="accordianBtn active pull-right"><span></span></span>
                    </div>
                  </div>
                  <div className="projectInfoData">
                    <ul className="contactListing clearfix">
                      <li className="clearfix">
                        <div className="userPic">
                          <i className="NMIcon-userGrey"></i>
                        </div>
                        <div className="userNameRoleWrap clearfix">
                          <div className="userNameEmail">
                            <span className="pinkText">Kristina G. Eppinger</span>
                            <span className="userEmail">kristineeppinger@maincustomer.com</span>
                          </div>
                          <div className="userRoles clearfix">
                              <ul className="clearfix">
                                <li className="active">
                                  <div className="checkboxWrap">
                                    <label className="cssCheckBox">
                                      <input type="checkbox" checked />
                                      <span className="NMIcon-checkbox lightColorLabel"></span>
                                    </label>
                                  </div>
                                  <label>Billing</label>
                                </li>
                                <li>
                                  <div className="checkboxWrap">
                                    <label className="cssCheckBox">
                                      <input type="checkbox" />
                                      <span className="NMIcon-checkbox lightColorLabel"></span>
                                    </label>
                                  </div>
                                  <label>IT Operations</label>
                                </li>
                                <li>
                                  <div className="checkboxWrap">
                                    <label className="cssCheckBox">
                                      <input type="checkbox" />
                                      <span className="NMIcon-checkbox lightColorLabel"></span>
                                    </label>
                                  </div>
                                  <label>SPOC</label>
                                </li>
                                <li>
                                  <div className="checkboxWrap">
                                    <label className="cssCheckBox">
                                      <input type="checkbox" />
                                      <span className="NMIcon-checkbox lightColorLabel"></span>
                                    </label>
                                  </div>
                                  <label>Authorized Signatory</label>
                                </li>
                              </ul>
                          </div>
                        </div>
                      </li>
                      <li className="clearfix">
                        <div className="userPic">
                          <i className="NMIcon-userGrey"></i>
                        </div>
                        <div className="userNameRoleWrap clearfix">
                          <div className="userNameEmail">
                            <span className="pinkText">Kristina G. Eppinger</span>
                            <span className="userEmail">kristineeppinger@maincustomer.com</span>
                          </div>
                          <div className="userRoles clearfix">
                              <ul className="clearfix">
                                <li className="active">
                                  <div className="checkboxWrap">
                                    <label className="cssCheckBox">
                                      <input type="checkbox" checked />
                                      <span className="NMIcon-checkbox lightColorLabel"></span>
                                    </label>
                                  </div>
                                  <label>Billing</label>
                                </li>
                                <li>
                                  <div className="checkboxWrap">
                                    <label className="cssCheckBox">
                                      <input type="checkbox" />
                                      <span className="NMIcon-checkbox lightColorLabel"></span>
                                    </label>
                                  </div>
                                  <label>IT Operations</label>
                                </li>
                                <li>
                                  <div className="checkboxWrap">
                                    <label className="cssCheckBox">
                                      <input type="checkbox" />
                                      <span className="NMIcon-checkbox lightColorLabel"></span>
                                    </label>
                                  </div>
                                  <label>SPOC</label>
                                </li>
                                <li className="active">
                                  <div className="checkboxWrap">
                                    <label className="cssCheckBox">
                                      <input type="checkbox" checked />
                                      <span className="NMIcon-checkbox lightColorLabel"></span>
                                    </label>
                                  </div>
                                  <label>Authorized Signatory</label>
                                </li>
                              </ul>
                          </div>
                        </div>
                      </li>
                      <li className="clearfix">
                        <div className="userPic">
                          <i className="NMIcon-userGrey"></i>
                        </div>
                        <div className="userNameRoleWrap clearfix">
                          <div className="userNameEmail">
                            <span className="pinkText">Kristina G. Eppinger</span>
                            <span className="userEmail">kristineeppinger@maincustomer.com</span>
                          </div>
                          <div className="userRoles clearfix">
                              <ul className="clearfix">
                                <li>
                                  <div className="checkboxWrap">
                                    <label className="cssCheckBox">
                                      <input type="checkbox" />
                                      <span className="NMIcon-checkbox lightColorLabel"></span>
                                    </label>
                                  </div>
                                  <label>Billing</label>
                                </li>
                                <li>
                                  <div className="checkboxWrap">
                                    <label className="cssCheckBox">
                                      <input type="checkbox" />
                                      <span className="NMIcon-checkbox lightColorLabel"></span>
                                    </label>
                                  </div>
                                  <label>IT Operations</label>
                                </li>
                                <li>
                                  <div className="checkboxWrap">
                                    <label className="cssCheckBox">
                                      <input type="checkbox" />
                                      <span className="NMIcon-checkbox lightColorLabel"></span>
                                    </label>
                                  </div>
                                  <label>SPOC</label>
                                </li>
                                <li>
                                  <div className="checkboxWrap">
                                    <label className="cssCheckBox">
                                      <input type="checkbox" />
                                      <span className="NMIcon-checkbox lightColorLabel"></span>
                                    </label>
                                  </div>
                                  <label>Authorized Signatory</label>
                                </li>
                              </ul>
                          </div>
                        </div>
                      </li>
                      <li className="clearfix notAssignedRole">
                        <div className="userPic">
                          <i className="NMIcon-userGrey"></i>
                        </div>
                        <div className="userNameRoleWrap clearfix">
                          <div className="userNameEmail">
                            <span className="pinkText">Kristina G. Eppinger</span>
                            <span className="userEmail">kristineeppinger@maincustomer.com</span>
                          </div>
                          <div className="userRoles clearfix">
                              <ul className="clearfix">
                                <li>
                                  <div className="checkboxWrap">
                                    <label className="cssCheckBox">
                                      <input type="checkbox" />
                                      <span className="NMIcon-checkbox lightColorLabel"></span>
                                    </label>
                                  </div>
                                  <label>Billing</label>
                                </li>
                                <li>
                                  <div className="checkboxWrap">
                                    <label className="cssCheckBox">
                                      <input type="checkbox" />
                                      <span className="NMIcon-checkbox lightColorLabel"></span>
                                    </label>
                                  </div>
                                  <label>IT Operations</label>
                                </li>
                                <li>
                                  <div className="checkboxWrap">
                                    <label className="cssCheckBox">
                                      <input type="checkbox" />
                                      <span className="NMIcon-checkbox lightColorLabel"></span>
                                    </label>
                                  </div>
                                  <label>SPOC</label>
                                </li>
                                <li>
                                  <div className="checkboxWrap">
                                    <label className="cssCheckBox">
                                      <input type="checkbox" />
                                      <span className="NMIcon-checkbox lightColorLabel"></span>
                                    </label>
                                  </div>
                                  <label>Authorized Signatory</label>
                                </li>
                              </ul>
                          </div>
                        </div>
                      </li>
                    </ul>
                  </div>
                  <div className="projectInfoBox clearfix">
                    <div className="aboutProjectInfo pull-left">
                      <h5>Please define billing address.</h5>
                      <p><span className="grayText">Before placing order you must have to define one of these.</span> <span className="mandatoryStar">*</span> </p>
                    </div>
                    <div className="addNewContacts pull-right">
                        <a href="#" className="blackBtn">Add New Address</a> <span className="accordianBtn active pull-right"><span></span></span>
                    </div>
                  </div>
                  <div className="projectInfoData">
                    <ul className="contactListing clearfix">
                      <li className="clearfix">
                        <div className="selectCompany">
                          <input type="radio"/>
                        </div>
                        <div className="userNameRoleWrap clearfix">
                          <div className="companyNamePhone">
                            <span className="pinkText">Company name</span>
                            <span className="companyPhone">+91 22-11223344</span>
                          </div>
                          <div className="companyAddress clearfix">
                              <p>
                                Floor no, Building Name, Area name, Address Line, nearby landmak, City Name, State Name - Pincode, Country
                              </p>
                          </div>
                        </div>
                      </li>
                      <li className="notAssignedRole clearfix">
                        <div className="selectCompany">
                          <input type="radio"/>
                        </div>
                        <div className="userNameRoleWrap clearfix">
                          <div className="companyNamePhone">
                            <span className="pinkText">Company name</span>
                            <span className="companyPhone">+91 22-11223344</span>
                          </div>
                          <div className="companyAddress clearfix">
                              <p>
                                Floor no, Building Name, Area name, Address Line, nearby landmak, City Name, State Name - Pincode, Country
                              </p>
                          </div>
                        </div>
                      </li>

                    </ul>
                  </div>

                  <div className="projectInfoBox clearfix">
                    <div className="aboutProjectInfo pull-left">
                      <h5>Please define support to address.</h5>
                      <p><span className="grayText">You can define support company address if any.</span></p>
                    </div>
                    <div className="addNewContacts pull-right">
                        <a href="#" className="blackBtn">Add New Address</a> <span className="accordianBtn active pull-right"><span></span></span>
                    </div>
                  </div>
                  <div className="projectInfoData">
                    <ul className="contactListing clearfix">
                      <li className="clearfix">
                        <div className="selectCompany">
                          <input type="radio"/>
                        </div>
                        <div className="userNameRoleWrap clearfix">
                          <div className="companyNamePhone">
                            <span className="pinkText">Company name</span>
                            <span className="companyPhone">+91 22-11223344</span>
                          </div>
                          <div className="companyAddress clearfix">
                              <p>
                                Floor no, Building Name, Area name, Address Line, nearby landmak, City Name, State Name - Pincode, Country
                              </p>
                          </div>
                        </div>
                      </li>
                      <li className="notAssignedRole clearfix">
                        <div className="selectCompany">
                          <input type="radio"/>
                        </div>
                        <div className="userNameRoleWrap clearfix">
                          <div className="companyNamePhone">
                            <span className="pinkText">Company name</span>
                            <span className="companyPhone">+91 22-11223344</span>
                          </div>
                          <div className="companyAddress clearfix">
                              <p>
                                Floor no, Building Name, Area name, Address Line, nearby landmak, City Name, State Name - Pincode, Country
                              </p>
                          </div>
                        </div>
                      </li>

                    </ul>
                  </div>
                </div>
            </li>
            <li>
              <div className="projectNameWrap">
                <h4>Project 1 - UAT  <span className="accordianBtn active pull-right"><span></span></span> </h4>
                <p>Please provide us below details before you place your order.</p>
              </div>
              <div className="cartProjectInfoWrap">
                <div className="projectInfoBox clearfix closed">
                  <div className="aboutProjectInfo pull-left">
                    <h5>Please define SOF contacts.</h5>
                    <p><span className="grayText">Before placing order you must have to define </span> Billing, IT Operations, SPOC and Authozied Signatory <span className="grayText">contact types.</span> <span className="mandatoryStar">*</span> </p>
                  </div>
                  <div className="addNewContacts pull-right">
                      <a href="#" className="blackBtn">Add New Contact</a> <span className="accordianBtn pull-right"><span></span></span>
                  </div>
                </div>
                <div className="projectInfoBox clearfix closed">
                  <div className="aboutProjectInfo pull-left">
                    <h5>Please define billing address.</h5>
                    <p><span className="grayText">Before placing order you must have to define one of these.</span><span className="mandatoryStar">*</span> </p>
                  </div>
                  <div className="addNewContacts pull-right">
                      <a href="#" className="blackBtn">Add New Address</a>
                      <span className="accordianBtn pull-right"><span></span></span>
                  </div>
                </div>
                <div className="projectInfoBox clearfix closed">
                  <div className="aboutProjectInfo pull-left">
                    <h5>Please define support to address.</h5>
                    <p>
                    <span className="grayText">You can define support company address if any.</span></p>
                  </div>
                  <div className="addNewContacts pull-right">
                      <a href="#" className="blackBtn">Add New Address</a> <span className="accordianBtn pull-right"><span></span></span>
                  </div>
                </div>
              </div>
            </li>
            <li>
              <div className="projectNameWrap">
                <h4>Project 3 - UAT  <span className="accordianBtn pull-right"><span></span></span> </h4>
                <p>Please provide us below details before you place your order.</p>
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


export default MyCart;
