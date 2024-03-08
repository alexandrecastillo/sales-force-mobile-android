package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.header

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.base.R as BaseR
import biz.belcorp.mobile.components.core.extensions.getColor
import biz.belcorp.mobile.components.core.extensions.getDrawable
import biz.belcorp.mobile.components.core.extensions.inflate
import biz.belcorp.salesforce.core.utils.backgroundColor
import biz.belcorp.salesforce.core.utils.backgroundDrawable
import biz.belcorp.salesforce.core.utils.textColor
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.section.TipDesarrolloModel
import biz.belcorp.salesforce.modules.developmentpath.utils.reemplazarSaltoLineaPorEspacioAlrededor
import kotlinx.android.synthetic.main.item_detalle_tips_desarrollo.view.*

class DetalleTipsDesarrolloAdapter(
    private val onSelectedItem: (item: TipDesarrolloModel, position: Int) -> Unit = { _, _ -> }
) : RecyclerView.Adapter<DetalleTipsDesarrolloAdapter.Viewholder>() {

    private val items = arrayListOf<TipDesarrolloModel>()
    var posicionElementoSeleccionado = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val view = parent.inflate(R.layout.item_detalle_tips_desarrollo)
        return Viewholder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        holder.bind(items[position], onSelectedItem, posicionElementoSeleccionado)
    }

    fun notificarElementosCambiados(position: Int) {
        notifyItemChanged(posicionElementoSeleccionado)
        posicionElementoSeleccionado = position
        notifyItemChanged(posicionElementoSeleccionado)
    }


    fun actualizar(items: List<TipDesarrolloModel>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(
            modelo: TipDesarrolloModel,
            onSelectedItem: (item: TipDesarrolloModel, posicion: Int) -> Unit,
            position: Int
        ) = with(itemView) {
            txtNombreOpcion.text = modelo.tituto.reemplazarSaltoLineaPorEspacioAlrededor()
            clContenedor.setOnClickListener { onSelectedItem(modelo, adapterPosition) }
            if (position == adapterPosition) estadoSeleccionado()
            else estadoOriginal()
        }


        private fun estadoOriginal() = with(itemView) {
            clContenedor.backgroundColor = getColor(BaseR.color.white)
            clContenedor.backgroundDrawable = getDrawable(BaseR.drawable.border_layout_black)
            txtNombreOpcion.textColor = getColor(BaseR.color.black)
        }

        private fun estadoSeleccionado() = with(itemView) {
            clContenedor.backgroundColor = getColor(BaseR.color.black)
            txtNombreOpcion.textColor = getColor(BaseR.color.white)
        }
    }
}
