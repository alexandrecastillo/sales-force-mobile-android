package biz.belcorp.salesforce.core.features.consultants

import biz.belcorp.salesforce.core.constants.Constant

interface OnConsultantsListener {
    fun getConsultants(ua: String)
    fun getGrownConsultants(
        filter: String,
        ua: String,
        typeSelection: Int = Constant.ONE_NEGATIVE
    )

    fun getEndPeriodConsultants(nivel: String = "%", section: String = "%")
}
