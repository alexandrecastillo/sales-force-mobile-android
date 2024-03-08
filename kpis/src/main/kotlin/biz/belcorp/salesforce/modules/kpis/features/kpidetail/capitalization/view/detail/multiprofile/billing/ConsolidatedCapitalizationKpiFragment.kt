package biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.view.detail.multiprofile.billing

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import biz.belcorp.mobile.components.design.spreadsheet.Spreadsheet
import biz.belcorp.mobile.components.design.spreadsheet.models.Column
import biz.belcorp.salesforce.core.base.FragmentParameters
import biz.belcorp.salesforce.core.constants.KpiType
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.utils.getCompatColor
import biz.belcorp.salesforce.core.utils.isNotNull
import biz.belcorp.salesforce.core.utils.withArguments
import biz.belcorp.salesforce.modules.kpis.R
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.view.detail.multiprofile.sales.consolidated.grid.GridCapitalizationKpiViewModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.view.detail.multiprofile.sales.consolidated.grid.GridCapitalizationViewState
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.detail.multiprofile.items.uadetail.UaDetailViewState
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.KpiGridSelectorFragment
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.model.KpiFragmentParameters
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.viewmodel.UaDetailViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class ConsolidatedCapitalizationKpiFragment : KpiGridSelectorFragment() {

    private val viewModel by viewModel<GridCapitalizationKpiViewModel>()
    private val uaViewModel by sharedViewModel<UaDetailViewModel>()

    private val isParent by lazy { getParentArgument() }
    private var uaKey: LlaveUA? = null

    private val observerDataResponse = Observer<GridCapitalizationViewState> { state ->
        when (state) {
            is GridCapitalizationViewState.Success -> doOnSuccess(state.data)
        }
    }

    private val uaViewState = Observer<UaDetailViewState> {
        when (it) {
            is UaDetailViewState.UpdateItem -> {
                this.uaKey = it.uaKey
                getGridInformation(lastSelectedOption, this.uaKey)
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    override fun notifyOptionSelected(option: String) {
        when {
            isParent -> getGridInformation(option)
            !isParent && uaKey.isNotNull() -> getGridInformation(option, uaKey)
            else -> Unit
        }
    }

    private fun setup() {
        updateSelectorOptions(KpiType.CAPITALIZATION)
        setupViewModel()
        setupAdvancementLabel()
    }

    private fun getParentArgument(): Boolean {
        val params = arguments?.getSerializable(FragmentParameters.key) as? KpiFragmentParameters
        this.uaKey = params?.uaKey
        return params?.isParent ?: false
    }

    private fun setupViewModel() {
        viewModel.viewState.observe(viewLifecycleOwner, observerDataResponse)
        if (!isParent) uaViewModel.uaViewState.observe(viewLifecycleOwner, uaViewState)
    }

    override fun setupGridView(view: Spreadsheet) {
        view.mainColor = getCompatColor(R.color.colorRetentionCapitalizationGrid)
        view.headerTextAppearance = R.style.TextAppearance_Digital_Grid_Header_Retention
    }

    private fun getGridInformation(type: String, uaKey: LlaveUA? = null) {
        viewModel.getGridData(type, uaKey)
    }

    private fun doOnSuccess(gridData: List<Column>) {
        updateGrid(gridData)
    }

    companion object {
        val TAG = ConsolidatedCapitalizationKpiFragment::class.java.simpleName

        fun newInstance(params: KpiFragmentParameters) = ConsolidatedCapitalizationKpiFragment()
            .withArguments(FragmentParameters.key to params)

        fun newInstance() = ConsolidatedCapitalizationKpiFragment()

    }

}
