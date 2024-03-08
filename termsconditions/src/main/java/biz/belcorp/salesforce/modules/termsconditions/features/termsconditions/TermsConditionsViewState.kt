package biz.belcorp.salesforce.modules.termsconditions.features.termsconditions

import biz.belcorp.salesforce.modules.termsconditions.features.termsconditions.model.TermsConditionsModel

sealed class TermsConditionsViewState {
    class Success(val data: List<TermsConditionsModel>) : TermsConditionsViewState()
    class Failed(val message: String) : TermsConditionsViewState()
}
