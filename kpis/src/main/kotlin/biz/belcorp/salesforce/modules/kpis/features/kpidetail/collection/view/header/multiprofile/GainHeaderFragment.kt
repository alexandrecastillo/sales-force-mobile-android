package biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.header.multiprofile

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import biz.belcorp.salesforce.components.utils.LineDividerDecoration
import biz.belcorp.salesforce.components.utils.decoration.ItemOffsetDecoration
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.base.FragmentParameters
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.events.EventSubject
import biz.belcorp.salesforce.core.events.LiveDataBus
import biz.belcorp.salesforce.modules.kpis.R
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.adapter.grid.KpiGridAdapter
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.model.GridModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.model.KpiFragmentParameters
import kotlinx.android.synthetic.main.include_collection_header.rvChargedOrders
import kotlinx.android.synthetic.main.include_collection_header.sw31daysCollection
import kotlinx.android.synthetic.main.include_collection_header.tvDateUpdate
import kotlinx.android.synthetic.main.include_collection_header.tvInvoicedTitle
import kotlinx.android.synthetic.main.include_collection_header.tvInvoicedTitleValue
import kotlinx.android.synthetic.main.include_collection_header.tvRecoveredTitle
import kotlinx.android.synthetic.main.include_collection_header.tvRecoveredValue
import kotlinx.android.synthetic.main.include_collection_header.tvRecoveryAdvanceTitle
import kotlinx.android.synthetic.main.include_collection_header.tvRecoveryTitle
import kotlinx.android.synthetic.main.include_collection_header.tvRecoveryValue
import org.koin.android.viewmodel.ext.android.viewModel
import biz.belcorp.salesforce.base.R as BaseR
import biz.belcorp.salesforce.components.R as ComponentR

class GainHeaderFragment : BaseFragment() {

    override fun getLayout() = R.layout.fragment_collection_header
    private val args by lazy { arguments?.getSerializable(FragmentParameters.key) as KpiFragmentParameters }
    private val viewModel by viewModel<GainHeaderViewModel>()

    private val dividerDecoration by lazy {
        LineDividerDecoration(
            requireContext(),
            BaseR.drawable.divider_newcycle,
            ComponentR.dimen.content_inset_less
        )
    }

    private val itemDecoration
        get() = ItemOffsetDecoration(
            resources.getDimensionPixelOffset(ComponentR.dimen.content_inset_small)
        )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModels(args.uaKey)
        setupViews()
    }

    private fun setupViews() {
        sw31daysCollection?.setOnCheckedChangeListener { _, isChecked ->
            publish(isChecked)
        }
    }

    private fun publish(is31Days: Boolean) {
        LiveDataBus.publish(
            EventSubject.COLLECTION_31_DAYS,
            is31Days
        )
    }

    private fun setupViewModels(uaKey: LlaveUA) {
        viewModel.viewState.observe(viewLifecycleOwner, viewStateObserver)
        viewModel.getGainHeaderInformation(uaKey)
    }

    private val viewStateObserver = Observer<GainHeaderViewState> { state ->
        when (state) {
            is GainHeaderViewState.Success -> showData(state.data)
            is GainHeaderViewState.Failed ->
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun showData(model: GainHeaderModel) = with(model) {
        tvRecoveryAdvanceTitle?.text = recoveryAdvanceTitle
        tvRecoveryTitle?.text = recoveryTitle
        tvRecoveryValue?.text = recoveryValue
        tvInvoicedTitle?.text = invoicedTitle
        tvInvoicedTitleValue?.text = invoicedValue
        tvRecoveredTitle?.text = collectedTitle
        tvRecoveredValue?.text = collectedValue
        tvDateUpdate?.text = syncDate
        setupChargedRecycler(chargedOrderList)
    }

    private fun setupChargedRecycler(list: List<GridModel>) {
        val chargedAdapter = KpiGridAdapter()
        rvChargedOrders?.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(
                requireContext(),
                list.size /*Constant.NUMBER_THREE*/,
                GridLayoutManager.VERTICAL,
                false
            )
            addItemDecoration(itemDecoration)
            addItemDecoration(dividerDecoration)
            adapter = chargedAdapter
        }
        chargedAdapter.update(list)
    }

    companion object {
        val TAG = GainHeaderFragment::class.java.simpleName

        fun newInstance() = GainHeaderFragment()
    }
}
