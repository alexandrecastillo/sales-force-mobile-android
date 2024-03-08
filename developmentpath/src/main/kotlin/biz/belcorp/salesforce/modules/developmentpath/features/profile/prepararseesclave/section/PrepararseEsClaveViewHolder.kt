package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.section

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.base.R as BaseR
import biz.belcorp.mobile.components.core.extensions.getColor
import biz.belcorp.mobile.components.core.extensions.getDrawable
import biz.belcorp.salesforce.core.utils.backgroundColor
import biz.belcorp.salesforce.core.utils.backgroundDrawable
import biz.belcorp.salesforce.core.utils.imageResource
import biz.belcorp.salesforce.core.utils.textColor
import biz.belcorp.salesforce.modules.developmentpath.R
import kotlinx.android.synthetic.main.item_detalle_prepararse_es_clave.view.*
import kotlinx.android.synthetic.main.item_prepararse_es_clave.view.*

class PrepararseEsClaveViewHolder(
    view: View,
    private val selectable: PrepararseEsClaveAdapter.SelectableCallback?
) : RecyclerView.ViewHolder(view) {

    private lateinit var item: PrepararseEsClaveModel
    private lateinit var type: Type


    fun bind(item: PrepararseEsClaveModel, type: Type, selectedPosition: Int) {
        this.item = item
        this.type = type
        when (type) {
            Type.GENERAL -> bindGeneral()
            Type.DETALLE -> bindDetalle(selectedPosition)
        }
    }

    private fun bindGeneral() {
        itemView.container_item.setOnClickListener {
            selectable?.onSelectePrepararseEsClaveItem(item, type, adapterPosition)

        }
        itemView.icon?.imageResource = item.icon
        itemView.text_title?.text = item.titulo
    }

    fun bindDetalle(selectedPosition: Int) {
        itemView.clContenedor.setOnClickListener {
            selectable?.onSelectePrepararseEsClaveItem(item, type, adapterPosition)
        }

        if (!item.esVisible) itemView.visibility = View.GONE

        itemView.txtNombreOpcion?.text = item.titulo

        if (selectedPosition == adapterPosition) {
            estadoSeleccionado()
        } else {
            estadoOriginal()
        }
    }

    fun estadoOriginal() = with(itemView) {
        clContenedor?.backgroundColor = getColor(BaseR.color.white)
        clContenedor?.backgroundDrawable = getDrawable(BaseR.drawable.border_layout_black)
        txtNombreOpcion?.textColor = getColor(BaseR.color.black)
        vwUnderline?.visibility = View.GONE
    }

    fun estadoSeleccionado() = with(itemView) {
        clContenedor?.backgroundColor = getColor(BaseR.color.black)
        txtNombreOpcion?.textColor = getColor(BaseR.color.white)
        vwUnderline?.visibility = View.VISIBLE
    }

    enum class Type { GENERAL, DETALLE }
}
