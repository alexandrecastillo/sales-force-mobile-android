package biz.belcorp.salesforce.modules.brightpath.core.domain.repository

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.usecases.sync.SyncType
import biz.belcorp.salesforce.modules.brightpath.core.domain.entities.LevelIndicator

interface BrightPathIndicatorRepository {
    suspend fun getIndicatorDataAsync(uaKey: LlaveUA): LevelIndicator
    suspend fun sync(uaKey: String): SyncType
}
