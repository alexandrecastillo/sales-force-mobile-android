package biz.belcorp.salesforce.modules.postulants.features.widget.validUnete

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.EditText
import biz.belcorp.salesforce.core.utils.dayOfMonth
import biz.belcorp.salesforce.core.utils.minusYears
import biz.belcorp.salesforce.core.utils.month
import biz.belcorp.salesforce.core.utils.year
import biz.belcorp.salesforce.modules.postulants.R
import com.tsongkha.spinnerdatepicker.DatePicker
import com.tsongkha.spinnerdatepicker.DatePickerDialog
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder
import java.util.*

class UDatePicker(mContext: Context, attrs: AttributeSet) : BaseInput(mContext, attrs),
        DatePickerDialog.OnDateSetListener {

    @JvmField
    var inputComponent: EditText? = null

    var minDate: Calendar? = null
    var maxDate: Calendar? = null
    private var defaultDate = Calendar.getInstance().minusYears(EDAD_PROMEDIO)

    private var DFL_YEAR = 0
    private var DFL_MONTH = 0
    private var DFL_DAY = 0

    private var MIN_YEAR = 0
    private var MIN_MONTH = 0
    private var MIN_DAY = 0

    private var MAX_YEAR = 0
    private var MAX_MONTH = 0
    private var MAX_DAY = 0

    var onDateListener: OnDateListener? = null

    override fun init() {
        super.init()
        inputComponent?.id = View.generateViewId()
    }

    override fun initializeOthers() {
        inputComponent = rootview.findViewById<EditText>(R.id.unete_input_txtComponent )
    }

    override fun getValue(): String {
        return inputComponent?.text.toString()
    }

    override fun getLayout(): Int {
        return R.layout.input_unete_datepicker
    }

    fun setText(text: String?) {
        inputComponent?.setText(text)
    }

    fun inputType(inputType: Int) {
        inputComponent?.inputType = inputType
    }

    fun getText(): String {
        return inputComponent?.text.toString()
    }

    override fun initEvents() {

        inputComponent?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) = Unit

            override fun afterTextChanged(s: Editable) {
                validate()
            }
        })

        inputComponent?.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (hasFocus)
                openDatepicker()
        }

        inputComponent?.setOnClickListener {
            openDatepicker()
        }

        inputComponent?.keyListener = null

    }

    override fun setEnable(disable: Boolean) {
        inputComponent?.isEnabled = disable
    }

    private fun openDatepicker() {

        setDates()

        val dtp = SpinnerDatePickerDialogBuilder()
                .context(mContext)
                .callback(this@UDatePicker)
                .defaultDate(DFL_YEAR, DFL_MONTH, DFL_DAY)
                .spinnerTheme(R.style.unete_dtpicker_spinner)
                .dialogTheme(R.style.unete_dtpicker_dialog)


        if (maxDate != null) {
            dtp.maxDate(MAX_YEAR, MAX_MONTH, MAX_DAY)
        }
        if (minDate != null) {
            dtp.minDate(MIN_YEAR, MIN_MONTH, MIN_DAY)
        }

        val d = dtp.build()
        d.show()
        //d.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

    }

    override fun onDateSet(view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val d: String = (if (dayOfMonth < 10) "0" else "") + dayOfMonth
        val m: String = (if (monthOfYear < 9) "0" else "") + (monthOfYear + 1)
        val y: String = "" + year
        inputComponent?.setText(String.format("%s/%s/%s", d, m, y))

        DFL_YEAR = year
        DFL_MONTH = monthOfYear
        DFL_DAY = dayOfMonth

        onDateListener?.onSet()
    }

    private fun setDates() {

        DFL_YEAR = defaultDate.year
        DFL_MONTH = defaultDate.month
        DFL_DAY = defaultDate.dayOfMonth

        if (maxDate != null) {
            MAX_YEAR = maxDate?.year!!
            MAX_MONTH = maxDate?.month!!
            MAX_DAY = maxDate?.dayOfMonth!!
        }

        if (minDate != null) {
            MIN_YEAR = minDate?.year!!
            MIN_MONTH = minDate?.month!!
            MIN_DAY = minDate?.dayOfMonth!!
        }

    }

    interface OnDateListener {
        fun onSet()
    }

    companion object {

        const val EDAD_PROMEDIO = 35

    }

}
