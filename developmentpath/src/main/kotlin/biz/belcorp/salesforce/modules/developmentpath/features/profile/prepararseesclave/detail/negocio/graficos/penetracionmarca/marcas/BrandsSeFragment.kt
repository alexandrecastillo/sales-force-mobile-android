package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.penetracionmarca.marcas

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import biz.belcorp.mobile.components.charts.pie.PieItem
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.features.utils.lazyPersonIdentifier
import biz.belcorp.salesforce.core.features.utils.withPersonIdentifier
import biz.belcorp.salesforce.core.utils.gone
import biz.belcorp.salesforce.core.utils.textColor
import biz.belcorp.salesforce.core.utils.visible
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.masvendido.marcasycategorias.BrandModel
import kotlinx.android.synthetic.main.fragment_marcas_y_categorias.*
import org.koin.android.viewmodel.ext.android.viewModel
import biz.belcorp.salesforce.base.R as BaseR

class BrandsSeFragment : BaseFragment() {

    private val viewModel by viewModel<BrandsViewModel>()

    override fun getLayout(): Int = R.layout.fragment_marcas_y_categorias

    private val personIdentifier by lazyPersonIdentifier()

    private val observerDataResponse = Observer<BrandsViewState> { state ->
        when (state) {
            is BrandsViewState.Success -> doOnSuccess(state.data)
            is BrandsViewState.Failure -> doOnFailure()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun doOnSuccess(brands: List<BrandModel>) {
        if (!isAttached()) return
        drawPieChart(brands)
    }

    private fun doOnFailure() {
        showWithoutBrandsView()
    }

    private fun init() {
        textTitulo?.text = getString(R.string.marcas_u6c)
        textTitulo?.textColor = ContextCompat.getColor(requireContext(), BaseR.color.black)
        viewModel.viewState.observe(viewLifecycleOwner, observerDataResponse)
        viewModel.getBrands(personIdentifier)
    }

    private fun drawPieChart(brands: List<BrandModel>) {
        if (!handleVisibility(brands)) return
        val items: MutableList<PieItem> = mutableListOf()
        brands.forEach {
            items.add(PieItem(it.color, it.name, it.average.toInt()))
        }
        //pieChartLegend?.refreshData(items)
    }

    private fun handleVisibility(brands: List<BrandModel>): Boolean {
        if (brands.isNullOrEmpty()) {
            showWithoutBrandsView()
            return false
        }
        //pieChartLegend?.visible()
        txtSinMarcas?.gone()
        return true
    }

    private fun showWithoutBrandsView() {
       // pieChartLegend?.gone()
        txtSinMarcas?.visible()
    }

    companion object {
        fun newInstance(personIdentifier: PersonIdentifier) = BrandsSeFragment()
            .withPersonIdentifier(personIdentifier)
    }
}
