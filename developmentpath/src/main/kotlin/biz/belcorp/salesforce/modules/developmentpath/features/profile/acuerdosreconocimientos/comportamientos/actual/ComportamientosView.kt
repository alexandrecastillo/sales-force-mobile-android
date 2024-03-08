package biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.comportamientos.actual

import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.comportamientos.model.ComportamientoModel

interface ComportamientosView {
    fun pintarReconocimientos(comportamientos: List<ComportamientoModel>)
    fun ocultarReconocimientos()
    fun pintarCumplimiento(razon: String)
    fun pintarProgreso(porcentaje: Float)
}
