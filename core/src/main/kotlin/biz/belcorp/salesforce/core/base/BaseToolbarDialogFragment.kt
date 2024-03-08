package biz.belcorp.salesforce.core.base

import android.os.Bundle
import android.view.View
import androidx.annotation.ColorInt
import androidx.appcompat.widget.Toolbar
import biz.belcorp.mobile.components.core.extensions.getColorAttr
import biz.belcorp.mobile.components.core.extensions.getDrawable
import biz.belcorp.mobile.components.core.extensions.tinted
import biz.belcorp.salesforce.core.R
import biz.belcorp.salesforce.core.utils.actionBar
import biz.belcorp.salesforce.core.utils.isColorLight
import com.google.android.material.appbar.MaterialToolbar

abstract class BaseToolbarDialogFragment : BaseDialogFragment() {

    abstract fun getToolbar(): MaterialToolbar?
    abstract fun getTitle(): String?
    abstract fun getMode(): Mode

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
    }

    override fun onStart() {
        super.onStart()
        fitFullScreen()
    }

    private fun setupToolbar() {
        actionBar(getToolbar()) {
            title = this@BaseToolbarDialogFragment.getTitle()
            val titleColor = getColorAttr(android.R.attr.textColorPrimary)
            val isTextLight = isColorLight(titleColor)
            when (getMode()) {
                Mode.RETURNABLE -> customizeReturnableMode(titleColor)
                Mode.CLOSEABLE -> post { customizeCloseableMode(isTextLight) }
            }
        }
    }

    private fun Toolbar.customizeReturnableMode(@ColorInt titleColor: Int) {
        val icon = getDrawable(R.drawable.ic_backspace)
        val iconTinted = icon?.tinted(titleColor)
        navigationIcon = iconTinted?.mutate()
        setNavigationOnClickListener { closeDialog() }
    }

    private fun Toolbar.customizeCloseableMode(isTextLight: Boolean) {
        val menu = if (isTextLight) {
            R.menu.menu_closeable_light
        } else {
            R.menu.menu_closeable_dark
        }
        inflateMenu(menu)
        setOnMenuItemClickListener {
            when (it.itemId) {
                android.R.id.home, R.id.menu_action_close -> {
                    closeDialog()
                    true
                }
                else -> false
            }
        }
    }

    enum class Mode { RETURNABLE, CLOSEABLE }

    companion object {
        inline fun <reified T : BaseToolbarDialogFragment> newInstance(): T {
            return T::class.java.newInstance()
        }
    }
}
