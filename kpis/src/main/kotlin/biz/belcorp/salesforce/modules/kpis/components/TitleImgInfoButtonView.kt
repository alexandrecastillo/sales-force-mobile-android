package biz.belcorp.salesforce.modules.kpis.components

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import biz.belcorp.mobile.components.core.extensions.inflateLayout
import biz.belcorp.salesforce.modules.kpis.R
import kotlinx.android.synthetic.main.include_title_img_info_button.view.*

class TitleImgInfoButtonView @JvmOverloads constructor(
    context: Context?,
    private val attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var title: String = ""
        set(value) {
            field = value
            tvTitle?.text = value
        }

    var info: String = ""
        set(value) {
            field = value
            tvw_info?.text = value
        }

    init {
        context?.inflateLayout(R.layout.include_title_img_info_button, this)
    }

    var imgLeft: Drawable? = null
        set(value) {
            field = value
            applyIcon(value)
        }

    var textButton: String = ""
        set(value) {
            field = value
            btn_info?.text = value
        }


    var btnOnClickListener: OnClickListener? = null
        set(value) {
            field = value
            btn_info?.setOnClickListener(value)
        }


    private fun applyIcon(drawable: Drawable?) {
        imgvLeft?.setImageDrawable(drawable)
    }


}
