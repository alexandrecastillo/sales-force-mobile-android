package biz.belcorp.salesforce.modules.postulants.utils

import android.content.Context
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.features.widget.dialogs.CustomDialog
import biz.belcorp.salesforce.modules.postulants.features.widget.dialogs.StandardDialog
import kotlinx.android.synthetic.main.dialog_material.*


fun Context.dialog(
    title: String? = null, content: String? = null,
    theme: Int = R.style.AppTheme_FlatDialog,
    init: (StandardDialog.() -> Unit)? = null
) = StandardDialog(this, theme).apply {
    titleText = title
    contentText = content
    init?.invoke(this)
}

fun Context.dialog1(init: (StandardDialog.() -> Unit)? = null) =
    dialog(init = init)

fun Context.dialog2(init: (StandardDialog.() -> Unit)? = null) =
    dialog( init = init)

fun Context.customDialog(layoutId: Int, init: (CustomDialog.() -> Unit)? = null) =
    CustomDialog(this, layoutId).apply {
        mOnCreated = init
    }


fun Context.solicitudRechazadaDialog(mensaje: String, f: () -> Unit) =
    customDialog(R.layout.dialog_material) {
        tvTitle.text = getString(R.string.solicitud_rechazada_title)
        tvContent.text = mensaje
        ivIcon.setImageResource(R.drawable.ic_warning_alert)
        btnOk.setText(R.string.accept)
        btnOk.setOnClickListener {
            dismiss()
            f.invoke()
        }
        setOnCancelListener {
            f.invoke()
        }
    }
