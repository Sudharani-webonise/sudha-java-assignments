import NewListInfraItem from './NewListInfraItem';
import UI from '../../../mixins/ui';
import CONSTANTS from '../../../constants/app-constant';

var BillGroup = createReactClass({
    getInitialState() {
        return {
            collapse: false
        }
    },

    getToggleClassSet() {
        return UI.classSet({
            'cartProjectInfoWrap': true,
            'show': this.state.collapse,
            'hide': !this.state.collapse
        });
    },

    getAccordianBtnClass() {
        return UI.classSet({
            'accordianBtn': true,
            'active': this.state.collapse,
            "pull-right": true
        });
    },

    toggle() {
        this.setState({ collapse: !this.state.collapse })
    },

    render() {
        let billGroup = this.props.billGroup;
        return (<li>
            <div className='projectNameWrap clearfix selected'>
                <div className="projectNameDuration">
                    <div className="checkboxWrap pull-left">
                        <label className="cssCheckBox hide">
                            <input type="checkbox" checked />
                            <span className="NMIcon-checkbox lightColorLabel"></span>
                        </label>
                    </div>
                    <h4 className="projectName">{billGroup && billGroup.projectname || CONSTANTS.NOT_AVAILABLE}</h4>
                    <div className="projectDuration">
                        <span>{billGroup && billGroup.cntrperiod} {billGroup && billGroup.cntrperiodunit}</span>
                        <span>{this.props.findLocationFromLocId(billGroup.loc)}</span>
                    </div>
                </div>
                <div className="projectCost">
                    <label className="oneTimeCharge">&#8377; {billGroup && billGroup.potc}</label>
                    <label className="monthlyCharge">&#8377; {billGroup && billGroup.pmrc}</label>
                    <span className={this.getAccordianBtnClass()} onClick={this.toggle}><span></span></span>
                </div>
            </div>
            <div className={this.getToggleClassSet()}>
                {_.map(billGroup && billGroup.items, (item, i) => {
                    return (
                        <NewListInfraItem
                            key = {i}
                            product={item}
                            index = {i}
                            showConfigurationsByTypeForListInfra={this.props.showConfigurationsByTypeForListInfra}
                            showConfigurationsByType={this.props.showConfigurationsByType}
                            billGroup={billGroup}
                            removeInfraFromList={this.props.removeInfraFromList}
                            addInfraEnabled={this.props.addInfraEnabled}
                            enableEditForProduct={this.props.enableEditForProduct}
                            getIndividualPricingDetails={this.props.getIndividualPricingDetails}
                            updateCart={this.props.updateCart}
                            cancelUpdate={this.props.cancelUpdate}
                            updateQuantity={this.props.updateQuantity}
                            updatedConfigItem={this.props.updatedConfigItem}/>
                    )
                }) }
            </div>
            </li>
        )
    }
});

export default BillGroup;
