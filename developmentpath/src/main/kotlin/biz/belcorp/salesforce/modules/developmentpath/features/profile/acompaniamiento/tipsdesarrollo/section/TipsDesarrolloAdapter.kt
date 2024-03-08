package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.section

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.mobile.components.core.extensions.inflate
import biz.belcorp.salesforce.components.utils.decoration.BoxOffsetDecoration
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.utils.reemplazarSaltoLineaPorEspacio
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_tips_desarrollo.view.*
import biz.belcorp.salesforce.base.R as BaseR


class TipsDesarrolloAdapter(
    private val onClickTip: (tip: TipDesarrolloModel) -> Unit = {}
) : RecyclerView.Adapter<TipsDesarrolloAdapter.ViewHolder>() {

    private val tipsDesarrollo = arrayListOf<TipDesarrolloModel>()
    private val recyclerPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.item_tips_desarrollo))
    }

    override fun getItemCount() = tipsDesarrollo.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tipsDesarrollo[position])
    }

    fun actualizar(tipsDesarrollo: List<TipDesarrolloModel>) {
        this.tipsDesarrollo.clear()
        this.tipsDesarrollo.addAll(tipsDesarrollo)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(entidad: TipDesarrolloModel) = with(itemView) {
            viewRipple.setOnClickListener { onClickTip(entidad) }
            val requestOptions = RequestOptions()
                .error(R.drawable.ic_upload_image)

            Glide.with(context)
                .load(entidad.imagen)
                .apply(requestOptions)
                .into(imgTip)
            txtTipTitulo?.text = entidad.tituto.reemplazarSaltoLineaPorEspacio()
            rvDetalle?.setHasFixedSize(true)
            rvDetalle?.layoutManager = LinearLayoutManager(context)
            rvDetalle?.addItemDecoration(
                BoxOffsetDecoration(
                    context,
                    BaseR.dimen.content_inset_small,
                    BaseR.dimen.zero
                )
            )
            rvDetalle?.setRecycledViewPool(recyclerPool)
            rvDetalle?.adapter = TipsDesarrolloDetalleAdapter(entidad.tipo).apply {
                actualizar(entidad.detalles)
            }
        }
    }
}
