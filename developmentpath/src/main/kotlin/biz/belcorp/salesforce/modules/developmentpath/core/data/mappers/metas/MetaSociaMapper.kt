package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.metas

import biz.belcorp.salesforce.core.entities.sql.anotaciones.AnotacionEntity
import biz.belcorp.salesforce.core.utils.negativoSiNull
import biz.belcorp.salesforce.core.utils.toCalendar
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.ceroSiNull
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.toLongDate
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.toStringDate
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.metas.MetaSocia
import java.util.*

class MetaSociaMapper {

    companion object {
        private const val TIPO_META = "M"
    }

    fun parse(metaSocia: MetaSocia) = with(metaSocia) {
        val anotacion = AnotacionEntity()
        anotacion.idLocal = id
        anotacion.campania = campania
        anotacion.descripcion = descripcion
        anotacion.fechaModificacion = metaSocia.fecha.toCalendar().toStringDate()
        anotacion.personaId = personaId
        anotacion.tipo = TIPO_META
        anotacion.region = region
        anotacion.zona = zona
        anotacion.seccion = seccion
        anotacion
    }

    fun parse(metaSocia: AnotacionEntity): MetaSocia {
        return MetaSocia(
            id = metaSocia.idLocal.ceroSiNull(),
            personaId = metaSocia.personaId.negativoSiNull(),
            campania = metaSocia.campania.orEmpty(),
            fecha = metaSocia.fechaModificacion?.toLongDate() ?: Date(),
            descripcion = metaSocia.descripcion.orEmpty(),
            zona = metaSocia.zona.orEmpty(),
            seccion = metaSocia.seccion.orEmpty(),
            region = metaSocia.region.orEmpty()
        )
    }

    fun parse(metasSocia: List<AnotacionEntity>) = metasSocia.map { parse(it) }
}
