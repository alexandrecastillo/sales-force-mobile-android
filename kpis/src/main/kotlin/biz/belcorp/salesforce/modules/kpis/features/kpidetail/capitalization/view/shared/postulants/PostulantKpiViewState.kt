package biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.view.shared.postulants

import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.model.PostulantKpiModel

sealed class PostulantKpiViewState {
    object Loading : PostulantKpiViewState()
    class Success(val data: PostulantKpiModel) : PostulantKpiViewState()
    class Failed(val message: String) : PostulantKpiViewState()
}
