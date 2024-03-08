package biz.belcorp.salesforce.base.features.webpage

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.Observer
import biz.belcorp.mobile.components.core.extensions.getColor
import biz.belcorp.mobile.components.core.extensions.gone
import biz.belcorp.mobile.components.core.extensions.tinted
import biz.belcorp.salesforce.base.R
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.domain.usecases.browser.WebTopic
import biz.belcorp.salesforce.core.utils.actionBar
import biz.belcorp.salesforce.core.utils.getCompatColor
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.android.synthetic.main.fragment_web_page.*
import org.koin.android.viewmodel.ext.android.viewModel


class WebPageFragment : BaseFragment() {

    private val viewModel by viewModel<WebPageViewModel>()

    private val args
        get() = arguments?.let {
            WebPageFragmentArgs.fromBundle(it)
        }

    override fun getLayout() = R.layout.fragment_web_page

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() {
        prepareView(args?.topic)
        viewModel.viewState.observe(viewLifecycleOwner, observeViewState)
        viewModel.getUrlByTopic(args?.topic)
    }

    private fun prepareView(webTopic: WebTopic?) {
        val (titleId, colorId) = when (webTopic) {
            WebTopic.MY_ACADEMY -> R.string.title_webpage_my_academy to R.color.colorToolbarDefault
            WebTopic.UCB -> R.string.title_webpage_ucb to R.color.colorToolbarUCB
            WebTopic.DATA_REPORT -> R.string.title_webpage_data_report to R.color.colorToolbarDataReport
            WebTopic.MATERIALES_REDES_SOCIALES -> R.string.title_webpage_materiales_redes_sociales to R.color.colorToolbarUCB
            else -> R.string.app_name to R.color.colorToolbarDefault
        }
        setupToolbar(getString(titleId), getCompatColor(colorId))
    }

    private fun setupToolbar(title: String, color: Int) {
        actionBar(toolbar as MaterialToolbar) {
            setTitle(title)
            setBackgroundColor(color)
            setNavigationIcon(R.drawable.ic_backspace)
            navigationIcon?.tinted(getColor(R.color.white))
            setNavigationOnClickListener { close() }
        }
    }

    private fun close() {
        activity?.onBackPressed()
    }

    private val observeViewState = Observer { state: WebPageViewState ->
        when (state) {
            is WebPageViewState.LoadWebPage -> loadUrl(state.url)
            WebPageViewState.Failed -> hideLoading()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun loadUrl(url: String) {
        try {
            webview?.settings?.javaScriptEnabled = true
            webview?.settings?.domStorageEnabled = true
            webview?.isScrollbarFadingEnabled = true
            webview?.loadUrl(url)
            webview?.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView, url: String) {
                    hideLoading()
                }
            }
        } catch (ex: Exception) {
            hideLoading()
        }
    }

    private fun hideLoading() {
        pgbWeb?.gone()
    }

}
