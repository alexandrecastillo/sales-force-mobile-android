package biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.view.detail.multiprofile.items.uadetail

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import biz.belcorp.mobile.components.design.selector.bar.model.SelectorModel
import biz.belcorp.salesforce.components.features.selector.BaseSelectorBarFragment
import biz.belcorp.salesforce.core.base.FragmentParameters
import biz.belcorp.salesforce.core.constants.KpiType
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.features.uainfo.UaInfoModel
import biz.belcorp.salesforce.core.utils.isGZ
import biz.belcorp.salesforce.core.utils.toast
import biz.belcorp.salesforce.core.utils.withArguments
import biz.belcorp.salesforce.modules.kpis.R
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.detail.multiprofile.items.uadetail.UaDetailViewState
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.view.detail.multiprofile.items.consolidated.grid.SaleOrderGridSelectorFragment
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.view.detail.se.SaleOrdersDetailSeFragment
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.model.KpiFragmentParameters
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.viewmodel.UaDetailViewModel
import biz.belcorp.salesforce.modules.kpis.utils.AnalyticUtils
import org.koin.android.viewmodel.ext.android.sharedViewModel
import biz.belcorp.salesforce.base.R as BaseR

class SaleOrdersUaDetailFragment : BaseSelectorBarFragment() {

    private val viewModel by sharedViewModel<UaDetailViewModel>()
    private val params get() = arguments?.getSerializable(FragmentParameters.key) as KpiFragmentParameters

    override fun getFragmentTheme() = R.style.KpiDetailTheme

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModels()
    }

    private fun setupViewModels() {
        viewModel.uaViewState.observe(viewLifecycleOwner, viewStateObserver)
        viewModel.getUaInformation()
    }

    private val viewStateObserver = Observer<UaDetailViewState> { state ->
        when (state) {
            is UaDetailViewState.Success -> setupSelector(state.items)
            is UaDetailViewState.ShowChildDescription ->
                createZoneInformation(state.uaName, state.personName, state.isCovered)
            is UaDetailViewState.Failure -> toast(state.message)
        }
    }

    private fun setupSelector(items: List<SelectorModel>) {
        val firstItem = items.firstOrNull() ?: return
        firstItem.isSelected = true
        createSelectorZoneData(items)
        if (firstItem.model is UaInfoModel) {
            val infoModel = firstItem.model as UaInfoModel
            setupTabDetail(infoModel.uaKey)
            viewModel.updateChildDetail(infoModel)
            viewModel.updateChildDescription(infoModel)
        }
    }

    private fun setupTabDetail(uaKey: LlaveUA) = with(params) {
        when {
            rol.isGZ() -> {
                updateSectionUaKey(uaKey)
                showChildDetail()
            }
            else -> createFragment(
                SaleOrderGridSelectorFragment.newInstance(
                    params.apply { isParent = false })
            )
        }
    }

    private fun showChildDetail() {
        val fragment = SaleOrdersDetailSeFragment.newInstance()
        fragment.arguments = bundleOf(FragmentParameters.key to params)
        createFragment(fragment)
    }

    private fun createFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction().replace(
            BaseR.id.flContainer, fragment, fragment.javaClass.simpleName
        ).commit()
    }

    private fun updateSectionUaKey(uaKey: LlaveUA) {
        params.uaKey = uaKey
    }

    override fun onItemBarSelected(model: Any?) {
        model?.let {
            if (model is UaInfoModel) {
                viewModel.updateChildDescription(model)
                viewModel.updateChildDetail(model)
                AnalyticUtils.seleccionaTuZona(KpiType.SALE_ORDERS, model.label)
            }
        }
    }

    companion object {
        fun newInstance(params: KpiFragmentParameters): SaleOrdersUaDetailFragment {
            return SaleOrdersUaDetailFragment()
                .withArguments(FragmentParameters.key to params)
        }
    }
}
