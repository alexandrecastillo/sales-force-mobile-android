package biz.belcorp.salesforce.core.data.repository.businesspartners

import biz.belcorp.salesforce.core.data.repository.businesspartners.cloud.BusinessPartnerCloudStore
import biz.belcorp.salesforce.core.data.repository.businesspartners.cloud.dto.BusinessPartnerQuery
import biz.belcorp.salesforce.core.data.repository.businesspartners.data.BusinessPartnerDataStore
import biz.belcorp.salesforce.core.data.repository.businesspartners.data.BusinessPartnerTableDataStore
import biz.belcorp.salesforce.core.data.repository.businesspartners.mappers.BusinessPartnerMapper
import biz.belcorp.salesforce.core.data.repository.businesspartners.mappers.BusinessPartnerTableMapper
import biz.belcorp.salesforce.core.domain.entities.people.SyncParams
import biz.belcorp.salesforce.core.domain.repository.businesspartner.BusinessPartnerSyncRepository
import biz.belcorp.salesforce.core.utils.deleteHyphen

class BusinessPartnerSyncDataRepository(
    private val cloudStore: BusinessPartnerCloudStore,
    private val dataStore: BusinessPartnerDataStore,
    private val tableDataStore: BusinessPartnerTableDataStore,
    private val mapper: BusinessPartnerMapper,
    private val tableMapper: BusinessPartnerTableMapper
) : BusinessPartnerSyncRepository {

    override suspend fun sync(params: SyncParams) {
        val qp = BusinessPartnerQuery.Params(
            params.countryIso,
            params.campaign,
            params.ua.codigoRegion.orEmpty().deleteHyphen(),
            params.ua.codigoZona.orEmpty().deleteHyphen(),
            params.ua.codigoSeccion.orEmpty().deleteHyphen()
        )
        val query = BusinessPartnerQuery(qp)
        val data = cloudStore.getBusinessPartners(query)
        val businessPartners = mapper.map(data)
        dataStore.saveBusinessPartners(businessPartners)
        val tableBusinessPartners = tableMapper.map(data)
        tableDataStore.saveBusinessPartners(tableBusinessPartners)
    }
}
