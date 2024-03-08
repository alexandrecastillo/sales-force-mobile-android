package biz.belcorp.salesforce.core.domain.entities.configuration

import biz.belcorp.salesforce.core.domain.entities.pais.Pais
import biz.belcorp.salesforce.core.domain.entities.people.BusinessPartner

object ConfigurationRules {

    fun getBrightLevelKpi(country: Pais?): List<BusinessPartner.Level> {
        return when (country) {
            Pais.COSTARICA -> getBrightLevels()
            else -> emptyList()
        }
    }

    private fun getBrightLevels() = mutableListOf(
        BusinessPartner.Level.Diamond,
        BusinessPartner.Level.Platinum
    )
}
