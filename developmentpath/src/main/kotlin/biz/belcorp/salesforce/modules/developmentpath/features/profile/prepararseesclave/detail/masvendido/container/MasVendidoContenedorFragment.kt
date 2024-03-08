package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.masvendido.container

import android.os.Bundle
import android.view.View
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalytics
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.features.utils.lazyPersonIdentifier
import biz.belcorp.salesforce.core.features.utils.withPersonIdentifier
import biz.belcorp.salesforce.core.utils.fadeAnimation
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.analytics.FirebaseAnalyticsPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.masvendido.marcasycategorias.BrandsAndCategoriesFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.masvendido.multimarkcategorie.MultiMarkCategoryFragment
import kotlinx.android.synthetic.main.fragment_mas_vendido_contenedor.clContenedorMasVendido

class MasVendidoContenedorFragment : BaseFragment() {

    private val analyticsPresenter by injectFragment<FirebaseAnalyticsPresenter>()

    private val personIdentifier by lazyPersonIdentifier()

    private val marcasCategoriaFragment by lazy {
        BrandsAndCategoriesFragment.newInstance(personIdentifier)
    }

    private val productosMasVendidos by lazy {
        MultiMarkCategoryFragment.newInstance(personIdentifier)
    }

    override fun getLayout(): Int = R.layout.fragment_mas_vendido_contenedor


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inicializar()
    }

    private fun inicializar() {
        incrustarFragments()
        clContenedorMasVendido?.fadeAnimation()
        inicializarPresenters()
    }

    private fun incrustarFragments() {
        if (!isAttached()) return
        childFragmentManager
            .beginTransaction()
            .replace(R.id.framelayoutMarcasDetalle, marcasCategoriaFragment)
            .replace(R.id.framelayoutProductosMasVendidos, productosMasVendidos)
            .commit()
    }

    private fun inicializarPresenters() {
        analyticsPresenter.enviarPantallaMasVendido(
            TagAnalytics.PREPARARSE_ES_CLAVE_MAS_VENDIDO,
            personIdentifier.role,
            personIdentifier.id
        )
    }

    companion object {
        fun newInstance(personIdentifier: PersonIdentifier) = MasVendidoContenedorFragment()
            .withPersonIdentifier(personIdentifier)
    }
}
