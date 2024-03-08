package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.performance.view

import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.performance.model.PerformanceModel

sealed class PerformanceSeViewState {

    class Success(val model: PerformanceModel) : PerformanceSeViewState()

}
