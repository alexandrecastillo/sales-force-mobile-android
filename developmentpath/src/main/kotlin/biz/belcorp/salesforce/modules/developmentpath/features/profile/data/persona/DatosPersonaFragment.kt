package biz.belcorp.salesforce.modules.developmentpath.features.profile.data.persona

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import biz.belcorp.mobile.components.core.extensions.visible
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.features.utils.lazyPersonIdentifier
import biz.belcorp.salesforce.core.features.utils.withPersonIdentifier
import biz.belcorp.salesforce.modules.developmentpath.R
import kotlinx.android.synthetic.main.fragment_datos_persona.*
import kotlinx.android.synthetic.main.layout_datos_persona_gz_gr.*
import org.koin.android.viewmodel.ext.android.viewModel

class DatosPersonaFragment : BaseFragment() {

    private val personIdentifier by lazyPersonIdentifier()

    override fun getLayout() = R.layout.fragment_datos_persona

    private val viewModel by viewModel<DatosPersonaViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel.viewState.observe(viewLifecycleOwner, viewStateObserver)
        viewModel.obtener(personIdentifier)
    }

    private val viewStateObserver = Observer<DatosPersonaViewState> {
        when (it) {
            is DatosPersonaViewState.ShowInfo -> {
                showInfo(it.model)
            }
        }
    }

    private fun showInfo(datosPersona: DatosPersonaModel) {
        tvUAGzGr?.text = datosPersona.ua
        tvRolGzGr?.text = personIdentifier.role.comoTexto()
        tvEstadoTitulo?.text = datosPersona.titulo
        tvEstado?.text = datosPersona.estado
        lyDatosGzGr?.visible()
    }

    companion object {

        fun newInstance(personIdentifier: PersonIdentifier) =
            DatosPersonaFragment()
                .withPersonIdentifier(personIdentifier)
    }
}
