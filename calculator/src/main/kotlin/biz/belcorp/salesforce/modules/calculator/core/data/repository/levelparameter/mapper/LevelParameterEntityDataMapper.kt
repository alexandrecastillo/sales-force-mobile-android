package biz.belcorp.salesforce.modules.calculator.core.data.repository.levelparameter.mapper

import biz.belcorp.salesforce.core.entities.calculator.LevelParameterEntity
import biz.belcorp.salesforce.modules.calculator.core.domain.entities.LevelParameter

class LevelParameterEntityDataMapper {

    fun parseLevelParameterList(list: List<LevelParameterEntity>?): List<LevelParameter>? {
        val entity = arrayListOf<LevelParameter>()

        list?.forEach {
            entity.add(LevelParameter(
                it.nivelID,
                it.gananciaPromedioPedido,
                it.pedidoMinimoNivel,
                it.ventaMinimaProximoNivel,
                it.metaIngresos,
                it.metaCapitalizacion,
                it.porcentajeRetencionActivas,
                it.metaPedidos,
                it.metaVenta,
                it.indicadorMedicionMeta,
                it.indicadorMedicionVariable,
                it.indicadorMedicionRetencion,
                it.bonoCambioNivel,
                it.porcentajeComisionEXAV,
                it.porcentajeComisionEXBV,
                it.porcentajeComisionNEAV,
                it.porcentajeComisionNEBV))
        }

        return entity
    }

    fun parseLevelParameter(parametroNivel: LevelParameterEntity?): LevelParameter? {

        return  parametroNivel?.let {
            LevelParameter(
                it.nivelID,
                it.gananciaPromedioPedido,
                it.pedidoMinimoNivel,
                it.ventaMinimaProximoNivel,
                it.metaIngresos,
                it.metaCapitalizacion,
                it.porcentajeRetencionActivas,
                it.metaPedidos,
                it.metaVenta,
                it.indicadorMedicionMeta,
                it.indicadorMedicionVariable,
                it.indicadorMedicionRetencion,
                it.bonoCambioNivel,
                it.porcentajeComisionEXAV,
                it.porcentajeComisionEXBV,
                it.porcentajeComisionNEAV,
                it.porcentajeComisionNEBV)
        }
    }
}
