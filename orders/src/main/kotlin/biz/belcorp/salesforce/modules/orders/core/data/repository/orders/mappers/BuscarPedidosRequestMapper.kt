package biz.belcorp.salesforce.modules.orders.core.data.repository.orders.mappers

import biz.belcorp.salesforce.core.utils.changeDateFormat
import biz.belcorp.salesforce.core.utils.formatShort2
import biz.belcorp.salesforce.core.utils.formatShort3
import biz.belcorp.salesforce.modules.orders.core.data.repository.orders.dto.BuscarPedidoRequest
import biz.belcorp.salesforce.modules.orders.core.domain.entities.orders.FiltroPedidosWeb

class BuscarPedidosRequestMapper {

    fun mapSE(filtroPedidosWeb: FiltroPedidosWeb): BuscarPedidoRequest {
        return BuscarPedidoRequest().apply {
            tipoFiltro = filtroPedidosWeb.tipoFiltro.orEmpty()
            pais = filtroPedidosWeb.pais.orEmpty()
            campania = filtroPedidosWeb.campania.orEmpty()
            consultoraLiderId = filtroPedidosWeb.consultoraLiderId.orEmpty()
            seccion = filtroPedidosWeb.seccion.orEmpty()
            estadoPedido = filtroPedidosWeb.estadoPedido
            fechaDesde = filtroPedidosWeb.fechaDesde.orEmpty()
            fechaHasta = filtroPedidosWeb.fechaHasta.orEmpty()
            codigoConsultora = filtroPedidosWeb.codigoConsultora.orEmpty()
            nombreConsultora = filtroPedidosWeb.nombreConsultora.orEmpty()
            segmentoId = filtroPedidosWeb.segmentoId
            deuda = obtenerDeudaValue(filtroPedidosWeb.deudaId).orEmpty()
        }
    }

    fun map(filtroPedidosWeb: FiltroPedidosWeb): HashMap<String, String> {
        val map = hashMapOf<String, String>()
        filtroPedidosWeb.seccion?.also { map[SECCION_KEY] = it }
        filtroPedidosWeb.estadoPedido?.toString()?.also { map[ESTADO_KEY] = it }
        formatearFecha(filtroPedidosWeb.fechaDesde)?.also { map[FECHA_DESDE_KEY] = it }
        formatearFecha(filtroPedidosWeb.fechaHasta)?.also { map[FECHA_HASTA_KEY] = it }
        filtroPedidosWeb.origen.orEmpty().also { map[ORIGEN_KEY] = it }
        filtroPedidosWeb.codigoConsultora?.also { map[CODIGO_KEY] = it }
        filtroPedidosWeb.nombreConsultora?.also { map[NOMBRE_KEY] = it }
        filtroPedidosWeb.segmentoId?.toString()?.also { map[SEGMENTO_KEY] = it }
        obtenerDeudaValue(filtroPedidosWeb.deudaId)?.also { map[DEUDA_KEY] = it }
        return map
    }

    private fun obtenerDeudaValue(deudaId: Int?): String? {
        return when (deudaId) {
            SIN_DEUDA_ID -> SIN_DEUDA_VALUE
            CON_DEUDA_ID -> CON_DEUDA_VALUE
            else -> null
        }
    }

    private fun formatearFecha(fecha: String?): String? {
        return fecha?.changeDateFormat(formatShort2, formatShort3)
    }

    companion object {

        private const val SIN_DEUDA_ID = 0
        private const val CON_DEUDA_ID = 1
        private const val SIN_DEUDA_VALUE = "N"
        private const val CON_DEUDA_VALUE = "Y"

        private const val SECCION_KEY = "seccion"
        private const val ESTADO_KEY = "estado"
        private const val FECHA_DESDE_KEY = "fechaDesde"
        private const val FECHA_HASTA_KEY = "fechaHasta"
        private const val ORIGEN_KEY = "origen"
        private const val CODIGO_KEY = "codigoConsultora"
        private const val NOMBRE_KEY = "nombreConsultora"
        private const val SEGMENTO_KEY = "segmento"
        private const val DEUDA_KEY = "deuda"

    }

}
