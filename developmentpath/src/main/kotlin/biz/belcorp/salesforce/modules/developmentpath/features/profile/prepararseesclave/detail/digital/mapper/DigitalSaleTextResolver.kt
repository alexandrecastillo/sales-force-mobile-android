package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.digital.mapper

import android.content.Context
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.domain.entities.configuration.MainBrandType
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.features.utils.CoreTextResolver
import biz.belcorp.salesforce.core.utils.isCO
import biz.belcorp.salesforce.core.utils.isSE
import biz.belcorp.salesforce.core.utils.takeLastTwo
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant.EMPTY_STRING

class DigitalSaleTextResolver(context: Context) : CoreTextResolver(context) {

    fun getConsultantAppName(@MainBrandType appType: String): String {
        val resId = if (appType == MainBrandType.LBEL) {
            R.string.title_digital_sale_lbel_name
        } else {
            R.string.title_digital_sale_esika_name
        }
        return context.getString(resId)
    }

    fun getConsultantAppDescription(role: Rol): String {
        return when {
            role.isCO() -> context.getString(R.string.title_digital_sale_esika_description)
            role.isSE() -> context.getString(R.string.title_digital_sale_esika_description_se)
            else -> Constant.EMPTY_STRING
        }
    }

    private fun getCampaignFormattedWithYear(campaignCode: String): String {
        return if (campaignCode.isNotEmpty()) {
            context.getString(
                R.string.label_digital_sale_campaign_year,
                campaignCode.takeLastTwo(),
                campaignCode.take(4)
            )
        } else Constant.EMPTY_STRING
    }

    fun getAccompanimentName(): String {
        return context.getString(R.string.title_digital_sale_accompaniment_name)
    }

    fun getAccompanimentDescription(): String {
        return context.getString(R.string.title_digital_sale_accompaniment_description)
    }

    fun getOnlineDescriptionShare(): String {
        return context.getString(R.string.title_digital_sale_online_store_description_share)
    }

    fun getOnlineDescriptionBuy(text: String): String {
        val format = context.getString(R.string.title_digital_sale_online_store_description_buy)
        return stringFormat(format, text)
    }

    fun getOnlineDescriptionSale(): String {
        return context.getString(R.string.title_digital_sale_online_store_description_sale)
    }

    fun getGanaMasName(): String {
        return context.getString(R.string.title_digital_sale_ganamas_name)
    }

    fun getGanaMasDescription(): String {
        return context.getString(R.string.title_digital_sale_ganamas_description)
    }

    fun getGanaMasDescriptionSe(vararg args: Any?): String {
        val format = context.getString(R.string.title_digital_sale_ganamas_description_se)
        return stringFormat(format, *args)
    }

    fun getUniqueIpName(): String {
        return context.getString(R.string.title_digital_sale_unique_ip_name)
    }

    fun getUniqueIpDescription(role: Rol): String {
        val resId =
            if (role.isCO()) R.string.title_digital_sale_unique_ip_description else R.string.title_digital_sale_unique_ip_description_se
        return context.getString(resId)
    }

    fun getDigitalCatalogName(): String {
        return context.getString(R.string.title_digital_sale_digital_catalog_name)
    }

    fun getDigitalCatalogDescription(): String {
        return context.getString(R.string.title_digital_sale_digital_catalog_description)
    }

    fun getDigitalCatalogItemOne(): String {
        return context.getString(R.string.title_digital_sale_digital_one)
    }

    fun getDigitalCatalogItemTwo(): String {
        return context.getString(R.string.title_digital_sale_digital_two)
    }

    fun getDigitalCatalogItemThree(): String {
        return context.getString(R.string.title_digital_sale_digital_three)
    }

    fun getMakeupAppName(): String {
        return context.getString(R.string.title_digital_sale_makeup_app_name)
    }

    fun getMakeupAppDescription(): String {
        return context.getString(R.string.title_digital_sale_makeup_app_description)
    }

    fun getSubscribedLabel(value: Boolean, campaignSubscription: String): String {
        return if (value) getSubscribeLabel(getCampaignFormattedWithYear(campaignSubscription)) else getUnSubscribeLabel()
    }

    fun getRegisteredLabel(value: Boolean, campaignSubscription: String): String {
        return if (value) EMPTY_STRING else getUnRegisteredLabel()
    }

    fun getSubscribeBuyersLabel(): String {
        return context.getString(R.string.label_digital_sale_subscribe_buyers)
    }


    fun getSubscribeNotBuyersLabel(): String {
        return context.getString(R.string.label_digital_sale_subscribe_not_buyers)
    }


    fun getNotSubscribeBuyersLabel(): String {
        return context.getString(R.string.label_digital_sale_not_subscribe_buyers)
    }


    fun getNotSubscribeNotBuyersLabel(): String {
        return context.getString(R.string.label_digital_sale_not_subscribe_not_buyers)
    }

    private fun getSubscribeLabel(campaignSubscription: String): String {
        return context.getString(R.string.label_digital_sale_subscribed, campaignSubscription)
    }

    private fun getUnSubscribeLabel(): String {
        return context.getString(R.string.label_digital_sale_unsubscribed)
    }

    private fun getRegisteredLabel(campaignSubscription: String): String {
        return context.getString(R.string.label_digital_sale_registered, campaignSubscription)
    }

    private fun getUnRegisteredLabel(): String {
        return context.getString(R.string.label_digital_sale_unregistered)
    }

    fun formatPercentageStringLabel(vararg args: Any?): String {
        val format = context.getString(R.string.percentage_string_format)
        return stringFormat(format, *args)
    }

    fun getActivesLabel(vararg args: Any?): String {
        val format = context.getString(R.string.label_digital_sale_actives)
        return stringFormat(format, *args)
    }

    fun getDigitalOfferLabel(): String {
        return context.getString(R.string.title_digital_sale_digital_offer_name_se)
    }

    fun getDigitalOfferDescription(): String {
        return context.getString(R.string.title_digital_sale_digital_offer_description_se)
    }
}
