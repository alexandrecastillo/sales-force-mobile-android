package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.tipsdesarrollo.novedades

import biz.belcorp.salesforce.core.utils.doOnSingle
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.tipsdesarrollo.novedades.NovedadesDBDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.tipsdesarrollo.novedades.ListaNovedadesMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.helper.Params
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.novedades.Novedades
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.tipsdesarrollo.NovedadesRepository
import io.reactivex.Single

class NovedadesDataRepository(
    private val mapper: ListaNovedadesMapper,
    private val dataStore: NovedadesDBDataStore
) : NovedadesRepository {

    override fun obtenerNovedades(params: Params): Single<List<Novedades>> {
        return doOnSingle {
            val data = dataStore.obtenerNovedades(params)
            mapper.parse(data)
        }
    }
}
