package biz.belcorp.salesforce.modules.billing.core.data.repository.rejectedorders

import biz.belcorp.salesforce.core.base.SyncOnDemandRepository
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.data.preferences.SyncSharedPreferences
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.utils.deleteHyphen
import biz.belcorp.salesforce.core.utils.isDV
import biz.belcorp.salesforce.core.utils.isGR
import biz.belcorp.salesforce.modules.billing.core.data.repository.mapper.RejectedOrdersMapper
import biz.belcorp.salesforce.modules.billing.core.data.repository.rejectedorders.cloud.RejectedOrdersCloudStore
import biz.belcorp.salesforce.modules.billing.core.data.repository.rejectedorders.cloud.dto.RejectedOrdersRequest
import biz.belcorp.salesforce.modules.billing.core.data.repository.rejectedorders.data.RejectedOrdersDataStore
import biz.belcorp.salesforce.modules.billing.core.domain.entities.RejectedOrdersBilling
import biz.belcorp.salesforce.modules.billing.core.domain.repository.RejectedOrdersRepository

class RejectedOrdersDataRepository(
    private val cloudStore: RejectedOrdersCloudStore,
    private val dataStore: RejectedOrdersDataStore,
    private val mapper: RejectedOrdersMapper,
    syncSharedPreferences: SyncSharedPreferences,
    type: SyncField
) : SyncOnDemandRepository(syncSharedPreferences, type), RejectedOrdersRepository {

    override suspend fun sync(params: RejectedOrdersParams) = with(params) {
        if (isAvailableToSync()) {
            val ua = LlaveUA(
                codigoRegion = uaKey.codigoRegion.orEmpty().deleteHyphen(),
                codigoSeccion = uaKey.codigoSeccion.orEmpty().deleteHyphen(),
                codigoZona = uaKey.codigoZona.orEmpty().deleteHyphen(),
                consultoraId = Constant.UNO_NEGATIVO.toLong()
            )
            val rejectedOrdersData =
                cloudStore.getRejectedOrders(createParams(countryIso, ua, campaignCode))
            val (model, detail) = mapper.map(rejectedOrdersData)
            dataStore.saveRejectedOrders(model)
            dataStore.saveRejectedOrdersDetail(detail)
            updateSyncDate()
        }
    }

    override suspend fun getRejectedOrders(
        uaKey: LlaveUA,
        campaignCode: String
    ): List<RejectedOrdersBilling> {
        val entities = dataStore.getRejectedOrders(uaKey, campaignCode)
        return entities.map { mapper.map(it) }
    }

    private fun createParams(
        countryIso: String,
        uaKey: LlaveUA,
        campaignCode: String
    ): RejectedOrdersRequest {
        return RejectedOrdersRequest(
            country = countryIso,
            campaign = campaignCode,
            region = uaKey.codigoRegion.orEmpty(),
            zone = uaKey.codigoZona.orEmpty(),
            section = uaKey.codigoSeccion.orEmpty(),
            showZones = uaKey.roleAssociated.isGR(),
            showRegions = uaKey.roleAssociated.isDV()
        )
    }
}
