package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.visitas

import biz.belcorp.salesforce.core.entities.sql.plan.DetallePlanRutaRDDEntity
import biz.belcorp.salesforce.core.entities.sql.visitas.GrDetalleRutaJoinned
import biz.belcorp.salesforce.core.entities.sql.visitas.GzDetalleRutaJoinned
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.toCalendar
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas.Visita

class VisitaMapper {

    fun parse(model: DetallePlanRutaRDDEntity): Visita {
        return Visita(
            id = model.id,
            planId = model.idPlanVisita,
            fechaRegistro = model.fechaVisita.toCalendar(),
            fechaProgramacionInicial = model.fechaPlanificacion.toCalendar(),
            fechaProgramacion = null,
            tips = model.tipsid,
            fechaReprogramacion = model.fechaReprogramacion.toCalendar(),
            enviado = model.enviado == 1,
            planificacionInicial = model.planInicial == 1,
            forzarEliminado = model.planificado == 0,
            esAdicional = model.esAdicional,
            usuarioRed = model.usuarioRed.orEmpty()
        )
    }

    fun parse(models: List<DetallePlanRutaRDDEntity>): List<Visita> {
        return models.map { parse(it) }
    }

    private fun parse(model: GzDetalleRutaJoinned): Visita {
        return Visita(
            id = model.id,
            planId = model.idPlanVisita,
            fechaRegistro = model.fechaVisita.toCalendar(),
            fechaProgramacionInicial = model.fechaPlanificacion.toCalendar(),
            fechaProgramacion = null,
            tips = model.tipsid,
            fechaReprogramacion = model.fechaReprogramacion.toCalendar(),
            enviado = model.enviado == 1,
            planificacionInicial = model.planInicial == 1,
            forzarEliminado = model.planificado == 0,
            esAdicional = model.esAdicional,
            usuarioRed = model.usuarioRed.orEmpty()
        )
    }

    fun parseFromGz(models: List<GzDetalleRutaJoinned>) = models.map { parse(it) }

    private fun parse(model: GrDetalleRutaJoinned): Visita {
        return Visita(
            id = model.id,
            planId = model.idPlanVisita,
            fechaRegistro = model.fechaVisita.toCalendar(),
            fechaProgramacionInicial = model.fechaPlanificacion.toCalendar(),
            fechaProgramacion = null,
            tips = model.tipsid,
            fechaReprogramacion = model.fechaReprogramacion.toCalendar(),
            enviado = model.enviado == 1,
            planificacionInicial = model.planInicial == 1,
            forzarEliminado = model.planificado == 0,
            esAdicional = model.esAdicional,
            usuarioRed = model.usuarioRed.orEmpty()
        )
    }

    fun parseFromGr(models: List<GrDetalleRutaJoinned>) = models.map { parse(it) }
}
