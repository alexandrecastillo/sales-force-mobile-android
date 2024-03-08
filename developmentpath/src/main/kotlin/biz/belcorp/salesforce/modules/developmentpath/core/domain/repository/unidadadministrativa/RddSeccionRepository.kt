package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.unidadadministrativa

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion.SeccionRdd

interface RddSeccionRepository {
    fun recuperarParaAvance(codigoZona: String): List<SeccionRdd>
}
