package biz.belcorp.salesforce.modules.kpis.core.data.repository.capitalization

import biz.belcorp.salesforce.core.base.SyncOnDemandRepository
import biz.belcorp.salesforce.core.data.preferences.SyncSharedPreferences
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.usecases.sync.SyncType
import biz.belcorp.salesforce.core.utils.deleteHyphen
import biz.belcorp.salesforce.modules.kpis.core.data.repository.capitalization.cloud.PostulantsKpiCloudStore
import biz.belcorp.salesforce.modules.kpis.core.data.repository.capitalization.cloud.dto.PostulantsKpiParams
import biz.belcorp.salesforce.modules.kpis.core.data.repository.capitalization.cloud.dto.PostulantsKpiQuery
import biz.belcorp.salesforce.modules.kpis.core.data.repository.capitalization.data.PostulantsKpiDataStore
import biz.belcorp.salesforce.modules.kpis.core.data.repository.capitalization.mapper.PostulantsKpiMapper
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.capitalization.PostulantKpi
import biz.belcorp.salesforce.modules.kpis.core.domain.repository.PostulantsKpiRepository
import biz.belcorp.salesforce.modules.kpis.core.domain.usecases.sync.SyncParams

class PostulantsKpiDataRepository(
    private val cloudStore: PostulantsKpiCloudStore,
    private val dataStore: PostulantsKpiDataStore,
    private val mapper: PostulantsKpiMapper,
    syncSharedPreferences: SyncSharedPreferences,
    type: SyncField
) : SyncOnDemandRepository(syncSharedPreferences, type), PostulantsKpiRepository {

    override suspend fun getPostulantsKpi(ua: LlaveUA): List<PostulantKpi> {
        val entities = dataStore.getPostulantsKpi(ua)
        return entities.map { mapper.map(it) }
    }

    override suspend fun sync(params: SyncParams): SyncType {
        return if (isAvailableToSync(params.isForced)) {
            val query = createQuery(params)
            val dto = cloudStore.getPostulantsKpi(query)
            val entities = mapper.map(dto.list)
            if (params.hasToBeUpdated) {
                if (entities.isNotEmpty()) dataStore.updatePostulant(entities.first())
                else SyncType.None
            } else dataStore.savePostulantsKpi(entities)
            updateSyncDate()
            SyncType.Updated()
        } else {
            SyncType.None
        }
    }

    private fun createQuery(params: SyncParams): PostulantsKpiQuery = with(params) {
        val dtoParams =
            PostulantsKpiParams(
                country = countryIso,
                region = ua.codigoRegion.orEmpty(),
                zone = ua.codigoZona.orEmpty(),
                section = ua.codigoSeccion?.deleteHyphen().orEmpty()
            )

        return PostulantsKpiQuery(
            dtoParams
        )
    }

}
