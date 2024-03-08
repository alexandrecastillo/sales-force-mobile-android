package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.dream

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.dream.Dream
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.dream.EditCreateDream

interface DreamRepository {

    suspend fun syncDreams(uaKey: LlaveUA, country: String)

    suspend fun getDreams(consultantCode: String?, uaKey: LlaveUA): List<Dream>

    suspend fun deleteDream(dream: Dream?, country: String?)

    suspend fun createDreams(uaKey: LlaveUA, country: String, dream: EditCreateDream)

    suspend fun editDreams(uaKey: LlaveUA, country: String, dream: EditCreateDream, campaign: String)
}
