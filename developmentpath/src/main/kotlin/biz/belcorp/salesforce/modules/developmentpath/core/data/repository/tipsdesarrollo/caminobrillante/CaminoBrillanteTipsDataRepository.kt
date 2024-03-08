package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.tipsdesarrollo.caminobrillante

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.caminobrillante.CaminoBrillanteTipEntity
import biz.belcorp.salesforce.core.utils.doOnSingle
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.tipsdesarrollo.TipsNegocioDBDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.tipsdesarrollo.TipsNegocioEntityMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.tipsdesarrollo.tips.caminobrillante.CaminoBrillanteTipsEntityMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.helper.Params
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.tips.caminobrillante.CaminoBrillanteTip
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.tipsdesarrollo.CaminoBrillanteTipsRepository
import io.reactivex.Single

class CaminoBrillanteTipsDataRepository(
    private val dataStore: TipsNegocioDBDataStore,
    private val tipsNegocioMapper: TipsNegocioEntityMapper<CaminoBrillanteTipEntity>,
    private val dataMapper: CaminoBrillanteTipsEntityMapper
) : CaminoBrillanteTipsRepository {

    override fun obtenerTips(params: Params): Single<CaminoBrillanteTip> {
        return doOnSingle { dataStore.obtenerTipsNegocio(params) }
            .map { entity ->
                val mapped = tipsNegocioMapper.map(entity)
                mapped.data?.let { dataMapper.map(it) } ?: CaminoBrillanteTip()
            }
    }
}
