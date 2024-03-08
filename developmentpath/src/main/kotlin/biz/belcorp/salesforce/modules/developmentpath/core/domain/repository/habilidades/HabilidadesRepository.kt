package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.habilidades

import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.habilidades.Habilidad

interface HabilidadesRepository {

    fun obtener(rol: Rol): List<Habilidad>
}
