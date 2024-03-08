package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.penetracionmarca.container

import android.os.Bundle
import android.view.View
import biz.belcorp.salesforce.core.utils.fadeAnimation
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.masvendido.productosmasvendidos.TopSoldProductsFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.GraphicSEFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.penetracionmarca.marcas.BrandsSeFragment
import kotlinx.android.synthetic.main.fragment_mas_vendido_contenedor.*

class PenetracionMarcaContenedorFragment : GraphicSEFragment() {

    private val marcasCategoriaFragment by lazy {
        BrandsSeFragment.newInstance(personIdentifier)
    }

    private val productosMasVendidos by lazy {
        TopSoldProductsFragment.newInstance(personIdentifier)
    }

    override fun getLayout(): Int = R.layout.fragment_mas_vendido_contenedor

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        incrustarFragments()
        clContenedorMasVendido.fadeAnimation()
    }

    private fun incrustarFragments() {
        if (!isAdded) return
        childFragmentManager
            .beginTransaction()
            .replace(R.id.framelayoutMarcasDetalle, marcasCategoriaFragment)
            .replace(R.id.framelayoutProductosMasVendidos, productosMasVendidos)
            .commit()
    }
}
