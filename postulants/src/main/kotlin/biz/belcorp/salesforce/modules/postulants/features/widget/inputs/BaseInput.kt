package biz.belcorp.salesforce.modules.postulants.features.widget.inputs

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import biz.belcorp.salesforce.core.utils.invisible
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.V
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Validation
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Validation.Companion.NO_SELECTION
import biz.belcorp.salesforce.modules.postulants.utils.Constant
import biz.belcorp.salesforce.modules.postulants.utils.Constant.EMAIL_NO_COINCIDE
import biz.belcorp.salesforce.modules.postulants.utils.toUpperCase
import kotlinx.android.synthetic.main.input_unete_autocomplete.view.*
import kotlin.properties.Delegates

abstract class BaseInput(val mContext: Context, val attrs: AttributeSet) :
    LinearLayout(mContext, attrs) {

    private lateinit var rootview: View

    private val validationList = mutableListOf<V>()

    var required: Boolean by Delegates.observable(false, { _, _, newValue ->
        onRequiredChanged(if (newValue) "*" else Constant.EMPTY_STRING)
    })

    var isRequired: Boolean? = false
    var campo_obligatorio = "*"
    protected var hint: String? = null
    var ta: TypedArray? = null
    var valids: MutableList<V> = mutableListOf()

    companion object {

        const val NEUTRO = 0
        const val OK = 1
        const val ERROR = 2

    }

    init {
        init()
    }

    fun init() {
        initAttributes()
        initView()
        initEvents()
        setEstado(NEUTRO, Constant.EMPTY_STRING)
    }

    open fun initAttributes() = Unit

    open fun initView() {

        rootview = inflate(context, getLayout(), this)
        rootview.layoutParams =
            LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        ta = context.theme.obtainStyledAttributes(attrs, R.styleable.UAutoCompleteTextView, 0, 0)
        hint = ta?.getString(R.styleable.UAutoCompleteTextView_hint)

    }

    open fun initEvents() = Unit

    abstract fun getLayout(): Int

    abstract fun setIcon(@DrawableRes resId: Int)

    abstract fun setMessage(message: String)

    abstract fun getText(): String?

    abstract fun onRequiredChanged(pref: String)

    fun setEstado(estado: Int, message: String = Constant.EMPTY_STRING) {
        txtMesssage?.invisible()
        when (estado) {
            NEUTRO -> {
                setIcon(R.drawable.icon_validation_neutral)
                setMessage(Constant.EMPTY_STRING)
            }
            OK -> {
                setIcon(R.drawable.icon_validation_ok)
                setMessage(Constant.EMPTY_STRING)
            }
            ERROR -> {
                setIcon(R.drawable.icon_validation_error)
                txtMesssage?.visibility = View.VISIBLE
                setMessage(message)
            }
        }
    }

    fun validate(): Boolean {
        if (validationList.isNotEmpty()) {
            return validateText(required, validationList)
        }

        if (valids.isNotEmpty()) {
            isRequired?.let { return validateText(it, valids) }
        }

        setEstado(NEUTRO)
        return true
    }

    private fun validateText(required: Boolean, items: MutableList<V>): Boolean {
        val text = getText().orEmpty()
        if (required || text.isNotBlank()) {
            items.forEach {
                if (!Validation.validate(it.tipo, getValueText(), it.value)) {
                    setEstado(ERROR, it.errorMsg)
                    return false
                }
            }
        }
        setEstado(OK)
        return true
    }

    fun validateRepeatEmail(emailFirst: String): Boolean {
        if (valids.isNotEmpty()) {
            val text = getText().orEmpty()
            setEstado(OK)

            if (emailFirst.toUpperCase().trim() != text.toUpperCase().trim()) {
                setEstado(ERROR, EMAIL_NO_COINCIDE)
                return false
            }

            if (isRequired == true || text.isNotBlank()) {
                valids.forEach {
                    if (!Validation.validate(it.tipo, getValueText(), it.value)) {
                        setEstado(ERROR, it.errorMsg)
                        return false
                    }
                }
            }

            return true
        }

        setEstado(NEUTRO)
        return true
    }

    fun addValidations(vararg v: V) {
        validationList.clear()
        validationList.addAll(v)
    }

    fun setIsRequired(isRequired: Boolean) {
        this.isRequired = isRequired
        if (isRequired) {
            inputLayout?.hint = campo_obligatorio + hint
        } else {
            inputLayout?.hint = hint
        }
    }

    fun addV(vararg v: V) {
        valids.addAll(v)
    }

    fun resetV() {
        valids.clear()
        setEstado(NEUTRO)
    }

    abstract fun getValueText(): String


}
