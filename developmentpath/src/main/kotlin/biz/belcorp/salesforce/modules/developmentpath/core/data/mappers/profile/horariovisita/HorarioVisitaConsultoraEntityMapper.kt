package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.profile.horariovisita

import biz.belcorp.salesforce.core.entities.sql.horariovisita.HorarioVisitaConsultoraEntity
import biz.belcorp.salesforce.core.utils.Mapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.horariovisita.HorarioVisitaConsultora

class HorarioVisitaConsultoraEntityMapper :
    Mapper<HorarioVisitaConsultoraEntity, HorarioVisitaConsultora>() {

    override fun reverseMap(value: HorarioVisitaConsultora): HorarioVisitaConsultoraEntity {
        return HorarioVisitaConsultoraEntity().apply {
            this.horarioVisitaId = value.horarioVisitaId
            this.consultoraId = value.consultoraId
            this.region = value.region
            this.zona = value.zona
            this.seccion = value.seccion
            this.activo = value.activo
            this.enviado = value.enviado
        }
    }

    override fun map(value: HorarioVisitaConsultoraEntity): HorarioVisitaConsultora {
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
}
