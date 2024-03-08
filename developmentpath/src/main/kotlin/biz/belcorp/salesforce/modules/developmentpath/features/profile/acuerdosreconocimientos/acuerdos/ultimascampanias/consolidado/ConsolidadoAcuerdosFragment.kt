package biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.acuerdos.ultimascampanias.consolidado

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.core.utils.withArguments
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import kotlinx.android.synthetic.main.item_acuerdos_single.*
import biz.belcorp.salesforce.base.R as BaseR

class ConsolidadoAcuerdosFragment : BaseFragment(), ConsolidadoAcuerdosView {

    private var personaId = -1L
    private lateinit var rol: Rol

    private val adapter by lazy { ConsolidadoAcuerdosAdapter() }
    private val presenter by injectFragment<ConsolidadoAcuerdosPresenter>()
    private val cambioAcuerdosReceiver = CambioAcuerdosReceiver()

    override fun getLayout(): Int = R.layout.item_acuerdos_single

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recuperarArgumentos()
    }

    private fun recuperarArgumentos() {
        arguments?.let {
            personaId = it.getLong(ARG_PERSONA_ID)
            rol = it.getSerializable(ARG_ROL) as Rol
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configurarYEstablecerTextos(view)
        configurarYEstablecerIcono(view)
        configurarRecyclerView(view)
        recuperarConsolidado()
        suscribirACambioEnAcuerdos()
    }

    private fun configurarYEstablecerIcono(view: View) {
        image_icon.setColorFilter(
            ContextCompat.getColor(view.context, BaseR.color.magenta),
            PorterDuff.Mode.SRC_IN
        )
        image_icon.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.ic_acuerdos))
    }

    private fun configurarYEstablecerTextos(view: View) {
        text_header.text = view.context.getString(R.string.acuerdos_consolidado_titulo)
        text_titulo.text = view.context.getString(R.string.acuerdos_consolidado_subtitulo)
        text_subtitulo.visibility = View.GONE
    }

    private fun configurarRecyclerView(view: View) {
        recycler.layoutManager = LinearLayoutManager(
            view.context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        recycler.addItemDecoration(ConsolidadoAcuerdosDecorator())
        recycler.adapter = adapter
    }

    private fun suscribirACambioEnAcuerdos() {
        val filter = IntentFilter(Constant.BROADCAST_CAMBIO_ACUERDOS)
        activity?.registerReceiver(cambioAcuerdosReceiver, filter)
    }

    override fun onDestroyView() {
        desuscribirDeCambioAcuerdos()
        super.onDestroyView()
    }

    private fun desuscribirDeCambioAcuerdos() = activity?.unregisterReceiver(cambioAcuerdosReceiver)

    override fun pintarCumplimientos(cumplimientos: List<CumplimientoModel>) {
        adapter.actualizar(cumplimientos)
    }

    private inner class CambioAcuerdosReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            recuperarConsolidado()
        }
    }

    private fun recuperarConsolidado() {
        presenter.obtener(personaId, rol)
    }

    companion object {

        private const val ARG_PERSONA_ID = "param1"
        private const val ARG_ROL = "param2"

        fun newInstance(personaId: Long, rol: Rol) = ConsolidadoAcuerdosFragment()
            .withArguments(
                ARG_PERSONA_ID to personaId,
                ARG_ROL to rol
            )
    }
}
