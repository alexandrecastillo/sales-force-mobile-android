package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.retencionactivas.mappers

import biz.belcorp.mobile.components.charts.bar.model.BarEntrySet
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ONE
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO
import biz.belcorp.salesforce.core.constants.Constant.ZERO_FLOAT
import biz.belcorp.salesforce.core.utils.Mapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos.ActivesRetention
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.retencionactivas.models.ActivesRetentionModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.retencionactivas.models.GraphicActivesRetentionModel
import biz.belcorp.salesforce.modules.developmentpath.utils.obtenerAnioCampania
import kotlin.math.max

class RetencionMapper : Mapper<ActivesRetentionModel, ActivesRetention>() {

    override fun map(value: ActivesRetentionModel) = ActivesRetention()

    override fun reverseMap(value: ActivesRetention): ActivesRetentionModel {
        return with(value) {
            ActivesRetentionModel(
                campaign = campaign,
                activesLastYear = activesLastYear,
                activesReal = activesReal
            )
        }
    }

    fun mapToModel(
        values: List<ActivesRetentionModel>,
        entrySet: BarEntrySet
    ): GraphicActivesRetentionModel {

        val common = values.firstOrNull()
        val year = common?.campaign?.obtenerAnioCampania()?.minus(NUMBER_ONE) ?: NUMBER_ZERO
        val activesLastYear = common?.activesLastYear?.toFloat() ?: ZERO_FLOAT
        val maxActivasReal = values.map { it.activesReal.toFloat() }.max() ?: ZERO_FLOAT
        val maxValue = max(maxActivasReal, activesLastYear) * MAX_VALUE_OFFSET

        return GraphicActivesRetentionModel(
            maxValue = maxValue,
            minValue = Constant.ZERO_DECIMAL.toFloat(),
            year = year.toString(),
            barEntrySet = entrySet,
            indicatorValue = activesLastYear
        )
    }

    companion object {
        private const val MAX_VALUE_OFFSET = 1.1f
    }

}
