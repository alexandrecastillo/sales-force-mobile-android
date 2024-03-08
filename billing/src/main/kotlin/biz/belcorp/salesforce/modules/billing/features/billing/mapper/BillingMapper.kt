package biz.belcorp.salesforce.modules.billing.features.billing.mapper

import biz.belcorp.salesforce.core.domain.entities.billing.Billing
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.billing.features.billing.model.BillingHeaderModel
import biz.belcorp.salesforce.modules.billing.features.billing.view.BillingHeaderTextResolver

class BillingMapper(
    private val textResolver: BillingHeaderTextResolver
) : SingleMapper<Billing, BillingHeaderModel>() {

    override fun map(value: Billing): BillingHeaderModel = with(value) {
        val salesDescription = if (isBright) getSalesBrightDescription(this)
        else getSalesDescription(this)

        val ordersDescription = if (isBright) getOrdersBrightDescription(this)
        else getOrdersDescription(this)

        return BillingHeaderModel(
            salesDescription = salesDescription,
            ordersDescription = ordersDescription
        )
    }

    private fun getSalesDescription(billing: Billing): String = with(billing) {
        textResolver.getSaleDescription(
            getCatalogSaleAmount(currencySymbol, currentCatalogSales), isThirdPerson = isThirdPerson
        )
    }

    private fun getSalesBrightDescription(billing: Billing): String = with(billing) {
        return when {
            remainingCatalogSale.gtZero() ->
                textResolver.getSaleBrightDescription(
                    fulfillmentCatalogSalesPercentage.toPercentageNumber().toString(),
                    getCatalogSaleAmount(currencySymbol, remainingCatalogSale),
                    isThirdPerson = isThirdPerson
                )
            remainingCatalogSale.ltZero() ->
                textResolver.getSaleSuccessBrightDescription(
                    fulfillmentCatalogSalesPercentage.toPercentageNumber().toString(),
                    getCatalogSaleAmount(currencySymbol, currentCatalogSales),
                    isThirdPerson = isThirdPerson
                )
            else -> textResolver.getSaleReachedBrightDescription(
                fulfillmentOrdersPercentage.toPercentageNumber().toString(),
                getCatalogSaleAmount(currencySymbol, currentCatalogSales),
                isThirdPerson = isThirdPerson
            )
        }
    }

    private fun getOrdersDescription(billing: Billing): String = with(billing) {
        return when {
            remainingOrders.isPositive() ->
                textResolver.getOrdersDescription(
                    fulfillmentOrdersPercentage.toPercentageNumber().toString(),
                    remainingOrders.formatWithThousands(),
                    quantity = remainingOrders,
                    isThirdPerson = isThirdPerson
                )
            remainingOrders.isNegative() ->
                textResolver.getOrdersSuccessDescription(
                    fulfillmentOrdersPercentage.toPercentageNumber().toString(),
                    currentOrders.formatWithThousands(),
                    quantity = currentOrders,
                    isThirdPerson = isThirdPerson
                )
            else -> textResolver.getOrdersReachedDescription(
                fulfillmentOrdersPercentage.toPercentageNumber().toString(),
                currentOrders.formatWithThousands(),
                quantity = currentOrders,
                isThirdPerson = isThirdPerson
            )
        }
    }

    private fun getOrdersBrightDescription(billing: Billing): String = with(billing) {
        textResolver.getOrdersBrightDescription(
            currentOrders.formatWithThousands(),
            quantity = currentOrders,
            isThirdPerson = isThirdPerson
        )
    }

    private fun getCatalogSaleAmount(currencySymbol: String, amount: Double) =
        getAmountWithCurrency(currencySymbol, amount.formatWithLowerThousands())

    private fun getAmountWithCurrency(currencySymbol: String, amount: String) = textResolver
        .getAmountWithCurrency(currencySymbol, amount)
}