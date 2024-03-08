package biz.belcorp.salesforce.modules.consultants.features.filters.baseunified.mappers

import biz.belcorp.salesforce.core.constants.SourceType
import biz.belcorp.salesforce.core.domain.entities.consultants.Consultant
import biz.belcorp.salesforce.core.domain.entities.consultants.ConsultantConfiguration
import biz.belcorp.salesforce.core.utils.toHashMap
import biz.belcorp.salesforce.modules.consultants.features.filters.baseunified.model.ConsultantContainerModel
import biz.belcorp.salesforce.modules.consultants.features.filters.baseunified.model.ConsultantModel
import biz.belcorp.salesforce.modules.consultants.features.filters.utils.ConsultantListSorterUtils

class ConsultantsMapper(private val consultantModelCreator: ConsultantModelCreator) {

    fun map(params: ConsultantModelParams): ConsultantContainerModel = with(params) {
        val consultants = mapToModels(type, configuration, consultants)
        val flags = generateFlags(type)
        return ConsultantContainerModel(
            consultants,
            showSearchBar = flags.containsKey(HASH_ENABLE_SEARCH),
            showFilter = flags.containsKey(HASH_ENABLE_FILTER),
            showOrder = flags.containsKey(HASH_ENABLE_ORDER),
            showSummary = flags.containsKey(HASH_ENABLE_SUMMARY),
            flagMto = configuration.flagMto
        )
    }

    private fun mapToModels(
        type: Int,
        configuration: ConsultantConfiguration,
        values: List<Consultant>
    ): List<ConsultantModel> =
        values.map { consultantModelCreator.createModel(type, configuration, it) }
            .let { ConsultantListSorterUtils.sortBySegment(it) }


    private fun generateFlags(type: Int): Map<String, String> {
        val flags = when {
            SourceType.isKpi(type) -> listOf(HASH_ENABLE_SEARCH, HASH_ENABLE_SUMMARY)
            SourceType.isDigital(type) -> listOf(HASH_ENABLE_SEARCH, HASH_ENABLE_SUMMARY)
            SourceType.isBillingAdvancement(type) -> listOf(HASH_ENABLE_SEARCH, HASH_ENABLE_SUMMARY, HASH_ENABLE_FILTER)
            else -> emptyList()
        }
        return flags.toHashMap()
    }

    companion object {
        private const val HASH_ENABLE_FILTER = "HEF"
        private const val HASH_ENABLE_SEARCH = "HES"
        private const val HASH_ENABLE_ORDER = "HEO"
        private const val HASH_ENABLE_SUMMARY = "HESM"
    }

    class ConsultantModelParams(
        @SourceType val type: Int,
        val configuration: ConsultantConfiguration,
        val consultants: List<Consultant>,
        val filters: List<String>
    )
}
