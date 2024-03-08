package biz.belcorp.salesforce.modules.postulants.features.indicador.components.resumen

import biz.belcorp.salesforce.modules.postulants.features.indicador.base.LoadDataView
import biz.belcorp.salesforce.modules.postulants.features.indicador.components.resumen.entities.DetalleIndicadorModel

interface ResumenConsolidadoView : LoadDataView {
    fun showConsolidado(list: List<DetalleIndicadorModel>?)
    fun showGrFilters()
    fun onLoadGROptions()
}
