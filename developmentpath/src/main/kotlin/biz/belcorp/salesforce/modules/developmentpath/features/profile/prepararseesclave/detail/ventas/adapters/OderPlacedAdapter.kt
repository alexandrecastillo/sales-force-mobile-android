package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.ventas.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.mobile.components.core.extensions.inflate
import biz.belcorp.salesforce.modules.developmentpath.R

class OderPlacedAdapter :
    RecyclerView.Adapter<OderPlacedAdapter.LevelRequestViewHolder>() {

    private val data: MutableList<String> = emptyList<String>().toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LevelRequestViewHolder {
        val view = parent.inflate(R.layout.item_order_placed)
        return LevelRequestViewHolder(view)
    }

    override fun onBindViewHolder(holder: LevelRequestViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size

    fun submitList(data: List<String>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    class LevelRequestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(data: String): Unit = with(itemView) {


        }
    }
}
