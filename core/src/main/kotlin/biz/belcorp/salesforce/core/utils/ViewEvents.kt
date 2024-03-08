package biz.belcorp.salesforce.core.utils

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Spinner
import androidx.viewpager.widget.ViewPager
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout

fun EditText.onTextChanged(onTextWatcher: (Editable) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) = Unit

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) = Unit

        override fun afterTextChanged(s: Editable) {
            onTextWatcher.invoke(s)
        }
    })
}

fun Spinner.setOnItemSelectedListener(onItemSelected: (position: Int) -> Unit) {
    onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(p0: AdapterView<*>?) = Unit

        override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
            onItemSelected.invoke(pos)
        }
    }
}

fun ViewPager.onPageSelected(onPageSelected: (position: Int) -> Unit) {
    addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) = Unit
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) = Unit

        override fun onPageSelected(position: Int) {
            onPageSelected(position)
        }
    })
}


fun AppBarLayout.onOffsetChanged(onOffsetChanged: (verticalOffset: Int) -> Unit) {
    addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
        onOffsetChanged.invoke(verticalOffset)
    })
}

fun TabLayout.onTabSelected(onTabSelected: (tab: TabLayout.Tab) -> Unit) {
    addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
        override fun onTabReselected(tab: TabLayout.Tab?) = Unit

        override fun onTabUnselected(tab: TabLayout.Tab?) = Unit

        override fun onTabSelected(tab: TabLayout.Tab) {
            onTabSelected.invoke(tab)
        }
    })
}

fun TabLayout.getSelectedTab(): TabLayout.Tab? {
    val position = selectedTabPosition
    return getTabAt(position)
}