package biz.belcorp.salesforce.modules.consultants.core.domain.usecases.nivel

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.core.entities.zonificacion.Rol

class UaKey(private val sesionRepository: SessionRepository) {

    private val session get() = requireNotNull(sesionRepository.getSession())

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
        codigoZona = session.zona,
        codigoSeccion = session.seccion,
        consultoraId = null
    )

    private fun getGrKey(uaId: String) = LlaveUA(
        codigoRegion = session.region,
        codigoZona = uaId,
        codigoSeccion = session.seccion,
        consultoraId = null
    )

    private fun getGzKey(uaId: String) = LlaveUA(
        codigoRegion = session.region,
        codigoZona = session.zona,
        codigoSeccion = uaId,
        consultoraId = null
    )

}
