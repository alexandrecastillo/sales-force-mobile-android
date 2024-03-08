package biz.belcorp.salesforce.modules.consultants.features.filters.baseunified.mappers

import biz.belcorp.salesforce.analytics.core.domain.entities.UserProperties.session
import biz.belcorp.salesforce.core.constants.FilterKey
import biz.belcorp.salesforce.core.constants.SourceType
import biz.belcorp.salesforce.core.domain.entities.consultants.ConsultantFilter
import biz.belcorp.salesforce.core.domain.entities.pais.Pais
import biz.belcorp.salesforce.core.utils.toHashMap
import biz.belcorp.salesforce.modules.consultants.core.domain.entities.unified.*

class FilterConsultantMapper {

    fun map(extraParams: ExtraFilterParams, filter: ConsultantFilter): Filter = with(filter) {
        val filterMap = filters.toHashMap()
        val filters = when (type) {
            SourceType.KPI_NEW_CYCLES -> mapNewCyclesFilter(filterMap)
            SourceType.KPI_SALE_ORDERS -> mapSaleOrdersFilter(filterMap)
            SourceType.KPI_COLLECTION -> mapCollectionFilter(filterMap)
            SourceType.KPI_CAPITALIZATION -> mapCapitalizationFilter(filterMap)
            SourceType.BILLING_ADVANCEMENT -> mapBillingAdvancementFilter(extraParams, filterMap)
            SourceType.DIGITAL_SUBSCRIBED,
            SourceType.DIGITAL_BUY,
            SourceType.DIGITAL_SHARE -> mapDigitalFilter(filterMap)
            else -> emptyList()
        }
        val specialFilter = mapSpecialFilter(extraParams)
        return Filter(uaKey, filters + specialFilter)
    }

    private fun mapNewCyclesFilter(filters: Map<String, String>): List<Filterable> =
        with(filters) {
            val filterable = NewCycleFilter(
                new5d5 = containsKey(FilterKey.KEY_5D5),
                new4d4 = containsKey(FilterKey.KEY_4D4),
                new3d3 = containsKey(FilterKey.KEY_3D3),
                new2d2 = containsKey(FilterKey.KEY_2D2),
                new1d1 = containsKey(FilterKey.KEY_1D1)
            )
            return listOf(filterable)
        }

    private fun mapCollectionFilter(filters: Map<String, String>): List<Filterable> =
        with(filters) {
            val filterable = OrdersFilter(
                withDebt = true,
                lowValueOrder = containsKey(FilterKey.KEY_LOW_VALUE),
                lowValueOrderPlus = (session?.pais?.codigoIso == Pais.PERU.codigoIso ||
                    session?.pais?.codigoIso == Pais.ECUADOR.codigoIso
                    ) && containsKey(FilterKey.KEY_LOW_VALUE_PLUS),
                highValueOrder = containsKey(FilterKey.KEY_HIGH_VALUE),
                highValueOrderPlus = containsKey(FilterKey.KEY_HIGH_VALUE_PLUS)
            )
            return listOf(filterable)
        }

    private fun mapSaleOrdersFilter(filters: Map<String, String>): List<Filterable> =
        with(filters) {
            val filterable = OrdersFilter(
                withDebt = false,
                lowValueOrder = containsKey(FilterKey.KEY_LOW_VALUE),
                lowValueOrderPlus = (session?.pais?.codigoIso == Pais.PERU.codigoIso ||
                    session?.pais?.codigoIso == Pais.ECUADOR.codigoIso
                    ) && containsKey(FilterKey.KEY_LOW_VALUE_PLUS),
                highValueOrder = containsKey(FilterKey.KEY_HIGH_VALUE),
                highValueOrderPlus = containsKey(FilterKey.KEY_HIGH_VALUE_PLUS)
            )
            return listOf(filterable)
        }

    private fun mapCapitalizationFilter(filters: Map<String, String>): List<Filterable> =
        with(filters) {
            val filterable = RetentionFilter(
                pegsWithDebt = containsKey(FilterKey.KEY_PEG_WITH_DEBT),
                pegsWithoutDebt = containsKey(FilterKey.KEY_PEG_WITHOUT_DEBT)
            )
            return listOf(filterable)
        }

    private fun mapBillingAdvancementFilter(
        extraParams: ExtraFilterParams,
        filters: Map<String, String>
    ): List<Filterable> =
        with(filters) {
            val billingFilter = mapBilling(this)
            val newCyclesFilter = mapNewCycle(this)
            val orderStatusFilter = mapOrderStatus(extraParams, this)
            val pegFilter = mapPeg(this)
            val stateFilter = mapState(this)
            val typeFilter = mapType(this)
            val multibrandFilter = mapMultiBrand(this)
            val multiCategoryFilter = mapMulticategory(this)
            val orderTypeFilter = mapOrderType(this)
            return billingFilter + newCyclesFilter + orderStatusFilter + pegFilter + stateFilter + typeFilter + multibrandFilter + multiCategoryFilter + orderTypeFilter
        }



    private fun mapBilling(filters: Map<String, String>): List<Filterable> =
        with(filters) {
            val filterable = BillingFilter(
                hasNotBilledOrders = containsKey(FilterKey.KEY_NO_BILLED_ORDERS),
                hasBilledOrders = containsKey(FilterKey.KEY_BILLED_ORDERS)
            )
            return listOf(filterable)
        }

    private fun mapNewCycle(filters: Map<String, String>): List<Filterable> {
        val hasNewCycleFilter = FilterKey.newCycleKeys().any { filters.containsKey(it) }
        return if (hasNewCycleFilter) mapNewCyclesFilter(filters) else emptyList()
    }

    private fun mapOrderStatus(
        extraParams: ExtraFilterParams,
        filters: Map<String, String>
    ): List<Filterable> =
        with(filters) {
            val filterParams = OrdersStatusFilter.Params(
                minimalOrderAmount = extraParams.minimalOrderAmount,
                tippingPoint = extraParams.tippingPoint
            )
            val filterable = OrdersStatusFilter(
                withDebt = containsKey(FilterKey.KEY_ORDERS_WITH_DEBT),
                nearToHighValueOrder = containsKey(FilterKey.KEY_ORDERS_NEAR_TP_HIGH_VALUE),
                notMinimumAmount = containsKey(FilterKey.KEY_ORDERS_MINIMAL_AMOUNT),
                params = filterParams
            )
            return listOf(filterable)
        }

    private fun mapPeg(filters: Map<String, String>): List<Filterable> =
        with(filters) {
            val filterable = PegFilter(
                isPeg = containsKey(FilterKey.KEY_PEGS),
                isPossiblePeg = containsKey(FilterKey.KEY_POSSIBLE_PEGS)
            )
            return listOf(filterable)
        }

    private fun mapState(filters: Map<String, String>): List<Filterable> =
        with(filters) {
            val filterable = StateFilter(
                isEntries = containsKey(FilterKey.KEY_ENTRIES),
                isReentries = containsKey(FilterKey.KEY_REENTRIES)
            )
            return listOf(filterable)
        }

    private fun mapType(filters: Map<String, String>): List<Filterable> =
        with(filters) {
            val filterable = TypeFilter(
                hasCashPayment = containsKey(FilterKey.KEY_HAS_CASH_PAYMENT),
                hasNotCashPayment = containsKey(FilterKey.KEY_HAS_NOT_CASH_PAYMENT)
            )
            return listOf(filterable)
        }

    private fun mapMultiBrand(filters: Map<String, String>): List<Filterable> =
        with(filters) {
            val filterable = MultibrandFilter(
                yesMultiBrand = containsKey(FilterKey.KEY_HAS_YES_MULTIBRAND),
                noMultiBrand = containsKey(FilterKey.KEY_HAS_NO_MULTIBRAND)
            )
            return listOf(filterable)
        }

    private fun mapOrderType(filters: Map<String, String>): List<Filterable> =
        with(filters){
            val filterable = OrderTypeFilter(
                lowValue = containsKey(FilterKey.KEY_LOW_VALUE),
                /* lowPlusValue = containsKey(FilterKey.KEY_LOW_VALUE_PLUS), */
                //4/7/2023:  Filter: BAJO_PLUS --> PBVP  not applied by requirement of missing data
                highValue = containsKey(FilterKey.KEY_HIGH_VALUE),
                highPlusValue = containsKey(FilterKey.KEY_HIGH_VALUE_PLUS),
            )

            return listOf(filterable)
        }

    private fun mapMulticategory(filters: Map<String, String>): List<Filterable> =
        with(filters){
            val filterable = MulticategoryFilter(
                yesMulticategory = containsKey(FilterKey.KEY_HAS_YES_MULTICATEGORY),
                noMulticategory = containsKey(FilterKey.KEY_HAS_NO_MULTICATEGORY)
            )
            return listOf(filterable)
        }


    private fun mapSpecialFilter(extraParams: ExtraFilterParams): List<Filterable> {
        val filterable = SpecialFilter(
            skipBrilliants = extraParams.skipBrilliants
        )
        return listOf(filterable)
    }

    private fun mapDigitalFilter(filters: Map<String, String>): List<Filterable> =
        with(filters) {
            val filterable = DigitalFilter(
                isSubscribed = checkSubscribedKey(filters),
                isNotSubscribed = containsKey(FilterKey.KEY_DIGITAL_NO_SUBSCRIBED),
                buy = containsKey(FilterKey.KEY_DIGITAL_BUY),
                noBuy = containsKey(FilterKey.KEY_DIGITAL_NO_BUY),
                share = containsKey(FilterKey.KEY_DIGITAL_SHARE),
                noShare = containsKey(FilterKey.KEY_DIGITAL_NO_SHARE)
            )
            return listOf(filterable)
        }

    private fun checkSubscribedKey(filters: Map<String, String>) =
        with(filters) {
            containsKey(FilterKey.KEY_DIGITAL_SUBSCRIBED) ||
                containsKey(FilterKey.KEY_DIGITAL_BUY) ||
                containsKey(FilterKey.KEY_DIGITAL_NO_BUY) ||
                containsKey(FilterKey.KEY_DIGITAL_SHARE) ||
                containsKey(FilterKey.KEY_DIGITAL_NO_SHARE)
        }

}
