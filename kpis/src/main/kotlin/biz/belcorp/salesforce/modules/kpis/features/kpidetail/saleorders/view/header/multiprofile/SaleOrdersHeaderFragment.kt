package biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.view.header.multiprofile

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import biz.belcorp.mobile.components.core.extensions.visible
import biz.belcorp.salesforce.components.utils.decoration.BoxOffsetDecoration
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.base.FragmentParameters
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.modules.kpis.R
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.saleorders.SaleOrdersIndicator
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.adapter.detail.SaleOrdersContentAdapter
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.model.SaleOrdersHeaderModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.model.KpiFragmentParameters
import biz.belcorp.salesforce.modules.kpis.utils.StringUtils.checkNumberDivisionByZero
import biz.belcorp.salesforce.modules.kpis.utils.StringUtils.checkStringToOperateWithNumbersDouble
import kotlinx.android.synthetic.main.fragment_sale_orders_header.*
import org.koin.android.viewmodel.ext.android.viewModel
import biz.belcorp.salesforce.components.R as ComponentsR

class SaleOrdersHeaderFragment : BaseFragment() {

    private val adapterGoal by lazy { SaleOrdersContentAdapter() }
    private val adapterAchievement by lazy { SaleOrdersContentAdapter() }
    private val viewModel by viewModel<SaleOrdersHeaderViewModel>()
    private val args by lazy {
        arguments?.getSerializable(FragmentParameters.key) as KpiFragmentParameters
    }


    override fun getLayout() = R.layout.fragment_sale_orders_header

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    private fun setup() {
        setupRecyclerGoal()
        setupRecyclerAchievement()
        setupViewModels()
    }

    private fun setupViewModels() {
        viewModel.viewState.observe(viewLifecycleOwner, observerDataResponse)
        viewModel.multiBrandState.observe(viewLifecycleOwner, observerMultiBrandDataResponse)
        viewModel.getSaleHeaderOrders()
    }

    private val observerDataResponse = Observer<SaleOrdersHeaderViewState> { state ->
        when (state) {
            is SaleOrdersHeaderViewState.Success -> {
                showTitles(state.data)
                showData(state.data)
            }
            is SaleOrdersHeaderViewState.Failed -> Unit
        }
    }

    private val observerMultiBrandDataResponse = Observer<MultiBrandViewState> { state ->
        when (state) {
            is MultiBrandViewState.Success -> {
                state.data?.model?.let { m ->
                    showMultiBrandData(m)
                }
            }
        }
    }


    private fun showMultiBrandData(data: SaleOrdersIndicator) {
            layoutMultibrand.visibility = View.VISIBLE
            tvMultimarkIndicator.text = context?.getString(R.string.operation_division, data.multibrandActives, data.multibrandPercentage)
            tvNoMultimarkActives.text = data.multibrandNoMultibrandActives
            tvMultimarcaVsLastCampaign.text = data.multibrandNumVsLastCampaign

            val tvMultimarkAVlastCampaignVsValueDenominator =
                if(data.multibrandActivesHighValue.isNullOrEmpty()) Constant.NUMBER_ONE else
                checkStringToOperateWithNumbersDouble(data.multibrandActivesHighValue)  /  checkNumberDivisionByZero(data.activesFinals)

            tvMultimarkAVlastCampaignVs.text =  if(data.multibrandActivesHighValue.isNullOrEmpty())
                context?.getString(R.string.operation_division, Constant.STRING_ZERO, tvMultimarkAVlastCampaignVsValueDenominator.toString()) + "%"

            else
                context?.getString(R.string.operation_division, data.multibrandActivesHighValue, tvMultimarkAVlastCampaignVsValueDenominator.toString()) + "%"

            tvMultimarcaActivasAVvsLastCampaign.text = data.multibrandHighValueNumVsLastCampaign
    }

    private fun showTitles(data: SaleOrdersHeaderModel) {
        tvGoals?.apply {
            visible(data.titleGoals.isNotEmpty())
            text = data.titleGoals
        }

        tvAchievements?.apply {
            visible(data.titleAchievements.isNotEmpty())
            text = data.titleAchievements
        }
    }

    private fun showData(data: SaleOrdersHeaderModel) {
        rvGoals?.visible(data.hasGoals)
        rvAchievements?.visible(data.hasAchievements)
        adapterGoal.addData(data.goals)
        adapterAchievement.addData(data.achievements)
    }

    private fun setupRecyclerGoal() {
        rvGoals?.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                BoxOffsetDecoration(
                    requireContext(),
                    ComponentsR.dimen.content_inset_normal,
                    ComponentsR.dimen.content_inset_normal
                )
            )
            adapter = adapterGoal
        }
    }

    private fun setupRecyclerAchievement() {
        rvAchievements?.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                BoxOffsetDecoration(
                    requireContext(),
                    ComponentsR.dimen.content_inset_normal,
                    ComponentsR.dimen.content_inset_normal
                )
            )
            adapter = adapterAchievement
        }
    }

    companion object {
        val TAG = SaleOrdersHeaderFragment::class.java.simpleName

        fun newInstance() = SaleOrdersHeaderFragment()

    }
}
