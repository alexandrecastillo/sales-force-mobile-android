package biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.detail.multiprofile.items.uadetail

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
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
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.detail.multiprofile.items.consolidated.grid.GainConsolidatedGridFragment
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.detail.se.GainDetailSeFragment
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.model.KpiFragmentParameters
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.viewmodel.UaDetailViewModel
import biz.belcorp.salesforce.modules.kpis.utils.AnalyticUtils
import org.koin.android.viewmodel.ext.android.sharedViewModel
import biz.belcorp.salesforce.base.R as BaseR

class GainUaDetailFragment : BaseSelectorBarFragment() {

    private val viewModel by sharedViewModel<UaDetailViewModel>()
    private val params get() = arguments?.getSerializable(FragmentParameters.key) as KpiFragmentParameters

    override fun getFragmentTheme() = R.style.KpiDetailTheme

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModels()
    }

    private fun setupViewModels() {
        viewModel.uaViewState.observe(viewLifecycleOwner, uaInofViewStateObserver)
        viewModel.getUaInformation()
    }

    private val uaInofViewStateObserver = Observer<UaDetailViewState> { state ->
        when (state) {
            is UaDetailViewState.Success -> setupSelector(state.items)
            is UaDetailViewState.ShowChildDescription -> createZoneInformation(
                state.uaName,
                state.personName,
                state.isCovered
            )
        }
    }

    private fun setupSelector(model: List<SelectorModel>) {
        val firstItem = model.firstOrNull() ?: return
        firstItem.isSelected = true
        createSelectorZoneData(model)
        if (firstItem.model is UaInfoModel) {
            val infoModel = firstItem.model as UaInfoModel
            updateUaKey(infoModel.uaKey)
            setupTabDetail()
            viewModel.updateChildDescription(infoModel)
        }
    }

    private fun setupTabDetail() {
        return when {
            params.rol.isGZ() -> showChildDetail()
            else -> showConsolidatedDetail()
        }
    }

    private fun showChildDetail() {
        val fragment = GainDetailSeFragment.newInstance()
        createFragment(BaseR.id.flContainer, fragment, params)
    }

    private fun showConsolidatedDetail() {
        val fragment = GainConsolidatedGridFragment.newInstance(params.apply { isParent = false })
        createFragment(BaseR.id.flContainer, fragment, params)
    }

    private fun createFragment(@IdRes id: Int, fragment: Fragment, params: KpiFragmentParameters) {
        childFragmentManager.beginTransaction().replaceOnce(
            id,
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
            viewModel.updateChildDescription(infoModel)
            viewModel.updateChildDetail(infoModel)
            AnalyticUtils.seleccionaTuZona(KpiType.COLLECTION, infoModel.label)
        }
    }

    companion object {

        fun newInstance(params: KpiFragmentParameters): GainUaDetailFragment {
            return GainUaDetailFragment()
                .withArguments(FragmentParameters.key to params)
        }
    }
}
