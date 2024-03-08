package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.perfil

import biz.belcorp.salesforce.core.entities.sql.consultora.ConsultoraEntity
import biz.belcorp.salesforce.core.entities.sql.horariovisita.HorarioVisitaConsultoraEntity
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.horariovisita.HorarioVisita
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.horariovisita.HorarioVisitaConsultora
import io.reactivex.Completable

interface HorarioVisitaRepository {
    fun obtener(consultoraId: Long): List<HorarioVisita>

    fun sincronizar(): Completable

    fun saveHorarioOffLline(value: HorarioVisitaConsultora): Completable

    fun updateHorarioVisitaConsultora(): Completable

    fun getConsultant(consultoraId: Int) : ConsultoraEntity?
}
