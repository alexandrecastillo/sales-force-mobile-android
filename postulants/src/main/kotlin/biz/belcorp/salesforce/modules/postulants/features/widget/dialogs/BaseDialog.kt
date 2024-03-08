package biz.belcorp.salesforce.modules.postulants.features.widget.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.WindowManager
import biz.belcorp.salesforce.core.utils.displayMetrics
import biz.belcorp.salesforce.modules.postulants.R

abstract class BaseDialog(context: Context, val theme: Int) : Dialog(context, theme) {

    protected var cancelButtonBackground = R.drawable.background_button_style_1
    protected var cancelTextColor = Color.WHITE
    protected var confirmButtonBackground = R.drawable.background_button_style_1
    protected var confirmTextColor = Color.WHITE

    var allowCancel = false
    var allowCancelOnTouchOutside = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setCancelable(allowCancel)
        setCanceledOnTouchOutside(allowCancelOnTouchOutside)
        setContentView(layout)
        setThemeAttributes()
        resizeDialog()
    }

    private fun setThemeAttributes() {

        val ta = context.theme.obtainStyledAttributes(theme, R.styleable.FlatDialog)

        cancelButtonBackground =
            ta.getResourceId(R.styleable.FlatDialog_cancelButtonStyle, cancelButtonBackground)
        cancelTextColor = ta.getColor(R.styleable.FlatDialog_cancelTextStyle, cancelTextColor)
        confirmButtonBackground =
            ta.getResourceId(R.styleable.FlatDialog_confirmButtonStyle, confirmButtonBackground)
        confirmTextColor = ta.getColor(R.styleable.FlatDialog_confirmTextStyle, confirmTextColor)

        ta.recycle()
    }

    private fun resizeDialog() {
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(window?.attributes)
        lp.width = (context.displayMetrics.widthPixels * 0.85).toInt()
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        window?.attributes = lp
    }

    protected abstract val layout: Int

}
