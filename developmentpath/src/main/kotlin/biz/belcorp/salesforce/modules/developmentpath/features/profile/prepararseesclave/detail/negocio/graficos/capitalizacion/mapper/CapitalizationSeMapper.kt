package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.capitalizacion.mapper

import biz.belcorp.mobile.components.charts.bar.model.BarEntrySet
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.utils.Mapper
import biz.belcorp.salesforce.core.utils.zeroIfNull
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos.CapitalizationSe
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.capitalizacion.model.CapitalizationSeModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.capitalizacion.model.GraphicCapitalizationSeModel

class CapitalizationSeMapper : Mapper<CapitalizationSeModel, CapitalizationSe>() {

    override fun map(value: CapitalizationSeModel) = CapitalizationSe()

    override fun reverseMap(value: CapitalizationSe): CapitalizationSeModel {
        return with(value) {
            CapitalizationSeModel(
                campaign = campaign,
                real = real,
                expenses = expenses.times(Constant.UNO_NEGATIVO),
                entries = entries,
                reentries = reentries
            )
        }
    }

    fun mapToModel(
        values: List<CapitalizationSeModel>,
        entrySet: BarEntrySet
    ): GraphicCapitalizationSeModel {

        val maxValue = values.map { it.entries + it.reentries }.max().zeroIfNull()
        val minValue = values.map { it.expenses }.min().zeroIfNull()

        return GraphicCapitalizationSeModel(
            maxValue = maxValue.toFloat(),
            minValue = minValue.toFloat(),
            barEntrySet = entrySet,
            items = values
        )
    }

}
