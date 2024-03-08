package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.ganancia.mapper

import biz.belcorp.mobile.components.charts.bar.model.BarEntrySet
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.utils.Mapper
import biz.belcorp.salesforce.core.utils.formatearConComas
import biz.belcorp.salesforce.core.utils.zeroIfNull
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos.ProfitSe
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.ganancia.model.GraphicProfitSeModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.ganancia.model.ProfitSeModel

class ProfitSeMapper : Mapper<ProfitSeModel, ProfitSe>() {

    override fun map(value: ProfitSeModel): ProfitSe = ProfitSe()

    override fun reverseMap(value: ProfitSe): ProfitSeModel {
        return with(value) {
            ProfitSeModel(
                campaign = campaign,
                total = total
            )
        }
    }

    fun mapToModel(
        values: List<ProfitSeModel>,
        entrySet: BarEntrySet
    ): GraphicProfitSeModel {

        val maxValue = values.map { it.total }.max().zeroIfNull()
        val average = values.map { it.total }.average()

        return GraphicProfitSeModel(
            maxValue = maxValue,
            minValue = Constant.ZERO_FLOAT,
            barEntrySet = entrySet,
            average = average.formatearConComas(),
            items = values
        )
    }

}
