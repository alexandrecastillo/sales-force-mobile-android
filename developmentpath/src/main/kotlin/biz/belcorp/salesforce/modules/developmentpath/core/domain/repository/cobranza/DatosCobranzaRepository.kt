package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.cobranza

import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.cobranza.ObtenerDatosCobranzaUseCase
import io.reactivex.Single

interface DatosCobranzaRepository {
    fun recuperar(personaId: Long, rol: Rol): Single<ObtenerDatosCobranzaUseCase.DatosCobranza>
}
