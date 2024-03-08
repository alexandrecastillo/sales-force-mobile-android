package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.dv.avanceregiones.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.utils.NonScrollableLayoutManager
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.dv.avanceregiones.model.RegionModel
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.dv.avanceregiones.presenter.AvanceRegionesPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.broadcast.SenderClickUaRdd
import kotlinx.android.synthetic.main.fragment_avance_regiones.*

class AvanceRegionesFragment : BaseFragment(), AvanceRegionesView {

    override fun getLayout() = R.layout.fragment_avance_regiones

    val presenter: AvanceRegionesPresenter by injectFragment()
    val senderClickUaRdd: SenderClickUaRdd by injectFragment()
    private val regionesAdapter by lazy { instanciarRegionesAdapter() }
    private val cambioRDDReceiver = CambioRDDReceiver()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_avance_regiones, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        suscribirBroadcastReceiver()
        configurarRecycler()
        presenter.recuperarAvance()
    }

    private fun suscribirBroadcastReceiver() {
        val filter = IntentFilter(Constant.BROADCAST_CAMBIO_PLANIFICACION_RDD)
        activity?.registerReceiver(cambioRDDReceiver, filter)
    }

    private fun instanciarRegionesAdapter(): AvanceRegionesAdapter {
        return AvanceRegionesAdapter().apply {
            establecerClickListener { planId ->
                senderClickUaRdd.clickearUa(planId)
            }
        }
    }

    private fun configurarRecycler() {
        recyclerRegiones?.layoutManager = NonScrollableLayoutManager()
                .withContext(context)
                .linearVertical()

        recyclerRegiones?.adapter = regionesAdapter
    }

    override fun onDestroyView() {
        desuscribirBroadcastReceiver()
        super.onDestroyView()
    }

    private fun desuscribirBroadcastReceiver() {
        activity?.unregisterReceiver(cambioRDDReceiver)
    }

    override fun pintarRegiones(regiones: List<RegionModel>) {
        regionesAdapter.actualizar(regiones)
    }

    inner class CambioRDDReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            presenter.recuperarAvance()
        }
    }

    companion object {

        const val PLAN_ID = "PLAN_ID"

        fun newInstance(): AvanceRegionesFragment {
            return AvanceRegionesFragment()
        }
    }
}
