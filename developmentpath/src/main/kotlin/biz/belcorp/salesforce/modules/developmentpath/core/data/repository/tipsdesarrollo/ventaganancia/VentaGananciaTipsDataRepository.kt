package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.tipsdesarrollo.ventaganancia

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tips.TipEntity
import biz.belcorp.salesforce.core.utils.doOnSingle
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.tipsdesarrollo.TipsNegocioDBDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.tipsdesarrollo.TipsNegocioEntityMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.tipsdesarrollo.tips.TipEntityMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.helper.Params
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.tips.Tip
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.tipsdesarrollo.VentaGananciaTipsRepository
import io.reactivex.Single

class VentaGananciaTipsDataRepository(
    private val dataStore: TipsNegocioDBDataStore,
    private val tipsNegocioMapper: TipsNegocioEntityMapper<List<TipEntity>>,
    private val dataMapper: TipEntityMapper
) : VentaGananciaTipsRepository {

    override fun obtenerTips(params: Params): Single<List<Tip>> {
        return doOnSingle { dataStore.obtenerTipsNegocio(params) }
            .map { entity ->
                val mapped = tipsNegocioMapper.map(entity)
                mapped.data?.let { dataMapper.map(it) } ?: listOf()
            }
    }
}
