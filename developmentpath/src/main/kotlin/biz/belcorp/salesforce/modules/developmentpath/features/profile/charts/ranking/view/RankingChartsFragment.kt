package biz.belcorp.salesforce.modules.developmentpath.features.profile.charts.ranking.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import biz.belcorp.mobile.components.core.extensions.gone
import biz.belcorp.salesforce.components.commons.pager.PagesBuilder
import biz.belcorp.salesforce.components.commons.pager.PagesFragmentAdapter
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.features.utils.lazyPersonIdentifier
import biz.belcorp.salesforce.core.features.utils.withPersonIdentifier
import biz.belcorp.salesforce.core.utils.nextPage
import biz.belcorp.salesforce.core.utils.previousPage
import biz.belcorp.salesforce.core.utils.requireGrandParentFragment
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.profile.ProfileViewModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.ProfileViewState
import biz.belcorp.salesforce.modules.developmentpath.features.profile.charts.ranking.common.BaseGraphicFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.charts.ranking.view.pages.*
import biz.belcorp.salesforce.modules.developmentpath.utils.cambiarVisibilidad
import kotlinx.android.synthetic.main.fragment_contenedor_graficos.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class RankingChartsFragment : BaseFragment() {

    private val viewModel by sharedViewModel<RankingChartsViewModel>(from = { this })

    private val profileViewModel by sharedViewModel<ProfileViewModel>(from = { requireGrandParentFragment() })

    private val pagesBuilder by lazy { PagesBuilder() }

    private val personIdentifier by lazyPersonIdentifier()

    override fun getLayout() = R.layout.fragment_contenedor_graficos

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupEvents()
        setupAdapter()
        initialize()
        setupViewModel()
    }

    private fun setupViewModel() {
        profileViewModel.viewState.observe(viewLifecycleOwner, viewStateObserver)
    }

    private val viewStateObserver = Observer<ProfileViewState> {
        when (it) {
            is ProfileViewState.SuccessSync -> initialize()
        }
    }

    private fun initialize() {
        viewModel.getRankingInfo(personIdentifier)
    }

    private fun setupEvents() {
        crdHeader?.setOnClickListener {
            crdBody?.cambiarVisibilidad()
        }
    }

    private fun setupAdapter() {
        setupPages()
        val pagerAdapter = PagesFragmentAdapter(childFragmentManager, pagesBuilder)
        viewPagerContenedorGraficos?.adapter = pagerAdapter
        crdBody?.gone()
    }

    private fun setupPages() {
        val pages = listOf(
            PagesBuilder.Page(
                title = getString(R.string.ranking_sales_net),
                fragment = BaseGraphicFragment.newInstance<VentaNetaMNFragment>(personIdentifier)
            ),
            PagesBuilder.Page(
                title = getString(R.string.ranking_sales_net),
                fragment = BaseGraphicFragment.newInstance<MetaRetActivasFragment>(personIdentifier)
            ),
            PagesBuilder.Page(
                title = getString(R.string.ranking_sales_net),
                fragment = BaseGraphicFragment.newInstance<ProductivasFragment>(personIdentifier)
            ),
            PagesBuilder.Page(
                title = getString(R.string.ranking_sales_net),
                fragment = BaseGraphicFragment.newInstance<IngresosIpUnicoFragment>(personIdentifier)
            ),
            PagesBuilder.Page(
                title = getString(R.string.ranking_sales_net),
                fragment = BaseGraphicFragment.newInstance<Ret6d6Fragment>(personIdentifier)
            )
        )
        pagesBuilder.addAll(pages)
    }

    fun nextPage() {
        viewPagerContenedorGraficos?.nextPage()
    }

    fun previousPage() {
        viewPagerContenedorGraficos?.previousPage()
    }

    companion object {

        fun newInstance(personIdentifier: PersonIdentifier): RankingChartsFragment {
            return RankingChartsFragment()
                .withPersonIdentifier(personIdentifier)
        }
    }
}
