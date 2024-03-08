package biz.belcorp.salesforce.modules.developmentpath.features.profile.information

import biz.belcorp.salesforce.modules.developmentpath.widgets.etiqueta.model.ContenedorInfoBasica

sealed class DatosPersonaViewState {

    class ShowInfo(val modelo: ContenedorInfoBasica) : DatosPersonaViewState()

}
