package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.section

import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclave.PrepararseEsClave

class PrepararseEsClaveMapper {

    fun parse(elemento: PrepararseEsClave): PrepararseEsClaveModel {
        return PrepararseEsClaveModel(
            id = elemento.id,
            icon = obtenerIcono(elemento.tipo),
            posicion = elemento.orden,
            esVisible = elemento.esVisible,
            tipo = elemento.tipo,
            titulo = elemento.titulo
        )
    }

    fun parse(elementos: List<PrepararseEsClave>): List<PrepararseEsClaveModel> {
        val modelosPrepararseEsClave = arrayListOf<PrepararseEsClaveModel>()
        for ((indice, elemento) in elementos.withIndex()) {
            modelosPrepararseEsClave.add(parse(elemento, indice))
        }
        return modelosPrepararseEsClave
    }

    private fun parse(elemento: PrepararseEsClave, posicion: Int): PrepararseEsClaveModel {
        return PrepararseEsClaveModel(
            id = elemento.id,
            icon = obtenerIcono(elemento.tipo),
            posicion = posicion,
            esVisible = elemento.esVisible,
            tipo = elemento.tipo,
            titulo = elemento.titulo
        )
    }

    private fun obtenerIcono(tipo: PrepararseEsClave.Tipo): Int {
        return when (tipo) {
            PrepararseEsClave.Tipo.VENTA, PrepararseEsClave.Tipo.RESULTADOCX -> R.drawable.ic_venta
            PrepararseEsClave.Tipo.LOMASVENDIDO -> R.drawable.ic_carrito
            PrepararseEsClave.Tipo.DIGITAL -> R.drawable.ic_celular
            PrepararseEsClave.Tipo.ACUERDOSU3C -> R.drawable.ic_acuerdos
            PrepararseEsClave.Tipo.NEGOCIO -> R.drawable.ic_negocio
        }
    }
}

