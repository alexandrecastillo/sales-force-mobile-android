package biz.belcorp.salesforce.modules.billing.features.billing.creator

import biz.belcorp.salesforce.core.base.FragmentCreator
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.billing.R
import biz.belcorp.salesforce.modules.billing.features.billing.model.BillingFragmentParameters
import biz.belcorp.salesforce.modules.billing.features.billing.view.detail.BillingDetailFragment
import biz.belcorp.salesforce.modules.billing.features.billing.view.detail.BillingMultiProfileDetailFragment
import biz.belcorp.salesforce.modules.billing.features.billing.view.header.multiprofile.BillingMultiProfileHeaderFragment
import biz.belcorp.salesforce.modules.billing.features.billing.view.header.se.BillingHeaderFragment

class BillingFragmentCreator private constructor(params: BillingFragmentParameters) :
    FragmentCreator<BillingFragmentParameters>(params) {

    companion object {
        fun init(params: BillingFragmentParameters) = BillingFragmentCreator(params)
    }

    override fun getHeaderId() = R.id.header

    override fun getContentId() = R.id.detail

    override fun replaceForDV() = replaceForMultoProfile()
    override fun replaceForGR() = replaceForMultoProfile()
    override fun replaceForGZ() = replaceForMultoProfile()

    private fun replaceForMultoProfile(): FragmentCreator<BillingFragmentParameters> {
        fragmentTransaction?.apply {
            replace(getHeaderId(), BillingMultiProfileHeaderFragment.TAG) {
                BillingMultiProfileHeaderFragment.newInstance()
            }
            replace(getContentId(), BillingMultiProfileDetailFragment.TAG) {
                BillingMultiProfileDetailFragment.newInstance()
            }
        }
        return this
    }

    override fun replaceForSE(): FragmentCreator<BillingFragmentParameters> {
        fragmentTransaction?.apply {
            replace(getHeaderId(), BillingHeaderFragment.TAG) {
                BillingHeaderFragment.newInstance()
            }
            replace(getContentId(), BillingDetailFragment.TAG) {
                BillingDetailFragment.newInstance()
            }
        }
        return this
    }

    override fun withRol(): FragmentCreator<BillingFragmentParameters> {
        when (params.rol) {
            Rol.DIRECTOR_VENTAS -> replaceForDV()
            Rol.GERENTE_REGION -> replaceForGR()
            Rol.GERENTE_ZONA -> replaceForGZ()
            Rol.SOCIA_EMPRESARIA -> replaceForSE()
            else -> Unit
        }
        return this
    }
}
