package biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.view.detail.multiprofile.sales.consolidated.grid

import biz.belcorp.mobile.components.design.spreadsheet.models.Column
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.CapitalizationTextResolver
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.view.detail.multiprofile.sales.consolidated.core.CapitalizationGridCreator
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.view.detail.multiprofile.sales.consolidated.model.CapitalizationConsolidated
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.model.SaleOrderGridType

class GridCapitalizationMapper(val textResolver: CapitalizationTextResolver) {

    private val gridCreator by lazy {
        CapitalizationGridCreator.init(textResolver)
    }

    fun map(entity: CapitalizationConsolidated, @SaleOrderGridType type: String): List<Column> {
        return gridCreator.getColumns(entity, type)
    }

}
