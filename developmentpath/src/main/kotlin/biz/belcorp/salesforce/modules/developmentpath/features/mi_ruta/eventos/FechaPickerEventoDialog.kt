package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos

import android.app.Dialog
import android.content.Context
import android.os.Build
import biz.belcorp.salesforce.core.utils.parseToTriple
import biz.belcorp.salesforce.modules.developmentpath.R
import kotlinx.android.synthetic.main.dialog_fragment_evento_date_picker.*
import java.util.*

data class FechaPickerEventoDialog(val builder: DialogBuilder) {

    private lateinit var dateDialog: Dialog
    private var calendar = Calendar.getInstance()

    fun showDialog() {

        dateDialog = crearDialogoDependiendoDeVersionDeSistema()
        dateDialog.setContentView(R.layout.dialog_fragment_evento_date_picker)

        darFormatoDosDigitos(dateDialog)
        asignarValoresMinMax(dateDialog)
        setearFechaInicial(builder.initialDate, dateDialog)
        escucharCambios(dateDialog)
        setupActions(dateDialog)
        calendar.time = builder.initialDate

        dateDialog.show()
    }

    private fun crearDialogoDependiendoDeVersionDeSistema(): Dialog {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Dialog(
                builder.context,
                android.R.style.Theme_Material_Light_NoActionBar_Fullscreen
            )
        } else {
            Dialog(
                builder.context,
                android.R.style.Theme_Light_NoTitleBar_Fullscreen
            )
        }
    }

    private fun darFormatoDosDigitos(dialog: Dialog) {
        with(dialog) {
            number_picker_day.setFormatter { String.format("%02d", it) }
            number_picker_month.setFormatter { String.format("%02d", it) }
        }
    }

    private fun asignarValoresMinMax(dialog: Dialog) {
        with(dialog) {
            number_picker_day.minValue = 1
            number_picker_day.maxValue = 31
            number_picker_month.minValue = 1
            number_picker_month.maxValue = 12
            number_picker_year.minValue = 2000
            number_picker_year.maxValue = 2060
        }
    }

    private fun setearFechaInicial(fecha: Date, dialog: Dialog) {
        with(dialog) {
            val (day, month, year) = fecha.parseToTriple()
            number_picker_year.value = year.toInt()
            number_picker_month.value = month.toInt()
            number_picker_day.value = day.toInt()
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
                }

                builder.listener.onDateSelected(calendarResult.time)
                dateDialog.dismiss()
            }

            /** Cancelar la selección de fecha: */
            button_fragm_datetime_picker_back.setOnClickListener {
                dateDialog.dismiss()
            }
        }
    }

    data class DialogBuilder(var context: Context) {

        var title: String = ""
        var initialDate: Date = Date()
        lateinit var listener: OnDateSelectedListener

        fun setInitalDate(initialDate: Date): DialogBuilder {
            this.initialDate = initialDate
            return this
        }

        fun setListener(listener: OnDateSelectedListener): DialogBuilder {
            this.listener = listener
            return this
        }

        fun build(): FechaPickerEventoDialog {
            return FechaPickerEventoDialog(this)
        }
    }
}
