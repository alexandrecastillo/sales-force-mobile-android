package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.informacionhistorica.graficobarras.view

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.core.utils.inflate
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.informacionhistorica.graficobarras.model.ValorXCampania
import kotlinx.android.synthetic.main.item_body_table_venta_neta.view.*

class ValoresEnCuadroAdapter : RecyclerView.Adapter<ValoresEnCuadroAdapter.ViewHolder>() {

    private var valoresPorCampania = mutableListOf<ValorXCampania>()

    override fun getItemCount() = valoresPorCampania.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.item_body_table_venta_neta)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(valoresPorCampania[position])
    }

    fun actualizar(elements: List<ValorXCampania>) {
        valoresPorCampania = elements.toMutableList().asReversed()
        notifyDataSetChanged()
    }


    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val monto = view.txtMonto
        private val campaniaganancia = view.txtCampaniaItem
        private val fdv = view.txtFdV

        fun bind(model: ValorXCampania) {
            monto.text = model.valor
            campaniaganancia.text = model.campania
            fdv.text = model.porcentaje
        }
    }
}
