import CONSTANTS from '../../../constants/app-constant';
import { Link } from 'react-router';

var AssetsWidgetData = createReactClass({

  getPrimaryAssetType(defaultAssets, assetType) {
    let asset = _.find(defaultAssets, (item) => item && item.assetType.toUpperCase() === assetType.toUpperCase());
    return asset ? (<div className="tcktTxtDetails serverWrap clearfix">
      <p>{asset && asset.assetType}</p>
      <span>{asset && asset.assetCount}</span>
    </div>) : null;
  },

  getSecondaryAssetType(defaultAssets, assetType) {
    let asset = _.find(defaultAssets,
      (item) => item && item.assetType.toUpperCase() === assetType.toUpperCase());
    return asset ? (<Link to={{ pathname: '/assets', query: { type: assetType}}}>
      <div className="tcktTxtDetails secondServerWrap clearfix">
        <span className="pull-left">{asset && asset.assetType}</span>
        <span className="pull-right">{asset && asset.assetCount}</span>
      </div></Link>) : null;
  },

  getFilteredAssetType(data) {
    return _.chain(data).filter((item) => _.contains(CONSTANTS.DEFAULT_ASSET_TYPES, item && item.assetType)).value()
  },

  render () {
    let data = this.props.data;
    let defaultAssets = this.getFilteredAssetType(this.props.data.listOfAssetTypeCount);
    return (
      data && data.assetCount ? (<div>
        <div className="widgetDataCount clearfix borderBottom">
          <Link to="/assets" activeClassName="">
            <p className="pull-left">
              {data.assetCount || CONSTANTS.NOT_AVAILABLE}
            </p>
          </Link>
          <label className="widgetLabel"></label>
        </div>
        <div className="outstanding clearfix">
          {this.getPrimaryAssetType(defaultAssets, "Physical  Server")}
          {this.getPrimaryAssetType(defaultAssets, "Cloud Server")}
          {this.getSecondaryAssetType(defaultAssets, "Network")}
          {this.getSecondaryAssetType(defaultAssets, "Compute")}
          {this.getSecondaryAssetType(defaultAssets, "Storage")}
          {this.getSecondaryAssetType(defaultAssets, "Other")}
        </div>
      </div>
    ) : (<span className="noDataFound widgetLabel">{CONSTANTS.UI_MESSAGES.dataNotFound}</span>)
    )
  }
});

export default AssetsWidgetData;
