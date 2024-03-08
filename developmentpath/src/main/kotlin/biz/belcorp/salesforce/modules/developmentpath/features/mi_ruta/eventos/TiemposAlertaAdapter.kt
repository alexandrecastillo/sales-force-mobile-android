package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.model.AgregarEventoViewModel
import kotlinx.android.synthetic.main.item_rdd_tiempo_alerta.view.*

class TiemposAlertaAdapter(context: Context) : RecyclerView.Adapter<TiemposAlertaAdapter.AlertaViewHolder>() {

    private var alertas = emptyList<AgregarEventoViewModel.Alerta>()
    private var onItemClickListener: OnItemClickListener? = null
    private val diffCallback = TiemposAlertaDiffCallback()
    private val alertaStringBuilder = AlertaStringBuilder(context)

    fun setOnItemClickListener(invocable: (pos: Int) -> Unit) {
        AgregarEventoFragment
        onItemClickListener = object : OnItemClickListener {
            override fun onClick(pos: Int) {
                invocable.invoke(pos)
            }
        }
    }

    fun actualizar(alertas: List<AgregarEventoViewModel.Alerta>) {
        diffCallback.establecer(alertas, this.alertas)

        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.alertas = alertas

        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlertaViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_rdd_tiempo_alerta, parent, false)

        return AlertaViewHolder(view)
    }

    override fun getItemCount() = alertas.size

    override fun onBindViewHolder(holder: AlertaViewHolder, position: Int) {
        val alerta = alertas[position]

        holder.textViewTiempo.text = alertaStringBuilder.construir(alerta.cantidad, alerta.unidad)
        holder.radioButton.isChecked = alerta.seleccionado
        holder.radioButton.setOnClickListener { onItemClickListener?.onClick(position) }
        holder.itemView.setOnClickListener { onItemClickListener?.onClick(position) }
    }

    class AlertaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewTiempo: TextView = view.textViewTiempo
        val radioButton: AppCompatRadioButton = view.radioButton
    }

    interface OnItemClickListener {
        fun onClick(pos: Int)
    }
}
