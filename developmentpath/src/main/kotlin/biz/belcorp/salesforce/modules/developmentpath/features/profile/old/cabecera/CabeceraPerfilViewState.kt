package biz.belcorp.salesforce.modules.developmentpath.features.profile.old.cabecera

import biz.belcorp.salesforce.modules.developmentpath.features.profile.header.PerfilCabeceraModel

sealed class CabeceraPerfilViewState {

    class ShowInfo(val model: PerfilCabeceraModel) : CabeceraPerfilViewState()

}
