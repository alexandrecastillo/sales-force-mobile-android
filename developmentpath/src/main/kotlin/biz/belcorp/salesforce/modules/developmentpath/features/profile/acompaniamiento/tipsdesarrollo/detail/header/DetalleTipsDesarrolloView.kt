package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.header

import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.section.TipDesarrolloModel

interface DetalleTipsDesarrolloView {
    fun cargarOpciones(opciones: List<TipDesarrolloModel>)
    fun moverAOpcionSeleccionada()
    fun establecerOpcionSeleccionada()
    fun redirigirFragmentoSeleccionado()
}
