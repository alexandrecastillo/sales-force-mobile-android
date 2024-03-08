package biz.belcorp.salesforce.modules.kpis.core.domain.repository

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.usecases.sync.SyncType
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.capitalization.PostulantKpi
import biz.belcorp.salesforce.modules.kpis.core.domain.usecases.sync.SyncParams


interface PostulantsKpiRepository {
    suspend fun getPostulantsKpi(ua: LlaveUA): List<PostulantKpi>
    suspend fun sync(params: SyncParams): SyncType
}
