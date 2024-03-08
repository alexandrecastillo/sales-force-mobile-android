package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.section

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.mobile.components.core.extensions.inflate

class PrepararseEsClaveAdapter(
    @LayoutRes private val layout: Int,
    private val type: PrepararseEsClaveViewHolder.Type
) : RecyclerView.Adapter<PrepararseEsClaveViewHolder>() {

    var dataSet: List<PrepararseEsClaveModel> = emptyList()
    var listener: SelectableCallback? = null

    var posicionElementoSeleccionado: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrepararseEsClaveViewHolder {
        val view: View = parent.inflate(layout)
        return PrepararseEsClaveViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: PrepararseEsClaveViewHolder, position: Int) {
        val item = dataSet[position]
        holder.bind(item, type, posicionElementoSeleccionado)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    fun notificarElementosCambiados(posicion: Int) {
        notifyItemChanged(posicionElementoSeleccionado)
        posicionElementoSeleccionado = posicion
        notifyItemChanged(posicionElementoSeleccionado)
    }

    fun actualizar(data: List<PrepararseEsClaveModel>) {
        this.dataSet = data
        notifyDataSetChanged()
    }

    interface SelectableCallback {
        fun onSelectePrepararseEsClaveItem(
            item: PrepararseEsClaveModel,
            type: PrepararseEsClaveViewHolder.Type,
            posicion: Int
        )
    }
}
