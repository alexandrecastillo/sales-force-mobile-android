package biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.view.detail.se.mapper

import androidx.core.content.ContextCompat
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.kpis.R
import biz.belcorp.salesforce.modules.kpis.core.data.repository.saleorders.mappers.LOW_VALUE_PLUS
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.saleorders.SaleOrderContainer
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.saleorders.SaleOrdersOrderRange
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.SaleOrdersTextResolver
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.model.*
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.model.DefaultGridModel
import biz.belcorp.salesforce.modules.kpis.utils.DateUtils
import biz.belcorp.salesforce.components.R as BaseR

class SaleOrderSeMapper(private val textResolver: SaleOrdersTextResolver) {

    fun map(container: SaleOrderContainer): SaleOrdersDetailModel = with(container) {
        return SaleOrdersDetailModel(
            title = createTitle(
                saleOrdersIndicator.campaign.takeLastTwo(),
                isBilling,
                isThirdPerson
            ),
            items = createModels(this),
            model = saleOrdersIndicator
        )
    }

    private fun createTitle(campaign: String, isBilling: Boolean, isThirdPerson: Boolean): String {
        return if (isBilling) textResolver.getTitleBilling(campaign, isThirdPerson)
        else textResolver.getTitleSale(campaign, isThirdPerson)
    }

    private fun createModels(container: SaleOrderContainer): List<ContentModel> = with(container) {
        return when (isBilling) {
            true -> parseBilling(this)
            else -> parseSales(this)
        }
    }

    private fun parseSales(container: SaleOrderContainer): List<ContentModel> =
        with(container) {
            listOf(
                ContentModel(type = ContentType.SIMPLE_CARD, items = itemSaleOrders(this)),
                ContentModel(type = ContentType.SIMPLE_CARD, items = itemActivitySale(this)),
                ContentModel(type = ContentType.SIMPLE_CARD, items = itemsRetention(this))
            )
        }

    private fun parseBilling(container: SaleOrderContainer): List<ContentModel> = with(container) {
        listOf(
            ContentModel(type = ContentType.SIMPLE_CARD, items = itemSaleOrders(this)),
            ContentModel(type = ContentType.SIMPLE_CARD, items = itemActivityBilling(this))
        )
    }

    private fun itemSaleOrders(container: SaleOrderContainer): List<ContentBaseModel> =
        with(container) {
            listOf(
                createFirstElement(this),
                Separator(),
                createCompactElement(this),
                Separator(),
                getOrderAverageAmount(currencySymbol, saleOrdersIndicator.averageAmount)
            )
        }

    private fun itemActivitySale(container: SaleOrderContainer): List<ContentBaseModel> =
        with(container) {
            listOf(
                Single(
                    textResolver.getActivityTitle(),
                    getActivityPercentage(saleOrdersIndicator.activityPercentage),
                    getColorMagenta(),
                    getColorMagenta()
                ),
                Single(
                    textResolver.getPegsTitle(),
                    saleOrdersIndicator.activityPegs.formatWithThousands()
                ),
                Single(
                    textResolver.getFinalActivesTitle(),
                    saleOrdersIndicator.activesFinals.formatWithThousands()
                )
            )
        }

    private fun itemActivityBilling(container: SaleOrderContainer): List<ContentBaseModel> =
        with(container) {
            listOf(
                Single(
                    textResolver.getActivityTitle(),
                    getActivityPercentage(saleOrdersIndicator.activityPercentage),
                    getColorMagenta(),
                    getColorMagenta()
                )
            )
        }

    private fun itemsRetention(container: SaleOrderContainer): List<ContentBaseModel> =
        with(container) {
            listOf(
                Single(
                    textResolver.getActiveRetentionTitle(),
                    getActiveRetention(saleOrdersIndicator.activesRetentionPercentage),
                    getColorMagenta(),
                    getColorMagenta()
                ),
                Single(
                    textResolver.getFinalActiveRetentionTitle(DateUtils.getLastYear().toString()),
                    saleOrdersIndicator.activesFinalsLastYear.formatWithThousands()
                )
            )
        }

    private fun createFirstElement(container: SaleOrderContainer): Single = with(container) {
        if (isBright) {
            getOrders(saleOrdersIndicator.orders)
        } else {
            getCatalogSale(currencySymbol, saleOrdersIndicator.catalogSale)
        }
    }

    private fun getCatalogSale(currencySymbol: String, amount: Double): Single {
        return Single(
            textResolver.getCatalogSaleTitle(),
            getCatalogSaleAmount(currencySymbol, amount)
        )
    }

    private fun getOrders(amount: Int) =
        Single(textResolver.getOrdersTitle(), getBilledOrders(amount))

    private fun createCompactElement(container: SaleOrderContainer): Compact =
        with(container) {
            if (isBright) {
                getCompactCatalogSale(this)
            } else {
                getCompactOrders(this)
            }
        }

    private fun getCompactOrders(saleOrderContainer: SaleOrderContainer): Compact =
        with(saleOrderContainer) {
            return Compact(
                textResolver.getOrdersTitle(),
                getBilledOrders(saleOrdersIndicator.orders),
                getOrdersGoal(saleOrdersIndicator.ordersGoal),
                getComplianceProgress(saleOrdersIndicator.fulfillmentOrderPercentage),
                BaseR.drawable.ic_order_box,
                createRanges(textResolver.getOrderDetailTitle(), saleOrdersIndicator.ordersRange)
            )
        }

    private fun getCompactCatalogSale(saleOrderContainer: SaleOrderContainer): Compact =
        with(saleOrderContainer) {
            return Compact(
                textResolver.getCatalogSaleTitle(),
                getCatalogSaleAmount(currencySymbol, saleOrdersIndicator.catalogSale),
                getCatalogSaleGoal(currencySymbol, saleOrdersIndicator.catalogSaleGoal),
                getComplianceProgress(saleOrdersIndicator.fulfillmentCatalogSalesPercentage),
                BaseR.drawable.ic_profit,
                createRanges(textResolver.getOrderDetailTitle(), saleOrdersIndicator.ordersRange)
            )
        }

    private fun getCatalogSaleAmount(currencySymbol: String, amount: Double) =
        textResolver.formatCurrencyAmount(currencySymbol, amount.formatWithLowerThousands())

    private fun getCatalogSaleGoal(currencySymbol: String, amount: Double) =
        textResolver.formatSaleDescription(currencySymbol, amount.formatWithNoDecimalThousands())

    private fun getBilledOrders(orders: Int) = textResolver.formatDescriptionOrdersBilled(orders)

    private fun getOrderAverageAmount(currency: String, amount: Double): Single {
        return Single(
            textResolver.getOrdersAverageTitle(),
            textResolver.formatCurrencyAmount(currency, amount.formatWithLowerThousands())
        )
    }

    private fun getOrdersGoal(ordersGoal: Int) = textResolver.formatQuantityOrders(ordersGoal)

    private fun getComplianceProgress(value: Double) = value.toPercentageNumber()

    private fun getActivityPercentage(amount: Double) =
        textResolver.formatPercentageStringLabel(amount.toPercentageFormat())

    private fun getActiveRetention(retentionPercentage: Double) =
        textResolver.formatPercentageStringLabel(retentionPercentage.toPercentageFormat())

    private fun getColorMagenta() =
        ContextCompat.getColor(textResolver.context, R.color.colorMagenta)

    private fun createRanges(title: String, ranges: List<SaleOrdersOrderRange>): Range {
        val items = ranges.filter{ LOW_VALUE_PLUS != it.range }.sortedBy { it.position }.map {
            DefaultGridModel(
                label = it.range.removeFirstWord(),
                value = it.amount.formatWithThousands()
            )
        }
        return Range(title, items)
    }
}
