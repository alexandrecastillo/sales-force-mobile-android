package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.digital.mapper

import biz.belcorp.salesforce.core.domain.entities.configuration.MainBrandType
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.addIf
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.digital.helper.DigitalViewTypeIdentifier
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.digital.model.DigitalSaleCampaignInformation
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.digital.model.DigitalSaleCampaignModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.digital.model.DigitalSaleItemModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.digital.model.DigitalSaleModel

open class DigitalSaleBaseMapper(
    private val textResolver: DigitalSaleTextResolver
) {

    fun createConsultantAppItem(
        digitalCampaignList: List<DigitalSaleCampaignModel>,
        @MainBrandType mainBrandType: String,
        role: Rol
    ): DigitalSaleModel {
        val imageRes =
            if (mainBrandType == MainBrandType.LBEL) R.drawable.ic_rdd_app_lbel_conmigo else R.drawable.ic_rdd_app_esika_conmigo
        return DigitalSaleModel(
            digitalId = Constant.CONSULTANT_APP_ITEM_ID,
            title = textResolver.getConsultantAppName(mainBrandType),
            subtitle = textResolver.getConsultantAppDescription(role),
            image = imageRes,
            type = DigitalViewTypeIdentifier.SINGLE_HORIZONTAL,
            itemList = listOf(
                DigitalSaleItemModel(
                    id = Constant.NUMBER_ONE,
                    campaignList = digitalCampaignList,
                    type = DigitalViewTypeIdentifier.SINGLE_HORIZONTAL
                )
            )
        )
    }

    fun createDigitalAccompanimentItem(digitalCampaignList: List<DigitalSaleCampaignModel>): DigitalSaleModel {
        return DigitalSaleModel(
            digitalId = Constant.DIGITAL_ACCOMPANIMENT_ITEM_ID,
            title = textResolver.getAccompanimentName(),
            subtitle = textResolver.getAccompanimentDescription(),
            image = R.drawable.ic_rdd_acompaniamiento_digital,
            type = DigitalViewTypeIdentifier.SINGLE_HORIZONTAL,
            itemList = listOf(
                DigitalSaleItemModel(
                    campaignList = digitalCampaignList,
                    type = DigitalViewTypeIdentifier.SINGLE_HORIZONTAL
                )
            )
        )
    }

    fun createUniqueIpItem(
        digitalCampaignList: List<DigitalSaleCampaignModel>,
        role: Rol
    ): DigitalSaleModel {
        return DigitalSaleModel(
            digitalId = Constant.UNIQUE_IP_ITEM_ID,
            title = textResolver.getUniqueIpName(),
            subtitle = textResolver.getUniqueIpDescription(role),
            image = R.drawable.ic_rdd_ip_unico,
            type = DigitalViewTypeIdentifier.SINGLE_HORIZONTAL,
            itemList = listOf(
                DigitalSaleItemModel(
                    campaignList = digitalCampaignList,
                    type = DigitalViewTypeIdentifier.SINGLE_HORIZONTAL
                )
            )
        )
    }

    fun createDigitalCatalogItem(campaignData: DigitalSaleCampaignInformation): DigitalSaleModel {
        val detailist = mutableListOf<DigitalSaleItemModel>().apply {
            addIf(
                true, DigitalSaleItemModel(
                    id = Constant.ZERO_NUMBER,
                    campaignList = campaignData.digitalCatalogList,
                    type = DigitalViewTypeIdentifier.SINGLE_HORIZONTAL
                )
            )
            addIf(
                campaignData.digitalCatalogSharedList.size > 0,
                DigitalSaleItemModel(
                    id = Constant.NUMBER_ONE,
                    description = textResolver.getDigitalCatalogItemOne(),
                    campaignList = campaignData.digitalCatalogSharedList,
                    type = DigitalViewTypeIdentifier.SINGLE_HORIZONTAL
                )
            )
            addIf(
                campaignData.digitalCatalogPreOrderList.size > 0,
                DigitalSaleItemModel(
                    id = Constant.NUMBER_TWO,
                    description = textResolver.getDigitalCatalogItemTwo(),
                    campaignList = campaignData.digitalCatalogPreOrderList,
                    type = DigitalViewTypeIdentifier.SINGLE_HORIZONTAL
                )
            )

            addIf(
                campaignData.digitalCatalogApprovedPreOrderList.size > 0,
                DigitalSaleItemModel(
                    id = Constant.NUMBER_THREE,
                    description = textResolver.getDigitalCatalogItemThree(),
                    campaignList = campaignData.digitalCatalogApprovedPreOrderList,
                    type = DigitalViewTypeIdentifier.SINGLE_HORIZONTAL
                )
            )

        }
        return DigitalSaleModel(
            digitalId = Constant.ZERO_NUMBER,
            title = textResolver.getDigitalCatalogName(),
            subtitle = textResolver.getDigitalCatalogDescription(),
            image = R.drawable.ic_rdd_catalogo_digital,
            type = DigitalViewTypeIdentifier.SINGLE_HORIZONTAL,
            itemList = detailist
        )
    }

    fun createMakeupAppItem(digitalCampaignList: List<DigitalSaleCampaignModel>): DigitalSaleModel {
        return DigitalSaleModel(
            digitalId = Constant.MAKEUP_APP_ITEM_ID,
            title = textResolver.getMakeupAppName(),
            subtitle = textResolver.getMakeupAppDescription(),
            image = R.drawable.ic_rdd_asesor_de_belleza,
            type = DigitalViewTypeIdentifier.SINGLE_HORIZONTAL,
            itemList = listOf(
                DigitalSaleItemModel(
                    campaignList = digitalCampaignList,
                    type = DigitalViewTypeIdentifier.SINGLE_HORIZONTAL
                )
            )
        )
    }
}
