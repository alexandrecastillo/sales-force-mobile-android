package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.dv.avanceregiones.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.utils.textColor
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.dv.avanceregiones.model.RegionModel
import kotlinx.android.synthetic.main.item_avance_region.view.*

class AvanceRegionesAdapter : RecyclerView.Adapter<AvanceRegionesAdapter.AvanceRegionHolder>() {

    private var regiones: List<RegionModel> = emptyList()

    private var clickListener: ClickListener? = null

    fun establecerClickListener(function: (LlaveUA) -> Unit) {
        clickListener = object : ClickListener{
            override fun alHacerClickEnRegion(llaveUA: LlaveUA) {
                function.invoke(llaveUA)
            }
        }
    }

    override fun onBindViewHolder(holder: AvanceRegionHolder, position: Int) {
        holder.bind(regiones[position])
    }

    override fun getItemCount(): Int {
        return regiones.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AvanceRegionHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_avance_region, parent, false)

        return AvanceRegionHolder(view)
    }

    fun actualizar(regiones: List<RegionModel>) {
        this.regiones = regiones
        notifyDataSetChanged()
    }

    inner class AvanceRegionHolder(private val view: View): RecyclerView.ViewHolder(view) {

        private val container = view.container
        private val textTituloItem = view.textTituloItem
        private val textCampania = view.textCampania
        private val textVisitasRegistradasItem = view.textVisitasRegistradasItem
        private val textVisitasPlanificadasItem = view.textVisitasPlanificadasItem
        private val progressVisitas = view.progressVisitas
        private val textEstadoProductividadItem = view.textEstadoProductividadItem
        private val layoutProductividad = view.layoutProductividad

        fun bind(model: RegionModel) {
            container.setOnClickListener { validarClickEInvocarListener(model) }
            textTituloItem.text = model.construirTitulo(view.context)
            textCampania.text = model.codigoCampania
            textTituloItem.textColor = model.construirColorTitulo(view.context)
            textVisitasRegistradasItem.text = model.visitasRealizadas.toString()
            textVisitasPlanificadasItem.text = model.obtenerTextoVisitasPlanificadas()
            progressVisitas.max = model.visitasPlanificadas
            progressVisitas.progress = model.visitasRealizadas
            layoutProductividad.visibility = model.mostrarProductividad
            textEstadoProductividadItem.text = model.productividad
        }

        private fun validarClickEInvocarListener(model: RegionModel) {
            clickListener?.alHacerClickEnRegion(model.llaveUA)
        }

        private fun RegionModel.obtenerTextoVisitasPlanificadas(): String {
            return view.context.getString(R.string.denominador_avance,
                                          this.visitasPlanificadas.toString())
        }

    }

    interface ClickListener {
        fun alHacerClickEnRegion(llaveUA: LlaveUA)
    }
}
