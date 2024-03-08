package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.tipsdesarrollo

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.helper.Params
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.tips.caminobrillante.CaminoBrillanteTip
import io.reactivex.Single

interface CaminoBrillanteTipsRepository {
    fun obtenerTips(params: Params): Single<CaminoBrillanteTip>
}
