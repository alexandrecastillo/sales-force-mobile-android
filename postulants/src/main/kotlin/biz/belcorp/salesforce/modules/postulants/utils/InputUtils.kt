package biz.belcorp.salesforce.modules.postulants.utils

import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.widget.AdapterView
import android.widget.AutoCompleteTextView
import biz.belcorp.salesforce.core.utils.changeDateFormat
import biz.belcorp.salesforce.core.utils.formatShort2
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.UDatePicker
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.UEditText
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.USpinner


fun USpinner.onItemSelected(f: () -> Unit) {
    onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, v: View?, position: Int, id: Long) {
            f.invoke()
        }

        override fun onNothingSelected(parent: AdapterView<*>) {
            Constant.NOT_IMPLEMENTED
        }
    }
}

fun UEditText.onTextChanged(f: (Editable) -> Unit) {
    onTextWatcher = object : UEditText.OnTextWatcher {
        override fun afterTextChanged(s: Editable) {
            f.invoke(s)
        }
    }
}

fun UEditText.onEnterKeyListener(f: () -> Boolean) {
    setKeyListener(View.OnKeyListener { _, keyCode, event ->
        if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
            return@OnKeyListener f.invoke()
        }
        false
    })
}

fun AutoCompleteTextView.onTextChanged(f: () -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            Constant.NOT_IMPLEMENTED
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            Constant.NOT_IMPLEMENTED
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            f.invoke()
        }
    })
}

fun UDatePicker.getText(format: String): String? {
    return getText().changeDateFormat(formatShort2, format)
}

fun UDatePicker.onDateChanged(f: () -> Unit) {
    onDateListener = object : UDatePicker.OnDateListener {
        override fun onSet() {
            f.invoke()
        }
    }
}

inline fun <reified T> USpinner.selected(): T? {
    return selectedItem() as? T?
}



