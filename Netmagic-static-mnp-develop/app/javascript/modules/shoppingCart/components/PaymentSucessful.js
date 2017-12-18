import { Link } from 'react-router';
import CONSTANTS from '../../../constants/app-constant';
import Time from '../../../mixins/time';
import Loader from 'react-loader';
import LoaderOptions from '../../../mixins/loader';
import http from '../../../mixins/restutils';
import Session from '../../../mixins/sessionUtils';
import User from '../../../services/user';
import Utility from '../../../mixins/basicUtils';
import UI from '../../../mixins/ui';
import shoppingCart from '../../../services/shoppingCart';
var AddVM = createReactClass({
	render () {
		return (

<div className="ticketListSection outStandingWrap peopleWrap addContactWrap">

   <section className="breadCrumbSection dashboardBreadCrumb">
                    <div className="container">
                        <div className="row">
                            <div className="col-xs-10 col-sm-10 col-md-10">
                                 <ol className="breadcrumb breadCrumbCustom clearfix">
            <li><a href="javascript:void(0);">Back to Dashboard</a></li>
            <li><a href="javascript:void(0);">My Cart</a></li>
            <li className="active">Payment Successful</li>
          </ol>                            </div>
                            <div className="col-xs-2 col-sm-2 col-md-2">
                            </div>
                        </div>
                    </div>
                </section>



    <div className="container paymentContainer">
      <div className="row">
     		<div className="col-xs-10 col-xs-offset-1 pay_border pad20">
				<div className="text-center"><p className="payment_done"></p></div>
	 			<p className="pay_done">Payment Successful</p>


				<div className="row pad_top20">
					<div className="col-xs-6 col-xs-offset-3">
						<div className="row">
							<div className="col-xs-6 head_style">Transaction ID:</div>
							<div className="col-xs-6 text-right info_style">3690001</div>
						</div>
						<div className="row">
							<div className="col-xs-6 head_style">Transaction Date & Time:</div>
							<div className="col-xs-6 text-right info_style">2017-03-08 13:57:06</div>
						</div>
						<div className="row">
							<div className="col-xs-6 head_style">Amount Paid:</div>
							<div className="col-xs-6 text-right info_style"><i class="rupeeIcon">₹</i> 33,400.00</div>
						</div>
					</div>
				</div>

					<div className="row pad_15">
							<table className="table font13">
								<thead>
									<tr>
									<th className="tb_head">SOF No.</th>
									<th className="tb_head">Bill to Customer</th>
									<th className="tb_head">Support to Customer</th>
									<th className="tb_head">Project</th>
									<th className="tb_head">Period</th>
									<th className="tb_head">Type</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td><Link to="dashboard" className="blue">20351001</Link></td>
										<td>Netmagic IT Services</td>
										<td>NTT Communication</td>
										<td>Zigma</td>
										<td>36 Month</td>
										<td>Amendment</td>
									</tr>
									<tr>
										<td><Link to="dashboard" className="blue">20351001</Link></td>
										<td>Netmagic IT Services</td>
										<td>NTT Communication</td>
										<td>Zigma</td>
										<td>36 Month</td>
										<td>Amendment</td>
									</tr>

								</tbody>
							</table>

							<hr/>
						</div>

						<div className="row text-center">
							<p className="font14 pad_top20"><Link to="dashboard" className="red_head">Click here to open Provisioning Cart</Link> | Those product for which technical details have been completed will be delivered automatically.</p>
						</div>

			</div>
	 </div>
    </div>

</div>
		)
	}
});

export default AddVM;