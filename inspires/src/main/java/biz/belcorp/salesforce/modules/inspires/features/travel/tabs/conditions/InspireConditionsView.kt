package biz.belcorp.salesforce.modules.inspires.features.travel.tabs.conditions

import biz.belcorp.salesforce.modules.inspires.model.InspiraCondicionesLeyendaDetalleModel
import biz.belcorp.salesforce.modules.inspires.model.InspiraCondicionesLeyendaModel
import biz.belcorp.salesforce.modules.inspires.model.InspiraCondicionesModel

interface InspireConditionsView {
    fun showHeadeIcon(active: Boolean)
    fun showHeaderMessage(active: Boolean, name: String?)
    fun hideRecommendatioonTitle()
    fun showConditionsList(list: List<InspiraCondicionesModel>)
    fun hideLegendTitle()
    fun showDetail(leyenda: List<InspiraCondicionesLeyendaModel>, detalle: List<InspiraCondicionesLeyendaDetalleModel>)
}
