package biz.belcorp.salesforce.modules.developmentpath.features.focos

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.focos.model.FocoModel
import kotlinx.android.synthetic.main.item_dialog_asignacion_focos_detalle.view.*

class AsignarFocosAdapter(val context: Context) : RecyclerView.Adapter<AsignarFocosAdapter.FocoViewHolder>() {

    var clickListener: ClickListener? = null
    private var focos: List<FocoModel> = emptyList()
    private val diffCallback = FocosDiffCallback()

    fun establecerClickEnFocoListener(listener: (posicion: Int) -> Unit) {
        clickListener = object :
                AsignarFocosAdapter.ClickListener {
            override fun alClickearFoco(posicion: Int) {
                listener.invoke(posicion)
            }
        }
    }

    fun actualizar(focos: List<FocoModel>) {
        diffCallback.establecer(this.focos, focos)

        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.focos = focos

        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FocoViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_dialog_asignacion_focos_detalle, parent, false)

        return FocoViewHolder(view)
    }

    override fun getItemCount() = focos.size

    override fun onBindViewHolder(holder: FocoViewHolder, position: Int) {
        val foco = focos[position]

        holder.btnSeleccionarFoco.text = foco.descripcion
        holder.btnSeleccionarFoco.background = obtenerBackground(foco.seleccionado)
        holder.iconoSeleccion.visibility = obtenerVisibilidadIcono(foco.seleccionado)
        holder.fondoSeleccion.visibility = obtenerVisibilidadIcono(foco.seleccionado)
        holder.btnSeleccionarFoco.setOnClickListener { clickListener?.alClickearFoco(position) }
    }

    private fun obtenerBackground(seleccionado: Boolean): Drawable? {
        return if (seleccionado) {
            ContextCompat.getDrawable(context, R.drawable.background_button_foco_seleccionado)
        } else {
            ContextCompat.getDrawable(context, R.drawable.background_button_foco_no_seleccionado)
        }
    }

    private fun obtenerVisibilidadIcono(seleccionado: Boolean): Int {
        return if (seleccionado) {
            View.VISIBLE
        } else {
            View.INVISIBLE
        }
    }

    class FocoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val btnSeleccionarFoco: TextView = view.btn_seleccionar_foco
        val iconoSeleccion: ImageView = view.iconoSeleccion
        val fondoSeleccion: View = view.fondoSeleccion
    }

    interface ClickListener {
        fun alClickearFoco(posicion: Int)
    }
}
