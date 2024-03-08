package biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.view.detail.multiprofile.sales.consolidated.core

import biz.belcorp.mobile.components.design.spreadsheet.models.Cell
import biz.belcorp.mobile.components.design.spreadsheet.models.Column
import biz.belcorp.mobile.components.design.spreadsheet.objects.CellType
import biz.belcorp.salesforce.core.utils.toUpperCase
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.view.detail.multiprofile.sales.consolidated.model.HeaderTitleGridModel

abstract class GridKpiCreator<H : HeaderTitleGridModel, L> {

    abstract fun getColumns(headerTitles: H, items: L): List<Column>

    fun createCell(value: String): Cell = Cell(value = value, type = CellType.DATA)

    fun createColumn(title: String, items: MutableList<Cell>): Column {
        val titleCell = Cell(value = title.toUpperCase(), type = CellType.HEADER_COLUMN)
        return Column(title = titleCell, values = items, isRowHeader = false)
    }

}
