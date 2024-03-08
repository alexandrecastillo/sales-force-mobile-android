package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.tipsdesarrollo.ventaganancia

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.ventaganancia.VentaGananciaEntity
import biz.belcorp.salesforce.core.utils.doOnSingle
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.tipsdesarrollo.TipsNegocioDBDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.tipsdesarrollo.TipsNegocioEntityMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.tipsdesarrollo.ventaganancia.VentaGananciaEntityMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.helper.Params
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.ventaganancia.VentaGanancia
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.tipsdesarrollo.VentaGananciaRepository
import io.reactivex.Single

class VentaGananciaDataRepository(
    private val dataStore: TipsNegocioDBDataStore,
    private val tipsNegocioMapper: TipsNegocioEntityMapper<VentaGananciaEntity>,
    private val dataMapper: VentaGananciaEntityMapper
) : VentaGananciaRepository {

    override fun obtenerVentaGanancia(params: Params): Single<VentaGanancia> {
        return doOnSingle { dataStore.obtenerTipsNegocio(params) }
            .map { entity ->
                val mapped = tipsNegocioMapper.map(entity)
                mapped.data?.let { dataMapper.map(it) } ?: VentaGanancia()
            }
    }
}
