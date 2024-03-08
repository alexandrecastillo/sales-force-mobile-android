package biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.view.detail.multiprofile.items.consolidated.grid

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import biz.belcorp.mobile.components.design.spreadsheet.Spreadsheet
import biz.belcorp.salesforce.core.base.FragmentParameters
import biz.belcorp.salesforce.core.constants.KpiType
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.events.ConsumableEvent
import biz.belcorp.salesforce.core.utils.getCompatColor
import biz.belcorp.salesforce.core.utils.isNotNull
import biz.belcorp.salesforce.core.utils.withArguments
import biz.belcorp.salesforce.modules.kpis.R
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.detail.multiprofile.items.uadetail.UaDetailViewState
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.KpiGridSelectorFragment
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.model.KpiFragmentParameters
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.viewmodel.UaDetailViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class SaleOrderGridSelectorFragment : KpiGridSelectorFragment() {

    private val viewModel by viewModel<ConsolidatedGridViewModel>()
    private val uaViewModel by sharedViewModel<UaDetailViewModel>()

    private val isParent by lazy { getParentArgument() }
    private var uaKey: LlaveUA? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    private fun getParentArgument(): Boolean {
        val params = arguments?.getSerializable(FragmentParameters.key) as? KpiFragmentParameters
        return params?.isParent ?: false
    }

    private fun setupViewModel() {
        viewModel.viewState.observe(viewLifecycleOwner, optionViewState)
        if (!isParent) uaViewModel.uaViewState.observe(viewLifecycleOwner, uaViewState)
    }

    private fun setup() {
        updateSelectorOptions(KpiType.SALE_ORDERS)
        setupViewModel()
        setupAdvancementLabel()
    }

    override fun setupGridView(view: Spreadsheet) {
        view.mainColor = getCompatColor(R.color.colorSaleOrders)
        view.headerTextAppearance = R.style.TextAppearance_Digital_Grid_Header_SaleOrders
    }

    override fun notifyOptionSelected(option: String) {
        when {
            isParent -> viewModel.getGridData(option)
            !isParent && uaKey.isNotNull() -> viewModel.getGridData(option, uaKey)
            else -> Unit
        }
    }

    private val uaViewState = Observer<UaDetailViewState> {
        when (it) {
            is UaDetailViewState.UpdateItem -> {
                this.uaKey = it.uaKey
                viewModel.getGridData(lastSelectedOption, this.uaKey)
            }
        }
    }

    private val optionViewState = Observer<ConsumableEvent> {
        it.runAndConsume {
            when (val state = it.value as ConsolidatedGridViewState) {
                is ConsolidatedGridViewState.Success -> updateGrid(state.columns)
            }
        }
    }

    companion object {
        val TAG = SaleOrderGridSelectorFragment::class.java.simpleName

        fun newInstance(params: KpiFragmentParameters): Fragment =
            SaleOrderGridSelectorFragment().withArguments(FragmentParameters.key to params)

        fun newInstance(): Fragment = SaleOrderGridSelectorFragment()
    }
}
