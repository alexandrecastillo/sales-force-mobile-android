package biz.belcorp.salesforce.modules.developmentpath.features.horariovisitas

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.core.utils.textColor
import biz.belcorp.salesforce.modules.developmentpath.R
import kotlinx.android.synthetic.main.item_horario_visita.view.*

class HorarioVisitaAdapter(
    private var dataSet: List<HorarioVisitaModel> = emptyList(),
    var callback: Callback? = null
) : RecyclerView.Adapter<HorarioVisitaAdapter.HorarioVisitaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorarioVisitaViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_horario_visita, parent, false)
        return HorarioVisitaViewHolder(view, callback)
    }

    override fun onBindViewHolder(holder: HorarioVisitaViewHolder, position: Int) {
        val item = dataSet[position]
        holder.bind(item, position)
    }

    override fun getItemViewType(position: Int) = position

    override fun getItemCount() = dataSet.size

    fun actualizar(data: List<HorarioVisitaModel>) {
        this.dataSet = data
        notifyDataSetChanged()
    }

    fun actualizarhorarioSeleccionado(horarioVisitaId: Int) {
        this.dataSet.forEach { it.seleccionado = false }
        this.dataSet.find { it.horarioVisitaId == horarioVisitaId }?.seleccionado = true

        notifyDataSetChanged()
    }

    fun getItems(): List<HorarioVisitaModel> {
        return dataSet
    }

    inner class HorarioVisitaViewHolder(view: View, private val callback: Callback?) :
        RecyclerView.ViewHolder(view) {

        private lateinit var model: HorarioVisitaModel

        fun bind(item: HorarioVisitaModel, position: Int) = with(itemView) {
            model = item

            if (item.seleccionado)
                itemView.container_item.txtHours.textColor =
                    context.resources.getColor(R.color.primary_color, null)
            else
                itemView.container_item.txtHours.textColor = Color.BLACK

            itemView.txtHours.text = item.descripcion

            itemView.container_item.setOnClickListener {
                callback?.onHorarioSeleccionadoItem(item, position)
            }
        }
    }

    interface Callback {
        fun onHorarioSeleccionadoItem(item: HorarioVisitaModel, posicion: Int)
    }
}
