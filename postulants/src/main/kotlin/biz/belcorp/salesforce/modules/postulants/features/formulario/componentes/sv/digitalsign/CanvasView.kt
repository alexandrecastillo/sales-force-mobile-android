package biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.sv.digitalsign

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class CanvasView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var posx: Float? = 0F
    private var posy: Float? = 0F

    private var path: Path? = null
    private var paint: Paint? = null
    private var paths = ArrayList<Path>()
    private var paints = ArrayList<Paint>()
    private var canvas: Canvas? = null
    private var onSignDone = false

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        //DRAW STUFF HERE
        canvas?.drawColor(Color.WHITE)

        var i = 0
        for (trazo in paths) {
            canvas?.drawPath(trazo, paints[i++])
        }

        this@CanvasView.canvas = canvas
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        onSignDone = true

        posx = event?.x ?: 0F
        posy = event?.y ?: 0F

        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                paint = Paint().apply {
                    strokeWidth = 5F
                    setARGB(255, 0, 0, 0)
                    style = Paint.Style.STROKE
                    paints.add(this)
                }
                paints.add(paint!!)

                path = Path()
                path?.moveTo(posx!!, posy!!)
                paths.add(path!!)
            }

            MotionEvent.ACTION_MOVE, MotionEvent.ACTION_UP -> {
                val puntosHistoriscos = event.historySize

                var pos = 0
                while (pos < puntosHistoriscos) {
                    path?.lineTo(event.getHistoricalX(pos), event.getHistoricalY(pos))
                    pos++
                }
            }
        }

        invalidate()
        return true
    }

    fun clear() {
        onSignDone = false

        paths.clear()
        paints.clear()
        invalidate()
    }

    fun getOnSignDone() = onSignDone
}
