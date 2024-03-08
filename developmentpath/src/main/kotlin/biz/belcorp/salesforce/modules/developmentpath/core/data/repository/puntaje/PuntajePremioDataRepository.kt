package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.puntaje

import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.puntaje.cloud.PuntajePremioCloudDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.puntaje.PuntajePremio
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.puntaje.PuntajePremioRepository
import io.reactivex.Single

class PuntajePremioDataRepository(private val cloudStore: PuntajePremioCloudDataStore) :
    PuntajePremioRepository {

    override fun recuperarPuntajePorConsultora(request: PuntajePremio.Request): Single<PuntajePremio> {
        return cloudStore.obtenerPuntaje(request)
    }

}
