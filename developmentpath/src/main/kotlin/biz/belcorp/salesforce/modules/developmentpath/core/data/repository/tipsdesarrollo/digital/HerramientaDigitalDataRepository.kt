package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.tipsdesarrollo.digital

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.digital.HerramientaDigitalEntity
import biz.belcorp.salesforce.core.utils.doOnSingle
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.tipsdesarrollo.TipsNegocioDBDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.tipsdesarrollo.TipsNegocioEntityMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.tipsdesarrollo.digital.HerramientaDigitalEntityMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.digital.HerramientaDigital
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.helper.Params
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.tipsdesarrollo.HerramientaDigitalRepository
import io.reactivex.Single

class HerramientaDigitalDataRepository(
    private val dataStore: TipsNegocioDBDataStore,
    private val tipsNegocioMapper: TipsNegocioEntityMapper<List<HerramientaDigitalEntity>>,
    private val dataMapper: HerramientaDigitalEntityMapper
) : HerramientaDigitalRepository {

    override fun obtenerDigital(params: Params): Single<List<HerramientaDigital>> {
        return doOnSingle { dataStore.obtenerTipsNegocio(params) }
            .map { entity ->
                val mapped = tipsNegocioMapper.map(entity)
                mapped.data?.let { dataMapper.map(it) } ?: listOf()
            }
    }
}
