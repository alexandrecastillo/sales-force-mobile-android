package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.focos

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.focos.DetalleFoco
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.focos.DetalleFocoSE
import io.reactivex.Completable

interface GuardadoFocosAsyncRepository {
    fun guardarSE(detallesFoco: List<DetalleFocoSE>): Completable
    fun guardar(detallesFoco: List<DetalleFoco>): Completable
}
