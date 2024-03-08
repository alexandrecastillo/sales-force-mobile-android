package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.tips

import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas.GrupoTipsVisita

interface TipsVisitaRepository {
    fun obtenerTipsDePersona(personaId: Long, rol: Rol): GrupoTipsVisita?
    fun obtenerIdCabeceraTip(personaId: Long, rol: Rol): Long?
}
