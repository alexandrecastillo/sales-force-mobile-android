package biz.belcorp.salesforce.core.utils

import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

fun DialogFragment.fitFullScreen() {
    val width = ViewGroup.LayoutParams.MATCH_PARENT
    val height = ViewGroup.LayoutParams.MATCH_PARENT
    dialog?.window?.setLayout(width, height)
}

fun DialogFragment.safeDismiss() {
    if (isResumed) {
        dismiss()
    }
}

fun DialogFragment.safeShow(manager: FragmentManager, tag: String?) {
    if (isAdded) {
        show(manager, tag)
    }
}
