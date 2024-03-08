package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.section

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.mobile.components.core.extensions.inflate
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.utils.TipoFuente
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.general.TipData
import biz.belcorp.salesforce.modules.developmentpath.utils.MultiFontStyler
import kotlinx.android.synthetic.main.detalle_etiqueta_tips_desarrollo.view.*
import kotlinx.android.synthetic.main.detalle_parrafo_tips_desarrollo.view.*
import biz.belcorp.salesforce.base.R as BaseR

class TipsDesarrolloDetalleAdapter(private val tipoVista: TipData.Tipo) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val detalles = arrayListOf<TipDesarrolloModel.Detalle>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        crearPorTipoVista(parent, viewType)

    override fun getItemCount() = detalles.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolderEtiqueta -> holder.bind(detalles[position])
            is ViewHolderItem -> holder.bind(detalles[position])
            is ViewHolderParrafo -> holder.bind(detalles[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (tipoVista) {
            TipData.Tipo.ITEM -> TipData.Tipo.ITEM.valor
            TipData.Tipo.ETIQUETA -> TipData.Tipo.ETIQUETA.valor
            else -> TipData.Tipo.PARRAFO.valor
        }
    }

    private fun crearPorTipoVista(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TipData.Tipo.ITEM.valor -> ViewHolderItem(parent.inflate(R.layout.detalle_item_tips_desarrollo))
            TipData.Tipo.ETIQUETA.valor -> ViewHolderEtiqueta(parent.inflate(R.layout.detalle_etiqueta_tips_desarrollo))
            else -> ViewHolderParrafo(parent.inflate(R.layout.detalle_parrafo_tips_desarrollo))
        }
    }

    fun actualizar(detalles: List<TipDesarrolloModel.Detalle>) {
        this.detalles.clear()
        this.detalles.addAll(detalles)
        notifyDataSetChanged()
    }

    inner class ViewHolderItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(entidad: TipDesarrolloModel.Detalle) = with(itemView) {
            txtTipDescripcion?.text =
                crearTexto(context, entidad, txtTipDescripcion.currentTextColor)
        }
    }

    inner class ViewHolderParrafo(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(entidad: TipDesarrolloModel.Detalle) = with(itemView) {
            txtTipDescripcion?.text =
                crearTexto(context, entidad, txtTipDescripcion.currentTextColor)
        }
    }

    inner class ViewHolderEtiqueta(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(entidad: TipDesarrolloModel.Detalle) = with(itemView) {
            chipTipDescripcion?.text = entidad.descripcion
            chipTipDescripcion?.isChecked = validarCheckeado(entidad.colores)
        }

        private fun validarCheckeado(colores: List<Int>): Boolean {
            if (colores.isEmpty()) return false
            return colores[0] == BaseR.color.positivo
        }
    }

    private fun crearTexto(
        context: Context,
        entidad: TipDesarrolloModel.Detalle,
        colorActual: Int
    ) = MultiFontStyler.establecerTexto(entidad.descripcion)
        .establecerContexto(context)
        .establecerDelimitador(Constant.PIPE)
        .establecerColorPrimario(colorActual)
        .establecerColoresSencundarios(entidad.colores)
        .establecerFuentePrimaria(TipoFuente.LATO_REGULAR)
        .establecerFuenteSecundaria(TipoFuente.LATO_BOLD)
        .procesar()
}
