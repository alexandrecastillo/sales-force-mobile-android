package biz.belcorp.salesforce.modules.consultants.core.data.repository.secciones

import biz.belcorp.salesforce.core.domain.entities.ua.Seccion
import biz.belcorp.salesforce.modules.consultants.core.domain.repository.secciones.SeccionRepository
import io.reactivex.Observable

class SeccionDataRepository(
    private val dataStore: SeccionDBDataStore,
    private val dataMapper: SeccionEntityDataMapper
) : SeccionRepository {

    override val all: Observable<List<Seccion>>
        get() {
            return dataStore.all.map { this.dataMapper.parse(it) }
        }

}
