package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.header

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.core.base.BaseDialogFragment
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalytics
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.features.utils.lazyPersonIdentifier
import biz.belcorp.salesforce.core.features.utils.withPersonIdentifier
import biz.belcorp.salesforce.core.utils.dimen
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.core.utils.toast
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.general.TipData
import biz.belcorp.salesforce.modules.developmentpath.features.analytics.AnalyticsConstants
import biz.belcorp.salesforce.modules.developmentpath.features.analytics.FirebaseAnalyticsPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.caminobrillante.container.CaminoBrillanteFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.concursos.ConcursosFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.digital.container.HerramientaDigitalContenedorFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.novedades.container.NovedadesContenedorFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.programanuevas.ProgramaNuevasFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.tipsestablecidas.TipsEstablecidasFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.ventaganancia.container.VentaGananciaContenedorFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.section.TipDesarrolloModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.headercontainer.ItemSeccionDecoration
import kotlinx.android.synthetic.main.fragment_detalle_tips_desarrollo.*
import org.koin.android.viewmodel.ext.android.viewModel
import biz.belcorp.salesforce.base.R as BaseR

class DetalleTipsDesarrolloFragment : BaseDialogFragment(), DetalleTipsDesarrolloView {

    var elemetoSeleccionado: TipDesarrolloModel? = null
    private var currentFragment: String = ""

    private val identifier by lazyPersonIdentifier()
    private val adapter by lazy { DetalleTipsDesarrolloAdapter(::onItemSelected) }
    private val viewModel by viewModel<DetalleTipsDesarrolloViewModel>()

    private val faPresenter by injectFragment<FirebaseAnalyticsPresenter>()

    private val ventaGananciaFragment by lazy {
        VentaGananciaContenedorFragment.newInstance(identifier.id, identifier.role)
            .apply {
                opciones = elemetoSeleccionado?.opciones ?: return@apply
            }
    }

    private val herramientaDigitalFragment by lazy {
        HerramientaDigitalContenedorFragment.newInstance(identifier.id, identifier.role)
            .apply {
                opciones = elemetoSeleccionado?.opciones ?: return@apply
            }
    }

    private val novedadesFragment by lazy {
        NovedadesContenedorFragment.newInstance(identifier.id, identifier.role)
            .apply {
                opciones = elemetoSeleccionado?.opciones ?: return@apply
            }
    }

    private val concursosFragment by lazy {
        ConcursosFragment.newInstance(
            identifier.id,
            identifier.role
        )
    }

    private val caminoBrillanteFragment by lazy {
        CaminoBrillanteFragment.newInstance(identifier.id, identifier.role)
            .apply {
                opciones = elemetoSeleccionado?.opciones ?: return@apply
            }
    }

    private val programaNuevasFragment by lazy {
        ProgramaNuevasFragment.newInstance(identifier.id, identifier.role)
            .apply {
                opciones = elemetoSeleccionado?.opciones ?: return@apply
            }
    }

    private val tipsEstablecidasFragment by lazy {
        TipsEstablecidasFragment.newInstance(identifier.id, identifier.role)
            .apply {
                opciones = elemetoSeleccionado?.opciones ?: return@apply
            }
    }

    override fun getLayout() = R.layout.fragment_detalle_tips_desarrollo

    override fun onStart() {
        super.onStart()
        fitFullScreen()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configurarRecycler()
        configurarBotonRegresar()
        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel.viewState.observe(viewLifecycleOwner, viewStateObserver)
        viewModel.obtener(identifier)
    }

    private val viewStateObserver = Observer<DetalleTipsDesarrolloViewState> {
        when (it) {
            is DetalleTipsDesarrolloViewState.ShowTips -> {
                establecerOpcionSeleccionada()
                cargarOpciones(it.tips)
                moverAOpcionSeleccionada()
                redirigirFragmentoSeleccionado()
            }
        }
    }

    override fun cargarOpciones(opciones: List<TipDesarrolloModel>) {
        adapter.actualizar(opciones)
    }

    override fun redirigirFragmentoSeleccionado() {
        mostrarFragmento(elemetoSeleccionado?.tipoImagen)
    }

    override fun establecerOpcionSeleccionada() {
        elemetoSeleccionado?.let {
            adapter.notificarElementosCambiados(it.posicion)
        }
    }

    override fun moverAOpcionSeleccionada() {
        elemetoSeleccionado?.let {
            rvDetalleTipsDesarrollo?.scrollToPosition(it.posicion)
        }
    }

    private fun configurarRecycler() {
        rvDetalleTipsDesarrollo?.setHasFixedSize(true)
        rvDetalleTipsDesarrollo?.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rvDetalleTipsDesarrollo?.addItemDecoration(crearDecoration())
        rvDetalleTipsDesarrollo?.adapter = adapter
    }

    private fun crearDecoration(): RecyclerView.ItemDecoration {
        val margen = requireContext().dimen(BaseR.dimen.content_inset_normal)
        return ItemSeccionDecoration(margen)
    }

    private fun mostrarFragmento(tipo: TipData.TipoImagen?) {
        if (!isAttached()) return
        when (tipo) {
            TipData.TipoImagen.VENTAGANANCIA -> {
                incrustarFragmento(ventaGananciaFragment)
                faPresenter.enviarEventoTagTipsDesarrollo(
                    TagAnalytics.EVENTO_TIPS_DESARROLLO_VENTA_Y_GANANCIA,
                    AnalyticsConstants.LABEL_TIPS_DESARROLLO_VENTA_Y_GANANCIA
                )
            }
            TipData.TipoImagen.DIGITAL -> {
                incrustarFragmento(herramientaDigitalFragment)
                faPresenter.enviarEventoTagTipsDesarrollo(
                    TagAnalytics.EVENTO_TIPS_DESARROLLO_DIGITAL,
                    AnalyticsConstants.LABEL_TIPS_DESARROLLO_DIGITAL
                )
            }
            TipData.TipoImagen.CONCURSOS -> {
                incrustarFragmento(concursosFragment)
                faPresenter.enviarEventoTagTipsDesarrollo(
                    TagAnalytics.EVENTO_TIPS_DESARROLLO_CONCURSOS,
                    AnalyticsConstants.LABEL_TIPS_DESARROLLO_CONCURSOS
                )
            }
            TipData.TipoImagen.NOVEDADES -> {
                incrustarFragmento(novedadesFragment)
                faPresenter.enviarEventoTagTipsDesarrollo(
                    TagAnalytics.EVENTO_TIPS_DESARROLLO_NOVEDADES,
                    AnalyticsConstants.LABEL_TIPS_DESARROLLO_NOVEDADES
                )
            }
            TipData.TipoImagen.PROGRAMANUEVAS -> {
                incrustarFragmento(programaNuevasFragment)
                faPresenter.enviarEventoTagTipsDesarrollo(
                    TagAnalytics.EVENTO_TIPS_DESARROLLO_PROGRAMA_NUEVAS,
                    AnalyticsConstants.LABEL_TIPS_DESARROLLO_PROGRAMA_NUEVAS
                )
            }
            TipData.TipoImagen.CAMINOBRILLANTE -> {
                incrustarFragmento(caminoBrillanteFragment)
                faPresenter.enviarEventoTagTipsDesarrollo(
                    TagAnalytics.EVENTO_TIPS_DESARROLLO_CAMINO_BRILLANTE,
                    AnalyticsConstants.LABEL_TIPS_DESARROLLO_CAMINO_BRILLANTE
                )
            }
            TipData.TipoImagen.TIPSESTABLECIDAS -> {
                incrustarFragmento(tipsEstablecidasFragment)
                faPresenter.enviarEventoTagTipsDesarrollo(
                    TagAnalytics.EVENTO_TIPS_DESARROLLO_TIPS_ESTABLECIDAS,
                    AnalyticsConstants.LABEL_TIPS_DESARROLLO_TIPS_ESTABLECIDAS
                )
            }
            TipData.TipoImagen.OTHER -> toast("No existe opcion")
        }
    }

    private fun incrustarFragmento(fragment: Fragment) {
        if (!isAttached()) return
        if (currentFragment != fragment::class.java.simpleName) {
            currentFragment = fragment::class.java.simpleName
            childFragmentManager.beginTransaction()
                .replace(R.id.flContenedor, fragment, fragment::class.java.simpleName)
                .commit()
        }
    }

    private fun configurarBotonRegresar() {
        imgBotonRegresar.setOnClickListener { dismiss() }
    }

    private fun onItemSelected(item: TipDesarrolloModel, posicion: Int) {
        elemetoSeleccionado = item
        adapter.notificarElementosCambiados(posicion)
        moverAOpcionSeleccionada()
        mostrarFragmento(item.tipoImagen)
    }

    companion object {

        fun newInstance(personIdentifier: PersonIdentifier) = DetalleTipsDesarrolloFragment()
            .withPersonIdentifier(personIdentifier)
    }
}
