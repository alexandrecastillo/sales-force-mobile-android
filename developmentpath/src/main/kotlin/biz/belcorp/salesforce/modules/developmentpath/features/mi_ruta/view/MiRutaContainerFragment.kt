package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import biz.belcorp.salesforce.core.base.ScopedFragment
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalytics
import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.events.EventSubject
import biz.belcorp.salesforce.core.events.EventSubject.DEVELOPMENT_PATH_DASHBOARD_SYNC
import biz.belcorp.salesforce.core.events.EventSubject.DEVELOPMENT_PATH_LEGACY_SYNC
import biz.belcorp.salesforce.core.events.sync.SyncState
import biz.belcorp.salesforce.core.events.utils.consumableObserver
import biz.belcorp.salesforce.core.events.utils.observeEventSubject
import biz.belcorp.salesforce.core.features.permissions.PermissionsUtil
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.common.broadcast.SenderIrAUbicacion
import biz.belcorp.salesforce.modules.developmentpath.common.broadcast.SenderVisibilidadMenu
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant.BROADCAST_IR_A_AGREGAR_EVENTO_FECHA_PARAM
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant.BROADCAST_IR_A_PERFIL_PARAM
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant.BROADCAST_IR_A_VER_DETALLE_EVENTO_ID_PARAM
import biz.belcorp.salesforce.modules.developmentpath.features.analytics.FirebaseAnalyticsPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.flujo.Navigator
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.barranavegacion.BarraNavegacionRddFragment
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.di.MI_RUTA_SCOPE
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.di.RDD_PRESENTER
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.AgregarEventoFragment
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.detalle.view.DetalleEventoFragment
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.model.CabeceraViewModel
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.model.CalendarioViewModel
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.model.ListaMarcadores
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.model.MenuPersonaModel
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.presenter.RddPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view.calendario.CalendarioView
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view.calendario.MiRutaCalendarView
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view.mapa.ListenerAccionesEnMapa
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view.mapa.MiRutaMapaFragment
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view.mapa.MiRutaMapaUnElementoFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.PerfilBuilder
import biz.belcorp.salesforce.modules.developmentpath.utils.PlayServicesUtils
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.fragment_mi_ruta_container.*
import kotlinx.android.synthetic.main.layout_cabecera_misma_persona_rdd.*
import java.util.*

class MiRutaContainerFragment : ScopedFragment(),
    CabeceraView,
    ListenerAccionesEnMapa,
    CalendarioView,
    ContainerRddView {

    override fun getLayout() = R.layout.fragment_mi_ruta_container

    override fun getScopeName() = MI_RUTA_SCOPE

    private val contentFragment by lazy { MiRutaContentFragment() }
    private var mapFragment: MiRutaMapaFragment? = null
    private var numeroTemporal: String? = null

    private val presenter by injectScoped<RddPresenter>(MI_RUTA_SCOPE, RDD_PRESENTER)

    private val analyticsPresenter: FirebaseAnalyticsPresenter by injectFragment()

    private var diaDePausa = Calendar.getInstance()

    private val sender: SenderVisibilidadMenu by injectFragment()

    private val recalculoReceiver = RecalculoReceiver()
    private val irAUbicarPersonaReceiver = IrAUbicarPersonaReceiver()
    private val irAPerfilReceiver = IrAPerfilReceiver()
    private val irAAgregarEventoReceiver = IrAAgregarEventoReceiver()
    private val irAVerDetalleEventoReceiver = IrAVerDetalleEventoReceiver()
    private val recargarVistaReceiver = RecargarVistaReceiver()

    private var ubicarFragment: MiRutaMapaUnElementoFragment? = null

    private var esDeUbicarUnaSolaPersona = false

    private var planId: Long = -1L

    var navigator: Navigator? = null

    companion object {

        const val TAG = "MiRuta"

        private const val ARG_PLAN_ID = "CONSTRUCTOR"

        fun newInstance(planId: Long, navigator: Navigator): MiRutaContainerFragment {
            val args = Bundle()
            val fragment = MiRutaContainerFragment()
            args.putLong(ARG_PLAN_ID, planId)
            fragment.arguments = args
            fragment.navigator = navigator

            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSyncObservers()
        configurarPresenter()
        presenter.recalcularPlan()
        analyticsPresenter.enviarPantalla(TagAnalytics.MI_RUTA_DESARROLLO_LISTADO, planId)
        registrarReceivers()
        configurarBotonesCabecera()
        escucharMapaSwitch()
        calendario.seleccionarDiaListener = (object : MiRutaCalendarView.SeleccionarDiaListener {
            override fun alSeleccionar(indice: Int) {
                presenter.seleccionar(indice)
            }
        })
        agregarBarraDeNavegacionAContainer()
        agregarContenidoMiRutaAContainer(contentFragment)
        escucharAccionRecargarVista()
    }

    private fun setupSyncObservers() {
        observeEventSubject(
            subjects = *arrayOf(
                DEVELOPMENT_PATH_DASHBOARD_SYNC,
                DEVELOPMENT_PATH_LEGACY_SYNC
            ),
            observer = syncStateObserver
        )
    }

    override fun setupSyncObservers(subjects: Array<EventSubject>) {
        if (!isAdded) return
        observeEventSubject(
            subjects = *subjects,
            observer = syncStateObserver
        )
    }

    private fun escucharAccionRecargarVista() {
        customSnackbar?.onClicEnAccion {
            presenter.recalcularPlan()
        }
    }

    override fun ocultarSnackbar() {
        customSnackbar?.visibility = View.GONE
    }

    private fun registrarReceivers() {
        registrarCambioPlanificacionBroadcast()
        registrarIrAUbicacionBroadcast()
        registrarIrAPerfilBroadcast()
        registrarIrAAgregarEventoBroadcast()
        registrarIrAVerDetalleEventoBroadcast()
        registrarRecargarVistaBroadcast()
    }

    private fun configurarPresenter() {
        presenter.cabeceraView = this
        presenter.calendarioView = this
        presenter.containerRddView = this
        establecerPlanId()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recuperarValores()
    }

    private fun recuperarValores() {
        planId = arguments!!.getLong(ARG_PLAN_ID)
    }

    override fun onResume() {
        super.onResume()
        if (!diaDePausa.es(Calendar.getInstance())) {
            presenter.recalcularPlan()
        }
        decidirVisibilidadMenu()
    }

    override fun onPause() {
        super.onPause()
        diaDePausa = Calendar.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_mi_ruta_container, container, false)
    }

    private fun configurarBotonesCabecera() {
        btn_back.setOnClickListener { navigator?.retroceder() }
        btn_mes_sig.setOnClickListener { presenter.mesSiguiente() }
        btn_mes_prev.setOnClickListener { presenter.mesAnterior() }
    }

    private fun registrarCambioPlanificacionBroadcast() {
        val filter = IntentFilter(Constant.BROADCAST_CAMBIO_PLANIFICACION_RDD)
        activity?.registerReceiver(recalculoReceiver, filter)
    }

    private fun registrarIrAUbicacionBroadcast() {
        val filter = IntentFilter(Constant.BROADCAST_IR_A_UBICAR_PERSONA)
        activity?.registerReceiver(irAUbicarPersonaReceiver, filter)
    }

    private fun registrarIrAPerfilBroadcast() {
        val filter = IntentFilter(Constant.BROADCAST_IR_A_PERFIL)
        activity?.registerReceiver(irAPerfilReceiver, filter)
    }

    private fun registrarIrAAgregarEventoBroadcast() {
        val filter = IntentFilter(Constant.BROADCAST_IR_A_AGREGAR_EVENTO)
        activity?.registerReceiver(irAAgregarEventoReceiver, filter)
    }

    private fun registrarIrAVerDetalleEventoBroadcast() {
        val filter = IntentFilter(Constant.BROADCAST_IR_A_VER_DETALLE_EVENTO)
        activity?.registerReceiver(irAVerDetalleEventoReceiver, filter)
    }

    private fun registrarRecargarVistaBroadcast() {
        val filter = IntentFilter(Constant.BROADCAST_RECARGAR_VISTA_PLAN)
        activity?.registerReceiver(recargarVistaReceiver, filter)
    }

    private fun establecerPlanId() = presenter.establecerPlanId(planId)

    override fun cargar(modelo: CalendarioViewModel) {
        calendario?.cargar(modelo)
    }

    override fun onDestroyView() {
        activity?.unregisterReceiver(recalculoReceiver)
        activity?.unregisterReceiver(irAUbicarPersonaReceiver)
        activity?.unregisterReceiver(irAPerfilReceiver)
        activity?.unregisterReceiver(irAAgregarEventoReceiver)
        activity?.unregisterReceiver(irAVerDetalleEventoReceiver)
        activity?.unregisterReceiver(recargarVistaReceiver)
        super.onDestroyView()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PermissionsUtil.REQUEST_CALL_ID -> {
                if (grantResults.isEmpty()) return
                if (isPermissionGranted(grantResults.first())) {
                    numeroTemporal?.apply { llamarANumero(this) }
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun agregarBarraDeNavegacionAContainer() {
        val fragment = BarraNavegacionRddFragment.newInstance(planId)
        fragment.navigator = navigator

        childFragmentManager
            .beginTransaction()
            .add(R.id.fl_barra_navegacion, fragment)
            .commit()
    }

    private fun agregarContenidoMiRutaAContainer(fragment: MiRutaContentFragment) {
        childFragmentManager
            .beginTransaction()
            .add(R.id.fl_container, fragment)
            .commit()
    }

    override fun cargar(modelo: CabeceraViewModel) {
        tv_campaign_information?.text = String.format(
            resources.getString(R.string.rdd_informacion_campania),
            obtenerPeriodo(modelo),
            modelo.nombreCortoCampania
        )

        tv_titulo_fecha_lista?.text = modelo.tituloFechaLista
        tv_subtitulo_fecha_lista?.text = modelo.subTituloFechaLista
        tv_titulo_fecha_mapa?.text = modelo.tituloFechaMapa
        tv_subtitulo_fecha_mapa?.text = modelo.subTituloFechaMapa

        btn_mes_prev?.visibility = visibilidadBoton(modelo.btnMesAnteriorHabilitado)
        btn_mes_sig?.visibility = visibilidadBoton(modelo.btnMesSiguienteHabilitado)
        container_switch_ruta?.visibility = visibilidadSwitch(modelo.mostrarMapa)
        btn_back?.visibility = visibilidadBoton(modelo.mostrarBotonRetroceder)
    }

    private fun obtenerPeriodo(modelo: CabeceraViewModel): String {
        return when (modelo.periodo) {
            Campania.Periodo.FACTURACION -> resources.getString(R.string.invoicing)
            Campania.Periodo.VENTA -> resources.getString(R.string.sale)
        }
    }

    private fun visibilidadBoton(habilitado: Boolean): Int {
        return if (habilitado) {
            View.VISIBLE
        } else {
            View.INVISIBLE
        }
    }

    private fun visibilidadSwitch(mostrarMapa: Boolean): Int {
        return if (mostrarMapa) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    override fun alCargarMapa() = presenter.cargarMapa()

    override fun alBuscarPersonasCerca(marcadores: ListaMarcadores) =
        presenter.buscarPersonasCerca(marcadores)

    private fun llamarANumero(numero: String) {
        val callIntent = Intent(Intent.ACTION_CALL)
        callIntent.data = Uri.parse("tel:$numero")
        startActivity(callIntent)
    }

    fun irAUbicarPersona(modelo: MenuPersonaModel) {
        esDeUbicarUnaSolaPersona = true
        instanciarUbicarFragment(modelo)
        switch_ruta.isChecked = true
    }

    private fun escucharMapaSwitch() {
        if (activity == null) return

        switch_ruta?.setOnCheckedChangeListener { _, isChecked ->

            if (isChecked && !PlayServicesUtils.checkPlayServices(activity!!)) {
                switch_ruta.isChecked = false
            } else {
                cambiarHeaderSwitch(isChecked)
                decidirVisibilidadMenu()
                if (isChecked)
                    irHaciaMapa()
                else
                    volverDeMapa()
            }
        }
    }

    private fun irHaciaMapa() {
        if (!esDeUbicarUnaSolaPersona) {
            analyticsPresenter.enviarPantalla(TagAnalytics.MI_RUTA_MAPA, planId)
            analyticsPresenter.enviarEvento(TagAnalytics.EVENTO_SWITCH_MAPA)
            if (mapFragment == null)
                instanciarMapFragment()
            else
                mostrarMapFragment()
        }
    }

    private fun volverDeMapa() {
        if (esDeUbicarUnaSolaPersona) {
            eliminarFragment()
            esDeUbicarUnaSolaPersona = false
        } else {
            ocultarMapFragment()
            analyticsPresenter.enviarEvento(TagAnalytics.EVENTO_SWITCH_LISTA)
        }
        ubicarFragment = null
    }

    private fun instanciarUbicarFragment(modelo: MenuPersonaModel) {
        ubicarFragment = MiRutaMapaUnElementoFragment.newInstance(Rol.CONSULTORA)
        presenter.mapaUbicarView = ubicarFragment
        ubicarFragment?.persona = modelo.enMapaModel
        ubicarFragment?.acciones = this
        agregarFragment(ubicarFragment!!)
    }

    private fun instanciarMapFragment() {
        mapFragment = MiRutaMapaFragment.newInstance(Rol.CONSULTORA, planId)
        mapFragment?.acciones = this
        presenter.mapaView = mapFragment
        agregarFragment(mapFragment as Fragment)
    }

    private fun agregarFragment(fragment: Fragment) {
        if (!isResumed) return

        childFragmentManager
            .beginTransaction()
            .add(R.id.fl_container, fragment)
            .commit()
    }

    private fun mostrarMapFragment() {
        if (!isResumed) return

        childFragmentManager
            .beginTransaction()
            .show(mapFragment ?: return)
            .commit()
        mapFragment?.refrescarMapa()
    }

    private fun ocultarMapFragment() {
        if (!isResumed) return

        mapFragment?.also {
            childFragmentManager
                .beginTransaction()
                .hide(it)
                .commit()
        }

    }

    private fun eliminarFragment() {
        if (!isResumed) return

        presenter.mapaUbicarView = null
        ubicarFragment?.also {
            childFragmentManager
                .beginTransaction()
                .remove(it)
                .commit()
        }
    }

    /** Método para cambiar el header de acuerdo al switch  mapa/lista */
    private fun cambiarHeaderSwitch(mapaSeleccionado: Boolean) {
        cambiarComportamientoLayout(mapaSeleccionado)
        cambiarVisibilidades(mapaSeleccionado)
    }

    private fun cambiarComportamientoLayout(mapaSeleccionado: Boolean) {
        val params = abl_layout.layoutParams as AppBarLayout.LayoutParams

        if (mapaSeleccionado) {
            params.scrollFlags = 0
        } else {
            params.scrollFlags =
                AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or
                    AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
        }
    }

    private fun cambiarVisibilidades(mapaSeleccionado: Boolean) {
        if (mapaSeleccionado) {
            ll_fecha_lista.visibility = View.GONE
            ll_fecha_mapa.visibility = View.VISIBLE
            calendario.visibility = View.GONE
        } else {
            ll_fecha_lista.visibility = View.VISIBLE
            ll_fecha_mapa.visibility = View.GONE
            calendario.visibility = View.VISIBLE
        }
    }

    private fun decidirVisibilidadMenu() {
        if (switch_ruta.isChecked)
            sender.ocultarMenuPrincipal()
        else
            sender.mostrarMenuPrincipal()
    }

    inner class RecalculoReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            presenter.recalcularPlan()
        }
    }

    inner class IrAUbicarPersonaReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val model =
                intent.getSerializableExtra(SenderIrAUbicacion.ARG_MODEL) as MenuPersonaModel
            irAUbicarPersona(model)
        }
    }

    inner class IrAPerfilReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val identificador =
                intent.getSerializableExtra(BROADCAST_IR_A_PERFIL_PARAM) as PersonIdentifier
            redirigirAPerfil(identificador)
        }
    }

    inner class IrAAgregarEventoReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val fecha =
                intent.getSerializableExtra(BROADCAST_IR_A_AGREGAR_EVENTO_FECHA_PARAM) as Date

            if (!isResumed) return

            AgregarEventoFragment
                .instanciarParaCrear(planId, fecha)
                .show(childFragmentManager, null)
        }
    }

    inner class IrAVerDetalleEventoReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val eventoXUaId = intent.getLongExtra(BROADCAST_IR_A_VER_DETALLE_EVENTO_ID_PARAM, -1L)

            if (!isResumed) return

            DetalleEventoFragment
                .newInstance(eventoXUaId)
                .show(childFragmentManager, null)
        }
    }


    inner class RecargarVistaReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            mostrarRecargarVistaSnackbar()
        }
    }

    private fun mostrarRecargarVistaSnackbar() {
        customSnackbar?.visibility = View.VISIBLE
    }

    fun redirigirAPerfil(personIdentifier: PersonIdentifier) {
        if (!isResumed) return
        PerfilBuilder
            .conParametros(personIdentifier)
            .redirigirAAcompaniamiento()
            .recuperarFragment()
            .showOnce(childFragmentManager)
    }

    override fun onDestroy() {
        super.onDestroy()
        sender.mostrarMenuPrincipal()
    }

    private val syncStateObserver = consumableObserver<SyncState.Updated> {
        presenter.recalcularPlan()
    }

}
