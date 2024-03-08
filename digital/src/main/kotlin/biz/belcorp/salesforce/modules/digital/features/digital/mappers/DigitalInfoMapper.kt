package biz.belcorp.salesforce.modules.digital.features.digital.mappers

import biz.belcorp.salesforce.components.R
import biz.belcorp.salesforce.core.domain.entities.configuration.OnlineStoreType
import biz.belcorp.salesforce.core.utils.toHundredPercentage
import biz.belcorp.salesforce.modules.digital.core.domain.entities.DigitalInfo
import biz.belcorp.salesforce.modules.digital.features.constants.DigitalFilterType.Companion.BUY
import biz.belcorp.salesforce.modules.digital.features.constants.DigitalFilterType.Companion.SHARE
import biz.belcorp.salesforce.modules.digital.features.constants.DigitalFilterType.Companion.SUBSCRIBED
import biz.belcorp.salesforce.modules.digital.features.digital.model.DigitalHeaderItemModel
import biz.belcorp.salesforce.modules.digital.features.utils.DigitalTextResolver
import kotlin.math.roundToInt

class DigitalInfoMapper(
    private val textResolver: DigitalTextResolver
) {

    fun map(
        storeTitle: String,
        @OnlineStoreType storeType: String,
        info: DigitalInfo
    ): List<DigitalHeaderItemModel> {

        val iconResId = getIcon(storeType)

        val subscribedPercentageValue = info.subscribedActivesRatio.toHundredPercentage().roundToInt()
        val sharePercentageValue = info.shareSubscribedRatio.toHundredPercentage().roundToInt()
        val buyPercentageValue = info.buySubscribedRatio.toHundredPercentage().roundToInt()

        return listOf(
            DigitalHeaderItemModel(
                title = textResolver.getHeaderTitleText(storeTitle, SUBSCRIBED),
                iconResId = iconResId,
                percentageNumber = info.subscribedActivesRatio,
                percentageValue = textResolver.getHeaderPercentageText(subscribedPercentageValue),
                percentageDescription = textResolver.getHeaderDescription1Text(storeTitle, SUBSCRIBED),
                progressValue = textResolver.getHeaderProgressText(info.subscribed, info.actives),
                progressDescription = textResolver.getHeaderDescription2Text(storeTitle, info.campaign, SUBSCRIBED)
            ),
            DigitalHeaderItemModel(
                title = textResolver.getHeaderTitleText(storeTitle, SHARE),
                iconResId = iconResId,
                percentageNumber = info.shareSubscribedRatio,
                percentageValue = textResolver.getHeaderPercentageText(sharePercentageValue),
                percentageDescription = textResolver.getHeaderDescription1Text(storeTitle, SHARE),
                progressValue = textResolver.getHeaderProgressText(info.share, info.subscribed),
                progressDescription = textResolver.getHeaderDescription2Text(storeTitle, info.campaign, SHARE)
            ),
            DigitalHeaderItemModel(
                title = textResolver.getHeaderTitleText(storeTitle, BUY),
                iconResId = iconResId,
                percentageNumber = info.buySubscribedRatio,
                percentageValue = textResolver.getHeaderPercentageText(buyPercentageValue),
                percentageDescription = textResolver.getHeaderDescription1Text(storeTitle, BUY),
                progressValue = textResolver.getHeaderProgressText(info.buy, info.subscribed),
                progressDescription = textResolver.getHeaderDescription2Text(storeTitle, info.campaign, BUY)
            )
        )
    }

    private fun getIcon(storeType: String) : Int {
        return when(storeType) {
            OnlineStoreType.DIGITAL_CATALOG -> R.drawable.ic_digital_catalog
            else -> R.drawable.ic_online_store
        }
    }

}
