package biz.belcorp.salesforce.modules.postulants.core.domain.repository

import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.Configuracion
import io.reactivex.Single

interface UneteConfiguracionRepository {
    fun get(pais: String, rol: String): Single<Configuracion>
}
