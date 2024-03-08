package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.cobranza

import biz.belcorp.salesforce.core.entities.sql.datos.CobranzaYGananciaEntity
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.cobranza.CobranzaYGanancia

class CobranzaYGananciaDataMapper {

    fun parseService(cobranzaYGananciaEntity: CobranzaYGananciaEntity): CobranzaYGanancia {
        return with(cobranzaYGananciaEntity) {
            CobranzaYGanancia(
                campania = campania,
                server = true,
                consultorasDeuda = consultorasDeuda,
                ganancia6d6 = ganancia6d6,
                gananciaCambioNivel = gananciaCambioNivel,
                gananciaPedidoAltoValor = gananciaPedidoAltoValor,
                gananciaTotal = gananciaTotal,
                montoFacturadoNeto = montoFacturadoNeto,
                montoRecuperado = montoRecuperado,
                recuperacion = recuperacion,
                retencionVSC = retencionVSC,
                region = region,
                saldoDeuda = saldoDeuda,
                seccion = seccion,
                zona = zona
            )
        }
    }

    fun parseLocal(cobranzaYGananciaEntity: CobranzaYGananciaEntity): CobranzaYGanancia {
        return with(cobranzaYGananciaEntity) {
            CobranzaYGanancia(
                campania = campania,
                local = true,
                consultorasDeuda = consultorasDeuda,
                ganancia6d6 = ganancia6d6,
                gananciaCambioNivel = gananciaCambioNivel,
                gananciaPedidoAltoValor = gananciaPedidoAltoValor,
                gananciaTotal = gananciaTotal,
                montoFacturadoNeto = montoFacturadoNeto,
                montoRecuperado = montoRecuperado,
                recuperacion = recuperacion,
                region = region,
                saldoDeuda = saldoDeuda,
                seccion = seccion,
                zona = zona
            )
        }
    }
}
