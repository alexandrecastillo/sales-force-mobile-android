package biz.belcorp.salesforce.core.features.uainfo

import biz.belcorp.mobile.components.design.selector.bar.model.SelectorModel


sealed class UaInfoViewState {
    class Success(val uas: List<SelectorModel>) : UaInfoViewState()
    class Failure(val message: String) : UaInfoViewState()
}
