package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.cobranza

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.cobranza.CobranzaYGanancia
import io.reactivex.Flowable

interface CobranzaYGananciaRepository {
    fun obtenerCobranzaGanancia(llaveUa: LlaveUA): Flowable<CobranzaYGanancia>
}
