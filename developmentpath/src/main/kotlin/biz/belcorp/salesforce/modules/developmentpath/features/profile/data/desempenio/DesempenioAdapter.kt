package biz.belcorp.salesforce.modules.developmentpath.features.profile.data.desempenio

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.R
import kotlinx.android.synthetic.main.item_rdd_perfil_desempenio.view.*
import biz.belcorp.salesforce.core.utils.textColor

class DesempenioAdapter(val rol: Rol) : RecyclerView.Adapter<DesempenioAdapter.DesempenioViewHolder>() {

    var desempenios: MutableList<DesempenioModel> = mutableListOf()

    fun actualizar(desempenios: List<DesempenioModel>) {
        this.desempenios = desempenios.toMutableList()
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: DesempenioViewHolder, position: Int) {
        val desempenio = desempenios[position]
        holder.tvCampania?.text = desempenio.campania
        holder.tvEstado?.text = desempenio.descripcionProductividad
        holder.tvEstado?.textColor = ContextCompat.getColor(holder.itemView.context, obtenerColor(desempenio.colorProductividad))
        holder.ivPunto?.setImageDrawable(obtenerPuntoExito(desempenio.colorProductividad, holder.itemView.context))
        holder.ivPunto?.visibility = obtenerVisibilidadPunto()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DesempenioViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_rdd_perfil_desempenio, parent, false)
        return DesempenioViewHolder(view)
    }

    private fun obtenerVisibilidadPunto(): Int {
        return if (rol == Rol.GERENTE_ZONA) View.GONE else View.VISIBLE
    }

    private fun obtenerPuntoExito(colorProductividad: DesempenioModel.ColorProductividad, context: Context) =
        when (colorProductividad) {
            DesempenioModel.ColorProductividad.VERDE -> ContextCompat.getDrawable(context, R.drawable.circulo_exitosa)
            else -> ContextCompat.getDrawable(context, R.drawable.circulo_no_exitosa)
        }

    override fun getItemCount(): Int {
        return desempenios.size
    }

    fun obtenerColor(colorProductividad: DesempenioModel.ColorProductividad): Int {
        return when (colorProductividad) {
            DesempenioModel.ColorProductividad.VERDE -> R.color.estado_positivo
            else -> R.color.estado_negativo
        }
    }

    inner class DesempenioViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvCampania: TextView? = view.tvCampania
        val tvEstado: TextView? = view.tvEstado
        val ivPunto: ImageView? = view.ivPuntoExito
    }
}
