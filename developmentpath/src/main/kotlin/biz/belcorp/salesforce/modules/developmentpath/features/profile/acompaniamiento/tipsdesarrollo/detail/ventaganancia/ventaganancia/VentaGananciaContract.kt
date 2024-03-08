package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.ventaganancia.ventaganancia

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.helper.Params

interface VentaGananciaContract {
    interface View {
        fun mostrarVentaGanancia(data: VentaGananciaModel)
        fun mostrarVentaGananciaVacio()
    }
    interface Presenter {
        fun obtener(params: Params)
    }
}
