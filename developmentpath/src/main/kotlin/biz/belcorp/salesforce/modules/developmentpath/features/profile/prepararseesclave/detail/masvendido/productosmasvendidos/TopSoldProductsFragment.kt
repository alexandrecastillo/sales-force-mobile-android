package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.masvendido.productosmasvendidos

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import biz.belcorp.mobile.components.core.extensions.gone
import biz.belcorp.mobile.components.core.extensions.visible
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.features.utils.lazyPersonIdentifier
import biz.belcorp.salesforce.core.features.utils.withPersonIdentifier
import biz.belcorp.salesforce.modules.developmentpath.R
import kotlinx.android.synthetic.main.fragment_productos_mas_vendidos_new.*
import org.koin.android.viewmodel.ext.android.viewModel

class TopSoldProductsFragment : BaseFragment() {

    private val viewModel by viewModel<TopSoldProductsViewModel>()

    private val adapter: TopSoldProductsAdapter by lazy { TopSoldProductsAdapter() }

    private val personIdentifier by lazyPersonIdentifier()

    override fun getLayout(): Int = R.layout.fragment_productos_mas_vendidos_new

    private val observerDataResponse = Observer<TopSoldProductsViewState> { state ->
        when (state) {
            is TopSoldProductsViewState.Success -> doOnSuccess(state.data)
            is TopSoldProductsViewState.Failed -> doOnFailed()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun doOnSuccess(data: List<TopSoldProductCampaignModel>) {
        hideNonProductsContainer()
        showProductsContainer()
        showTopSoldProductsContainer(data)
    }

    private fun showTopSoldProductsContainer(productosMasVendidos: List<TopSoldProductCampaignModel>) {
        adapter.update(productosMasVendidos)
    }

    private fun showProductsContainer() {
        rvProductosMasVendidos?.visible()
    }

    private fun hideProductsContainer() {
        rvProductosMasVendidos?.gone()
    }

    private fun showNonProductsFound() {
        txtSinProductosMasVendidos?.visible()
    }

    private fun doOnFailed() {
        showNonProductsFound()
        hideProductsContainer()
    }

    private fun hideNonProductsContainer() {
        txtSinProductosMasVendidos?.visibility = View.GONE
    }

    private fun init() {
        setupRecycler()
        initViewModel()
    }

    private fun setupRecycler() {
        rvProductosMasVendidos?.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@TopSoldProductsFragment.adapter
        }
    }

    private fun initViewModel() {
        viewModel.viewState.observe(viewLifecycleOwner, observerDataResponse)
        viewModel.getMostSoldProductsInfo(personIdentifier)
    }

    companion object {
        fun newInstance(personIdentifier: PersonIdentifier) = TopSoldProductsFragment()
            .withPersonIdentifier(personIdentifier)
    }
}
