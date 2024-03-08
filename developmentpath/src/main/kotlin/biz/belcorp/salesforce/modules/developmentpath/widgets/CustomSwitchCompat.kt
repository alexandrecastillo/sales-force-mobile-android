package biz.belcorp.salesforce.modules.developmentpath.widgets

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import biz.belcorp.salesforce.modules.developmentpath.R
import kotlinx.android.synthetic.main.layout_custom_switch_compat.view.*

class CustomSwitchCompat : LinearLayout {

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(
            context,
            attrs,
            defStyleAttr,
            defStyleRes
    ) {
        init(context)
    }

    fun init(context: Context) {

        orientation = VERTICAL

        LayoutInflater.from(context).inflate(
            R.layout.layout_custom_switch_compat,
                this, true)
        switchCompat?.isChecked = false
    }

    private fun obtenerTituloSegunSwitch(activo: Boolean): Int {
        return if (activo) R.string.si else R.string.no
    }

    fun onSwitch(block: (isChecked: Boolean) -> Unit) {
        switchCompat?.setOnCheckedChangeListener { _, isChecked ->
            block.invoke(isChecked)
            cambiarTextoTitulo(isChecked)
        }
    }

    fun noChecked() {
        switchCompat?.isChecked = false
        cambiarTextoTitulo(false)
    }

    fun checked() {
        switchCompat?.isChecked = true
        cambiarTextoTitulo(true)
    }

    private fun cambiarTextoTitulo(checked: Boolean) {
        tvTitulo?.text = context.getString(obtenerTituloSegunSwitch(checked))
    }

}
