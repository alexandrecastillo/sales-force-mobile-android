package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.tipsdesarrollo.tipsoferta

import biz.belcorp.salesforce.core.entities.sql.tips.acompaniamiento.TipOfertaEntity
import biz.belcorp.salesforce.core.utils.doOnSingle
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.tipsdesarrollo.tipsoferta.TipsOfertaCloudDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.tipsdesarrollo.tipsoferta.TipsOfertaDBDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.tipsdesarrollo.tips.tipsoferta.TipsOfertaEntityMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.tips.tipsoferta.Oferta
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.tips.tipsoferta.Params
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.tips.tipsoferta.TipOferta
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.tipsdesarrollo.TipsOfertaRepository
import io.reactivex.Single

class TipsOfertaDataRepository(
    private val cloudDataStore: TipsOfertaCloudDataStore,
    private val localDataStore: TipsOfertaDBDataStore,
    private val mapper: TipsOfertaEntityMapper
) : TipsOfertaRepository {

    override fun obtenerTipsOfertas(params: Params): Single<List<TipOferta<List<Oferta>>>> {
        return if (params.offline) {
            obtenerLocal(params)
        } else {
            obtenerCloud(params)
        }
    }

    private fun obtenerLocal(params: Params): Single<List<TipOferta<List<Oferta>>>> {
        val single = doOnSingle {
            localDataStore.obtenerTipsOferta(params.personaId, params.ua)
        }
        return single.map(mapper::map)
    }

    private fun obtenerCloud(params: Params): Single<List<TipOferta<List<Oferta>>>> {
        return cloudDataStore.obtenerTipsOfertas(params.pais, params.documento, params.campania)
            .filter {
                it.isNotEmpty()
            }
            .flatMapSingle {
                actualizarDb(it)
                obtenerLocal(params)
            }
            .onErrorResumeNext {
                obtenerLocal(params)
            }
    }

    private fun actualizarDb(data: List<TipOfertaEntity>) {
        localDataStore.actualizarData(data)
    }
}
