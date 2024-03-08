package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.session

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.DirectorioTelefonico

class ProcesadorPrefijoTelefonico(private val prefijo: String) {

    fun procesar(telefono: DirectorioTelefonico.Telefono): DirectorioTelefonico.Telefono? {
        val numero = obtenerGuionONumeroConPrefijo(telefono.numero)

        return DirectorioTelefonico.Telefono.construir(numero, telefono.esFavorito)
    }

    fun obtenerGuionONumeroConPrefijo(numero: String?): String {
        return if (numero.isNullEmptyOrDashes()) {
            Constant.HYPHEN
        } else {
            obtenerNumeroConPrefijo(numero!!)
        }
    }

    private fun obtenerNumeroConPrefijo(numero: String): String {
        return numero.takeIf { it.startsWith("+") } ?: agregarPrefijo(numero)
    }

    private fun agregarPrefijo(numero: String): String {
        return "+$prefijo $numero"
    }

    private fun String?.isNullEmptyOrDashes() =
        this.isNullOrEmpty() || this.all { it.toString() == Constant.HYPHEN }
}
