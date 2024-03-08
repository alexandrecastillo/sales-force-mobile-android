package biz.belcorp.salesforce.modules.developmentpath.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import biz.belcorp.salesforce.core.utils.parseToHour
import biz.belcorp.salesforce.core.utils.parseToHourAmPm
import biz.belcorp.salesforce.core.utils.parseToMinutes
import biz.belcorp.salesforce.modules.developmentpath.R
import kotlinx.android.synthetic.main.hora_picker.view.*
import java.util.*

class SelectorHora @JvmOverloads constructor(context: Context,
                                             attrs: AttributeSet? = null,
                                             defStyleAttr: Int = 0) :
        ConstraintLayout(context, attrs, defStyleAttr) {

    val horaSeleccionadaCalendar: Calendar get() = obtenerHora()

    val horaSeleccionada: Date get() = obtenerHora().time

    var title = ""

    init {
        val a = context.theme
                .obtainStyledAttributes(attrs, R.styleable.SelectorFecha, 0, 0)
        try {
            title = ""
        } finally {
            a.recycle()
        }

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.hora_picker, this)

        darFormatoDosDigitos()
        asignarValoresMinMax()
        setearFecha(Date())
    }

    private fun darFormatoDosDigitos() {
        number_picker_minute.setFormatter { String.format("%02d", it) }
    }

    private fun asignarValoresMinMax() {
            number_picker_ampm.minValue = 0
            number_picker_ampm.maxValue = 1
            number_picker_ampm.displayedValues = arrayOf("AM", "PM")
            number_picker_hour.minValue = 1
            number_picker_hour.maxValue = 12
            number_picker_minute.minValue = 0
            number_picker_minute.maxValue = 59
    }

    private fun setearFecha(fecha: Date) {

            number_picker_hour.value = fecha.parseToHourAmPm().toInt()
            number_picker_ampm.value = if (fecha.parseToHour().toInt() < 12) 0 else 1
            number_picker_minute.value = fecha.parseToMinutes().toInt()
    }

    private fun obtenerHora(): Calendar {

            val calendarResult = Calendar.getInstance()

            val hour =
                    if (number_picker_hour.value == 12)
                        if (number_picker_ampm.value == 0) 0
                        else number_picker_hour.value
                    else
                        if (number_picker_ampm.value == 0) number_picker_hour.value
                        else number_picker_hour.value + 12

            calendarResult.set(Calendar.HOUR_OF_DAY, hour)
            calendarResult.set(Calendar.MINUTE, number_picker_minute.value)
            calendarResult.set(Calendar.SECOND, 0)
            calendarResult.set(Calendar.MILLISECOND, 0)

            return calendarResult
    }
}
