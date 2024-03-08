package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos

import android.app.Dialog
import android.content.Context
import android.os.Build
import android.view.View
import biz.belcorp.salesforce.core.utils.parseToHour
import biz.belcorp.salesforce.core.utils.parseToHourAmPm
import biz.belcorp.salesforce.core.utils.parseToMinutes
import biz.belcorp.salesforce.modules.developmentpath.R
import kotlinx.android.synthetic.main.dialog_fragment_evento_hour_picker.*
import biz.belcorp.salesforce.core.utils.toast
import java.util.*

data class HoraPickerEventoDialog(val builder: DialogBuilder) {

    private lateinit var dateDialog: Dialog

    fun showDialog() {

        dateDialog = crearDialogoDependiendoDeVersionDeSistema()
        dateDialog.setContentView(R.layout.dialog_fragment_evento_hour_picker)

        ocultarFondosDeHoraYMinutosSiVersionInferiorALollipop(dateDialog)
        darFormatoDosDigitos(dateDialog)
        asignarValoresMinMax(dateDialog)
        setearFechaInicio(builder.startDate, dateDialog)
        setearFechaFin(builder.endDate, dateDialog)
        setupActions(dateDialog)

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
                fondo_horas_inicio.visibility = View.GONE
                fondo_minutos_inicio.visibility = View.GONE
                fondo_horas_fin.visibility = View.GONE
                fondo_minutos_fin.visibility = View.GONE
            }
        }
    }

    private fun darFormatoDosDigitos(dialog: Dialog) {
        with(dialog) {
            number_picker_minute_inicio.setFormatter { String.format("%02d", it) }
            number_picker_minute_fin.setFormatter { String.format("%02d", it) }
        }
    }

    private fun asignarValoresMinMax(dialog: Dialog) {
        with(dialog) {
            number_picker_ampm_inicio.minValue = 0
            number_picker_ampm_inicio.maxValue = 1
            number_picker_ampm_inicio.displayedValues = arrayOf("AM", "PM")
            number_picker_hour_inicio.minValue = 1
            number_picker_hour_inicio.maxValue = 12
            number_picker_minute_inicio.minValue = 0
            number_picker_minute_inicio.maxValue = 59

            number_picker_ampm_fin.minValue = 0
            number_picker_ampm_fin.maxValue = 1
            number_picker_ampm_fin.displayedValues = arrayOf("AM", "PM")
            number_picker_hour_fin.minValue = 1
            number_picker_hour_fin.maxValue = 12
            number_picker_minute_fin.minValue = 0
            number_picker_minute_fin.maxValue = 59

        }
    }

    private fun setearFechaInicio(fecha: Date, dialog: Dialog) {
        with(dialog) {
            number_picker_hour_inicio.value = fecha.parseToHourAmPm().toInt()
            number_picker_ampm_inicio.value = if (fecha.parseToHour().toInt() < 12) 0 else 1
            number_picker_minute_inicio.value = fecha.parseToMinutes().toInt()
        }
    }

    private fun setearFechaFin(fecha: Date, dialog: Dialog) {
        with(dialog) {
            number_picker_hour_fin.value = fecha.parseToHourAmPm().toInt()
            number_picker_ampm_fin.value = if (fecha.parseToHour().toInt() < 12) 0 else 1
            number_picker_minute_fin.value = fecha.parseToMinutes().toInt()
        }
    }

    private fun setupActions(dialog: Dialog) {
        with(dialog) {
            /** Guardar la selección de fecha: */
            button_fragm_datetime_picker_save.setOnClickListener {
                // Recuperamos el datetime con los valores seleccionados :
                val horaInicio = obtenerHoraInicio(this)
                val horaFin = obtenerHoraFin(this)

                if (!validarHoraFinMayorQueHoraInicio(horaInicio, horaFin)) {
                    context.toast(R.string.rdd_agregareventos_horapicker_error_comparacion)
                    return@setOnClickListener
                }

                builder.listener.onDatesSelected(horaInicio, horaFin)
                dateDialog.dismiss()

            }

            /** Cancelar la selección de fecha: */
            button_fragm_datetime_picker_back.setOnClickListener {
                dateDialog.dismiss()
            }
        }
    }

    private fun obtenerHoraInicio(dialog: Dialog): Date {
        with(dialog) {
            val calendarResult = Calendar.getInstance()

            val hour =
                    if (number_picker_hour_inicio.value == 12)
                        if (number_picker_ampm_inicio.value == 0) 0
                        else number_picker_hour_inicio.value
                    else
                        if (number_picker_ampm_inicio.value == 0) number_picker_hour_inicio.value
                        else number_picker_hour_inicio.value + 12

            calendarResult.set(Calendar.HOUR_OF_DAY, hour)
            calendarResult.set(Calendar.MINUTE, number_picker_minute_inicio.value)
            calendarResult.set(Calendar.SECOND, 0)
            calendarResult.set(Calendar.MILLISECOND, 0)

            return calendarResult.time
        }
    }

    private fun obtenerHoraFin(dialog: Dialog): Date {
        with(dialog) {
            val calendarResult = Calendar.getInstance()

            val hour =
                    if (number_picker_hour_fin.value == 12)
                        if (number_picker_ampm_fin.value == 0) 0
                        else number_picker_hour_fin.value
                    else
                        if (number_picker_ampm_fin.value == 0) number_picker_hour_fin.value
                        else number_picker_hour_fin.value + 12

            calendarResult.set(Calendar.HOUR_OF_DAY, hour)
            calendarResult.set(Calendar.MINUTE, number_picker_minute_fin.value)
            calendarResult.set(Calendar.SECOND, 0)
            calendarResult.set(Calendar.MILLISECOND, 0)

            return calendarResult.time
        }
    }

    private fun validarHoraFinMayorQueHoraInicio(horaInicio: Date, horaFin: Date): Boolean {
        return horaFin > horaInicio
    }

    data class DialogBuilder(var context: Context) {

        var startDate: Date = Date()
        var endDate: Date = Date()
        lateinit var listener: OnHoursSelectedListener

        fun setStartDate(startDate: Date): DialogBuilder {
            this.startDate = startDate
            return this
        }

        fun setEndDate(endDate: Date): DialogBuilder {
            this.endDate = endDate
            return this
        }

        fun setListener(listener: OnHoursSelectedListener): DialogBuilder {
            this.listener = listener
            return this
        }

        fun build(): HoraPickerEventoDialog {
            return HoraPickerEventoDialog(this)
        }
    }
}
