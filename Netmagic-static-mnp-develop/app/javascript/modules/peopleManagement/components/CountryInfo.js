import CONSTANTS from '../../../constants/app-constant';
import Utility from '../../../mixins/basicUtils';
import Session from '../../../mixins/sessionUtils';
import http from '../../../mixins/restutils';
import countries from '../../../data/countries';

var CountryInfo = createReactClass({

    getInitialState() {
        return {
            makeEditable: false,
            countries: countries
        }
    },

    filterCountries(event) {
        var codeSpan = _.first($(event.currentTarget).find('span'));
        if(CONSTANTS.REGEX.SPECIAL_CHAR.test(codeSpan.innerHTML)) {
            codeSpan.innerText = '';
        }
        var text = codeSpan && codeSpan.textContent && codeSpan.textContent.toUpperCase();
        var updatedCountries = _.filter(countries, (item) => {
            var itemText = item.code + ' ' + item.dial_code;
            return itemText.indexOf(text.trim()) !== -1;
        });
        this.setState({ countries: updatedCountries });
    },

    getFirstElement(data) {
        return _.first(data.split(" "));
    },

    showCountryCodes() {
        return _.map(this.state.countries, (data, key) => {
            return (<li>
                <img className="countryFlag" id="flag" src={http.buildRestUrl(CONSTANTS.API_URLS.flags,
                    { flag: (data && data.code).toLowerCase() }) } alt={data && data.name}/>
                <span className="callingCode">{data && data.code} {data && data.dial_code}</span>
            </li>)
        });
    },

    render() {
        let data = this.props.data;
        return (
            <div className="customSelectGroup countryCode pull-left" data-id="countryCode">
                <label for="countryCode">Country Code</label>
                <ul className="customSelectList countryCodeList">
                    { this.showCountryCodes()}
                </ul>
                <p className="selectContent" onInput={this.filterCountries}>
                    { _.isObject(data) && data.countryCode && !this.state.makeEditable ?
                        <img className="countryFlag" id="flag" src={http.buildRestUrl(CONSTANTS.API_URLS.flags,
                            { flag: this.getFirstElement(data.countryCode).toLowerCase() }) }
                            alt={this.getFirstElement(data.countryCode).toLowerCase() }/> : null
                    }
                    <span className="callingCode" contentEditable="true">{data && data.countryCode}</span>
                </p>
            </div>
        )
    }
});

export default CountryInfo;
