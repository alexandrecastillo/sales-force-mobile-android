package biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.detail.multiprofile.items.consolidated.grid.creator

import biz.belcorp.mobile.components.design.spreadsheet.models.Cell
import biz.belcorp.mobile.components.design.spreadsheet.models.Column
import biz.belcorp.mobile.components.design.spreadsheet.objects.CellType
import biz.belcorp.salesforce.core.utils.toUpperCase
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.model.CollectionConsolidatedModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.model.CollectionGridIndicatorModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.detail.multiprofile.consolidated.grid.CollectionHeaderType
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.detail.multiprofile.consolidated.grid.HeaderType
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.detail.multiprofile.consolidated.grid.UaHeader

class GainGridCreator {

    fun getColumns(model: CollectionConsolidatedModel): List<Column> {
        val headers = listOf(
            UaHeader(model.uaTitle),
            CollectionHeaderType.Recovery(model.recoveryTitle),
            CollectionHeaderType.Invoiced(model.invoicedTitle),
            CollectionHeaderType.Collected(model.collectedTitle)
        )
        return createGridInformation(headers, model)
    }

    private fun createGridInformation(
        headers: List<HeaderType>,
        model: CollectionConsolidatedModel
    ): List<Column> {
        return headers.map { header ->
            val cells = model.collectionList.map { createCells(header, it) }
            createColumn(header.title, cells)
        }
    }

    private fun createCells(
        headerType: HeaderType,
        model: CollectionGridIndicatorModel
    ): Cell {
        return when (headerType) {
            is CollectionHeaderType -> createCellForCollection(headerType, model)
            is UaHeader -> createCellForUa(model)
        }
    }

    private fun createCellForCollection(
        headerType: CollectionHeaderType,
        model: CollectionGridIndicatorModel
    ): Cell {
        return when (headerType) {
            is CollectionHeaderType.Recovery -> createCell(model.recoveryValue)
            is CollectionHeaderType.Invoiced -> createCell(model.invoicedValue)
            is CollectionHeaderType.Collected -> createCell(model.collectedValue)
        }
    }

    private fun createUaCell(value: String) = createCell(value, type = CellType.ROW_HEADER)

    private fun createCellForUa(model: CollectionGridIndicatorModel): Cell {
        return createUaCell(model.uaLabel)
    }

    private fun createCell(value: String, type: Int = CellType.DATA): Cell {
        return Cell(value = value, type = type)
    }

    private fun createColumn(title: String, items: List<Cell>): Column {
        val titleCell = Cell(value = title.toUpperCase(), type = CellType.HEADER_COLUMN)
        return Column(title = titleCell, values = items, isRowHeader = false)
    }

}
