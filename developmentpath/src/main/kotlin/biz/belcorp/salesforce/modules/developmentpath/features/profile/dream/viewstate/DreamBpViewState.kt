package biz.belcorp.salesforce.modules.developmentpath.features.profile.dream.viewstate

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.dream.DreamBusinessPartner

sealed class DreamBpViewState {
    class Success(val dreamBusinessPartner: DreamBusinessPartner?) : DreamBpViewState()

    object Failed : DreamBpViewState()

}
