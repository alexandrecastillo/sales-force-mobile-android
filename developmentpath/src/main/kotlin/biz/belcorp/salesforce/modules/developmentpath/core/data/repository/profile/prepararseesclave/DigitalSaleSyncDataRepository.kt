package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.prepararseesclave

import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.isCO
import biz.belcorp.salesforce.core.utils.isSE
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.profile.prepararseesclave.digital.DigitalSaleDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.profile.prepararseesclave.digital.cloud.DigitalSaleCloudStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.prepararseesclave.detail.digital.mapper.DigitalSaleMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.prepararseesclave.detail.digital.mapper.DigitalSaleQueryMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.prepararseesclave.detail.digital.model.DigitalSaleCoQuery
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.prepararseesclave.detail.digital.model.DigitalSaleSeQuery
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.perfil.DigitalSaleSyncRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.profile.model.DigitalSaleQueryParams

class DigitalSaleSyncDataRepository(
    private val digitalSaleDataStore: DigitalSaleDataStore,
    private val digitalSaleCloudStore: DigitalSaleCloudStore,
    private val digitalSaleMapper: DigitalSaleMapper,
    private val queryMapper: DigitalSaleQueryMapper
) : DigitalSaleSyncRepository {

    override suspend fun sync(role: Rol, params: DigitalSaleQueryParams) = with(role) {
        when {
            isCO() -> syncDigitalSaleConsultant(params)
            isSE() -> syncDigitalSaleBusinessPartner(params)
            else -> Unit
        }
    }

    private suspend fun syncDigitalSaleConsultant(params: DigitalSaleQueryParams) = with(params) {
        if (!digitalSaleDataStore.hasDigitalSalesConsultant(consultantCode, campaigns)) {
            val query = DigitalSaleCoQuery(queryMapper.mapConsultantParams(this))
            val response = digitalSaleCloudStore.getDigitalSaleConsultant(query)
            val entities = digitalSaleMapper.map(response)
            digitalSaleDataStore.deleteDigitalSaleConsultantData(consultantCode)
            digitalSaleDataStore.saveDigitalSaleConsultant(entities)
        }
    }

    private suspend fun syncDigitalSaleBusinessPartner(params: DigitalSaleQueryParams) =
        with(params) {
            if (!digitalSaleDataStore.hasDigitalSaleSe(uakey, campaigns)) {
                val query = DigitalSaleSeQuery(queryMapper.mapBusinessPartnerParams(params))
                val response = digitalSaleCloudStore.getDigitalSaleBusinessPartner(query)
                val entities = digitalSaleMapper.map(response)
                digitalSaleDataStore.deleteDigitalSaleBusinessPartnerData(uakey)
                digitalSaleDataStore.saveDigitalSaleBusinessPartner(entities)
            }
        }
}
