package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.barranavegacion

import android.os.Bundle
import android.view.View
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.utils.NonScrollableLayoutManager
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalytics
import biz.belcorp.salesforce.core.utils.showOnce
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.plan.InfoPlanRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.unidadadministrativa.UaRegresable
import biz.belcorp.salesforce.modules.developmentpath.features.ConfirmacionDialogFragment
import biz.belcorp.salesforce.modules.developmentpath.features.analytics.FirebaseAnalyticsPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.flujo.Navigator
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.barranavegacion.adapter.*
import kotlinx.android.synthetic.main.fragment_barra_navegacion_rdd.*

class BarraNavegacionRddFragment : BaseFragment(),
                                   BarraNavegacionView,
                                   ExpandirListener,
                                   RegresarUnNivelListener,
                                   SalirListener,
                                   RegresarARaizListener {

    override fun getLayout() = R.layout.fragment_barra_navegacion_rdd

    private val presenter: BarraNavegacionPresenter by injectFragment()

    private val firebaseAnalyticsPresenter: FirebaseAnalyticsPresenter by injectFragment()

    var navigator: Navigator? = null
    private var planId: Long = -1L
    private lateinit var myRol: InfoPlanRdd


    private val confirmacionDialogFragment by lazy {
        val titulo = getString(R.string.rdd_cabecera_confirmacion)
        val ok = getString(R.string.rdd_confirmacion_ok)
        return@lazy ConfirmacionDialogFragment.newInstance(titulo, ok)
    }

    private val adapter by lazy {
        BarraNavegacionAdapter(expandirListener = this,
                               regresarUnNivelListener = this,
                               salirListener = this,
                               regresarARaizListener = this)
    }

    companion object {

        private const val ARG_PLAN_ID = "param1"

        fun newInstance(planId: Long): BarraNavegacionRddFragment {
            val fragment = BarraNavegacionRddFragment()
            val args = Bundle()
            args.putLong(ARG_PLAN_ID, planId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        planId = arguments!!.getLong(ARG_PLAN_ID)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configurarRecycler()
        presenter.configurarBarra(planId)
    }

    private fun configurarRecycler() {
        rvNiveles?.layoutManager = NonScrollableLayoutManager()
                .withContext(requireContext())
                .linearVertical()

        rvNiveles?.adapter = adapter
    }

    override fun cargarBarra(modelo: BarraNavegacionModel) {
        adapter.actualizar(modelo.nivelesVisibles)

    }

    override fun alClickearExpandir() {
        presenter.toggle()
        if(!Constant.EVENTO_VER_MAS){
            Constant.EVENTO_VER_MAS_MENOS = 1
        } else {
            Constant.EVENTO_VER_MAS_MENOS = 0
        }
        firebaseAnalyticsPresenter.enviarRegionAvance(TagAnalytics.EVENTO_VER_MAS_MENOS, myRol, Constant.EVENTO_VER_MAS_MENOS )
    }

    override fun alClickearRegresarUnNivel(modelo: NivelModel.UaRegresable) {
        val regresable =  UaRegresable(modelo.nombreCortoCampania, modelo.tipoUa, modelo.codigoUa, modelo.nombreResponsable, modelo.rolLiderAsociado, Constant.EVENTO_VER_RUTA)
        firebaseAnalyticsPresenter.enviarRegionVerSalirRuta(TagAnalytics.EVENTO_VER_SALIR_AVANCE, regresable)
        navigator?.retroceder()
    }

    override fun alClickearSalir(modelo: NivelModel.Ua) {
        val regresable =  UaRegresable(modelo.nombreCortoCampania, modelo.tipoUa, modelo.codigoUa, modelo.nombreResponsable, modelo.rolLiderAsociado, Constant.EVENTO_SALIR)
        firebaseAnalyticsPresenter.enviarRegionVerSalirRuta(TagAnalytics.EVENTO_VER_SALIR_AVANCE, regresable)
        navigator?.retroceder()
    }

    override fun alClickearRegresarARaiz() {
        mostrarDialogConfirmacion { navigator?.retrocederARaiz() }
        firebaseAnalyticsPresenter.enviarRegionAvance(TagAnalytics.EVENTO_REGRESAR_A_MI_RUTA, myRol, Constant.EVENTO_REGRESAR_RUTA )
    }

    private fun mostrarDialogConfirmacion(accion: () -> Unit) {
        confirmacionDialogFragment.setListener(accion)
        confirmacionDialogFragment.showOnce(childFragmentManager)
    }

    override fun traerRol(rol: InfoPlanRdd) {
        myRol = rol
    }

}

