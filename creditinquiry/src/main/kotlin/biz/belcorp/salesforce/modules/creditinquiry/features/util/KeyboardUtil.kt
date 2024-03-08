package biz.belcorp.salesforce.modules.creditinquiry.features.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

object KeyboardUtil {

    @JvmStatic
    fun putEditTextCaretAtTheEnd(editText: EditText) {
        editText.setSelection(editText.text.length)
    }

    @JvmStatic
    fun dismissKeyboard(context: Context?, view: View?) {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

}
