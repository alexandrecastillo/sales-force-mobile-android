package biz.belcorp.salesforce.modules.calculator.core.data.repository.upperlevel

import biz.belcorp.salesforce.modules.calculator.core.data.repository.upperlevel.data.UpperLevelDBDataStore
import biz.belcorp.salesforce.modules.calculator.core.data.repository.upperlevel.mapper.UpperLevelEntityDataMapper
import biz.belcorp.salesforce.modules.calculator.core.domain.entities.UpperLevel
import biz.belcorp.salesforce.modules.calculator.core.domain.repository.upperlevel.UpperLevelRepository

class UpperLevelDataRepository (
    private val upperLevelDBDataStore: UpperLevelDBDataStore,
    private val upperLevelEntityDataMapper: UpperLevelEntityDataMapper) : UpperLevelRepository {

    override suspend fun list(): List<UpperLevel>? {
        val list = upperLevelDBDataStore.all()
        return upperLevelEntityDataMapper.parseUpperLevel(list)
    }
}
