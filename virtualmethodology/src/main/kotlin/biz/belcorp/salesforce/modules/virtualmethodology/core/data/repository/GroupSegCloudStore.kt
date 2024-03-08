package biz.belcorp.salesforce.modules.virtualmethodology.core.data.repository

import biz.belcorp.salesforce.core.data.repository.auth.SafeApiCallHelper
import biz.belcorp.salesforce.core.entities.sql.metodologia.GrupoSegEntity
import biz.belcorp.salesforce.core.entities.sql.metodologia.SegmentacionEntity
import biz.belcorp.salesforce.modules.virtualmethodology.core.data.network.VirtualMethodologyApi


class GroupSegCloudStore(
    private val virtualMethodologyApi: VirtualMethodologyApi,
    private val apiCallHelper: SafeApiCallHelper
) {

    suspend fun syncGroupSegmentacion(
        country: String, ua: String
    ): Pair<List<GrupoSegEntity>, List<SegmentacionEntity>> {
        val response = apiCallHelper.safeLegacyApiCall {
            virtualMethodologyApi.download(country, ua)
        }
        val groups = response?.resultado?.listadoGrupoMensajeSegmentacion ?: emptyList()
        val list = response?.resultado?.listadoMensajeSegmentacion ?: emptyList()
        return Pair(groups, list)
    }

}
