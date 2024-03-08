package biz.belcorp.salesforce.modules.consultants.core.data.repository.consultoras


import biz.belcorp.salesforce.core.data.preferences.UserSharedPreferences
import biz.belcorp.salesforce.core.domain.entities.pais.Pais
import biz.belcorp.salesforce.modules.consultants.core.data.repository.consultoras.mappers.ConsultoraEntityDataMapper
import biz.belcorp.salesforce.modules.consultants.core.data.repository.consultoras.mappers.ConsultoraPosibleCambioNivelEntityDataMapper
import biz.belcorp.salesforce.modules.consultants.core.domain.entities.Consultora
import biz.belcorp.salesforce.modules.consultants.core.domain.entities.FilterSearchConsultant
import biz.belcorp.salesforce.modules.consultants.core.domain.repository.consultoras.ConsultoraRepository
import io.reactivex.Observable
import io.reactivex.Single

class ConsultoraDataRepository(
    private val consultoraDBStore: ConsultoraDBDataStore,
    private val userPref: UserSharedPreferences,
    private val consultoraEntityDataMapper: ConsultoraEntityDataMapper,
    private val consulltoraPosibleCambioNivelEntityDataMapper: ConsultoraPosibleCambioNivelEntityDataMapper
) : ConsultoraRepository {

    override fun consultoras(sectionCode: String?, idIndicator: Int, idList: Int):
        Observable<List<Consultora>> {
        val order = if (userPref.codPais.equals(Pais.ECUADOR.codigoIso, ignoreCase = true)) 0 else 1
        return consultoraDBStore
            .consultoras(sectionCode, idIndicator, idList, order)
            .map { consultoraEntityDataMapper.parseConsultoraList(it) }
    }

    override fun find(params: FilterSearchConsultant): Observable<List<Consultora>> {
        return consultoraDBStore
            .find(params)
            .map { consultoraEntityDataMapper.parseConsultoraList(it) }
    }

    override fun findSize(params: FilterSearchConsultant): Observable<Int> {
        return consultoraDBStore
            .findSize(params)
    }

    override fun findPDV(
        nivel: String,
        seccion: String
    ): Single<List<Consultora>> {
        return consultoraDBStore
            .getBeautyConsultantsByLevel(nivel, seccion)
            .map { consultoraEntityDataMapper.parseConsultoraList(it, true) }
    }

    override fun getPossibleLevelChanges(seccion: String): Single<List<Consultora>> {
        return consultoraDBStore
            .consultantWithPossibleLevelChange(seccion)
            .map { consulltoraPosibleCambioNivelEntityDataMapper.parseConsultoraList(it) }
    }

    override fun getEndPeriod(level: String, seccion: String): Single<List<Consultora>> {
        return consultoraDBStore
            .consultantsEndPeriod(level, seccion)
            .map { consulltoraPosibleCambioNivelEntityDataMapper.parseConsultoraEndPeriodList(it) }
    }

    override suspend fun getConsultant(id: Int): Consultora {
        val entity = consultoraDBStore.getConsultant(id)
        return requireNotNull(consultoraEntityDataMapper.parseConsultora(entity))
    }
}
