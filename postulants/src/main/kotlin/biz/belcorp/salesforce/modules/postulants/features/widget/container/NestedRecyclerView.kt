package biz.belcorp.salesforce.modules.postulants.features.widget.container

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.ListView

class NestedRecyclerView : ListView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    )

    private val maxItems = 99

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        var newHeight = 0
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        if (heightMode != MeasureSpec.EXACTLY) {
            val listAdapter = adapter
            if (listAdapter != null && !listAdapter.isEmpty) {
                var listPosition = 0
                while (listPosition < listAdapter.count && listPosition < maxItems) {
                    val listItem = listAdapter.getView(listPosition, null, this)
                    //now it will not throw a NPE if listItem is a ViewGroup instance
                    (listItem as? ViewGroup)?.layoutParams = LayoutParams(
                        LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT
                    )

                    listItem.measure(widthMeasureSpec, heightMeasureSpec)

                    newHeight += listItem.measuredHeight
                    listPosition++
                }
                newHeight += dividerHeight * listPosition
            }
            if (heightMode == MeasureSpec.AT_MOST && newHeight > heightSize && newHeight > heightSize) {
                newHeight = heightSize
            }
        } else {
            newHeight = measuredHeight
        }
        setMeasuredDimension(measuredWidth, newHeight)
    }
}
