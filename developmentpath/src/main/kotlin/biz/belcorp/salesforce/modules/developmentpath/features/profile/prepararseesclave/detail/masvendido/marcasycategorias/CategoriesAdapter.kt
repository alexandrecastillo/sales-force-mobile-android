package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.masvendido.marcasycategorias

import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.mobile.components.core.extensions.inflate
import biz.belcorp.salesforce.modules.developmentpath.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_category.view.iv_category_image
import kotlinx.android.synthetic.main.item_category.view.tv_product_name
import kotlinx.android.synthetic.main.item_category.view.tv_product_purchase_campaign
import kotlinx.android.synthetic.main.item_category.view.tv_product_units

class CategoryProductsAdapter :
    RecyclerView.Adapter<CategoryProductsAdapter.CategoryProductsViewHolder>() {

    private var items: MutableList<CategoryProductModel> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryProductsViewHolder {
        val view = parent.inflate(R.layout.item_category)
        return CategoryProductsViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryProductsViewHolder, position: Int) {
        val category = items[position]
        holder.bind(category)
    }

    override fun getItemCount(): Int = items.size

    fun update(categorias: List<CategoryProductModel>) {
        this.items = categorias.toMutableList()
        notifyDataSetChanged()
    }

    class CategoryProductsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(model: CategoryProductModel) = with(itemView) {
            tv_product_name.text = model.name
            tv_product_purchase_campaign.text = model.buyCampaigns
            tv_product_units.text = model.quantity.toString()

            if (model.image != null && model.image.photoProduct.isNotEmpty()) {
                Glide.with(context)
                    .load(model.image.photoProduct[0])
                    .placeholder(R.drawable.products_place_holder)
                    .into(iv_category_image)
            } else {
                iv_category_image.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.products_place_holder))
            }

        }
    }
}
