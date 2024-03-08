package biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.creator

import biz.belcorp.salesforce.core.base.FragmentCreator
import biz.belcorp.salesforce.core.constants.PeriodType
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.kpis.R
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.detail.multiprofile.GainDetailFragment
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.detail.se.GainDetailSeFragment
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.header.multiprofile.GainHeaderFragment
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.model.KpiFragmentParameters

class GainFragmentCreator private constructor(
    params: KpiFragmentParameters
) : FragmentCreator<KpiFragmentParameters>(params) {

    companion object {
        fun init(params: KpiFragmentParameters) = GainFragmentCreator(params)
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
        when (params.periodType) {
            PeriodType.SALE -> replaceCollectionForDVSale()
            PeriodType.BILLING -> replaceCollectionForDVBilling()
            else -> Unit
        }
        return this
    }

    override fun replaceForGR(): FragmentCreator<KpiFragmentParameters> {
        when (params.periodType) {
            PeriodType.SALE -> replaceCollectionForGRSale()
            PeriodType.BILLING -> replaceCollectionForGRBilling()
            else -> Unit
        }
        return this
    }

    override fun replaceForGZ(): FragmentCreator<KpiFragmentParameters> {
        when (params.periodType) {
            PeriodType.SALE -> replaceCollectionForGZSale()
            PeriodType.BILLING -> replaceCollectionForGZBilling()
            else -> Unit
        }
        return this
    }

    override fun replaceForSE(): FragmentCreator<KpiFragmentParameters> {
        fragmentTransaction?.apply {
            replace(getContentId(), GainDetailSeFragment.TAG) {
                GainDetailSeFragment.newInstance()
            }
        }
        return this
    }

    private fun replaceCollectionForDVSale() {
        replaceCollectionForSale()
    }

    private fun replaceCollectionForGRSale() {
        replaceCollectionForSale()
    }

    private fun replaceCollectionForGZSale() {
        replaceCollectionForSale()
    }

    private fun replaceCollectionForSale() {
        fragmentTransaction?.apply {
            replace(getHeaderId(), GainHeaderFragment.TAG) {
                GainHeaderFragment.newInstance()
            }
            replace(getContentId(), GainDetailFragment.TAG) {
                GainDetailFragment.newInstance()
            }
        }
    }

    private fun replaceCollectionForBilling() {
        fragmentTransaction?.apply {
            replace(getHeaderId(), GainHeaderFragment.TAG) {
                GainHeaderFragment.newInstance()
            }
            replace(getContentId(), GainDetailFragment.TAG) {
                GainDetailFragment.newInstance()
            }
        }
    }

    private fun replaceCollectionForGZBilling() {
        replaceCollectionForBilling()
    }

    private fun replaceCollectionForGRBilling() {
        replaceCollectionForBilling()
    }

    private fun replaceCollectionForDVBilling() {
        replaceCollectionForBilling()
    }
}
