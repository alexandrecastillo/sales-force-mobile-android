package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.novedades.novedades

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.helper.TipoArchivoNovedades
import biz.belcorp.salesforce.modules.developmentpath.utils.getIdYoutube

class DetalleNovedadesModel(
    val tipoArchivo: String,
    val url: String
) {
    val idYoutube
        get() = when (tipoArchivo) {
            TipoArchivoNovedades.VIDEO -> url.getIdYoutube()
            else -> null
        }
}
