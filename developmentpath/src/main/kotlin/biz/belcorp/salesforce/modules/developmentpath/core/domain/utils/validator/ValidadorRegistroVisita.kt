package biz.belcorp.salesforce.modules.developmentpath.core.domain.utils.validator

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas.Visita

class ValidadorRegistroVisita(private val visita: Visita) {

    fun validarRegistroHoy() {
        if (!visita.persona.puedeRegistrarVisitaHoy)
            throw ExcepcionAlValidarCantidadRegistrosEnDia()
    }

    class ExcepcionAlValidarCantidadRegistrosEnDia(
        mensaje: String = "No se puede registrar dos visitas el mismo día."
    ) : Exception(mensaje)
}
