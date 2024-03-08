package biz.belcorp.salesforce.modules.developmentpath.features.profile.cobranzaganancia

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.cobranza.CobranzaYGanancia

class CobranzaGananciaMapper {
    fun parseDataToDomain(cobranzaYGananciaEntity: CobranzaYGanancia): CobranzaYGananciaModel {
        return with(cobranzaYGananciaEntity) {
            CobranzaYGananciaModel(
                campania = campania,
                local = local,
                server = server,
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
}
