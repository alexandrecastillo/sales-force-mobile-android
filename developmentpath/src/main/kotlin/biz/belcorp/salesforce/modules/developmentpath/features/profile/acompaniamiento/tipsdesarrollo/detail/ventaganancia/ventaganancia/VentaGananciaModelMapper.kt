package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.ventaganancia.ventaganancia

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.ventaganancia.VentaGanancia
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.helper.AcompaniamientoResourcesProvider

class VentaGananciaModelMapper {

    fun parse(model: VentaGanancia): VentaGananciaModel {
        val ventaMonto = createVentaGananciaValorModel(model.ventaMonto)
        val ventaDescripcion = createVentaGananciaValorModel(model.ventaDescripcion)
        val gananciaMonto = createVentaGananciaValorModel(model.gananciaMonto)
        val gananciaDescripcion = createVentaGananciaValorModel(model.gananciaDescripcion)
        return VentaGananciaModel(ventaMonto, ventaDescripcion, gananciaMonto, gananciaDescripcion)
    }

    private fun createVentaGananciaValorModel(data: VentaGanancia.Valor?): VentaGananciaModel.ValorModel? {
        if (data == null) return data
        return VentaGananciaModel.ValorModel(
            texto = data.texto,
            colores = data.colores.map { AcompaniamientoResourcesProvider.fromColor(it) })
    }
}
