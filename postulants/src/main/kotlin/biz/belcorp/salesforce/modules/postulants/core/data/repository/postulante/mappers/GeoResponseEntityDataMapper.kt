package biz.belcorp.salesforce.modules.postulants.core.data.repository.postulante.mappers

import biz.belcorp.salesforce.core.utils.Mapper
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.GeoZonificacion

class GeoResponseEntityDataMapper : Mapper<GeoZonificacion, String>() {

    override fun map(value: GeoZonificacion): String {
        throw UnsupportedOperationException()
    }

    override fun reverseMap(value: String): GeoZonificacion {
        val entity = GeoZonificacion()
        entity.region = value.trim().substring(0, 2)
        entity.zona = value.trim().substring(2, 6)
        entity.seccion = value.trim().substring(6, 7)
        entity.territorio = value.trim().substring(7)
        entity.respuesta = value
        return entity
    }
}
