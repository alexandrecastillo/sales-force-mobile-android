package biz.belcorp.salesforce.modules.calculator.core.data.repository.partnervariable.mapper

import biz.belcorp.salesforce.core.entities.calculator.PartnerVariableEntity
import biz.belcorp.salesforce.modules.calculator.core.domain.entities.PartnerVariable

class PartnerVariableEntityDataMapper {

    fun parsePartnerVariable(entity: PartnerVariableEntity): PartnerVariable {

        return entity.let {
            PartnerVariable(
                nivelID = it.nivelID,
                indicadorNuevaLider = it.indicadorNuevaLider,
                metaPedido = it.metaPedido,
                metaVenta = it.metaVenta,
                numeroActivasIniciales = it.numeroActivasIniciales,
                porcentajeMetaRecuperacion = it.porcentajeMetaRecuperacion,
                promedioVentaPedidosAV = it.promedioVentaPedidosAV,
                promedioVentaPedidosBV = it.promedioVentaPedidosBV,
                nivelCambioCampania = it.nivelCambioCampania,
                metaIngresos = it.metaIngresos,
                metaCapitalizacion = it.metaCapitalizacion,
                indicadorMedicionVariable = it.indicadorMedicionVariable,
                indicadorMedicionMeta = it.indicadorMedicionMeta,
                porcentajeComisionEXAV = it.porcentajeComisionEXAV,
                porcentajeComisionEXBV = it.porcentajeComisionEXBV,
                porcentajeComisionNEAV = it.porcentajeComisionNEAV,
                porcentajeComisionNEBV = it.porcentajeComisionNEBV,
                bonoCambioNivel = it.bonoCambioNivel)
        }

    }
}
