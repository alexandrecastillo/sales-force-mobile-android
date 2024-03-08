package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.container

import android.os.Bundle
import android.view.View
import biz.belcorp.salesforce.components.commons.pager.PagesBuilder
import biz.belcorp.salesforce.components.commons.pager.PagesFragmentAdapter
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.features.utils.lazyPersonIdentifier
import biz.belcorp.salesforce.core.features.utils.withPersonIdentifier
import biz.belcorp.salesforce.core.utils.nextPage
import biz.belcorp.salesforce.core.utils.onPageSelected
import biz.belcorp.salesforce.core.utils.previousPage
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.GraphicSEFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.capitalizacion.view.CapitalizationSeFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.ganancia.view.ProfitSeFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.pedidosu6c.ProfileSeOrdersU6CFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.penetracionmarca.container.PenetracionMarcaContenedorFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.retencionactivas.view.ActivesRetentionFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.ventaneta.view.GraphicNetSaleSeFragment
import kotlinx.android.synthetic.main.fragment_graficos_contenedor.*

class ContenedorGraficosFragment : BaseFragment(), OnTitleChangeListener {

    private val personIdentifier by lazyPersonIdentifier()

    private val pagesBuilder by lazy { PagesBuilder() }

    override fun getLayout(): Int = R.layout.fragment_graficos_contenedor

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inicializar()
    }

    override fun onTitleChangeListener(value: String?) {
        textTituloIndicador?.text = value
    }

    private fun inicializar() {
        configurarViewPager()
        escucharAcciones()
        dispatchFragmentUpdateTitle(isChangedForListener = false)
    }

    private fun configurarViewPager() {
        configurarPaginas()
        viewPagerGraficos?.adapter = PagesFragmentAdapter(childFragmentManager, pagesBuilder)
    }

    private fun configurarPaginas() {
        pagesBuilder.add(
            PagesBuilder.Page(
                title = getString(R.string.venta_neta_u6c),
                fragment = GraphicSEFragment.newInstance<GraphicNetSaleSeFragment>(personIdentifier)
            )
        )
        pagesBuilder.add(
            PagesBuilder.Page(
                title = getString(R.string.pedidos_u6c),
                fragment = GraphicSEFragment.newInstance<ProfileSeOrdersU6CFragment>(
                    personIdentifier
                )
            )
        )
        pagesBuilder.add(
            PagesBuilder.Page(
                title = getString(R.string.ganancia_u6c),
                fragment = GraphicSEFragment.newInstance<ProfitSeFragment>(personIdentifier)
            )
        )
        pagesBuilder.add(
            PagesBuilder.Page(
                title = getString(R.string.penetracion_marca),
                fragment = GraphicSEFragment.newInstance<PenetracionMarcaContenedorFragment>(
                    personIdentifier
                )
            )
        )
        pagesBuilder.add(
            PagesBuilder.Page(
                title = getString(R.string.capitalizacion_u6c),
                fragment = GraphicSEFragment.newInstance<CapitalizationSeFragment>(
                    personIdentifier
                )
            )
        )
        pagesBuilder.add(
            PagesBuilder.Page(
                title = getString(R.string.retencionactivas_u6c),
                fragment = GraphicSEFragment.newInstance<ActivesRetentionFragment>(personIdentifier)
            )
        )
    }

    private fun escucharAcciones() {
        imageAnterior?.setOnClickListener {
            viewPagerGraficos?.previousPage()
        }
        imagePosterior?.setOnClickListener {
            viewPagerGraficos?.nextPage()
        }
        viewPagerGraficos?.onPageSelected {
            dispatchFragmentUpdateTitle(isChangedForListener = true)
        }
    }

    private fun dispatchFragmentUpdateTitle(isChangedForListener: Boolean) {
        if (!isChangedForListener) {
            viewPagerGraficos?.post {
                onTitleChangeListener(pagesBuilder.get(viewPagerGraficos.currentItem).title)
            }
            return
        }
        onTitleChangeListener(pagesBuilder.get(viewPagerGraficos.currentItem).title)
    }

    companion object {

        fun newInstance(personIdentifier: PersonIdentifier) = ContenedorGraficosFragment()
            .withPersonIdentifier(personIdentifier)
    }
}
