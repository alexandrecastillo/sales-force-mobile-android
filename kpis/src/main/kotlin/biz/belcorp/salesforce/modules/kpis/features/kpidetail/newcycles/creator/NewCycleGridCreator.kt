package biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.creator

import androidx.annotation.StringRes
import biz.belcorp.mobile.components.design.spreadsheet.models.Cell
import biz.belcorp.mobile.components.design.spreadsheet.models.Column
import biz.belcorp.mobile.components.design.spreadsheet.objects.CellType
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.constants.Constant.HYPHEN
import biz.belcorp.salesforce.core.domain.entities.ua.UaInfo
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.kpis.R
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.saleorders.GridUaInfo
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.NewCycleTextResolver
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.model.NewCycleConsolidated
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.model.NewCycleGridIndicatorModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.view.detail.multiprofile.consolidated.grid.HeaderType
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.view.detail.multiprofile.consolidated.grid.HighValueHeaderType
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.view.detail.multiprofile.consolidated.grid.LowValueHeaderType
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.view.detail.multiprofile.consolidated.grid.UaHeader
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.filtergrid.types.NewCycleGridType
import kotlin.text.toUpperCase

class NewCycleGridCreator(private val textResolver: NewCycleTextResolver) {

    fun getColumns(
        model: NewCycleConsolidated,
        @NewCycleGridType gridType: String
    ): List<Column> {
        return when (gridType) {
            NewCycleGridType.LOW_VALUE -> getLowValueColumns(model)
            NewCycleGridType.HIGH_VALUE -> getHighValueColumns(model)
            else -> emptyList()
        }
    }

    private fun getLowValueColumns(model: NewCycleConsolidated): List<Column> {
        val headers = listOf(
            UaHeader(getHeaderLabel(model.role)),
            LowValueHeaderType.Segment6d6(getStringValue(R.string.text_kpi_newcycle_possible_6d6)),
            LowValueHeaderType.Segment5d5(getStringValue(R.string.text_kpi_newcycle_possible_5d5)),
            LowValueHeaderType.Segment4d4(getStringValue(R.string.text_kpi_newcycle_possible_4d4)),
            LowValueHeaderType.Segment3d3(getStringValue(R.string.text_kpi_newcycle_possible_3d3)),
            LowValueHeaderType.Segment2d2(getStringValue(R.string.text_kpi_newcycle_possible_2d2))
        )
        return createGridInformation(headers, model)
    }

    private fun getHighValueColumns(model: NewCycleConsolidated): List<Column> {
        val headers = arrayListOf(
            UaHeader(getHeaderLabel(model.role)),
            HighValueHeaderType.Segment6d6(getStringValue(R.string.text_kpi_newcycle_possible_6d6_high_value)),
            HighValueHeaderType.Segment5d5(getStringValue(R.string.text_kpi_newcycle_possible_5d5_high_value))
        )
        if (model.isBilling) {
            val highValueSegment4d4 =
                HighValueHeaderType.Segment4d4(getStringValue(R.string.text_kpi_newcycle_possible_4d4_high_value))
            headers.add(highValueSegment4d4)
        }
        return createGridInformation(headers, model)
    }

    private fun createGridInformation(
        headers: List<HeaderType>,
        model: NewCycleConsolidated
    ): List<Column> {
        return headers.map { header ->
            val cells = model.pairData.map { createCells(header, model.isParent, it) }
            createColumn(header.title, cells)
        }
    }

    private fun createCells(
        headerType: HeaderType,
        isParent: Boolean,
        model: Pair<UaInfo, NewCycleGridIndicatorModel?>
    ): Cell {
        return when (headerType) {
            is LowValueHeaderType -> createCellForLowValue(headerType, model)
            is HighValueHeaderType -> createCellsForHighValue(headerType, model)
            is UaHeader -> createCellForUa(model.first, isParent)
        }
    }

    private fun createCellForLowValue(
        headerType: LowValueHeaderType,
        model: Pair<UaInfo, NewCycleGridIndicatorModel?>
    ): Cell {
        val entity = model.second ?: return createCell(HYPHEN)
        return when (headerType) {
            is LowValueHeaderType.Segment6d6 -> createCell(entity.lowValue6d6)
            is LowValueHeaderType.Segment5d5 -> createCell(entity.lowValue5d5)
            is LowValueHeaderType.Segment4d4 -> createCell(entity.lowValue4d4)
            is LowValueHeaderType.Segment3d3 -> createCell(entity.lowValue3d3)
            is LowValueHeaderType.Segment2d2 -> createCell(entity.lowValue2d2)
        }
    }

    private fun createCellsForHighValue(
        headerType: HighValueHeaderType,
        model: Pair<UaInfo, NewCycleGridIndicatorModel?>
    ): Cell {
        val entity = model.second ?: return createCell(HYPHEN)
        return when (headerType) {
            is HighValueHeaderType.Segment6d6 -> createCell(entity.highValue6d6)
            is HighValueHeaderType.Segment5d5 -> createCell(entity.highValue5d5)
            is HighValueHeaderType.Segment4d4 -> createCell(entity.highValue4d4)
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

    private fun createCellForUa(model: UaInfo, isParent: Boolean): Cell = with(model) {
        return if (!isThirdPerson && isParent) createUaCell(getParentLabel(role))
        else createUaCell(getChildrenLabel(uaKey, role))
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

