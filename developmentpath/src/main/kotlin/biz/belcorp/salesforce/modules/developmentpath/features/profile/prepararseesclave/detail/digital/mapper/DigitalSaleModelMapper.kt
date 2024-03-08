package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.digital.mapper

import biz.belcorp.salesforce.core.utils.isCO
import biz.belcorp.salesforce.core.utils.isSE
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant.ITEM_DISABLES
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.digital.DigitalSaleContainer
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.digital.model.DigitalSaleContainerModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.digital.model.DigitalSaleModel

class DigitalSaleModelMapper(
    private val digitalSaleConsultantMapper: DigitalSaleCoMapper,
    private val digitalSaleBusinessPartnerMapper: DigitalSaleSeMapper
) {

    fun map(data: DigitalSaleContainer, iso: String? = null): DigitalSaleContainerModel =
        with(data) {
            val (list, activesTitle) = when {
                role.isCO() -> digitalSaleConsultantMapper.map(this, iso = iso)
                role.isSE() -> digitalSaleBusinessPartnerMapper.map(this)
                else -> emptyList<DigitalSaleModel>() to Constant.EMPTY_STRING
            }
            return DigitalSaleContainerModel(list.filterNot { it.digitalId in ITEM_DISABLES }, activesTitle)
        }
}
