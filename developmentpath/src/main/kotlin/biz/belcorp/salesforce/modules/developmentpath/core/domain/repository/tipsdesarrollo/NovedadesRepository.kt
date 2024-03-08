package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.tipsdesarrollo

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.helper.Params
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.novedades.Novedades
import io.reactivex.Single

interface NovedadesRepository {
    fun obtenerNovedades(params: Params): Single<List<Novedades>>
}
