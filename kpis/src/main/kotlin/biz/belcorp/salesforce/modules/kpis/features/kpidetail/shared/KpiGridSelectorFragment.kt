package biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared

import androidx.lifecycle.Observer
import biz.belcorp.mobile.components.core.extensions.visible
import biz.belcorp.mobile.components.design.selector.bar.view.SelectorBar
import biz.belcorp.mobile.components.design.spreadsheet.Spreadsheet
import biz.belcorp.mobile.components.design.spreadsheet.models.Column
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.constants.KpiType
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.utils.getCompatColor
import biz.belcorp.salesforce.core.utils.visible
import biz.belcorp.salesforce.modules.kpis.R
import biz.belcorp.salesforce.modules.kpis.utils.AnalyticUtils
import kotlinx.android.synthetic.main.fragment_kpi_grid_selector.*
import kotlinx.android.synthetic.main.include_data_not_available.*
import org.koin.android.viewmodel.ext.android.viewModel

abstract class KpiGridSelectorFragment : BaseFragment(), SelectorBar.SelectorBarListener {

    private val kpiGridSelectorViewModel by viewModel<KpiGridSelectorViewModel>()

    override fun getLayout() = R.layout.fragment_kpi_grid_selector

    abstract fun setupGridView(view: Spreadsheet)

    protected open fun notifyOptionSelected(option: String) = Unit

    protected var lastSelectedOption = EMPTY_STRING

    private val labelObserverDataResponse = Observer<KpiGridSelectorViewState> { state ->
        when (state) {
            is KpiGridSelectorViewState.Success -> setupAdvancementText(state.label)
            is KpiGridSelectorViewState.Failed -> Unit
        }
    }

    override fun onSelected(model: Any?) {
        if (model is String) {
            lastSelectedOption = model
            notifyOptionSelected(model)
            AnalyticUtils.sectionAdvance(model)
        }
    }

    protected fun updateSelectorOptions(@KpiType type: Int) {
        val options = GridSelectorBuilder.init(requireContext()).with(type).build()
        selector?.apply {
            visible()
            dataSet = options
            addOnItemSelectorBarListener(this@KpiGridSelectorFragment)
        }
        lastSelectedOption = options.first { it.isSelected }.model as String
        notifyOptionSelected(lastSelectedOption)
    }

    protected fun updateGrid(data: List<Column>) {
        grid?.visible(data.isNotEmpty())
        if (data.isEmpty()) {
            grid?.data?.clear()
            return
        }
        grid?.apply {
            setupGridView(this)
            this.data = data.toMutableList()
        }
    }

    protected fun showNoDataAvailable(llaveUA: LlaveUA) {
        updateGrid(emptyList())
        iEmptyView?.visible()
        val uaName = llaveUA.roleAssociated.nameAsCode()
        tvEmptyData?.text = getString(R.string.text_kpi_data_not_available, uaName)
    }

    private fun setupAdvancementText(label: String) {
        tvSelectorBarLabel?.apply {
            visible(label.isNotBlank())
            text = label
        }
    }

    private fun getAdvancementLabel() {
        kpiGridSelectorViewModel.getAdvancementLabel()
    }

    protected fun setupAdvancementLabel() {
        kpiGridSelectorViewModel.viewState.observe(viewLifecycleOwner, labelObserverDataResponse)
        getAdvancementLabel()
    }

}
