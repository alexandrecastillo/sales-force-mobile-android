package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.cobranza

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.cobranza.CobranzaCampaniaAnterior

interface DatosCobranzaCampaniaAnteriorRepository {
    suspend fun obtener(uaKey: LlaveUA, campaign: String): CobranzaCampaniaAnterior
}
