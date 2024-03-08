package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.capitalizacion.view

import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.capitalizacion.model.GraphicCapitalizationSeModel

sealed class CapitalizationSeViewState {
    class Success(val model: GraphicCapitalizationSeModel) : CapitalizationSeViewState()
    object Empty : CapitalizationSeViewState()
    object Failure : CapitalizationSeViewState()
}
