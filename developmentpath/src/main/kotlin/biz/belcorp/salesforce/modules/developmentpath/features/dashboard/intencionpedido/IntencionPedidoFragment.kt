package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.intencionpedido


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import kotlinx.android.synthetic.main.fragment_intencion_pedido_antiguo.*

class IntencionPedidoFragment : BaseFragment(), IntencionPedidoView {
    override fun getLayout() = R.layout.fragment_intencion_pedido_antiguo

    private var planId: Long = -1

    private val presenter: IntencionPedidoPresenter by injectFragment()

    private val registroVisitaReceiver = RegistroVisitaReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            planId = arguments!!.getLong(ARG_INFO_PLAN_ID)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        suscribirARegistroVisita()
        presenter.obtenerCantidadIntencionPedido(planId)
    }

    override fun onDestroyView() {
        activity?.unregisterReceiver(registroVisitaReceiver)
        super.onDestroyView()
    }

    override fun pintarCantidadIntencionPedido(cantidadIntencionPedido: String) {
        tvCantidadIntencionPedido?.text = cantidadIntencionPedido
    }

    override fun pintarCampania(campania: String) {
        tvTitulo?.text = getString(R.string.intencion_pedido_campania, campania)
    }

    override fun mostrarCargando() {
        pbCargando?.visibility = View.VISIBLE
    }

    override fun ocultarCargando() {
        pbCargando?.visibility = View.GONE
    }

    private fun suscribirARegistroVisita() {
        val filter = IntentFilter(Constant.BROADCAST_REGISTRO_VISITA)
        activity?.registerReceiver(registroVisitaReceiver, filter)
    }

    inner class RegistroVisitaReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            presenter.obtenerCantidadIntencionPedido(planId)
        }
    }

    companion object {

        private const val ARG_INFO_PLAN_ID = "param1"

        @JvmStatic
        fun newInstance(planId: Long) =
                IntencionPedidoFragment().apply {
                    arguments = Bundle().apply {
                        putLong(ARG_INFO_PLAN_ID, planId)
                    }
                }
    }
}
