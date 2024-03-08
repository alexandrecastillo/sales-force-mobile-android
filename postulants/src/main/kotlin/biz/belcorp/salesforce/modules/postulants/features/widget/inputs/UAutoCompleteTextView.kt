package biz.belcorp.salesforce.modules.postulants.features.widget.inputs

import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import biz.belcorp.salesforce.core.utils.imageResource
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.utils.onTextChanged
import kotlinx.android.synthetic.main.input_unete_autocomplete.view.*
import java.util.*


class UAutoCompleteTextView(mContext: Context, attrs: AttributeSet) : BaseInput(mContext, attrs) {

    private var hintText: String? = null
    private var disableCopyPaste: Boolean = false

    private val itemList = mutableListOf<Any>()

    override fun getLayout() = R.layout.input_unete_autocomplete

    override fun initAttributes() {

        val ta =
            context.theme.obtainStyledAttributes(attrs, R.styleable.UAutoCompleteTextView, 0, 0)

        hintText = ta.getString(R.styleable.UAutoCompleteTextView_hint)

        disableCopyPaste = ta.getBoolean(R.styleable.UAutoCompleteTextView_disableCopyPaste, false)

        ta.recycle()

    }

    override fun initView() {
        super.initView()

        inputLayout?.hint = hintText
        if (disableCopyPaste) setDisableCopyPaste()

    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        inputComponent?.isEnabled = enabled
    }

    override fun setIcon(resId: Int) {
        icValidation?.imageResource = resId
    }

    override fun setMessage(message: String) {
        txtMesssage?.text = message
    }

    override fun getText(): String? {
        return inputComponent?.text?.toString()
    }

    fun <T> getValue(): T? {
        val text = inputComponent?.text.toString()
        return itemList.find { it.toString() == text } as? T
    }

    override fun onRequiredChanged(pref: String) {
        inputLayout?.hint = "$pref$hintText"
    }

    fun <T> setItems(list: List<T>) {
        itemList.clear()
        itemList.addAll(list as List<Any>)
        val adapter = UAutoCompleteAdapter(context,
            android.R.layout.simple_dropdown_item_1line,
            list.map { it.toString() })
        inputComponent?.setAdapter(adapter)
    }

    fun <T> getItems(): List<T> {
        return itemList as List<T>
    }

    fun onTextChanged(f: () -> Unit) {
        inputComponent?.onTextChanged(f)
    }

    fun setText(text: String?) {
        inputComponent.setText(text.orEmpty())
    }

    fun inputType(inputType: Int) {
        inputComponent?.inputType = inputType or InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
    }

    fun setDisableCopyPaste() {
        inputComponent?.customSelectionActionModeCallback = object : ActionMode.Callback {

            override fun onCreateActionMode(
                actionMode: ActionMode,
                menu: Menu
            ) = false

            override fun onPrepareActionMode(
                actionMode: ActionMode,
                menu: Menu
            ) = false

            override fun onActionItemClicked(
                actionMode: ActionMode,
                item: MenuItem
            ) = false

            override fun onDestroyActionMode(actionMode: ActionMode) = Unit
        }

        inputComponent?.isLongClickable = false
        inputComponent?.setTextIsSelectable(false)

    }

    fun setListFiltrer(arrayMails: Array<String>) {

        var adapter = ArrayAdapter(
            context, android.R.layout.simple_dropdown_item_1line, arrayMails
        )

        inputComponent.setAdapter(adapter)

        inputComponent.threshold = 3

        inputComponent.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                validate()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
                Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if (!s?.contains('@', false)!!) {
                    val list: ArrayList<String> = ArrayList()

                    for (mails in arrayMails) {
                        val emailNew: String = s.toString() + mails
                        list.add(emailNew)
                    }

                    adapter = ArrayAdapter<String>(
                        context,
                        android.R.layout.simple_dropdown_item_1line,
                        list
                    )

                    inputComponent.setAdapter(adapter)

                }
            }
        })

    }

    fun setR(filters: InputFilter) {
        inputComponent?.filters = arrayOf(filters)
    }

    fun setRestriction(filters: InputFilter) {
        inputComponent?.filters = arrayOf(filters)
    }

    override fun getValueText(): String {
        return inputComponent?.text.toString()
    }

}
