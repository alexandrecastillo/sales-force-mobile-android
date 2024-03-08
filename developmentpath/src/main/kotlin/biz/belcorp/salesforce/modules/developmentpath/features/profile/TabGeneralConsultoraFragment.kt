package biz.belcorp.salesforce.modules.developmentpath.features.profile

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.features.utils.lazyPersonIdentifier
import biz.belcorp.salesforce.core.features.utils.withPersonIdentifier
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.horariovisitas.HorarioVisitaFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.information.DatosMetasFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.section.PrepararseEsClaveFragment
import org.koin.android.viewmodel.ext.android.sharedViewModel

class TabGeneralConsultoraFragment : BaseFragment() {

    private val profileViewModel by sharedViewModel<ProfileViewModel>(from = { requireParentFragment() })

    private val personIdentifier by lazyPersonIdentifier()

    private val datosConsultoraFragment
        get() = DatosMetasFragment.newInstance(personIdentifier)

    private val prepararseEsClaveFragment
        get() = PrepararseEsClaveFragment.newInstance(personIdentifier)


    private val horarioVisitaFragment
        get() = HorarioVisitaFragment.newInstance(personIdentifier.id, personIdentifier.role)

    override fun getLayout(): Int = R.layout.fragment_tab_general_consultora

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configurarPorRol()
        setupViewModel()
    }

    private fun setupViewModel() {
        profileViewModel.viewState.observe(viewLifecycleOwner, viewStateObserver)
    }

    private val viewStateObserver = Observer<ProfileViewState> {
        when (it) {
            is ProfileViewState.SuccessSync -> configurarPorRol()
        }
    }

    private fun configurarPorRol() {
        when (personIdentifier.role) {
            Rol.CONSULTORA -> incrustarFragmentsConsultora()
            Rol.SOCIA_EMPRESARIA -> incrustarFragmentsSociaEmpresaria()
            else -> throw Exception("No soportado")
        }

        val rolUsuarioLogueado = profileViewModel.getSesion()

        if (!rolUsuarioLogueado.rol.equals(Rol.GERENTE_REGION)) {
            childFragmentManager.beginTransaction()
                .replace(R.id.framelayoutHorarioVisita, horarioVisitaFragment)
                .commit()
        }
    }

    private fun incrustarFragmentsConsultora() {
        if (!isAdded) return
        childFragmentManager
            .beginTransaction()
            .replace(R.id.framelayoutDatosConsultora, datosConsultoraFragment)
            .replace(R.id.framelayoutPrepararseEsClave, prepararseEsClaveFragment)
            .commit()
    }

    private fun incrustarFragmentsSociaEmpresaria() {
        if (!isAdded) return
        childFragmentManager
            .beginTransaction()
            .replace(R.id.framelayoutDatosConsultora, datosConsultoraFragment)
            .replace(R.id.framelayoutPrepararseEsClave, prepararseEsClaveFragment)
            .commit()
    }

    companion object {

        fun newInstance(personIdentifier: PersonIdentifier) = TabGeneralConsultoraFragment()
            .withPersonIdentifier(personIdentifier)
    }
}
