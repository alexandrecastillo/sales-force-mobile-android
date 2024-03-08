package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.gr.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.utils.porcentajeDe
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.gr.model.AvanceZonaModel
import kotlinx.android.synthetic.main.item_rdd_dashboard_avance_zona.view.*

class AvanceZonasAdapter(val context: Context) :
        RecyclerView.Adapter<AvanceZonasAdapter.AvanceZonaHolder>() {

    private var zonas: List<AvanceZonaModel> = emptyList()

    private var clickListener: ClickListener? = null

    fun setClickListener(invocable: (llaveUA: LlaveUA) -> Unit) {
        clickListener = object : ClickListener {
            override fun alHacerClickEnZona(llaveUA: LlaveUA) {
                invocable.invoke(llaveUA)
            }
        }
    }

    override fun onBindViewHolder(holder: AvanceZonaHolder, position: Int) =
            holder.bind(zonas[position])

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AvanceZonaHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_rdd_dashboard_avance_zona, parent, false)

        return AvanceZonaHolder(view)
    }

    override fun getItemCount() = zonas.size

    fun actualizar(zonas: List<AvanceZonaModel>) {
        this.zonas = zonas
        notifyDataSetChanged()
    }

    inner class AvanceZonaHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val textZonaGerente: TextView? = view.text_zona_gerente
        private val textCampania: TextView? = view.text_campania
        private val textVisitasRealizadas: TextView? = view.text_numerador_visitas_zona
        private val textVisitasPlanificadas: TextView? = view.text_denominador_visitas_zona
        private val progressAvance = view.progress_visitas_zona
        private val textEstadoProductividad = view.text_etiqueta_estado_productividad
        private val groupEtiquetaNueva = view.group_etiqueta_nueva
        private val groupEtiquetaPoductividad = view.group_etiqueta_productividad
        private val card: CardView = view.card

        fun bind(model: AvanceZonaModel) {
            model.apply {
                textZonaGerente?.text = obtenerNombre()
                textZonaGerente?.setTextColor(obtenerColorCobertura())
                textCampania?.text = nombreCortoCampania
                textVisitasRealizadas?.text = visitasRealizadas.toString()
                textVisitasPlanificadas?.text = obtenerDenominador()
                progressAvance?.progress = obtenerProgreso()
                textEstadoProductividad?.text = estadoProductividad
                groupEtiquetaNueva?.visibility = obtenerVisibilidadNueva()
                groupEtiquetaPoductividad?.visibility = obtenerVisibilidadProductividad()

                card.setOnClickListener { clickListener?.alHacerClickEnZona(llaveUA) }
            }
        }

        private fun AvanceZonaModel.obtenerNombre() =
                if (estaCoberturada) {
                    context.getString(R.string.zona_nombre_paramentros,
                        llaveUA.codigoZona,
                        nombreGerente).toUpperCase()
                } else {
                    context.getString(R.string.rdd_dashboard_zona_descoberturada, llaveUA.codigoZona)
                }

        private fun AvanceZonaModel.obtenerColorCobertura() =
                if (estaCoberturada) {
                    ContextCompat.getColor(context, R.color.rdd_accent)
                } else {
                    ContextCompat.getColor(context, R.color.rdd_danger_alt)
                }

        private fun AvanceZonaModel.obtenerVisibilidadNueva() =
                if (esNueva) {
                    View.VISIBLE
                } else {
                    View.GONE
                }

        private fun AvanceZonaModel.obtenerVisibilidadProductividad() =
                if (estadoProductividad != null) {
                    View.VISIBLE
                } else {
                    View.GONE
                }

        private fun AvanceZonaModel.obtenerDenominador() =
                context.getString(R.string.denominador_avance, visitasPlanificadas.toString())

        private fun AvanceZonaModel.obtenerProgreso() =
                visitasRealizadas.porcentajeDe(visitasPlanificadas)
    }

    interface ClickListener {
        fun alHacerClickEnZona(llaveUA: LlaveUA)
    }
}
