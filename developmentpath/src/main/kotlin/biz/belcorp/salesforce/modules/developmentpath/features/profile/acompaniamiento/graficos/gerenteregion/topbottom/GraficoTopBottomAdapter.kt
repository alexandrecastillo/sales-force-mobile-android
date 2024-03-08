package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.graficos.gerenteregion.topbottom

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.core.utils.inflate
import biz.belcorp.salesforce.core.utils.textColor
import biz.belcorp.salesforce.modules.developmentpath.R

class GraficoTopBottomAdapter : RecyclerView.Adapter<GraficoTopBottomAdapter.GraficoTopBottomViewHolder>() {

    private var topBottomList: List<TopBottomModel> = emptyList()
    private var mostrarDosLineas: Boolean = false

    fun actualizar(topBottomList: List<TopBottomModel>, mostrarDosLineas: Boolean) {
        this.topBottomList = topBottomList
        this.mostrarDosLineas = mostrarDosLineas
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): GraficoTopBottomViewHolder {
        val view = parent.inflate(obtenerLayoutParaUnaODosLineas())
        return GraficoTopBottomViewHolder(view)
    }

    override fun getItemCount(): Int {
        return topBottomList.size
    }

    override fun onBindViewHolder(holder: GraficoTopBottomViewHolder, position: Int) {
        val topBottom = topBottomList[position]

        holder.tvZona?.text = topBottom.zona
        holder.tvValor?.text = topBottom.valor
        holder.tvValor?.textColor = ContextCompat.getColor(holder.itemView.context,
                obtenerColor(topBottom.color))
        holder.tvPorcentaje?.text = topBottom.porcentaje
        holder.tvPorcentaje?.textColor = ContextCompat.getColor(holder.itemView.context,
                obtenerColor(topBottom.color))
    }

    private fun obtenerColor(color: TopBottomModel.TopBottomColor): Int {
        return when (color) {
            TopBottomModel.TopBottomColor.VERDE -> R.color.estado_positivo
            TopBottomModel.TopBottomColor.ROJO -> R.color.estado_negativo
        }
    }

    private fun obtenerLayoutParaUnaODosLineas(): Int {
        return if (mostrarDosLineas) R.layout.item_rdd_acompaniamiento_grafico_top_bottom_dos_lineas
        else R.layout.item_rdd_acompaniamiento_grafico_top_bottom_una_linea
    }

    inner class GraficoTopBottomViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvZona = itemView.findViewById<TextView>(R.id.tvZona)
        val tvValor = itemView.findViewById<TextView>(R.id.tvValor)
        val tvPorcentaje: TextView? = itemView.findViewById(R.id.tvPorcentaje)
    }
}
