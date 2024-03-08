package biz.belcorp.salesforce.base.features.home.menu.sidemenu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.base.R
import biz.belcorp.salesforce.base.features.home.menu.model.MenuOptionModel
import kotlinx.android.synthetic.main.item_side_menu.view.*


class SideMenuAdapter(private val dataSet: MutableList<MenuOptionModel> = mutableListOf()) :
    ListAdapter<MenuOptionModel, SideMenuAdapter.ViewHolder>(MenuOptionDiff) {

    var onClick: ((MenuOptionModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_side_menu, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = dataSet.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataSet[position]
        holder.bind(item)
    }

    fun setData(data: List<MenuOptionModel>) {
        this.dataSet.clear()
        this.dataSet.addAll(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: MenuOptionModel) = with(itemView) {
            tvTitle?.text = item.text
            tvDescription?.text = item.text
            ivIcon?.setImageResource(item.iconResId)
            setOnClickListener {
                onClick?.invoke(item)
            }
        }

    }

    object MenuOptionDiff : DiffUtil.ItemCallback<MenuOptionModel>() {
        override fun areItemsTheSame(oldItem: MenuOptionModel, newItem: MenuOptionModel) =
            oldItem.code == newItem.code

        override fun areContentsTheSame(oldItem: MenuOptionModel, newItem: MenuOptionModel) =
            oldItem == newItem
    }

}
