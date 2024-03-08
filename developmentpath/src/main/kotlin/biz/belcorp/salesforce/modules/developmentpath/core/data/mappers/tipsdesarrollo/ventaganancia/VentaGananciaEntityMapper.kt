package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.tipsdesarrollo.ventaganancia

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.ventaganancia.VentaGananciaEntity
import biz.belcorp.salesforce.core.utils.Mapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.ventaganancia.VentaGanancia

class VentaGananciaEntityMapper : Mapper<VentaGananciaEntity, VentaGanancia>() {

    override fun map(value: VentaGananciaEntity): VentaGanancia {
        return VentaGanancia(
            ventaMonto = value.ventaMonto?.let { ValorEntityMapper().map(it) },
            ventaDescripcion = value.ventaDescripcion?.let { ValorEntityMapper().map(it) },
            gananciaMonto = value.gananciaMonto?.let { ValorEntityMapper().map(it) },
            gananciaDescripcion = value.gananciaDescripcion?.let { ValorEntityMapper().map(it) })
    }

    override fun reverseMap(value: VentaGanancia): VentaGananciaEntity {
        return VentaGananciaEntity(
            ventaMonto = value.ventaMonto?.let { ValorEntityMapper().reverseMap(it) },
            ventaDescripcion = value.ventaDescripcion?.let { ValorEntityMapper().reverseMap(it) },
            gananciaMonto = value.gananciaMonto?.let { ValorEntityMapper().reverseMap(it) },
            gananciaDescripcion = value.gananciaDescripcion?.let { ValorEntityMapper().reverseMap(it) })
    }

    inner class ValorEntityMapper : Mapper<VentaGananciaEntity.ValorEntity, VentaGanancia.Valor>() {
        override fun map(value: VentaGananciaEntity.ValorEntity): VentaGanancia.Valor {
            return VentaGanancia.Valor(
                texto = value.texto,
                colores = value.tipoColores)
        }

        override fun reverseMap(value: VentaGanancia.Valor): VentaGananciaEntity.ValorEntity {
            return VentaGananciaEntity.ValorEntity(
                texto = value.texto,
                colores = value.tipoColores)
        }
    }
}
