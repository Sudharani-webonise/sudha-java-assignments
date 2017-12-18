<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>E-Billing Solutions Pvt Ltd - Payment Page</title>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link rel="stylesheet" href="Formvalidation.css"></link>
<script type="text/javascript" src="FormValidation.js"></script>

<style type="text/css">
h1 {
	font-family: 'Gentium Book Basic';
	font-size: 24pt;
	color: #08185A;
	font-weight: 100;
	margin-bottom: 0.1em
}

h2.co {
	font-family: Arial, sans-serif;
	font-size: 24pt;
	color: #FFFFFF;
	margin-top: 0.1em;
	margin-bottom: 0.1em;
	font-weight: 100
}

h3.co {
	font-family: Arial, sans-serif;
	font-size: 16pt;
	color: #000000;
	margin-top: 0.1em;
	margin-bottom: 0.1em;
	font-weight: 100
}

h3 {
	font-family: Arial, sans-serif;
	font-size: 30pt;
	color: #08185A;
}

body {
	font-family: Gentium Book Basic;
	font-size: 40pt;
	color: #08185A;
	margin-top: 0px;
}

th {
	font-size: 15px;
	background: #008080;
	color: #FFFFFF;
	font-weight: bold;
	height: 30px;
}

td {
	font-size: 15px;
}

.pageTitle {
	font-size: 24px;
}

#mode {
	width: 126px;
}
</style>
<script language="JavaScript" type="text/javascript">
	function a2() {
		document.getElementById('a1').style.display = "none";
	}

	function checkit() {

		var value = document.getElementById('channel').value;
		{
			if (value === '0') {
				document.getElementById('a1').style.display = "none";
			} else {
				document.getElementById('a1').style.display = "inline";

			}
		}
	}
</script>






</head>
<body onload="a2()"
	style="background-color: lightgray; margin-left: 300px; margin-right: 300px; margin-top: 0px;">
	<center>
		<div style="background-color: white;">
			<form action="submitpayment" method="post" name="frm" id="theForm"
				onsubmit="return validateForm()" autocomplete="off" />

			<input type="hidden" name="v3URL"
				value="https://secure.ebs.in/pg/ma/payment/request" />
			<div>

				<h1>EBS - JSP Version 3</h1>

				<table width="700" style="text-align: left;">

					<tr>
						<th colspan="2">Request Details</th>
					</tr>
					<tr>
						<td>Account Id<span class="red">*</span></td>
						<td align="left"><input type="text" id="account_id"
							name="account_id" value="6463" /></td>
						<tr>

							<tr>
								<td>Channel<span class="red">*</span></td>
								<td align="left"><select id="channel" name="channel"
									onchange="checkit()">
										<option value="0">Standard</option>
										<option value="2" selected>Direct</option>
								</select></td>
							</tr>

							<tr>
								<td class="fieldName" width="50%">Currency<span class="red">*</span></td>
								<td align="left" width="50%"><select id="currency"
									name="currency">
										<option value="INR">INR</option>
										<option value="GBP">GBP</option>
										<option value="EUR">EUR</option>
										<option value="USD">USD</option>
								</select></td>
							</tr>

							<tr>
								<td>Return URL<span class="red">*</span></td>
								<td align="left"><input type="text" id="return_url"
									name="return_url"
									value="http://localhost:8080/spectrum-api/payment/paymentresponse" /></td>
								<tr>
									<tr>
										<td>Mode<span class="red">*</span></td>
										<td align="left"><input id="mode" name="mode" type="text"
											value="TEST" readonly="true" /></td>
									</tr>

									<tr>
										<td class="fieldName">Secure Hash Algorithm<span
											class="red">*</span></td>
										<td align="left"><select id="algo" name="algo">
												<option value="MD5">MD5</option>
												<option value="SHA1">SHA1</option>
												<option value="SHA512">SHA512</option>
										</select></td>
									</tr>
									<tr>
										<th colspan="2">Transaction Details</th>
									</tr>
									<tr>
										<td>Reference Number<span class="red">*</span></td>
										<td align="left"><input type="text" id="reference_no"
											name="reference_no" value="123" /></td>
									</tr>
									<tr>
										<td>Amount<span class="red">*</span></td>
										<td align="left"><input type="text" id="amount"
											name="amount" value="100" /></td>
									</tr>

									<tr>
										<td>Description<span class="red">*</span></td>
										<td align="left" width="50%"><input id="description"
											name="description" type="text" value="asas" /></td>
									</tr>
									<tr>
										<th colspan="2">Billing Address</th>
									</tr>
									<tr>
										<td>Name<span class="red">*</span></td>
										<td align="left"><input id="name" name="name" type="text"
											value="asas" /></td>
									</tr>
									<tr>
										<td>Address<span class="red">*</span></td>
										<td align="left"><input id="address" name="address"
											type="text" value="asas" /></td>
									</tr>
									<tr>
										<td>City<span class="red">*</span></td>
										<td align="left"><input id="city" name="city" type="text"
											value="asas" /></td>
									</tr>
									<tr>
										<td>State/Province<span class="red">*</span></td>
										<td align="left"><input id="state" name="state"
											type="text" value="asas" /></td>
									</tr>
									<tr>
										<td>ZIP/Postal Code<span class="red">*</span></td>
										<td align="left"><input id="postal_code"
											name="postal_code" type="text" value="121212" /></td>
									</tr>
									<tr>
										<td>Country<span class="red">*</span></td>
										<td align="left"><select name="country" id="country">
												<option value=""></option>
												<option value="ABW">Aruba</option>
												<option value="AFG">Afghanistan</option>
												<option value="AGO">Angola</option>
												<option value="AIA">Anguilla</option>
												<option value="ALA">�land Islands</option>
												<option value="ALB">Albania</option>
												<option value="AND">Andorra</option>
												<option value="ANT">Netherlands Antilles</option>
												<option value="ARE">United Arab Emirates</option>
												<option value="ARG">Argentina</option>
												<option value="ARM">Armenia</option>
												<option value="ASM">American Samoa</option>
												<option value="ATA">Antarctica</option>
												<option value="ATF">French Southern Territories</option>
												<option value="ATG">Antigua and Barbuda</option>
												<option value="AUS">Australia</option>
												<option value="AUT">Austria</option>
												<option value="AZE">Azerbaijan</option>
												<option value="BDI">Burundi</option>
												<option value="BEL">Belgium</option>
												<option value="BEN">Benin</option>
												<option value="BFA">Burkina Faso</option>
												<option value="BGD">Bangladesh</option>
												<option value="BGR">Bulgaria</option>
												<option value="BHR">Bahrain</option>
												<option value="BHS">Bahamas</option>
												<option value="BIH">Bosnia and Herzegovina</option>
												<option value="BLM">Saint Barth�lemy</option>
												<option value="BLR">Belarus</option>
												<option value="BLZ">Belize</option>
												<option value="BMU">Bermuda</option>
												<option value="BOL">Bolivia</option>
												<option value="BRA">Brazil</option>
												<option value="BRB">Barbados</option>
												<option value="BRN">Brunei Darussalam</option>
												<option value="BTN">Bhutan</option>
												<option value="BVT">Bouvet Island</option>
												<option value="BWA">Botswana</option>
												<option value="CAF">Central African Republic</option>
												<option value="CAN">Canada</option>
												<option value="CCK">Cocos (Keeling) Islands</option>
												<option value="CHE">Switzerland</option>
												<option value="CHL">Chile</option>
												<option value="CHN">China</option>
												<option value="CIV">C�te d`Ivoire</option>
												<option value="CMR">Cameroon</option>
												<option value="COD">Congo, the Democratic Republic
													of the</option>
												<option value="COG">Congo</option>
												<option value="COK">Cook Islands</option>
												<option value="COL">Colombia</option>
												<option value="COM">Comoros</option>
												<option value="CPV">Cape Verde</option>
												<option value="CRI">Costa Rica</option>
												<option value="CUB">Cuba</option>
												<option value="CXR">Christmas Island</option>
												<option value="CYM">Cayman Islands</option>
												<option value="CYP">Cyprus</option>
												<option value="CZE">Czech Republic</option>
												<option value="DEU">Germany</option>
												<option value="DJI">Djibouti</option>
												<option value="DMA">Dominica</option>
												<option value="DNK">Denmark</option>
												<option value="DOM">Dominican Republic</option>
												<option value="DZA">Algeria</option>
												<option value="ECU">Ecuador</option>
												<option value="EGY">Egypt</option>
												<option value="ERI">Eritrea</option>
												<option value="ESH">Western Sahara</option>
												<option value="ESP">Spain</option>
												<option value="EST">Estonia</option>
												<option value="ETH">Ethiopia</option>
												<option value="FIN">Finland</option>
												<option value="FJI">Fiji</option>
												<option value="FLK">Falkland Islands (Malvinas)</option>
												<option value="FRA">France</option>
												<option value="FRO">Faroe Islands</option>
												<option value="FSM">Micronesia, Federated States of</option>
												<option value="GAB">Gabon</option>
												<option value="GBR">United Kingdom</option>
												<option value="GEO">Georgia</option>
												<option value="GGY">Guernsey</option>
												<option value="GHA">Ghana</option>
												<option value="GIN">N Guinea</option>
												<option value="GIB">Gibraltar</option>
												<option value="GLP">Guadeloupe</option>
												<option value="GMB">Gambia</option>
												<option value="GNB">Guinea-Bissau</option>
												<option value="GNQ">Equatorial Guinea</option>
												<option value="GRC">Greece</option>
												<option value="GRD">Grenada</option>
												<option value="GRL">Greenland</option>
												<option value="GTM">Guatemala</option>
												<option value="GUF">French Guiana</option>
												<option value="GUM">Guam</option>
												<option value="GUY">Guyana</option>
												<option value="HKG">Hong Kong</option>
												<option value="HMD">Heard Island and McDonald
													Islands</option>
												<option value="HND">Honduras</option>
												<option value="HRV">Croatia</option>
												<option value="HTI">Haiti</option>
												<option value="HUN">Hungary</option>
												<option value="IDN">Indonesia</option>
												<option value="IMN">Isle of Man</option>
												<option value="IND" selected="selected">India</option>
												<option value="IOT">British Indian Ocean Territory</option>
												<option value="IRL">Ireland</option>
												<option value="IRN">Iran, Islamic Republic of</option>
												<option value="IRQ">Iraq</option>
												<option value="ISL">Iceland</option>
												<option value="ISR">Israel</option>
												<option value="ITA">Italy</option>
												<option value="JAM">Jamaica</option>
												<option value="JEY">Jersey</option>
												<option value="JOR">Jordan</option>
												<option value="JPN">Japan</option>
												<option value="KAZ">Kazakhstan</option>
												<option value="KEN">Kenya</option>
												<option value="KGZ">Kyrgyzstan</option>
												<option value="KHM">Cambodia</option>
												<option value="KIR">Kiribati</option>
												<option value="KNA">Saint Kitts and Nevis</option>
												<option value="KOR">Korea, Republic of</option>
												<option value="KWT">Kuwait</option>
												<option value="LAO">Lao People`s Democratic
													Republic</option>
												<option value="LBN">Lebanon</option>
												<option value="LBR">Liberia</option>
												<option value="LBY">Libyan Arab Jamahiriya</option>
												<option value="LCA">Saint Lucia</option>
												<option value="LIE">Liechtenstein</option>
												<option value="LKA">Sri Lanka</option>
												<option value="LSO">Lesotho</option>
												<option value="LTU">Lithuania</option>
												<option value="LUX">Luxembourg</option>
												<option value="LVA">Latvia</option>
												<option value="MAC">Macao</option>
												<option value="MAF">Saint Martin (French part)</option>
												<option value="MAR">Morocco</option>
												<option value="MCO">Monaco</option>
												<option value="MDA">Moldova</option>
												<option value="MDG">Madagascar</option>
												<option value="MDV">Maldives</option>
												<option value="MEX">Mexico</option>
												<option value="MHL">Marshall Islands</option>
												<option value="MKD">Macedonia, the former Yugoslav
													Republic of</option>
												<option value="MLI">Mali</option>
												<option value="MLT">Malta</option>
												<option value="MMR">Myanmar</option>
												<option value="MNE">Montenegro</option>
												<option value="MNG">Mongolia</option>
												<option value="MNP">Northern Mariana Islands</option>
												<option value="MOZ">Mozambique</option>
												<option value="MRT">Mauritania</option>
												<option value="MSR">Montserrat</option>
												<option value="MTQ">Martinique</option>
												<option value="MUS">Mauritius</option>
												<option value="MWI">Malawi</option>
												<option value="MYS">Malaysia</option>
												<option value="MYT">Mayotte</option>
												<option value="NAM">Namibia</option>
												<option value="NCL">New Caledonia</option>
												<option value="NER">Niger</option>
												<option value="NFK">Norfolk Island</option>
												<option value="NGA">Nigeria</option>
												<option value="NIC">Nicaragua</option>
												<option value="NOR">R Norway</option>
												<option value="NIU">Niue</option>
												<option value="NLD">Netherlands</option>
												<option value="NPL">Nepal</option>
												<option value="NRU">Nauru</option>
												<option value="NZL">New Zealand</option>
												<option value="OMN">Oman</option>
												<option value="PAK">Pakistan</option>
												<option value="PAN">Panama</option>
												<option value="PCN">Pitcairn</option>
												<option value="PER">Peru</option>
												<option value="PHL">Philippines</option>
												<option value="PLW">Palau</option>
												<option value="PNG">Papua New Guinea</option>
												<option value="POL">Poland</option>
												<option value="PRI">Puerto Rico</option>
												<option value="PRK">Korea, Democratic People`s
													Republic of</option>
												<option value="PRT">Portugal</option>
												<option value="PRY">Paraguay</option>
												<option value="PSE">Palestinian Territory, Occupied</option>
												<option value="PYF">French Polynesia</option>
												<option value="QAT">Qatar</option>
												<option value="REU">R�union</option>
												<option value="ROU">Romania</option>
												<option value="RUS">Russian Federation</option>
												<option value="RWA">Rwanda</option>
												<option value="SAU">Saudi Arabia</option>
												<option value="SDN">Sudan</option>
												<option value="SEN">Senegal</option>
												<option value="SGP">Singapore</option>
												<option value="SGS">South Georgia and the South
													Sandwich Islands</option>
												<option value="SHN">Saint Helena</option>
												<option value="SJM">Svalbard and Jan Mayen</option>
												<option value="SLB">Solomon Islands</option>
												<option value="SLE">Sierra Leone</option>
												<option value="SLV">El Salvador</option>
												<option value="SMR">San Marino</option>
												<option value="SOM">Somalia</option>
												<option value="SPM">Saint Pierre and Miquelon</option>
												<option value="SRB">Serbia</option>
												<option value="STP">Sao Tome and Principe</option>
												<option value="SUR">Suriname</option>
												<option value="SVK">Slovakia</option>
												<option value="SVN">Slovenia</option>
												<option value="SWE">Sweden</option>
												<option value="SWZ">Swaziland</option>
												<option value="SYC">Seychelles</option>
												<option value="SYR">Syrian Arab Republic</option>
												<option value="TCA">Turks and Caicos Islands</option>
												<option value="TCD">Chad</option>
												<option value="TGO">Togo</option>
												<option value="THA">Thailand</option>
												<option value="TJK">Tajikistan</option>
												<option value="TKL">Tokelau</option>
												<option value="TKM">Turkmenistan</option>
												<option value="TLS">Timor-Leste</option>
												<option value="TON">Tonga</option>
												<option value="TTO">Trinidad and Tobago</option>
												<option value="TUN">Tunisia</option>
												<option value="TUR">Turkey</option>
												<option value="TUV">Tuvalu</option>
												<option value="TWN">Taiwan, Province of China</option>
												<option value="TZA">Tanzania, United Republic of</option>
												<option value="UGA">Uganda</option>
												<option value="UKR">Ukraine</option>
												<option value="UMI">United States Minor Outlying
													Islands</option>
												<option value="URY">Uruguay</option>
												<option value="USA">United States</option>
												<option value="UZB">Uzbekistan</option>
												<option value="VAT">Holy See (Vatican City State)</option>
												<option value="VCT">Saint Vincent and the
													Grenadines</option>
												<option value="VEN">Venezuela</option>
												<option value="VGB">Virgin Islands, British</option>
												<option value="VIR">Virgin Islands, U.S.</option>
												<option value="VNM">Viet Nam</option>
												<option value="VUT">Vanuatu</option>
												<option value="WLF">Wallis and Futuna</option>
												<option value="WSM">Samoa</option>
												<option value="YEM">Yemen</option>
												<option value="ZAF">South Africa</option>
												<option value="ZMB">Zambia</option>
												<option value="ZWE">Zimbabwe</option>
										</select></td>
									</tr>
									<tr>
										<td>Email<span class="red">*</span></td>
										<td align="left"><input id="email" name="email"
											type="text" value="asa@asa.com" /></td>
									</tr>
									<tr>
										<td>Telephone<span class="red">*</span></td>
										<td align="left"><input id="phone" name="phone"
											type="text" value="121212" /></td>
									</tr>
									<tr>
										<th colspan="2">Shipping Details</th>
									</tr>
									<tr>
										<td>Ship Name<span class="red">*</span></td>
										<td align="left"><input id="ship_name" name="ship_name"
											type="text" value="asas" /></td>
									</tr>
									<tr>
										<td>Ship Address<span class="red">*</span></td>
										<td align="left"><input id="ship_address"
											name="ship_address" type="text" value="asas" /></td>
									</tr>
									<tr>
										<td>Ship City<span class="red">*</span></td>
										<td align="left"><input id="ship_city" name="ship_city"
											type="text" value="asas" /></td>
									</tr>
									<tr>
										<td>Ship State<span class="red">*</span></td>
										<td align="left"><input id="ship_state" name="ship_state"
											type="text" value="assa" /></td>
									</tr>
									<tr>
										<td>Ship Country<span class="red">*</span></td>
										<td align="left"><select name="ship_country"
											id="ship_country">
												<option value=""></option>
												<option value="ABW">Aruba</option>
												<option value="AFG">Afghanistan</option>
												<option value="AGO">Angola</option>
												<option value="AIA">Anguilla</option>
												<option value="ALA">�land Islands</option>
												<option value="ALB">Albania</option>
												<option value="AND">Andorra</option>
												<option value="ANT">Netherlands Antilles</option>
												<option value="ARE">United Arab Emirates</option>
												<option value="ARG">Argentina</option>
												<option value="ARM">Armenia</option>
												<option value="ASM">American Samoa</option>
												<option value="ATA">Antarctica</option>
												<option value="ATF">French Southern Territories</option>
												<option value="ATG">Antigua and Barbuda</option>
												<option value="AUS">Australia</option>
												<option value="AUT">Austria</option>
												<option value="AZE">Azerbaijan</option>
												<option value="BDI">Burundi</option>
												<option value="BEL">Belgium</option>
												<option value="BEN">Benin</option>
												<option value="BFA">Burkina Faso</option>
												<option value="BGD">Bangladesh</option>
												<option value="BGR">Bulgaria</option>
												<option value="BHR">Bahrain</option>
												<option value="BHS">Bahamas</option>
												<option value="BIH">Bosnia and Herzegovina</option>
												<option value="BLM">Saint Barth�lemy</option>
												<option value="BLR">Belarus</option>
												<option value="BLZ">Belize</option>
												<option value="BMU">Bermuda</option>
												<option value="BOL">Bolivia</option>
												<option value="BRA">Brazil</option>
												<option value="BRB">Barbados</option>
												<option value="BRN">Brunei Darussalam</option>
												<option value="BTN">Bhutan</option>
												<option value="BVT">Bouvet Island</option>
												<option value="BWA">Botswana</option>
												<option value="CAF">Central African Republic</option>
												<option value="CAN">Canada</option>
												<option value="CCK">Cocos (Keeling) Islands</option>
												<option value="CHE">Switzerland</option>
												<option value="CHL">Chile</option>
												<option value="CHN">China</option>
												<option value="CIV">C�te d`Ivoire</option>
												<option value="CMR">Cameroon</option>
												<option value="COD">Congo, the Democratic Republic
													of the</option>
												<option value="COG">Congo</option>
												<option value="COK">Cook Islands</option>
												<option value="COL">Colombia</option>
												<option value="COM">Comoros</option>
												<option value="CPV">Cape Verde</option>
												<option value="CRI">Costa Rica</option>
												<option value="CUB">Cuba</option>
												<option value="CXR">Christmas Island</option>
												<option value="CYM">Cayman Islands</option>
												<option value="CYP">Cyprus</option>
												<option value="CZE">Czech Republic</option>
												<option value="DEU">Germany</option>
												<option value="DJI">Djibouti</option>
												<option value="DMA">Dominica</option>
												<option value="DNK">Denmark</option>
												<option value="DOM">Dominican Republic</option>
												<option value="DZA">Algeria</option>
												<option value="ECU">Ecuador</option>
												<option value="EGY">Egypt</option>
												<option value="ERI">Eritrea</option>
												<option value="ESH">Western Sahara</option>
												<option value="ESP">Spain</option>
												<option value="EST">Estonia</option>
												<option value="ETH">Ethiopia</option>
												<option value="FIN">Finland</option>
												<option value="FJI">Fiji</option>
												<option value="FLK">Falkland Islands (Malvinas)</option>
												<option value="FRA">France</option>
												<option value="FRO">Faroe Islands</option>
												<option value="FSM">Micronesia, Federated States of</option>
												<option value="GAB">Gabon</option>
												<option value="GBR">United Kingdom</option>
												<option value="GEO">Georgia</option>
												<option value="GGY">Guernsey</option>
												<option value="GHA">Ghana</option>
												<option value="GIN">N Guinea</option>
												<option value="GIB">Gibraltar</option>
												<option value="GLP">Guadeloupe</option>
												<option value="GMB">Gambia</option>
												<option value="GNB">Guinea-Bissau</option>
												<option value="GNQ">Equatorial Guinea</option>
												<option value="GRC">Greece</option>
												<option value="GRD">Grenada</option>
												<option value="GRL">Greenland</option>
												<option value="GTM">Guatemala</option>
												<option value="GUF">French Guiana</option>
												<option value="GUM">Guam</option>
												<option value="GUY">Guyana</option>
												<option value="HKG">Hong Kong</option>
												<option value="HMD">Heard Island and McDonald
													Islands</option>
												<option value="HND">Honduras</option>
												<option value="HRV">Croatia</option>
												<option value="HTI">Haiti</option>
												<option value="HUN">Hungary</option>
												<option value="IDN">Indonesia</option>
												<option value="IMN">Isle of Man</option>
												<option value="IND" selected="selected">India</option>
												<option value="IOT">British Indian Ocean Territory</option>
												<option value="IRL">Ireland</option>
												<option value="IRN">Iran, Islamic Republic of</option>
												<option value="IRQ">Iraq</option>
												<option value="ISL">Iceland</option>
												<option value="ISR">Israel</option>
												<option value="ITA">Italy</option>
												<option value="JAM">Jamaica</option>
												<option value="JEY">Jersey</option>
												<option value="JOR">Jordan</option>
												<option value="JPN">Japan</option>
												<option value="KAZ">Kazakhstan</option>
												<option value="KEN">Kenya</option>
												<option value="KGZ">Kyrgyzstan</option>
												<option value="KHM">Cambodia</option>
												<option value="KIR">Kiribati</option>
												<option value="KNA">Saint Kitts and Nevis</option>
												<option value="KOR">Korea, Republic of</option>
												<option value="KWT">Kuwait</option>
												<option value="LAO">Lao People`s Democratic
													Republic</option>
												<option value="LBN">Lebanon</option>
												<option value="LBR">Liberia</option>
												<option value="LBY">Libyan Arab Jamahiriya</option>
												<option value="LCA">Saint Lucia</option>
												<option value="LIE">Liechtenstein</option>
												<option value="LKA">Sri Lanka</option>
												<option value="LSO">Lesotho</option>
												<option value="LTU">Lithuania</option>
												<option value="LUX">Luxembourg</option>
												<option value="LVA">Latvia</option>
												<option value="MAC">Macao</option>
												<option value="MAF">Saint Martin (French part)</option>
												<option value="MAR">Morocco</option>
												<option value="MCO">Monaco</option>
												<option value="MDA">Moldova</option>
												<option value="MDG">Madagascar</option>
												<option value="MDV">Maldives</option>
												<option value="MEX">Mexico</option>
												<option value="MHL">Marshall Islands</option>
												<option value="MKD">Macedonia, the former Yugoslav
													Republic of</option>
												<option value="MLI">Mali</option>
												<option value="MLT">Malta</option>
												<option value="MMR">Myanmar</option>
												<option value="MNE">Montenegro</option>
												<option value="MNG">Mongolia</option>
												<option value="MNP">Northern Mariana Islands</option>
												<option value="MOZ">Mozambique</option>
												<option value="MRT">Mauritania</option>
												<option value="MSR">Montserrat</option>
												<option value="MTQ">Martinique</option>
												<option value="MUS">Mauritius</option>
												<option value="MWI">Malawi</option>
												<option value="MYS">Malaysia</option>
												<option value="MYT">Mayotte</option>
												<option value="NAM">Namibia</option>
												<option value="NCL">New Caledonia</option>
												<option value="NER">Niger</option>
												<option value="NFK">Norfolk Island</option>
												<option value="NGA">Nigeria</option>
												<option value="NIC">Nicaragua</option>
												<option value="NOR">R Norway</option>
												<option value="NIU">Niue</option>
												<option value="NLD">Netherlands</option>
												<option value="NPL">Nepal</option>
												<option value="NRU">Nauru</option>
												<option value="NZL">New Zealand</option>
												<option value="OMN">Oman</option>
												<option value="PAK">Pakistan</option>
												<option value="PAN">Panama</option>
												<option value="PCN">Pitcairn</option>
												<option value="PER">Peru</option>
												<option value="PHL">Philippines</option>
												<option value="PLW">Palau</option>
												<option value="PNG">Papua New Guinea</option>
												<option value="POL">Poland</option>
												<option value="PRI">Puerto Rico</option>
												<option value="PRK">Korea, Democratic People`s
													Republic of</option>
												<option value="PRT">Portugal</option>
												<option value="PRY">Paraguay</option>
												<option value="PSE">Palestinian Territory, Occupied</option>
												<option value="PYF">French Polynesia</option>
												<option value="QAT">Qatar</option>
												<option value="REU">R�union</option>
												<option value="ROU">Romania</option>
												<option value="RUS">Russian Federation</option>
												<option value="RWA">Rwanda</option>
												<option value="SAU">Saudi Arabia</option>
												<option value="SDN">Sudan</option>
												<option value="SEN">Senegal</option>
												<option value="SGP">Singapore</option>
												<option value="SGS">South Georgia and the South
													Sandwich Islands</option>
												<option value="SHN">Saint Helena</option>
												<option value="SJM">Svalbard and Jan Mayen</option>
												<option value="SLB">Solomon Islands</option>
												<option value="SLE">Sierra Leone</option>
												<option value="SLV">El Salvador</option>
												<option value="SMR">San Marino</option>
												<option value="SOM">Somalia</option>
												<option value="SPM">Saint Pierre and Miquelon</option>
												<option value="SRB">Serbia</option>
												<option value="STP">Sao Tome and Principe</option>
												<option value="SUR">Suriname</option>
												<option value="SVK">Slovakia</option>
												<option value="SVN">Slovenia</option>
												<option value="SWE">Sweden</option>
												<option value="SWZ">Swaziland</option>
												<option value="SYC">Seychelles</option>
												<option value="SYR">Syrian Arab Republic</option>
												<option value="TCA">Turks and Caicos Islands</option>
												<option value="TCD">Chad</option>
												<option value="TGO">Togo</option>
												<option value="THA">Thailand</option>
												<option value="TJK">Tajikistan</option>
												<option value="TKL">Tokelau</option>
												<option value="TKM">Turkmenistan</option>
												<option value="TLS">Timor-Leste</option>
												<option value="TON">Tonga</option>
												<option value="TTO">Trinidad and Tobago</option>
												<option value="TUN">Tunisia</option>
												<option value="TUR">Turkey</option>
												<option value="TUV">Tuvalu</option>
												<option value="TWN">Taiwan, Province of China</option>
												<option value="TZA">Tanzania, United Republic of</option>
												<option value="UGA">Uganda</option>
												<option value="UKR">Ukraine</option>
												<option value="UMI">United States Minor Outlying
													Islands</option>
												<option value="URY">Uruguay</option>
												<option value="USA">United States</option>
												<option value="UZB">Uzbekistan</option>
												<option value="VAT">Holy See (Vatican City State)</option>
												<option value="VCT">Saint Vincent and the
													Grenadines</option>
												<option value="VEN">Venezuela</option>
												<option value="VGB">Virgin Islands, British</option>
												<option value="VIR">Virgin Islands, U.S.</option>
												<option value="VNM">Viet Nam</option>
												<option value="VUT">Vanuatu</option>
												<option value="WLF">Wallis and Futuna</option>
												<option value="WSM">Samoa</option>
												<option value="YEM">Yemen</option>
												<option value="ZAF">South Africa</option>
												<option value="ZMB">Zambia</option>
												<option value="ZWE">Zimbabwe</option>
										</select></td>
									</tr>

									<tr>
										<td>Ship Phone<span class="red">*</span></td>
										<td align="left"><input id="ship_phone" name="ship_phone"
											type="text" value="12121212" /></td>
									</tr>
									<tr>
										<td>Ship Postal Code<span class="red">*</span></td>
										<td align="left"><input id="ship_postal_code"
											name="ship_postal_code" type="text" value="121212" /></td>
									</tr>

									<tr>
										<td valign="top" align="center" colspan="2"><span
											class="red">*denotes required field</span></td>
									</tr>
									<tr>
										<td valign="top" align="center" colspan="2"><input
											id="submit" name="submit" value="Submit" type="submit" />&nbsp;
											<input value="Reset" type="reset" id="reset" /></td>
									</tr>
				</table>
			</div>


			<div id="a1">
				<table width="700" style="text-align: left;">
					<tr>
						<th colspan="2" id="a">Card details</th>
					</tr>
					<tr>
						<td class="fieldName" width="50%">*Name on Card</td>
						<td align="left" width="50%"><input id="name_on_card"
							name="name_on_card" type="text" value="Test" /></td>
					</tr>

					<tr>
						<td class="fieldName" width="50%">*Card Number</td>
						<td align="left" width="50%"><input id="card_number"
							name="card_number" type="text" value="4111111111111111" /></td>
					</tr>

					<tr>
						<td class="fieldName" width="50%">*Card Expiry</td>
						<td align="left" width="50%"><input id="card_expiry"
							name="card_expiry" type="text" value="0720" /></td>
					</tr>
					<tr>
						<td class="fieldName" width="50%">*CVV</td>
						<td align="left" width="50%"><input id="card_cvv"
							name="card_cvv" type="password" value="123" /></td>
					</tr>
				</table>

			</div>


			</form>
		</div>
	</center>
</body>
</html>
