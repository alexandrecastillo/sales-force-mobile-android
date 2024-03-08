package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.gz.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.analytics.core.domain.entities.ScreenTag
import biz.belcorp.salesforce.analytics.features.BelcorpAnalytics
import biz.belcorp.salesforce.core.utils.loadImageDrawable
import biz.belcorp.salesforce.core.utils.loadImageResource
import biz.belcorp.salesforce.core.utils.toast
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.gz.model.SeccionAvanceModel
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.broadcast.SenderClickUaRdd
import kotlinx.android.synthetic.main.item_rdd_seccion.view.*

class SeccionesAdapter(val context: Context) :
    RecyclerView.Adapter<SeccionesAdapter.SeccionSEViewHolder>() {

    private val senderClickUaRdd = SenderClickUaRdd(context)

    private var secciones = emptyList<SeccionAvanceModel>()

    fun actualizar(secciones: List<SeccionAvanceModel>) {
        this.secciones = secciones
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeccionSEViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_rdd_seccion, parent, false)

        return SeccionSEViewHolder(view)
    }

    override fun getItemCount(): Int {
        return secciones.size
    }

    override fun onBindViewHolder(viewHolder: SeccionSEViewHolder, position: Int) {
        val seccion = secciones[position]

        viewHolder.itemView.setOnClickListener {
            validarPlanYEjecutarAccion(seccion)
        }

        viewHolder.tvTitle?.setTextColor(obtenerColorCobertura(seccion))
        viewHolder.tvGuion?.setTextColor(obtenerColorCobertura(seccion))
        viewHolder.tvCodigo?.setTextColor(obtenerColorCobertura(seccion))
        viewHolder.tvNombre?.setTextColor(obtenerColorCobertura(seccion))
        viewHolder.tvExitosa?.setTextColor(obtenerColorExito(seccion))

        viewHolder.llNivel?.visibility = obtenerVisibilidadNivel(seccion)
        viewHolder.llEstado?.visibility = obtenerVisibilidadCobertura(seccion)
        viewHolder.llExitosa?.visibility = obtenerVisibilidadCobertura(seccion)
        viewHolder.ivAlert?.visibility = obtenerVisibilidadInversaCobertura(seccion)

        viewHolder.ivAlert?.loadImageResource(R.drawable.ic_rdd_alert_dashboard)
        viewHolder.ivNivel?.loadImageResource(R.drawable.ic_rdd_nivel)

        obtenerCirculoExito(seccion)?.also {
            viewHolder.circuloExito?.loadImageDrawable(it)
        }

        viewHolder.tvNombre?.text = obtenerNombre(seccion)
        viewHolder.tvCodigo?.text = seccion.codigoSeccion
        viewHolder.tvVisitadas?.text = seccion.visitadas
        viewHolder.tvTotal?.text = seccion.total
        viewHolder.progressBar?.progress = seccion.progreso
        viewHolder.tvNivel?.text = seccion.nivel
        viewHolder.tvExitosa?.text = seccion.exito.titulo
        viewHolder.tvEstado?.text = seccion.estado
    }

    private fun validarPlanYEjecutarAccion(seccion: SeccionAvanceModel) {
        if (seccion.seccionFuePlanificada) {
            senderClickUaRdd.clickearUa(seccion.llaveUA)
            BelcorpAnalytics.log(ScreenTag.SE_ADVANCE)
        } else {
            context.toast("La sección no ha sido planificada")
        }
    }

    private fun obtenerColorCobertura(modelo: SeccionAvanceModel): Int {
        return if (modelo.coberturada) {
            ContextCompat.getColor(context, R.color.rdd_accent)
        } else {
            ContextCompat.getColor(context, R.color.rdd_danger_alt)
        }
    }

    private fun obtenerColorExito(modelo: SeccionAvanceModel): Int {
        return when (modelo.exito.color) {
            SeccionAvanceModel.Exito.Color.ROJO ->
                ContextCompat.getColor(context, R.color.estado_negativo)
            SeccionAvanceModel.Exito.Color.VERDE ->
                ContextCompat.getColor(context, R.color.estado_positivo)
        }
    }

    private fun obtenerCirculoExito(modelo: SeccionAvanceModel): Drawable? {
        return when (modelo.exito.color) {
            SeccionAvanceModel.Exito.Color.ROJO ->
                ContextCompat.getDrawable(context, R.drawable.circulo_no_exitosa)
            SeccionAvanceModel.Exito.Color.VERDE ->
                ContextCompat.getDrawable(context, R.drawable.circulo_exitosa)
        }
    }

    private fun obtenerNombre(modelo: SeccionAvanceModel): String {
        return if (modelo.coberturada) {
            modelo.nombreSocia
        } else {
            context.getString(R.string.rdd_dashboard_descoberturada)
        }
    }

    private fun obtenerVisibilidadNivel(modelo: SeccionAvanceModel): Int {
        return if (modelo.coberturada) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    private fun obtenerVisibilidadCobertura(modelo: SeccionAvanceModel): Int {
        return if (modelo.coberturada) {
            View.VISIBLE
        } else {
            View.INVISIBLE
        }
    }

    private fun obtenerVisibilidadInversaCobertura(modelo: SeccionAvanceModel): Int {
        return if (modelo.coberturada) {
            View.INVISIBLE
        } else {
            View.VISIBLE
        }
    }

    class SeccionSEViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView? = view.tv_seccion_title
        val tvGuion: TextView? = view.tv_guion
        val tvNombre: TextView? = view.tv_nombre
        val tvCodigo: TextView? = view.tv_codigo
        val tvVisitadas: TextView? = view.tv_visitadas
        val tvTotal: TextView? = view.tv_total
        val progressBar: ProgressBar? = view.pb_visitadas
        val ivAlert: ImageView? = view.iv_alert
        val llNivel: LinearLayout? = view.ll_nivel
        val ivNivel: ImageView? = view.iv_nivel
        val tvNivel: TextView? = view.tv_nivel
        val llExitosa: LinearLayout? = view.ll_exitosa
        val circuloExito: ImageView? = view.circulo_exito
        val tvExitosa: TextView? = view.tv_exitosa
        val llEstado: LinearLayout? = view.ll_estado
        val tvEstado: TextView? = view.tv_estado
    }
}
