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
import kotlinx.android.synthetic.main.inflate_custom_header_icon_text.view.*
import biz.belcorp.salesforce.base.R as BaseR

class HeaderIconTextCustomView @JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var hitIcon: Drawable? = null
        set(value) {
            field = value
            setValues()
        }
    var hitTitle: String? = null
        set(value) {
            field = value
            setValues()
        }
    var hitTitleColor: Int = Color.BLACK
        set(value) {
            field = value
            setValues()
        }
    var hitTitleFontFamily: Typeface? = Typeface.DEFAULT
        set(value) {
            field = value
            setValues()
        }
    var hitDescription: String? = null
        set(value) {
            field = value
            setValues()
        }
    var hitDescriptionColor: Int = Color.DKGRAY
        set(value) {
            field = value
            setValues()
        }

    var hitDescriptionFontFamily: Typeface? = Typeface.DEFAULT
        set(value) {
            field = value
            setValues()
        }

    var hitTitleSize: Float = DEFAULT_TITLE_SIZE
        set(value) {
            field = value
            setValues()
        }

    var hitDescriptionSize: Float = DEFAULT_DESCRIPTION_SIZE
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
        LayoutInflater.from(context).inflate(R.layout.inflate_custom_header_icon_text, this)
    }

    private fun setupAttributes() {
        val attributes =
            context.obtainStyledAttributes(attrs, R.styleable.HeaderIconTextCustomView, 0, 0)
        try {
            val iconValue =
                attributes.getResourceId(R.styleable.HeaderIconTextCustomView_hitIcon, -1)
            hitTitle = attributes.getString(R.styleable.HeaderIconTextCustomView_hitTitle)
            hitTitleColor = attributes.getColor(
                R.styleable.HeaderIconTextCustomView_hitTitleColor,
                getColor(BaseR.color.black)
            )
            val titleFontFamilyValue = attributes.getResourceId(
                R.styleable.HeaderIconTextCustomView_hitTitleFontFamily,
                BaseR.font.lato_bold
            )
            hitDescription =
                attributes.getString(R.styleable.HeaderIconTextCustomView_hitDescription)
            hitDescriptionColor = attributes.getColor(
                R.styleable.HeaderIconTextCustomView_hitDescriptionColor,
                getColor(BaseR.color.gray_5)
            )
            hitTitleSize = attributes.getDimension(
                R.styleable.HeaderIconTextCustomView_hitTitleSize,
                hitTitleSize
            )
            hitDescriptionSize = attributes.getDimension(
                R.styleable.HeaderIconTextCustomView_hitDescriptionSize,
                hitDescriptionSize
            )
            val descriptionFontFamilyValue = attributes.getResourceId(
                R.styleable.HeaderIconTextCustomView_hitDescriptionFontFamily,
                BaseR.font.lato_regular
            )

            if (iconValue != -1) {
                hitIcon = getDrawable(iconValue)
            }
            if (titleFontFamilyValue != -1) {
                hitTitleFontFamily = getFont(titleFontFamilyValue)
            }
            if (descriptionFontFamilyValue != -1) {
                hitDescriptionFontFamily = getFont(descriptionFontFamilyValue)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            attributes.recycle()
        }
    }

    private fun setValues() {
        icon?.apply {
            if (hitIcon != null) {
                visibility = View.VISIBLE
                setImageDrawable(hitIcon)
            } else {
                visibility = View.GONE
            }
        }
        text_title?.apply {
            text = hitTitle
            textColor = hitTitleColor
            typeface = hitTitleFontFamily
            textSize =
                if (hitTitleSize == DEFAULT_TITLE_SIZE) hitTitleSize else convertPxToSp(hitTitleSize)
        }
        text_description?.apply {
            text = hitDescription
            textColor = hitDescriptionColor
            typeface = hitDescriptionFontFamily
            textSize =
                if (hitDescriptionSize == DEFAULT_DESCRIPTION_SIZE) hitDescriptionSize else convertPxToSp(
                    hitDescriptionSize
                )
            visibility = if (!hitDescription.isNullOrEmpty()) View.VISIBLE else View.GONE
        }
    }

    private fun convertPxToSp(size: Float): Float {
        return size / resources.displayMetrics.scaledDensity
    }

    companion object {
        private const val DEFAULT_TITLE_SIZE = 16f
        private const val DEFAULT_DESCRIPTION_SIZE = 14f
    }
}
