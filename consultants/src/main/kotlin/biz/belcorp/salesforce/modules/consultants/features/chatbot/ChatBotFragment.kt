package biz.belcorp.salesforce.modules.consultants.features.chatbot

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.*
import androidx.lifecycle.Observer
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.utils.gone
import biz.belcorp.salesforce.core.utils.visible
import biz.belcorp.salesforce.modules.consultants.R
import kotlinx.android.synthetic.main.fragment_chatbot.*
import org.koin.android.viewmodel.ext.android.viewModel

class ChatBotFragment : BaseFragment() {

    private val chatBotViewModel by viewModel<ChatBotViewModel>()

    override fun getLayout() = R.layout.fragment_chatbot

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chatBotViewModel.viewState.observe(viewLifecycleOwner, observeViewState)
        chatBotViewModel.getChatBotUrl()
        initView()
    }

    private val observeViewState = Observer { state: ChatBotViewState ->
        when (state) {
            is ChatBotViewState.ChatBotSuccess -> webView?.loadUrl(state.url)
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initView() {
        progressBar.visible()
        webView?.apply {
            settings?.apply {
                allowFileAccess = true
                allowContentAccess = true
                javaScriptEnabled = true
                domStorageEnabled = true
                databaseEnabled = true
                builtInZoomControls = false
                cacheMode = WebSettings.LOAD_NO_CACHE;
            }
            webChromeClient = object : WebChromeClient() {
                override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
                    Log.i(TAG, consoleMessage?.message().orEmpty())
                    return true
                }

                override fun onProgressChanged(view: WebView, progress: Int) = Unit
            }
            webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView, url: String) {
                    progressBar?.gone()
                }
            }

        }
    }

    companion object {
        val TAG = ChatBotFragment::class.java.simpleName
    }
}
