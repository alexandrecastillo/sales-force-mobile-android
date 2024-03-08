package biz.belcorp.salesforce.modules.developmentpath.widgets

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import biz.belcorp.salesforce.modules.developmentpath.R

class CustomProgressView @JvmOverloads constructor(
        context: Context, private val attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var paintCirculo: Paint
    private var paintTexto: Paint
    private var paintArco: Paint
    private var pathCirculo: Path

    private var cuadradoProgreso = RectF()
    private val verde = "#38be62"
    private val rojo = "#e81c36"
    private val naranja = "#f5a623"
    private val gris = "#d6d6e4"
    private val stroke = 1f

    private val startAngle = 270f
    private val maxAngle: Float = 360f

    private val progresoNivelBajoHasta = 0.25f
    private val progresoNivelMedioHasta = 0.65f

    private val colorNivelBajo = Color.parseColor(rojo)
    private val colorNivelMedio = Color.parseColor(naranja)
    private val colorNivelAlto = Color.parseColor(verde)

    private var fontFamily: Typeface? = null

    private var colorArco = colorNivelBajo

    private val minProgress: Float = 0f
    private val maxProgress: Float = 1f
    var progress: Float = 0f

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        crearCuadradoProgreso()
        pintarTextoProgreso(canvas)
        pintarHuecoCirculo(canvas)
        pintarCirculo(canvas)
        pintarArcoPintado(canvas)
    }

    private fun crearCuadradoProgreso() {
        cuadradoProgreso = RectF(0f, 0f, width.toFloat(), width.toFloat())
    }

    private fun pintarTextoProgreso(canvas: Canvas?) {
        val progreso = obtenerProgresoParaTexto()
        val textBounds = Rect()
        paintTexto.getTextBounds(progreso, 0, progreso.length, textBounds)
        canvas?.drawText(progreso, width / 2f, (width / 2f) + textBounds.height() / 2, paintTexto)
    }

    private fun obtenerProgresoParaTexto(): String {
        return String.format("%d%%", (obtenerProgreso() * 100).toInt())
    }

    private fun obtenerProgreso(): Float {
        return (maxProgress.takeIf { progress >= maxProgress } ?: (progress))
    }

    fun setProgreso(progreso: Int, animar: Boolean = false, duration: Long = 3500) {
        progress = (progreso / 100f)
        animarProgreso(animar, duration)
    }

    fun animarProgreso(animated: Boolean, duracion: Long) {
        if (animated) {
            animar(duracion)
        } else {
            construirColores()
            invalidate()
        }
    }

    private fun animar(duracion: Long) {
        ValueAnimator.ofFloat(minProgress, progress).apply {
            duration = duracion
            interpolator = FastOutSlowInInterpolator()
            addUpdateListener {
                progress = it.animatedValue as Float
                construirColores()
                invalidate()
            }
            start()
        }
    }

    private fun construirColores() {
        colorArco = when {
            progress <= progresoNivelBajoHasta -> colorNivelBajo
            progress <= progresoNivelMedioHasta -> colorNivelMedio
            else -> colorNivelAlto
        }
    }

    private fun pintarArcoPintado(canvas: Canvas?) {
        val progreso = obtenerProgresoParaPintar()
        paintArco.color = colorArco
        canvas?.drawArc(cuadradoProgreso, startAngle, progreso, true, paintArco)
    }

    private fun obtenerProgresoParaPintar(): Float {
        return maxAngle * progress / maxProgress
    }

    private fun pintarHuecoCirculo(canvas: Canvas?) {
        val radio = (width / 2) * 0.85f
        if (pathCirculo.isEmpty) {
            pathCirculo.addCircle(cuadradoProgreso.centerX(), cuadradoProgreso.centerY(), radio, Path.Direction.CW)
        }
        canvas?.clipPath(pathCirculo, Region.Op.DIFFERENCE)
    }

    private fun pintarCirculo(canvas: Canvas?) {
        val radio = width / 2f
        canvas?.drawCircle(cuadradoProgreso.centerX(), cuadradoProgreso.centerY(), radio, paintCirculo)
    }

    init {
        iniciarAtributos()
        paintCirculo = Paint().apply {
            color = Color.parseColor(gris)
            isAntiAlias = true
            strokeWidth = stroke
        }
        paintArco = Paint().apply {
            color = colorArco
            isAntiAlias = true
            strokeWidth = stroke
        }

        paintTexto = Paint().apply {
            color = Color.BLACK
            strokeWidth = 8f
            typeface = fontFamily
            textSize = 16f * resources.displayMetrics.density
            textAlign = Paint.Align.CENTER
        }

        pathCirculo = Path()
    }

    private fun iniciarAtributos() {
        val ta = context.theme.obtainStyledAttributes(attrs, R.styleable.CustomProgressView, 0, 0)

        val fontId = ta.getResourceId(R.styleable.CustomProgressView_fontFamily, -1)
        if (fontId != -1) {
            fontFamily = getFont(fontId)
        }

        ta.recycle()
    }

    private fun getFont(fontId: Int): Typeface? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            resources.getFont(fontId)
        } else {
            ResourcesCompat.getFont(context, fontId)
        }
    }
}
