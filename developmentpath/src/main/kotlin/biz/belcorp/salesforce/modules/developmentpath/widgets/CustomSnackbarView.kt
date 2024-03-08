package biz.belcorp.salesforce.modules.developmentpath.widgets

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import biz.belcorp.salesforce.modules.developmentpath.R
import kotlinx.android.synthetic.main.layout_custom_snackbar.view.*

class CustomSnackbarView : LinearLayout {

    constructor(context: Context) : super(context) {
        init(context, null, 0, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs, 0, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs, defStyleAttr, 0)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    ) {
        init(context, attrs, defStyleAttr, defStyleRes)
    }

    fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {

        orientation = VERTICAL

        val attributes = context
            .obtainStyledAttributes(
                attrs,
                R.styleable.CustomSnackbarView,
                defStyleAttr,
                defStyleRes
            )

        val textoInformativo = attributes.getString(R.styleable.CustomSnackbarView_textoInformativo)
        val textoAccion = attributes.getString(R.styleable.CustomSnackbarView_textoAccion)

        LayoutInflater.from(context).inflate(
            R.layout.layout_custom_snackbar,
            this, true
        )

        tvTextoInformativo?.text = textoInformativo ?: "Texto informativo"
        tvTextoAccion?.text = textoAccion ?: "Texto de accion"
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
    }

    fun onClicEnAccion(block: () -> Unit) {
        tvTextoAccion?.setOnClickListener {
            block.invoke()
        }
    }
}
