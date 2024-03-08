package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.barranavegacion.adapter

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.barranavegacion.NivelModel
import kotlinx.android.synthetic.main.item_barra_navegacion_rdd_intermedio.view.*

class NivelIntermedioViewHolder(private val regresarUnNivelListener: RegresarUnNivelListener?,
                                itemView: View) :
        RecyclerView.ViewHolder(itemView),
        BarraNavegacionAdapter.BindableViewHolder {

    private val textViewDescripcion: TextView = itemView.textViewDescripcionIntermedia
    private val botonVerRuta: Button = itemView.botonVerRuta
    private val constructorTextoNivel = ConstructorTextoNivel(itemView.context)

    override fun bind(nivelModel: NivelModel) {
        val modelo = nivelModel as NivelModel.UaRegresable

        textViewDescripcion.text = constructorTextoNivel.construir(modelo)

        botonVerRuta.setOnClickListener {
            regresarNVeces(modelo)
        }
    }

    private fun regresarNVeces(modelo: NivelModel.UaRegresable) {
        repeat(modelo.cantidadPantallasRegreso) {
            regresarUnNivelListener?.alClickearRegresarUnNivel(modelo)
        }
    }
}
