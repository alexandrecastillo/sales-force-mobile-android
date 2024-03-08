package biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.model

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.model.GridModel

class ValueAttributes(
    val value: String,
    val textAppearanceInt: Int
)

sealed class CoupledModel {

    class SingleItem(
        val label: ValueAttributes,
        val value: ValueAttributes
    ) : CoupledModel()

    open class GridsItemModel(
        val kpiValues: List<GridModel>
    ) : CoupledModel()

    class GridWithHeaderItemModel(
        val header: SingleItem,
        val kpiValues: List<GridModel>,
        val spanItems: Int = Constant.NUMBER_THREE,
        val hasDividerDecoration: Boolean = true
    ) : CoupledModel()

}





