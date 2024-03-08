package biz.belcorp.salesforce.modules.consultants.features.list.adapters.holders

import android.view.View
import biz.belcorp.salesforce.modules.consultants.R
import biz.belcorp.salesforce.modules.consultants.features.list.adapters.OnConsultantItemSelected
import biz.belcorp.salesforce.modules.consultants.features.list.models.ConsultoraModel
import biz.belcorp.salesforce.modules.consultants.features.list.utils.agregarDigitoVerificador
import kotlinx.android.synthetic.main.container_view_grown_consultant_list.view.*

class GrownConsultantViewHolder(var view: View) : BaseGrownConsultantViewHolder(view) {

    fun bind(model: ConsultoraModel, listener: OnConsultantItemSelected?) {
        this.listener = listener
        this.model = model

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
            itemView.tvwBeautyConsultantConstancyVal?.text =
                handleConstancyValue(constancia.orEmpty())
            handleLevelView(nivel.orEmpty())
        }
    }


    override fun onItemViewSelected(model: ConsultoraModel) {
        listener?.onGrownConsultantItemSelected(model)
    }

    override fun onWhatsAppIconSelected(phoneNumber: String?) {
        listener?.onWhatsAppIconSelected(phoneNumber)
    }

    override fun onCallIconSelected(section: String?, phoneNumber: String?) {
        listener?.onCallIconSelected(section, phoneNumber)
    }

    private fun handleConstancyValue(constancy: String) = "$constancy/6"

}
