package biz.belcorp.salesforce.modules.postulants.features.adapter.validacionmensaje

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.modules.postulants.R
import kotlinx.android.synthetic.main.item_validacion_mensaje.view.*

class ValidacionesMensajesAdapter :
    ListAdapter<ValidacionMensajeModel, ValidacionesMensajesAdapter.ValidacionMensajeViewHolder>(
        MessageDiff
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ValidacionMensajeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_validacion_mensaje, parent, false)

        return ValidacionMensajeViewHolder(view)
    }

    override fun onBindViewHolder(holder: ValidacionMensajeViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class ValidacionMensajeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(model: ValidacionMensajeModel) {
            applyTextValues(itemView.tvwTitle, model.title)
            applyTextValues(itemView.tvwSubtitle, model.subtitle)
            applyTextValues(itemView.tvwDetail, model.description)
        }

    }

    private fun applyTextValues(
        textView: TextView,
        text: ValidacionMensajeModel.Text,
    ) {
        textView.text = text.value
        text.color?.let {
            textView.setTextColor(it)
        }
        textView.visibility = if (text.isVisible) View.VISIBLE else View.GONE
    }

    object MessageDiff : DiffUtil.ItemCallback<ValidacionMensajeModel>() {
        override fun areItemsTheSame(
            oldItem: ValidacionMensajeModel,
            newItem: ValidacionMensajeModel
        ) = oldItem.title == newItem.title &&
            oldItem.subtitle == newItem.subtitle &&
            oldItem.description == newItem.description

        override fun areContentsTheSame(
            oldItem: ValidacionMensajeModel,
            newItem: ValidacionMensajeModel
        ) = oldItem.title == newItem.title &&
            oldItem.subtitle == newItem.subtitle &&
            oldItem.description == newItem.description
    }

}
