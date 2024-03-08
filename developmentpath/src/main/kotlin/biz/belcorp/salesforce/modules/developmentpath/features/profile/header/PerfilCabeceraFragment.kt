package biz.belcorp.salesforce.modules.developmentpath.features.profile.header

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.features.utils.lazyPersonIdentifier
import biz.belcorp.salesforce.core.features.utils.withPersonIdentifier
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.profile.header.loader.CabeceraViewLoaderFactory

class PerfilCabeceraFragment : BaseFragment() {

    private val viewModel by injectFragment<PerfilCabeceraViewModel>()

    private val personIdentifier by lazyPersonIdentifier()

    var txtNombreCabecera: TextView? = null
    var txtDigitalSegment: TextView? = null
    var imgIndicador: ImageView? = null

    override fun getLayout(): Int = R.layout.fragment_cabecera_perfil_v2

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        initialize()
    }

    private fun setupViewModel() {
        viewModel.viewState.observe(viewLifecycleOwner, infoViewState)
    }

    private fun initialize() {
        viewModel.obtener(personIdentifier)
    }

    private val infoViewState = Observer<PerfilCabeceraViewState> { infoState ->
        infoState?.let {
            when (it) {
                is PerfilCabeceraViewState.ShowInfo -> mostrarInformacion(it.model)
            }
        }
    }

    private fun mostrarInformacion(model: PerfilCabeceraModel) {
        view?.let {
            CabeceraViewLoaderFactory.with(model)
                .useContext(requireContext())
                .loadCabeceraName(txtNombreCabecera)
                .loadDigitalSegment(txtDigitalSegment)
                .loadIndicador(imgIndicador)
                .bind(it)
        }
    }

    companion object {

        fun newInstance(personIdentifier: PersonIdentifier): PerfilCabeceraFragment {
            return PerfilCabeceraFragment()
                .withPersonIdentifier(personIdentifier)
        }
    }
}
