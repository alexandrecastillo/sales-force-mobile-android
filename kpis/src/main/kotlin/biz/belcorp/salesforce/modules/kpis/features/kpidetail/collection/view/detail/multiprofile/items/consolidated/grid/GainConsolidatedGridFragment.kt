package biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.detail.multiprofile.items.consolidated.grid

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import biz.belcorp.mobile.components.design.spreadsheet.Spreadsheet
import biz.belcorp.salesforce.core.base.FragmentParameters
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.events.ConsumableEvent
import biz.belcorp.salesforce.core.events.EventSubject
import biz.belcorp.salesforce.core.events.utils.consumableObserver
import biz.belcorp.salesforce.core.events.utils.observeEventSubject
import biz.belcorp.salesforce.core.utils.getCompatColor
import biz.belcorp.salesforce.core.utils.toast
import biz.belcorp.salesforce.core.utils.withArguments
import biz.belcorp.salesforce.modules.kpis.R
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.KpiQueryParams
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.detail.multiprofile.items.uadetail.UaDetailViewState
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.KpiGridSelectorFragment
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.model.KpiFragmentParameters
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.viewmodel.UaDetailViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class GainConsolidatedGridFragment : KpiGridSelectorFragment() {

    private val params by lazy {
        arguments?.getSerializable(FragmentParameters.key) as KpiFragmentParameters
    }

    private lateinit var sessionUaKey : LlaveUA
    private val viewModel by viewModel<GainConsolidatedGridViewModel>()
    private val uaViewModel by sharedViewModel<UaDetailViewModel>()

    private val observerDataResponse = Observer<ConsumableEvent> {
        it.runAndConsume {
            handleViewState((it.value as GainConsolidatedGridViewState))
        }
    }

    private val viewUaStateObserver = Observer<UaDetailViewState> { state ->
        when (state) {
            is UaDetailViewState.UpdateItem -> updateUa(state.uaKey)
            is UaDetailViewState.Failure -> toast(state.message)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModels()
        setupLiveObservers()
    }

    private fun setupLiveObservers() {
        observeEventSubject(EventSubject.COLLECTION_31_DAYS, observer = filterObserver)
    }

    private fun setupViewModels() {
        viewModel.viewState.observe(viewLifecycleOwner, observerDataResponse)
        if (!params.isParent) {
            uaViewModel.uaViewState.observe(viewLifecycleOwner, viewUaStateObserver)
        }
        sessionUaKey = params.uaKey
        updateDetail(params.uaKey)
    }

    override fun setupGridView(view: Spreadsheet) {
        view.mainColor = getCompatColor(R.color.colorGreenLight)
        view.headerTextAppearance = R.style.TextAppearance_Digital_Grid_Header_Collection
    }

    private fun updateUa(uaKey: LlaveUA) {
        params.uaKey = uaKey
        updateDetail(uaKey)
    }

    private fun updateDetail(uaKey: LlaveUA) {
        viewModel.getGridData(uaKey, KpiQueryParams.COLLECTION_DAYS_21)
    }

    private val filterObserver = consumableObserver<Boolean> {
        val days = if (it) KpiQueryParams.COLLECTION_DAYS_31 else KpiQueryParams.COLLECTION_DAYS_21
        viewModel.getGridData(sessionUaKey, days)
    }


    private fun handleViewState(state: GainConsolidatedGridViewState) {
        when (state) {
            is GainConsolidatedGridViewState.Success -> updateGrid(state.columns)
        }
    }

    companion object {
        val TAG = GainConsolidatedGridFragment::class.java.simpleName

        fun newInstance(params: KpiFragmentParameters) =
            GainConsolidatedGridFragment().withArguments(
                FragmentParameters.key to params
            )
    }
}
