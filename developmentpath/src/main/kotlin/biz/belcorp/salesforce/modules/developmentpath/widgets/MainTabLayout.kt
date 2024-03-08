package biz.belcorp.salesforce.modules.developmentpath.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import biz.belcorp.salesforce.modules.developmentpath.R
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.main_tab_layout.view.*

class MainTabLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    val tabs: TabLayout

    init {
        LayoutInflater.from(context).inflate(R.layout.main_tab_layout, this)
        tabs = mainTabLayout
    }
}
