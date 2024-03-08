package biz.belcorp.salesforce.modules.developmentpath.widgets

import android.annotation.TargetApi
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import biz.belcorp.mobile.components.core.extensions.getColor
import biz.belcorp.mobile.components.core.extensions.getDrawable
import biz.belcorp.mobile.components.core.extensions.getFont
import biz.belcorp.salesforce.core.utils.textColor
import biz.belcorp.salesforce.modules.developmentpath.R
import kotlinx.android.synthetic.main.inflate_custom_flat_icon_button.view.*
import biz.belcorp.salesforce.base.R as BaseR

class IndicatorButtonCustomView @JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var ibText: String? = null
        set(value) {
            field = value
            setValues()
        }
    var ibTextColor: Int = Color.BLACK
        set(value) {
            field = value
            setValues()
        }
    var ibTextFontFamily: Typeface? = Typeface.DEFAULT
        set(value) {
            field = value
            setValues()
        }
    var ibIcon: Drawable? = null
        set(value) {
            field = value
            setValues()
        }
    var ibBackgroundColor: Int = Color.TRANSPARENT
        set(value) {
            field = value
            setValues()
        }
    var ibRippleColor: Int = getColor(BaseR.color.magenta)
        set(value) {
            field = value
            setValues()
        }
    var outline: Boolean = false
        set(value) {
            field = value
            setValues()
        }
    var borderColor: Int = -1
        set(value) {
            field = value
            setValues()
        }
    var borderRadius: Float = context.resources.getDimension(BaseR.dimen.content_inset_tiniest)
        set(value) {
            field = value
            setValues()
        }
    var borderSize: Int = context.resources.getDimensionPixelSize(BaseR.dimen.ds_divider_size)
        set(value) {
            field = value
            setValues()
        }

    init {
        inflateLayout()
        setupAttributes()
        setValues()
    }

    private fun inflateLayout() {
        LayoutInflater.from(context).inflate(R.layout.inflate_custom_indicator_button, this)
    }

    private fun setupAttributes() {
        val attributes =
            context.obtainStyledAttributes(attrs, R.styleable.IndicatorButtonCustomView, 0, 0)
        try {
            ibText = attributes.getString(R.styleable.IndicatorButtonCustomView_ibText)
            ibTextColor = attributes.getColor(
                R.styleable.IndicatorButtonCustomView_ibTextColor,
                getColor(BaseR.color.black)
            )
            val titleFontFamilyValue = attributes.getResourceId(
                R.styleable.IndicatorButtonCustomView_ibTextFontFamily,
                BaseR.font.lato_bold
            )
            val iconValue =
                attributes.getResourceId(R.styleable.IndicatorButtonCustomView_ibIcon, -1)

            if (titleFontFamilyValue != -1) {
                ibTextFontFamily = getFont(titleFontFamilyValue)
            }
            if (iconValue != -1) {
                ibIcon = getDrawable(iconValue)
            }

            ibBackgroundColor = attributes.getInt(
                R.styleable.IndicatorButtonCustomView_ibBackgroundColor,
                ibBackgroundColor
            )
            ibRippleColor = attributes.getInt(
                R.styleable.IndicatorButtonCustomView_ibRippleColor,
                ibRippleColor
            )

            outline =
                attributes.getBoolean(R.styleable.IndicatorButtonCustomView_ibOutline, outline)
            borderRadius = attributes.getDimension(
                R.styleable.IndicatorButtonCustomView_ibBorderRadius,
                borderRadius
            )
            borderSize = attributes.getDimensionPixelSize(
                R.styleable.IndicatorButtonCustomView_ibBorderSize,
                borderSize
            )
            borderColor = attributes.getColor(
                R.styleable.IndicatorButtonCustomView_ibBorderColor,
                borderColor
            )
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            attributes.recycle()
        }
    }

    private fun setValues() {
        setupContainerButton()
        text?.apply {
            text = ibText
            textColor = ibTextColor
            typeface = ibTextFontFamily
        }
        icon?.apply {
            if (ibIcon != null) {
                visibility = View.VISIBLE
                setImageDrawable(ibIcon)
            } else {
                visibility = View.GONE
            }
        }
    }

    private fun setupContainerButton() {
        if (outline) setBackgroundOutline()
        else setBackground()
    }

    private fun setBackground() {
        val shape = GradientDrawable()
        shape.shape = GradientDrawable.RECTANGLE
        shape.cornerRadius = borderRadius
        shape.setColor(ibBackgroundColor)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.background = getBackgroundDrawable(ibRippleColor, shape)
        } else {
            this.background = shape
        }
    }

    private fun setBackgroundOutline() {
        if (borderColor == -1) borderColor = ibBackgroundColor

        val shape = GradientDrawable()
        shape.shape = GradientDrawable.RECTANGLE
        shape.cornerRadius = borderRadius
        shape.setStroke(borderSize, borderColor)
        shape.setColor(Color.WHITE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.background = getBackgroundDrawable(ibRippleColor, shape)
        } else {
            this.background = shape
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun getBackgroundDrawable(
        pressedColor: Int,
        backgroundDrawable: Drawable
    ): RippleDrawable {
        return RippleDrawable(getPressedState(pressedColor), backgroundDrawable, null)
    }

    private fun getPressedState(pressedColor: Int): ColorStateList {
        return ColorStateList(arrayOf(intArrayOf()), intArrayOf(pressedColor))
    }
}
