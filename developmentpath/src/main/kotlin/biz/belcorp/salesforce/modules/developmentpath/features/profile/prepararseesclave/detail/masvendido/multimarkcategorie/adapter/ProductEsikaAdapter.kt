package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.masvendido.multimarkcategorie.adapter

import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.core.utils.inflate
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.masvendido.productosmasvendidos.EsikaLbel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.view_product.view.campaign_product
import kotlinx.android.synthetic.main.view_product.view.iv_category_image
import kotlinx.android.synthetic.main.view_product.view.tv_product_name

class ProductEsikaAdapter :
    RecyclerView.Adapter<ProductEsikaAdapter.MultiMarkCampaignViewHolder>() {

    var items: List<EsikaLbel> = emptyList()

    fun actualizar(items: List<EsikaLbel>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MultiMarkCampaignViewHolder {
        val view = parent.inflate(R.layout.view_product)
        return MultiMarkCampaignViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MultiMarkCampaignViewHolder, position: Int) {
        holder.bind(items[position], position)
    }


    inner class MultiMarkCampaignViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: EsikaLbel, position: Int) = with(view) {

            var mssg = context.getString(R.string.compra_vibranza)

            when (position) {
                1 -> {
                    mssg = context.getString(R.string.compra_mega_full_size)
                }

                2 -> {
                    mssg = context.getString(R.string.compra_colorfix_duo_tattoo)
                }
            }

            tv_product_name.text = mssg

            if (item.images.photoProduct.isNotEmpty()) {
                Glide.with(context)
                    .load(item.images.photoProduct[0])
                    .placeholder(R.drawable.products_place_holder)
                    .into(iv_category_image)
            } else {
                iv_category_image.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.products_place_holder))
            }

            val adapter = ProductCampaignAdapter()
            campaign_product?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            campaign_product?.itemAnimator = DefaultItemAnimator()
            campaign_product?.adapter = adapter
            adapter.actualizar(item.histories)

        }

    }
}
