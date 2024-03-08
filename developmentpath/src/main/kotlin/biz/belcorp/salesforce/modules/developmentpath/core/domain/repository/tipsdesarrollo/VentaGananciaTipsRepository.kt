package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.tipsdesarrollo

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.helper.Params
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.tips.Tip
import io.reactivex.Single

interface VentaGananciaTipsRepository {
    fun obtenerTips(params: Params): Single<List<Tip>>
}
