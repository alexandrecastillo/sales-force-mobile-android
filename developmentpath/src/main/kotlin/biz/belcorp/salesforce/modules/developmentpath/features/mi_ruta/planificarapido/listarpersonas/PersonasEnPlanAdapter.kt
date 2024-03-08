package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.planificarapido.listarpersonas

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.Group
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.core.utils.setOnOneClickListener
import biz.belcorp.salesforce.modules.developmentpath.R
import kotlinx.android.synthetic.main.item_persona_planificar_rapido.view.*
import biz.belcorp.salesforce.core.utils.textColor

class PersonasEnPlanAdapter(private val context: Context) :
        RecyclerView.Adapter<PersonasEnPlanAdapter.PersonaViewHolder>() {

    private var personas = emptyList<PersonaEnPlanModel>()
    private var clickListener: ClickListener? = null
    private val diffCallback = PersonasDiffCallback()

    fun actualizar(personas: List<PersonaEnPlanModel>) {
        diffCallback.establecer(this.personas, personas)

        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.personas = personas

        diffResult.dispatchUpdatesTo(this)
    }

    fun setItemClickListener(accion: (visitaId: Long) -> (Unit)) {
        clickListener = object : ClickListener {
            override fun alClickearPlanificar(visitaId: Long) {
                accion.invoke(visitaId)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): PersonaViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(
                    R.layout.item_persona_planificar_rapido,
                         parent, false)

        return PersonaViewHolder(view)
    }

    override fun getItemCount() = personas.size

    override fun onBindViewHolder(viewHolder: PersonaViewHolder, position: Int) {
        viewHolder.bind(personas[position])
    }

    interface ClickListener {
        fun alClickearPlanificar(visitaId: Long)
    }

    inner class PersonaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iniciales: TextView = itemView.inicialesTextView
        val nombres: TextView = itemView.textViewNombres
        val descripcion: TextView = itemView.textViewDescripcion
        val botonPlanificar: ViewGroup = itemView.layoutBotonPlanificar
        val textoBotonPlanificar: TextView = itemView.textViewBotonPlanificar
        val iconoIzquierda: ImageView = itemView.iconoIzquierdaPlanificar
        val iconoDerecha: ImageView = itemView.iconoDerechaPlanificar
        val grupoFecha: Group = itemView.grupoFechaPlanificacion
        val fecha: TextView = itemView.textViewFecha

        fun bind(model: PersonaEnPlanModel) {
            nombres.text = model.nombres
            iniciales.text = model.iniciales
            descripcion.text = model.descripcion
            fecha.text = model.fechaPlanificacion
            grupoFecha.visibility = visibilidadFecha(model.planificada)
            textoBotonPlanificar.text = textoBotonPlanificar(model.planificada)
            textoBotonPlanificar.textColor = colorPlanificado(model.planificada)
            botonPlanificar.background = backgroundPlanificado(model.planificada)
            botonPlanificar.setClickListener(model)
            iconoIzquierda.visibility = iconoIzquierdaVisibilidad(model.planificada)
            iconoDerecha.visibility = iconoDerechaVisibilidad(model.planificada)
        }

        private fun visibilidadFecha(planificado: Boolean): Int {
            return if (planificado) View.VISIBLE else View.GONE
        }

        private fun textoBotonPlanificar(planificado: Boolean): String {
            return if (planificado)
                context.getString(R.string.planificada)
            else
                context.getString(R.string.planificar)
        }

        private fun colorPlanificado(planificado: Boolean): Int {
            return if (planificado)
                ContextCompat.getColor(context, R.color.estado_positivo)
            else
                ContextCompat.getColor(context, R.color.rdd_accent)
        }

        private fun backgroundPlanificado(planificado: Boolean): Drawable? {
            return if (planificado)
                ContextCompat.getDrawable(context, R.drawable.background_rdd_boton_planificado)
            else
                ContextCompat.getDrawable(context, R.drawable.background_rdd_boton_planificar)
        }

        private fun iconoIzquierdaVisibilidad(planificado: Boolean): Int {
            return if (planificado) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }

        private fun iconoDerechaVisibilidad(planificado: Boolean): Int {
            return iconoIzquierdaVisibilidad(!planificado)
        }

        private fun ViewGroup.setClickListener(model: PersonaEnPlanModel) {
            if (model.planificada)
                this.setOnClickListener(null)
            else
                this.setOnOneClickListener {
                    clickListener?.alClickearPlanificar(model.visitaId
                                                                ?: return@setOnOneClickListener)
                }
        }
    }
}
