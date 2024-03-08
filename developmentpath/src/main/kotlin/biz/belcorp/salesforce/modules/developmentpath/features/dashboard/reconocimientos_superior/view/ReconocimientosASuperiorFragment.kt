package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.reconocimientos_superior.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.utils.NonScrollableLayoutManager
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.se.model.ReconocimientoModel
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.reconocimientos_superior.presenter.ReconocimientosASuperiorPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.reconocimientos.superior.view.ReconocimientoASuperiorDetalleFragment
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_reconocimientos_a_superior.*

class ReconocimientosASuperiorFragment :
    BaseFragment(),
    ReconocimientosASuperiorView {

    override fun getLayout() = R.layout.fragment_reconocimientos_a_superior

    private val presenter: ReconocimientosASuperiorPresenter by injectFragment()

    private val adapter = ReconocimientosAdapter()

    private var subscribe: Disposable? = null

    private val receiver = CambioReceiver()

    companion object {
        const val TAG = "ReconocimientoASuperior"

        fun newInstance() = ReconocimientosASuperiorFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inicializar()
    }

    override fun onDestroyView() {
        activity?.unregisterReceiver(receiver)
        subscribe?.dispose()
        super.onDestroyView()
    }

    override fun pintarReconocimientos(reconocimientos: List<ReconocimientoModel>) {
        adapter.actualizar(reconocimientos)
        hacerVisibleReconocimientos()
    }

    private fun registrarReceiver() {
        val filter = IntentFilter(Constant.BROADCAST_CAMBIO_RECONOCIMIENTO_A_SUPERIOR)
        activity?.registerReceiver(receiver, filter)
    }

    override fun pintarNombre(nombre: String) {
        text_nombre_reconocida?.text = nombre
    }

    override fun pintarUnidadAdministrativa(ua: String) {
        text_ua_reconocida?.text = ua
    }

    override fun pintarPlaceholder() {
        layout_reconocimientos_vacios?.visibility = View.VISIBLE
        recycler_reconocimientos?.visibility = View.GONE
    }

    private fun hacerVisibleReconocimientos() {
        layout_reconocimientos_vacios?.visibility = View.GONE
        recycler_reconocimientos?.visibility = View.VISIBLE
    }

    private fun inicializar() {
        registrarReceiver()
        configurarRecycler()
        presenter.recuperar()
        setearSubscriberItem()
    }

    private fun configurarRecycler() {
        recycler_reconocimientos?.layoutManager = NonScrollableLayoutManager()
            .withContext(context)
            .linearVertical()
        recycler_reconocimientos?.adapter = adapter
    }

    private fun setearSubscriberItem() {
        subscribe = adapter.clickObservable.subscribe { lanzarActividadReconocimiento(it) }
    }

    private fun lanzarActividadReconocimiento(modelo: ReconocimientoModel) {
        val fragment = ReconocimientoASuperiorDetalleFragment.newInstance(modelo.idReconocimiento)
        fragmentManager?.let { fragment.show(it, fragment.tag) }
    }

    inner class CambioReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            presenter.recuperar()
        }
    }
}
