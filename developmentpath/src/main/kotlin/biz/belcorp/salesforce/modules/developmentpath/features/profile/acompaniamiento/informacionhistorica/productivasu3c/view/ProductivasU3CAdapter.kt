package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.informacionhistorica.productivasu3c.view

import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.core.utils.inflate
import biz.belcorp.salesforce.core.utils.textColorResource
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.informacionhistorica.productivasu3c.model.ProductividadOCampaniaModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.informacionhistorica.productivasu3c.model.ZonaProductivaModel
import kotlinx.android.synthetic.main.item_productividad_region.view.*
import biz.belcorp.salesforce.base.R as BaseR

class ProductivasU3CAdapter : RecyclerView.Adapter<ProductivasU3CAdapter.ZonaProductivaHolder>() {

    private var zonasProductivas: List<ZonaProductivaModel> = emptyList()

    override fun onBindViewHolder(holder: ZonaProductivaHolder, position: Int) {
        if (position == 0) holder.bindTitulo(zonasProductivas[position])
        else holder.bind(zonasProductivas[position])
    }

    override fun getItemCount(): Int {
        return zonasProductivas.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ZonaProductivaHolder {
        val view = parent.inflate(R.layout.item_productividad_region)
        return ZonaProductivaHolder(view)
    }

    fun actualizar(zonas: List<ZonaProductivaModel>) {
        this.zonasProductivas = zonas
        notifyDataSetChanged()
    }

    inner class ZonaProductivaHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val textZona = view.textZona
        private val textUltimaCampania = view.textUltimaCampania
        private val textPenultimaCampania = view.textPenultimaCampania
        private val textAntepenultimaCampania = view.textAntepenultimaCampania

        fun bindTitulo(zonaProductividad: ZonaProductivaModel) {
            val font = ResourcesCompat.getFont(itemView.context, BaseR.font.mulish_bold)
            textZona.typeface = font
            bind(zonaProductividad)
        }

        fun bind(zonaProductividad: ZonaProductivaModel) {
            textZona.text = zonaProductividad.zona
            textUltimaCampania.text = zonaProductividad.productividadCampaniaUltima.valor
            textPenultimaCampania.text = zonaProductividad.productividadCampaniaPenultima.valor
            textAntepenultimaCampania.text = zonaProductividad.productividadCampaniaAntepenultima.valor
            textUltimaCampania.textColorResource = elegirColor(zonaProductividad.productividadCampaniaUltima)
            textPenultimaCampania.textColorResource = elegirColor(zonaProductividad.productividadCampaniaPenultima)
            textAntepenultimaCampania.textColorResource = elegirColor(zonaProductividad.productividadCampaniaAntepenultima)
        }
    }

    private fun elegirColor(productividad: ProductividadOCampaniaModel): Int {
        return when (productividad) {
            is ProductividadOCampaniaModel.Campania -> R.color.rdd_evento
            is ProductividadOCampaniaModel.Productiva -> R.color.estado_positivo
            is ProductividadOCampaniaModel.Estable -> R.color.rdd_evento
            is ProductividadOCampaniaModel.Critica -> R.color.estado_negativo
            else -> BaseR.color.black
        }
    }
}
