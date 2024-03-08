package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.focos.se.view

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.focos.se.model.FocoModel
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.focos.se.presenter.FocosSEPresenter
import kotlinx.android.synthetic.main.fragment_rdd_focos_se.*

class FocosSEFragment : BaseFragment(), FocosSEView {

    override fun getLayout() = R.layout.fragment_rdd_focos_se

    private val presenter: FocosSEPresenter by injectFragment()

    companion object {

        fun newInstance(): FocosSEFragment {
            return FocosSEFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inicializar()
        configurarRecycler()
    }

    private fun configurarRecycler() {
        val linearLayoutManager = LinearLayoutManager(context)
        recycler_mis_focos?.layoutManager = linearLayoutManager
        recycler_mis_focos?.adapter = FocosSEAdapter()
        agregarDivisores(linearLayoutManager.orientation)
    }

    private fun agregarDivisores(orientacion: Int) {

        recycler_mis_focos.addItemDecoration(
            object : DividerItemDecoration(context, orientacion) {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    val position = parent.getChildAdapterPosition(view)
                    if (position == parent.adapter?.itemCount ?: 0 - 1)
                        outRect.setEmpty()
                    else
                        super.getItemOffsets(outRect, view, parent, state)
                }
            })
    }

    private fun inicializar() {
        presenter.inicializar()
    }

    override fun pintarFocos(focos: List<FocoModel>) {
        ocultarPlaceHolder()
        (recycler_mis_focos?.adapter as? FocosSEAdapter)?.actualizar(focos)
    }

    private fun ocultarPlaceHolder() {
        card_sin_focos?.visibility = View.INVISIBLE
        recycler_mis_focos?.visibility = View.VISIBLE
    }

    override fun mostrarPlaceHolder() {
        card_sin_focos?.visibility = View.VISIBLE
        recycler_mis_focos?.visibility = View.INVISIBLE
    }
}
