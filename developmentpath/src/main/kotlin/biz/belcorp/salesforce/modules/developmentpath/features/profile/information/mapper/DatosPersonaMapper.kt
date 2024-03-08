package biz.belcorp.salesforce.modules.developmentpath.features.profile.information.mapper

import biz.belcorp.salesforce.modules.developmentpath.widgets.etiqueta.model.ContenedorInfoBasica

abstract class DatosPersonaMapper {
    abstract fun map(): ContenedorInfoBasica
}
