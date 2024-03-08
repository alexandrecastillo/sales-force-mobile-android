package biz.belcorp.salesforce.modules.developmentpath.widgets

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
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

class FlatIconButtonCustomView @JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var fibText: String? = null
        set(value) {
            field = value
            setValues()
        }
    var fibTextColor: Int = Color.BLACK
        set(value) {
            field = value
            setValues()
        }
    var fibTextFontFamily: Typeface? = Typeface.DEFAULT
        set(value) {
            field = value
            setValues()
        }
    var fibIcon: Drawable? = null
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
        LayoutInflater.from(context).inflate(R.layout.inflate_custom_flat_icon_button, this)
    }

    private fun setupAttributes() {
        val attributes =
            context.obtainStyledAttributes(attrs, R.styleable.FlatIconButtonCustomView, 0, 0)
        try {
            fibText = attributes.getString(R.styleable.FlatIconButtonCustomView_fibText)
            fibTextColor = attributes.getColor(
                R.styleable.FlatIconButtonCustomView_fibTextColor,
                getColor(BaseR.color.black)
            )
            val titleFontFamilyValue = attributes.getResourceId(
                R.styleable.FlatIconButtonCustomView_fibTextFontFamily,
                BaseR.font.lato_bold
            )
            val iconValue =
                attributes.getResourceId(R.styleable.FlatIconButtonCustomView_fibIcon, -1)

            if (titleFontFamilyValue != -1) {
                fibTextFontFamily = getFont(titleFontFamilyValue)
            }
            if (iconValue != -1) {
                fibIcon = getDrawable(iconValue)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            attributes.recycle()
        }
    }

    private fun setValues() {
        text?.apply {
            text = fibText
            textColor = fibTextColor
            typeface = fibTextFontFamily
        }
        icon?.apply {
            if (fibIcon != null) {
                visibility = View.VISIBLE
                setImageDrawable(fibIcon)
            } else {
                visibility = View.GONE
            }
        }
    }
}
