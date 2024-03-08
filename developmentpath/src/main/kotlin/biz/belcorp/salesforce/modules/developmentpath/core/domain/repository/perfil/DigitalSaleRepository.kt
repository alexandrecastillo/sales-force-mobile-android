package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.perfil

import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.digital.DigitalSale

interface DigitalSaleRepository {
    suspend fun getDigitalSaleByRole(
        personIdentifier: PersonIdentifier,
        uaKey: LlaveUA,
        campaigns: List<String>
    ): List<DigitalSale>
}
