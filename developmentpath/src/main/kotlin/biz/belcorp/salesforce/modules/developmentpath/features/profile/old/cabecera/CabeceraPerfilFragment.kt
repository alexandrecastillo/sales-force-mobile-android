package biz.belcorp.salesforce.modules.developmentpath.features.profile.old.cabecera

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import biz.belcorp.mobile.components.core.GlideApp
import biz.belcorp.salesforce.components.widgets.TextDrawable
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.features.utils.lazyPersonIdentifier
import biz.belcorp.salesforce.core.features.utils.withPersonIdentifier
import biz.belcorp.salesforce.core.utils.sp
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.profile.header.PerfilCabeceraModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.old.cabecera.loader.CabeceraViewLoaderFactory
import kotlinx.android.synthetic.main.fragment_reconocimiento_comportamientos.*
import org.koin.android.viewmodel.ext.android.viewModel
import biz.belcorp.salesforce.core.R as CoreR

class CabeceraPerfilFragment : BaseFragment() {

    private val personIdentifier by lazyPersonIdentifier()

    private val viewModel by viewModel<CabeceraPerfilViewModel>()

    override fun getLayout() = R.layout.fragment_cabecera_perfil

    companion object {

        fun newInstance(personIdentifier: PersonIdentifier) =
            CabeceraPerfilFragment()
                .withPersonIdentifier(personIdentifier)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel.viewState.observe(viewLifecycleOwner, viewStateObserver)
        viewModel.cargar(personIdentifier)
    }

    private val viewStateObserver = Observer<CabeceraPerfilViewState> {
        when (it) {
            is CabeceraPerfilViewState.ShowInfo -> {
                cargarModelo(it.model)
                cargarAvatar(it.model.iniciales)
            }
        }
    }

    private fun cargarModelo(model: PerfilCabeceraModel) {
        view?.let {
            CabeceraViewLoaderFactory
                .with(model)
                .load(it)
        }
    }

    private fun cargarAvatar(placeholder: String?) {
        if (context == null) return

        val font = ResourcesCompat.getFont(context!!, CoreR.font.mulish_regular)

        val circularPlaceHolder = TextDrawable.builder()
            .beginConfig()
            .fontSize(sp(20))
            .useFont(requireNotNull(font))
            .endConfig()
            .buildRound(
                requireNotNull(placeholder), ContextCompat.getColor(
                    requireContext(),
                    R.color.rdd_accent
                )
            )

        GlideApp.with(this)
            .load(Constant.EMPTY_STRING)
            .placeholder(circularPlaceHolder)
            .into(imgIconConsultant ?: return)
    }

}
