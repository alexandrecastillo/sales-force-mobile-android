package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.presenter

import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.model.AgregarEventoViewModel

class RolHandler(private val viewHandler: AgregarEventoViewHandler,
                 private val model: AgregarEventoViewModel) {

    fun manejarRespuesta(rol: Rol) {
        model.rolCreador = rol
        viewHandler.pintarAvisoCompartir()
        viewHandler.pintarTextoCheckboxCompartir()
    }
}
