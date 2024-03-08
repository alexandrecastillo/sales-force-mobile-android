package biz.belcorp.salesforce.modules.calculator.util

import biz.belcorp.salesforce.core.constants.CountryISO
import biz.belcorp.salesforce.core.constants.Level

fun diferentiatedNeededCampaignProjection(countryIso: String, level: String?): Boolean {
    return countryIso == CountryISO.COLOMBIA ||
        (countryIso == CountryISO.MEXICO && (Level.PLATINUM == level || Level.DIAMOND == level)) ||
        countryIso == CountryISO.CHILE ||
        countryIso == CountryISO.COSTA_RICA ||
        countryIso == CountryISO.GUATEMALA ||
        countryIso == CountryISO.DOMINICANA
}
