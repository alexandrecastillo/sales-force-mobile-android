package biz.belcorp.salesforce.core.utils

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import biz.belcorp.salesforce.core.constants.Constant


fun Fragment.llamarATelefono(numero: String): Boolean {
    return activity?.llamarATelefono(numero) ?: false
}

fun Fragment.commitFragment(resId: Int, frag: Fragment) {
    this.childFragmentManager
        .beginTransaction()
        .replace(resId, frag, frag.javaClass.name)
        .commit()
}

inline fun Fragment.actionBar(
    toolbar: Toolbar?,
    hide: Boolean = false,
    predicate: Toolbar.() -> Unit = {}
) {
    toolbar?.let {
        if (hide) {
            toolbar.visibility = View.GONE; return
        }
        val activity = (activity as? AppCompatActivity)
        activity?.setSupportActionBar(it)
        activity?.supportActionBar?.title = Constant.EMPTY_STRING
        activity?.supportActionBar?.subtitle = Constant.EMPTY_STRING
        predicate.invoke(it)
    }
}

fun <T : Fragment> T.withArguments(vararg params: Pair<String, Any?>): T {
    arguments = bundleOf(*params)
    return this
}

fun Fragment.onBackPressedHandler(onBackPressed: (() -> Unit)? = null) {
    val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            onBackPressed?.invoke()
        }
    }
    activity?.onBackPressedDispatcher?.addCallback(this, callback)
}

inline fun FragmentTransaction.replaceOnce(
    @IdRes containerViewId: Int,
    tag: String,
    params: Bundle? = null,
    fm: FragmentManager?,
    block: () -> Fragment
): FragmentTransaction {
    val findFragment = fm?.findFragmentByTag(tag)
    if (findFragment == null) replace(
        containerViewId,
        block.invoke().apply { arguments = params },
        tag
    )
    return this
}

inline fun FragmentTransaction.replaceOnce(
    @IdRes containerViewId: Int,
    tag: String,
    fm: FragmentManager?,
    block: () -> Fragment
): FragmentTransaction {
    val findFragment = fm?.findFragmentByTag(tag)
    if (findFragment == null) replace(
        containerViewId,
        block.invoke(),
        tag
    )
    return this
}

fun Fragment.getCompatColor(id: Int) = requireContext().getCompatColor(id)

fun DialogFragment.showOnce(fm: FragmentManager) {
    val className = javaClass.simpleName
    if (fm.findFragmentByTag(className) == null) {
        show(fm, className)
    }
}

fun Fragment.requireGrandParentFragment(): Fragment {
    return requireParentFragment().requireParentFragment()
}
