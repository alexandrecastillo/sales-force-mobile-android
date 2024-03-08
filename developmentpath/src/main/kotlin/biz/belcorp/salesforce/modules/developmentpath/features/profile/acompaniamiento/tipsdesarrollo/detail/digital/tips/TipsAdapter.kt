package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.digital.tips

import android.content.res.Resources
import android.text.SpannableStringBuilder
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.mobile.components.design.counter.Counter
import biz.belcorp.mobile.components.offers.model.OfferModel
import biz.belcorp.mobile.components.core.extensions.inflate
import biz.belcorp.mobile.components.offers.offer.MiniOffer
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.utils.TipoFuente
import biz.belcorp.salesforce.core.utils.fadeAnimation
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.digital.tips.model.TipModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.ventaganancia.tips.mapper.MiniOfferModelMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.ventaganancia.tips.model.OfertaModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.ventaganancia.tips.model.TipOfertaModel
import biz.belcorp.salesforce.modules.developmentpath.utils.MultiFontStyler
import kotlinx.android.synthetic.main.item_tips.view.*
import kotlinx.android.synthetic.main.item_tips_offer.view.*

class TipsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    enum class ViewType { TYPE_NORMAL, TYPE_OFFER }

    var dataSet: MutableList<TipModel> = mutableListOf()
    var dataSetOffer: MutableList<TipOfertaModel> = mutableListOf()

    var callback: Callback? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ViewType.TYPE_OFFER.ordinal -> {
                val view = parent.inflate(R.layout.item_tips_offer)
                OfferHolder(view, callback)
            }
            else -> {
                val view = parent.inflate(R.layout.item_tips)
                NormalHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (isDataOffer(position)) {
            val positionX = position - (dataSet.size - 1)
            if (holder is OfferHolder) holder.bind(dataSetOffer[positionX - 1])
            return
        }
        if (holder is NormalHolder) holder.bind(dataSet[position])
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemViewType(position: Int): Int {
        if (isDataOffer(position))
            return ViewType.TYPE_OFFER.ordinal
        return ViewType.TYPE_NORMAL.ordinal
    }

    override fun getItemCount() = dataSet.size + dataSetOffer.size

    fun actualizarData(data: List<TipModel>) {
        this.dataSet.clear()
        this.dataSet.addAll(data)
        notifyDataSetChanged()
    }

    fun actualizarOffers(data: List<TipOfertaModel>) {
        this.dataSetOffer.clear()
        this.dataSetOffer.addAll(data)
        notifyDataSetChanged()
    }

    fun limpiarOffers() {
        this.dataSetOffer.clear()
        notifyDataSetChanged()
    }

    fun getTipsCount() = dataSet.size

    fun getTipsOfferCount() = dataSetOffer.size

    private fun isDataOffer(position: Int): Boolean {
        return position >= dataSet.size && dataSetOffer.isNotEmpty()
    }

    private abstract inner class ViewHolder<in T>(view: View) : RecyclerView.ViewHolder(view) {

        abstract fun bind(model: T)

        fun String.formatRich(colors: List<Int>): SpannableStringBuilder? {
            return MultiFontStyler.establecerTexto(this)
                .establecerContexto(itemView.context)
                .establecerDelimitador(Constant.PIPE)
                .establecerColorPrimario(itemView.message.currentTextColor)
                .establecerColoresSencundarios(colors)
                .establecerFuentePrimaria(TipoFuente.LATO_REGULAR)
                .establecerFuenteSecundaria(TipoFuente.LATO_BOLD)
                .procesar()
        }
    }

    private inner class NormalHolder(view: View) : ViewHolder<TipModel>(view) {
        override fun bind(model: TipModel) = with(itemView) {
            icon?.setImageResource(R.drawable.ic_star)
            message?.text = model.descripcion?.formatRich(model.colores)
        }
    }

    private inner class OfferHolder(view: View, val callback: Callback?) :
        ViewHolder<TipOfertaModel>(view), MiniOffer.Listener {

        private var ofertaModelList: MutableList<OfertaModel> = mutableListOf()
        private var resources: Resources = itemView.resources

        init {
            itemView.offer?.listener = this
        }

        override fun bind(model: TipOfertaModel): Unit = with(itemView) {
            ofertaModelList.clear()
            ofertaModelList.addAll(model.data)

            icon?.setImageResource(R.drawable.ic_star)
            message?.text = model.descripcion?.formatRich(model.colores)

            configurarOffers(model.data)

            fadeAnimation()
        }

        override fun didPressedItemButtonSelection(item: OfferModel, pos: Int, type: Any?) {
            callback?.onSharedClickItem(ofertaModelList[pos])
        }

        override fun didPressedItem(item: OfferModel, pos: Int, type: Any?) = Unit

        override fun didPressedItemButtonAdd(
            typeLever: String,
            keyItem: String,
            quantity: Int,
            counterView: Counter,
            type: Any?
        ) = Unit

        override fun didPressedItemButtonShowOffer(item: OfferModel, pos: Int, type: Any?) {
            callback?.onSharedClickItem(ofertaModelList[pos])
        }

        override fun impressionItems(typeLever: String, list: ArrayList<OfferModel>, type: Any?) =
            Unit

        private fun configurarOffers(data: List<OfertaModel>) {
            if (data.isNullOrEmpty()) {
                itemView.offer?.visibility = View.GONE
            } else {
                itemView.offer?.visibility = View.VISIBLE
                val offersList = MiniOfferModelMapper().map(data, resources)
                itemView.offer?.setProducts(ArrayList(offersList))
            }
        }
    }

    interface Callback {
        fun onSharedClickItem(model: OfertaModel)
    }
}
