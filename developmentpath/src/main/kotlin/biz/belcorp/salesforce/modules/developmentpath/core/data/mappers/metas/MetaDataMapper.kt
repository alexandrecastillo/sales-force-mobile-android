package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.metas

import biz.belcorp.salesforce.core.entities.sql.anotaciones.AnotacionEntity
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.toCalendarAnotacion
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.toStringDate
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.metas.MetaPersonal

class MetaDataMapper {

    companion object {
        private const val TIPO_META = "M"
    }

    fun parse(anotacion: AnotacionEntity) = anotacion.run {
        MetaPersonal(
            metaId = idLocal,
            personaId = personaId ?: -1L,
            descripcion = descripcion ?: "",
            fecha = fechaModificacion.toCalendarAnotacion(),
            campania = campania ?: "")
    }

    fun parse(meta: MetaPersonal) = meta.run {

        val anotacion = AnotacionEntity()
        anotacion.idLocal = metaId
        anotacion.campania = campania
        anotacion.descripcion = descripcion
        anotacion.fechaModificacion = fecha.toStringDate()
        anotacion.personaId = personaId
        anotacion.tipo = TIPO_META
        anotacion.region = region
        anotacion.zona = zona
        anotacion.seccion = seccion

        anotacion
    }

    fun parse(anotaciones: List<AnotacionEntity>) = anotaciones.map { parse(it) }
}
