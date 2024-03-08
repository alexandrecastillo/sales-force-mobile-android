package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.digital

import biz.belcorp.salesforce.core.domain.entities.configuration.MainBrandType
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.utils.Constants

data class DigitalSaleContainer(
    val isGanaMas: Boolean = false,
    val isOnlineStoreEnabled: Boolean = false,
    val onlineStoreTitle: String = Constants.EMPTY_STRING,
    @MainBrandType val mainBrandType: String,
    val digitalSaleList: List<DigitalSale> = listOf(),
    val role: Rol
)
