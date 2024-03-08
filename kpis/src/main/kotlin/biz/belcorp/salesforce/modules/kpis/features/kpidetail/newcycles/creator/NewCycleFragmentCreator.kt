package biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.creator

import biz.belcorp.salesforce.core.base.FragmentCreator
import biz.belcorp.salesforce.core.constants.PeriodType
import biz.belcorp.salesforce.core.utils.isDV
import biz.belcorp.salesforce.core.utils.isGR
import biz.belcorp.salesforce.core.utils.isGZ
import biz.belcorp.salesforce.core.utils.isSE
import biz.belcorp.salesforce.modules.kpis.R
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.view.detail.multiprofile.NewCycleDetailFragment
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.view.detail.multiprofile.consolidated.NewCycleGridSelectorFragment
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.view.detail.se.NewCycleDetailSeFragment
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.view.header.NewCycleHeaderFragment
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.model.KpiFragmentParameters

class NewCycleFragmentCreator private constructor(
    params: KpiFragmentParameters
) : FragmentCreator<KpiFragmentParameters>(params) {

    companion object {
        fun init(params: KpiFragmentParameters) = NewCycleFragmentCreator(params)
    }

    override fun withRol(): FragmentCreator<KpiFragmentParameters> {
        params.apply {
            when {
                rol.isDV() -> replaceForDV()
                rol.isGR() -> replaceForGR()
                rol.isGZ() -> replaceForGZ()
                rol.isSE() -> replaceForSE()
                else -> Unit
            }
        }
        return this
    }

    override fun getHeaderId() = R.id.header

    override fun getContentId() = R.id.detail

    override fun replaceForDV(): FragmentCreator<KpiFragmentParameters> {
        return replaceForMultiProfile()
    }

    override fun replaceForGR(): FragmentCreator<KpiFragmentParameters> {
        return replaceForMultiProfile()
    }

    override fun replaceForGZ(): FragmentCreator<KpiFragmentParameters> {
        return replaceForMultiProfile()
    }

    private fun replaceForMultiProfile(): FragmentCreator<KpiFragmentParameters> {
        when (params.periodType) {
            PeriodType.SALE -> replaceNewCycleForSale()
            PeriodType.BILLING -> replaceNewCycleForBilling()
            else -> Unit
        }
        return this
    }

    override fun replaceForSE(): FragmentCreator<KpiFragmentParameters> {
        fragmentTransaction?.apply {
            replace(getContentId(), NewCycleDetailSeFragment.TAG) {
                NewCycleDetailSeFragment.newInstance()
            }
        }
        return this
    }

    private fun replaceNewCycleForSale() {
        fragmentTransaction?.apply {
            replace(getHeaderId(), NewCycleHeaderFragment.TAG) {
                NewCycleHeaderFragment.newInstance()
            }
            replace(getContentId(), NewCycleDetailFragment.TAG) {
                NewCycleDetailFragment.newInstance()
            }
        }
    }

    private fun replaceNewCycleForBilling() {
        fragmentTransaction?.apply {
            replace(getHeaderId(), NewCycleHeaderFragment.TAG) {
                NewCycleHeaderFragment.newInstance()
            }
            replace(getContentId(), NewCycleDetailFragment.TAG) {
                NewCycleGridSelectorFragment.newInstance()
            }
        }
    }
}
