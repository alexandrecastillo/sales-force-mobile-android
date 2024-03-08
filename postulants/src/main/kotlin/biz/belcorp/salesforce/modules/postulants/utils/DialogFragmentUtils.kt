package biz.belcorp.salesforce.modules.postulants.utils

import android.view.ViewGroup
import androidx.fragment.app.DialogFragment


fun DialogFragment.fitFullScreen() {
    val width = ViewGroup.LayoutParams.MATCH_PARENT
    val height = ViewGroup.LayoutParams.MATCH_PARENT
    dialog?.window?.setLayout(width, height)
}
