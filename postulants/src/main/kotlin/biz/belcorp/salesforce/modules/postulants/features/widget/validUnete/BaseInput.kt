package biz.belcorp.salesforce.modules.postulants.features.widget.validUnete

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Animatable
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import biz.belcorp.salesforce.core.utils.gone
import biz.belcorp.salesforce.core.utils.invisible
import biz.belcorp.salesforce.core.utils.visible
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.utils.Constant
import com.google.android.material.textfield.TextInputLayout

abstract class BaseInput(val mContext: Context, val attrs: AttributeSet) :
        LinearLayout(mContext, attrs), Input {

    lateinit var rootview: View

    var txtMesssage: TextView? = null
    var icValidation: ImageView? = null
    var txtInputLayout: TextInputLayout? = null

    protected var validateIconVisible: Boolean = true
    private var readOnly: Boolean = false
    protected var hint: String? = null
    protected var placeholder: String? = null
    private var validMsg: String? = null
    var required: Boolean? = false
    var state = NEUTRO

    var ta: TypedArray? = null

    var valids: MutableList<V> = mutableListOf()

    var mandatoryField = "*"

    private var hasPrefix: Boolean = false

    companion object {
        const val NEUTRO = 0
        const val OK = 1
        const val ERROR = 2
        const val VALIDATING = 3
    }

    init {
        init()
    }

    abstract fun initializeOthers()

    abstract fun getLayout(): Int

    abstract fun getValue(): String

    open fun init() {

        rootview = inflate(context, getLayout(), this)
        rootview.layoutParams =
            LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        txtMesssage = rootview.findViewById(R.id.txtMesssage )
        icValidation = rootview.findViewById(R.id.icValidation)
        txtInputLayout = rootview.findViewById(R.id.unete_input_txtInputLayout)

        initializeOthers()

        setAttributes()
        setEstado(NEUTRO, Constant.EMPTY_STRING)
        initEvents()

    }

    open fun setAttributes() {

        ta = context.theme.obtainStyledAttributes(attrs, R.styleable.UEditText, 0, 0)

        hasPrefix = ta?.getBoolean(R.styleable.UEditText_hasPrefix, hasPrefix) ?: false
        hint = ta?.getString(R.styleable.UEditText_hint)
        placeholder = ta?.getString(R.styleable.UEditText_placeholder)
        validMsg = ta?.getString(R.styleable.UEditText_valid_msg)
        readOnly = ta?.getBoolean(R.styleable.UEditText_readOnly, readOnly) ?: false
        validateIconVisible = ta?.getBoolean(R.styleable.UEditText_validateIconVisible, validateIconVisible) ?: true

        if (!validateIconVisible) {
            icValidation?.gone()
        }

        txtInputLayout?.hint = hint
        txtMesssage?.text = validMsg

    }

    fun setValidationIconVisibility(isVisible: Boolean) {
        if (isVisible)
            icValidation?.visible()
        else
            icValidation?.gone()

    }

    open fun initEvents() = Unit

    open fun setEstado(estado: Int, message: String = Constant.EMPTY_STRING) {
        when (estado) {
            NEUTRO -> {
                icValidation?.setImageResource(R.drawable.icon_validation_neutral)
                txtMesssage?.invisible()
                txtMesssage?.text = message
                state = NEUTRO
            }
            OK -> {
                icValidation?.setImageResource(R.drawable.icon_validation_ok)
                txtMesssage?.invisible()
                txtMesssage?.text = message
                state = OK
            }
            ERROR -> {
                icValidation?.setImageResource(R.drawable.icon_validation_error)
                txtMesssage?.visible()
                txtMesssage?.text = message
                state = ERROR
            }
            VALIDATING -> {
                (icValidation?.drawable as Animatable).start()
                txtMesssage?.invisible()
                txtMesssage?.text = message
                state = VALIDATING
            }
        }
    }

    fun setRequired(required: Boolean) {
        this.required = required
        if (required) {
            txtInputLayout?.hint = mandatoryField + hint
        } else {
            txtInputLayout?.hint = hint
        }
    }

    open fun validate(): Boolean {
        if (valids.isNotEmpty()) {
            when (required) {
                true -> valids.forEach {
                    if (!Validation.validate(it.tipo, getValue(), it.value)) {
                        setEstado(ERROR, it.errorMsg)
                        return false
                    }
                }
                else -> if (getValue().isNotBlank()) {
                    valids.forEach {
                        if (!Validation.validate(it.tipo, getValue(), it.value)) {
                            setEstado(ERROR, it.errorMsg)
                            return false
                        }
                    }
                }
            }
            setEstado(OK)
            return true
        }
        setEstado(NEUTRO)
        return true
    }

    fun validateAll(): Boolean {

        return true
    }

    fun addV(vararg v: V) {
        valids.addAll(v)
    }

    fun resetV() {
        valids.clear()
        setEstado(NEUTRO)
    }

    fun v(vararg v: V) {
        valids = v.toMutableList()
    }


    //endregion

}
