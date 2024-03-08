package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.unidadadministrativa

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.entities.zonificacion.UnidadAdministrativa
import biz.belcorp.salesforce.core.entities.zonificacion.Rol

interface UnidadAdministrativaRepository {
    fun obtenerUaPorPlan(planId: Long): UnidadAdministrativa?
    fun obtenerUaPorLlave(llaveUA: LlaveUA): UnidadAdministrativa?
    fun obtenerLlaveUaPorIdPersona(personaId: Long, rol: Rol): LlaveUA?
}
