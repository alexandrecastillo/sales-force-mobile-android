package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.metas.cloud

import biz.belcorp.salesforce.core.data.repository.auth.SafeApiCallHelper
import biz.belcorp.salesforce.core.entities.sql.datos.MetasEntity
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.PerfilApi
import io.reactivex.Single

class MetaConsultoraCloudDataStore(
    private val api: PerfilApi,
    private val apiCallHelper: SafeApiCallHelper
) {

    fun guardar(metasConsultora: List<MetasEntity>): Single<MetasEntity> {
        return apiCallHelper.safeSingleApiCall {
            api.guardar(metasConsultora)
        }
    }

}
