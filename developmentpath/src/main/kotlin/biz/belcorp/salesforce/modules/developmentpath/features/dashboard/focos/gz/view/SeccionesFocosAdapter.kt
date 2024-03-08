package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.focos.gz.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.core.utils.NonScrollableLayoutManager
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.focos.model.SeccionFocoModel
import kotlinx.android.synthetic.main.item_rdd_dashboard_focos_seccion.view.*

class SeccionesFocosAdapter : RecyclerView.Adapter<SeccionesFocosAdapter.SeccionFocosViewHolder>() {

    private var secciones: List<SeccionFocoModel> = emptyList()
    var listener: EditarFocosListener? = null

    fun actualizar(secciones: List<SeccionFocoModel>) {
        this.secciones = secciones
        notifyDataSetChanged()
    }

    fun setEditarFocoListener(invocable: (zonaId: Long) -> Unit) {
        listener = object : EditarFocosListener {
            override fun alHacerClickEnEditar(zonaId: Long) {
                invocable.invoke(zonaId)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeccionFocosViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.item_rdd_dashboard_focos_seccion,
                parent, false
            )
        return SeccionFocosViewHolder(view)
    }

    override fun getItemCount(): Int = secciones.size

    override fun onBindViewHolder(holder: SeccionFocosViewHolder, position: Int) {
        val seccion = secciones[position]

        holder.tvTitulo?.text = obtenerTitulo(holder.itemView.context, seccion)
        holder.llEditar?.visibility = obtenerVisibilidad(seccion.mostrarEditar)
        holder.rvFocos?.visibility = obtenerVisibilidad(seccion.mostrarFocos)
        holder.llSinFoco?.visibility = obtenerVisibilidad(!seccion.mostrarFocos)
        holder.llEditar?.setOnClickListener {
            listener?.alHacerClickEnEditar(seccion.sociaId ?: -1)
        }
        (holder.rvFocos?.adapter as FocosAdapter).actualizar(seccion.focos)
    }

    private fun obtenerTitulo(context: Context, seccion: SeccionFocoModel): String {
        return if (seccion.coberturada) {
            context.getString(
                R.string.rdd_dashboard_tab_focos_titulo_seccion_coberturada,
                seccion.codigoSeccion,
                seccion.nombresSocia
            )
        } else {
            context.getString(
                R.string.rdd_dashboard_tab_focos_titulo_seccion_descoberturada,
                seccion.codigoSeccion
            )
        }
    }

    private fun obtenerVisibilidad(mostrar: Boolean): Int {
        return if (mostrar) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    class SeccionFocosViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitulo: TextView? = view.tv_titulo
        val llEditar: LinearLayout? = view.ll_editar_focos
        val llSinFoco: LinearLayout? = view.ll_sin_foco
        val rvFocos: RecyclerView? = view.rv_focos

        init {
            rvFocos?.layoutManager = NonScrollableLayoutManager()
                .withContext(view.context)
                .linearVertical()
            rvFocos?.adapter = FocosAdapter()
        }
    }

    interface EditarFocosListener {
        fun alHacerClickEnEditar(zonaId: Long)
    }
}
