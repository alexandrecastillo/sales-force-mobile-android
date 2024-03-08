package biz.belcorp.salesforce.modules.developmentpath.features.profile.data.gz.goals.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.core.utils.parseToDateItem
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.profile.data.gz.goals.model.MetaPersonalModel
import kotlinx.android.synthetic.main.item_metas_card.view.*

class MetaPersonalAdapter : RecyclerView.Adapter<MetaPersonalAdapter.ViewHolder>() {

    var metas = mutableListOf<MetaPersonalModel>()
    var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_metas_card, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(metas[position])

    override fun getItemCount() = metas.size

    fun establecerListener(listenerView: OnItemClickListener) {
        listener = listenerView
    }

    fun actualizar(metas: List<MetaPersonalModel>) {
        this.metas = metas.toMutableList()
        notifyDataSetChanged()
    }

    fun agregar(element: MetaPersonalModel, indice: Int = 0) {
        metas.add(indice, element)
        notifyItemInserted(indice)
    }

    fun eliminar(element: MetaPersonalModel) {
        val index = metas.indexOfFirst { it.metaId == element.metaId }
        metas.removeAt(index)

        notifyItemRemoved(index)
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        private val textDescripcion = view.textDescripcion
        private val textFecha = view.textMetaFecha
        private val buttonEliminar = view.buttonEliminarMeta
        private val buttonEditar = view.buttonEditarMeta

        fun bind(model: MetaPersonalModel) {

            textDescripcion.text = model.descripcion
            textFecha.text = model.fecha.parseToDateItem()

            buttonEliminar.setOnClickListener { listener?.alEliminar(model) }

            buttonEditar.setOnClickListener { listener?.alEditar(model, adapterPosition) }
        }
    }

    interface OnItemClickListener {

        fun alEliminar(model: MetaPersonalModel)

        fun alEditar(model: MetaPersonalModel, indice: Int)
    }
}
