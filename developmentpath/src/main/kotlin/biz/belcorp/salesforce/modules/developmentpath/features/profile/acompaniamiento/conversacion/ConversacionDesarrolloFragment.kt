package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.conversacion

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.text.SpannableStringBuilder
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.features.loading.LoadingDialogFragment
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view.ErrorDescargaInformacionDialogFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.conversacion.focotrabajo.FocosTrabajoAdapter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.conversacion.focotrabajo.FocosTrabajoPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.conversacion.focotrabajo.FocosTrabajoView
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.conversacion.focotrabajo.GraficoGrModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.graficos.gerenteregion.GraficosGrPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.graficos.gerenteregion.GraficosGrView
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.habilidades.asignar.view.AsignarHabilidadFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.habilidades.detalle.adapter.DetallesHabilidadAdapter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.habilidades.detalle.model.HabilidadAsignadaDetalleModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.habilidades.detalle.presenter.DetalleHabilidadesPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.habilidades.detalle.view.DetalleHabilidadesView
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.informacionhistorica.contenedor.view.InformacionHistoricaRegionFragment
import kotlinx.android.synthetic.main.fragment_conversacion_desarrollo.*

class ConversacionDesarrolloFragment : BaseFragment(),
    FocosTrabajoView, DetalleHabilidadesView,
    GraficosGrView, FocosTrabajoAdapter.FocoTrabajoListener {

    private val presenter by injectFragment<DetalleHabilidadesPresenter>()
    private val graficoPresenter by injectFragment<GraficosGrPresenter>()
    private val focosTrabajoPresenter by injectFragment<FocosTrabajoPresenter>()
    private val recargarHabilidadesAsignadasReceiver = RecargarHabilidadesAsignadasReceiver()
    private var graficoPosicion: Int = 0
    private var personaId: Long = -1L
    private val rol = Rol.GERENTE_REGION

    private val detallesHabilidadAdapter by lazy { DetallesHabilidadAdapter() }

    private val focosTrabajoAdapter by lazy {
        FocosTrabajoAdapter()
            .apply { listener = this@ConversacionDesarrolloFragment }
    }

    private val pantallaCarga by lazy { LoadingDialogFragment() }


    override fun getLayout() = R.layout.fragment_conversacion_desarrollo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recuperarArgs()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inicializar()
    }

    private fun inicializar() {
        registrarRecargarHabilidadesAsignadasReceiver()
        configurarRecyclerView()
        presenter.obtener(personaId, rol)
        focosTrabajoPresenter.obtener(personaId, rol)
        configurarListener()
        cargarFuentes()
    }

    private fun cargarFuentes() {
        if (context == null) return

        textAprendizajes?.text =
            cargarConDistintasFuentes(getString(R.string.pregunta_aprendizajes))
        textFocosTrabajo?.text =
            cargarConDistintasFuentes(getString(R.string.pregunta_focos_trabajo))
        textHabilidades?.text = cargarConDistintasFuentes(getString(R.string.pregunta_habilidades))
        textMensajeFinal?.text =
            cargarConDistintasFuentes(getString(R.string.preguntas_poderosas_mensaje_final))
    }

    private fun cargarConDistintasFuentes(texto: String): SpannableStringBuilder {
        return FontStyler
            .establecerTexto(texto)
            .conContext(requireContext())
            .conDelimitador("**")
            .conFuentePrimaria(TipoFuente.MULISH_BOLD)
            .conFuenteSecundaria(TipoFuente.MULISH_REGULAR)
            .conColorPrimarioDesdeRecurso(R.color.gris_escala_5)
            .procesar()
    }

    private fun configurarListener() {
        llAsignarHabilidades?.setOnClickListener { abrirAsignarHabilidades() }
    }

    private fun abrirAsignarHabilidades() {
        val habilidadesFragment = AsignarHabilidadFragment
            .newInstance(AsignarHabilidadFragment.Request.EditarHabilidadesAOtro(personaId))
        fragmentManager?.let { habilidadesFragment.show(it, habilidadesFragment.tag) }
    }

    private fun registrarRecargarHabilidadesAsignadasReceiver() {
        val filter = IntentFilter(Constant.BROADCAST_RECARGAR_HABILIDADES_EQUIPO)
        activity?.registerReceiver(recargarHabilidadesAsignadasReceiver, filter)
    }

    override fun onDestroyView() {
        activity?.unregisterReceiver(recargarHabilidadesAsignadasReceiver)
        super.onDestroyView()
    }

    override fun onClickFocoTrabajo(posicion: Int) {
        graficoPosicion = posicion
        graficoPresenter.obtener(personaId, rol)
    }

    private fun recuperarArgs() {
        arguments?.let { personaId = it.getLong(PERSONA_ID) }
    }

    override fun cargar(habilidadAsignadaDetalles: List<HabilidadAsignadaDetalleModel>) {
        (rvHabilidades?.adapter as? DetallesHabilidadAdapter)?.actualizar(habilidadAsignadaDetalles)
    }

    override fun mostrarSinHabilidades(mostrar: Boolean) {
        llAsignarHabilidades?.visibility = obtenerVisibilidad(mostrar)
    }

    override fun habilitarAsignacion(habilitar: Boolean) {
        llAsignarHabilidades?.visibility = obtenerVisibilidad(habilitar)
    }

    override fun mostrarDetalleHabilidades(mostrar: Boolean) {
        rvHabilidades?.visibility = obtenerVisibilidad(mostrar)
    }

    override fun graficoNoDescargadosPorConexionInternet() {
        val fragment = ErrorDescargaInformacionDialogFragment.newInstance(
            ErrorDescargaInformacionDialogFragment.TipoMensaje.MENSAJE_GENERICO
        )
        fragment.show(childFragmentManager, "")
    }

    override fun pintar(graficosGr: List<GraficoGrModel>) {
        (rvFocosTrabajo?.adapter as? FocosTrabajoAdapter)?.actualizar(graficosGr)
    }


    override fun mostrarProgressGraficos() {
        pantallaCarga.show(childFragmentManager, null)
    }

    override fun ocultarProgressGraficos() {
        Handler().postDelayed({ pantallaCarga.dismiss() }, 400)

    }

    private fun configurarRecyclerView() {
        val context = context ?: return

        rvHabilidades?.layoutManager = NonScrollableLayoutManager()
            .withContext(context)
            .linearVertical()

        rvHabilidades?.adapter = detallesHabilidadAdapter

        rvFocosTrabajo?.layoutManager = NonScrollableLayoutManager()
            .withContext(context)
            .grid(2)
            .apply { primeraFilaOcupaDosEspacios() }

        rvFocosTrabajo?.adapter = focosTrabajoAdapter
    }

    private fun GridLayoutManager.primeraFilaOcupaDosEspacios() {
        spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (focosTrabajoAdapter.getItemViewType(position)) {
                    FocosTrabajoAdapter.FIRST_ITEM -> 2
                    FocosTrabajoAdapter.SECOND_ITEM -> 1
                    else -> -1
                }
            }
        }
    }

    override fun graficosDescargados() {
        recagarTitulos()
        mostrarGraficos()
    }

    private fun mostrarGraficos() {
        InformacionHistoricaRegionFragment
            .newInstance(personaId)
            .seleccionadoEn(graficoPosicion)
            .show(childFragmentManager, InformacionHistoricaRegionFragment.TAG)
    }

    private fun recagarTitulos() {
        focosTrabajoPresenter.obtener(personaId, rol)
    }

    inner class RecargarHabilidadesAsignadasReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            presenter.obtener(personaId, rol)
        }
    }

    private fun obtenerVisibilidad(seleccionado: Boolean): Int {
        return if (seleccionado) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    companion object {
        private const val PERSONA_ID = "personaId"

        fun newInstance(personaId: Long) =
            ConversacionDesarrolloFragment().withArguments(PERSONA_ID to personaId)
    }

}
