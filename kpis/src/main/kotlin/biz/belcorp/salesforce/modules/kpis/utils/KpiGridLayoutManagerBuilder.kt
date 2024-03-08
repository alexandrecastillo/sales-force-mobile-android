package biz.belcorp.salesforce.modules.kpis.utils

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ONE
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO

object KpiGridLayoutManagerBuilder {

    fun build(context: Context, spanCount: Int, allSame: Boolean = false): GridLayoutManager {
        return GridLayoutManager(context, spanCount).apply {
            spanSizeLookup = dynamicSpanSizeLookup(spanCount, allSame)
        }
    }

    private fun dynamicSpanSizeLookup(
        spanCount: Int,
        allSame: Boolean
    ): GridLayoutManager.SpanSizeLookup {
        return object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (position) {
                    NUMBER_ZERO -> if (allSame) DEFAULT_SPAN else spanCount
                    else -> DEFAULT_SPAN
                }
            }
        }
    }

    private const val DEFAULT_SPAN = NUMBER_ONE
}


