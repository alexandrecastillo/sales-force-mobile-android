package biz.belcorp.salesforce.modules.developmentpath.features.profile

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import biz.belcorp.salesforce.core.base.BaseDialogFragment
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalytics
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.features.utils.lazyPersonIdentifier
import biz.belcorp.salesforce.core.features.utils.withPersonIdentifier
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.core.utils.isCO
import biz.belcorp.salesforce.core.utils.isSE
import biz.belcorp.salesforce.core.utils.onOffsetChanged
import biz.belcorp.salesforce.core.utils.onTabSelected
import biz.belcorp.salesforce.core.utils.toast
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant.BROADCAST_REGISTRO_VISITA
import biz.belcorp.salesforce.modules.developmentpath.features.analytics.FirebaseAnalyticsPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view.ErrorDescargaInformacionDialogFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.reconocimiento.MostrarReconocimientoPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.reconocimiento.MostrarReconocimientoView
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.comportamientos.reconocer.ReconocerComportamientosFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.header.PerfilCabeceraFragment
import kotlinx.android.synthetic.main.fragment_perfil.appBarLayout
import kotlinx.android.synthetic.main.fragment_perfil.btnRegistrarVisita
import kotlinx.android.synthetic.main.fragment_perfil.btn_cerrar
import kotlinx.android.synthetic.main.fragment_perfil.imgIndicadorCabecera
import kotlinx.android.synthetic.main.fragment_perfil.pager
import kotlinx.android.synthetic.main.fragment_perfil.tabLayout
import kotlinx.android.synthetic.main.fragment_perfil.tvCabecera
import org.koin.android.viewmodel.ext.android.sharedViewModel
import kotlin.math.abs

class PerfilFragment : BaseDialogFragment(), PerfilConfigurable,
    MostrarReconocimientoView {

    private val mostrarReconocimientoPresenter by injectFragment<MostrarReconocimientoPresenter>()
    private val firebaseAnalyticsPresenter by injectFragment<FirebaseAnalyticsPresenter>()
    private val viewModel by sharedViewModel<ProfileViewModel>(from = { this })
    private val personIdentifier by lazyPersonIdentifier()

    private var redirigirAAcompaniamiento = false

    private val registroVisitaReceiver = RegistroVisitaReceiver()

    private val cabeceraPerfilFragment by lazy {
        PerfilCabeceraFragment.newInstance(personIdentifier).also {
            it.txtNombreCabecera = tvCabecera
            it.imgIndicador = imgIndicadorCabecera
        }
    }

    private val reconocimientoFragment by lazy {
        ReconocerComportamientosFragment.newInstance(personIdentifier.id, personIdentifier.role)
    }

    override fun redirigirAAcompaniamiento(): PerfilFragment {
        this.redirigirAAcompaniamiento = true
        return this
    }

    override fun getLayout(): Int {
        return R.layout.fragment_perfil
    }

    override fun onStart() {
        super.onStart()
        fitFullScreen()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_cerrar?.setOnClickListener { dismissAllowingStateLoss() }
        initToolbar()
        inscrustarCabecera()
        setupViewModel()
        configurarTabs()
        suscribirARegistroVisita()
    }

    private fun initToolbar() {
        appBarLayout?.onOffsetChanged { verticalOffset ->
            when (appBarLayout?.totalScrollRange) {
                abs(verticalOffset) -> {
                    tvCabecera?.visibility = View.VISIBLE
                    imgIndicadorCabecera?.visibility = View.VISIBLE
                }

                else -> {
                    tvCabecera?.visibility = View.GONE
                    imgIndicadorCabecera?.visibility = View.GONE
                }
            }
        }
    }

    private fun inscrustarCabecera() {
        childFragmentManager
            .beginTransaction()
            .replace(R.id.fl_cabecera, cabeceraPerfilFragment)
            .commit()
    }

    private fun setupViewModel() {
        viewModel.viewState.observe(viewLifecycleOwner, viewStateObserver)
        viewModel.getInfo(personIdentifier)
    }

    private val viewStateObserver = Observer<ProfileViewState> {
        when (it) {
            is ProfileViewState.Success -> showInfo(it.name)
            is ProfileViewState.ShowNetworkError -> mostrarErrorConexion(it.role)
            is ProfileViewState.ShowError -> mostrarErrorDescargaPerfil()
        }
    }

    private fun configurarTabs() {
        val adapter =
            PerfilPagerAdapter(context!!, personIdentifier, childFragmentManager)
                .apply { botonRegistrarVisita = btnRegistrarVisita }
        pager?.adapter = adapter
        pager?.offscreenPageLimit = Constant.NUMBER_THREE
        tabLayout?.tabs?.setupWithViewPager(pager)
        tabLayout?.tabs?.onTabSelected { tab ->
            if (personIdentifier.role.isCO()) {
                when (tab.position) {
                    Constant.ZERO_NUMBER -> {
                        btnRegistrarVisita?.visibility = View.GONE
                        enviarEventoFirebase()
                    }

                    Constant.NUMBER_ONE -> btnRegistrarVisita?.visibility = View.GONE
                    Constant.NUMBER_TWO -> btnRegistrarVisita?.visibility = View.VISIBLE
                    Constant.NUMBER_THREE -> btnRegistrarVisita?.visibility = View.GONE
                    Constant.NUMBER_FOUR -> btnRegistrarVisita?.visibility = View.GONE
                }
            } else if (personIdentifier.role.isSE()) {
                when (tab.position) {
                    Constant.ZERO_NUMBER -> {
                        btnRegistrarVisita?.visibility = View.GONE
                        enviarEventoFirebase()
                    }

                    Constant.NUMBER_ONE -> btnRegistrarVisita?.visibility = View.GONE
                    Constant.NUMBER_TWO -> btnRegistrarVisita?.visibility = View.VISIBLE
                    Constant.NUMBER_THREE -> btnRegistrarVisita?.visibility = View.GONE
                }
            } else {
                when (tab.position) {
                    Constant.ZERO_NUMBER -> {
                        btnRegistrarVisita?.visibility = View.GONE
                        enviarEventoFirebase()
                    }

                    Constant.NUMBER_ONE -> btnRegistrarVisita?.visibility = View.VISIBLE
                    Constant.NUMBER_TWO -> btnRegistrarVisita?.visibility = View.GONE
                }
            }
        }
        redirigirAAcompaniamientoSiEsNecesario()
    }

    private fun showInfo(name: String) {
        tvCabecera?.text = name
    }

    private fun enviarEventoFirebase() {
        firebaseAnalyticsPresenter.enviarPantallaPerfil(
            TagAnalytics.PERFIL_DATOS,
            personIdentifier.role,
            personIdentifier.id
        )
    }

    private fun redirigirAAcompaniamientoSiEsNecesario() {
        if (redirigirAAcompaniamiento) {
            when (personIdentifier.role) {
                Rol.CONSULTORA,
                Rol.SOCIA_EMPRESARIA -> pager.currentItem = 2

                else -> pager.currentItem = 1
            }
        }
    }

    private fun suscribirARegistroVisita() {
        val filter = IntentFilter(BROADCAST_REGISTRO_VISITA)
        activity?.registerReceiver(registroVisitaReceiver, filter)
    }

    override fun onDestroyView() {
        desuscribirARegistroVisita()
        super.onDestroyView()
    }

    private fun desuscribirARegistroVisita() {
        try {
            activity?.unregisterReceiver(registroVisitaReceiver)
        } catch (ex: Exception) {
            println(ex)
        }
    }

    private inner class RegistroVisitaReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            redirigirAAcuerdos()
            mostrarReconocimientoSegunVisitasRegistradas()
        }
    }

    private fun redirigirAAcuerdos() {
        pager.currentItem = 2
    }

    private fun mostrarReconocimientoSegunVisitasRegistradas() {
        mostrarReconocimientoPresenter.validarYMostrar(personIdentifier.id, personIdentifier.role)
    }

    override fun mostrarReconocimientoComportamientos() {
        if (!isResumed) return
        reconocimientoFragment.show(childFragmentManager, null)
    }

    override fun mostrarReconocimientoHabilidades() = Unit

    override fun mostrarMensaje(mensaje: String) {
        toast(mensaje)
    }

    companion object {

        fun newInstance(personIdentifier: PersonIdentifier) = PerfilFragment()
            .withPersonIdentifier(personIdentifier)
    }

    private fun mostrarErrorConexion(rol: Rol) {
        ErrorDescargaInformacionDialogFragment
            .newInstance(ErrorDescargaInformacionDialogFragment.TipoMensaje.MENSAJE_POR_ROL, rol)
            .show(childFragmentManager, "")
    }

    private fun mostrarErrorDescargaPerfil() {
        toast(R.string.perfil_error_datos)
    }

}
