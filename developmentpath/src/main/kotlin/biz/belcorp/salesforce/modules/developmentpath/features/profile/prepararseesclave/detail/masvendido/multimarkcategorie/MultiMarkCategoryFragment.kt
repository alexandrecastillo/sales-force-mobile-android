package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.masvendido.multimarkcategorie

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.features.utils.lazyPersonIdentifier
import biz.belcorp.salesforce.core.features.utils.withPersonIdentifier
import biz.belcorp.salesforce.core.utils.gone
import biz.belcorp.salesforce.core.utils.visible
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.masvendido.productosmasvendidos.EsikaLbelData
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.salesconsultant.MultiCategories
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.salesconsultant.MultiMark
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.masvendido.multimarkcategorie.adapter.MultiCategoryCampaignAdapter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.masvendido.multimarkcategorie.adapter.MultiMarkCampaignAdapter
import kotlinx.android.synthetic.main.fragment_brand_category.btn_see_products
import kotlinx.android.synthetic.main.fragment_brand_category.buyFragrance
import kotlinx.android.synthetic.main.fragment_brand_category.containerBuyMakUp
import kotlinx.android.synthetic.main.fragment_brand_category.containerSelllBel
import kotlinx.android.synthetic.main.fragment_brand_category.containerSkinCare
import kotlinx.android.synthetic.main.fragment_brand_category.imgNodata
import kotlinx.android.synthetic.main.fragment_brand_category.makeup
import kotlinx.android.synthetic.main.fragment_brand_category.multiMarkCampaign
import kotlinx.android.synthetic.main.fragment_brand_category.multicategory
import kotlinx.android.synthetic.main.fragment_brand_category.multimark
import kotlinx.android.synthetic.main.fragment_brand_category.sellLbel
import kotlinx.android.synthetic.main.fragment_brand_category.skincare
import kotlinx.android.synthetic.main.fragment_brand_category.textTitulo
import kotlinx.android.synthetic.main.fragment_brand_category.textTitulo2
import org.koin.android.viewmodel.ext.android.viewModel

private const val DIALOG_PRODUCTS = "DIALOG_PRODUCTS"

class MultiMarkCategoryFragment : BaseFragment() {

    private val viewModel by viewModel<MultiMarkCategoryViewModel>()

    private val personIdentifier by lazyPersonIdentifier()

    override fun getLayout(): Int = R.layout.fragment_brand_category

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.viewState.observe(viewLifecycleOwner, observerDataResponse)
        viewModel.getMultiMarkCategoriesInfo(personIdentifier.id, personIdentifier.role)

    }

    companion object {
        fun newInstance(personIdentifier: PersonIdentifier) = MultiMarkCategoryFragment()
            .withPersonIdentifier(personIdentifier)
    }

    private val observerDataResponse = Observer<MultiMarkCategoryViewState> { state ->
        when (state) {
            is MultiMarkCategoryViewState.Success -> doOnSuccess(state.data)
            is MultiMarkCategoryViewState.SuccessProducts -> doOnSuccessProducts(state.data)
            is MultiMarkCategoryViewState.Failed -> Unit
        }
    }

    private fun doOnSuccess(data: Pair<List<MultiMark>, List<MultiCategories>>) = with(data) {
        if (!isAttached()) return

        viewModel.getProductsInfo(personIdentifier.id, personIdentifier.role)

        if (first.isEmpty() || second.isEmpty()) {
            showEmpty()
        } else {
            showData()
            loadMultiMark(first)
            loadlBel(first)
            buyFragrances(second)
            buySkinCare(second)
            buyMakUp(second)
        }
    }

    private fun doOnSuccessProducts(data: EsikaLbelData) = with(data) {
        if (!isAttached()) return


        val isMx = viewModel.isMx()
        if (isMx) btn_see_products.text = getString(R.string.see_detail_product)

        btn_see_products.setOnClickListener {
            MoreSellingProducts(data, isMx).show(childFragmentManager, DIALOG_PRODUCTS)

        }
    }

    private fun showEmpty() {
        imgNodata.visible()
        textTitulo.gone()
        multimark.gone()
        containerSelllBel.gone()
        textTitulo2.gone()
        multicategory.gone()
        containerSkinCare.gone()
        containerBuyMakUp.gone()
    }

    private fun showData() {
        imgNodata.gone()
        textTitulo.visible()
        multimark.visible()
        containerSelllBel.visible()
        textTitulo2.visible()
        multicategory.visible()
        containerSkinCare.visible()
        containerBuyMakUp.visible()
    }


    private fun loadMultiMark(items: List<MultiMark>) {
        val adapter = MultiMarkCampaignAdapter()
        multiMarkCampaign?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        multiMarkCampaign?.itemAnimator = DefaultItemAnimator()
        multiMarkCampaign?.adapter = adapter
        adapter.actualizar(items[0].histories.reversed())
    }

    private fun loadlBel(items: List<MultiMark>) {
        val adapter = MultiMarkCampaignAdapter()
        sellLbel?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        sellLbel?.itemAnimator = DefaultItemAnimator()
        sellLbel?.adapter = adapter
        adapter.actualizar(items[1].histories.reversed())
    }


    private fun buyFragrances(items: List<MultiCategories>) {
        val adapter = MultiCategoryCampaignAdapter()
        buyFragrance?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        buyFragrance?.itemAnimator = DefaultItemAnimator()
        buyFragrance?.adapter = adapter
        adapter.actualizar(items[0].histories.reversed())
    }

    private fun buySkinCare(items: List<MultiCategories>) {
        val adapter = MultiCategoryCampaignAdapter()
        skincare?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        skincare?.itemAnimator = DefaultItemAnimator()
        skincare?.adapter = adapter
        adapter.actualizar(items[1].histories.reversed())
    }

    private fun buyMakUp(items: List<MultiCategories>) {
        val adapter = MultiCategoryCampaignAdapter()
        makeup?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        makeup?.itemAnimator = DefaultItemAnimator()
        makeup?.adapter = adapter
        adapter.actualizar(items[2].histories.reversed())
    }


}
