package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.habilidades.avance

import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.habilidades.AvanceHabilidad
import io.reactivex.Single

interface AvanceHabilidadesRepository {

    fun getAvanceHabilidades(request: RequestAvanceHabilidades): Single<Pair<List<String>, List<AvanceHabilidad>>>


    class RequestAvanceHabilidades(val rol: Rol,
                                   val zona: String?,
                                   val region: String,
                                   val campanias: List<String>)

}
