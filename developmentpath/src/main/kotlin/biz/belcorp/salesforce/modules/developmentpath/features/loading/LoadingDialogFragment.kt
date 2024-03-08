package biz.belcorp.salesforce.modules.developmentpath.features.loading

import biz.belcorp.salesforce.core.base.BaseDialogFragment
import biz.belcorp.salesforce.modules.developmentpath.R

class LoadingDialogFragment : BaseDialogFragment(){

    override fun getLayout() = R.layout.dialog_loading_fragment

    override fun onStart() {
        super.onStart()
        fitFullScreen()
    }
}
