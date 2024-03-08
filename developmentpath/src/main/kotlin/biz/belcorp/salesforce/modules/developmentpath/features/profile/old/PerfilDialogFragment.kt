package biz.belcorp.salesforce.modules.developmentpath.features.profile.old

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import biz.belcorp.mobile.components.core.extensions.gone
import biz.belcorp.salesforce.core.base.BaseDialogFragment
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalytics
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.features.utils.lazyPersonIdentifier
import biz.belcorp.salesforce.core.features.utils.withPersonIdentifier
import biz.belcorp.salesforce.core.utils.inject
import biz.belcorp.salesforce.core.utils.safeUnregisterReceiver
import biz.belcorp.salesforce.core.utils.toast
import biz.belcorp.salesforce.core.utils.visible
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.features.analytics.FirebaseAnalyticsPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view.ErrorDescargaInformacionDialogFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.PerfilConfigurable
import biz.belcorp.salesforce.modules.developmentpath.features.profile.PerfilPagerAdapter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.ProfileViewModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.ProfileViewState
import biz.belcorp.salesforce.modules.developmentpath.features.profile.old.cabecera.CabeceraPerfilFragment
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayout.Tab
import kotlinx.android.synthetic.main.fragment_dialog_perfil.*
import org.koin.android.viewmodel.ext.android.viewModel
import kotlin.math.abs


class PerfilDialogFragment : BaseDialogFragment(),
    PerfilConfigurable {

    override fun getLayout(): Int {
        return R.layout.fragment_dialog_perfil
    }

    private var redirigirAAcompaniamiento = false
    private val registroVisitaReceiver = RegistroVisitaReceiver()

    private val viewModel: ProfileViewModel by viewModel()

    private val firebaseAnalyticsPresenter: FirebaseAnalyticsPresenter by inject()

    private val personIdentifier by lazyPersonIdentifier()

    companion object {

        fun newInstance(personIdentifier: PersonIdentifier) = PerfilDialogFragment()
            .withPersonIdentifier(personIdentifier)
    }

    override fun redirigirAAcompaniamiento(): DialogFragment {
        this.redirigirAAcompaniamiento = true
        return this
    }

    private fun suscribirARegistroVisita() {
        val filter = IntentFilter(Constant.BROADCAST_REGISTRO_VISITA)
        activity?.registerReceiver(registroVisitaReceiver, filter)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_cerrar?.setOnClickListener { dismissAllowingStateLoss() }
        initToolbar()
        incrustarFragments()
        setupViewModel()
        configurarTabs()
        suscribirARegistroVisita()
    }

    private fun initToolbar() {
        appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            when {
                abs(verticalOffset) == appBarLayout.totalScrollRange -> tvCabecera?.visible()
                verticalOffset == 0 -> tvCabecera?.gone()
                else -> tvCabecera?.gone()
            }
        })
    }

    private fun incrustarFragments() {
        val cabeceraPerfilFragment =
            CabeceraPerfilFragment.newInstance(personIdentifier)
        childFragmentManager
            .beginTransaction()
            .add(R.id.fl_cabecera, cabeceraPerfilFragment, "cabecera")
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

    override fun onStart() {
        super.onStart()
        fitFullScreen()
    }

    override fun onDestroyView() {
        desuscribirARegistroVisita()
        super.onDestroyView()
    }

    private fun desuscribirARegistroVisita() {
        activity?.safeUnregisterReceiver(registroVisitaReceiver)
    }

    private fun configurarTabs() {
        val adapter = PerfilPagerAdapter(context!!, personIdentifier, childFragmentManager)
            .apply { botonRegistrarVisita = btnRegistrarVisita }
        pager.adapter = adapter
        pager.offscreenPageLimit = 2
        tabs?.setupWithViewPager(pager)
        tabs?.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: Tab) {
                when (tab.position) {
                    0 -> {
                        btnRegistrarVisita?.gone()
                        firebaseAnalyticsPresenter.enviarEvento(TagAnalytics.EVENTO_PERFIL_DATOS)
                    }
                    1 -> {
                        btnRegistrarVisita?.visible()
                    }
                    2 -> btnRegistrarVisita?.gone()
                }
            }

            override fun onTabUnselected(tab: Tab) = Unit

            override fun onTabReselected(tab: Tab) = Unit
        })
        redirigirAAcompaniamientoSiEsNecesario()
    }

    private fun showInfo(name: String) {
        tvCabecera?.text = name
    }

    private fun mostrarErrorConexion(rol: Rol) {
        ErrorDescargaInformacionDialogFragment
            .newInstance(ErrorDescargaInformacionDialogFragment.TipoMensaje.MENSAJE_POR_ROL, rol)
            .show(childFragmentManager, "")
    }

    private fun mostrarErrorDescargaPerfil() {
        toast(R.string.perfil_error_datos)
    }

    private fun redirigirAAcompaniamientoSiEsNecesario() {
        if (redirigirAAcompaniamiento) pager.currentItem = 1
    }

    private fun redirigirAAcuerdos() {
        pager.currentItem = 2
    }

    private inner class RegistroVisitaReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            redirigirAAcuerdos()
        }
    }
}
