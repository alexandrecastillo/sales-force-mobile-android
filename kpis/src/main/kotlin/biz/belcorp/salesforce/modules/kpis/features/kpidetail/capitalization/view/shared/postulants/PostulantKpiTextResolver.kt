package biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.view.shared.postulants

import android.content.Context
import biz.belcorp.salesforce.core.base.BaseTextResolver
import biz.belcorp.salesforce.modules.kpis.R

class PostulantKpiTextResolver(val context: Context) : BaseTextResolver() {

    fun formatHandleYourCapitalization(isParent: Boolean, vararg args: Any): String {
        val intRes = if (isParent) R.string.text_save_your_capitalization_in_campaign
        else R.string.text_push_its_capitalization_in_campaign

        val format = context.getString(intRes)
        return stringFormat(format, *args)
    }

    fun getPreApprovedText() = context.getString(R.string.text_pre_approved)

    fun getApprovedText() = context.getString(R.string.text_approved)

    fun getAnticipatedIncomesText() = context.getString(R.string.text_anticipates_incomes)

    fun getInEvaluationText() = context.getString(R.string.text_in_evaluation)

}
