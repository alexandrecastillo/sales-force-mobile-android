package biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.acuerdos.gz

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.core.utils.NonScrollableLayoutManager
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.acuerdos.AcuerdoAdapter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.acuerdos.AcuerdoModel
import biz.belcorp.salesforce.modules.developmentpath.widgets.DividerItemDecoration
import kotlinx.android.synthetic.main.item_rdd_acuerdo_habilidad.view.*

class AcuerdosHabilidadesAdapter : RecyclerView.Adapter<AcuerdosHabilidadesAdapter.AcuerdosHabilidadesViewHolder>() {

    private var campaniaCampanias: List<CampaniaCampaniaModel> = emptyList()
    var listener: CampaniaCampaniaListener? = null

    fun actualizar(campaniaCampanias: List<CampaniaCampaniaModel>) {
        this.campaniaCampanias = campaniaCampanias
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): AcuerdosHabilidadesViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.item_rdd_acuerdo_habilidad,
                parent, false)

        return AcuerdosHabilidadesViewHolder(
            view)
    }

    override fun onBindViewHolder(viewHolder: AcuerdosHabilidadesViewHolder, position: Int) {
        viewHolder.bind(campaniaCampanias[position])
    }

    override fun getItemCount(): Int {
        return campaniaCampanias.size
    }

    private fun obtenerVisibilidad(mostrar: Boolean): Int {
        return if (mostrar) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    inner class AcuerdosHabilidadesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: CampaniaCampaniaModel)= with(itemView) {

            (rv_habilidades.adapter as HabilidadReconocidaAdapter).actualizar(item.habilidadesReconocidas)
            (rv_acuerdos.adapter as AcuerdoAdapter).actualizar(item.acuerdos)
            rv_acuerdos.visibility = obtenerVisibilidad(item.acuerdos.isNotEmpty())
            tv_campania.text = item.tituloCampania
            tv_cantidad_habilidades.text = item.cantidadHabilidadesReconocidas
            card_sin_habilidades.visibility = obtenerVisibilidad(item.mostrarReconocerHabilidades)
            card_habilidades.visibility = obtenerVisibilidad(item.mostrarCardHabilidades)
            rv_habilidades.visibility = obtenerVisibilidad(item.mostrarHabilidades)
            grupo_sin_habilidades.visibility = obtenerVisibilidad(item.mostrarSinHabilidades)
            pgb_habilidades.progress = item.porcentaje.toFloat()
            cl_habilidades.setOnClickListener {
                listener?.alHacerClickEnVerHabilidadesReconocidas(item.campania)
            }
            ll_asignar_habilidades.setOnClickListener {
                listener?.alHacerClickEnAsignarHabilidades()
            }
            (rv_acuerdos.adapter as AcuerdoAdapter).setEliminarAcuerdoListener {
                listener?.alHacerClickEliminar(it)
            }
            (rv_acuerdos.adapter as AcuerdoAdapter).setGuardarAcuerdoListener {
                listener?.alHacerClickGuardar(it)
            }


        }

        init {
            itemView.rv_habilidades.layoutManager = NonScrollableLayoutManager()
                .withContext(itemView.context)
                .grid(6)
            itemView.rv_habilidades.adapter = HabilidadReconocidaAdapter()

            itemView.rv_acuerdos.layoutManager = NonScrollableLayoutManager()
                .withContext(itemView.context)
                .linearVertical()
            itemView.rv_acuerdos.adapter = AcuerdoAdapter()
            val divider = ContextCompat.getDrawable(itemView.context, R.drawable.line_divider_horizontal_16_inset)
            itemView.rv_acuerdos.addItemDecoration(DividerItemDecoration(divider, mShowFirstDivider = false, mShowLastDivider = false))
        }

    }

    interface CampaniaCampaniaListener {
        fun alHacerClickEnVerHabilidadesReconocidas(campania: String)
        fun alHacerClickEnAsignarHabilidades()
        fun alHacerClickGuardar(acuerdo: AcuerdoModel)
        fun alHacerClickEliminar(acuerdo: AcuerdoModel)
    }
}
