package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.digital.mapper

import biz.belcorp.salesforce.core.utils.toPercentageWithLowerThousands
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.digital.DigitalSaleBusinessPartner
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.digital.DigitalSaleContainer
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.digital.helper.DigitalViewTypeIdentifier
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.digital.model.*
import biz.belcorp.salesforce.base.R as BaseR

class DigitalSaleSeMapper(
    private val textResolver: DigitalSaleTextResolver
) : DigitalSaleBaseMapper(textResolver) {

    fun map(data: DigitalSaleContainer): Pair<List<DigitalSaleModel>, String> = with(data) {
        val list = digitalSaleList.map { it as DigitalSaleBusinessPartner }
        val campaignInfo = createCampaignInformationSe(list, isGanaMas)
        val digitalModelList = arrayListOf(
            createConsultantAppItem(campaignInfo.consultantAppList, mainBrandType, role),
            createUniqueIpItem(campaignInfo.uniqueIpList, role)
        )
        val model = if (isGanaMas) createGanaMasItem(
            campaignInfo.ganaMasList,
            getGanMasDescriptionLabel(list.last())
        )
        else createDigitalOfferItem(campaignInfo.ganaMasList)
        digitalModelList.add(model)
        return digitalModelList.sortedBy { it.digitalId } to getActivesLabel(list.last())
    }

    private fun getActivesLabel(digitalSale: DigitalSaleBusinessPartner): String =
        with(digitalSale) {
            return textResolver.getActivesLabel(
                finalActiveConsultants,
                textResolver.getCampaignFormatted(campaign)
            )
        }

    private fun getGanMasDescriptionLabel(digitalSale: DigitalSaleBusinessPartner): String =
        with(digitalSale) {
            return textResolver.getGanaMasDescriptionSe(
                ganamasSubscribed,
                finalActiveConsultants,
                textResolver.getCampaignFormatted(campaign)
            )
        }

    private fun createCampaignInformationSe(
        list: List<DigitalSaleBusinessPartner>,
        isGanMas: Boolean
    ): DigitalSaleCampaignInformation {
        return DigitalSaleCampaignInformation().apply {
            list.forEach {
                consultantAppList.add(
                    createDigitalSaleCampaignModelSe(
                        it.campaign,
                        it.orderSentApp.toString()
                    )
                )
                ganaMasList.add(getDigitalOfferByCountry(it, isGanMas))
                uniqueIpList.add(
                    createDigitalSaleCampaignModelSe(
                        it.campaign,
                        getUniquePercentageFormatted(it.uniqueIpPercentage)
                    )
                )
            }
        }
    }

    private fun getUniquePercentageFormatted(uniqueIpPercentage: Double): String {
        return textResolver.formatPercentageStringLabel(uniqueIpPercentage.toPercentageWithLowerThousands())
    }

    private fun createDigitalSaleCampaignModelSe(
        campaign: String,
        value: String,
        number: String = Constant.EMPTY_STRING
    ): DigitalSaleCampaignModel {
        return DigitalSaleCampaignModel(
            campaign = campaign,
            campaignCode = textResolver.getCampaignFormatted(campaign),
            saleCampaignChild = listOf(
                DigitalSaleCampaignChildModel(
                    colorRes = BaseR.color.black,
                    value = value,
                    number = number
                )
            )
        )
    }

    private fun getDigitalOfferByCountry(
        digitalSale: DigitalSaleBusinessPartner,
        isGanMas: Boolean
    ): DigitalSaleCampaignModel = with(digitalSale) {
        return if (isGanMas) {
            createGanaMasCampaignModel(this)
        } else {
            val buyers = ganamasSubscribedBuyers + ganamasNotSubscribedBuyers
            createDigitalSaleCampaignModelSe(campaign, buyers.toString())
        }
    }

    private fun createGanaMasCampaignModel(digitalSale: DigitalSaleBusinessPartner): DigitalSaleCampaignModel =
        with(digitalSale) {
            return DigitalSaleCampaignModel(
                campaign = campaign,
                campaignCode = textResolver.getCampaignFormatted(campaign),
                saleCampaignChild = listOf(
                    DigitalSaleCampaignChildModel(
                        colorRes = BaseR.color.positivo,
                        value = textResolver.getSubscribeBuyersLabel(),
                        number = ganamasSubscribedBuyers.toString()
                    ),
                    DigitalSaleCampaignChildModel(
                        colorRes = BaseR.color.black,
                        value = textResolver.getSubscribeNotBuyersLabel(),
                        number = ganamasSubscribedNotBuyers.toString()
                    ),
                    DigitalSaleCampaignChildModel(
                        colorRes = BaseR.color.black,
                        value = textResolver.getNotSubscribeBuyersLabel(),
                        number = ganamasNotSubscribedBuyers.toString()
                    ),
                    DigitalSaleCampaignChildModel(
                        colorRes = BaseR.color.black,
                        value = textResolver.getNotSubscribeNotBuyersLabel(),
                        number = ganamasNotSubscribedNotBuyers.toString()
                    )
                )
            )
        }

    private fun createGanaMasItem(
        digitalCampaignList: List<DigitalSaleCampaignModel>,
        subscription: String
    ): DigitalSaleModel {
        return DigitalSaleModel(
            digitalId = Constant.NUMBER_THREE,
            title = textResolver.getGanaMasName(),
            subtitle = subscription,
            image = R.drawable.ic_rdd_ofertas_digitales,
            type = DigitalViewTypeIdentifier.SINGLE_VERTICAL,
            itemList = listOf(
                DigitalSaleItemModel(
                    id = Constant.NUMBER_ONE,
                    campaignList = digitalCampaignList,
                    type = DigitalViewTypeIdentifier.SINGLE_VERTICAL
                )
            )
        )
    }

    private fun createDigitalOfferItem(digitalCampaignList: List<DigitalSaleCampaignModel>): DigitalSaleModel {
        return DigitalSaleModel(
            digitalId = Constant.NUMBER_THREE,
            title = textResolver.getDigitalOfferLabel(),
            subtitle = textResolver.getDigitalOfferDescription(),
            image = R.drawable.ic_rdd_ofertas_digitales,
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
}
