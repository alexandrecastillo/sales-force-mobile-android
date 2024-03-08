package biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.view.detail.multiprofile.uadetail

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
import biz.belcorp.salesforce.core.utils.replaceOnce
import biz.belcorp.salesforce.core.utils.withArguments
import biz.belcorp.salesforce.modules.kpis.R
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.detail.multiprofile.items.uadetail.UaDetailViewState
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.view.detail.multiprofile.consolidated.NewCycleGridSelectorFragment
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.view.detail.se.NewCycleDetailSeFragment
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.model.KpiFragmentParameters
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.viewmodel.UaDetailViewModel
import biz.belcorp.salesforce.modules.kpis.utils.AnalyticUtils
import org.koin.android.viewmodel.ext.android.sharedViewModel
import biz.belcorp.salesforce.base.R as BaseR

class NewCycleUaDetailFragment : BaseSelectorBarFragment() {

    private val uaViewModel by sharedViewModel<UaDetailViewModel>()
    private val params get() = arguments?.getSerializable(FragmentParameters.key) as KpiFragmentParameters

    override fun getFragmentTheme() = R.style.KpiDetailTheme

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModels()
    }

    private fun setupViewModels() {
        uaViewModel.uaViewState.observe(viewLifecycleOwner, uaInfoViewStateObserver)
        uaViewModel.getUaInformation()
    }

    private val uaInfoViewStateObserver = Observer<UaDetailViewState> { state ->
        when (state) {
            is UaDetailViewState.Success -> setupSelector(state.items)
            is UaDetailViewState.ShowChildDescription -> createZoneInformation(
                state.uaName,
                state.personName,
                state.isCovered
            )
        }
    }

    private fun setupSelector(selectorList: List<SelectorModel>) {
        val firstItem = selectorList.firstOrNull() ?: return
        firstItem.isSelected = true
        createSelectorZoneData(selectorList)
        if (firstItem.model is UaInfoModel) {
            val infoModel = firstItem.model as UaInfoModel
            setupTabDetail(infoModel.uaKey)
            uaViewModel.updateChildDescription(infoModel)
            uaViewModel.updateChildDetail(infoModel)
        }
    }

    private fun setupTabDetail(uaKey: LlaveUA) {
        return when {
            params.rol.isGZ() -> {
                updateUaKey(uaKey)
                showChildDetailByDefault()
            }
            else -> showGridSelector()
        }
    }

    private fun showGridSelector() {
        val fragment = NewCycleGridSelectorFragment.newInstance()
        createFragment(fragment, params.apply { isParent = false })
    }

    private fun showChildDetailByDefault() {
        val fragment = NewCycleDetailSeFragment.newInstance()
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

    private fun updateUaKey(uaKey: LlaveUA) {
        params.uaKey = uaKey
    }

    override fun onItemBarSelected(model: Any?) {
        model?.let {
            val infoModel = model as UaInfoModel
            uaViewModel.updateChildDescription(infoModel)
            uaViewModel.updateChildDetail(infoModel)
            AnalyticUtils.seleccionaTuZona(KpiType.NEW_CYCLES, infoModel.label)
        }
    }

    companion object {
        fun newInstance(params: KpiFragmentParameters): NewCycleUaDetailFragment {
            return NewCycleUaDetailFragment()
                .withArguments(FragmentParameters.key to params)
        }
    }
}

