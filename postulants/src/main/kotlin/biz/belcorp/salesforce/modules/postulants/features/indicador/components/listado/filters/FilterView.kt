package biz.belcorp.salesforce.modules.postulants.features.indicador.components.listado.filters

import biz.belcorp.salesforce.modules.postulants.features.entities.FiltroAprobadoUneteModel

interface FilterView {
    fun showList(list: List<FiltroAprobadoUneteModel>)
    fun showPaymentTypeList(list: List<FiltroAprobadoUneteModel>)
}
