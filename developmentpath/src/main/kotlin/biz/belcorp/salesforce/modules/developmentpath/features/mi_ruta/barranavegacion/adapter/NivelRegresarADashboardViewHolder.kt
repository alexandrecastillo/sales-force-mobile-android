package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.barranavegacion.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.barranavegacion.NivelModel
import kotlinx.android.synthetic.main.item_barra_navegacion_rdd_regresar.view.*

class NivelRegresarADashboardViewHolder(private val regresarARaizListener: RegresarARaizListener?,
                                        itemView: View) :
        RecyclerView.ViewHolder(itemView),
        BarraNavegacionAdapter.BindableViewHolder {

    private val viewGroupRegresar: ViewGroup = itemView.layoutRegresarRuta

    override fun bind(nivelModel: NivelModel) {
        viewGroupRegresar.setOnClickListener {
            regresarARaizListener?.alClickearRegresarARaiz()
        }
    }
}
