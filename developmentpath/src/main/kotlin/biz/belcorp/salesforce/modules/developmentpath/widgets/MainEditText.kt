package biz.belcorp.salesforce.modules.developmentpath.widgets

import android.content.Context
import android.util.AttributeSet
import android.widget.EditText
import biz.belcorp.salesforce.core.utils.backgroundResource
import biz.belcorp.salesforce.core.utils.dip
import biz.belcorp.salesforce.core.utils.horizontalPadding
import biz.belcorp.salesforce.core.utils.verticalPadding
import biz.belcorp.salesforce.modules.developmentpath.R

class MainEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : EditText(context, attrs, defStyleAttr) {
    init {
        backgroundResource = R.drawable.background_text_component
        verticalPadding = dip(12)
        horizontalPadding = dip(16)
    }
}
