package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.habilidades

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.habilidades.DesarrolloHabilidad
import io.reactivex.Single

interface DesarrolloHabilidadesRepository {

    fun getDesarrolloHabilidades(llaveUA: LlaveUA): Single<List<DesarrolloHabilidad>>

}
