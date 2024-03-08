package biz.belcorp.salesforce.modules.digital.features.digital.creator

import biz.belcorp.salesforce.core.base.FragmentCreator
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.digital.R
import biz.belcorp.salesforce.modules.digital.features.digital.model.DigitalFragmentParameters
import biz.belcorp.salesforce.modules.digital.features.digital.view.detail.multiprofile.DigitalConsolidatedFragment
import biz.belcorp.salesforce.modules.digital.features.digital.view.detail.se.DigitalDetailFragment
import biz.belcorp.salesforce.modules.digital.features.digital.view.header.DigitalHeaderFragment

class DigitalFragmentCreator private constructor(params: DigitalFragmentParameters) :
    FragmentCreator<DigitalFragmentParameters>(params) {

    companion object {
        fun init(params: DigitalFragmentParameters) = DigitalFragmentCreator(params)
    }

    override fun getHeaderId() = R.id.header

    override fun getContentId() = R.id.detail

    override fun replaceForDV() = replaceForMultiProfile()
    override fun replaceForGR() = replaceForMultiProfile()
    override fun replaceForGZ() = replaceForMultiProfile()

    private fun replaceForMultiProfile(): FragmentCreator<DigitalFragmentParameters> {
        fragmentTransaction?.apply {
            replace(getHeaderId(), DigitalHeaderFragment.TAG) {
                DigitalHeaderFragment.newInstance()
            }
            replace(getContentId(), DigitalConsolidatedFragment.TAG) {
                DigitalConsolidatedFragment.newInstance()
            }
        }
        return this
    }

    override fun replaceForSE(): FragmentCreator<DigitalFragmentParameters> {
        fragmentTransaction?.apply {
            replace(getHeaderId(), DigitalHeaderFragment.TAG) {
                DigitalHeaderFragment.newInstance()
            }
            replace(getContentId(), DigitalDetailFragment.TAG) {
                DigitalDetailFragment.newInstance()
            }
        }
        return this
    }

    override fun withRol(): FragmentCreator<DigitalFragmentParameters> {
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
