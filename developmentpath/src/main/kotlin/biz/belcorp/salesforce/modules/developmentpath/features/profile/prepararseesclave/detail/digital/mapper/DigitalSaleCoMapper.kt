package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.digital.mapper

import biz.belcorp.salesforce.core.utils.addAllIf
import biz.belcorp.salesforce.core.utils.formatOneDecimal
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant.EMPTY_STRING
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant.TITLE_ONLINE_STORE
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant.ZERO_NUMBER
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.digital.DigitalSaleConsultant
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.digital.DigitalSaleContainer
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.digital.helper.DigitalViewTypeIdentifier
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.digital.model.DigitalSaleCampaignChildModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.digital.model.DigitalSaleCampaignInformation
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.digital.model.DigitalSaleCampaignModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.digital.model.DigitalSaleItemModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.digital.model.DigitalSaleModel
import biz.belcorp.salesforce.base.R as BaseR
import biz.belcorp.salesforce.components.R as ComponentsR

class DigitalSaleCoMapper(
    private val textResolver: DigitalSaleTextResolver
) : DigitalSaleBaseMapper(textResolver) {
    fun map(data: DigitalSaleContainer, iso: String? = null): Pair<List<DigitalSaleModel>, String> =
        with(data) {
            val list = digitalSaleList.map { it as DigitalSaleConsultant }
            val campaignInformation = createCampaignInformation(list)
            val digitalModelList = arrayListOf(
                createUniqueIpItem(campaignInformation.uniqueIpList, role)
            )

            iso?.let { i ->
                if (!Constant.MTO_COUNTRIES.contains(i.toUpperCase())) {
                    digitalModelList.add(createMakeupAppItem(campaignInformation.makeupAppList))
                }
            }

            if (isGanaMas) {
                val subscriptionLabel = EMPTY_STRING
                digitalModelList.add(
                    createGanaMasItem(
                        campaignInformation.ganaMasList,
                        subscriptionLabel
                    )
                )
            }
            iso?.let { i ->
                if (!Constant.MTO_COUNTRIES.contains(i.toUpperCase())) {
                    val headerData = list.last()
                    digitalModelList.add(
                        createOnlineStoreItem(
                            onlineStoreTitle,
                            headerData,
                            campaignInformation
                        )
                    )
                }
            }
            digitalModelList.add(
                createDigitalCatalogItem(campaignInformation)
            )

            return digitalModelList.sortedBy { it.digitalId } to Constant.EMPTY_STRING
        }

    private fun createCampaignInformation(list: List<DigitalSaleConsultant>): DigitalSaleCampaignInformation {
        return DigitalSaleCampaignInformation().apply {
            list.forEach {
                consultantAppList.add(
                    createDigitalSaleCampaignModel(
                        it.orderSentApp,
                        it.campaign,
                        false,
                        ZERO_NUMBER.toString()
                    )
                )
                digitalAccompanimentList.add(
                    createDigitalSaleCampaignModel(
                        it.openVirtualCoach,
                        it.campaign,
                        false,
                        ZERO_NUMBER.toString()
                    )
                )
                uniqueIpList.add(
                    createDigitalSaleCampaignModel(
                        it.uniqueIp,
                        it.campaign,
                        false,
                        ZERO_NUMBER.toString()
                    )
                )
                digitalCatalogList.add(
                    createDigitalSaleCampaignModel(
                        it.usabilityDigitalCatalog,
                        it.campaign,
                        false,
                        ZERO_NUMBER.toString()
                    )
                )
                digitalCatalogSharedList.add(
                    createDigitalSaleCampaignModel(
                        false,
                        it.campaign,
                        true,
                        it.digitalCatalogQuantitySharedCatalogs.toString()
                    )
                )
                digitalCatalogPreOrderList.add(
                    createDigitalSaleCampaignModel(
                        false,
                        it.campaign,
                        true,
                        it.digitalCatalogReceivePreOrders.toString()
                    )
                )
                digitalCatalogApprovedPreOrderList.add(
                    createDigitalSaleCampaignModel(
                        false,
                        it.campaign,
                        true,
                        it.digitalCatalogApprovePreOrders.toString()
                    )
                )
                makeupAppList.add(
                    createDigitalSaleCampaignModel(
                        it.usabilityMakeupApp,
                        it.campaign,
                        false,
                        ZERO_NUMBER.toString()
                    )
                )
                ganaMasList.add(
                    createDigitalSaleCampaignModel(
                        it.ganamasBuy, it.campaign,
                        false, ZERO_NUMBER.toString()
                    )
                )
                onlineStoreBuyList.add(
                    createDigitalSaleCampaignModel(
                        it.onlineStoreBuy,
                        it.campaign,
                        false,
                        ZERO_NUMBER.toString()
                    )
                )
                onlineStoreSaleList.add(
                    createDigitalSaleCampaignModel(
                        false,
                        it.campaign,
                        true,
                        it.onlineStoreMtoSales.formatOneDecimal()
                    )
                )
                onlineStoreShareList.add(
                    createDigitalSaleCampaignModel(
                        it.onlineStoreShare,
                        it.campaign,
                        false,
                        ZERO_NUMBER.toString()
                    )
                )
            }
        }
    }

    private fun createDigitalSaleCampaignModel(
        value: Boolean,
        campaign: String,
        hasNumberValue: Boolean,
        numberValue: String,
    ): DigitalSaleCampaignModel {
        val (label: String, color: Int) = getCampaignChildAttributes(value)
        return DigitalSaleCampaignModel(
            campaign = campaign,
            campaignCode = textResolver.getCampaignFormatted(campaign),
            saleCampaignChild = listOf(
                DigitalSaleCampaignChildModel(
                    colorRes = if (hasNumberValue) R.color.blue_item else color,
                    value = if (hasNumberValue) numberValue else label
                )
            )
        )
    }

    private fun createGanaMasItem(
        digitalCampaignList: List<DigitalSaleCampaignModel>,
        subscription: String
    ): DigitalSaleModel {
        return DigitalSaleModel(
            digitalId = Constant.GANA_MAS_ITEM_ID,
            title = textResolver.getGanaMasName(),
            subtitle = subscription,
            image = R.drawable.ic_rdd_ofertas_digitales,
            type = DigitalViewTypeIdentifier.SINGLE_HORIZONTAL,
            itemList = listOf(
                DigitalSaleItemModel(
                    description = textResolver.getGanaMasDescription(),
                    campaignList = digitalCampaignList,
                    type = DigitalViewTypeIdentifier.SINGLE_HORIZONTAL
                )
            )
        )
    }

    private fun createOnlineStoreItem(
        title: String,
        headerData: DigitalSaleConsultant,
        campaignData: DigitalSaleCampaignInformation
    ): DigitalSaleModel {

        val subscriptionLabel = textResolver.getRegisteredLabel(
            headerData.onlineStoreIsSuscribed,
            headerData.onlineCampaignSubscription
        )
        val image = if (title.contains(TITLE_ONLINE_STORE))
            ComponentsR.drawable.ic_online_store
        else
            R.drawable.ic_rdd_catalogo_digital

        val detailist = mutableListOf<DigitalSaleItemModel>().apply {
            addAllIf(
                headerData.onlineStoreIsSuscribed,
                listOf(
                    DigitalSaleItemModel(
                        id = Constant.NUMBER_ONE,
                        description = textResolver.getOnlineDescriptionShare(),
                        campaignList = campaignData.onlineStoreShareList,
                        type = DigitalViewTypeIdentifier.SINGLE_HORIZONTAL
                    ), DigitalSaleItemModel(
                        id = Constant.NUMBER_TWO,
                        description = textResolver.getOnlineDescriptionBuy(title),
                        campaignList = campaignData.onlineStoreBuyList,
                        type = DigitalViewTypeIdentifier.SINGLE_HORIZONTAL
                    ),
                    DigitalSaleItemModel(
                        id = Constant.NUMBER_THREE,
                        description = textResolver.getOnlineDescriptionSale(),
                        campaignList = campaignData.onlineStoreSaleList,
                        type = DigitalViewTypeIdentifier.SINGLE_HORIZONTAL
                    )
                )
            )
        }
        return DigitalSaleModel(
            digitalId = Constant.NUMBER_ONE,
            title = title,
            subtitle = subscriptionLabel,
            image = image,
            type = DigitalViewTypeIdentifier.SINGLE_HORIZONTAL,
            itemList = detailist
        )
    }


    private fun getCampaignChildAttributes(value: Boolean): Pair<String, Int> {
        return if (value) Constant.YES to BaseR.color.positivo else Constant.NO to BaseR.color.negativo
    }
}
