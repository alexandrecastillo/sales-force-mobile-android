package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.focos

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.focos.data.propios.MisFocosLocalStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.focos.propios.MisFocosDataMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.focos.Foco
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.focos.MisFocosRepository
import io.reactivex.Single

class MisFocosDataRepository (private val mapper: MisFocosDataMapper,
                              private val localStore: MisFocosLocalStore
) : MisFocosRepository {

    override fun recuperar(llaveUa: LlaveUA): Single<List<Foco>> {

        return Single.create {
            val detalle = localStore.recuperarFocosDetalle(llaveUa)
            val maestros = localStore.recuperarFocosMaestros()

            if (detalle == null) {
                it.onSuccess(emptyList())
            } else {
                val focos = mapper.parse(maestros, detalle)
                it.onSuccess(focos)
            }
        }
    }
}
