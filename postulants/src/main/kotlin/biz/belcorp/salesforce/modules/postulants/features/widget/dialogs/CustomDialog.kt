package biz.belcorp.salesforce.modules.postulants.features.widget.dialogs

import android.content.Context
import android.os.Bundle
import biz.belcorp.salesforce.modules.postulants.R


class CustomDialog(context: Context, private val layoutId: Int) :
    BaseDialog(context, R.style.AppTheme_FlatDialog) {

    override val layout: Int
        get() = layoutId

    var mOnCreated: (CustomDialog.() -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mOnCreated?.invoke(this)
    }

}
