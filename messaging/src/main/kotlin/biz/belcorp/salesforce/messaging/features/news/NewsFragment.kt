package biz.belcorp.salesforce.messaging.features.news

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import biz.belcorp.salesforce.core.base.BaseDialogFragment
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.core.utils.AppUri.ACTION_KEY
import biz.belcorp.salesforce.core.utils.AppUri.CODE_KEY
import biz.belcorp.salesforce.messaging.R
import biz.belcorp.salesforce.messaging.features.news.model.NewsModel
import kotlinx.android.synthetic.main.fragment_news.*
import org.koin.android.viewmodel.ext.android.viewModel


class NewsFragment : BaseDialogFragment() {

    private val viewModel by viewModel<NewsViewModel>()

    private val code by lazy { arguments?.getString(CODE_KEY) }

    override fun getLayout() = R.layout.fragment_news

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    override fun onStart() {
        super.onStart()
        fitFullScreen()
    }

    private fun initialize() {
        viewModel.viewState.observe(viewLifecycleOwner, viewStateObserver)
        viewModel.loadDetail(code ?: return)
        initEvents()
    }

    private fun initEvents() {
        btnClose?.setOnClickListener {
            dismiss()
        }
    }

    private val viewStateObserver = Observer { state: NewsViewState ->
        when (state) {
            is NewsViewState.ShowDetail -> setupView(state.model)
        }
    }

    private fun setupView(model: NewsModel) = with(model) {
        setupCloseButton(model)
        setupNewsImage(model)
    }

    private fun setupCloseButton(model: NewsModel) = with(model) {
        btnClose?.setColorFilter(Color.parseColor(closeIconColor))
        btnClose?.background?.setTint(Color.parseColor(closeBgColor))
    }

    private fun setupNewsImage(model: NewsModel) = with(model) {
        pbNews?.visible()
        ivNews?.loadImage(imageUrl) { pbNews?.gone() }
        ivNews?.setOnClickListener { manageImageEvent(this) }
    }

    private fun manageImageEvent(model: NewsModel) = with(model) {
        when {
            videoUrl != null -> loadVideo(videoUrl!!)
            else -> loadDisplay(action)
        }
    }

    private fun loadVideo(videoUrl: String) {
        val uri = Uri.parse(videoUrl)
        startActivity(uri)
    }

    private fun loadDisplay(action: String?) {
        val uri = AppUri.create()
            .withParameters(
                ACTION_KEY to action,
                CODE_KEY to code.toString()
            )
            .build()
        startActivity(uri)
    }

    private fun startActivity(uri: Uri) {
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
        dismiss()
    }
}
