package biz.belcorp.salesforce.modules.digital.features.digital.view.detail.multiprofile.grid

import biz.belcorp.mobile.components.design.spreadsheet.models.Column
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.isDV
import biz.belcorp.salesforce.core.utils.isGR
import biz.belcorp.salesforce.core.utils.isGZ
import biz.belcorp.salesforce.core.utils.toHundredPercentage
import biz.belcorp.salesforce.modules.digital.core.domain.entities.DigitalConsolidated
import biz.belcorp.salesforce.modules.digital.core.domain.entities.DigitalInfo
import biz.belcorp.salesforce.modules.digital.features.constants.DigitalFilterType
import biz.belcorp.salesforce.modules.digital.features.utils.DigitalTextResolver
import kotlin.math.roundToInt

class DigitalConsolidatedMapper(
    private val textResolver: DigitalTextResolver,
    private val gridCreator: DigitalGridCreator
) {

    fun map(
        @DigitalFilterType type: Int,
        container: DigitalConsolidated
    ): List<Column> {

        val consolidated = DigitalConsolidatedModel(
            uaTitle = textResolver.getLowerUaRolText(container.role),
            digitalList = createGridIndicatorList(type, container),
            valueTitle = textResolver.getGridHeaderText(type),
            valuePercentageTitle = textResolver.getGridPercentageHeaderText(type)
        )

        return gridCreator.getColumns(consolidated)
    }

    private fun createGridIndicatorList(
        @DigitalFilterType type: Int,
        container: DigitalConsolidated
    ) = with(container) {
        pairData.map { pair ->
            val uaLabel = getUaLabel(pair.first.uaKey, role, container.isThirdPerson)
            pair.second?.let { getGridModel(type, uaLabel, it) } ?: getEmptyModel(uaLabel)
        }
    }

    private fun getEmptyModel(uaLabel: String): DigitalInfoGridModel {
        return DigitalInfoGridModel(uaLabel = uaLabel)
    }

    private fun getGridModel(
        @DigitalFilterType type: Int,
        uaLabel: String,
        info: DigitalInfo
    ): DigitalInfoGridModel {

        val (value, valuePercentage) = when (type) {
            DigitalFilterType.SUBSCRIBED -> Pair(
                formatValue(info.subscribed, info.actives),
                formateValuePercentage(info.subscribedActivesRatio)
            )
            DigitalFilterType.SHARE -> Pair(
                formatValue(info.share, info.subscribed),
                formateValuePercentage(info.shareSubscribedRatio)
            )
            else -> Pair(
                formatValue(info.buy, info.subscribed),
                formateValuePercentage(info.buySubscribedRatio)
            )
        }

        return DigitalInfoGridModel(
            uaLabel = uaLabel,
            value = value,
            valuePercentage = valuePercentage
        )
    }

    private fun formatValue(progress: Int, total: Int): String {
        return textResolver.getHeaderProgressText(progress, total)
    }

    private fun formateValuePercentage(percentage: Float): String {
        return textResolver.getHeaderPercentageText(percentage.toHundredPercentage().roundToInt())
    }

    private fun getUaLabel(uaKey: LlaveUA, role: Rol, isThirdPerson: Boolean): String {
        return getChildUaLabel(uaKey, role) ?: getOwnUaLabel(uaKey, role, isThirdPerson)
    }

    private fun getChildUaLabel(kpi: LlaveUA, role: Rol): String? {
        return when {
            role.isDV() -> kpi.codigoRegion
            role.isGR() -> kpi.codigoZona
            role.isGZ() -> kpi.codigoSeccion
            else -> Constant.EMPTY_STRING
        }.takeIf { it.orEmpty().isNotEmpty() }
    }

    private fun getOwnUaLabel(kpi: LlaveUA, role: Rol, isThirdPerson: Boolean): String {
        return when {
            role.isDV() -> textResolver.getTextUaCountry()
            role.isGR() -> if (isThirdPerson) kpi.codigoRegion.orEmpty() else textResolver.getTextUaRegion()
            role.isGZ() -> if (isThirdPerson) kpi.codigoZona.orEmpty() else textResolver.getTextUaZone()
            else -> Constant.EMPTY_STRING
        }
    }

}
