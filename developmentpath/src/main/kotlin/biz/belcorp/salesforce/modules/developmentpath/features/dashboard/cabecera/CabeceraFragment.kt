package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.cabecera

import android.os.Bundle
import android.view.View
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalytics
import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.session.Sesion
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.features.broadcast.SenderEstadoProgress
import biz.belcorp.salesforce.core.utils.gone
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.core.utils.setOnOneClickListener
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.analytics.FirebaseAnalyticsPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.flujo.FlujoCascadaPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.flujo.FlujoMiRutaView
import biz.belcorp.salesforce.modules.developmentpath.features.flujo.Navigator
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view.ErrorDescargaRutaDialogFragment
import kotlinx.android.synthetic.main.fragment_rdd_dashboard_cabecera.btn_back
import kotlinx.android.synthetic.main.fragment_rdd_dashboard_cabecera.btn_ver_mi_ruta
import kotlinx.android.synthetic.main.fragment_rdd_dashboard_cabecera.textInicialesCabecera
import kotlinx.android.synthetic.main.fragment_rdd_dashboard_cabecera.tv_campaign_information
import kotlinx.android.synthetic.main.fragment_rdd_dashboard_cabecera.tv_nombre
import kotlinx.android.synthetic.main.fragment_rdd_dashboard_cabecera.tv_title
import kotlinx.android.synthetic.main.fragment_rdd_dashboard_cabecera.tv_user_role_info
import kotlinx.android.synthetic.main.fragment_rdd_dashboard_cabecera.tv_user_role_sub_info

class CabeceraFragment : BaseFragment(), CabeceraView, FlujoMiRutaView {

    override fun getLayout() = R.layout.fragment_rdd_dashboard_cabecera

    override var navigator: Navigator? = null
    private val cabeceraPresenter: CabeceraPresenter by injectFragment()
    private val flujoCascadaPresenter: FlujoCascadaPresenter by injectFragment()
    private val senderEstadoProgress: SenderEstadoProgress by injectFragment()
    private val firebaseAnalyticsPresenter: FirebaseAnalyticsPresenter by injectFragment()
    private val errorDescargaRutaDialogFragment by lazy { ErrorDescargaRutaDialogFragment() }
    private var planId: Long = -1L

    companion object {

        private const val PLAN_ID = "PLAN_ID"

        fun newInstance(planId: Long): CabeceraFragment {
            val fragment = CabeceraFragment()
            val arguments = Bundle()
            arguments.putLong(PLAN_ID, planId)
            fragment.arguments = arguments
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recuperarArgumentos()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configurarPresenters()

        cabeceraPresenter.obtener(planId)

        btn_back?.setOnClickListener { navigator?.retroceder() }
    }

    private fun configurarPresenters() {
        flujoCascadaPresenter.flujoMiRutaView = this
    }

    override fun cargarNombreCampania(periodoCampania: Campania.Periodo, nombre: String) {
        tv_campaign_information?.text = crearNombreCampaniaParaCabecera(periodoCampania, nombre)
    }

    private fun crearNombreCampaniaParaCabecera(
        periodoCampania: Campania.Periodo,
        nombre: String
    ): String {
        return String.format(
            resources.getString(R.string.rdd_informacion_campania),
            obtenerPeriodo(periodoCampania),
            nombre
        )
    }

    private fun obtenerPeriodo(periodoCampania: Campania.Periodo): String {
        return when (periodoCampania) {
            Campania.Periodo.FACTURACION -> resources.getString(R.string.invoicing)
            Campania.Periodo.VENTA -> resources.getString(R.string.sale)
        }
    }

    override fun cargarRolZonaSeccion(
        gzRolZona: String, rol: Rol?,
        sesion: Sesion
    ) {
        tv_nombre?.text = getString(R.string.rdd_dashboard_title_new, gzRolZona)
        when (rol) {
            Rol.SOCIA_EMPRESARIA -> {
                tv_user_role_info?.text = getString(R.string.rdd_gz_section, sesion.seccion)
                tv_user_role_sub_info?.text =
                    getString(R.string.rdd_se_level, sesion.nivel)
            }

            Rol.GERENTE_ZONA -> {
                tv_user_role_info?.text = getString(R.string.rdd_gz)
                tv_user_role_sub_info?.text =
                    getString(R.string.rdd_gz_zone, sesion.zona)
            }

            Rol.GERENTE_REGION -> {
                tv_user_role_info?.text = getString(R.string.rdd_gr_region, sesion.region)
                tv_user_role_sub_info?.gone()
            }

            else -> {
                tv_user_role_info?.gone()
                tv_user_role_sub_info?.gone()
            }
        }
    }

    override fun cargarIniciales(iniciales: String) {
        textInicialesCabecera?.text = iniciales
        tv_title?.text = getString(R.string.rdd_dashboard_titulo)
    }

    override fun configurarBotonVerMiRuta(planId: Long, rol: Rol) {
        btn_ver_mi_ruta?.setOnOneClickListener {
            firebaseAnalyticsPresenter.enviarEvento(TagAnalytics.EVENTO_VER_MI_RUTA_MAPA)
            flujoCascadaPresenter.irAMiRuta(planId)
        }
    }

    override fun habilitarBotonVerMiRuta() {
        btn_ver_mi_ruta?.isEnabled = true
    }

    override fun deshabilitarBotonVerMiRuta() {
        btn_ver_mi_ruta?.isEnabled = false
    }

    override fun pintarMiRutaEnBoton() {
        btn_ver_mi_ruta?.text = getString(R.string.rdd_dashboard_btn_ver_mi_ruta)
    }

    override fun pintarVerRutaEnBoton() {
        btn_ver_mi_ruta?.text = getString(R.string.rdd_dashboard_btn_ver_ruta)
    }

    private fun recuperarArgumentos() {
        arguments?.let { planId = it.getLong(PLAN_ID) }
    }

    override fun mostrarBotonRetroceder() {
        btn_back?.visibility = View.VISIBLE
    }

    override fun ocultarBotonRetroceder() {
        btn_back?.visibility = View.INVISIBLE
    }

    override fun mostrarErrorConexion() {
        if (!isResumed) return

        errorDescargaRutaDialogFragment.show(childFragmentManager, null)
    }

    override fun irARuta(planId: Long) {
        navigator?.irAMiRuta(planId)
    }

    override fun mostrarMensaje(mensaje: String) {
        toast(mensaje)
    }

    override fun mostrarCargando() {
        senderEstadoProgress.mostrarProgress()
    }

    override fun ocultarCargando() {
        senderEstadoProgress.ocultarProgress()
    }
}
