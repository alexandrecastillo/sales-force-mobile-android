package biz.belcorp.salesforce.components.widgets.viewpager

import android.animation.*
import android.annotation.TargetApi
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.ViewAnimationUtils
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.annotation.Dimension
import androidx.annotation.Px
import biz.belcorp.salesforce.components.R

open class IndicatorDotView : ImageView {

    private val TAG = "IndicatorDotView"

    val REVEAL_ANIM_DURATION: Long = 100   // 100 ms

    //endregion

    private val dot = ShapeDrawable(OvalShape())
    @Px
    var dotRadius: Int = 0

    //region Constructors

    companion object {
        @Dimension
        val DEFAULT_DOT_RADIUS_DIP = 3
        @ColorInt
        val DEFAULT_DOT_COLOR = Color.LTGRAY
        @ColorInt
        val DEFAULT_UNSELECTED_DOT_COLOR = DEFAULT_DOT_COLOR
        @ColorInt
        val DEFAULT_SELECTED_DOT_COLOR = Color.WHITE
    }

    constructor(context: Context) : super(context) {
        init(context, null, 0, 0)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs, 0, 0)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs, defStyleAttr, 0)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context, attrs, defStyleAttr, defStyleRes)
    }

    //endregion

    private fun init(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) {
        val attributes = context
            .obtainStyledAttributes(attrs, R.styleable.IndicatorDotView, defStyleAttr, defStyleRes)

        val scale = resources.displayMetrics.density
        val defaultDotRadius = (DEFAULT_DOT_RADIUS_DIP * scale + 0.5).toInt()
        dotRadius = defaultDotRadius
        dotRadius = attributes.getDimensionPixelSize(
            R.styleable.IndicatorDotView_idv_dotRadius,
            defaultDotRadius
        )
        setRadius(dotRadius)

        val dotColor = attributes.getColor(R.styleable.IndicatorDotView_dotColor, DEFAULT_DOT_COLOR)
        setColor(dotColor)

        attributes.recycle()

        setImageDrawable(dot)
    }

    //region Accessors

    /**
     * Get this dot's current radius.
     *
     * @return The dot's current radius, in pixels.
     */
    @Px
    fun getRadius(): Int {
        return dotRadius
    }

    /**
     * Set the preferred dot radius for this view.
     *
     * @param newRadius The new radius, in pixels.
     */
    fun setRadius(@Px newRadius: Int) {
        dotRadius = newRadius

        val diameter = newRadius * 2
        dot.intrinsicWidth = diameter
        dot.intrinsicHeight = diameter

        invalidate()
    }

    /**
     * Get the current dot color for this view.
     *
     * @return The current color value for the dot.
     */
    @ColorInt
    fun getColor(): Int {
        return dot.paint.color
    }

    /**
     * Set a new dot color for this view.
     *
     * @param color The new color for the dot.
     */
    fun setColor(@ColorInt color: Int) {
        dot.paint.color = color
    }

    //endregion

    //region Reveal animation

    /**
     * Animation: Reveal this view, starting from the center.
     *
     * @return An animator that reveals this view from its center.
     */
    fun revealAnimator(): Animator {
        val centerX = getWidth() / 2
        val centerY = getHeight() / 2

        val animator = revealAnimator(centerX, centerY)
        animator.duration = REVEAL_ANIM_DURATION
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                setVisibility(View.VISIBLE)
            }
        })
        return animator
    }

    /**
     * Create the API-level appropriate animator for this indicator's reveal.
     *
     * @param centerX The X point from which the animation starts.
     * @param centerY The Y point from which the animation starts.
     *
     * @see .revealAnimator
     */
    private fun revealAnimator(centerX: Int, centerY: Int): Animator {
        val animator: Animator
        /*
         * The standard material circular reveal animation is exactly what we want.
         *
         * Since this view is already circular, a scale animation effectively simulates the
         * material circular reveal on earlier API versions.
         */
        val oldScale = 0
        val newScale = 1
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            animator = ViewAnimationUtils
                .createCircularReveal(this, centerX, centerY, 0f, dotRadius.toFloat())
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            val scaleX = PropertyValuesHolder
                .ofFloat(View.SCALE_X, oldScale.toFloat(), newScale.toFloat())
            val scaleY = PropertyValuesHolder
                .ofFloat(View.SCALE_Y, oldScale.toFloat(), newScale.toFloat())
            animator = ObjectAnimator.ofPropertyValuesHolder(this, scaleX, scaleY)
        } else {
            val scaleX =
                ObjectAnimator.ofFloat(this, "scaleX", oldScale.toFloat(), newScale.toFloat())
            val scaleY =
                ObjectAnimator.ofFloat(this, "scaleY", oldScale.toFloat(), newScale.toFloat())

            val animatorSet = AnimatorSet()
            animatorSet.playTogether(scaleX, scaleY)
            animator = animatorSet
        }
        return animator
    }

    //endregion

    //region Slide animation

    /**
     * Animation: Slide this view to another location on the screen.
     *
     * @param toX The horizontal coordinate to which this view should move, in this registrarView's
     * coordinate space.
     * @param toY The vertical coordinate to which this view should move, in this registrarView's
     * coordinate space.
     * @return An animator that will move this view to (toX, toY) when started.
     */
    fun slideAnimator(toX: Float, toY: Float, animationDuration: Long): Animator {
        val fromX = getTranslationX()
        val fromY = getTranslationY()

        val animator: Animator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            val translationX = PropertyValuesHolder
                .ofFloat(View.TRANSLATION_X, fromX, toX)
            val translationY = PropertyValuesHolder
                .ofFloat(View.TRANSLATION_Y, fromY, toY)
            animator = ObjectAnimator.ofPropertyValuesHolder(this, translationX, translationY)
        } else {
            val translationXAnimator = ObjectAnimator
                .ofFloat(this, "translationX", fromX, toX)
            val translationYAnimator = ObjectAnimator
                .ofFloat(this, "translationY", fromY, toY)

            val animatorSet = AnimatorSet()
            animatorSet.playTogether(translationXAnimator, translationYAnimator)
            animator = animatorSet
        }
        animator.duration = animationDuration

        return animator
    }

}
