package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.barranavegacion.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.barranavegacion.NivelModel
import kotlinx.android.synthetic.main.item_barra_navegacion_rdd_propio.view.*

class NivelExpandibleViewHolder(private val expandirListener: ExpandirListener?,
                                private val regresarARaizListener: RegresarARaizListener?,
                                itemView: View) :
        RecyclerView.ViewHolder(itemView),
        BarraNavegacionAdapter.BindableViewHolder {

    private lateinit var modelo: NivelModel.UaExpandible
    private val constructorTextoNivel = ConstructorTextoNivel(itemView.context)
    private val layoutExpandir: ViewGroup = itemView.layoutExpandir
    private val textViewExpandir: TextView = itemView.textViewExpandir
    private val textViewIconoExpandir: TextView = itemView.textViewIconoExpandir
    private val botonSalirFull: Button = itemView.botonSalirFull
    private val botonSalirIcono: ImageButton = itemView.botonSalirIcono
    private val textViewDescripcion: TextView = itemView.textViewDescripcionPropia

    override fun bind(nivelModel: NivelModel) {
        modelo = nivelModel as NivelModel.UaExpandible

        textViewDescripcion.text = constructorTextoNivel.construir(modelo)
        textViewExpandir.text = obtenerTextoLayoutExpandir()
        textViewIconoExpandir.text = obtenerTextoIconoExpandir()
        botonSalirFull.visibility = obtenerVisibilidadBotonSalirFull()
        layoutExpandir.visibility = obtenerVisibilidadLayoutExpandir()
        botonSalirIcono.visibility = obtenerVisibilidadBotonSalirIcono()
        layoutExpandir.setOnClickListener { expandirListener?.alClickearExpandir() }
        botonSalirIcono.setOnClickListener { regresarARaizListener?.alClickearRegresarARaiz() }
    }

    private fun obtenerVisibilidadBotonSalirFull(): Int {
        return View.GONE
    }

    private fun obtenerVisibilidadBotonSalirIcono(): Int {
        return if (modelo.expandido) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    private fun obtenerVisibilidadLayoutExpandir(): Int {
        return View.VISIBLE
    }

    private fun obtenerTextoLayoutExpandir(): String {
        return if (modelo.expandido) {
            itemView.context.getString(R.string.rdd_cabecera_otra_persona_ver_menos)
        } else {
            itemView.context.getString(R.string.rdd_cabecera_otra_persona_ver_mas)
        }
    }

    private fun obtenerTextoIconoExpandir(): String {
        return if (modelo.expandido) {
            itemView.context.getString(R.string.rdd_cabecera_ver_menos_ico)
        } else {
            modelo.cantidadVerMas.toString()
        }
    }
}
