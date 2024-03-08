package biz.belcorp.salesforce.messaging.features.list.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.mobile.components.core.extensions.getColor
import biz.belcorp.mobile.components.core.extensions.setSafeOnClickListener
import biz.belcorp.salesforce.core.utils.backgroundColor
import biz.belcorp.salesforce.core.utils.inflate
import biz.belcorp.salesforce.messaging.R
import biz.belcorp.salesforce.messaging.features.list.model.NotificationModel
import kotlinx.android.synthetic.main.item_notification_messaging.view.*

class NotificationAdapter(
    private val onClick: (model: NotificationModel) -> Unit = { }
) : RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

    private val models = arrayListOf<NotificationModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            parent.inflate(R.layout.item_notification_messaging)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onClick)
    }

    override fun getItemCount() = models.size

    private fun getItem(position: Int) = models[position]

    fun submitList(data: List<NotificationModel>) {
        this.models.clear()
        this.models.addAll(data)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(model: NotificationModel, onClick: (model: NotificationModel) -> Unit) =
            with(itemView) {
                backgroundColor =
                    if (!model.seen) getColor(R.color.colorUnreadMessages)
                    else getColor(R.color.white)

                messageNotification?.text = model.message
                imgNotification?.apply {
                    loadIcon(model.icon)
                    setBackgoundColor(getColor(model.backgroundColor))
                    setIconColor(getColor(model.iconColor))
                }
                timeNotification?.text = model.elapsedTime
                itemNotification.setSafeOnClickListener {
                    onClick.invoke(model)
                }
            }
    }
}

