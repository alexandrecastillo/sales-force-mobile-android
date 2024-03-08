package biz.belcorp.salesforce.modules.developmentpath.features.profile.data.persona

sealed class DatosPersonaViewState {

    class ShowInfo(val model: DatosPersonaModel) : DatosPersonaViewState()

}
