package biz.belcorp.salesforce.modules.kpis.utils

import android.app.Activity
import biz.belcorp.salesforce.analytics.core.domain.entities.*
import biz.belcorp.salesforce.analytics.features.BelcorpAnalytics
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.constants.KpiType
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.view.detail.multiprofile.sales.CapitalizationKpiDetailOnSalesFragment
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.view.detail.se.sales.CapitalizationKpiDetailSeOnSalesFragment
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.detail.multiprofile.GainDetailFragment
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.view.detail.multiprofile.NewCycleDetailFragment
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.model.SaleOrderGridType
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.view.detail.multiprofile.SaleOrdersDetailFragment
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.filtergrid.types.CapitalizationGridType
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.filtergrid.types.NewCycleGridType

object AnalyticUtils {

    fun trackKPI(@KpiType kpiType: Int, activity: Activity) =
        BelcorpAnalytics.track(getScreen(kpiType), activity)

    fun screenKPI(@KpiType kpiType: Int) =
        BelcorpAnalytics.log(getScreen(kpiType))

    fun seleccionaTuZona(@KpiType kpiType: Int, zona: String) {
        val category = getScreen(kpiType)
        BelcorpAnalytics.log(
            Event(
                logName = EventTag.SELECCIONA_TU_ZONA,
                category = category,
                action = Action.SELECCIONA_TU_ZONA,
                label = zona,
                screenName = category
            )
        )
    }

    fun cardAvanceCampania(kpi: String) {
        BelcorpAnalytics.log(
            Event(
                logName = EventTag.AVANCE_CAMPANA,
                category = Category.HOME,
                action = Action.CAMPAIGN_ADVANCE,
                label = kpi,
                screenName = ScreenTag.HOME
            )
        )
    }

    fun tabSelection(kpiTag: String, tab: String) {

        when (kpiTag) {
            NewCycleDetailFragment.TAG ->
                sendTabsSelection(Category.NEW_CYCLES, tab, ScreenTag.NEW_CYCLES)
            GainDetailFragment.TAG ->
                sendTabsSelection(Category.COLLECTION, tab, ScreenTag.COLLECTION)
            SaleOrdersDetailFragment.TAG ->
                sendTabsSelection(Category.SALE_ORDERS, tab, ScreenTag.SALES)
            CapitalizationKpiDetailOnSalesFragment.TAG,
            CapitalizationKpiDetailSeOnSalesFragment.TAG ->
                sendTabsSelection(Category.CAPITALIZATION, tab, ScreenTag.CAPITALIZATION)
        }

    }

    fun sectionAdvance(filter: String) {

        return when (filter) {
            NewCycleGridType.LOW_VALUE ->
                send(
                    Category.NEW_CYCLES,
                    Label.NEW_CYCLES_NUEVAS,
                    ScreenTag.NEW_CYCLES
                )
            NewCycleGridType.HIGH_VALUE ->
                send(
                    Category.NEW_CYCLES,
                    Label.NEW_CYCLES_NUEVAS_ALTO_VALOR,
                    ScreenTag.NEW_CYCLES
                )
            CapitalizationGridType.PEGS ->
                send(
                    Category.CAPITALIZATION,
                    Label.CAPITALIZATION_PEGS,
                    ScreenTag.CAPITALIZATION
                )
            CapitalizationGridType.CAPITALIZATION ->
                send(
                    Category.CAPITALIZATION,
                    Label.CAPITALIZATION_CAPITALIZATION,
                    ScreenTag.CAPITALIZATION
                )
            SaleOrderGridType.SALES ->
                send(
                    Category.SALE_ORDERS,
                    Label.SALES_SALES,
                    ScreenTag.SALES
                )
            SaleOrderGridType.ORDERS ->
                send(
                    Category.SALE_ORDERS,
                    Label.SALES_ORDERS,
                    ScreenTag.SALES
                )
            SaleOrderGridType.ACTIVITY ->
                send(
                    Category.SALE_ORDERS,
                    Label.SALES_ACTIVITY,
                    ScreenTag.SALES
                )
            SaleOrderGridType.ACTIVES_RETENTION ->
                send(
                    Category.SALE_ORDERS,
                    Label.SALES_ACTIVES_RETENTION,
                    ScreenTag.SALES
                )
            else -> Unit
        }

    }

    fun avanceFacturacion(@KpiType kpyType: Int) {

        val category = getCategory(kpyType)

        BelcorpAnalytics.log(
            Event(
                logName = EventTag.AVANCE_FACTURACION,
                category = category,
                action = Action.YOUR_DETAIL_BILLING,
                label = Label.BILLING,
                screenName = ScreenTag.BILLING
            )
        )
    }

    private fun getCategory(@KpiType kpiType: Int): String = with(kpiType) {
        return when (kpiType) {
            KpiType.COLLECTION -> Category.COLLECTION
            KpiType.NEW_CYCLES -> Category.NEW_CYCLES
            KpiType.SALE_ORDERS -> Category.SALE_ORDERS
            KpiType.CAPITALIZATION -> Category.CAPITALIZATION
            else -> Constant.EMPTY_STRING
        }
    }

    private fun getScreen(@KpiType kpiType: Int): String = with(kpiType) {
        return when (kpiType) {
            KpiType.COLLECTION -> ScreenTag.COLLECTION
            KpiType.NEW_CYCLES -> ScreenTag.NEW_CYCLES
            KpiType.SALE_ORDERS -> ScreenTag.SALES
            KpiType.CAPITALIZATION -> ScreenTag.CAPITALIZATION
            else -> Constant.EMPTY_STRING
        }
    }

    private fun send(category: String, label: String, screen: String) {
        BelcorpAnalytics.log(
            Event(
                logName = EventTag.SECTION_ADVANCE,
                category = category,
                action = Action.SECTION_ADVANCE,
                label = label,
                screenName = screen
            )
        )
    }

    private fun sendTabsSelection(category: String, label: String, screen: String) {
        BelcorpAnalytics.log(
            Event(
                logName = EventTag.SECTION_DETAIL,
                category = category,
                action = Action.SELECCION_TABS,
                label = label,
                screenName = screen
            )
        )
    }


}
