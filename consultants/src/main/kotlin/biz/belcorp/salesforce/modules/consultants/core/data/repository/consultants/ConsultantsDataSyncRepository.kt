package biz.belcorp.salesforce.modules.consultants.core.data.repository.consultants

import biz.belcorp.salesforce.core.base.SyncOnDemandRepository
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ONE_THOUSAND
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO_LONG
import biz.belcorp.salesforce.core.data.preferences.SyncSharedPreferences
import biz.belcorp.salesforce.core.domain.entities.people.SyncParams
import biz.belcorp.salesforce.core.domain.repository.consultant.ConsultantsSyncRepository
import biz.belcorp.salesforce.core.domain.usecases.sync.SyncType
import biz.belcorp.salesforce.core.entities.ConsultantEntity
import biz.belcorp.salesforce.core.entities.sql.consultora.ConsultoraEntity
import biz.belcorp.salesforce.core.utils.deleteHyphen
import biz.belcorp.salesforce.modules.consultants.core.data.repository.consultants.cloud.ConsultantsCloudStore
import biz.belcorp.salesforce.modules.consultants.core.data.repository.consultants.cloud.dto.ConsultantDto
import biz.belcorp.salesforce.modules.consultants.core.data.repository.consultants.cloud.dto.ConsultantsParams
import biz.belcorp.salesforce.modules.consultants.core.data.repository.consultants.cloud.dto.ConsultantsQuery
import biz.belcorp.salesforce.modules.consultants.core.data.repository.consultants.cloud.dto.DigitalDto
import biz.belcorp.salesforce.modules.consultants.core.data.repository.consultants.cloud.dto.DigitalParams
import biz.belcorp.salesforce.modules.consultants.core.data.repository.consultants.cloud.dto.DigitalQuery
import biz.belcorp.salesforce.modules.consultants.core.data.repository.consultants.data.ConsultantsDataStore
import biz.belcorp.salesforce.modules.consultants.core.data.repository.consultants.data.ConsultantsTableDataStore
import biz.belcorp.salesforce.modules.consultants.core.data.repository.consultants.mapper.ConsultantsMapper
import biz.belcorp.salesforce.modules.consultants.core.data.repository.consultants.mapper.ConsultantsTableMapper

class ConsultantsDataSyncRepository(
    private val consultantsCloudStore: ConsultantsCloudStore,
    private val consultantsDataStore: ConsultantsDataStore,
    private val consultantsTableDataStore: ConsultantsTableDataStore,
    private val consultantsMapper: ConsultantsMapper,
    private val consultantsTableMapper: ConsultantsTableMapper,
    syncSharedPreferences: SyncSharedPreferences,
    type: SyncField
) : SyncOnDemandRepository(syncSharedPreferences, type),
    ConsultantsSyncRepository {

    override suspend fun sync(params: SyncParams): SyncType {
        with(params) {
            return if (isAvailableToSync(isForced)) {
                val consultantList = fetchConsultants(this)
                val digitalList = fetchDigital(this)
                val (boxEntities, tableEntities) = map(consultantList, digitalList, params.countryIso)
                save(onlyModified, boxEntities, tableEntities)
                updateSyncDate()
                SyncType.Updated()
            } else {
                SyncType.None
            }
        }
    }

    private suspend fun fetchConsultants(params: SyncParams): List<ConsultantDto.Consultant> {
        val query = createQuery(params)
        val dto = consultantsCloudStore.getConsultants(query)
        return dto.consultants.list.distinctBy { it.code }
    }

    private suspend fun fetchDigital(params: SyncParams): List<DigitalDto.OnlineStore> {
        val digitalQuery = createDigitalQuery(params)
        val dto = consultantsCloudStore.getDigitalConsultants(digitalQuery)
        return dto.digitalInfo
    }

    private fun map(
        consultantList: List<ConsultantDto.Consultant>,
        digitalList: List<DigitalDto.OnlineStore>,
        country: String
    ):
        Pair<List<ConsultantEntity>, List<ConsultoraEntity>> {
        val boxEntities = consultantsMapper.map(consultantList, digitalList, country)
        val tableEntities = consultantsTableMapper.map(consultantList)
        return Pair(boxEntities, tableEntities)
    }

    private fun save(
        onlyModified: Boolean,
        boxEntities: List<ConsultantEntity>,
        tableEntities: List<ConsultoraEntity>
    ) {
        if (onlyModified) {
            consultantsDataStore.updateConsultants(boxEntities)
            consultantsTableDataStore.updateConsultants(tableEntities)
        } else {
            consultantsDataStore.saveConsultants(boxEntities)
            consultantsTableDataStore.saveConsultants(tableEntities)
        }
    }

    private fun createDigitalQuery(syncParams: SyncParams): DigitalQuery {
        val params = DigitalParams(
            campaign = syncParams.campaign,
            country = syncParams.countryIso,
            region = syncParams.ua.codigoRegion.orEmpty().deleteHyphen(),
            zone = syncParams.ua.codigoZona.orEmpty().deleteHyphen(),
            section = syncParams.ua.codigoSeccion.orEmpty().deleteHyphen()
        )
        return DigitalQuery(params)
    }

    private fun createQuery(syncParams: SyncParams): ConsultantsQuery {
        val params = ConsultantsParams().apply {
            campaign = syncParams.campaign
            phase = syncParams.phase
            country = syncParams.countryIso
            region = syncParams.ua.codigoRegion.orEmpty().deleteHyphen()
            zone = syncParams.ua.codigoZona.orEmpty().deleteHyphen()
            section = syncParams.ua.codigoSeccion.orEmpty().deleteHyphen()
            lastModified = syncDateSeconds(syncParams.onlyModified)
        }
        return ConsultantsQuery(params)
    }

    private fun syncDateSeconds(onlyModified: Boolean): Long {
        return if (onlyModified)
            getSyncDate() / NUMBER_ONE_THOUSAND
        else NUMBER_ZERO_LONG
    }
}
