package biz.belcorp.salesforce.modules.developmentpath.features.profile.charts.ranking.utils

import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.IValueFormatter
import com.github.mikephil.charting.utils.ViewPortHandler
import java.text.DecimalFormat


class NewValueFormatter {

    class NewValueFormatter : IValueFormatter {

        init {
            mFormat = DecimalFormat("#")
        }

        override fun getFormattedValue(
            value: Float,
            entry: Entry,
            dataSetIndex: Int,
            viewPortHandler: ViewPortHandler
        ): String {
            return requireNotNull(mFormat)
                .format(value.toDouble())
        }

    }

    companion object {

        private var mFormat: DecimalFormat? = null
        private var formatter: NewValueFormatter? = null

        fun newInstance(): NewValueFormatter {
            if (formatter == null) {
                formatter =
                    NewValueFormatter()
            }
            return requireNotNull(formatter)
        }

    }

}
