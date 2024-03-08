package biz.belcorp.salesforce.modules.developmentpath.features.profile.dream.viewstate

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.dream.Dream

sealed class DreamViewState {
    class Success(val dream: Dream?) : DreamViewState()

    object Failed : DreamViewState()

}
