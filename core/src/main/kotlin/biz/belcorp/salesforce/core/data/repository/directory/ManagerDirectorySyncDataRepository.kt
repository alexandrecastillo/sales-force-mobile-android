package biz.belcorp.salesforce.core.data.repository.directory

import biz.belcorp.salesforce.core.data.repository.directory.cloud.ManagerDirectoryCloudStore
import biz.belcorp.salesforce.core.data.repository.directory.cloud.dto.DirectoryParams
import biz.belcorp.salesforce.core.data.repository.directory.cloud.dto.ManagerDirectoryQuery
import biz.belcorp.salesforce.core.data.repository.directory.data.ManagerDirectoryDataStore
import biz.belcorp.salesforce.core.data.repository.directory.data.ManagerDirectoryTableDataStore
import biz.belcorp.salesforce.core.data.repository.directory.mappers.ManagerDirectoryMapper
import biz.belcorp.salesforce.core.data.repository.directory.mappers.ManagerDirectoryTableMapper
import biz.belcorp.salesforce.core.domain.entities.people.SyncParams
import biz.belcorp.salesforce.core.domain.repository.directory.ManagerDirectorySyncRepository
import biz.belcorp.salesforce.core.utils.deleteHyphen

class ManagerDirectorySyncDataRepository(
    private val cloudStore: ManagerDirectoryCloudStore,
    private val dataStore: ManagerDirectoryDataStore,
    private val tableDataStore: ManagerDirectoryTableDataStore,
    private val mapper: ManagerDirectoryMapper,
    private val tableMapper: ManagerDirectoryTableMapper
) : ManagerDirectorySyncRepository {

    override suspend fun sync(params: SyncParams) = with(params) {
        val region = ua.codigoRegion ?: return@with
        val role = ua.rolHijoAsociado.codigoRol.deleteHyphen()
        val directoryParams = DirectoryParams(campaign, countryIso, region, role)
        val data = cloudStore.getManagerDirectory(ManagerDirectoryQuery(directoryParams))
        val entities = data.directory.map { mapper.map(it) }
        dataStore.saveManagerDirectory(entities)
        val tableEntities = tableMapper.map(data.directory)
        tableDataStore.saveManagers(tableEntities)
    }
}
