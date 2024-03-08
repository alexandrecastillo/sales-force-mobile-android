package biz.belcorp.salesforce.modules.developmentpath.core.domain.utils.validator

import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.utils.before
import biz.belcorp.salesforce.core.utils.finDelDia
import java.util.*

class ValidadorTiempos(private val campania: Campania) {

    fun validar(fecha: Date) {
        if (campania.inicio!!.after(fecha) || campania.fin!!.finDelDia() before fecha) {
            throw ExcepcionAlValidarTiemposCampania()
        }
    }

    fun validarHoy() {
        validar(Date())
    }

    class ExcepcionAlValidarTiemposCampania(
        mensaje: String = "La fecha establecida debe estar dentro de la campaña actual"
    ) : Exception(mensaje)
}
