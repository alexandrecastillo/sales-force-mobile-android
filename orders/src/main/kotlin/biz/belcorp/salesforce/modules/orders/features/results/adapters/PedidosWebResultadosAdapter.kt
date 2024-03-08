package biz.belcorp.salesforce.modules.orders.features.results.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.base.utils.isGz
import biz.belcorp.salesforce.base.utils.isSe
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.orders.R
import biz.belcorp.salesforce.modules.orders.features.results.model.ResultadoItemPedidosWebModel
import biz.belcorp.salesforce.modules.orders.features.results.utils.PedidosMasOpciones
import kotlinx.android.synthetic.main.item_resultados_pedidos_web.view.*
import biz.belcorp.salesforce.base.R as BaseR


class PedidosWebResultadosAdapter(val listener: PedidosMasOpciones.AccionesListener) : RecyclerView.Adapter<PedidosWebResultadosAdapter.ViewHolder>() {

    private val items = mutableListOf<ResultadoItemPedidosWebModel>()
    private var mostrarOpciones = 0

    var campaniaActual = ""
    var rol: Rol? = null
    var bloqueoActivado = 1
    private var highlightName: String? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_resultados_pedidos_web, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = items[position]
        viewHolder.bind(item)
    }

    fun setHighlightName(highlightName: String?) {
        this.highlightName = highlightName?.takeIf { it.isNotBlank() }
    }

    fun actualizarData(items: List<ResultadoItemPedidosWebModel>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun clear() {
        this.items.clear()
        notifyDataSetChanged()
    }

    fun actualizarBloqueo(pedidoId: Int, bloqueado: Int) {
        this.items.find { it.orderId == pedidoId }?.locked = bloqueado
        notifyDataSetChanged()
    }

    fun mostrarOpciones(consultoraId: Int) {
        this.items.forEach {
            if (it.consultorasId == consultoraId) {
                it.display = 1
            } else {
                it.display = 0
            }
        }
        mostrarOpciones = 1
        notifyDataSetChanged()
    }

    fun ocultarOpciones() {
        this.items.forEach {
            it.display = 0
        }
        mostrarOpciones = 0
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: ResultadoItemPedidosWebModel) = with(itemView) {

            mostrarNombre(item.fullName)

            tvCodigoValue?.text = item.code
            tvEstadoValue?.text = obtenerEstadoPorRol(item)
            tvOrigenValue?.text = obtenerOrigen(item.source).guionSiNull()

            verificarBloqueo(item.locked)
            verificarOpciones(item.display, item)
            verificarSaldoPendiente(item.saldoPendiente)
            verificarMontoTotal(item.orderAmount, item.orderAmountMinimun)

            fabOpciones?.setOnClickListener {
                listener.abrirOpciones(item.consultorasId)
            }

            backgroundOpciones?.setOnClickListener {
                listener.cerrarOpciones()
            }

        }

        private fun mostrarNombre(fullName: String?) = with(itemView) {
            if (highlightName != null) {
                tvNombre?.setFont(BaseR.font.mulish_light)
                tvNombre?.text = fullName?.highlight(highlightName.toString())
            } else {
                tvNombre?.setFont(BaseR.font.mulish_regular)
                tvNombre?.text = fullName
            }
        }

        private fun verificarBloqueo(bloqueado: Int) = with(itemView) {
            if (bloqueado == 1) {
                tvBloqueado?.visible()
                layoutIndicador?.visible()
            } else {
                tvBloqueado?.gone()
                layoutIndicador?.gone()
            }
        }

        private fun verificarOpciones(opciones: Int, item: ResultadoItemPedidosWebModel) = with(itemView) {
            if (opciones == 1) {
                layoutOpciones?.visible()
                fabOpciones?.invisible()
                PedidosMasOpciones.Builder(itemView, item)
                    .setCampaniaActual(campaniaActual)
                    .setBloqueoActivado(bloqueoActivado)
                    .setListener(listener)
                    .build()
            } else {
                layoutOpciones?.gone()
                fabOpciones?.visible()
                layoutOpciones?.removeAllViews()
            }
            if (mostrarOpciones == 1) {
                backgroundOpciones?.visible()
            } else {
                backgroundOpciones?.gone()
            }
        }

        private fun verificarSaldoPendiente(saldoPendiente: String?) = with(itemView) {
            if (saldoPendiente?.toDoubleOrNull() ?: 0.0 > 0.0) {
                tvSaldoValue?.text = saldoPendiente
                tvSaldoValue?.visible()
                tvSaldo?.visible()
            } else {
                tvSaldoValue?.gone()
                tvSaldo?.gone()
            }
        }

        private fun verificarMontoTotal(montoTotal: String?, montoMinimo: Int) = with(itemView) {
            val colorId = if (montoMinimo == 1) {
                BaseR.color.negativo
            } else {
                R.color.gray_label_dark
            }
            tvMontoTotalValue?.text = montoTotal
            tvMontoTotalValue.setTextColor(context.getCompatColor(colorId))
        }

        private fun obtenerEstadoPorRol(model: ResultadoItemPedidosWebModel): String {
            return when {
                rol.isSe() -> model.estado.orEmpty()
                rol.isGz() -> WordUtils.capitalizeFully(model.orderStatus)
                else -> ""
            }
        }

        private fun obtenerOrigen(origen: String?) = when (origen) {
            ResultadoItemPedidosWebModel.ORIGEN_WEB -> {
                itemView.context.getString(R.string.pedido_origen_web)
            }
            ResultadoItemPedidosWebModel.ORIGEN_DIGITACION -> {
                itemView.context.getString(R.string.pedido_origen_digitacion)
            }
            else -> null
        }

    }

}
