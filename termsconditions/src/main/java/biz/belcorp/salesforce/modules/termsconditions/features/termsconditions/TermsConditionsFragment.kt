package biz.belcorp.salesforce.modules.termsconditions.features.termsconditions

import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.utils.DocumentHelper
import biz.belcorp.salesforce.core.utils.toast
import biz.belcorp.salesforce.modules.termsconditions.R
import biz.belcorp.salesforce.modules.termsconditions.features.termsconditions.adapters.TermsConditionsAdapter
import biz.belcorp.salesforce.modules.termsconditions.features.termsconditions.model.TermsConditionsModel
import biz.belcorp.salesforce.modules.termsconditions.features.sync.utils.startTermsSyncWorker
import kotlinx.android.synthetic.main.fragment_terms_conditions.*
import org.koin.android.viewmodel.ext.android.viewModel

class TermsConditionsFragment : BaseFragment() {

    override fun getLayout(): Int = R.layout.fragment_terms_conditions

    private val viewModel by viewModel<TermsConditionsViewModel>()

    private val downloadHelper by lazy { DocumentHelper(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ViewCompat.setTranslationZ(view, 2F)
        initEvents()
        setupViewModels()
    }

    private fun setupViewModels() {
        viewModel.viewState.observe(viewLifecycleOwner, observerDataResponse)
        viewModel.getPoliticsTermsConditions()
    }

    private val observerDataResponse = Observer<TermsConditionsViewState> { state ->
        when (state) {
            is TermsConditionsViewState.Success -> showPoliticsTermConditions(state.data)
            is TermsConditionsViewState.Failed -> toast(state.message)
        }
    }

    override fun onResume() {
        super.onResume()
        startSync()
    }

    private fun initEvents() {
        btnBack?.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun showPoliticsTermConditions(items: List<TermsConditionsModel>) {
        val adapter = TermsConditionsAdapter(items)
        adapter.setDownloadDocumentListener { downloadHelper.downloadDocument(it) }
        adapter.setOpenDocumentListener { downloadHelper.openDocument(it) }
        rvPoliticsTermsConditions?.layoutManager = LinearLayoutManager(context)
        rvPoliticsTermsConditions?.adapter = adapter
    }

    private fun startSync() {
        context?.startTermsSyncWorker()
    }

}
