package biz.belcorp.salesforce.modules.digital.features.digital.view.header

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import biz.belcorp.mobile.components.core.extensions.gone
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.base.FragmentParameters
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.digital.R
import biz.belcorp.salesforce.modules.digital.features.constants.DigitalFilterType
import biz.belcorp.salesforce.modules.digital.features.digital.adapters.DigitalHeaderAdapter
import biz.belcorp.salesforce.modules.digital.features.digital.model.DigitalFragmentParameters
import biz.belcorp.salesforce.modules.digital.features.digital.model.DigitalHeaderModel
import biz.belcorp.salesforce.modules.digital.features.digital.view.DigitalViewModel
import biz.belcorp.salesforce.modules.digital.features.digital.view.DigitalViewState
import kotlinx.android.synthetic.main.fragment_digital_header.*
import kotlinx.android.synthetic.main.include_data_no_available.*
import kotlinx.android.synthetic.main.include_digital_header.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class DigitalHeaderFragment : BaseFragment() {

    private val viewModel by viewModel<DigitalHeaderViewModel>()
    private val uaViewModel by sharedViewModel<DigitalViewModel>(from = { requireParentFragment() })

    private val args by lazy {
        requireNotNull(arguments?.getSerializable(FragmentParameters.key)) as DigitalFragmentParameters
    }

    private val pagerAdaper = DigitalHeaderAdapter()

    override fun getLayout() = R.layout.fragment_digital_header

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    private fun setup() {
        setupViewPager()
        setupViewModel()
        listenDigitalChanges()
    }

    private fun setupViewModel() {
        viewModel.viewState.observe(viewLifecycleOwner, digitalObserver)
        viewModel.getDigitalInfo(args.uaKey)
    }

    private fun setupViewPager() {
        viewPager?.adapter = pagerAdaper
        viewPager?.onPageSelected {
            updateButtonStates(it)
            updateSelectors(it)
        }
        btnPrevPage?.setOnClickListener {
            viewPager?.previousPage()
        }
        btnNextPage?.setOnClickListener {
            viewPager?.nextPage()
        }
        viewPagerIndicator?.setupWithViewPager(viewPager)
    }

    private fun updateButtonStates(position: Int) {
        btnPrevPage?.isEnabled = position > Constant.NUMBER_ZERO
        btnNextPage?.isEnabled = position < viewPager.lastIndex()
    }

    private fun updateSelectors(position: Int) {
        uaViewModel.updateSelectors(position)
    }

    private fun listenDigitalChanges() {
        uaViewModel.uaViewState.observe(viewLifecycleOwner, uaViewStateObserver)
    }

    private fun showDigitalInfo(model: DigitalHeaderModel) {
        pagerAdaper.updateList(model.items)
        viewPagerGroup?.visible()
        hideEmptyView()
        uaViewModel.updateSelectors(viewPager.currentItem)
    }

    private fun hideDigitalInfo() {
        pagerAdaper.clear()
        viewPagerGroup?.gone()
        showEmptyView()
        uaViewModel.updateSelectors(DigitalFilterType.NONE)
    }

    private fun showEmptyView() {
        iEmptyView?.visible()
        val uaName = args.uaKey.roleAssociated.nameAsCode()
        tvEmptyData?.text = getString(R.string.digital_text_data_no_available, uaName)
    }

    private fun hideEmptyView() {
        iEmptyView?.gone()
        args.uaKey.roleAssociated.nameAsCode()
    }

    private fun updateUa(uaKey: LlaveUA) {
        args.uaKey = uaKey
        viewModel.getDigitalInfo(uaKey)
    }

    private val uaViewStateObserver = Observer<DigitalViewState> { state ->
        when (state) {
            is DigitalViewState.ShowInfo -> {
                if (state.updateHeader) updateUa(state.uaKey)
            }
        }
    }

    private val digitalObserver = Observer<DigitalHeaderViewState> { state ->
        when (state) {
            is DigitalHeaderViewState.Success -> showDigitalInfo(state.model)
            is DigitalHeaderViewState.NoDataAvailable -> hideDigitalInfo()
        }
    }

    companion object {
        val TAG = DigitalHeaderFragment::class.java.simpleName

        fun newInstance() = DigitalHeaderFragment()
    }

}
