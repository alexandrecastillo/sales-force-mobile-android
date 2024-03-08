package biz.belcorp.salesforce.components.exported

import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.include_section_go_billing.*
import kotlinx.android.synthetic.main.include_toolbar_dark.*
import kotlinx.android.synthetic.main.include_toolbar_light.*

inline val Fragment.sharedToolbarDark get() = toolbar
inline val Fragment.sharedToolbarLight get() = toolbarLight
inline val Fragment.sharedBtnGoBilling get() = btnGoBilling
