package biz.belcorp.salesforce.modules.digital.features.digital.view.detail.multiprofile

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import biz.belcorp.mobile.components.core.extensions.gone
import biz.belcorp.mobile.components.core.extensions.visible
import biz.belcorp.mobile.components.design.spreadsheet.models.Column
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.base.FragmentParameters
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.digital.R
import biz.belcorp.salesforce.modules.digital.features.constants.DigitalFilterType
import biz.belcorp.salesforce.modules.digital.features.digital.model.DigitalFragmentParameters
import biz.belcorp.salesforce.modules.digital.features.digital.view.DigitalViewModel
import biz.belcorp.salesforce.modules.digital.features.digital.view.DigitalViewState
import kotlinx.android.synthetic.main.fragment_digital_multiprofile_detail.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class DigitalConsolidatedFragment : BaseFragment() {

    private val params by lazy {
        arguments?.getSerializable(FragmentParameters.key) as DigitalFragmentParameters
    }

    private val viewModel by viewModel<DigitalConsolidatedViewModel>()
    private val uaViewModel by sharedViewModel<DigitalViewModel>(from = { requireParentFragment() })

    override fun getLayout() = R.layout.fragment_digital_multiprofile_detail

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModels()
    }

    private fun setupViewModels() {
        viewModel.viewState.observe(viewLifecycleOwner, viewStateObserver)
        uaViewModel.uaViewState.observe(viewLifecycleOwner, viewUaStateObserver)
    }

    private fun updateParams(type: Int, uaKey: LlaveUA) {
        params.uaKey = uaKey
        viewModel.getGridData(type, uaKey)
    }

    private fun updateView(data: List<Column>) {
        grid?.data = data.toMutableList()
    }

    private fun showDetailView() {
        grid?.visible()
    }

    private fun hideDetailView() {
        grid?.gone()
    }

    private fun showInfoViewState(state: DigitalViewState.ShowInfo) {
        if (state.type == DigitalFilterType.NONE) {
            hideDetailView()
        } else {
            showDetailView()
            updateParams(state.type, state.uaKey)
        }
    }

    private val viewStateObserver = Observer<DigitalConsolidatedViewState> { state ->
        when (state) {
            is DigitalConsolidatedViewState.Success -> updateView(state.columns)
        }
    }

    private val viewUaStateObserver = Observer<DigitalViewState> { state ->
        when (state) {
            is DigitalViewState.ShowInfo -> showInfoViewState(state)
        }
    }

    companion object {
        val TAG = DigitalConsolidatedFragment::class.java.simpleName

        fun newInstance() = DigitalConsolidatedFragment()
    }

}
