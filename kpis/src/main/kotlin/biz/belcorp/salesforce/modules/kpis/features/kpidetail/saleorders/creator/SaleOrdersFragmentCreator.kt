package biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.creator

import biz.belcorp.salesforce.core.base.FragmentCreator
import biz.belcorp.salesforce.core.constants.PeriodType
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.kpis.R
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.view.detail.multiprofile.SaleOrdersDetailFragment
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.view.detail.multiprofile.items.consolidated.grid.SaleOrderGridSelectorFragment
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.view.detail.se.SaleOrdersDetailSeFragment
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.view.header.multiprofile.SaleOrdersHeaderFragment
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.model.KpiFragmentParameters

class SaleOrdersFragmentCreator private constructor(
    params: KpiFragmentParameters
) : FragmentCreator<KpiFragmentParameters>(params) {

    companion object {
        fun init(params: KpiFragmentParameters) = SaleOrdersFragmentCreator(params)
    }

    override fun withRol(): FragmentCreator<KpiFragmentParameters> {
        when (params.rol) {
            Rol.DIRECTOR_VENTAS -> replaceForDV()
            Rol.GERENTE_REGION -> replaceForGR()
            Rol.GERENTE_ZONA -> replaceForGZ()
            Rol.SOCIA_EMPRESARIA -> replaceForSE()
            else -> Unit
        }
        return this
    }

    override fun getHeaderId() = R.id.header

    override fun getContentId() = R.id.detail

    override fun replaceForDV(): FragmentCreator<KpiFragmentParameters> {
        return replaceForGZ()
    }

    override fun replaceForGR(): FragmentCreator<KpiFragmentParameters> {
        return replaceForGZ()
    }

    override fun replaceForGZ(): FragmentCreator<KpiFragmentParameters> {
        when (params.periodType) {
            PeriodType.SALE -> replaceSaleMultiProfile()
            PeriodType.BILLING -> replaceBillingMultiProfile()
            else -> Unit
        }
        return this
    }

    override fun replaceForSE(): FragmentCreator<KpiFragmentParameters> {
        fragmentTransaction?.apply {
            replace(getContentId(), SaleOrdersDetailSeFragment.TAG) {
                SaleOrdersDetailSeFragment.newInstance()
            }
        }
        return this
    }

    private fun replaceSaleMultiProfile() {
        fragmentTransaction?.apply {
            replace(getHeaderId(), SaleOrdersHeaderFragment.TAG) {
                SaleOrdersHeaderFragment.newInstance()
            }
            replace(getContentId(), SaleOrdersDetailFragment.TAG) {
                SaleOrdersDetailFragment.newInstance()
            }
        }
    }

    private fun replaceBillingMultiProfile() {
        fragmentTransaction?.apply {
            replace(getHeaderId(), SaleOrdersHeaderFragment.TAG) {
                SaleOrdersHeaderFragment.newInstance()
            }
            replace(getContentId(), SaleOrderGridSelectorFragment.TAG) {
                SaleOrderGridSelectorFragment.newInstance()
            }
        }
    }
}
