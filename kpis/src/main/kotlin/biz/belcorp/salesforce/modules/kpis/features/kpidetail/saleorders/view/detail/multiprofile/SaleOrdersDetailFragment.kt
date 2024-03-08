package biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.view.detail.multiprofile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.base.FragmentParameters
import biz.belcorp.salesforce.core.utils.toast
import biz.belcorp.salesforce.modules.kpis.R
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.view.detail.multiprofile.items.consolidated.grid.SaleOrderGridSelectorFragment
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.view.detail.multiprofile.items.uadetail.SaleOrdersUaDetailFragment
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.adapter.KpiDetailPagerAdapter
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.model.KpiFragmentParameters
import biz.belcorp.salesforce.modules.kpis.utils.AnalyticUtils
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_sale_orders_detail.*
import org.koin.android.viewmodel.ext.android.viewModel

class SaleOrdersDetailFragment : BaseFragment(), TabLayout.OnTabSelectedListener {

    private val pagerAdapter by lazy {
        KpiDetailPagerAdapter(requireContext(), args.rol, childFragmentManager)
    }
    private val args get() = arguments?.getSerializable(FragmentParameters.key) as KpiFragmentParameters
    private val viewModel by viewModel<SaleOrdersDetailViewModel>()

    override fun getLayout() = R.layout.fragment_sale_orders_detail

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    private fun setup() {
        setupViewModels()
        setupTabLayout()
        configureViewPager()
        configurePages()
    }

    private fun setupViewModels() {
        viewModel.viewState.observe(viewLifecycleOwner, viewStateObserver)
        viewModel.getInfo()
    }

    override fun onTabSelected(tab: TabLayout.Tab) {
        vpSaleOrders?.currentItem = tab.position
        AnalyticUtils.tabSelection(this.tag.toString(), tab.text.toString())
    }

    override fun onTabReselected(tab: TabLayout.Tab) = Unit

    override fun onTabUnselected(tab: TabLayout.Tab) = Unit

    private fun setupTabLayout() {
        tlSaleOrders?.apply {
            setupWithViewPager(vpSaleOrders)
            addOnTabSelectedListener(this@SaleOrdersDetailFragment)
        }
    }

    private fun configureViewPager() {
        vpSaleOrders?.apply {
            offscreenPageLimit = PAGER_OFFSET
            this.adapter = pagerAdapter
        }
    }


    private fun configurePages() {
        val fragments: List<Fragment> = listOf(
            SaleOrderGridSelectorFragment.newInstance(args),
            SaleOrdersUaDetailFragment.newInstance(args)
        )
        pagerAdapter.update(fragments)
    }

    private val viewStateObserver = Observer<SaleOrdersDetailViewState> { state ->
        when (state) {
            is SaleOrdersDetailViewState.Success -> tvTitle?.text = state.title
            is SaleOrdersDetailViewState.Failed -> toast(state.message)
        }
    }

    companion object {

        private const val PAGER_OFFSET = 2
        val TAG = SaleOrdersDetailFragment::class.java.simpleName

        fun newInstance() = SaleOrdersDetailFragment()
    }
}
