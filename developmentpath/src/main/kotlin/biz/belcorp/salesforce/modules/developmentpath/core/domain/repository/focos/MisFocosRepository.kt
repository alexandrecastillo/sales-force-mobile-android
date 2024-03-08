package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.focos

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.focos.Foco
import io.reactivex.Single

interface MisFocosRepository {
    fun recuperar(llaveUa: LlaveUA): Single<List<Foco>>
}
