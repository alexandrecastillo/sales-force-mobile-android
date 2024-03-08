package biz.belcorp.salesforce.modules.digital.features.digital.view.detail.multiprofile.grid

import biz.belcorp.mobile.components.design.spreadsheet.models.Cell
import biz.belcorp.mobile.components.design.spreadsheet.models.Column
import biz.belcorp.mobile.components.design.spreadsheet.objects.CellType
import biz.belcorp.salesforce.core.utils.toUpperCase

class DigitalGridCreator {

    fun getColumns(model: DigitalConsolidatedModel): List<Column> {
        val headers = listOf(
            UaHeader(model.uaTitle),
            DigitalHeaderType.Value(model.valueTitle),
            DigitalHeaderType.ValuePercentage(model.valuePercentageTitle)
        )
        return createGridInformation(headers, model)
    }

    private fun createGridInformation(
        headers: List<HeaderType>,
        model: DigitalConsolidatedModel
    ): List<Column> {
        return headers.map { header ->
            val cells = model.digitalList.map { createCells(header, it) }
            createColumn(header.title, cells)
        }
    }

    private fun createCells(
        headerType: HeaderType,
        model: DigitalInfoGridModel
    ): Cell {
        return when (headerType) {
            is DigitalHeaderType -> createCellForCollection(headerType, model)
            is UaHeader -> createCellForUa(model)
        }
    }

    private fun createCellForCollection(
        headerType: DigitalHeaderType,
        model: DigitalInfoGridModel
    ): Cell {
        return when (headerType) {
            is DigitalHeaderType.Value -> createCell(model.value)
            is DigitalHeaderType.ValuePercentage -> createCell(model.valuePercentage)
        }
    }

    private fun createUaCell(value: String) = createCell(value, type = CellType.ROW_HEADER)

    private fun createCellForUa(model: DigitalInfoGridModel): Cell {
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
