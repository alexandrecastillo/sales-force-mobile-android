package biz.belcorp.salesforce.modules.developmentpath.features.horariovisitas

import biz.belcorp.salesforce.core.utils.Mapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.horariovisita.HorarioVisita

class HorarioVisitaModelMapper : Mapper<HorarioVisita, HorarioVisitaModel>() {

    override fun reverseMap(value: HorarioVisitaModel): HorarioVisita {
        return HorarioVisita(
            horarioVisitaId = value.horarioVisitaId,
            descripcion = value.descripcion,
            horaInicio = value.horaInicio,
            horaFin = value.horaFin,
            orden = value.orden,
            otroHorario = value.otroHorario,
            activo = value.activo,
            seleccionado = value.seleccionado
        )
    }

    override fun map(value: HorarioVisita): HorarioVisitaModel {
        return HorarioVisitaModel().apply {
            this.horarioVisitaId = value.horarioVisitaId
            this.descripcion = value.descripcion
            this.horaInicio = value.horaInicio
            this.horaFin = value.horaFin
            this.orden = value.orden
            this.otroHorario = value.otroHorario
            this.activo = value.activo
            this.seleccionado = value.seleccionado
        }
    }
}
