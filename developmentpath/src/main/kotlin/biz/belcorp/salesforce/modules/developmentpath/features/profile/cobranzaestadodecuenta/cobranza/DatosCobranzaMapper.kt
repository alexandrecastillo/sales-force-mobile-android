package biz.belcorp.salesforce.modules.developmentpath.features.profile.cobranzaestadodecuenta.cobranza

import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.cobranza.ObtenerDatosCobranzaUseCase

class DatosCobranzaMapper {
    fun parse(entity: ObtenerDatosCobranzaUseCase.DatosCobranza) = DatosCobranzaModel(
        ventaGanancia = entity.ventaGanancia,
        consultoraConsecutiva = entity.consultoraConsecutiva,
        gananciaVentaRetail = entity.gananciaVentaRetail,
        ganancia = entity.ganancia,
        recaudoTotal = entity.recaudoTotal,
        ventaRetail = entity.ventaRetail,
        ventaFacturada = entity.ventaFacturada,
        recaudoComisionable = entity.recaudoComisionable,
        saldoPendiente = entity.saldoPendiente,
        recaudoNoComisionable = entity.recaudoNoComisionable
    )
}
