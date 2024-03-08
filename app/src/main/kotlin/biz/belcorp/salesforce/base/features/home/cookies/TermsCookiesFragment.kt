package biz.belcorp.salesforce.base.features.home.cookies

import android.content.Context
import android.os.Bundle
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.UnderlineSpan
import android.text.util.Linkify
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import biz.belcorp.salesforce.base.R
import biz.belcorp.salesforce.base.utils.onLinkPressed
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.domain.entities.terms.ApproveTermsParams
import biz.belcorp.salesforce.core.events.EventSubject
import biz.belcorp.salesforce.core.events.LiveDataBus
import biz.belcorp.salesforce.core.utils.DocumentHelper
import biz.belcorp.salesforce.core.utils.gone
import biz.belcorp.salesforce.core.utils.toast
import biz.belcorp.salesforce.core.utils.visible
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.include_cookies.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.regex.Pattern

class TermsCookiesFragment : BaseFragment() {

    override fun getLayout() = R.layout.include_cookies

    private val viewModel by viewModel<TermsCookiesViewModel>()
    private val downloadHelper by lazy { DocumentHelper(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setupViewModels()
    }

    private fun initViews() {
        val cookiesItem = Pair(getString(R.string.cookies), getString(R.string.cookies_label))
        context?.let {
            tvCookies?.setLink(it, cookiesItem, ApproveTermsParams.COOKIES)
        }

        val undestoodText = getString(R.string.accept)
        val content = SpannableString(undestoodText)
        content.setSpan(UnderlineSpan(), 0, undestoodText.length, 0)
        btnApproveCookies?.text = content

        btnApproveCookies?.setOnClickListener {
            viewModel.saveTerms()
        }
    }


    private fun setupViewModels() {
        viewModel.viewState.observe(viewLifecycleOwner, observerResponse)
    }

    private val observerResponse = Observer<TermsCookiesViewState> { state ->
        when (state) {
            is TermsCookiesViewState.SuccessLink -> openPdf(state.url)
            is TermsCookiesViewState.SuccessApproved -> onSaveResponse()
            is TermsCookiesViewState.Failed -> toast(state.message)
        }
    }

    private fun openPdf(url: String) {
        if (url.isNotEmpty()) downloadHelper.openDocument(url)
        else toast(R.string.empty_url)
    }

    private fun onSaveResponse() {
        LiveDataBus.publish(EventSubject.TERM_COOKIES, true)
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

    companion object {
        val TAG = TermsCookiesFragment::class.java.simpleName
        fun newInstance() = TermsCookiesFragment()
    }
}
