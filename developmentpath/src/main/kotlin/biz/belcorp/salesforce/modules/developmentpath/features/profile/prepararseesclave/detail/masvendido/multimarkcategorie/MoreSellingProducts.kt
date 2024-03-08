package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.masvendido.multimarkcategorie

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import biz.belcorp.salesforce.core.base.BaseToolbarDialogFragment
import biz.belcorp.salesforce.core.utils.gone
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.masvendido.productosmasvendidos.EsikaLbel
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.masvendido.productosmasvendidos.EsikaLbelData
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.masvendido.multimarkcategorie.adapter.ProductEsikaAdapter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.masvendido.multimarkcategorie.adapter.ProductLbelAdapter
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.android.synthetic.main.fragment_more_selling_products.headerToolbar
import kotlinx.android.synthetic.main.fragment_more_selling_products.productsEsika
import kotlinx.android.synthetic.main.fragment_more_selling_products.productsLbel
import kotlinx.android.synthetic.main.fragment_more_selling_products.textTituloEsika
import kotlinx.android.synthetic.main.fragment_more_selling_products.textTituloLbel
import kotlinx.android.synthetic.main.fragment_more_selling_products.viewFlipperEsika
import kotlinx.android.synthetic.main.fragment_more_selling_products.viewFlipperTrio

private const val SHOW_EMPTY = 0
private const val SHOW_DATA = 1

class MoreSellingProducts(val data: EsikaLbelData, val isMX: Boolean) : BaseToolbarDialogFragment() {

    override fun getToolbar(): MaterialToolbar? = headerToolbar as? MaterialToolbar

    override fun getTitle(): String = getString(R.string.sale)

    override fun getMode(): Mode = Mode.RETURNABLE

    override fun getLayout(): Int = R.layout.fragment_more_selling_products

    override fun onStart() {
        super.onStart()
        fitFullScreen()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadLbel()

        if (!isMX) {
            loadEsika()
        } else {
            textTituloLbel.text = getString(R.string.trio_lbel)
            textTituloEsika.gone()
            viewFlipperEsika.gone()
        }

    }

    private fun loadLbel() {
        val lbel: List<EsikaLbel> = if(isMX){
            data.lBel
        }else {
            data.lBelStay
        }
        if (lbel.isEmpty()) {
            viewFlipperTrio.displayedChild = SHOW_EMPTY
        } else {
            viewFlipperTrio.displayedChild = SHOW_DATA
            val adapter = ProductLbelAdapter()
            productsLbel?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            productsLbel?.itemAnimator = DefaultItemAnimator()
            productsLbel?.adapter = adapter

            if (isMX) {
                adapter.actualizar(lbel)
            } else {
                adapter.actualizar(lbel)
            }

        }
    }

    private fun loadEsika() {
        if (data.esika.isEmpty()) {

            viewFlipperEsika.displayedChild = SHOW_EMPTY
        } else {
            viewFlipperEsika.displayedChild = SHOW_DATA
            val adapter = ProductEsikaAdapter()
            productsEsika?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            productsEsika?.itemAnimator = DefaultItemAnimator()
            productsEsika?.adapter = adapter
            adapter.actualizar(data.esika)
        }
    }


}
