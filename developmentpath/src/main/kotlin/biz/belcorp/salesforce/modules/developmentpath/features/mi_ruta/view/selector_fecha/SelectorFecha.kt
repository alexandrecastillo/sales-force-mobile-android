package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view.selector_fecha

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import biz.belcorp.salesforce.core.utils.parseToHourAmPm
import biz.belcorp.salesforce.core.utils.parseToLongestString
import biz.belcorp.salesforce.core.utils.parseToMinutes
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.widgets.DatetimePickerDialog
import biz.belcorp.salesforce.modules.developmentpath.widgets.SelectDatetimePicker
import kotlinx.android.synthetic.main.control_fecha.view.*
import java.util.*

class SelectorFecha @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), SelectDatetimePicker {

    var date = Date()
    var title = Constant.EMPTY_STRING

    init {
        val a = context.theme
            .obtainStyledAttributes(attrs, R.styleable.SelectorFecha, 0, 0)
        try {
            title = a.getString(R.styleable.SelectorFecha_title)!!
        } finally {
            a.recycle()
        }

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.control_fecha, this)
        setUpFecha(date)
        setUpActions()
    }

    private fun setUpFecha(date: Date) {
        val calendar = Calendar.getInstance()
        calendar.time = date
        text_registrar_visita_fecha_valor.text = date.parseToLongestString()
        text_registrar_visita_hora_valor.text = date.parseToHourAmPm()
        text_registrar_visita_minutos_valor.text = date.parseToMinutes()
        text_registrar_visita_ampm.text =
            if (calendar.get(Calendar.HOUR_OF_DAY) < 12) Constant.AM else Constant.PM
    }

    private fun setUpActions() {
        text_registrar_visita_fecha_valor.setOnClickListener {
            mostrarDateTimePicker()
        }

        text_registrar_visita_hora_valor.setOnClickListener {
            mostrarDateTimePicker()
        }

        text_registrar_visita_minutos_valor.setOnClickListener {
            mostrarDateTimePicker()
        }
    }

    override fun onSaveDatetime(datetime: Date) {
        date = datetime
        setUpFecha(date)
    }

    override fun onCancelDateTime() = Unit

    private fun mostrarDateTimePicker() {
        val date = DatetimePickerDialog.DialogBuilder(context!!)
            .setInitalDate(date)
            .setTitle(title)
            .setListener(this)
            .build()
        date.showDialog()
    }
}
