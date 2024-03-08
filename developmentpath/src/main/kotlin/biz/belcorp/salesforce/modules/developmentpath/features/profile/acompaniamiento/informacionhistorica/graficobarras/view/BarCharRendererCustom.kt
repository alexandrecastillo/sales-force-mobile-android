package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.informacionhistorica.graficobarras.view

import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import com.github.mikephil.charting.animation.ChartAnimator
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.renderer.BarChartRenderer
import com.github.mikephil.charting.utils.Utils
import com.github.mikephil.charting.utils.ViewPortHandler

class BarCharRendererCustom(chart: BarDataProvider,
                            animator: ChartAnimator,
                            viewPortHandler: ViewPortHandler,
                            val radius: Float) :
        BarChartRenderer(chart, animator, viewPortHandler) {

    override fun drawDataSet(canvas: Canvas, dataSet: IBarDataSet, index: Int) {

        val trans = mChart.getTransformer(dataSet.axisDependency)

        mBarBorderPaint.color = dataSet.barBorderColor
        mBarBorderPaint.strokeWidth = Utils.convertDpToPixel(dataSet.barBorderWidth)

        val phaseX = mAnimator.phaseX
        val phaseY = mAnimator.phaseY

        // initialize the buffer
        val buffer = mBarBuffers[index]
        buffer.setPhases(phaseX, phaseY)
        buffer.setDataSet(index)
        buffer.setInverted(mChart.isInverted(dataSet.axisDependency))
        buffer.setBarWidth(mChart.barData.barWidth)

        buffer.feed(dataSet)

        trans.pointValuesToPixel(buffer.buffer)

        val isSingleColor = dataSet.colors.size == 1

        if (isSingleColor) {
            mRenderPaint.color = dataSet.color
        }


        var j = 0
        while (j < buffer.size()) {
            if (!mViewPortHandler.isInBoundsLeft(buffer.buffer[j + 2]))
                continue

            if (!mViewPortHandler.isInBoundsRight(buffer.buffer[j]))
                break

            if (!isSingleColor) {
                mRenderPaint.color = dataSet.getColor(j / 4)
            }
            canvas.drawPath(roundRectPath(buffer.buffer[j], buffer.buffer[j + 1], buffer.buffer[j + 2],
                    buffer.buffer[j + 3], radius), mRenderPaint)
            j += 4
        }
    }

    private fun roundRectPath(left: Float, top: Float, right: Float, bottom: Float, r: Float): Path {
        val roundRect = Path()
        val rect = RectF(0f, 0f, r, r)
        rect.offsetTo(right - r, bottom)
        roundRect.arcTo(rect, 0f, 90f)
        rect.offsetTo(left, bottom)
        roundRect.arcTo(rect, 90f, 90f)
        rect.offsetTo(left, top)
        roundRect.arcTo(rect, 180f, 90f)
        rect.offsetTo(right - r, top)
        roundRect.arcTo(rect, 270f, 90f)
        roundRect.close()
        return roundRect
    }
}
