package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.tipsdesarrollo

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.tips.tipsoferta.Oferta
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.tips.tipsoferta.Params
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.tips.tipsoferta.TipOferta
import io.reactivex.Single

interface TipsOfertaRepository {
    fun obtenerTipsOfertas(params: Params): Single<List<TipOferta<List<Oferta>>>>
}
