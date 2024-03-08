package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.tipsdesarrollo.tipsoferta

import biz.belcorp.salesforce.core.data.repository.auth.SafeApiCallHelper
import biz.belcorp.salesforce.core.entities.sql.tips.acompaniamiento.TipOfertaEntity
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.TipsOfertasRestApi
import io.reactivex.Single

class TipsOfertaCloudDataStore(
    private val api: TipsOfertasRestApi,
    private val apiCallHelper: SafeApiCallHelper
) {

    fun obtenerTipsOfertas(
        pais: String,
        documento: String,
        campania: String
    ): Single<List<TipOfertaEntity>> {
        return apiCallHelper.safeSingleApiCall {
            api.obtenerTipsOfertas(pais, documento, campania)
                .map { it.resultado!! }
        }
    }
}
