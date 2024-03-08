package biz.belcorp.salesforce.modules.consultants.features.filters.baseunified.view

import biz.belcorp.salesforce.modules.consultants.features.filters.baseunified.model.ConsultantContainerModel
import biz.belcorp.salesforce.modules.consultants.features.filters.baseunified.model.ConsultantModel

sealed class SearchConsultantViewState {
    class Success(val model: ConsultantContainerModel) : SearchConsultantViewState()
    class Empty(val model: ConsultantContainerModel) : SearchConsultantViewState()
    class SuccessSearch(val consultants: List<ConsultantModel>) : SearchConsultantViewState()
    object EmptySearch : SearchConsultantViewState()
    class Failure(val message: String) : SearchConsultantViewState()
    class FetchAmountFailure(val message: String) : SearchConsultantViewState()
    object HideLoading : SearchConsultantViewState()
}
