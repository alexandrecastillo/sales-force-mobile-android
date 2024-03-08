package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.profile.horariovisita

import biz.belcorp.salesforce.core.entities.sql.horariovisita.HorarioVisitaEntity
import biz.belcorp.salesforce.core.utils.Mapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.horariovisita.HorarioVisita

class HorarioVisitaEntityMapper : Mapper<HorarioVisitaEntity, HorarioVisita>() {

    override fun reverseMap(value: HorarioVisita): HorarioVisitaEntity {
        return HorarioVisitaEntity().apply {
            this.horarioVisitaId = value.horarioVisitaId
            this.descripcion = value.descripcion
            this.horaInicio = value.horaInicio
            this.horaFin = value.horaFin
            this.orden = value.orden
            this.otroHorario = value.otroHorario
            this.activo = value.activo
        }
    }

    override fun map(value: HorarioVisitaEntity): HorarioVisita {
        return HorarioVisita(
            horarioVisitaId = value.horarioVisitaId,
            descripcion = value.descripcion,
            horaInicio = value.horaInicio,
            horaFin = value.horaFin,
            orden = value.orden,
            otroHorario = value.otroHorario,
            activo = value.activo
        )
    }
}
