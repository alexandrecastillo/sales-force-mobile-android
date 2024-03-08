package biz.belcorp.salesforce.modules.billing.features.billing.view

import android.content.Context
import biz.belcorp.salesforce.core.base.BaseTextResolver
import biz.belcorp.salesforce.modules.billing.R

class BillingDetailTextResolver(val context: Context) : BaseTextResolver() {

    fun getNewCycleBillingTitle(type: Int): String {
        return context.getString(R.string.ixdx, type, type)
    }

    fun getNewCycleHighValueBillingTitle(type: Int): String {
        return context.getString(R.string.ixdx_high_value, type, type)
    }

    fun getNewCycleBillingDescription(vararg args: Any?): String {
        val format = context.getString(R.string.description_billing_new_cycle)
        return stringFormat(format, *args)
    }

    fun getNumberWithPercentage(vararg args: Any?): String {
        val format = context.getString(R.string.all_number_with_percentage)
        return stringFormat(format, *args)
    }

    fun getRetainedPegsTitle(isThirdPerson: Boolean): String {
        return if (isThirdPerson) {
            context.getString(R.string.title_billing_retained_peg_third_person)
        } else {
            context.getString(R.string.title_billing_retained_peg)
        }
    }

    fun getNewCycleTitle(isThirdPerson: Boolean): String {
        return if (isThirdPerson) {
            context.getString(R.string.title_billing_newcycle_third_person)
        } else {
            context.getString(R.string.title_billing_newcycle)
        }
    }

    fun getRetainedPegsDescription(vararg args: Any?): String {
        val format = context.getString(R.string.description_billing_pending_pegs)
        return stringFormat(format, *args)
    }

    fun getRetainedPegsMessage() = context.getString(R.string.message_billing_pending_pegs)

    fun getPendingPegsTargetDescription(vararg args: Any?): String {
        val format = context.getString(R.string.target_description_billing_pending_pegs)
        return stringFormat(format, *args)
    }
}
