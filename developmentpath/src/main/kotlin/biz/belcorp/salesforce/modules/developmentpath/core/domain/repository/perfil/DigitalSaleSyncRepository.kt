package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.perfil

import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.profile.model.DigitalSaleQueryParams

interface DigitalSaleSyncRepository {
    suspend fun sync(role: Rol, params: DigitalSaleQueryParams)
}
