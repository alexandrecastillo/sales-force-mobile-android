package biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization

import biz.belcorp.salesforce.base.utils.isDv
import biz.belcorp.salesforce.base.utils.isGr
import biz.belcorp.salesforce.base.utils.isGz
import biz.belcorp.salesforce.base.utils.isSe
import biz.belcorp.salesforce.core.base.FragmentCreator
import biz.belcorp.salesforce.core.constants.PeriodType.Companion.BILLING
import biz.belcorp.salesforce.core.constants.PeriodType.Companion.SALE
import biz.belcorp.salesforce.modules.kpis.R
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.view.detail.multiprofile.billing.ConsolidatedCapitalizationKpiFragment
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.view.detail.multiprofile.sales.CapitalizationKpiDetailOnSalesFragment
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.view.detail.se.billing.CapitalizationKpiDetailSeOnBillingFragment
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.view.detail.se.sales.CapitalizationKpiDetailSeOnSalesFragment
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.view.header.businesspartner.billing.CapitalizationKpiHeaderOnBillingFragment
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.view.header.businesspartner.sales.CapitalizationKpiHeaderOnSalesFragment
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.model.KpiFragmentParameters

class CapitalizationFragmentCreator(params: KpiFragmentParameters) :
    FragmentCreator<KpiFragmentParameters>(params) {

    override fun getHeaderId() = R.id.header

    override fun getContentId() = R.id.detail

    override fun replaceForDV(): FragmentCreator<KpiFragmentParameters> {
        return this
    }

    override fun replaceForGR(): FragmentCreator<KpiFragmentParameters> {
        return replaceForMultiprofile()
    }

    override fun replaceForGZ(): FragmentCreator<KpiFragmentParameters> {
        return replaceForMultiprofile()
    }

    private fun replaceForMultiprofile(): FragmentCreator<KpiFragmentParameters> {
        when (params.periodType) {
            SALE -> doOnSalesPeriodForGz()
            BILLING -> doOnBillingPeriodForGz()
        }
        return this
    }

    override fun replaceForSE(): FragmentCreator<KpiFragmentParameters> {
        when (params.periodType) {
            SALE -> doOnSalesPeriodForSE()
            BILLING -> doOnBillingPeriodForSE()
        }
        return this
    }

    override fun withRol(): FragmentCreator<KpiFragmentParameters> {
        with(params.rol) {
            when {
                isDv() -> replaceForDV()
                isGr() -> replaceForGR()
                isGz() -> replaceForGZ()
                isSe() -> replaceForSE()
                else -> Unit
            }
        }
        return this
    }

    private fun doOnSalesPeriodForSE() {
        fragmentTransaction?.apply {
            replace(getContentId(), CapitalizationKpiDetailSeOnSalesFragment.TAG) {
                CapitalizationKpiDetailSeOnSalesFragment.newInstance()
            }
        }
    }

    private fun doOnBillingPeriodForSE() {
        fragmentTransaction?.apply {
            replace(getContentId(), CapitalizationKpiDetailSeOnBillingFragment.TAG) {
                CapitalizationKpiDetailSeOnBillingFragment.newInstance()
            }
        }
    }

    private fun doOnSalesPeriodForGz() {
        fragmentTransaction?.apply {
            replace(getHeaderId(), CapitalizationKpiHeaderOnSalesFragment.TAG) {
                params.apply { isParent = true }
                CapitalizationKpiHeaderOnSalesFragment.newInstance()
            }

            replace(getContentId(), CapitalizationKpiDetailSeOnSalesFragment.TAG) {
                CapitalizationKpiDetailOnSalesFragment.newInstance(params)
            }
        }
    }

    private fun doOnBillingPeriodForGz() {
        fragmentTransaction?.apply {
            replace(getHeaderId(), CapitalizationKpiHeaderOnBillingFragment.TAG) {
                CapitalizationKpiHeaderOnBillingFragment.newInstance()
            }

            replace(getContentId(), ConsolidatedCapitalizationKpiFragment.TAG) {
                ConsolidatedCapitalizationKpiFragment.newInstance()
            }
        }
    }

    companion object {
        fun init(params: KpiFragmentParameters) = CapitalizationFragmentCreator(params)
    }

}
