package biz.belcorp.salesforce.modules.consultants.features.filters.baseunified.adapter

import androidx.recyclerview.widget.DiffUtil
import biz.belcorp.salesforce.modules.consultants.features.filters.baseunified.model.ConsultantModel

object SearchDiffUtil :
    DiffUtil.ItemCallback<ConsultantModel>() {

    override fun areItemsTheSame(
        oldConsultant: ConsultantModel,
        newConsultant: ConsultantModel
    ): Boolean {
        return oldConsultant == newConsultant
    }

    override fun areContentsTheSame(
        oldConsultant: ConsultantModel,
        newConsultant: ConsultantModel
    ): Boolean {
        return oldConsultant.code == newConsultant.code
    }

}
