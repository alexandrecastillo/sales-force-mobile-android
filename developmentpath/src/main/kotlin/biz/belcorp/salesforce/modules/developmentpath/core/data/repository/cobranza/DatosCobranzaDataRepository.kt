package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.cobranza

import biz.belcorp.salesforce.core.domain.exceptions.UnsupportedRoleException
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.cobranza.data.DatosCobranzaConsultoraSociaDbStore
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.cobranza.DatosCobranzaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.cobranza.ObtenerDatosCobranzaUseCase
import io.reactivex.Single

class DatosCobranzaDataRepository(
    private val datosCobranzaConsultoraSociaDbStore: DatosCobranzaConsultoraSociaDbStore
) : DatosCobranzaRepository {

    override fun recuperar(
        personaId: Long,
        rol: Rol
    ): Single<ObtenerDatosCobranzaUseCase.DatosCobranza> {
        return when (rol) {
            Rol.CONSULTORA, Rol.SOCIA_EMPRESARIA -> datosCobranzaConsultoraSociaDbStore.recuperar(
                personaId
            )
            else -> throw UnsupportedRoleException(rol)
        }
    }
}
