package biz.belcorp.salesforce.modules.developmentpath.features.rdd.flujo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.features.broadcast.SenderEstadoProgress
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.core.utils.showOnce
import biz.belcorp.salesforce.core.utils.toast
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.ruta.data.SenderCambioRDD
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.kinesis.KinesisTag
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.RddDashboardDvFragment
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.RddDashboardGrFragment
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.RddDashboardGzFragment
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.RddDashboardSeFragment
import biz.belcorp.salesforce.modules.developmentpath.features.flujo.*
import biz.belcorp.salesforce.modules.developmentpath.features.manager.KinesisManagerPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view.ErrorDescargaInformacionDialogFragment
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view.MiRutaContainerFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.PerfilBuilder
import biz.belcorp.salesforce.modules.developmentpath.features.resultadovisitas.ListarResultadoVisitasFragment
import biz.belcorp.salesforce.modules.developmentpath.features.sync.utils.startFullSyncWorker
import biz.belcorp.salesforce.modules.developmentpath.utils.recuperarUA
import biz.belcorp.salesforce.core.R as coreR

class RddFragment : BaseFragment(), Navigator, FlujoDashboardView, FlujoMiRutaView {

    override fun getLayout() = R.layout.fragment_rdd

    private val kinesisPresenter: KinesisManagerPresenter by injectFragment()

    private val flujoRddPresenter: FlujoRddPresenter by injectFragment()

    private val flujoCascadaPresenter: FlujoCascadaPresenter by injectFragment()

    private val senderEstadoProgress: SenderEstadoProgress by injectFragment()

    private val senderCambioRDD: SenderCambioRDD by injectFragment()

    private val clickUaReceiver = ClickUaReceiver()

    private val proveedorMensajePlanInvalido = ProveedorMensajePlanInvalido()

    private var primerDashboardMostrado = false

    private val errorDescargaRutaDialogFragment by lazy {
        ErrorDescargaInformacionDialogFragment.newInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBackPressedDispatcher()
    }

    private fun onBackPressedDispatcher() {
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            val count = fragmentManager?.backStackEntryCount
            if (count == 0) {
                activity?.onBackPressed()
            } else {
                fragmentManager?.popBackStack()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configurarPresenters()
        registerReceivers()
        flujoRddPresenter.decidirQueMostrar()
        startSync()
    }

    private fun startSync() {
        startFullSyncWorker()
    }

    private fun configurarPresenters() {
        flujoCascadaPresenter.flujoDashboardView = this
        flujoCascadaPresenter.flujoMiRutaView = this
        flujoRddPresenter.flujoDashboardView = this
    }

    override fun onDestroyView() {
        unregisterReceivers()
        super.onDestroyView()
    }

    override fun onResume() {
        super.onResume()
        //kinesisPresenter.enviarPantalla(KinesisTag.PANTALLA_RDD)
    }

    override fun mostrarErrorConexion() {
        if (!isResumed) return

        fragmentManager?.let { errorDescargaRutaDialogFragment.show(it, "") }
    }

    override fun irAMiRuta(planId: Long) {
        if (!isResumed) return

        val miRutaFragment = MiRutaContainerFragment.newInstance(planId, this)
        fragmentManager?.also { fm ->
            fm.beginTransaction()
                .setCustomAnimations(coreR.anim.enter, coreR.anim.exit)
                .add(R.id.rddContainer, miRutaFragment, MiRutaContainerFragment.TAG)
                .addToBackStack(null)
                .commit()
        }

    }

    override fun irAPerfil(personIdentifier: PersonIdentifier) {
        PerfilBuilder
            .conParametros(personIdentifier)
            .recuperarFragment()
            .showOnce(childFragmentManager)
    }

    override fun retroceder() {
        activity?.onBackPressed()
    }

    override fun retrocederARaiz() {
        fragmentManager?.also { fm ->
            if (fm.backStackEntryCount > 0) {
                val idRaiz = fm.getBackStackEntryAt(1).id
                fm.popBackStack(idRaiz, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                toast(R.string.dashboard_updated)
            }
        }
    }

    override fun irADashboardDv(planId: Long) {
        val fragment = RddDashboardDvFragment.newInstance(planId)
        fragment.navigator = this

        agregarFragmentVerificandoBackstack(fragment)
    }

    override fun irADashboardGr(planId: Long) {
        val fragment = RddDashboardGrFragment.newInstance(planId)
        fragment.navigator = this

        agregarFragmentVerificandoBackstack(fragment)
    }

    override fun irADashboardGz(planId: Long) {
        val fragment = RddDashboardGzFragment.newInstance(planId)
        fragment.navigator = this

        agregarFragmentVerificandoBackstack(fragment)
    }

    override fun irADashboardSe(planId: Long) {
        val fragment = RddDashboardSeFragment.newInstance(planId)
        fragment.navigator = this

        agregarFragmentVerificandoBackstack(fragment)
    }

    override fun irAListaResultadoVisitasFacturaron() {
        val fragment = ListarResultadoVisitasFragment
            .instanciarTipoFacturaron()
            .apply { navigator = this@RddFragment }

        fragmentManager?.also { fm ->
            fm.beginTransaction()
                .add(R.id.rddContainer, fragment)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun irAListaResultadoVisitasNoFacturaron() {
        val fragment = ListarResultadoVisitasFragment
            .instanciarTipoNoFacturaron()
            .apply { navigator = this@RddFragment }

        fragmentManager?.also { fm ->
            fm.beginTransaction()
                .add(R.id.rddContainer, fragment)
                .addToBackStack(null)
                .commit()
        }
    }

    private fun agregarFragmentVerificandoBackstack(fragment: Fragment) {
        if (!isResumed) return

        fragmentManager?.also { fm ->
            fm.beginTransaction()
                .add(R.id.rddContainer, fragment)
                .apply { verificarSiAgregarABackstack() }
                .commit()
        }
    }

    private fun FragmentTransaction.verificarSiAgregarABackstack() {
        if (primerDashboardMostrado) {
            addToBackStack(null)
        }
        primerDashboardMostrado = true
    }

    override fun irARuta(planId: Long) = irAMiRuta(planId)

    override fun mostrarMensaje(mensaje: String) {
        toast(mensaje)
    }

    override fun mostrarCargando() {
        senderEstadoProgress.mostrarProgress()
    }

    override fun ocultarCargando() {
        senderEstadoProgress.ocultarProgress()
    }

    override fun notificarCambioRDD() {
        senderCambioRDD.notificarCambioEnPlanificacion()
    }

    inner class ClickUaReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {

            val llaveUA = intent.extras?.recuperarUA() ?: return
            flujoCascadaPresenter.verificarDashboardDeOtra(llaveUA)
        }
    }

    override fun mostrarErrorPlanInvalido(rolAsociado: Rol) {
        toast(proveedorMensajePlanInvalido.recuperarRecurso(rolAsociado))
    }

    private fun registerReceivers() {
        val filter = IntentFilter(Constant.BROADCAST_CLICK_SECCION_RDD)
        activity?.registerReceiver(clickUaReceiver, filter)
    }

    private fun unregisterReceivers() {
        activity?.unregisterReceiver(clickUaReceiver)
    }
}
