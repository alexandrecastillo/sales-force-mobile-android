package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.reconocimientos

import biz.belcorp.salesforce.core.entities.sql.path.ReconocimientoEntity
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.reconocimientos.CalificarVisitaRequest
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.reconocimiento.ReconocimientoAGrabar
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.reconocimiento.ReconocimientoASuperior

class ReconocimientoDataMapper {

    fun parse(entity: ReconocimientoEntity) = entity.run {
        ReconocimientoASuperior(
            idReconocimiento = id,
            idPersonaReconoce = idPersonaReconoce,
            rolReconoce = Rol.Builder.construir(rolPersonaReconoce),
            idPersonaReconocida = idPersonaReconocida,
            rolReconocida = Rol.Builder.construir(rolPersonaReconocida),
            valoracion = valoracion,
            pendienteReconocimiento = valoracion < 0,
            comentarios = comentarios,
            nombreReconocida = nombrePersonaReconocida,
            campania = campania)
    }

    fun parse(entities: List<ReconocimientoEntity>) = entities.map { parse(it) }

    fun parse(reconocimiento: ReconocimientoASuperior): ReconocimientoEntity {
        with(reconocimiento) {
            val entity = ReconocimientoEntity()
            entity.id = idReconocimiento
            entity.idPersonaReconoce = idPersonaReconoce
            entity.rolPersonaReconoce = rolReconoce.codigoRol
            entity.idPersonaReconocida = idPersonaReconocida
            entity.rolPersonaReconocida = rolReconocida.codigoRol
            entity.nombrePersonaReconocida = nombreReconocida
            entity.campania = campania
            entity.valoracion = valoracion
            entity.comentarios = comentarios ?: ""

            return entity
        }
    }

    private fun parseToRequest(reconocimiento: ReconocimientoEntity): CalificarVisitaRequest =
        CalificarVisitaRequest(
            idReconocimiento = reconocimiento.id,
            calificacion = reconocimiento.valoracion,
            comentarios = reconocimiento.comentarios)

    fun parseToRequest(reconocimientos: List<ReconocimientoEntity>): List<CalificarVisitaRequest> {
        return reconocimientos.map { parseToRequest(it) }
    }

    fun parse(reconocimiento: ReconocimientoAGrabar): ReconocimientoEntity {
        val entity = ReconocimientoEntity()
        entity.id = reconocimiento.id
        entity.comentarios = reconocimiento.comentarios
        entity.valoracion = reconocimiento.calificacion

        return entity
    }
}
