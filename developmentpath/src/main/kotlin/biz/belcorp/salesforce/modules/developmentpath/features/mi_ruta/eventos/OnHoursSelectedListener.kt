package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos

import java.util.*

interface OnHoursSelectedListener {
    fun onDatesSelected(startDate: Date, endDate: Date)
}
