package biz.belcorp.salesforce.components.widgets.viewpager

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.annotation.TargetApi
import android.content.Context
import android.database.DataSetObserver
import android.graphics.Rect
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.annotation.Dimension
import androidx.annotation.IdRes
import androidx.annotation.Px
import androidx.core.view.ViewCompat
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import biz.belcorp.salesforce.components.R
import biz.belcorp.salesforce.core.constants.Constant
import java.lang.ref.WeakReference
import java.util.*

@ViewPager.DecorView
class ViewPagerIndicator : ViewGroup {

    private val pageListener = PageListener()
    private var viewPager: ViewPager? = null
    private var pagerAdapterRef: WeakReference<PagerAdapter>? = null

    @IdRes
    private var viewPagerId: Int = Constant.NUMBER_ZERO


    companion object {

        @Dimension
        val DEFAULT_DOT_PADDING_DIP = 9

        const val TAG = "ViewPagerIndicator"
        const val DOT_SLIDE_ANIM_DURATION: Long = 150
        const val LAST_KNOW_CURRENT_PAGE = -1
        const val LAST_KNOW_POSITION_OFFSET = -1f

    }

    private val indicatorDots = ArrayList<IndicatorDotView>()
    private val dotPaths = ArrayList<IndicatorDotPathView>()
    private var selectedDot: IndicatorDotView? = null
    @Px
    private var dotPadding: Int = Constant.NUMBER_ZERO
    @Px
    private var dotRadius: Int = Constant.NUMBER_ZERO
    @ColorInt
    private var unselectedDotColor: Int = Constant.NUMBER_ZERO
    @ColorInt
    private var selectedDotColor: Int = Constant.NUMBER_ZERO


    private var gravity = Gravity.CENTER_VERTICAL
    private var lastKnownCurrentPage = LAST_KNOW_CURRENT_PAGE
    private var lastKnownPositionOffset = LAST_KNOW_POSITION_OFFSET
    private var isUpdatingPositions = false
    private var isUpdatingIndicator = false
    private var selectedDotNeedsLayout = true


    constructor(context: Context) : super(context) {
        init(context, null, Constant.NUMBER_ZERO, Constant.NUMBER_ZERO)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs, Constant.NUMBER_ZERO, Constant.NUMBER_ZERO)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs, defStyleAttr, Constant.NUMBER_ZERO)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    ) {
        init(context, attrs, defStyleAttr, defStyleRes)
    }

    private fun init(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) {
        val attributes = context
            .obtainStyledAttributes(
                attrs,
                R.styleable.ViewPagerIndicator,
                defStyleAttr,
                defStyleRes
            )

        gravity = attributes.getInt(R.styleable.ViewPagerIndicator_android_gravity, gravity)

        val scale = resources.displayMetrics.density

        val defaultDotPadding = (DEFAULT_DOT_PADDING_DIP * scale + 0.5).toInt()
        dotPadding = attributes
            .getDimensionPixelSize(R.styleable.ViewPagerIndicator_dotPadding, defaultDotPadding)

        val defaultDotRadius = (IndicatorDotView.DEFAULT_DOT_RADIUS_DIP * scale + 0.5).toInt()
        dotRadius = defaultDotRadius
        dotRadius = attributes.getDimensionPixelSize(
            R.styleable.ViewPagerIndicator_vpi_dotRadius,
            defaultDotRadius
        )

        unselectedDotColor = attributes.getColor(
            R.styleable.ViewPagerIndicator_unselectedDotColor,
            IndicatorDotView.DEFAULT_UNSELECTED_DOT_COLOR
        )
        selectedDotColor = attributes.getColor(
            R.styleable.ViewPagerIndicator_selectedDotColor,
            IndicatorDotView.DEFAULT_SELECTED_DOT_COLOR
        )

        viewPagerId = attributes.getResourceId(R.styleable.ViewPagerIndicator_viewPagerId, -1)

        attributes.recycle()

        selectedDot = IndicatorDotView(context)
        selectedDot?.setColor(selectedDotColor)
        selectedDot?.setRadius(dotRadius)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val heightPadding = paddingTop + paddingBottom
        val childHeightSpec = ViewGroup.getChildMeasureSpec(
            heightMeasureSpec,
            heightPadding, ViewGroup.LayoutParams.WRAP_CONTENT
        )

        val widthPadding = paddingLeft + paddingRight
        val childWidthSpec = ViewGroup.getChildMeasureSpec(
            widthMeasureSpec,
            widthPadding, ViewGroup.LayoutParams.WRAP_CONTENT
        )

        selectedDot?.measure(childWidthSpec, childHeightSpec)
        indicatorDots.forEach { indicatorDot ->
            indicatorDot.measure(childWidthSpec, childHeightSpec)
        }
        dotPaths.forEach { dotPath ->
            dotPath.measure(childWidthSpec, childHeightSpec)
        }

        val width: Int
        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        if (widthMode == View.MeasureSpec.EXACTLY) {
            /*
             * Due to the implementation of onMeasure() in ViewPager, this case will always be
             * called if vertical layout_gravity is specified on this view. Since the Material
             * Design spec usually positions dot indicators like this at the bottom of pages, this
             * case will be called almost all the time.
             */
            width = View.MeasureSpec.getSize(widthMeasureSpec)
        } else {
            val dotCount = indicatorDots.size
            val totalDotWidth = selectedDot?.measuredWidth ?: Constant.NUMBER_ZERO * dotCount
            val totalDotPadding = dotPadding * (dotCount - 1)
            val minWidth = ViewCompat.getMinimumWidth(this)
            width = Math.max(minWidth, totalDotWidth + totalDotPadding + widthPadding)
        }

        val height: Int
        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)
        if (heightMode == View.MeasureSpec.EXACTLY) {
            height = View.MeasureSpec.getSize(heightMeasureSpec)
        } else {
            val indicatorHeight = selectedDot?.measuredHeight ?: Constant.NUMBER_ZERO
            val minHeight = ViewCompat.getMinimumHeight(this)
            height = Math.max(minHeight, indicatorHeight + heightPadding)
        }

        val childState = ViewCompat.getMeasuredHeightAndState(selectedDot)
        val measuredHeight = ViewCompat.resolveSizeAndState(
            height, heightMeasureSpec,
            childState
        )
        setMeasuredDimension(width, measuredHeight)
    }

    override fun requestLayout() {
        if (!isUpdatingIndicator) {
            super.requestLayout()
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        refresh()
    }

    private fun refresh() {
        if (viewPager != null) {
            viewPager?.currentItem?.let { updateIndicators(it, viewPager?.adapter) }

            val offset =
                if (lastKnownPositionOffset >= Constant.NUMBER_ZERO) lastKnownPositionOffset else 0.0f
            updateIndicatorPositions(lastKnownCurrentPage, offset, true)
        }
    }

    override fun onAttachedToWindow() {

        super.onAttachedToWindow()

        val parent = parent

        viewPager = when {
            viewPagerId != -1 && parent is ViewGroup -> {
                parent.findViewById<View>(viewPagerId) as ViewPager
            }
            parent !is ViewPager -> {
                throw IllegalStateException(
                    "ViewPagerIndicator must be a direct child of a ViewPager or Id must be set via viewPagerId."
                )
            }
            else -> parent
        }

        val adapter = viewPager?.adapter
        viewPager?.addOnPageChangeListener(pageListener)
        viewPager?.addOnAdapterChangeListener(pageListener)

        val lastAdapter = if (pagerAdapterRef != null) pagerAdapterRef?.get() else null
        updateAdapter(lastAdapter, adapter)
    }

    override fun onDetachedFromWindow() {

        super.onDetachedFromWindow()
        if (viewPager != null) {
            updateAdapter(viewPager?.adapter, null)
            viewPager?.removeOnPageChangeListener(pageListener)
            viewPager?.removeOnAdapterChangeListener(pageListener)
            viewPager = null
        }
    }

    /**
     * Update the ViewPager adapter being observed by the indicator. The
     *
     *
     * Taken from:
     * https://android.googlesource.com/platform/frameworks/support/+/nougat-release/v4/java/android/support/v4/view/PagerTitleStrip.java#319
     *
     * @param oldAdapter The previous adapter being tracked by the indicator.
     * @param newAdapter The previous adapter that should be tracked by the indicator.
     */
    private fun updateAdapter(
        oldAdapter: PagerAdapter?,
        newAdapter: PagerAdapter?
    ) {
        if (oldAdapter != null) {
            oldAdapter.unregisterDataSetObserver(pageListener)
            pagerAdapterRef = null
        }
        if (newAdapter != null) {
            newAdapter.registerDataSetObserver(pageListener)
            pagerAdapterRef = WeakReference(newAdapter)
        }
        if (viewPager != null) {
            lastKnownCurrentPage = -1
            lastKnownPositionOffset = -1f
            viewPager?.currentItem?.let { updateIndicators(it, newAdapter) }
            requestLayout()
        }
    }

    private fun updateIndicators(currentPage: Int, pagerAdapter: PagerAdapter?) {
        isUpdatingIndicator = true

        val pageCount = pagerAdapter?.count ?: Constant.NUMBER_ZERO
        updateDotCount(pageCount)

        lastKnownCurrentPage = currentPage

        if (!isUpdatingPositions) {
            updateIndicatorPositions(currentPage, lastKnownPositionOffset, false)
        }

        isUpdatingIndicator = false
    }

    private fun updateDotCount(newDotCount: Int) {
        val layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        var dotCount = indicatorDots.size
        if (dotCount < newDotCount) {

            while (dotCount++ != newDotCount) {
                val newDot = IndicatorDotView(context)
                newDot.setRadius(dotRadius)
                newDot.setColor(unselectedDotColor)
                indicatorDots.add(newDot)
                addViewInLayout(newDot, -1, layoutParams, true)
            }
        } else if (dotCount > newDotCount) {
            val removedDots = ArrayList(indicatorDots.subList(newDotCount, dotCount))
            for (removedDot in removedDots) {
                removeViewInLayout(removedDot)
            }
            indicatorDots.removeAll(removedDots)
        }

        updatePathCount(newDotCount - 1)

        if (newDotCount > Constant.NUMBER_ZERO) {
            addViewInLayout(selectedDot, -1, layoutParams, true)
        } else {
            removeViewInLayout(selectedDot)
        }
    }

    private fun updatePathCount(newPathCount: Int) {
        var pathCount = dotPaths.size
        if (pathCount < newPathCount) {
            val layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            while (pathCount++ != newPathCount) {
                val newPath = IndicatorDotPathView(
                    context, getUnselectedDotColor(), getDotPadding(), getDotRadius()
                )
                newPath.visibility = View.INVISIBLE
                dotPaths.add(newPath)
                addViewInLayout(newPath, -1, layoutParams, true)
            }
        } else if (pathCount > newPathCount && newPathCount >= Constant.NUMBER_ZERO) {
            val pathsToRemove = ArrayList(dotPaths.subList(newPathCount, pathCount))
            for (dotPath in pathsToRemove) {
                removeViewInLayout(dotPath)
            }
            dotPaths.removeAll(pathsToRemove)
        }
    }

    /**
     * Taken from:
     * https://android.googlesource.com/platform/frameworks/support/+/nougat-release/v4/java/android/support/v4/view/PagerTitleStrip.java#336
     *
     * @param currentPage    The index of the page we are on in the ViewPager.
     * @param positionOffset The offset of the current page from horizontal center.
     * @param forceUpdate    Whether or not to force an update
     */
    private fun updateIndicatorPositions(
        currentPage: Int,
        positionOffset: Float,
        forceUpdate: Boolean
    ) {
        if (currentPage != lastKnownCurrentPage && viewPager != null) {
            updateIndicators(currentPage, viewPager?.adapter)
        } else if (!forceUpdate && positionOffset == lastKnownPositionOffset) {
            return
        }

        isUpdatingPositions = true

        val dotWidth = 2 * dotRadius
        val top = calculateIndicatorDotTop()
        val bottom = top + dotWidth
        var left = calculateIndicatorDotStart()
        var right = left + dotWidth
        var i = Constant.NUMBER_ZERO
        val dotCount = indicatorDots.size
        val pathCount = dotPaths.size
        while (i < dotCount) {
            val dotView = indicatorDots[i]
            dotView.layout(left, top, right, bottom)

            if (i < pathCount) {
                val dotPath = dotPaths[i]
                dotPath.layout(left, top, left + dotPath.measuredWidth, bottom)
            }

            if (i == currentPage && selectedDotNeedsLayout) {
                selectedDot?.layout(left, top, right, bottom)
                selectedDotNeedsLayout = false
            }

            left = right + dotPadding
            right = left + dotWidth
            ++i
        }
        selectedDot?.bringToFront()

        lastKnownPositionOffset = positionOffset
        isUpdatingPositions = false
    }

    /**
     * Calculate the starting vertical position for the line of indicator dots.
     * @return The first Y coordinate where the indicator dots start.
     */
    @Px
    private fun calculateIndicatorDotTop(): Int {
        val top: Int
        val verticalGravity = gravity and Gravity.VERTICAL_GRAVITY_MASK
        when (verticalGravity) {
            Gravity.CENTER_VERTICAL -> top =
                (height - paddingTop - paddingBottom) / 2 - getDotRadius()
            Gravity.TOP -> top = paddingTop
            Gravity.BOTTOM -> top = height - paddingBottom - 2 * getDotRadius()
            else -> top = (height - paddingTop - paddingBottom) / 2 - getDotRadius()
        }
        return top
    }

    /**
     * Calculate the starting horizontal position for the line of indicator dots.
     * Assumes dots are centered horizontally.
     *
     * @return The first X coordinate where the indicator dots start.
     */
    @Px
    private fun calculateIndicatorDotStart(): Int {
        /*
         * Calculate the start position by starting from the center of the view and moving left
         * for half of the dots.
         */
        val dotCount = indicatorDots.size
        val halfDotCount = dotCount / 2f

        val dotWidth = 2 * dotRadius
        val totalDotWidth = dotWidth * halfDotCount
        val halfDotPaddingCount = Math.max(halfDotCount - 0.5f, 0f)
        val totalDotPaddingWidth = dotPadding * halfDotPaddingCount

        var startPosition = width / 2
        startPosition -= (totalDotWidth + totalDotPaddingWidth).toInt()
        return startPosition
    }

    private fun pageChangeAnimator(lastPageIndex: Int, newPageIndex: Int): Animator? {
        val dotPath = getDotPathForPageChange(lastPageIndex, newPageIndex)
        val lastDot = getDotForPage(lastPageIndex)

        if (dotPath == null || lastDot == null) {
            val warning = if (dotPath == null) "dotPath is null!" else "lastDot is null!"
            Log.w(TAG, warning)
            return null
        }

        val connectPathAnimator = dotPath.connectPathAnimator()
        connectPathAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                dotPath.visibility = View.VISIBLE
                lastDot.visibility = View.INVISIBLE
            }
        })

        val dotSlideDuration = DOT_SLIDE_ANIM_DURATION
        val selectedDotSlideAnimator = selectedDotSlideAnimator(newPageIndex, dotSlideDuration, 0)

        val pathDirection = getPathDirectionForPageChange(lastPageIndex, newPageIndex)
        val retreatPathAnimator = dotPath.retreatConnectedPathAnimator(pathDirection)

        val dotRevealAnimator = lastDot.revealAnimator()
        dotRevealAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                dotPath.visibility = View.INVISIBLE
            }
        })

        val animatorSet = AnimatorSet()
        animatorSet.play(connectPathAnimator).before(selectedDotSlideAnimator)
        animatorSet.play(retreatPathAnimator).after(selectedDotSlideAnimator)
        animatorSet.play(dotRevealAnimator).with(retreatPathAnimator)

        return animatorSet
    }

    private fun selectedDotSlideAnimator(
        newPageIndex: Int,
        animationDuration: Long,
        startDelay: Long
    ): Animator? {
        val newPageDotRect = Rect()
        val newPageDot = getDotForPage(newPageIndex)
        if (newPageDot != null) {
            newPageDot.getDrawingRect(newPageDotRect)
            offsetDescendantRectToMyCoords(newPageDot, newPageDotRect)
            offsetRectIntoDescendantCoords(selectedDot, newPageDotRect)
        }
        val toX = newPageDotRect.left.toFloat()
        val toY = newPageDotRect.top.toFloat()

        val animator = selectedDot?.slideAnimator(toX, toY, animationDuration)
        animator?.startDelay = startDelay

        return animator
    }

    /**
     * Watches the ViewPager for changes, updating the indicator as needed.
     */
    private inner class PageListener : DataSetObserver(), ViewPager.OnPageChangeListener,
        ViewPager.OnAdapterChangeListener {

        private var scrollState: Int = Constant.NUMBER_ZERO

        override fun onChanged() {
            super.onChanged()
            refresh()
        }

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            //do nothing
        }

        override fun onPageSelected(position: Int) {
            val pageChangeAnimator = pageChangeAnimator(lastKnownCurrentPage, position)
            if (scrollState == ViewPager.SCROLL_STATE_IDLE && viewPager != null) {
                // Only update the text here if we're not dragging or settling.
                refresh()
            }
            pageChangeAnimator?.start()
            //update lastKnownCurrentPage here
            lastKnownCurrentPage = position
        }

        override fun onPageScrollStateChanged(state: Int) {
            scrollState = state
        }

        override fun onAdapterChanged(
            viewPager: ViewPager,
            oldAdapter: PagerAdapter?,
            newAdapter: PagerAdapter?
        ) {
            updateAdapter(oldAdapter, newAdapter)
        }

        //endregion
    }

    private fun getDotForPage(pageIndex: Int): IndicatorDotView? {
        return if (pageIndex > indicatorDots.size - 1 || pageIndex < Constant.NUMBER_ZERO) null else indicatorDots[pageIndex]
    }

    private fun getDotPathForPageChange(
        oldPageIndex: Int,
        newPageIndex: Int
    ): IndicatorDotPathView? {
        if (oldPageIndex < Constant.NUMBER_ZERO || newPageIndex < Constant.NUMBER_ZERO
            || oldPageIndex == newPageIndex
        )
            return null

        val dotPathIndex = if (oldPageIndex < newPageIndex) oldPageIndex else newPageIndex
        return if (dotPathIndex >= dotPaths.size) null else dotPaths[dotPathIndex]
    }

    @IndicatorDotPathView.PathDirection
    private fun getPathDirectionForPageChange(oldPageIndex: Int, newPageIndex: Int): Int {
        return if (oldPageIndex < newPageIndex) {
            IndicatorDotPathView.PATH_DIRECTION_RIGHT
        } else {
            IndicatorDotPathView.PATH_DIRECTION_LEFT
        }
    }

    /**
     * Get the [Gravity] used to position dots within the indicator.
     * Only the vertical gravity component is used.
     */
    fun getGravity(): Int {
        return gravity
    }

    /**
     * Set the [Gravity] used to position dots within the indicator.
     * Only the vertical gravity component is used.
     *
     * @param newGravity [Gravity] constant for positioning indicator dots.
     */
    fun setGravity(newGravity: Int) {
        gravity = newGravity
        requestLayout()
    }

    /**
     * Get the current spacing between each indicator dot.
     *
     * @return The distance between each indicator dot, in pixels.
     */
    @Px
    fun getDotPadding(): Int {
        return dotPadding
    }

    /**
     * Set the spacing between each indicator dot.
     *
     * @param newDotPadding The distance to use between each indicator dot, in pixels.
     */
    fun setDotPadding(@Px newDotPadding: Int) {
        var newDotPadding = newDotPadding
        if (dotPadding == newDotPadding) return
        if (newDotPadding < Constant.NUMBER_ZERO) newDotPadding = Constant.NUMBER_ZERO

        dotPadding = newDotPadding
        invalidate()
        requestLayout()
    }

    /**
     * Get the current radius of each indicator dot.
     *
     * @return The radius of each indicator dot, in pixels.
     */
    @Px
    fun getDotRadius(): Int {
        return dotRadius
    }

    /**
     * Set the radius of each indicator dot.
     *
     * @param newRadius The new radius to use for each indicator dot.
     */
    fun setDotRadius(@Px newRadius: Int) {
        var newRadius = newRadius
        if (dotRadius == newRadius) return
        if (newRadius < Constant.NUMBER_ZERO) newRadius = Constant.NUMBER_ZERO

        dotRadius = newRadius
        for (indicatorDot in indicatorDots) {
            indicatorDot.setRadius(dotRadius)
        }
        invalidate()
        requestLayout()
    }

    /**
     * Get the current color for unselected indicator dots.
     *
     * @return The unselected dot color.
     */
    @ColorInt
    fun getUnselectedDotColor(): Int {
        return unselectedDotColor
    }

    /**
     * Set the current color for unselected indicator dots.
     *
     * @param color The new unselected dot color to use.
     */
    fun setUnselectedDotColor(@ColorInt color: Int) {
        unselectedDotColor = color
        for (indicatordot in indicatorDots) {
            indicatordot.setColor(color)
            indicatordot.invalidate()
        }
    }

    /**
     * Get the current color for selected indicator dots.
     *
     * @return The selected dot color.
     */
    @ColorInt
    fun getSelectedDotColor(): Int {
        return selectedDotColor
    }

    /**
     * Set the current color for selected indicator dots.
     *
     * @param color The new selected dot color to use.
     */
    fun setSelectedDotColor(@ColorInt color: Int) {
        selectedDotColor = color
        if (selectedDot != null) {
            selectedDot?.setColor(color)
            selectedDot?.invalidate()
        }
    }
}
