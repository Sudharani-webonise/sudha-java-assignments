import { Link } from 'react-router';
import Session from '../../../mixins/sessionUtils';
import http from '../../../mixins/restutils';

var SSOIframe = createReactClass({

    componentDidMount() {
        this.setFormData();
    },

    componentDidUpdate() {
        this.setFormData();
    },

    setFormData() {
        var formData = this.props.formData
        var subFormData = formData.serviceContractDetailsBean
        var getParamsForUrl ={
            cv_crm_id : formData.cv_crm_id,
            bill_to_cv_crm_id : formData.bill_to_cv_crm_id,
            project_id : formData.project_id
        }
        var moduleUrl = http.appendUrl(this.props.formData.url , getParamsForUrl)
        $('#proForm').attr('action', moduleUrl)
        for (var key in formData) {
            if (formData.hasOwnProperty(key)) {
                $('#' + key).val(formData[key])
            }
        }
        var index = 0;
        _.each(subFormData, (data) => {
          try {
                $('#' + index).val(data.productQty)
          }
          catch(err) {
                console.log(err) //TODO : data will be fixed from NM side, then we can remove try catch from here
          }
          index++;
        });
        $('#proForm').submit()
    },

    showFormData(subFormData) {
        return  _.map(subFormData, (value, key) => {
            return (
                <input key={key} type='hidden' value={value.productQty} name={value.productCode} id={key} />
            );
        });
    },

    render() {

        let formData = this.props.formData
        let subFormData = formData.serviceContractDetailsBean
        if(this.props.formData.url)
        {
                return (
                    <div>
                        <iframe name='iframePro' id='iframePro' frameborder='0'
                            className="ssoIframeContainer" >
                        </iframe>
                        <form method='POST' target="iframePro" id='proForm'>
                            <input type='hidden' name='source' value='O' id='source' />
                            <input type='hidden' name='cv_crm_id' id='cv_crm_id' />
                            <input type='hidden' name='bill_to_cv_crm_id' id='bill_to_cv_crm_id' />
                            <input type='hidden' name='projectId' id='projectId' />
                            <input type='hidden' name='product_code' id='product_code' />
                            <input type='hidden' name='combinationId' id='combinationId' />
                            <input type='hidden' name='item_number' id='item_number' />
                            <input type='hidden' name='vm_name' id='vm_name' />
                            <input type='hidden' name='irtask_number' id='irtask_number' />
                            <input type='hidden' name='qty' id='qty' />
                            <input type='hidden' name='ip_address' id='ip_address' />
                            <input type='hidden' name='iptype' id='iptype' />
                            <input type='hidden' name='serviceterm_month' id='serviceterm_month' />
                            <input type='hidden' name='zone_name' id='zone_name' />
                            <input type='hidden' name='token' id='token' />
                            <input type='hidden' name='module' value=''  id='module' />
                            <input type='hidden' name='serviceterm_days' id='serviceterm_days' />
                            <input type='hidden' name='sof_number' id='sof_number' />
                            { this.showFormData(subFormData) }
                        </form>
                    </div>
                )
        }
        else{
            return (<div>Oops , some error occured.<br/> Kindly try again.</div>)
        }
    }
});

export default SSOIframe;
