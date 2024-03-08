package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.navegacion

import biz.belcorp.salesforce.core.domain.entities.zonificacion.UnidadAdministrativa
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.diferenciaDeNivel

open class BarraNavegacion(val niveles: List<Nivel>) {

    class Nivel(val ua: UnidadAdministrativa)

    val tipoBarra
        get() = when {
            niveles.isEmpty() -> TipoBarra.SIN_BARRA
            niveles.size == 1 -> TipoBarra.NIVEL_UNICO
            niveles.isNotEmpty() -> TipoBarra.MULTINIVEL
            else -> throw Exception("Error al obtener tipo de barra")
        }

    companion object {
        fun construir(
            rolSesion: Rol,
            rolDuenioRuta: Rol,
            unidadAdministrativa: UnidadAdministrativa
        ): BarraNavegacion {
            val niveles = mutableListOf<Nivel>()
            val diferenciaNiveles = rolSesion diferenciaDeNivel rolDuenioRuta
            var unidadAdministrativaNivel = unidadAdministrativa

            for (i in 0 until diferenciaNiveles) {
                val nivel = Nivel(unidadAdministrativaNivel)
                niveles.add(nivel)
                unidadAdministrativaNivel = unidadAdministrativaNivel.padre ?: break
            }

            return BarraNavegacion(niveles.asReversed())
        }
    }

    enum class TipoBarra {
        SIN_BARRA, NIVEL_UNICO, MULTINIVEL
    }
}
