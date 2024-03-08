package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.tipsdesarrollo.novedades

import biz.belcorp.salesforce.core.utils.doOnSingle
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.tipsdesarrollo.novedades.NovedadesDBDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.tipsdesarrollo.novedades.ListaIncentivosMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.helper.Params
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.novedades.Documento
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.tipsdesarrollo.DocumentoRepository
import io.reactivex.Single

class DocumentoDataRepository(
    private val mapper: ListaIncentivosMapper,
    private val dataStore: NovedadesDBDataStore
) : DocumentoRepository {

    override fun obtenerDocumentos(params: Params): Single<List<Documento>> {
        return doOnSingle {
            val data = dataStore.obtenerNovedades(params)
            mapper.parse(data)
        }
    }
}
