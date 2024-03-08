package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.performance.mapper

import biz.belcorp.salesforce.core.utils.WordUtils
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.profile.ProfilePerformanceSe
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.profile.SuccessfulHistoricItem
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.performance.model.PerformanceModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.performance.model.SuccessfulModel
import biz.belcorp.salesforce.modules.developmentpath.features.utils.ModuleTextResolver
import java.util.*

class PerformanceModelMapper(
    private val textResolver: ModuleTextResolver
) {

    fun map(performance: ProfilePerformanceSe) =
        PerformanceModel(
            productivity = getProductivityText(performance.productivity),
            successfulHistoric = performance.successfulHistoric.map { map(it) },
            hasNotProductivity = performance.productivity.isBlank()
        )

    private fun map(item: SuccessfulHistoricItem) =
        SuccessfulModel(
            campaign = item.campaign,
            description = getSuccessfulText(item.isSuccessful),
            color = getSuccessfulColor(item.isSuccessful)
        )

    private fun getSuccessfulText(successful: Boolean): String {
        return if (successful) {
            textResolver.getSuccessfulText()
        } else {
            textResolver.getNotSuccessfulText()
        }
    }

    private fun getSuccessfulColor(successful: Boolean): Int {
        return if (successful) R.color.estado_negativo else R.color.estado_positivo
    }

    private fun getProductivityText(productivity: String): String {
        return WordUtils.capitalize(productivity.toLowerCase(Locale.getDefault()))
    }

}
