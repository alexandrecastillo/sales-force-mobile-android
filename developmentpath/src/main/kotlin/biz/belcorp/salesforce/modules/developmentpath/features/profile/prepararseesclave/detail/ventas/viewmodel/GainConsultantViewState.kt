package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.ventas.viewmodel

import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.ventas.model.GainConsultantModel

sealed class GainConsultantViewState {
    class Success(val model: GainConsultantModel) : GainConsultantViewState()
    object Failure : GainConsultantViewState()
}