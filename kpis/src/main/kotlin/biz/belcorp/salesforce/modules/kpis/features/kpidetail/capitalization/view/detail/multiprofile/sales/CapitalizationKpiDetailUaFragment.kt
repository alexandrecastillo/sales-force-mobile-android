package biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.view.detail.multiprofile.sales

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import biz.belcorp.mobile.components.design.selector.bar.model.SelectorModel
import biz.belcorp.salesforce.components.features.selector.BaseSelectorBarFragment
import biz.belcorp.salesforce.core.base.FragmentParameters
import biz.belcorp.salesforce.core.constants.KpiType.Companion.CAPITALIZATION
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.features.uainfo.UaInfoModel
import biz.belcorp.salesforce.core.utils.isGZ
import biz.belcorp.salesforce.core.utils.replaceOnce
import biz.belcorp.salesforce.core.utils.withArguments
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.view.detail.multiprofile.billing.ConsolidatedCapitalizationKpiFragment
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.view.detail.se.sales.CapitalizationKpiDetailSeOnSalesFragment
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.detail.multiprofile.items.uadetail.UaDetailViewState
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.model.KpiFragmentParameters
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.viewmodel.UaDetailViewModel
import biz.belcorp.salesforce.modules.kpis.utils.AnalyticUtils
import org.koin.android.viewmodel.ext.android.sharedViewModel
import biz.belcorp.salesforce.base.R as BaseR
import biz.belcorp.salesforce.modules.kpis.R as KpisR

class CapitalizationKpiDetailUaFragment : BaseSelectorBarFragment() {

    private val params by lazy {
        arguments?.getSerializable(FragmentParameters.key) as KpiFragmentParameters
    }

    private val viewModel by sharedViewModel<UaDetailViewModel>()

    private val uaInfoViewStateObserver = Observer<UaDetailViewState> { state ->
        when (state) {
            is UaDetailViewState.Success -> setupSelector(state.items)
            is UaDetailViewState.ShowChildDescription ->
                createZoneInformation(state.uaName, state.personName, state.isCovered)
        }
    }

    override fun getFragmentTheme() = KpisR.style.KpiDetailTheme

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
    }

    override fun onItemBarSelected(model: Any?) {
        model?.let {
            val infoModel = model as UaInfoModel
            viewModel.updateChildDescription(infoModel)
            viewModel.updateChildDetail(infoModel)
            AnalyticUtils.seleccionaTuZona(CAPITALIZATION, infoModel.label)
        }
    }

    private fun setupViewModel() {
        viewModel.uaViewState.observe(viewLifecycleOwner, uaInfoViewStateObserver)
        viewModel.getUaInformation()
    }

    private fun setupSelector(model: List<SelectorModel>) {
        val firstItem = model.firstOrNull() ?: return
        firstItem.isSelected = true
        createSelectorZoneData(model)
        if (firstItem.model is UaInfoModel) {
            val infoModel = firstItem.model as UaInfoModel
            setupTabDetail(infoModel.uaKey)
            viewModel.updateChildDescription(infoModel)
        }
    }

    private fun setupTabDetail(ua: LlaveUA) {
        updateUaKey(ua)
        return when {
            params.rol.isGZ() -> showChildContent()
            else -> showMultiProfileChildContent()
        }
    }

    private fun updateUaKey(uaKey: LlaveUA) {
        params.uaKey = uaKey
    }

    private fun showChildContent() {
        params.apply { isParent = false }
        val fragment = CapitalizationKpiDetailSeOnSalesFragment.newInstance()
        createFragment(fragment, params)
    }

    private fun showMultiProfileChildContent() {
        params.apply { isParent = false }
        val fragment = ConsolidatedCapitalizationKpiFragment.newInstance()
        createFragment(fragment, params)
    }

    private fun createFragment(fragment: Fragment, params: KpiFragmentParameters) {
        childFragmentManager.beginTransaction().replaceOnce(
            BaseR.id.flContainer,
            fragment.javaClass.simpleName,
            bundleOf(FragmentParameters.key to params),
            childFragmentManager
        ) { fragment }.commit()
    }

    companion object {
        val TAG = CapitalizationKpiDetailUaFragment::class.java.simpleName

        fun newInstance(params: KpiFragmentParameters) = CapitalizationKpiDetailUaFragment()
            .withArguments(FragmentParameters.key to params)
    }

}
