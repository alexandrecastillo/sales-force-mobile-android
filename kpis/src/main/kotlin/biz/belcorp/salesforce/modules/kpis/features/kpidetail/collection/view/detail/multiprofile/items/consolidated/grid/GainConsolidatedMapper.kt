package biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.detail.multiprofile.items.consolidated.grid

import biz.belcorp.mobile.components.design.spreadsheet.models.Column
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.collection.CollectionConsolidated
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.collection.CollectionIndicator
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.CollectionTextResolver
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.model.CollectionConsolidatedModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.model.CollectionGridIndicatorModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.detail.multiprofile.items.consolidated.grid.creator.GainGridCreator

class GainConsolidatedMapper(
    private val textResolver: CollectionTextResolver,
    private val gridCreator: GainGridCreator
) {

    fun map(container: CollectionConsolidated): List<Column> {

        val consolidated = CollectionConsolidatedModel(
            uaTitle = textResolver.getLowerUaRolText(container.role),
            collectionList = createGridIndicatorList(container),
            recoveryTitle = textResolver.getRecoveryGridTitle(),
            invoicedTitle = textResolver.getInvoicedGridTitle(),
            collectedTitle = textResolver.getCollectedGridTitle()
        )

        return gridCreator.getColumns(consolidated)
    }

    private fun createGridIndicatorList(container: CollectionConsolidated) = with(container) {
        pairData.map { pair ->
            val uaLabel = getUaLabel(pair.first.uaKey, role, container.isThirdPerson)
            pair.second?.let { getGridModel(uaLabel, currencySymbol, it) } ?: getEmptyModel(uaLabel)
        }
    }

    private fun getEmptyModel(uaLabel: String): CollectionGridIndicatorModel {
        return CollectionGridIndicatorModel(uaLabel = uaLabel)
    }

    private fun getGridModel(
        uaLabel: String,
        currencySymbol: String,
        indicator: CollectionIndicator
    ): CollectionGridIndicatorModel {

        val recoveryFormated = indicator.percentage.toPercentageFormat()
        val invoicedFormated = indicator.invoicedSale.formatWithNoDecimalThousands()
        val collectedFormated = indicator.amountCollected.formatWithNoDecimalThousands()

        val recoveryValue = textResolver.formatPercentage(recoveryFormated)
        val invoicedValue = textResolver.formatCurrency(currencySymbol, invoicedFormated)
        val collectedValue = textResolver.formatCurrency(currencySymbol, collectedFormated)

        return CollectionGridIndicatorModel(
            uaLabel = uaLabel,
            recoveryValue = recoveryValue,
            invoicedValue = invoicedValue,
            collectedValue = collectedValue
        )
    }

    private fun getUaLabel(uaKey: LlaveUA, role: Rol, isThirdPerson: Boolean): String {
        return getChildUaLabel(uaKey, role) ?: getOwnUaLabel(uaKey, role, isThirdPerson)
    }

    private fun getChildUaLabel(kpi: LlaveUA, role: Rol): String? {
        return when {
            role.isDV() -> kpi.codigoRegion
            role.isGR() -> kpi.codigoZona
            role.isGZ() -> kpi.codigoSeccion
            else -> EMPTY_STRING
        }.takeIf { it.orEmpty().isNotEmpty() }
    }

    private fun getOwnUaLabel(kpi: LlaveUA, role: Rol, isThirdPerson: Boolean): String {
        return when {
            role.isDV() -> textResolver.getCountryGridLabel()
            role.isGR() -> if (isThirdPerson) kpi.codigoRegion.orEmpty() else textResolver.getRegionGridLabel()
            role.isGZ() -> if (isThirdPerson) kpi.codigoZona.orEmpty() else textResolver.getZoneGridLabel()
            else -> EMPTY_STRING
        }
    }

}
