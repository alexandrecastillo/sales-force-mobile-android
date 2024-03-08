package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.profile.horariovisita.data

import biz.belcorp.salesforce.core.entities.sql.consultora.ConsultoraEntity
import biz.belcorp.salesforce.core.entities.sql.consultora.ConsultoraEntity_Table
import biz.belcorp.salesforce.core.entities.sql.horariovisita.HorarioVisitaConsultoraEntity
import biz.belcorp.salesforce.core.entities.sql.horariovisita.HorarioVisitaConsultoraEntity_Table
import biz.belcorp.salesforce.core.utils.doOnCompletable
import com.raizlabs.android.dbflow.kotlinextensions.*
import io.reactivex.Completable

class HorarioVisitaConsultoraLocalDataStore {
    fun obtenerHorarioVisitaporConsultora(consultoraId: Long): List<HorarioVisitaConsultoraEntity> {
        val query = (select from HorarioVisitaConsultoraEntity::class
            where (HorarioVisitaConsultoraEntity_Table.consultoraId eq consultoraId))

        return query.queryList()
    }

    fun obtenerHorarioVisitaporConsultoraParaSync(): List<HorarioVisitaConsultoraEntity> {
        val query = (select from HorarioVisitaConsultoraEntity::class
            where (HorarioVisitaConsultoraEntity_Table.enviado eq false))

        return query.queryList()
    }

    fun saveHorarioOffLline(value: HorarioVisitaConsultoraEntity): Completable {
        return doOnCompletable {
            value.save()
        }
    }

    fun updateHorarioVisitaConsultoraEnviadas(): Completable {
        val horariosPendientesEnvio = obtenerHorarioVisitaporConsultoraParaSync()

        return doOnCompletable {
            horariosPendientesEnvio.forEach {
                it.enviado = true
                it.save()
            }
        }
    }

    fun getConsultant(consultoraId: Int): ConsultoraEntity? =
        (select from ConsultoraEntity::class where (ConsultoraEntity_Table.ConsultorasId eq consultoraId)).querySingle()
}
