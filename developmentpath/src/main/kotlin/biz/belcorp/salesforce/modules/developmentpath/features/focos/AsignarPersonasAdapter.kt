package biz.belcorp.salesforce.modules.developmentpath.features.focos

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.components.widgets.TextDrawable
import biz.belcorp.salesforce.core.utils.backgroundColorResource
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.focos.model.PersonaModel
import biz.belcorp.salesforce.modules.developmentpath.utils.spToPx
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_dialog_asignacion_socias_detalle.view.*
import biz.belcorp.salesforce.core.R as coreR

class AsignarPersonasAdapter(val context: Context) : RecyclerView.Adapter<AsignarPersonasAdapter.SociaViewHolder>() {

    var clickListener: ClickListener? = null

    private var personas: List<PersonaModel> = emptyList()

    private val diffCallback = SociasDiffCallback()

    fun establecerClickEnSociaListener(listener: (posicion: Int) -> Unit) {
        clickListener = object :
                AsignarPersonasAdapter.ClickListener {
            override fun alClickearSocia(posicion: Int) {
                listener.invoke(posicion)
            }
        }
    }

    fun actualizar(personas: List<PersonaModel>) {
        diffCallback.establecer(this.personas, personas)

        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.personas = personas

        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SociaViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_dialog_asignacion_socias_detalle, parent, false)

        return SociaViewHolder(view)
    }

    override fun getItemCount() = personas.size

    override fun onBindViewHolder(holder: SociaViewHolder, position: Int) {
        val persona = personas[position]

        holder.itemView.isEnabled = persona.seleccionHabilitada

        holder.itemView.backgroundColorResource = obtenerColorBackground(persona.habilitado)

        holder.ivNoSeleccionado.visibility =
                obtenerVisibilidadNoSeleccionado(persona.seleccionado)

        holder.ivSeleccionado.visibility =
                obtenerVisibilidadSeleccionado(persona.seleccionado)

        holder.tvDescripcion.text = persona.descripcion

        val placeholderCircular = persona.iniciales?.let {
            TextDrawable.builder()
                .beginConfig()
                .fontSize(18.spToPx(context))
                .endConfig()
                .buildRound(
                    it,
                    ContextCompat.getColor(context, R.color.rdd_accent))
        }

        Glide.with(context)
            .load("")
            .placeholder(placeholderCircular)
            .apply(RequestOptions.circleCropTransform())
            .into(holder.ivAvatar)

        holder.itemView.setOnClickListener {
            clickListener?.alClickearSocia(position)
        }
    }

    private fun obtenerColorBackground(habilitado: Boolean): Int {
        return if (habilitado) {
            coreR.color.white
        } else {
            R.color.rdd_dashboard_asignar_foco_disabled
        }
    }

    private fun obtenerVisibilidadNoSeleccionado(seleccionado: Boolean): Int {
        return if (seleccionado) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    private fun obtenerVisibilidadSeleccionado(seleccionado: Boolean): Int {
        return if (seleccionado) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    class SociaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivNoSeleccionado: View = view.no_seleccionado
        val ivSeleccionado: ImageView = view.seleccionado
        val ivAvatar: ImageView = view.iv_avatar
        val tvDescripcion: TextView = view.tv_descripcion
    }

    interface ClickListener {
        fun alClickearSocia(posicion: Int)
    }
}
