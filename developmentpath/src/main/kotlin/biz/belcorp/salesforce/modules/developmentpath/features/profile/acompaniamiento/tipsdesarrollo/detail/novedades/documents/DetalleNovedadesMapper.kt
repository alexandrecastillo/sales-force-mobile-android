package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.novedades.documents

import biz.belcorp.salesforce.core.utils.Mapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.novedades.DetalleNovedades
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.novedades.novedades.DetalleNovedadesModel

class DetalleNovedadesMapper : Mapper<DetalleNovedadesModel, DetalleNovedades>() {
    override fun map(value: DetalleNovedadesModel): DetalleNovedades {
        return DetalleNovedades(
            tipoArchivo = value.tipoArchivo,
            url = value.url
        )
    }

    override fun reverseMap(value: DetalleNovedades): DetalleNovedadesModel {
        return DetalleNovedadesModel(
            tipoArchivo = value.tipoArchivo,
            url = value.url
        )
    }
}
