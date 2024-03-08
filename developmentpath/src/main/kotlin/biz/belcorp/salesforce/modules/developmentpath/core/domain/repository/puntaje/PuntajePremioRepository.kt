package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.puntaje

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.puntaje.PuntajePremio
import io.reactivex.Single

interface PuntajePremioRepository {
    fun recuperarPuntajePorConsultora(request: PuntajePremio.Request): Single<PuntajePremio>
}
