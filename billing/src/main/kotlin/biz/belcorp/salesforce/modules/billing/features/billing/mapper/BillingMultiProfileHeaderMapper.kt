package biz.belcorp.salesforce.modules.billing.features.billing.mapper

import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.domain.entities.billing.Billing
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.billing.features.billing.model.BillingHeaderMultiProfileModel
import biz.belcorp.salesforce.modules.billing.features.billing.view.BillingHeaderTextResolver

class BillingMultiProfileHeaderMapper(private val textResolver: BillingHeaderTextResolver) :
    SingleMapper<Billing, BillingHeaderMultiProfileModel>() {

    override fun map(value: Billing): BillingHeaderMultiProfileModel = with(value) {
        return BillingHeaderMultiProfileModel(
            titleSale = textResolver.getTitleSaleBilling(),
            salesDescription = getNetSaleDescription(this),
            titleOrders = textResolver.getTitleOrdersBilling(),
            ordersDescription = getOrdersDescription(this)
        )
    }

    private fun getNetSaleDescription(billing: Billing): String = with(billing) {
        return when {
            remainingNetSale.gtZero() ->
                textResolver.getSaleDescriptionMultiProfile(
                    fulfillmentNetSalesPercentage.toPercentageNumber().toString(),
                    getNetSaleAmount(currencySymbol, remainingNetSale),
                    isThirdPerson = isThirdPerson
                )
            remainingNetSale.ltZero() ->
                textResolver.getSaleDescriptionSuccessMultiProfile(
                    fulfillmentNetSalesPercentage.toPercentageNumber().toString(),
                    getNetSaleAmount(currencySymbol, currentNetSales),
                    isThirdPerson = isThirdPerson
                )
            else -> EMPTY_STRING
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
                textResolver.getOrdersSuccessDescriptionMultiProfile(
                    fulfillmentOrdersPercentage.toPercentageNumber().toString(),
                    currentOrders.formatWithThousands(),
                    quantity = currentOrders,
                    isThirdPerson = isThirdPerson
                )
            else -> EMPTY_STRING
        }
    }

    private fun getNetSaleAmount(currencySymbol: String, amount: Double) =
        getAmountWithCurrency(currencySymbol, amount.formatWithThousands())

    private fun getAmountWithCurrency(currencySymbol: String, amount: String) = textResolver
        .getAmountWithCurrency(currencySymbol, amount)
}
