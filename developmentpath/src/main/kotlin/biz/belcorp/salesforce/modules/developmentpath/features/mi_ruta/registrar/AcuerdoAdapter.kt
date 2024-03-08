package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.registrar

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.modules.developmentpath.R
import kotlinx.android.synthetic.main.item_acuerdos.view.*

class AcuerdoAdapter(val context: Context,
                     val listener: OnItemClickListener) : RecyclerView.Adapter<VHAcuerdo>() {

    val acuerdos = mutableListOf<AcuerdoModel>()

    fun agregarAcuerdo(modelo: AcuerdoModel) {
        acuerdos.add(0, modelo)
        notifyItemInserted(0)
        notifyItemRangeChanged(1, acuerdos.size - 1)
    }

    fun eliminarAcuerdo(posicion: Int) {
        acuerdos.removeAt(posicion)
        notificarEliminacion(posicion)
        notifyItemRemoved(posicion)
    }

    private fun notificarEliminacion(posicion: Int) {
        notifyItemRangeChanged(posicion + 1, acuerdos.size - posicion)
    }

    override fun onBindViewHolder(holder: VHAcuerdo, position: Int) {
        val modelo = acuerdos[position]

        holder.txtAcuerdo?.text = modelo.contenido
        holder.txtAcuerdoFecha?.text = modelo.fecha
        holder.txtEliminarAcuerdo?.setOnClickListener { listener.onItemDeleteAcuerdo(position) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHAcuerdo {
        return VHAcuerdo(LayoutInflater
                .from(context)
                .inflate(R.layout.item_acuerdos, parent, false))
    }

    override fun getItemCount(): Int {
        return acuerdos.size
    }

    interface OnItemClickListener {
        fun onItemDeleteAcuerdo(posicion: Int)
    }
}

class VHAcuerdo(view: View) : RecyclerView.ViewHolder(view) {
    val txtAcuerdo = view.txt_acuerdo
    val txtAcuerdoFecha = view.txt_acuerdo_fecha
    val txtEliminarAcuerdo = view.txt_eliminar_acuerdo
}
