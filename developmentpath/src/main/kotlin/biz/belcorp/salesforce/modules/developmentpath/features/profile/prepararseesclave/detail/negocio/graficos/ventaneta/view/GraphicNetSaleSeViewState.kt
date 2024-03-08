package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.ventaneta.view

import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.ventaneta.models.GraphicNetSaleModel

sealed class GraphicNetSaleSeViewState {
    class Success(val model: GraphicNetSaleModel) : GraphicNetSaleSeViewState()
    object Empty : GraphicNetSaleSeViewState()
    object Failure : GraphicNetSaleSeViewState()
}