package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.barranavegacion.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.barranavegacion.NivelModel

class BarraNavegacionAdapter(
        expandirListener: ExpandirListener?,
        regresarUnNivelListener: RegresarUnNivelListener?,
        salirListener: SalirListener?,
        regresarARaizListener: RegresarARaizListener?) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val viewHolderFactory = NivelViewHolderFactory(expandirListener,
            regresarUnNivelListener,
            salirListener,
            regresarARaizListener)

    private var niveles = emptyList<NivelModel>()

    fun actualizar(niveles: List<NivelModel>) {
        this.niveles = niveles
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return niveles.size
    }

    override fun getItemViewType(position: Int): Int {
        return mapearItemViewType(niveles[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return viewHolderFactory.crear(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? BindableViewHolder)?.bind(niveles[position])
    }

    private fun mapearItemViewType(modelo: NivelModel): Int {
        return when (modelo) {
            is NivelModel.RegresarADashboard -> NIVEL_REGRESAR_A_DASHBOARD
            is NivelModel.UaRegresable -> NIVEL_INTERMEDIO
            is NivelModel.UaExpandible -> NIVEL_EXPANDIBLE
            is NivelModel.Ua -> NIVEL_PROPIO
        }
    }

    companion object {
        const val NIVEL_REGRESAR_A_DASHBOARD = 0
        const val NIVEL_INTERMEDIO = 1
        const val NIVEL_PROPIO = 2
        const val NIVEL_EXPANDIBLE = 3
    }

    interface BindableViewHolder {
        fun bind(nivelModel: NivelModel)
    }
}
