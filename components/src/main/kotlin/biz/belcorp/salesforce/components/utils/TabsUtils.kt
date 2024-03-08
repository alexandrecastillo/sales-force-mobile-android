package biz.belcorp.salesforce.components.utils

import android.content.Context
import android.content.res.ColorStateList
import androidx.core.content.ContextCompat
import biz.belcorp.mobile.components.core.extensions.getColor
import biz.belcorp.salesforce.components.R
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_THREE
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.MODE_FIXED
import com.google.android.material.tabs.TabLayout.MODE_SCROLLABLE

fun TabLayout.createTabs(items: List<TabItem>) {
    for (item in items) {
        addTab(createTab(item))
    }
    tabMode = if (items.size > NUMBER_THREE) MODE_SCROLLABLE else MODE_FIXED
}

private fun TabLayout.createTab(item: TabItem): TabLayout.Tab {
    return newTab().apply {
        text = item.text
        tag = item.tag
        tabTextColors = ColorStateList(states(), colors(context))
        setSelectedTabIndicatorColor(getColor(R.color.colorDefaultBadge))
    }
}

fun TabLayout.updateTabWithBadge(position: Int) {
    val badgeDrawable = getTabAt(position)?.orCreateBadge
    badgeDrawable?.apply {
        backgroundColor = getColor(R.color.colorDefaultBadge)
    }
}


private fun states(): Array<IntArray> {
    return arrayOf(
        intArrayOf(android.R.attr.state_selected),
        intArrayOf(-android.R.attr.state_selected)
    )
}

private fun colors(context: Context): IntArray {
    return intArrayOf(
        ContextCompat.getColor(context, R.color.colorPrimaryDark),
        ContextCompat.getColor(context, R.color.textColorTernaryVariant)
    )
}

class TabItem(val text: String, val tag: String = EMPTY_STRING)
