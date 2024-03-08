package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.profile.prepararseesclave

import biz.belcorp.salesforce.core.utils.deleteHyphen
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclave.PrepararseEsClave

class PrepararseEsClaveProvider {

    companion object {
        fun obtenerElementosSocia(): List<PrepararseEsClave> {
            return listOf(
                PrepararseEsClave(Constant.RESULTADOSCX, Constant.TEXT_VENTA, 0, PrepararseEsClave.Tipo.RESULTADOCX, true),
                PrepararseEsClave(Constant.NEGOCIO, Constant.TEXT_NEGOCIO, 1, PrepararseEsClave.Tipo.NEGOCIO, true),
                PrepararseEsClave(Constant.DIGITAL, Constant.TEXT_DIGITAL, 2, PrepararseEsClave.Tipo.DIGITAL, true),
            ).sortedBy { it.orden }
        }

        fun obtenerElementosConsultora(): List<PrepararseEsClave> {
            return listOf(
                PrepararseEsClave(Constant.VENTA, Constant.TEXT_VENTA, 0, PrepararseEsClave.Tipo.VENTA, true),
                PrepararseEsClave(Constant.MASVENDIDO, Constant.TEXT_MASVENDIDO, 1, PrepararseEsClave.Tipo.LOMASVENDIDO, true),
                PrepararseEsClave(Constant.DIGITAL, Constant.TEXT_DIGITAL, 2, PrepararseEsClave.Tipo.DIGITAL, true)
            ).sortedBy { it.orden }
        }

        fun obtenerElementosOtrosRoles(): List<PrepararseEsClave> {
            return listOf()
        }

        fun reemplazarCampaniaEnElementosSocia(campania: String, elementos: List<PrepararseEsClave>): List<PrepararseEsClave> {
            val elementosPrepararseEsClave = mutableListOf<PrepararseEsClave>()
            elementos.forEach {
                val titulo = if (it.tipo == PrepararseEsClave.Tipo.RESULTADOCX) String.format(
                    it.titulo,
                    campania.deleteHyphen()
                ) else it.titulo
                elementosPrepararseEsClave.add(PrepararseEsClave(it.id, titulo, it.orden, it.tipo, it.esVisible))
            }
            return elementosPrepararseEsClave
        }
    }
}
