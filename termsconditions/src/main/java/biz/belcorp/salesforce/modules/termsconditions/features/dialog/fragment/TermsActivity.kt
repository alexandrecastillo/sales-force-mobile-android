package biz.belcorp.salesforce.modules.termsconditions.features.dialog.fragment

import android.os.Bundle
import biz.belcorp.salesforce.core.base.BaseActivity
import biz.belcorp.salesforce.modules.termsconditions.R
import biz.belcorp.salesforce.base.R as BaseR

class TermsActivity : BaseActivity() {

    override fun getLayout() = R.layout.activity_terms

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(BaseR.style.AppTheme)
        super.onCreate(savedInstanceState)
    }
}
