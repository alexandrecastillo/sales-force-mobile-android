package biz.belcorp.salesforce.base.features.home.shortcuts

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.base.R
import biz.belcorp.salesforce.core.utils.inflate
import kotlinx.android.synthetic.main.item_home_shortcut.view.*

internal class ShortcutsAdapter(
    var onClick: (ShortcutModel) -> Unit = {}
) : ListAdapter<ShortcutModel, ShortcutViewHolder>(ShortcutDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShortcutViewHolder {
        val view = parent.inflate(R.layout.item_home_shortcut)
        return ShortcutViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: ShortcutViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

internal class ShortcutViewHolder(
    view: View,
    private val callback: (ShortcutModel) -> Unit
) : RecyclerView.ViewHolder(view) {
    fun bind(model: ShortcutModel) = with(itemView) {
        ivIcon?.setImageResource(model.iconRes)
        tvTitle?.text = model.text
        cvShortcut?.setOnClickListener { callback.invoke(model) }
    }
}

internal object ShortcutDiff : DiffUtil.ItemCallback<ShortcutModel>() {
    override fun areItemsTheSame(oldItem: ShortcutModel, newItem: ShortcutModel) =
        oldItem.code == newItem.code

    override fun areContentsTheSame(oldItem: ShortcutModel, newItem: ShortcutModel) =
        oldItem == newItem
}
