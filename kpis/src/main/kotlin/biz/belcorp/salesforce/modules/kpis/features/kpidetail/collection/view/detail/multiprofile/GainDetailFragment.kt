package biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.detail.multiprofile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.base.FragmentParameters
import biz.belcorp.salesforce.core.utils.toast
import biz.belcorp.salesforce.modules.kpis.R
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.detail.multiprofile.items.consolidated.grid.GainConsolidatedGridFragment
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.detail.multiprofile.items.uadetail.GainUaDetailFragment
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.adapter.KpiDetailPagerAdapter
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.model.KpiFragmentParameters
import biz.belcorp.salesforce.modules.kpis.utils.AnalyticUtils
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_collection_detail.*
import org.koin.android.viewmodel.ext.android.viewModel

class GainDetailFragment : BaseFragment(), TabLayout.OnTabSelectedListener {

    private val adapter by lazy {
        KpiDetailPagerAdapter(requireContext(), args.rol, childFragmentManager)
    }

    private val args by lazy { arguments?.getSerializable(FragmentParameters.key) as KpiFragmentParameters }

    private val viewModel by viewModel<CollectionDetailViewModel>()

    override fun getLayout() = R.layout.fragment_collection_detail

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModels()
        setupView()
    }

    private fun setupView() {
        setupTabLayout()
        configureViewPager()
        configurePages()
    }

    private fun setupViewModels() {
        viewModel.viewState.observe(viewLifecycleOwner, observerDataResponse)
        viewModel.getGainInformation(args.uaKey)
    }

    private val observerDataResponse = Observer<GainDetailViewState> { state ->
        when (state) {
            is GainDetailViewState.Success -> configureTitle(state.title)
            is GainDetailViewState.Failed -> toast(state.message)
        }
    }

    override fun onTabSelected(tab: TabLayout.Tab) {
        vpCollection?.currentItem = tab.position
        AnalyticUtils.tabSelection(this.tag.toString(), tab.text.toString())
    }

    override fun onTabReselected(tab: TabLayout.Tab) = Unit

    override fun onTabUnselected(tab: TabLayout.Tab) = Unit

    private fun configureTitle(title: String) {
        tvSection?.text = title
    }

    private fun setupTabLayout() {
        tlCollection?.setupWithViewPager(vpCollection)
        tlCollection?.addOnTabSelectedListener(this)
    }

    private fun configureViewPager() {
        vpCollection?.offscreenPageLimit = PAGER_OFFSET
        vpCollection?.adapter = adapter
    }

    private fun configurePages() {
        val fragments: List<Fragment> = listOf(
            GainConsolidatedGridFragment.newInstance(args.apply { isParent = true }),
            GainUaDetailFragment.newInstance(args)
        )
        adapter.update(fragments)
    }

    companion object {

        private const val PAGER_OFFSET = 2
        val TAG = GainDetailFragment::class.java.simpleName

        fun newInstance() = GainDetailFragment()
    }
}
