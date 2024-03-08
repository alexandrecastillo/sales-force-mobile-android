package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.retencionactivas.view

import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.retencionactivas.models.GraphicActivesRetentionModel

sealed class ActivesRetentionViewState {
    class Success(val model: GraphicActivesRetentionModel) : ActivesRetentionViewState()
    object Empty : ActivesRetentionViewState()
    object Failure : ActivesRetentionViewState()
}
