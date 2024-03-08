package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.desarrollo.view.habilidades

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import biz.belcorp.salesforce.core.base.BaseDialogFragment
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.core.utils.withArguments
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.gr.model.DesarrolloHabilidadModel
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.gr.presenter.ReconocimientoRegionPresenter
import kotlinx.android.synthetic.main.fragment_desarrollo_ua_habilidades.*

class DesarrolloUaHabilidadesFragment : BaseDialogFragment(), DesarrolloUaHabilidadesView {

    private val presenter: ReconocimientoRegionPresenter by injectFragment()

    override fun getLayout() = R.layout.fragment_desarrollo_ua_habilidades

    private val planId: Long by lazy { arguments?.getLong(PLAN_ID, 0L) ?: 0L }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iniciarPresenter()

        img_back_seccion?.setOnClickListener {
            dismiss()
        }
    }

    private fun iniciarPresenter() {
        presenter.obtenerDatos(planId)
    }

    override fun onStart() {
        super.onStart()
        fitFullScreen()
    }

    override fun showCurrentCampaign(periodoCampania: String) {
        txt_campania?.text = periodoCampania
    }

    override fun showTitle(uaCampaniaAnterior: String) {
        txt_titulo_region?.text = getString(R.string.desarrollo_ua_title, uaCampaniaAnterior)
    }

    override fun showDesarrolloRegion(list: List<DesarrolloHabilidadModel>) {
        rv_percentages?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = DesarrolloHabilidadAdapter(list)
            isNestedScrollingEnabled = false
        }
    }

    override fun onDestroyView() {
        presenter.detachView()
        super.onDestroyView()
    }

    companion object {

        private const val PLAN_ID = "PLAN_ID"

        fun newInstance(planId: Long): DesarrolloUaHabilidadesFragment {
            return DesarrolloUaHabilidadesFragment().withArguments(PLAN_ID to planId)
        }
    }
}
