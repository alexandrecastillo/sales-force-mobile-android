package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.caminobrillante.progress

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.mobile.components.core.extensions.inflate
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.utils.TipoFuente
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.caminobrillante.progress.model.TipProgresoCaminoBrillanteModel
import biz.belcorp.salesforce.modules.developmentpath.utils.MultiFontStyler
import kotlinx.android.synthetic.main.item_tip_progreso_camino_brillante.view.*

class TipsProgresoCaminoBrillanteAdapter :
    RecyclerView.Adapter<TipsProgresoCaminoBrillanteAdapter.ViewHolder>() {

    private val tips = arrayListOf<TipProgresoCaminoBrillanteModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.item_tip_progreso_camino_brillante)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tips[position])
    }

    override fun getItemCount() = tips.size

    fun actualizar(tips: List<TipProgresoCaminoBrillanteModel>) {
        this.tips.clear()
        this.tips.addAll(tips)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(modelo: TipProgresoCaminoBrillanteModel) = with(itemView) {
            if (modelo.icon != -1) imgIcono?.setImageResource(modelo.icon)
            else imgIcono?.visibility = View.GONE
            txtTitulo?.text = MultiFontStyler.establecerTexto(modelo.texto)
                .establecerContexto(context)
                .establecerDelimitador(Constant.PIPE)
                .establecerColorPrimario(txtTitulo.currentTextColor)
                .establecerColoresSencundarios(modelo.colores)
                .establecerFuentePrimaria(TipoFuente.LATO_REGULAR)
                .establecerFuenteSecundaria(TipoFuente.LATO_BOLD)
                .procesar()
        }
    }
}
