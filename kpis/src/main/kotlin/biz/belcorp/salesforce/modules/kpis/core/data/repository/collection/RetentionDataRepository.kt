package biz.belcorp.salesforce.modules.kpis.core.data.repository.collection

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.kpis.core.data.repository.collection.cloud.RetentionCloudStore
import biz.belcorp.salesforce.modules.kpis.core.data.repository.collection.cloud.dto.RetentionQuery
import biz.belcorp.salesforce.modules.kpis.core.data.repository.collection.data.RetentionDataStore
import biz.belcorp.salesforce.modules.kpis.core.data.repository.collection.mapper.RetentionMapper
import biz.belcorp.salesforce.modules.kpis.core.data.repository.common.mappers.KpiQueryMapper
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.KpiQueryParams
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.collection.RetentionIndicator
import biz.belcorp.salesforce.modules.kpis.core.domain.repository.RetentionRepository


class RetentionDataRepository(
    private val retentionCloudStore: RetentionCloudStore,
    private val retentionDataStore: RetentionDataStore,
    private val retentionMapper: RetentionMapper,
    private val queryMapper: KpiQueryMapper
) : RetentionRepository {

    override suspend fun sync(request: KpiQueryParams) {
        val query = RetentionQuery(queryMapper.mapKpi(request))
        val data = retentionCloudStore.getRetention(query)
        val retentions = retentionMapper.map(data)
        retentionDataStore.saveRetention(retentions)
    }

    override suspend fun getRetentionByCampaigns(
        uaKey: LlaveUA,
        campaigns: List<String>
    ): List<RetentionIndicator> {
        val data = retentionDataStore.getRetentionByCampaigns(uaKey, campaigns)

        val dataMapped = data.map {
            retentionMapper.map(it)
        }

        return dataMapped
    }
}
