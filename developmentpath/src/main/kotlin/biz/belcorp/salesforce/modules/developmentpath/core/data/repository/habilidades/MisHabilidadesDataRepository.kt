package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.habilidades

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.habilidades.data.MisHabilidadesLocalStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.habilidades.MisHabilidadesDataMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.habilidades.Habilidad
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.habilidades.MisHabilidadesRepository
import io.reactivex.Single

class MisHabilidadesDataRepository(private val localStore: MisHabilidadesLocalStore,
                                   private val mapper: MisHabilidadesDataMapper
) : MisHabilidadesRepository {

    override fun recuperar(llaveUA: LlaveUA, codigoCampania: String): Single<List<Habilidad>> {
        return Single.create {

            var habilidades = emptyList<Habilidad>()
            val detalle = localStore.recuperarDetalle(llaveUA, codigoCampania)
            val maestras = localStore.recuperarMaestros()

            if (detalle != null)
                habilidades = mapper.parse(maestras, detalle)

            it.onSuccess(habilidades)
        }
    }
}
