package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.ganancia.view

import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.ganancia.model.GraphicProfitSeModel

sealed class ProfitSeViewState {
    class Success(val model: GraphicProfitSeModel) : ProfitSeViewState()
    object Empty : ProfitSeViewState()
    object Failure : ProfitSeViewState()
}
