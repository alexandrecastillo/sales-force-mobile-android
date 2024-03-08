package biz.belcorp.salesforce.core.domain.usecases.ua

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.core.entities.zonificacion.Rol

class GenerateUaUseCase(val sessionRepository: SessionRepository) {

    private val session get() = requireNotNull(sessionRepository.getSession())


    fun get() = LlaveUA(
        codigoRegion = session.region,
        codigoZona = session.zona,
        codigoSeccion = session.seccion,
        consultoraId = null
    )

    fun build(uaId: String) = when (session.rol) {
        Rol.DIRECTOR_VENTAS -> getDvKey(uaId)
        Rol.GERENTE_REGION -> getGrKey(uaId)
        Rol.GERENTE_ZONA -> getGzKey(uaId)
        else -> get()
    }

    private fun getDvKey(uaId: String) = LlaveUA(
        codigoRegion = uaId,
        codigoZona = null,
        codigoSeccion = null,
        consultoraId = null
    )

    private fun getGrKey(uaId: String) = LlaveUA(
        codigoRegion = session.region,
        codigoZona = uaId,
        codigoSeccion = null,
        consultoraId = null
    )

    private fun getGzKey(uaId: String) = LlaveUA(
        codigoRegion = session.region,
        codigoZona = session.zona,
        codigoSeccion = uaId,
        consultoraId = null
    )
}
