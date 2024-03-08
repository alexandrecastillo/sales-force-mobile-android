package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.ventaganancia.tips

import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.ventaganancia.tips.model.TipOfertaModel

interface VentaGananciaTipsView {
    fun mostrarProgreso(show: Boolean)
    fun mostrarTipsGanaMas(data: List<TipOfertaModel>)
    fun mostrarTipsGanaMasVacio()
}
