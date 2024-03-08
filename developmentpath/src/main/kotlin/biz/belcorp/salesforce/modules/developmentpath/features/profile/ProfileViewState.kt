package biz.belcorp.salesforce.modules.developmentpath.features.profile

import biz.belcorp.salesforce.core.entities.zonificacion.Rol

sealed class ProfileViewState {

    class Success(val name: String) : ProfileViewState()

    object SuccessSync : ProfileViewState()

    class ShowNetworkError(val role: Rol) : ProfileViewState()

    object ShowError : ProfileViewState()

}
