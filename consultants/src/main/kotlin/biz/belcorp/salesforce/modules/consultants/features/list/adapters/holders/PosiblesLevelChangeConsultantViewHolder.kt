package biz.belcorp.salesforce.modules.consultants.features.list.adapters.holders

import android.graphics.Color
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.orZero
import biz.belcorp.salesforce.modules.consultants.R
import biz.belcorp.salesforce.modules.consultants.core.domain.constants.Constants
import biz.belcorp.salesforce.modules.consultants.features.list.adapters.OnConsultantItemSelected
import biz.belcorp.salesforce.modules.consultants.features.list.models.ConsultoraModel
import biz.belcorp.salesforce.modules.consultants.features.list.utils.agregarDigitoVerificador
import kotlinx.android.synthetic.main.container_view_grown_consultant_list.view.*
import kotlinx.android.synthetic.main.item_view_posible_level_changes_consultan_list.view.*

class PosiblesLevelChangeConsultantViewHolder(var view: View) :
    BaseGrownConsultantViewHolder(view) {


    private var currency = ""

    fun bind(currencySymbol: String, model: ConsultoraModel, listener: OnConsultantItemSelected?) {
        this.listener = listener
        this.model = model
        this.currency = currencySymbol

        setViewValues()
        setListeners()
    }

    private fun setViewValues() {
        model?.apply {
            itemView.tvConsultantName?.text = nombre
            itemView.tvwConsultantCodeVal?.text = String.format(
                itemView.context.getString(R.string.code),
                codigo.agregarDigitoVerificador(digitoVerificador)
            )

            val currencyValue = "$currency ${monto.orZero()}"
            val customText = "$currencyValue ${Constants.BEAUTY_CONSULTANT_TO_BE} $nivelSiguiente"
            val txtSpannable = SpannableString(customText)
            txtSpannable.setSpan(
                ForegroundColorSpan(Color.RED), 0, currencyValue.length,
                SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            itemView.tvwRemainingAmount?.text = txtSpannable

            handleLevelView(nivelActual.orEmpty())
        }
    }

    override fun onItemViewSelected(model: ConsultoraModel) {
        val personIdentifier = PersonIdentifier(
            model.consultorasId.toLong(),
            model.codigo ?: return,
            Rol.CONSULTORA
        )
        listener?.onConsultantItemSelected(personIdentifier)
    }

    override fun onWhatsAppIconSelected(phoneNumber: String?) {
        listener?.onWhatsAppIconSelected(phoneNumber)
    }

    override fun onCallIconSelected(section: String?, phoneNumber: String?) {
        listener?.onCallIconSelected(section, phoneNumber)
    }

}
