package biz.belcorp.salesforce.modules.consultants.features.filters.baseunified.adapter.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.mobile.components.core.extensions.getColor
import biz.belcorp.mobile.components.core.extensions.setSafeOnClickListener
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.enviarAWhatsapp
import biz.belcorp.salesforce.core.utils.invisible
import biz.belcorp.salesforce.core.utils.llamarATelefono
import biz.belcorp.salesforce.core.utils.visible
import biz.belcorp.salesforce.modules.consultants.features.filters.baseunified.model.ConsultantModel
import kotlinx.android.synthetic.main.view_consultant_info.view.*

abstract class ConsultantViewHolder<M : ConsultantModel>(itemView: View) :
    RecyclerView.ViewHolder(itemView) {

    open fun bind(model: M, onClick: ((PersonIdentifier) -> Unit)?) {
        setupEvent(model, onClick)
        bindSegment(model)
        bindUser(model)
        bindNumbers(model)
        bindIndicator(model)
        bindAmounts(model)
        bindDescription(model)
        bindSellingAmounts(model)
        bindFlagMultiBrand(model)
    }

    private fun setupEvent(model: ConsultantModel, onClick: ((PersonIdentifier) -> Unit)?) {
        itemView.setSafeOnClickListener {
            val personIdentifier = PersonIdentifier(model.id, model.code, Rol.CONSULTORA)
            onClick?.invoke(personIdentifier)
        }
    }

    private fun bindSegment(model: M): Unit = with(itemView) {
        ivSegment?.apply {
            setColorFilter(getColor(model.colorSegment))
        }
        tvSegment?.apply {
            text = model.segment
            setTextColor(getColor(model.colorSegment))
        }
    }

    private fun bindUser(model: M): Unit = with(itemView) {
        tvUser?.text = model.name
    }

    private fun bindNumbers(model: M): Unit = with(itemView) {
        ivWhatsApp?.apply {
            if (model.whatsAppNumber.isNotEmpty()) visible() else invisible()
            setOnClickListener { context.enviarAWhatsapp(model.whatsAppNumber) }
        }
        ivPhoneNumber?.apply {
            if (model.phoneNumber.isNotEmpty()) visible() else invisible()
            setOnClickListener { context.llamarATelefono(model.phoneNumber) }
        }
    }

    open fun bindIndicator(model: M) = Unit

    open fun bindDescription(model: M) = Unit

    open fun bindAmounts(model: M) = Unit

    open fun bindSellingAmounts(model: M) = Unit

    open fun bindFlagMultiBrand(model: M) = Unit

}


