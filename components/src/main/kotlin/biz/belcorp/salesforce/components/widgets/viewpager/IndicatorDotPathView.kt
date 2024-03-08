package biz.belcorp.salesforce.components.widgets.viewpager

import android.animation.*
import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.annotation.IntDef
import androidx.annotation.Px
import androidx.core.view.ViewCompat


class IndicatorDotPathView : ViewGroup {

    private val PATH_STRETCH_ANIM_DURATION: Long = 150 // 150 ms.
    private val PATH_RETREAT_ANIM_DURATION: Long = 100 // 100 ms.

    //@Retention(RetentionPolicy.SOURCE)
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    @IntDef(PATH_DIRECTION_LEFT, PATH_DIRECTION_RIGHT)
    internal annotation class PathDirection

    //endregion
    companion object {
        @PathDirection
        const val PATH_DIRECTION_LEFT = 0
        @PathDirection
        const val PATH_DIRECTION_RIGHT = 1
    }

    @Px
    private var dotPadding: Int = 0
    @Px
    private var dotRadius: Int = 0

    //region Subviews

    private val startDot: IndicatorDotView
    private val endDot: IndicatorDotView

    /** Portion of the path that stretches out from startDot toward endDot.  */
    private val startPathSegment: DotPathSegment
    /** Portion of the path that stretches out from endDot toward startDot.  */
    private val endPathSegment: DotPathSegment
    /** Portion of the path that grows from where startPathDot and endPathDot meet.  */
    private val centerSegment: ImageView
    private val centerPathShape = ShapeDrawable(RectShape())

    //endregion

    //region Constructors

    constructor(context: Context) : super(context) {
        val scale = context.resources.displayMetrics.density
        this.dotPadding = (ViewPagerIndicator.DEFAULT_DOT_PADDING_DIP * scale + 0.5).toInt()
        this.dotRadius = (IndicatorDotView.DEFAULT_DOT_RADIUS_DIP * scale + 0.5).toInt()

        this.startDot = IndicatorDotView(context)
        this.endDot = IndicatorDotView(context)
        this.startPathSegment = DotPathSegment(context)
        this.endPathSegment = DotPathSegment(context)

        this.centerSegment = ImageView(context)
        this.centerSegment.setImageDrawable(centerPathShape)

        init(IndicatorDotView.DEFAULT_DOT_COLOR)
    }


    constructor(
        context: Context,
        @ColorInt dotColor: Int,
        @Px dotPadding: Int,
        @Px dotRadius: Int
    ) : this(context) {
        this.dotPadding = dotPadding
        this.dotRadius = dotRadius

        setDotColor(dotColor)
        setDotPadding(dotPadding)
        setDotRadius(dotRadius)
    }

    //endregion

    private fun init(@ColorInt dotColor: Int) {
        val layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        addView(startDot, -1, layoutParams)
        addView(endDot, -1, layoutParams)
        addView(startPathSegment, -1, layoutParams)
        addView(endPathSegment, -1, layoutParams)
        addView(centerSegment, -1, layoutParams)
        centerSegment.visibility = View.GONE

        setDotColor(dotColor)
        setDotPadding(dotPadding)
        setDotRadius(dotRadius)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        // Layout children, starting from the left.
        val dotDiameter = 2 * dotRadius
        val top = paddingTop
        val bottom = top + dotDiameter
        var left = paddingLeft

        startDot.layout(left, top, left + dotDiameter, bottom)
        startPathSegment.layout(left, top, left + dotDiameter, bottom)

        left += dotRadius
        centerSegment.layout(left, top, left + dotPadding + dotDiameter, bottom)

        left += dotRadius + dotPadding
        endDot.layout(left, top, left + dotDiameter, bottom)
        endPathSegment.layout(left, top, left + dotDiameter, bottom)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthPadding = paddingLeft + paddingRight
        val childWidthSpec = ViewGroup.getChildMeasureSpec(
            widthMeasureSpec,
            widthPadding, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        val heightPadding = paddingTop + paddingBottom
        val childHeightSpec = ViewGroup.getChildMeasureSpec(
            heightMeasureSpec,
            heightPadding, ViewGroup.LayoutParams.WRAP_CONTENT
        )

        startDot.measure(childWidthSpec, childHeightSpec)
        startPathSegment.measure(childWidthSpec, childHeightSpec)
        endDot.measure(childWidthSpec, childHeightSpec)
        endPathSegment.measure(childWidthSpec, childHeightSpec)
        centerSegment.measure(childWidthSpec, childHeightSpec)

        // Calculate measurement for this view.
        val width: Int
        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        width = if (widthMode == View.MeasureSpec.EXACTLY) {
            View.MeasureSpec.getSize(widthMeasureSpec)
        } else {
            val totalDotWidth = startDot.measuredWidth + endDot.measuredWidth
            val minWidth = ViewCompat.getMinimumWidth(this)
            Math.max(minWidth, totalDotWidth + dotPadding + widthPadding)
        }

        val height: Int
        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)
        height = if (heightMode == View.MeasureSpec.EXACTLY) {
            View.MeasureSpec.getSize(heightMeasureSpec)
        } else {
            val indicatorHeight = startDot.measuredHeight
            val minHeight = ViewCompat.getMinimumHeight(this)
            Math.max(minHeight, indicatorHeight + heightPadding)
        }

        val childState = ViewCompat.getMeasuredHeightAndState(startDot)
        val measuredHeight = ViewCompat.resolveSizeAndState(
            height, heightMeasureSpec,
            childState
        )
        setMeasuredDimension(width, measuredHeight)
    }

    //region Accessors

    @ColorInt
    fun getDotColor(): Int {
        // Relies on the invariant that all subviews are the same color.
        return startDot.getColor()
    }

    fun setDotColor(@ColorInt dotColor: Int) {
        startDot.setColor(dotColor)
        endDot.setColor(dotColor)
        startPathSegment.setColor(dotColor)
        endPathSegment.setColor(dotColor)
        centerPathShape.paint.color = dotColor
        invalidate()
    }

    @Px
    fun getDotPadding(): Int {
        return dotPadding
    }

    fun setDotPadding(@Px dotPadding: Int) {
        this.dotPadding = dotPadding
        centerPathShape.intrinsicWidth = dotPadding + 2 * dotRadius
        invalidate()
        requestLayout()
    }

    @Px
    fun getDotRadius(): Int {
        return dotRadius
    }

    fun setDotRadius(@Px dotRadius: Int) {
        startDot.setRadius(dotRadius)
        endDot.setRadius(dotRadius)
        startPathSegment.setRadius(dotRadius)
        endPathSegment.setRadius(dotRadius)

        val dotDiameter = 2 * dotRadius
        centerPathShape.intrinsicWidth = dotPadding + dotDiameter
        centerPathShape.intrinsicHeight = dotDiameter

        invalidate()
        requestLayout()
    }

    //endregion

    //region Dot connection animation

    fun connectPathAnimator(): Animator {
        val startSegmentBounds = viewRectInNeighborCoords(startPathSegment, endPathSegment)
        val endSegmentBounds = viewRectInNeighborCoords(endPathSegment, startPathSegment)

        val startSegmentToX = if (endSegmentBounds.centerX() < 0) {
            endSegmentBounds.left
        } else {
            endSegmentBounds.right
        }
        val startSegmentToY = if (endSegmentBounds.centerY() < 0) {
            endSegmentBounds.top
        } else {
            endSegmentBounds.bottom
        }
        val endSegmentToX = if (startSegmentBounds.centerX() < 0) {
            startSegmentBounds.left
        } else {
            startSegmentBounds.right
        }
        val endSegmentToY = if (startSegmentBounds.centerY() < 0) {
            startSegmentBounds.top
        } else {
            startSegmentBounds.bottom
        }

        val startSegmentAnimator = startPathSegment
            .stretchAnimator(
                PATH_STRETCH_ANIM_DURATION,
                startSegmentToX.toFloat(),
                startSegmentToY.toFloat()
            )
        val endSegmentAnimator = endPathSegment
            .stretchAnimator(
                PATH_STRETCH_ANIM_DURATION,
                endSegmentToX.toFloat(),
                endSegmentToY.toFloat()
            )

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(
            startSegmentAnimator,
            endSegmentAnimator,
            centerSegmentGrowAnimator()
        )
        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                startDot.visibility = View.VISIBLE
                endDot.visibility = View.VISIBLE
            }
        })

        return animatorSet
    }

    private fun centerSegmentGrowAnimator(): Animator {
        val fromScale = 0f
        val toScale = 1f

        val growAnimator: ObjectAnimator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            val scaleYProperty = PropertyValuesHolder
                .ofFloat(View.SCALE_Y, fromScale, toScale)
            growAnimator = ObjectAnimator.ofPropertyValuesHolder(centerSegment, scaleYProperty)
        } else {
            growAnimator = ObjectAnimator.ofFloat(centerSegment, "scaleY", fromScale, toScale)
        }
        // Start growing when the two ends of the path meet in the middle.
        val animationDuration = PATH_STRETCH_ANIM_DURATION / 4
        growAnimator.startDelay = animationDuration
        growAnimator.duration = animationDuration

        growAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                super.onAnimationStart(animation)
                centerSegment.visibility = View.VISIBLE
            }
        })

        return growAnimator
    }


    private fun viewRectInNeighborCoords(view: View, neighbor: View): Rect {
        val bounds = Rect()
        view.getDrawingRect(bounds)
        offsetDescendantRectToMyCoords(view, bounds)
        offsetRectIntoDescendantCoords(neighbor, bounds)
        return bounds
    }

    //endregion

    //region Retreat animation

    fun retreatConnectedPathAnimator(@PathDirection pathDirection: Int): Animator {
        val fromDot = if (pathDirection == PATH_DIRECTION_RIGHT) startDot else endDot
        val toDot = if (pathDirection == PATH_DIRECTION_LEFT) startDot else endDot
        return retreatConnectedPathAnimator(fromDot, toDot)
    }

    private fun retreatConnectedPathAnimator(
        fromDot: IndicatorDotView,
        toDot: IndicatorDotView
    ): Animator {
        var endDotBounds = viewRectInNeighborCoords(toDot, fromDot)
        var toX = endDotBounds.left.toFloat()
        var toY = endDotBounds.top.toFloat()
        val dotRetreatAnimator = retreatDotAnimator(fromDot, toX, toY, PATH_RETREAT_ANIM_DURATION)

        endDotBounds = viewRectInNeighborCoords(toDot, centerSegment)
        toX = (if (endDotBounds.centerX() <= 0) 0 else centerSegment.width).toFloat()
        toY = (if (endDotBounds.centerY() <= 0) 0 else centerSegment.height).toFloat()
        val pathRetreatAnimator = retreatCenterSegmentAnimator(toX, toY, PATH_RETREAT_ANIM_DURATION)

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(dotRetreatAnimator, pathRetreatAnimator)
        return animatorSet
    }

    private fun retreatDotAnimator(
        retreatingDot: IndicatorDotView,
        toX: Float,
        toY: Float,
        animationDuration: Long
    ): Animator {
        val dotSlideAnimator = retreatingDot
            .slideAnimator(toX, toY, animationDuration)

        val originalTranslationX = retreatingDot.translationX
        val originalTranslationY = retreatingDot.translationY
        dotSlideAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                retreatingDot.visibility = View.INVISIBLE
                retreatingDot.translationX = originalTranslationX
                retreatingDot.translationY = originalTranslationY
            }
        })

        return dotSlideAnimator
    }

    private fun retreatCenterSegmentAnimator(
        toX: Float,
        toY: Float,
        animationDuration: Long
    ): Animator {
        val originalScale = 1f
        val scaleX = 0f
        val scaleY = 1f

        val animator = scaleAnimator(centerSegment, originalScale, scaleX, scaleY)

        // Choose the corner of the view farthest from the destination location.
        val originalPivotX = pivotX
        val originalPivotY = pivotY
        val pivotX = Math.max(0f, Math.min(toX, centerSegment.width.toFloat()))
        val pivotY = Math.max(0f, Math.min(toY, centerSegment.height.toFloat()))
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                super.onAnimationStart(animation)
                centerSegment.pivotX = pivotX
                centerSegment.pivotY = pivotY
            }

            override fun onAnimationEnd(animation: Animator) {
                // Reset values.
                centerSegment.visibility = View.INVISIBLE
                centerSegment.scaleX = originalScale
                centerSegment.scaleY = originalScale
                centerSegment.pivotX = originalPivotX
                centerSegment.pivotY = originalPivotY
            }
        })
        animator.duration = animationDuration

        return animator
    }


    //endregion

    fun scaleAnimator(
        view: View,
        originalScale: Float,
        scaleX: Float,
        scaleY: Float
    ): Animator {
        val animator: Animator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            val scaleXProperty = PropertyValuesHolder.ofFloat(View.SCALE_X, originalScale, scaleX)
            val scaleYProperty = PropertyValuesHolder.ofFloat(View.SCALE_Y, originalScale, scaleY)
            animator = ObjectAnimator.ofPropertyValuesHolder(view, scaleXProperty, scaleYProperty)
        } else {
            val scaleXAnimator = ObjectAnimator.ofFloat(view, "scaleX", originalScale, scaleX)
            val scaleYAnimator = ObjectAnimator.ofFloat(view, "scaleY", originalScale, scaleY)

            val animatorSet = AnimatorSet()
            animatorSet.playTogether(scaleXAnimator, scaleYAnimator)
            animator = animatorSet
        }
        return animator
    }


    class DotPathSegment internal constructor(context: Context) : IndicatorDotView(context) {

        internal fun stretchAnimator(animationDuration: Long, toX: Float, toY: Float): Animator {
            val distanceX = Math.abs(toX) + if (toX < 0) width else 0
            val distanceY = Math.abs(toY) + if (toY < 0) height else 0

            val scaleX = distanceX / width
            val scaleY = distanceY / height
            val originalScale = 1f

            val animator = scaleAnimator(this, originalScale, scaleX, scaleY)

            animator.setDuration(animationDuration)

            val originalPivotX = pivotX
            val originalPivotY = pivotY
            animator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator) {
                    visibility = View.VISIBLE
                    setStretchAnimatorPivot(toX, toY)
                }

                override fun onAnimationEnd(animation: Animator) {
                    // Reset values.
                    visibility = View.INVISIBLE
                    setScaleX(originalScale)
                    setScaleY(originalScale)
                    setStretchAnimatorPivot(originalPivotX, originalPivotY)
                }
            })

            return animator
        }

        fun scaleAnimator(
            view: View,
            originalScale: Float,
            scaleX: Float,
            scaleY: Float
        ): Animator {
            val animator: Animator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                val scaleXProperty =
                    PropertyValuesHolder.ofFloat(View.SCALE_X, originalScale, scaleX)
                val scaleYProperty =
                    PropertyValuesHolder.ofFloat(View.SCALE_Y, originalScale, scaleY)
                animator =
                    ObjectAnimator.ofPropertyValuesHolder(view, scaleXProperty, scaleYProperty)
            } else {
                val scaleXAnimator = ObjectAnimator.ofFloat(view, "scaleX", originalScale, scaleX)
                val scaleYAnimator = ObjectAnimator.ofFloat(view, "scaleY", originalScale, scaleY)

                val animatorSet = AnimatorSet()
                animatorSet.playTogether(scaleXAnimator, scaleYAnimator)
                animator = animatorSet
            }
            return animator
        }


        private fun setStretchAnimatorPivot(toX: Float, toY: Float) {
            val pivotX = width - Math.max(0f, Math.min(toX, width.toFloat()))
            val pivotY = height - Math.max(0f, Math.min(toY, height.toFloat()))

            setPivotX(pivotX)
            setPivotY(pivotY)
        }

    }
}
