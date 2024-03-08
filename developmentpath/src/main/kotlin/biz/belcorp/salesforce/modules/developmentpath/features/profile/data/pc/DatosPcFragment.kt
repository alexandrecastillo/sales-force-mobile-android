package biz.belcorp.salesforce.modules.developmentpath.features.profile.data.pc

import android.os.Bundle
import android.view.View
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.features.utils.lazyPersonIdentifier
import biz.belcorp.salesforce.core.features.utils.withPersonIdentifier
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.profile.data.contacto.DatosContactoFragment

class DatosPcFragment : BaseFragment() {

    private val personIdentifier by lazyPersonIdentifier()

    private val pcFragment by lazy { DatosContactoFragment.newInstance(personIdentifier) }

    override fun getLayout() = R.layout.fragment_datos_pc

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        iniciarDatosContacto()
    }

    private fun iniciarDatosContacto() {
        if (!isAdded) return
        childFragmentManager
            .beginTransaction()
            .replace(R.id.framelayoutDatosContacto, pcFragment, pcFragment.javaClass.simpleName)
            .commitAllowingStateLoss()
    }

    companion object {

        fun newInstance(personIdentifier: PersonIdentifier) =
            DatosPcFragment()
                .withPersonIdentifier(personIdentifier)
    }

}
