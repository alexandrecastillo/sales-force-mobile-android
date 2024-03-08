package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.habilidades

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.habilidades.Habilidad
import io.reactivex.Single

interface MisHabilidadesRepository {

    fun recuperar(llaveUA: LlaveUA, codigoCampania: String): Single<List<Habilidad>>
}
