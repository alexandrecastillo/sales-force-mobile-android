package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.barranavegacion.adapter

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.barranavegacion.NivelModel
import kotlinx.android.synthetic.main.item_barra_navegacion_rdd_propio.view.*

class NivelPropioViewHolder(private var salirListener: SalirListener?,
                            itemView: View) :
        RecyclerView.ViewHolder(itemView),
        BarraNavegacionAdapter.BindableViewHolder {

    private lateinit var modelo: NivelModel.Ua
    private val constructorTextoNivel = ConstructorTextoNivel(itemView.context)
    private val botonSalirFull: Button = itemView.botonSalirFull
    private val textViewDescripcion: TextView = itemView.textViewDescripcionPropia

    override fun bind(nivelModel: NivelModel) {
        modelo = nivelModel as NivelModel.Ua
        textViewDescripcion.text = constructorTextoNivel.construir(modelo)
        botonSalirFull.visibility = obtenerVisibilidadBotonSalirFull()
        botonSalirFull.setOnClickListener { salirListener?.alClickearSalir(modelo)}
    }

    private fun obtenerVisibilidadBotonSalirFull(): Int {
        return View.VISIBLE
    }
}

