package biz.belcorp.salesforce.modules.postulants.utils

import android.view.View
import android.view.ViewGroup
import android.widget.ListView

object FormUtils {

    fun setListViewHeightBasedOnChildren(listView: ListView?) {
        val listAdapter = listView?.adapter ?: return
        val desiredWidth =
            View.MeasureSpec.makeMeasureSpec(listView.width, View.MeasureSpec.AT_MOST)
        var totalHeight = Constant.NUMERO_CERO
        var view: View? = null
        for (i in Constant.NUMERO_CERO until listAdapter.count) {
            view = listAdapter.getView(i, view, listView)
            if (i == Constant.NUMERO_CERO) {
                view.layoutParams =
                    ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT)
            }
            view!!.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED)
            totalHeight += view.measuredHeight
        }
        val params = listView.layoutParams
        params.height = totalHeight + listView.dividerHeight * (listAdapter.count - 1)
        listView.layoutParams = params
        listView.requestLayout()
    }

}
