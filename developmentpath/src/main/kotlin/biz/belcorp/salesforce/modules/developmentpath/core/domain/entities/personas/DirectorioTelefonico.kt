package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas

import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.session.ProcesadorPrefijoTelefonico
import biz.belcorp.salesforce.core.utils.obtenerGuionONumeroConPrefijo
import java.io.Serializable

class DirectorioTelefonico(
    val celular: Telefono?,
    val casa: Telefono?,
    val otro: Telefono?
) : Serializable {

    var prefijo: String? = null

    var procesadorPrefijoTelefonico: ProcesadorPrefijoTelefonico? = null

    fun configurarPrefijo(prefijo: String) {
        procesadorPrefijoTelefonico =
            ProcesadorPrefijoTelefonico(
                prefijo
            )
    }

    val celularConPrefijo: Telefono?
        get() {
            return procesarTelefono(celular)
        }

    val casaConPrefijo: Telefono?
        get() {
            return procesarTelefono(casa)
        }

    val otroConPrefijo: Telefono?
        get() {
            return procesarTelefono(otro)
        }

    fun obtenerFavorito(): Telefono? {
        var favorito: Telefono? = celular
        casa?.let { if (it.esFavorito) favorito = it }
        otro?.let { if (it.esFavorito) favorito = it }
        return favorito
    }

    fun obtenerFavoritoConPrefijo(): Telefono? {
        return procesarTelefono(obtenerFavorito())
    }

    private fun procesarTelefono(telefono: Telefono?): Telefono? {
        if (telefono == null) return null
        if (prefijo == null) return telefono
        val numero = telefono.numero.obtenerGuionONumeroConPrefijo(prefijo)
        return Telefono.construir(
            numero,
            telefono.esFavorito
        )
    }

    class Telefono(
        val numero: String,
        val esFavorito: Boolean
    ) : Serializable {

        companion object {
            fun construir(
                numero: String?,
                esFavorito: Boolean = false
            ): Telefono? {
                if (numero == null) return null

                return Telefono(
                    numero,
                    esFavorito
                )
            }

            fun construirFavorito(numero: String?): Telefono? {
                return construir(
                    numero,
                    true
                )
            }
        }
    }

    companion object {
        fun construirDummy() =
            DirectorioTelefonico(
                null,
                null,
                null
            )
    }
}
