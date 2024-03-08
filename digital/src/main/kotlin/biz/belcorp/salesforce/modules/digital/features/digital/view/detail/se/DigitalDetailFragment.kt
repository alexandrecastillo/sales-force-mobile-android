package biz.belcorp.salesforce.modules.digital.features.digital.view.detail.se

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import biz.belcorp.mobile.components.core.extensions.gone
import biz.belcorp.mobile.components.core.extensions.visible
import biz.belcorp.mobile.components.design.selector.bar.view.SelectorBar
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.base.FragmentParameters
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.constants.SourceType
import biz.belcorp.salesforce.core.domain.entities.consultants.ConsultantFilter
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.events.EventSubject
import biz.belcorp.salesforce.core.events.LiveDataBus
import biz.belcorp.salesforce.core.include.Include
import biz.belcorp.salesforce.core.include.IncludeManager
import biz.belcorp.salesforce.core.utils.inject
import biz.belcorp.salesforce.core.utils.replaceOnce
import biz.belcorp.salesforce.modules.digital.R
import biz.belcorp.salesforce.modules.digital.features.constants.DigitalFilterType
import biz.belcorp.salesforce.modules.digital.features.digital.model.DigitalFragmentParameters
import biz.belcorp.salesforce.modules.digital.features.digital.view.DigitalViewModel
import biz.belcorp.salesforce.modules.digital.features.digital.view.DigitalViewState
import biz.belcorp.salesforce.modules.digital.features.digital.view.detail.shared.DetailSelectorBuilder
import kotlinx.android.synthetic.main.fragment_digital_detail.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class DigitalDetailFragment : BaseFragment(), SelectorBar.SelectorBarListener {

    private val includeManager by inject<IncludeManager>()

    private val uaViewModel by sharedViewModel<DigitalViewModel>(from = { requireParentFragment() })

    private var sourceType = SourceType.NONE

    private val args by lazy { arguments?.getSerializable(FragmentParameters.key) as DigitalFragmentParameters }

    private var lastSelectedOption = EMPTY_STRING

    override fun getLayout() = R.layout.fragment_digital_detail

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupFragment()
        setupViewModel()
    }

    private fun setupViewModel() {
        uaViewModel.uaViewState.observe(viewLifecycleOwner, uaViewStateObserver)
    }

    override fun onSelected(model: Any?) {
        if (model is String) {
            lastSelectedOption = model
            updateConsultantsList()
        }
    }

    private fun updateSelectorOptions(@DigitalFilterType type: Int) {
        sourceType = DigitalFilterType.getSourceType(type)
        val options = DetailSelectorBuilder.init(requireContext()).with(type).build()
        selector?.apply {
            visible()
            dataSet = options
            addOnItemSelectorBarListener(this@DigitalDetailFragment)
        }
        val model = options.firstOrNull { it.isSelected }?.model as? String
        lastSelectedOption = model.orEmpty()
        updateConsultantsList()
    }

    private fun setupFragment() {
        createFragment(includeManager.getInclude(Include.BILLING_ADVANCEMENT))
    }

    private fun createFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction().replaceOnce(
            R.id.cfvDigital,
            fragment.javaClass.simpleName,
            childFragmentManager
        ) { fragment }.commit()
    }

    private fun updateUa(uaKey: LlaveUA) {
        args.uaKey = uaKey
        updateConsultantsList()
    }

    private fun updateConsultantsList() {
        publish(args.uaKey, lastSelectedOption)
    }

    private fun publish(uaKey: LlaveUA, filter: String) {
        LiveDataBus.publish(
            EventSubject.CONSULTANT_FILTERS,
            ConsultantFilter(sourceType, uaKey, listOf(filter))
        )
    }

    private fun showDetailView() {
        detailGroup?.visible()
    }

    private fun hideDetailView() {
        detailGroup?.gone()
    }

    private fun shouldUpdateDetailView(type: Int, f: () -> Unit) {
        if (type == DigitalFilterType.NONE) {
            hideDetailView()
        } else {
            showDetailView()
            f.invoke()
        }
    }

    private val uaViewStateObserver = Observer<DigitalViewState> { state ->
        when (state) {
            is DigitalViewState.ShowInfo -> {
                shouldUpdateDetailView(state.type) {
                    if (!state.asParent || args.isParent) {
                        updateUa(state.uaKey)
                        updateSelectorOptions(state.type)
                    }
                }
            }
        }
    }

    companion object {
        val TAG = DigitalDetailFragment::class.java.simpleName

        fun newInstance() =
            DigitalDetailFragment()
    }

}
