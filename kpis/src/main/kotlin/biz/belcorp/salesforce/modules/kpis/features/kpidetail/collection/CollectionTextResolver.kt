package biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection

import android.content.Context
import biz.belcorp.salesforce.core.base.BaseTextResolver
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.isDV
import biz.belcorp.salesforce.core.utils.isGR
import biz.belcorp.salesforce.core.utils.isGZ
import biz.belcorp.salesforce.modules.kpis.R
import java.util.*
import biz.belcorp.salesforce.components.R as ComponentR

class CollectionTextResolver(val context: Context) : BaseTextResolver() {

    fun formatHeader() = context.getString(R.string.collection_header)

    fun formatHeaderMultiProfile() = context.getString(R.string.collection_header_multi_profile)

    fun formatTitle(vararg args: Any?, isThirdPerson: Boolean): String {
        val resource = if (!isThirdPerson) R.string.collection_campaign_title
        else R.string.collection_campaign_title_third
        return stringFormat(context.getString(resource), *args)
    }

    fun formatDescriptionRecovery(vararg args: Any?): String {
        val format = context.getString(R.string.collection_description_recovery)
        return stringFormat(format, *args)
    }

    fun formatDescriptionChargedOrders(vararg args: Any?): String {
        val format = context.getString(R.string.collection_description_charged_orders)
        return stringFormat(format, *args)
    }


    fun formatTotalGainSEEstablecida(campaignNumber: String) = context.getString(R.string.text_total_gain_se_establecida, campaignNumber)

    fun formatTotalGainSENueva(campaignNumber: String) = context.getString(R.string.text_total_gain_se_nueva, campaignNumber)

    fun getGainByOrdersTitle() = context.getString(R.string.gain_by_orders)

    fun getGainByCompetitionTitle() = context.getString(R.string.gain_by_competition)

    fun getHighValuePlusOrdersText() = context.getString(R.string.high_values_plus_orders)

    fun getHighValueOrdersText() = context.getString(R.string.high_values_orders)

    fun getLowValueOrdersText() = context.getString(R.string.low_values_orders)

    fun getCapitalizationTitle() = context.getString(R.string.capitalization)

    fun getRetentionTitle() = context.getString(R.string.retention_6d6)

    fun formatNonDataAvailableText(role: Rol) = when {
        role.isDV() -> {
            val region = context.getString(R.string.region_label)
            context.getString(ComponentR.string.text_no_data_available_on_grid, region)
        }
        role.isGR() -> {
            val zone = context.getString(R.string.zone_label)
            context.getString(ComponentR.string.text_no_data_available_on_grid, zone)
        }
        role.isGZ() -> {
            val section = context.getString(R.string.section_label)
            context.getString(ComponentR.string.text_no_data_available_on_grid, section)
        }
        else -> EMPTY_STRING
    }

    fun obtainGridTitleDetail(role: Rol): String = with(role) {
        return when {
            isDV() -> context.getString(R.string.title_detail_by_region)
            isGR() -> context.getString(R.string.title_detail_by_zone)
            isGZ() -> context.getString(R.string.title_detail_by_section)
            else -> EMPTY_STRING
        }
    }

    fun getLowerUaRolText(role: Rol): String = with(role) {
        return when {
            isDV() -> context.getString(R.string.column_header_dv).toUpperCase(Locale.getDefault())
            isGR() -> context.getString(R.string.column_header_gr).toUpperCase(Locale.getDefault())
            isGZ() -> context.getString(R.string.column_header_gz).toUpperCase(Locale.getDefault())
            else -> EMPTY_STRING
        }
    }

    fun formatSyncDateLabel(vararg args: Any?): String {
        val format = context.getString(R.string.date_update)
        return stringFormat(format, *args)
    }

    fun getRecoveryTitle(): String {
        return context.getString(R.string.title_recovery)
    }


    fun getSociaLevelTitle(): String {
        return context.getString(R.string.title_socia_level)
    }

    fun getSociaCampaignResultTitle(): String {
        return context.getString(R.string.title_socia_campaign_result)
    }


    fun getInvoicedTitle(vararg args: Any?): String {
        val format = context.getString(R.string.title_invoiced)
        return stringFormat(format, *args)
    }

    fun getCollectedTitle(vararg args: Any?): String {
        val format = context.getString(R.string.title_recovered)
        return stringFormat(format, *args)
    }

    fun getTooltip(): String {
        return context.getString(R.string.info_total_gain_projected)
    }

    fun formatCurrency(vararg args: Any?): String {
        val format = context.getString(R.string.number_with_currency)
        return stringFormat(format, *args)
    }

    fun getProfitAdvanceTitle(): String {
        return context.getString(R.string.title_advance)
    }

    fun getRecoveryAdvanceTitle( vararg args: Any?): String {
        val format = context.getString(R.string.collection_recovery_advance_title)
        return stringFormat(format, *args)
    }

    fun getProfitReceivedTitle(vararg args: Any?): String {
        val format = context.getString(R.string.profit_received_title)
        return stringFormat(format, *args)
    }

    fun getChargedOrderTitle(): String {
        return context.getString(R.string.collection_charged_orders)
    }

    fun getPotentialTitle(): String {
        return context.getString(R.string.title_profit_potential)
    }

    fun getIngresosTitle(): String {
        return context.getString(R.string.title_entries_acomplished)
    }

    fun formatPercentage(vararg args: Any?): String {
        val format = context.getString(R.string.percentage_string_format)
        return stringFormat(format, *args)
    }

    fun formatRangeSummary(twoLines: Boolean, vararg args: Any?): String {
        val format = if (twoLines) {
            context.getString(R.string.new_cycle_in_delimiter_two_lines)
        } else {
            context.getString(R.string.new_cycle_in_delimiter)
        }
        return stringFormat(format, *args)
    }

    fun getRecoveryGridTitle(): String {
        return context.getString(R.string.grid_title_recovery)
    }

    fun getInvoicedGridTitle(): String {
        return context.getString(R.string.grid_title_invoiced)
    }

    fun getCollectedGridTitle(): String {
        return context.getString(R.string.grid_title_recovered)
    }

    fun getCountryGridLabel(): String {
        return context.getString(R.string.your_country)
    }

    fun getRegionGridLabel(): String {
        return context.getString(R.string.your_region)
    }

    fun getZoneGridLabel(): String {
        return context.getString(R.string.your_zone)
    }

    fun getGainOrderDateLabel(orderDateLabel: String): String{
        return context.getString(R.string.text_orders_gain_date_order_name, orderDateLabel)
    }

    fun getOrderDateTitle(): String{
        return context.getString(R.string.text_orders_gain_date)
    }

    fun getRetention6d6Title(): String{
        return context.getString(R.string.text_retention_title_6d6)
    }

    fun getRetention6d6Detail(detail: String): String{
        return context.getString(R.string.text_retention_detail_6d6,detail)
    }

    fun getRetention6d6DifferentiatedDetailTitle(total6d6:String): String{
        return context.getString(R.string.text_retention_detail_differentiated_title_6d6 ,total6d6)
    }

    fun getRetention6d6DifferentiatedDetailFirstRow(total6d6: String): String{
        return context.getString(R.string.text_retention_detail_differentiated_title_6d6_low_value ,total6d6)
    }

    fun getRetention6d6DifferentiatedDetailSecondtRow(total6d6: String): String{
        return context.getString(R.string.text_retention_detail_differentiated_title_6d6_high_value ,total6d6)
    }


    fun getOtherWaysProfit() : String{
        return context.getString(R.string.text_other_ways_profits)
    }

    fun getCambioDeNivelTitle() : String {
        return context.getString(R.string.ganancia_cambio_de_nivel)
    }

    fun getCapitalizacionTitle() : String {
        return context.getString(R.string.capitalization)
    }

    fun getSegureAmountGain(): String {
        return context.getString(R.string.segure_gain_amount)
    }

    fun getCapitalizacionMessage(amountOfCapitalization: String) : String {
        return context.getString(R.string.capitalizacion_detail_nueva, amountOfCapitalization)
    }

    fun formatTotalGain() = context.getString(R.string.text_total_gain)



}
