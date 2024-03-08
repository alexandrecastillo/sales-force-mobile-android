package biz.belcorp.salesforce.modules.developmentpath.features.horariovisitas

import biz.belcorp.salesforce.core.utils.Mapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.horariovisita.HorarioVisitaConsultora

class HorarioVisitaConsultoraModelMapper :
    Mapper<HorarioVisitaConsultora, HorarioVisitaConsultoraModel>() {

    override fun reverseMap(value: HorarioVisitaConsultoraModel): HorarioVisitaConsultora {
        return HorarioVisitaConsultora(
            horarioVisitaId = value.horarioVisitaId,
            consultoraId = value.consultoraId,
            region = value.region,
            zona = value.zona,
            seccion = value.seccion,
            activo = value.activo,
            enviado = value.enviado
        )
    }

    override fun map(value: HorarioVisitaConsultora): HorarioVisitaConsultoraModel {
        return HorarioVisitaConsultoraModel(
            horarioVisitaId = value.horarioVisitaId,
            consultoraId = value.consultoraId,
            region = value.region,
            zona = value.zona,
            seccion = value.seccion,
            activo = value.activo,
            enviado = value.enviado
        )
    }
}
