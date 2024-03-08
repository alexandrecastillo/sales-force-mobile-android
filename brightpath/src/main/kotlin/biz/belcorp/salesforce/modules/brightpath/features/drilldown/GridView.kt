package biz.belcorp.salesforce.modules.brightpath.features.drilldown

import biz.belcorp.salesforce.modules.brightpath.features.drilldown.model.ConsultantHeaderDetailedKpiModel


interface GridView {
    fun drawUAListView()
    fun setupGridView(items: List<ConsultantHeaderDetailedKpiModel>)
    fun setListenerForGz()
}
