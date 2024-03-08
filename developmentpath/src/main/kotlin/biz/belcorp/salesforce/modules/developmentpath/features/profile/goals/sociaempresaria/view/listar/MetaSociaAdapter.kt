package biz.belcorp.salesforce.modules.developmentpath.features.profile.goals.sociaempresaria.view.listar

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.core.utils.inflate
import biz.belcorp.salesforce.core.utils.toDescriptionDay
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.profile.goals.sociaempresaria.model.MetaSociaModelo
import kotlinx.android.synthetic.main.item_meta.view.*

class MetaSociaAdapter : RecyclerView.Adapter<MetaSociaAdapter.ViewHolder>() {

    private val metas = mutableListOf<MetaSociaModelo>()
    var listener: MetaListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.item_meta)
        return ViewHolder(view)
    }

    override fun getItemCount() = metas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(metas[position], listener)
    }

    fun actualizar(metas: List<MetaSociaModelo>) {
        this.metas.clear()
        this.metas.addAll(metas)
        notifyDataSetChanged()
    }

    fun eliminar(posicion: Int) {
        this.metas.removeAt(posicion)
        notifyItemRemoved(posicion)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(meta: MetaSociaModelo, listener: MetaListener?) = with(itemView) {
            txtDescripcion?.text = meta.descripcion
            txtFecha?.text = meta.fecha.toDescriptionDay()
            btnEditar?.setOnClickListener { listener?.editarMeta(meta, adapterPosition) }
            btnEliminar?.setOnClickListener { listener?.eliminarMeta(meta, adapterPosition) }
        }
    }

    interface MetaListener {
        fun eliminarMeta(meta: MetaSociaModelo, posicion: Int)
        fun editarMeta(meta: MetaSociaModelo, posicion: Int)
    }
}
