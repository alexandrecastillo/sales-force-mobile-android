package biz.belcorp.salesforce.components.widgets

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatImageView
import biz.belcorp.salesforce.components.R
import kotlin.math.min

class CircularImageView @JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    private var mBackGroundColor = Color.TRANSPARENT
    private var mBorderColor = Color.TRANSPARENT
    private var mBorderWidth = 4.toFloat()
    private var canvasSize: Int = 0
    private var mColorFilter: ColorFilter? = null

    private lateinit var paint: Paint
    private lateinit var paintBorder: Paint
    private lateinit var paintBackground: Paint

    private var mImage: Bitmap? = null
    private var mDrawable: Drawable? = null

    init {
        init()
    }

    private fun init() {

        paint = Paint().apply { isAntiAlias = true }
        paintBorder = Paint().apply { isAntiAlias = true }
        paintBackground = Paint().apply { isAntiAlias = true }

        val attributes =
            context.theme.obtainStyledAttributes(attrs, R.styleable.CircularImageView, 0, 0)

        val borderColor =
            attributes.getColor(R.styleable.CircularImageView_borderColor, mBorderColor)
        val borderWidth =
            attributes.getDimension(R.styleable.CircularImageView_borderWidth, mBorderWidth)

        if (attributes.getBoolean(R.styleable.CircularImageView_borderColor, true)) {
            mBorderWidth = borderWidth
            paintBorder.apply { color = borderColor }
        }

        if (attributes.getBoolean(R.styleable.CircularImageView_backgroundColor, true)) {
            val bgColor =
                attributes.getColor(R.styleable.CircularImageView_backgroundColor, mBackGroundColor)
            paintBackground.apply { color = bgColor }
        }

        attributes.recycle()
    }

    fun setBorderWidth(borderWidth: Float) {
        mBorderWidth = borderWidth
        requestLayout()
        invalidate()
    }

    fun setBorderColor(@ColorInt borderColor: Int) {
        paintBorder.apply { color = borderColor }
        invalidate()
    }

    fun setBgColor(bgColor: Int) {
        paintBackground.apply { color = bgColor }
        invalidate()
    }

    private fun loadImageBitmap() {
        if (mDrawable === drawable) return
        mDrawable = drawable
        mImage = convertDrawableToBitmap(mDrawable)
        applyCircularShader()
    }

    private fun convertDrawableToBitmap(mDrawable: Drawable?): Bitmap? {
        if (mDrawable == null) return null
        else if (mDrawable is BitmapDrawable) return mDrawable.bitmap

        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        mDrawable.setBounds(0, 0, canvas.width, canvas.height)
        mDrawable.draw(canvas)
        return bitmap
    }

    private fun applyCircularShader() {
        mImage?.let {
            val shader = BitmapShader(it, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)

            var scale = 0f
            var dx = 0f
            var dy = 0f

            when (scaleType) {
                ScaleType.CENTER_CROP -> {
                    if (it.width * height > width * it.height) {
                        scale = height / it.height.toFloat()
                        dx = (width - it.width * scale) * 0.5f
                    } else {
                        scale = width / it.width.toFloat()
                        dy = (height - it.height * scale) * 0.5f
                    }
                }
                ScaleType.CENTER_INSIDE -> {
                    if (it.width * height < width * it.height) {
                        scale = height / it.height.toFloat()
                        dx = (width - it.width * scale) * 0.5f
                    } else {
                        scale = width / it.width.toFloat()
                        dy = (height - it.height * scale) * 0.5f
                    }
                }
                else -> Unit
            }

            val matrix = Matrix()
            matrix.apply {
                setScale(scale, scale)
                postTranslate(dx + 1, dy)
            }
            shader.setLocalMatrix(matrix)
            paint.shader = shader
            paint.colorFilter = colorFilter
        }
    }

    private fun drawCircularImage(canvas: Canvas) {
        if (mImage == null) return

        if (!isInEditMode) canvasSize = min(width, height)

        val circleImageCenter = (canvasSize - mBorderWidth * 2).toInt() / 2

        paintBorder.let {
            canvas.drawCircle(
                circleImageCenter + mBorderWidth,
                circleImageCenter + mBorderWidth,
                circleImageCenter + mBorderWidth,
                it
            )
        }

        paintBackground.let {
            canvas.drawCircle(
                circleImageCenter + mBorderWidth,
                circleImageCenter + mBorderWidth,
                circleImageCenter.toFloat(),
                it
            )
        }

        paint.let {
            canvas.drawCircle(
                circleImageCenter + mBorderWidth,
                circleImageCenter + mBorderWidth,
                circleImageCenter.toFloat(),
                it
            )
        }
    }

    private fun getMeasureSpec(measureSpec: Int, isHeight: Boolean): Int {
        val sumHeight = if (isHeight) 2 else 0
        return when {
            MeasureSpec.EXACTLY == MeasureSpec.getMode(measureSpec) ||
                MeasureSpec.AT_MOST == MeasureSpec.getMode(measureSpec) ->
                getMeasureSpecSize(measureSpec, sumHeight)
            else -> canvasSize + sumHeight
        }
    }

    private fun getMeasureSpecSize(measureSpec: Int, height: Int): Int {
        return MeasureSpec.getSize(measureSpec) + height
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val mWidth = getMeasureSpec(widthMeasureSpec, false)
        val mHeight = getMeasureSpec(heightMeasureSpec, true)
        setMeasuredDimension(mWidth, mHeight)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        loadImageBitmap()
        drawCircularImage(canvas)
    }

    override fun setColorFilter(colorFilter: ColorFilter) {
        if (mColorFilter === colorFilter) return
        mColorFilter = colorFilter
        mDrawable = null
        invalidate()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        canvasSize = min(w, h)
        mImage.apply { applyCircularShader() }
    }

    override fun getScaleType(): ScaleType {
        val scaleType = super.getScaleType()
        return if (scaleType == null || scaleType != ScaleType.CENTER_INSIDE) ScaleType.CENTER_CROP else scaleType
    }
}
