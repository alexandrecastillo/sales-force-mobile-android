package biz.belcorp.salesforce.modules.developmentpath.features.profile.data.contacto

sealed class DatosContactoViewState {

    object ManagersViewMode : DatosContactoViewState()
    object PostulantsViewMode : DatosContactoViewState()

    class ShowInfo(val model: DatosContactoModel) : DatosContactoViewState()

}
