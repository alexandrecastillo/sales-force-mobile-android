package biz.belcorp.salesforce.modules.postulants.features.widget.inputs

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.utils.gone
import biz.belcorp.salesforce.core.utils.imageResource
import biz.belcorp.salesforce.core.utils.visible
import biz.belcorp.salesforce.modules.postulants.R
import kotlinx.android.synthetic.main.input_unete_spinner.view.*

@Suppress("UNCHECKED_CAST")
class USpinner(mContext: Context, attrs: AttributeSet) : BaseInput(mContext, attrs) {

    private var hintText: String? = null
    private var hasSelection: Boolean = false
    private var promptText: String? = null
    private var validateIconVisible: Boolean = false

    private var isFirstValidation: Boolean = true

    private val itemList = mutableListOf<Any>()

    private var spinnerTouched = false

    override fun getLayout() = R.layout.input_unete_spinner

    override fun initAttributes() {

        val ta = context.theme.obtainStyledAttributes(attrs, R.styleable.USpinner, 0, 0)

        hintText = ta.getString(R.styleable.USpinner_hint).orEmpty()
        hasSelection = ta.getBoolean(R.styleable.USpinner_hasSelection, hasSelection)
        validateIconVisible = ta.getBoolean(R.styleable.USpinner_validateIconVisible, true)

        promptText = context.getString(R.string.default_prompt)

        ta.recycle()

    }

    override fun initView() {
        super.initView()
        txtHint?.text = hintText
        if (validateIconVisible) {
            icValidation?.visible()
        } else {
            icValidation?.gone()
        }
    }

    override fun initEvents() {
        super.initEvents()

        onItemSelected {

        }

    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        spnComponent?.isEnabled = enabled
    }

    override fun setIcon(resId: Int) {
        icValidation?.imageResource = resId
    }

    override fun setMessage(message: String) {
        txtMesssage?.text = message
    }

    override fun getText(): String? {
        val value = spnComponent?.selectedItem as? String
        return if (value == promptText) Constant.UNO_NEGATIVO.toString() else value
    }

    override fun onRequiredChanged(pref: String) {
        txtHint?.text = String.format("%s%s", pref, hintText)
    }

    fun <T> load(list: List<T>) {
        itemList.clear()
        itemList.addAll(list as List<Any>)
        val adapter = ArrayAdapter<Any>(context,
                R.layout.spinner_item_unete, prepareList())
        spnComponent?.adapter = adapter
    }

    fun <T> selection(f: (T?) -> Boolean) {
        val element = itemList.firstOrNull { f(it as? T) }
        val elementPosition = element?.let { itemList.indexOf(it) } ?: Constant.UNO_NEGATIVO
        selection(elementPosition)
    }

    fun <T> selected(): T? {
        val text = spnComponent?.selectedItem
        return itemList.find { it.toString() == text } as? T
    }

    fun onItemSelected(f: (fromUser: Boolean) -> Unit) {
        spnComponent?.setOnTouchListener { view, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                spinnerTouched = true
            }

            view.performClick()

            false
        }

        spnComponent?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                f.invoke(spinnerTouched)

                if(spinnerTouched) {
                    spinnerTouched = false
                }

                if (!isFirstValidation) {
                    validate()
                } else {
                    isFirstValidation = false
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) = Unit
        }
    }

    private fun prepareList(): List<String> {
        val list = itemList.map { it.toString() }.toMutableList()
        if (hasSelection) {
            list.add(0, promptText!!)
        }
        return list
    }

    private fun selection(position: Int) {
        if (hasSelection) {
            spnComponent?.setSelection(position + 1)
        } else {
            spnComponent?.setSelection(Math.max(position, 0))
        }
    }

    override fun getValueText(): String {return getText().toString()}

}
