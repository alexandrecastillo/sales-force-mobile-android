package biz.belcorp.salesforce.modules.developmentpath.widgets

import android.app.Dialog
import android.content.Context
import android.os.Build
import android.view.View
import biz.belcorp.salesforce.core.utils.parseToHour
import biz.belcorp.salesforce.core.utils.parseToHourAmPm
import biz.belcorp.salesforce.core.utils.parseToMinutes
import biz.belcorp.salesforce.core.utils.parseToTriple
import biz.belcorp.salesforce.modules.developmentpath.R
import kotlinx.android.synthetic.main.dialog_fragment_datetime_picker.*
import java.util.*

/**
 * Created by Oscar Sequeiros on 2/24/18.
 * Full dialog fragment para la selección de fecha y hora
 */

data class DatetimePickerDialog(val builder: DialogBuilder) {

    private lateinit var dateDialog: Dialog
    private var calendar = Calendar.getInstance()

    fun showDialog() {

        dateDialog = crearDialogoDependiendoDeVersionDeSistema()
        dateDialog.setContentView(R.layout.dialog_fragment_datetime_picker)

        ocultarFondosDeHoraYMinutosSiVersionInferiorALollipop(dateDialog)
        darFormatoDosDigitos(dateDialog)
        asignarValoresMinMax(dateDialog)
        setearTitulo(dateDialog)
        setearFechaInicial(builder.initialDate, dateDialog)
        escucharCambios(dateDialog)
        setupActions(dateDialog)
        calendar.time = builder.initialDate

        dateDialog.show()
    }

    private fun crearDialogoDependiendoDeVersionDeSistema(): Dialog {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Dialog(builder.context,
                    android.R.style.Theme_Material_Light_NoActionBar_Fullscreen)
        } else {
            Dialog(builder.context,
                    android.R.style.Theme_Light_NoTitleBar_Fullscreen)
        }
    }

    private fun ocultarFondosDeHoraYMinutosSiVersionInferiorALollipop(dialog: Dialog) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            with(dialog) {
                fondo_horas.visibility = View.GONE
                fondo_minutos.visibility = View.GONE
            }
        }
    }

    private fun darFormatoDosDigitos(dialog: Dialog) {
        with(dialog) {
            number_picker_day.setFormatter { String.format("%02d", it) }
            number_picker_month.setFormatter { String.format("%02d", it) }
            number_picker_hour.setFormatter { String.format("%02d", it) }
            number_picker_minute.setFormatter { String.format("%02d", it) }
        }
    }

    private fun asignarValoresMinMax(dialog: Dialog) {
        with(dialog) {
            number_picker_ampm.minValue = 0
            number_picker_ampm.maxValue = 1
            number_picker_ampm.displayedValues = arrayOf("AM", "PM")
            number_picker_day.minValue = 1
            number_picker_day.maxValue = 31
            number_picker_month.minValue = 1
            number_picker_month.maxValue = 12
            number_picker_year.minValue = 2000
            number_picker_year.maxValue = 2060
            number_picker_hour.minValue = 1
            number_picker_hour.maxValue = 12
            number_picker_minute.minValue = 0
            number_picker_minute.maxValue = 59
        }
    }

    private fun setearTitulo(dialog: Dialog) {
        dialog.text_fragm_datetime_picker_title.text =
                builder.context.getString(R.string.fragm_datetime_picker_title, builder.title)
    }

    private fun setearFechaInicial(fecha: Date, dialog: Dialog) {
        with(dialog) {
            val (day, month, year) = fecha.parseToTriple()
            number_picker_year.value = year.toInt()
            number_picker_month.value = month.toInt()
            number_picker_day.value = day.toInt()
            number_picker_hour.value = fecha.parseToHourAmPm().toInt()
            number_picker_ampm.value = if (fecha.parseToHour().toInt() < 12) 0 else 1
            number_picker_minute.value = fecha.parseToMinutes().toInt()
            val calendar = Calendar.getInstance()
            number_picker_day.maxValue = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        }
    }

    private fun escucharCambios(dialog: Dialog) {
        with(dialog) {
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

    private fun setupActions(dialog: Dialog) {
        with(dialog) {
            /** Guardar la selección de fecha: */
            button_fragm_datetime_picker_save.setOnClickListener {
                // Recuperamos el datetime con los valores seleccionados :
                val calendarResult = Calendar.getInstance()
                with(calendarResult) {
                    set(Calendar.YEAR, number_picker_year.value)
                    set(Calendar.MONTH, number_picker_month.value - 1)
                    set(Calendar.DAY_OF_MONTH, number_picker_day.value)
                    val hour =
                            if (number_picker_hour.value == 12)
                                if (number_picker_ampm.value == 0) 0
                                else number_picker_hour.value
                            else
                                if (number_picker_ampm.value == 0) number_picker_hour.value
                                else number_picker_hour.value + 12

                    set(Calendar.HOUR_OF_DAY, hour)
                    set(Calendar.MINUTE, number_picker_minute.value)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }
                val dateResult = Date()
                dateResult.time = calendarResult.timeInMillis
                builder.listener.onSaveDatetime(dateResult)
                dateDialog.dismiss()
            }

            /** Cancelar la selección de fecha: */
            button_fragm_datetime_picker_back.setOnClickListener {
                builder.listener.onCancelDateTime()
                dateDialog.dismiss()
            }
        }
    }

    data class DialogBuilder(var context: Context) {

        var title: String = ""
        var initialDate: Date = Date()
        lateinit var listener: SelectDatetimePicker

        fun setTitle(title: String): DialogBuilder {
            this.title = title
            return this
        }

        fun setInitalDate(initialDate: Date): DialogBuilder {
            this.initialDate = initialDate
            return this
        }

        fun setListener(listener: SelectDatetimePicker): DialogBuilder {
            this.listener = listener
            return this
        }

        fun build(): DatetimePickerDialog {
            return DatetimePickerDialog(this)
        }
    }
}
