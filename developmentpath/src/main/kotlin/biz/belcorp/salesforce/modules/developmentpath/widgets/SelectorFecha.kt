package biz.belcorp.salesforce.modules.developmentpath.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.utils.parseToTriple
import biz.belcorp.salesforce.modules.developmentpath.R
import kotlinx.android.synthetic.main.fecha_picker.view.*
import java.util.*

class SelectorFecha @JvmOverloads constructor(context: Context,
                                              attrs: AttributeSet? = null,
                                              defStyleAttr: Int = 0) :
    ConstraintLayout(context, attrs, defStyleAttr) {

    val fechaSeleccionadaCalendar: Calendar get() {
        val calendarResult = Calendar.getInstance()

        with(calendarResult) {
            set(Calendar.YEAR, number_picker_year.value)
            set(Calendar.MONTH, number_picker_month.value - 1)
            set(Calendar.DAY_OF_MONTH, number_picker_day.value)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        return calendarResult
    }

    val fechaSeleccionada: Date get() = fechaSeleccionadaCalendar.time

    private val calendar = Calendar.getInstance()

    var title = Constant.EMPTY_STRING

    init {
        val a = context.theme
            .obtainStyledAttributes(attrs, R.styleable.SelectorFecha, 0, 0)
        try {
            title = Constant.EMPTY_STRING
        } finally {
            a.recycle()
        }

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.fecha_picker, this)
        darFormatoDosDigitos()
        asignarValoresMinMax()
        setearFechaInicial(Date())
        escucharCambios()
    }

    private fun darFormatoDosDigitos() {
        number_picker_day.setFormatter { String.format("%02d", it) }
        number_picker_month.setFormatter { String.format("%02d", it) }
    }

    private fun asignarValoresMinMax() {

        number_picker_day.minValue = 1
        number_picker_day.maxValue = 31
        number_picker_month.minValue = 1
        number_picker_month.maxValue = 12
        number_picker_year.minValue = Calendar.getInstance().get(Calendar.YEAR)
        number_picker_year.maxValue = Calendar.getInstance().get(Calendar.YEAR) + 1
    }

    private fun setearFechaInicial(fecha: Date) {
        val (day, month, year) = fecha.parseToTriple()
        number_picker_year.value = year.toInt()
        number_picker_month.value = month.toInt()
        number_picker_day.value = day.toInt()

        val calendar = Calendar.getInstance()
        number_picker_day.maxValue = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    }

    private fun escucharCambios() {
        /** Evento de cambio de mes : */
        number_picker_month.setOnValueChangedListener { _, _, newVal ->
            calendar.set(Calendar.MONTH, newVal - 1)
            calendar.set(Calendar.YEAR, number_picker_year.value)
            val maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
            number_picker_day.maxValue = maxDays
        }

        /** Evento de cambio de año : */
        number_picker_year.setOnValueChangedListener { _, _, newVal ->
            calendar.set(Calendar.MONTH, number_picker_month.value - 1)
            calendar.set(Calendar.YEAR, newVal)
            val maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
            number_picker_day.maxValue = maxDays
        }
    }
}
