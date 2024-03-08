package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.notas

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.sql.anotaciones.AnotacionEntity
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.toCalendarAnotacion
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.toStringDate
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.notas.perfil.Nota

class NotaMapper {

    companion object {
        private const val TIPO_ANOTACION = "N"
    }

    fun parse(anotacion: AnotacionEntity) = anotacion.run {
        val nota = Nota(
            id = idLocal,
            descripcion = descripcion ?: "",
            fecha = fechaModificacion.toCalendarAnotacion(),
            campania = campania ?: "",
            personaId = personaId ?: 0)

        nota.unidadAdministrativa = LlaveUA(
            codigoRegion = region, codigoZona = zona,
            codigoSeccion = seccion, consultoraId = personaId
        )
        nota
    }


    fun parseLista(anotaciones: List<AnotacionEntity>): List<Nota> {
        return anotaciones.map { parse(it) }
    }

    fun reverseParse(nota: Nota) = nota.run {
        val anotacion = AnotacionEntity()
        anotacion.idLocal = id
        anotacion.campania = campania
        anotacion.descripcion = descripcion
        anotacion.fechaModificacion = fecha.toStringDate()
        anotacion.region = unidadAdministrativa.codigoRegion
        anotacion.zona = unidadAdministrativa.codigoZona
        anotacion.seccion = unidadAdministrativa.codigoSeccion
        anotacion.personaId = personaId
        anotacion.tipo = TIPO_ANOTACION

        anotacion
    }
}
