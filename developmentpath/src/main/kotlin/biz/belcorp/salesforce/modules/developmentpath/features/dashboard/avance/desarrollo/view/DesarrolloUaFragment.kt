package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.desarrollo.view

import android.os.Bundle
import android.view.View
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.core.utils.withArguments
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalytics
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.plan.InfoPlanRdd
import biz.belcorp.salesforce.modules.developmentpath.features.analytics.FirebaseAnalyticsPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.desarrollo.presenter.DesarrolloUaPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.desarrollo.view.comportamientos.DesarrolloUaComportamientosFragment
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.desarrollo.view.habilidades.DesarrolloUaHabilidadesFragment
import kotlinx.android.synthetic.main.fragment_desarrollo_ua.*
import kotlinx.android.synthetic.main.layout_desarrollo_dv.*

class DesarrolloUaFragment : BaseFragment(), DesarrolloUaView {

    companion object {

        private const val PLAN_ID = "PLAN_ID"

        fun newInstance(planId: Long): DesarrolloUaFragment {
            return DesarrolloUaFragment().withArguments(PLAN_ID to planId)
        }
    }

    override fun getLayout() = R.layout.fragment_desarrollo_ua

    private val presenter: DesarrolloUaPresenter by injectFragment()
    private val firebaseAnalyticsPresenter: FirebaseAnalyticsPresenter by injectFragment()

    private var planId = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recuperarArgumentos()
    }

    override fun onResume() {
        super.onResume()
        presenter.recuperar(planId)
    }

    private fun recuperarArgumentos() {
        arguments?.apply {
            planId = getLong(PLAN_ID)
        }
    }

    override fun pintarTexto(uA: String, campania: String) {
        text_desarrollo?.text = getString(R.string.desarrollo_ua_avance, uA, campania)
    }

    override fun irADesarrolloComportamientosAlHacerClickEnCard() {
        card_desarrollo_ua?.setOnClickListener {
            abrirDesarrolloComportamientos()
        }
    }

    private fun abrirDesarrolloComportamientos() {
        val fragment = DesarrolloUaComportamientosFragment.newInstance(planId)
        fragment.show(childFragmentManager, fragment.tag)
    }

    override fun irADesarrolloRegionAlHacerClickEnCard(infoPlan: InfoPlanRdd) {
        card_desarrollo_ua?.setOnClickListener {
            tagAnalyticsdesarrollo(infoPlan)
            abrirDesarrolloUa()
        }
    }

    override fun irADesarrolloPaisAlHacerClickEnCard() {
        text_desarrollo_dv?.text = getString(R.string.titulo_desarrollo_habilidades)
        card_desarrollo_ua.visibility = View.GONE
        constraintLayout_desarrollo_dv?.visibility = View.VISIBLE
        constraintLayout_desarrollo_dv?.setBackgroundResource(R.drawable.shadow_rdd)
        constraintLayout_desarrollo_dv?.setOnClickListener {
            abrirDesarrolloUa()
        }
    }

    private fun abrirDesarrolloUa() {
        DesarrolloUaHabilidadesFragment
            .newInstance(planId)
            .show(childFragmentManager, "")
    }

    private fun tagAnalyticsdesarrollo(infoPlan: InfoPlanRdd) {
        firebaseAnalyticsPresenter.enviarRegionAvance(
            TagAnalytics.EVENTO_SELECCION_DESARROLLO,
            infoPlan,
            0
        )
    }
}
