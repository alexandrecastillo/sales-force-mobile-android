package biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.view.detail.multiprofile.sales.consolidated.core

import androidx.annotation.StringRes
import biz.belcorp.mobile.components.design.spreadsheet.models.Cell
import biz.belcorp.mobile.components.design.spreadsheet.models.Column
import biz.belcorp.mobile.components.design.spreadsheet.objects.CellType
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.constants.Constant.HYPHEN
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.kpis.R
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.capitalization.CapitalizationIndicator
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.CapitalizationTextResolver
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.view.detail.multiprofile.sales.consolidated.model.CapitalizationConsolidated
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.filtergrid.types.CapitalizationGridType


class CapitalizationGridCreator(
    private val textResolver: CapitalizationTextResolver
) {
    companion object {
        fun init(textResolver: CapitalizationTextResolver) =
            CapitalizationGridCreator(textResolver)
    }

    fun getColumns(
        model: CapitalizationConsolidated,
        @CapitalizationGridType gridType: String
    ): List<Column> {
        return when (gridType) {
            CapitalizationGridType.PEGS -> getPEGsColumns(model)
            CapitalizationGridType.CAPITALIZATION -> getCapitalizationColumns(model)
            else -> emptyList()
        }
    }

    private fun getPEGsColumns(model: CapitalizationConsolidated): List<Column> {
        val campaign = model.currentCampaign.shortNameOnly
        val pegsResId = if (model.isBilling) R.string.pegs_column_title else R.string.pegs
        val pegsColumn = PEGsHeaderType.PEGs(
            getStringValue(pegsResId).appendIf(!model.isBilling, campaign, true)
        )
        val pegsPercentageColumn = PEGsHeaderType.RetentionPEGs(
            getStringValue(R.string.title_retention_pegs).appendIf(!model.isBilling, campaign)
        )

        val headers = mutableListOf(UaHeader(getHeaderLabel(model.role)), pegsColumn)
        headers.addIf(model.isBilling, pegsPercentageColumn)

        return createGridInformation(headers, model)
    }

    private fun getCapitalizationColumns(model: CapitalizationConsolidated): List<Column> {
        val campaign = model.campaign.shortNameOnly
        val capitalizationResId =
            if (model.isBilling) R.string.capitalization_projected else R.string.capitalization
        val headers = listOf(
            UaHeader(getHeaderLabel(model.role)),
            CapitalizationHeaderType.Entries(
                getStringValue(R.string.incomes).appendIf(
                    !model.isBilling,
                    campaign,
                    true
                )
            ),
            CapitalizationHeaderType.Reentries(
                getStringValue(R.string.reentries).appendIf(
                    !model.isBilling,
                    campaign,
                    true
                )
            ),
            CapitalizationHeaderType.Expenses(
                getStringValue(R.string.expenses).appendIf(
                    !model.isBilling,
                    campaign,
                    true
                )
            ),
            CapitalizationHeaderType.Capitalization(
                getStringValue(capitalizationResId).appendIf(
                    !model.isBilling,
                    campaign,
                    true
                )
            ),
            CapitalizationHeaderType.CapitalizationGoal(
                getStringValue(R.string.text_goal).appendIf(
                    !model.isBilling,
                    campaign,
                    true
                )
            )
        )
        return createGridInformation(headers, model)
    }

    private fun createGridInformation(
        headers: List<HeaderType>,
        model: CapitalizationConsolidated
    ): List<Column> {
        return headers.map { header ->
            val cells = model.pairData.map { createCells(model.role, model.isBilling, header, it) }
            createColumn(header.title, cells)
        }
    }

    private fun createCells(
        role: Rol,
        isBilling: Boolean,
        headerType: HeaderType,
        model: Pair<LlaveUA, CapitalizationIndicator?>
    ): Cell {
        return when (headerType) {
            is PEGsHeaderType -> createCellForPEGs(headerType, model, isBilling)
            is CapitalizationHeaderType -> createCellsForCapitalization(headerType, model)
            is UaHeader -> createCellForUa(role, model.first)
        }
    }

    private fun createCellForPEGs(
        headerType: PEGsHeaderType,
        model: Pair<LlaveUA, CapitalizationIndicator?>,
        isBilling: Boolean
    ): Cell {
        val entity = model.second ?: return createCell(HYPHEN)
        return when (headerType) {
            is PEGsHeaderType.PEGs  -> {
                if (isBilling)
                    createCell((entity.pegRetentionRemaining).toString())
                else
                    createCell((entity.pegsReal).toString())
            }
            is PEGsHeaderType.RetentionPEGs -> createCell(
                textResolver.formatPercentage(
                    entity.pegRetentionPercentage.toPercentageFormat()
                )
            )
        }
    }

    private fun createCellsForCapitalization(
        headerType: CapitalizationHeaderType,
        model: Pair<LlaveUA, CapitalizationIndicator?>
    ): Cell {
        val entity = model.second ?: return createCell(HYPHEN)
        return when (headerType) {
            is CapitalizationHeaderType.Entries -> createCell(entity.capitalizationEntries.toString())
            is CapitalizationHeaderType.Reentries -> createCell(entity.capitalizationReentries.toString())
            is CapitalizationHeaderType.Expenses -> createCell(entity.capitalizationExpenses.toString())
            is CapitalizationHeaderType.Capitalization -> createCell(entity.capitalizationReal.toString())
            is CapitalizationHeaderType.CapitalizationGoal -> createCell(entity.capitalizationGoal.toString())
        }
    }

    private fun createColumn(title: String, items: List<Cell>): Column {
        val titleCell = Cell(value = title.toUpperCase(), type = CellType.HEADER_COLUMN)
        return Column(title = titleCell, values = items, isRowHeader = false)
    }

    private fun createCell(value: String, type: Int = CellType.DATA): Cell {
        return Cell(value = value, type = type)
    }

    private fun createUaCell(value: String) = createCell(value, type = CellType.ROW_HEADER)

    private fun createCellForUa(role: Rol, ua: LlaveUA): Cell {
        return if (ua.roleAssociated == role) createUaCell(getParentLabel(role))
        else createUaCell(getChildrenLabel(ua, ua.roleAssociated))
    }

    private fun getChildrenLabel(uaKey: LlaveUA, role: Rol): String = with(role) {
        return when {
            isGR() -> uaKey.codigoRegion.orEmpty()
            isGZ() -> uaKey.codigoZona.orEmpty()
            isSE() -> uaKey.codigoSeccion.orEmpty()
            else -> Constant.EMPTY_STRING
        }
    }

    private fun getParentLabel(role: Rol): String = with(role) {
        return when {
            isDV() -> getStringValue(R.string.your_country)
            isGR() -> getStringValue(R.string.your_region)
            isGZ() -> getStringValue(R.string.your_zone)
            else -> Constant.EMPTY_STRING
        }
    }

    private fun getHeaderLabel(role: Rol): String = with(role) {
        return when {
            isDV() -> textResolver.context.getString(R.string.text_country_and_regions)
            isGR() -> textResolver.context.getString(R.string.text_region_and_zones)
            isGZ() -> textResolver.context.getString(R.string.text_zone_and_sections)
            else -> Constant.EMPTY_STRING
        }
    }

    private fun getStringValue(@StringRes resId: Int, vararg args: Any?) =
        textResolver.context.getString(resId, *args)
}
