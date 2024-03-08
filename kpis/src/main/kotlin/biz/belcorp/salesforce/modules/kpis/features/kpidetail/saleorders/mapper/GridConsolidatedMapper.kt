package biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.mapper

import biz.belcorp.mobile.components.design.spreadsheet.models.Column
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.saleorders.SaleOrdersConsolidated
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.SaleOrdersTextResolver
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.model.SaleOrderGridType
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.view.detail.multiprofile.items.consolidated.grid.creator.SaleOrderGridCreator

class GridConsolidatedMapper(private val textResolver: SaleOrdersTextResolver) {

    private val gridCreator by lazy { SaleOrderGridCreator.init(textResolver) }

    fun map(entity: SaleOrdersConsolidated, @SaleOrderGridType type: String): List<Column> {
        return gridCreator.getColumns(entity, type)
    }

}
