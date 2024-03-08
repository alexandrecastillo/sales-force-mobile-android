package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.focos

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion.RegionRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion.SeccionRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion.ZonaRdd

interface FocosHabilidadesEnDashboardRepository {
    fun obtenerSecciones(): List<SeccionRdd>
    fun obtenerZonas(): List<ZonaRdd>
    fun obtenerRegiones(): List<RegionRdd>
}
