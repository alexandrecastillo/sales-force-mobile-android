package biz.belcorp.salesforce.core.base

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import biz.belcorp.salesforce.core.utils.replaceOnce

abstract class FragmentCreator<T : FragmentParameters>(parameters: T) {

    protected var fm: FragmentManager? = null
    protected var fragmentTransaction: FragmentTransaction? = null
    protected var params: T = parameters
    protected var bundle: Bundle? = null

    private val tags = mutableListOf<String>()

    fun withManager(fm: FragmentManager): FragmentCreator<T> {
        this.fm = fm
        this.fragmentTransaction = fm.beginTransaction()
        this.bundle = bundleOf(FragmentParameters.key to params)
        return this
    }

    @IdRes
    protected abstract fun getHeaderId(): Int

    @IdRes
    protected abstract fun getContentId(): Int

    protected abstract fun replaceForDV(): FragmentCreator<T>
    protected abstract fun replaceForGR(): FragmentCreator<T>
    protected abstract fun replaceForGZ(): FragmentCreator<T>
    protected abstract fun replaceForSE(): FragmentCreator<T>

    abstract fun withRol(): FragmentCreator<T>

    open fun commit() {
        fragmentTransaction?.commit()
    }

    protected fun FragmentTransaction.replace(
        @IdRes containerViewId: Int,
        tag: String,
        block: () -> Fragment
    ): FragmentTransaction {
        replaceOnce(containerViewId, tag, bundle, fm) { block.invoke() }
        tags.add(tag)
        return this
    }

    fun clear() {
        val fragments = tags.mapNotNull { fm?.findFragmentByTag(it) }
        fm?.beginTransaction()?.apply {
            fragments.forEach { remove(it) }
        }?.commit()
        tags.clear()
    }

}
