package biz.belcorp.salesforce.modules.postulants.features.widget.validUnete

import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.Gravity.TOP
import android.view.View
import android.view.inputmethod.EditorInfo
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.modules.postulants.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.input_unete_edittext.view.*

class UEditText(mContext: Context, attrs: AttributeSet) : BaseInput(mContext, attrs) {

    @JvmField
    var inputComponent: TextInputEditText? = null

    @JvmField
    var textInputLayoutComponent: TextInputLayout? = null

    var multiline: Boolean = false

    var onTextWatcher: OnTextWatcher? = null

    override fun init() {
        super.init()
        inputComponent?.id = View.generateViewId()
    }

    override fun initializeOthers() {
        inputComponent = unete_input_txtComponent
        textInputLayoutComponent = unete_input_txtInputLayout
    }

    override fun getValue() = inputComponent?.text.toString()

    override fun getLayout() = R.layout.input_unete_edittext

    fun setText(text: String?) {
        inputComponent?.setText(text)
    }

    fun setError(text: String?) {
        inputComponent?.requestFocus()
        inputComponent?.error = text
    }

    fun inputType(inputType: Int) {
        inputComponent?.inputType = inputType or InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
    }

    fun getText() = inputComponent?.text.toString()

    fun setRestriction(filters: Array<out InputFilter>) {
        inputComponent?.filters = filters
    }

    fun setRestriction(filters: InputFilter) {
        inputComponent?.filters = arrayOf(filters)
    }

    fun setR(filters: Array<out InputFilter>) {
        inputComponent?.filters = filters
    }

    fun setR(filters: InputFilter) {
        inputComponent?.filters = arrayOf(filters)
    }

    fun setTextWatcher(txtWatcher: TextWatcher) {
        inputComponent?.addTextChangedListener(txtWatcher)
    }

    fun setKeyListener(kl: View.OnKeyListener) {
        inputComponent?.setOnKeyListener(kl)
    }

    override fun initEvents() {
        inputComponent?.addTextChangedListener(
                object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) = Unit

                    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) = Unit

                    override fun afterTextChanged(s: Editable) {
                        validate()
                        onTextWatcher?.afterTextChanged(s)
                    }
                })

        inputComponent?.setOnFocusChangeListener { _, hasFocus ->
            if(placeholder.isNullOrBlank())
                return@setOnFocusChangeListener

            inputComponent?.hint = if (hasFocus) placeholder else Constant.EMPTY_STRING
        }
    }

    override fun setEnable(disable: Boolean) {
        inputComponent?.isEnabled = disable
    }

    override fun setAttributes() {
        super.setAttributes()
        multiline = ta?.getBoolean(R.styleable.UEditText_multiline, false) ?: false
        inputComponent?.inputType = InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
        if (multiline) setMultiline()
    }

     fun setCustomHint(hint: String){
         textInputLayoutComponent?.hint = hint
    }

    fun setCustomPlaceholder(placeholder: String) {
        this.placeholder = placeholder
    }

    fun clear(){
        inputComponent?.text?.clear()
    }

    private fun setMultiline() {

        inputComponent?.gravity = TOP
        inputComponent?.inputType = InputType.TYPE_TEXT_FLAG_MULTI_LINE or InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
        inputComponent?.setLines(3)
        inputComponent?.maxLines = 3
        inputComponent?.minLines = 3
        inputComponent?.isSingleLine = false
        inputComponent?.imeOptions = (EditorInfo.IME_FLAG_NO_ENTER_ACTION)

    }

    interface OnTextWatcher {
        fun afterTextChanged(s: Editable)
    }

}
