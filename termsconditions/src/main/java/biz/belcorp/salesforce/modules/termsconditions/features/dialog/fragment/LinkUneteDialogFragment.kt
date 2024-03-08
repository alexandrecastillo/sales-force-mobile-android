package biz.belcorp.salesforce.modules.termsconditions.features.dialog.fragment

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import biz.belcorp.salesforce.base.utils.onLinkPressed
import biz.belcorp.salesforce.core.base.BaseDialogFragment
import biz.belcorp.salesforce.core.domain.entities.terms.ApproveTermsParams
import biz.belcorp.salesforce.core.events.EventSubject
import biz.belcorp.salesforce.core.events.LiveDataBus
import biz.belcorp.salesforce.core.utils.DocumentHelper
import biz.belcorp.salesforce.core.utils.toast
import biz.belcorp.salesforce.modules.termsconditions.R
import biz.belcorp.salesforce.modules.termsconditions.features.dialog.TermsDialogViewModel
import biz.belcorp.salesforce.modules.termsconditions.features.dialog.TermsDialogViewState
import kotlinx.android.synthetic.main.layout_link_se_dialog.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.regex.Pattern

class LinkUneteDialogFragment : BaseDialogFragment() {

    private val downloadHelper by lazy { DocumentHelper(requireContext()) }
    private val viewModel by viewModel<TermsDialogViewModel>()
    override fun getLayout() = R.layout.layout_link_se_dialog

    override fun onStart() {
        super.onStart()
        fitFullScreen()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setupListener()
        setupViewModels()
    }

    private fun setupViewModels() {
        viewModel.viewState.observe(viewLifecycleOwner, observerResponse)
        viewModel.getUsername()
    }

    private val observerResponse = Observer<TermsDialogViewState> { state ->
        when (state) {
            is TermsDialogViewState.Success -> onSaveResponse(state.success)
            is TermsDialogViewState.SuccessLink -> openPdf(state.url)
            is TermsDialogViewState.SuccessSync -> Unit
            is TermsDialogViewState.SuccessName -> setUsername(state.name)
            is TermsDialogViewState.Failed -> toast(state.message)
        }
    }

    private fun initViews() {
        val termsAndConditions = getString(R.string.terms_cond)

        context?.let {
            tvTermsConditions?.apply {
                text = String.format(getString(R.string.terms_label), termsAndConditions)
                setLinkTextColor(ContextCompat.getColor(it, R.color.black))
                val termsAndConditionsMatcher: Pattern = Pattern.compile(termsAndConditions)
                Linkify.addLinks(tvTermsConditions, termsAndConditionsMatcher, "terms:")
                onLinkPressed { viewModel.getTermUrl(ApproveTermsParams.LINK_SE) }
                movementMethod = LinkMovementMethod.getInstance()
            }
        }

        tvTerm1?.text = getString(R.string.share_link_term1)
        tvTerm2?.text = getString(R.string.share_link_term2)
        tvTerm3?.text = getString(R.string.share_link_term3)
    }

    private fun setUsername(name: String) {
        val userName = String.format(getString(R.string.share_link_dear), name)
        tvHeader?.text = userName
    }

    private fun setupListener() {
        checkboxCompartir?.setOnCheckedChangeListener { _, check ->
            btnShareLink?.isEnabled = check
        }
        ivClose?.setOnClickListener { activity?.onBackPressed() }
        btnShareLink.setOnClickListener {
            viewModel.saveTerms()
        }
    }

    private fun openPdf(url: String) {
        if (url.isNotEmpty()) downloadHelper.openDocument(url)
        else toast(R.string.empty_url)
    }

    private fun onSaveResponse(success: Boolean) {
        LiveDataBus.publish(EventSubject.SHARE_LINK_SE, success)
        activity?.onBackPressed()
    }


}
