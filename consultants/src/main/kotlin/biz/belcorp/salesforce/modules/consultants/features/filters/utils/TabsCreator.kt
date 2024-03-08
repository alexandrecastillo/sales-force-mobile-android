package biz.belcorp.salesforce.modules.consultants.features.filters.utils

import android.content.Context
import androidx.annotation.StringRes
import biz.belcorp.salesforce.analytics.core.domain.entities.UserProperties.session
import biz.belcorp.salesforce.components.utils.TabItem
import biz.belcorp.salesforce.components.utils.createTabs
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.constants.FilterKey
import biz.belcorp.salesforce.core.constants.SourceType
import biz.belcorp.salesforce.core.domain.entities.pais.Pais
import biz.belcorp.salesforce.modules.consultants.R
import com.google.android.material.tabs.TabLayout

object TabsCreator {

    private var tabLayout: TabLayout? = null
    private var context: Context? = null

    fun with(tabLayout: TabLayout): TabsCreator {
        this.tabLayout = tabLayout
        this.context = tabLayout.context
        return this
    }

    fun create(sourceType: Int) = when (sourceType) {
        SourceType.KPI_NEW_CYCLES -> createNewCycle()
        SourceType.KPI_SALE_ORDERS -> createSaleOrders()
        SourceType.KPI_CAPITALIZATION -> createCapitalization()
        SourceType.KPI_COLLECTION -> createCollection()
        else -> Unit
    }

    private fun createNewCycle() {
        tabLayout?.apply {
            createTabs(
                listOf(
                    TabItem(getString(R.string.text_all), FilterKey.KEY_ALL),
                    TabItem(getString(R.string.text_6d6), FilterKey.KEY_5D5),
                    TabItem(getString(R.string.text_5d5), FilterKey.KEY_4D4),
                    TabItem(getString(R.string.text_4d4), FilterKey.KEY_3D3),
                    TabItem(getString(R.string.text_3d3), FilterKey.KEY_2D2),
                    TabItem(getString(R.string.text_2d2), FilterKey.KEY_1D1)
                )
            )
        }
    }

    private fun createSaleOrders() {
        tabLayout?.apply {
            if((session?.pais?.codigoIso == Pais.PERU.codigoIso ||
                    session?.pais?.codigoIso == Pais.ECUADOR.codigoIso
                    ) ){
                createTabs(
                    listOf(
                        TabItem(getString(R.string.text_all), FilterKey.KEY_ALL),
                        TabItem(getString(R.string.text_low_value), FilterKey.KEY_LOW_VALUE),
                        TabItem(
                            getString(R.string.text_low_value_plus),
                            FilterKey.KEY_LOW_VALUE_PLUS
                        ),
                        TabItem(getString(R.string.text_high_value), FilterKey.KEY_HIGH_VALUE),
                        TabItem(
                            getString(R.string.text_high_value_plus),
                            FilterKey.KEY_HIGH_VALUE_PLUS
                        )
                    )
                )
            }else {
                createTabs(
                    listOf(
                        TabItem(getString(R.string.text_all), FilterKey.KEY_ALL),
                        TabItem(getString(R.string.text_low_value), FilterKey.KEY_LOW_VALUE),
                        TabItem(getString(R.string.text_high_value), FilterKey.KEY_HIGH_VALUE),
                        TabItem(
                            getString(R.string.text_high_value_plus),
                            FilterKey.KEY_HIGH_VALUE_PLUS
                        )
                    )
                )
            }
        }
    }

    private fun createCapitalization() {
        tabLayout?.apply {
            createTabs(
                listOf(
                    TabItem(getString(R.string.text_all), FilterKey.KEY_ALL),
                    TabItem(getString(R.string.text_pegs_with_debt), FilterKey.KEY_PEG_WITH_DEBT),
                    TabItem(
                        getString(R.string.text_peg_without_debt),
                        FilterKey.KEY_PEG_WITHOUT_DEBT
                    )
                )
            )
        }
    }

    private fun createCollection() {
        tabLayout?.apply {
            if((session?.pais?.codigoIso == Pais.PERU.codigoIso ||
                    session?.pais?.codigoIso == Pais.ECUADOR.codigoIso
                    ) ){
                createTabs(
                    listOf(
                        TabItem(getString(R.string.text_all), FilterKey.KEY_ALL),
                        TabItem(getString(R.string.text_low_value), FilterKey.KEY_LOW_VALUE),
                        TabItem(
                            getString(R.string.text_low_value_plus),
                            FilterKey.KEY_LOW_VALUE_PLUS
                        ),
                        TabItem(getString(R.string.text_high_value), FilterKey.KEY_HIGH_VALUE),
                        TabItem(
                            getString(R.string.text_high_value_plus),
                            FilterKey.KEY_HIGH_VALUE_PLUS
                        )
                    )
                )
            }else {
                createTabs(
                    listOf(
                        TabItem(getString(R.string.text_all), FilterKey.KEY_ALL),
                        TabItem(getString(R.string.text_low_value), FilterKey.KEY_LOW_VALUE),
                        TabItem(getString(R.string.text_high_value), FilterKey.KEY_HIGH_VALUE),
                        TabItem(
                            getString(R.string.text_high_value_plus),
                            FilterKey.KEY_HIGH_VALUE_PLUS
                        )
                    )
                )
            }
        }
    }

    private fun getString(@StringRes resId: Int) = context?.getString(resId) ?: EMPTY_STRING
}
