package biz.belcorp.salesforce.modules.developmentpath.features.profile.header

sealed class PerfilCabeceraViewState {

    class ShowInfo(val model: PerfilCabeceraModel) : PerfilCabeceraViewState()

}
