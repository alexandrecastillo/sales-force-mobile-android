package biz.belcorp.salesforce.modules.termsconditions.features.dialog.fragment

import android.content.Context
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import biz.belcorp.salesforce.base.utils.onLinkPressed
import biz.belcorp.salesforce.core.base.BaseDialogFragment
import biz.belcorp.salesforce.core.domain.entities.terms.ApproveTermsParams
import biz.belcorp.salesforce.core.utils.DocumentHelper
import biz.belcorp.salesforce.core.utils.gone
import biz.belcorp.salesforce.core.utils.toast
import biz.belcorp.salesforce.core.utils.visible
import biz.belcorp.salesforce.modules.termsconditions.R
import biz.belcorp.salesforce.modules.termsconditions.features.dialog.TermsDialogViewModel
import biz.belcorp.salesforce.modules.termsconditions.features.dialog.TermsDialogViewState
import kotlinx.android.synthetic.main.layout_terms_dialog.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.regex.Pattern
import biz.belcorp.salesforce.base.R as BaseR

class TermsDialogFragment : BaseDialogFragment() {

    private val viewModel by viewModel<TermsDialogViewModel>()
    private val downloadHelper by lazy { DocumentHelper(requireContext()) }
    override fun getLayout() = R.layout.layout_terms_dialog
    private var termCheck = false
    private var privateCheck = false
    private var publicyCheck = false

    override fun onStart() {
        super.onStart()
        fitFullScreen()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setupViewModels()
    }

    private fun setupViewModels() {
        viewModel.viewState.observe(viewLifecycleOwner, observerResponse)
        showProgress()
        viewModel.syncTerms()
    }

    private val observerResponse = Observer<TermsDialogViewState> { state ->
        hideProgress()
        when (state) {
            is TermsDialogViewState.Success -> goToHome()
            is TermsDialogViewState.SuccessLink -> openPdf(state.url)
            is TermsDialogViewState.Failed -> toast(state.message)
            is TermsDialogViewState.SuccessSync -> Unit
        }
    }

    private fun goToHome() {
        findNavController().navigate(R.id.globalToHomeActivity)
        activity?.finish()
    }

    private fun initViews() {
        val termsItem = Pair(getString(R.string.terms_cond), getString(R.string.terms_label))
        val privacyItem = Pair(getString(R.string.policy), getString(R.string.policy_label))
        val publicy = Pair(getString(R.string.publicy), getString(R.string.publicy_label))

        tvBlockSETitle?.text =
            String.format(
                getString(R.string.welcome_term_title),
                getString(BaseR.string.app_name_simple)
            )

        context?.let {
            tvTerms?.setLink(it, termsItem, ApproveTermsParams.TERM)
            tvPrivacy?.setLink(it, privacyItem, ApproveTermsParams.PRIVACY)
            tvPublicity?.setLink(it, publicy, ApproveTermsParams.PUBLICITY)
        }

        cbTerm?.setOnCheckedChangeListener { _, check ->
            termCheck = check
            btnContinue?.isEnabled = termCheck && privateCheck
        }

        cbPrivacy?.setOnCheckedChangeListener { _, check ->
            privateCheck = check
            btnContinue?.isEnabled = termCheck && privateCheck
        }

        cbPublicity?.setOnCheckedChangeListener { _, check ->
            publicyCheck = check
        }

        ivClose?.setOnClickListener { activity?.finish() }

        btnContinue?.setOnClickListener {
            showProgress()
            viewModel.saveTerms(getApprovedTerms())
        }
    }

    private fun getApprovedTerms(): List<String> {
        val terms = mutableListOf<String>()
        if (termCheck) terms.add(ApproveTermsParams.TERM)
        if (privateCheck) terms.add(ApproveTermsParams.PRIVACY)
        if (publicyCheck) terms.add(ApproveTermsParams.PUBLICITY)
        return terms
    }

    private fun TextView.setLink(
        context: Context, items: Pair<String, String>, link: String
    ) {
        this.apply {
            text = String.format(items.second, items.first)
            setLinkTextColor(ContextCompat.getColor(context, R.color.black))
            val matcher = Pattern.compile(items.first)
            Linkify.addLinks(this, matcher, "terms:")
            onLinkPressed { viewModel.getTermUrl(link) }
            movementMethod = LinkMovementMethod.getInstance()
        }
    }

    private fun openPdf(url: String) {
        if (url.isNotEmpty()) downloadHelper.openDocument(url)
        else toast(R.string.empty_url)
    }

    private fun showProgress() {
        loadingView?.visible()
    }

    private fun hideProgress() {
        loadingView?.gone()
    }

}
