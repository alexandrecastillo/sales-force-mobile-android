package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.unidadadministrativa

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion.ZonaRdd

interface RddZonaRepository {
    fun recuperarParaAvance(codigoRegion: String): List<ZonaRdd>
}
