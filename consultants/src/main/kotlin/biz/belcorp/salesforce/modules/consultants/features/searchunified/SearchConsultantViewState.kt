package biz.belcorp.salesforce.modules.consultants.features.searchunified

import biz.belcorp.mobile.components.design.selector.bar.model.SelectorModel

sealed class SearchConsultantViewState {
    class Success(val uas: List<SelectorModel>) : SearchConsultantViewState()
    class Failure(val message: String) : SearchConsultantViewState()
}