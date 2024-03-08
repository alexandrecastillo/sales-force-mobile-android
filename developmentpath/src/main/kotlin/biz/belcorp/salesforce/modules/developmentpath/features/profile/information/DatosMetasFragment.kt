package biz.belcorp.salesforce.modules.developmentpath.features.profile.information

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalytics
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.domain.exceptions.UnsupportedRoleException
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.features.utils.lazyPersonIdentifier
import biz.belcorp.salesforce.core.features.utils.withPersonIdentifier
import biz.belcorp.salesforce.core.utils.dimen
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.core.utils.showOnce
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PersonaRdd
import biz.belcorp.salesforce.modules.developmentpath.features.analytics.AnalyticsConstants
import biz.belcorp.salesforce.modules.developmentpath.features.analytics.FirebaseAnalyticsPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.cobranzaestadodecuenta.BaseDatosDialogFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.cobranzaestadodecuenta.CobranzaYEstadoCuentadialogFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.cobranzaganancia.CobranzaGananciaDialogFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.cobranzaganancia.CobranzaYGananciaModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.goals.sociaempresaria.view.listar.ListarMetasSociaFragment
import biz.belcorp.salesforce.modules.developmentpath.utils.maskCampaignWithPrefix
import biz.belcorp.salesforce.modules.developmentpath.widgets.etiqueta.EtiquetaInfoAdapter
import biz.belcorp.salesforce.modules.developmentpath.widgets.etiqueta.EtiquetaInfoDecoration
import biz.belcorp.salesforce.modules.developmentpath.widgets.etiqueta.GridManagerBuilder
import biz.belcorp.salesforce.modules.developmentpath.widgets.etiqueta.model.ContenedorInfoBasica
import kotlinx.android.synthetic.main.fragment_datos_consultora.*
import org.koin.android.viewmodel.ext.android.viewModel
import biz.belcorp.salesforce.base.R as BaseR

class DatosMetasFragment : BaseFragment(), DatosCobranzaGananciaView {

    private val personIdentifier by lazyPersonIdentifier()

    private var cobranzaGananciaCampania: String? = null

    private val datosPersonaViewModel by viewModel<DatosPersonaViewModel>()
    private val cobranzaGananciaPresenter by injectFragment<DatosCobranzaGananciaPresenter>()
    private val firebaseAnalyticsPresenter by injectFragment<FirebaseAnalyticsPresenter>()

    private val expandible by lazy {
        Expandible(expandableContainer, fadingView, toggleIcon)
    }

    private val datosPersonaAdapter by lazy { EtiquetaInfoAdapter() }

    private val listarMetasSociaFragment by lazy {
        ListarMetasSociaFragment.newInstance(personIdentifier)
    }

    override fun getLayout(): Int = R.layout.fragment_datos_consultora

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupViewModel()
        initialize()
    }

    private fun setupView() {
        configurarRecycler()
        configurarExpandible()
        configurarPorRol()
    }

    private fun setupViewModel() {
        datosPersonaViewModel.viewState.observe(viewLifecycleOwner, infoViewState)
    }

    private fun initialize() {
        datosPersonaViewModel.obtener(personIdentifier)
        firebaseAnalyticsPresenter.enviarPantallaPerfil(
            TagAnalytics.PERFIL_PERFIL,
            personIdentifier.role,
            personIdentifier.id
        )
    }

    private val infoViewState = Observer<DatosPersonaViewState> { infoState ->
        infoState?.let {
            when (it) {
                is DatosPersonaViewState.ShowInfo -> {
                    pintarContenedorInfo(it.modelo)
                }
            }
        }
    }

    private fun pintarContenedorInfo(modelo: ContenedorInfoBasica) {
        recyclerDatos?.layoutManager = GridManagerBuilder.buildForContainer(context, modelo)
        datosPersonaAdapter.actualizar(modelo.grupos)
    }

    override fun pintarDatosCobranzaGanancia(modelo: CobranzaYGananciaModel) {
        if (!isAttached()) return
        cobranzaGananciaCampania = modelo.campania?.maskCampaignWithPrefix().orEmpty()
        btnCobranzaYEstadoDeCuenta?.ibText =
            getString(R.string.btn_cobranza_y_ganancia, cobranzaGananciaCampania)
        btnCobranzaYEstadoDeCuenta?.setOnClickListener {
            val title = getString(R.string.title_cobranza_y_ganancia, cobranzaGananciaCampania)
            BaseDatosDialogFragment.newInstance<CobranzaGananciaDialogFragment>(
                personIdentifier.id,
                personIdentifier.role,
                title
            ).showOnce(childFragmentManager)
        }
    }

    override fun cobranzaGananciaError() {
        if (!isAttached()) return
        cobranzaGananciaCampania = getString(R.string.btn_cobranza_y_ganancia_do_data)
        btnCobranzaYEstadoDeCuenta?.ibText = cobranzaGananciaCampania
        btnCobranzaYEstadoDeCuenta?.setOnClickListener(null)
    }

    private fun configurarRecycler() {
        recyclerDatos?.isNestedScrollingEnabled = false
        recyclerDatos?.addItemDecoration(EtiquetaInfoDecoration(dimen(BaseR.dimen.content_inset_normal)))
        recyclerDatos?.adapter = datosPersonaAdapter
    }

    private fun configurarExpandible() {
        expandible.redibujar()
        toggleButton?.setOnClickListener {
            expandible.invertirEstado()
            if (personIdentifier.role == Rol.CONSULTORA && expandible.isExpandido) {
                firebaseAnalyticsPresenter.enviarEventoTagConsultora(
                    TagAnalytics.EVENTO_PERFIL_CONSULTORA,
                    AnalyticsConstants.VER_MAS_DATOS_CONSULTORA
                )
            }
        }
    }

    private fun configurarPorRol() {
        when (personIdentifier.role) {
            Rol.CONSULTORA -> {
                setTextHeader("consultora")
                configurarCobranzaYEstadoDeCuenta()
            }
            Rol.SOCIA_EMPRESARIA -> {
                setTextHeader("socia")
                configurarMetas(listarMetasSociaFragment)
                configurarCobranzaYGanancia()
            }
            else -> throw UnsupportedRoleException(personIdentifier.role)
        }
    }

    private fun setTextHeader(perfil: String) {
        header?.hitTitle = resources.getString(R.string.perfil_datos_de_la_x, perfil)
    }

    private fun configurarMetas(fragment: Fragment) {
        childFragmentManager
            .beginTransaction()
            .replace(R.id.frameLayoutMetas, fragment)
            .commit()
    }

    private fun configurarCobranzaYEstadoDeCuenta() {
        btnCobranzaYEstadoDeCuenta?.ibText = getString(R.string.btn_cobranza_y_estado_de_cuenta)
        btnCobranzaYEstadoDeCuenta?.visibility = View.VISIBLE
        btnCobranzaYEstadoDeCuenta?.setOnClickListener {
            val title = getString(R.string.title_cobranza_y_estado_de_cuenta)
            BaseDatosDialogFragment.newInstance<CobranzaYEstadoCuentadialogFragment>(
                personIdentifier.id,
                personIdentifier.role,
                title
            ).showOnce(childFragmentManager)
            firebaseAnalyticsPresenter.enviarEventoTagConsultora(
                TagAnalytics.EVENTO_PERFIL_CONSULTORA,
                AnalyticsConstants.VER_COBRANZA_ESTADO_CUENTA
            )
        }
    }

    private fun configurarCobranzaYGanancia() {
        val persona = PersonaRdd.Identificador(personIdentifier.id, personIdentifier.role)
        cobranzaGananciaPresenter.obtener(persona)
        btnCobranzaYEstadoDeCuenta?.visibility = View.VISIBLE
    }

    companion object {

        fun newInstance(personIdentifier: PersonIdentifier): DatosMetasFragment {
            return DatosMetasFragment()
                .withPersonIdentifier(personIdentifier)
        }
    }
}
