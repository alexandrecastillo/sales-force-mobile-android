package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.ruta

import biz.belcorp.salesforce.core.entities.sql.plan.DetallePlanRutaRDDEntity
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.visitas.CrearVisitaRequest
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.toCalendar
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas.Visita

class DetalleRutaMapper {

    fun parse(modelo: DetallePlanRutaRDDEntity): Visita {
        return Visita(id = modelo.id,
            planId = modelo.idPlanVisita,
            fechaProgramacionInicial = modelo.fechaPlanificacion?.toCalendar(),
            fechaProgramacion = modelo.fechaPlanificacion.toCalendar(),
            fechaReprogramacion = modelo.fechaReprogramacion?.toCalendar(),
            fechaRegistro = modelo.fechaVisita?.toCalendar(),
            enviado = modelo.enviado == 1,
            tips = modelo.tipsid,
            planificacionInicial = modelo.planInicial == 1,
            forzarEliminado = modelo.planificado == 0,
            esAdicional = modelo.esAdicional,
            usuarioRed = modelo.usuarioRed.orEmpty())
    }

    fun parse(modelos: List<DetallePlanRutaRDDEntity>): List<Visita> {
        return modelos.map { parse(it) }
    }

    fun parseToRequest(detallePlanRutaRDDEntity: DetallePlanRutaRDDEntity): CrearVisitaRequest {
        val request = CrearVisitaRequest()
        request.idLocal = detallePlanRutaRDDEntity.idLocal
        request.planVisitaId = detallePlanRutaRDDEntity.idPlanVisita
        request.rol = detallePlanRutaRDDEntity.rol
        request.fechaPlanificacion = detallePlanRutaRDDEntity.fechaPlanificacion
        request.fechaReprogramacion = detallePlanRutaRDDEntity.fechaReprogramacion
        request.fechaVisita = detallePlanRutaRDDEntity.fechaVisita
        request.consultoraId = detallePlanRutaRDDEntity.consultoraId
        request.codigoConsultora = detallePlanRutaRDDEntity.codigoConsultora
        request.numeroDocumento = detallePlanRutaRDDEntity.numeroDocumento
        request.tipsId = detallePlanRutaRDDEntity.tipsid.toLong()
        return request
    }
}
