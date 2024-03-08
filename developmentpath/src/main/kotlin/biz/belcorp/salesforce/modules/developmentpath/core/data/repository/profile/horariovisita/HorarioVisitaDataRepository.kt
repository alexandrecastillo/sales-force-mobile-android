package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.horariovisita

import biz.belcorp.salesforce.core.entities.sql.consultora.ConsultoraEntity
import biz.belcorp.salesforce.core.entities.sql.horariovisita.HorarioVisitaConsultoraEntity
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.profile.horariovisita.cloud.HorarioVisitaCloudDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.profile.horariovisita.data.HorarioVisitaConsultoraLocalDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.profile.horariovisita.data.HorarioVisitaLocalDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.profile.horariovisita.HorarioVisitaConsultoraEntityMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.profile.horariovisita.HorarioVisitaEntityMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.horariovisita.HorarioVisita
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.horariovisita.HorarioVisitaConsultora
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.perfil.HorarioVisitaRepository
import com.raizlabs.android.dbflow.kotlinextensions.update
import io.reactivex.Completable

class HorarioVisitaDataRepository(
    private val horarioVisitaCloudDataStore: HorarioVisitaCloudDataStore,
    private val horarioVisitaLocalDataStore: HorarioVisitaLocalDataStore,
    private val horarioVisitaConsultoraLocalDataStore: HorarioVisitaConsultoraLocalDataStore,
    private val horarioVisitaEntityMapper: HorarioVisitaEntityMapper,
    private val horarioVisitaConsultoraEntityMapper: HorarioVisitaConsultoraEntityMapper
) : HorarioVisitaRepository {

    override fun obtener(consultoraId: Long): List<HorarioVisita> {
        val horasVisita = horarioVisitaLocalDataStore.obtenerHorarioVisita()
        val horasConsultoraVisita =
            horarioVisitaConsultoraLocalDataStore.obtenerHorarioVisitaporConsultora(consultoraId)

        val result = ArrayList<HorarioVisita>()

        if (horasConsultoraVisita.isEmpty()) {
            horasVisita.forEach {
                result.add(horarioVisitaEntityMapper.map(it))
            }
            return result
        }

        horasVisita.forEach { horaVisita ->
            val horaVisitaModel = horarioVisitaEntityMapper.map(horaVisita)

            horasConsultoraVisita.forEach { horaConsultoraVisita ->
                if (horaVisitaModel.horarioVisitaId == horaConsultoraVisita.horarioVisitaId)
                    horaVisitaModel.seleccionado = true

            }
            result.add(horaVisitaModel)
        }

        return result
    }

    override fun saveHorarioOffLline(value: HorarioVisitaConsultora): Completable {
        return horarioVisitaConsultoraLocalDataStore.saveHorarioOffLline(
            horarioVisitaConsultoraEntityMapper.reverseMap(value)
        )
    }

    override fun sincronizar(): Completable {
        val horariosParaSync =
            horarioVisitaConsultoraLocalDataStore.obtenerHorarioVisitaporConsultoraParaSync()

        return horarioVisitaCloudDataStore.sincronizar(horariosParaSync)
    }

    override fun updateHorarioVisitaConsultora(): Completable {
        return horarioVisitaConsultoraLocalDataStore.updateHorarioVisitaConsultoraEnviadas()
    }

    override fun getConsultant(consultoraId: Int): ConsultoraEntity? {
        return horarioVisitaConsultoraLocalDataStore.getConsultant(consultoraId)
    }
}
