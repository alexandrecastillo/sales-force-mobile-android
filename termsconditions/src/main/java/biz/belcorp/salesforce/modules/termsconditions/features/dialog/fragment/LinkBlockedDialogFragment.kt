package biz.belcorp.salesforce.modules.termsconditions.features.dialog.fragment

import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StyleSpan
import android.view.View
import biz.belcorp.salesforce.core.base.BaseDialogFragment
import biz.belcorp.salesforce.modules.termsconditions.R
import kotlinx.android.synthetic.main.layout_link_se_blocked_dialog.*

class LinkBlockedDialogFragment : BaseDialogFragment() {

    override fun getLayout() = R.layout.layout_link_se_blocked_dialog

    override fun onStart() {
        super.onStart()
        fitWrapScreen()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        val boldSpan = StyleSpan(Typeface.BOLD)
        val ss = SpannableString(getString(R.string.blocked_link_se_subtitle))
        ss.setSpan(boldSpan, 65, 89, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        tvBlockSESubtitle?.text = ss
        btnCloseDialog?.setOnClickListener {
            activity?.onBackPressed()
        }
    }

}
