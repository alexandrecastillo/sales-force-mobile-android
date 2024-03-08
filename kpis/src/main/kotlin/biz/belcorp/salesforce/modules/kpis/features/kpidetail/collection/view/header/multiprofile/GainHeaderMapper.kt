package biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.header.multiprofile

import biz.belcorp.salesforce.core.utils.formatWithNoDecimalThousands
import biz.belcorp.salesforce.core.utils.parseToDayMonthString
import biz.belcorp.salesforce.core.utils.parseToHourMinAmPmString
import biz.belcorp.salesforce.core.utils.toPercentageFormat
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.collection.CollectionContainer
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.collection.CollectionIndicator
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.CollectionTextResolver
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.common.CollectionMapper

class GainHeaderMapper(private val textResolver: CollectionTextResolver) :
    CollectionMapper(textResolver) {

    fun map(collectionContainer: CollectionContainer): GainHeaderModel = with(collectionContainer) {

        val previousCollection = collectionList.previousData
            ?: CollectionIndicator(campaign = collectionList.previousCampaign)
        val recoveryAdvanceTitle =
            textResolver.getRecoveryAdvanceTitle( campaign)
        val chargedOrderTitle = textResolver.getChargedOrderTitle()
        val chargedOrderList = createChargedOrderList(previousCollection)

        val recoveryTitle = textResolver.getRecoveryTitle()
        val recoveryFormated = previousCollection.percentage.toPercentageFormat()
        val recoveryValue = textResolver.formatPercentage(recoveryFormated)

        val invoicedTitle = textResolver.getInvoicedTitle(campaign)
        val invoicedFormated = previousCollection.invoicedSale.formatWithNoDecimalThousands()
        val invoicedValue = textResolver.formatCurrency(currencySymbol, invoicedFormated)

        val collectedTitle = textResolver.getCollectedTitle(campaign)
        val collectedFormated = previousCollection.amountCollected.formatWithNoDecimalThousands()
        val collectedValue = textResolver.formatCurrency(currencySymbol, collectedFormated)

        val syncDateFormatted = textResolver.formatSyncDateLabel(
            syncDate.parseToDayMonthString(),
            syncDate.parseToHourMinAmPmString()
        )
        return GainHeaderModel(
            recoveryAdvanceTitle = recoveryAdvanceTitle,
            chargedOrderTitle = chargedOrderTitle,
            chargedOrderList = chargedOrderList,
            recoveryTitle = recoveryTitle,
            recoveryValue = recoveryValue,
            invoicedTitle = invoicedTitle,
            invoicedValue = invoicedValue,
            collectedTitle = collectedTitle,
            collectedValue = collectedValue,
            syncDate = syncDateFormatted
        )
    }

}
