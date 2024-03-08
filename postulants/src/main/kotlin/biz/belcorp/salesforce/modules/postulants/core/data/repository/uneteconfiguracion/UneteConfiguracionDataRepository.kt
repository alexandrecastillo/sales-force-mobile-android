package biz.belcorp.salesforce.modules.postulants.core.data.repository.uneteconfiguracion

import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.Configuracion
import biz.belcorp.salesforce.modules.postulants.core.domain.repository.UneteConfiguracionRepository
import io.reactivex.Single

class UneteConfiguracionDataRepository(
    private val dbStore: ConfiguracionDBStore,
    private val mapper: ConfiguracionEntityDataMapper
) : UneteConfiguracionRepository {

    override fun get(pais: String, rol: String): Single<Configuracion> {
        return dbStore.get(pais, rol).map { mapper.reverseMap(it) }
    }

}
