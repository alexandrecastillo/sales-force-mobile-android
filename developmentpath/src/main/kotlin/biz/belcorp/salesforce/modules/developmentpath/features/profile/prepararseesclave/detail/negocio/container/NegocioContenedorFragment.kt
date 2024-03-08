package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.container

import android.os.Bundle
import android.view.View
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.features.utils.lazyPersonIdentifier
import biz.belcorp.salesforce.core.features.utils.withPersonIdentifier
import biz.belcorp.salesforce.core.utils.fadeAnimation
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.container.ContenedorGraficosFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.performance.view.PerformanceSeFragment
import kotlinx.android.synthetic.main.fragment_negocio_contenedor.*

class NegocioContenedorFragment : BaseFragment() {

    private val personIdentifier by lazyPersonIdentifier()

    private val desempenioFragment by lazy {
        PerformanceSeFragment.newInstance(personIdentifier)
    }

    private val graficosFragment by lazy {
        ContenedorGraficosFragment.newInstance(personIdentifier)
    }

    override fun getLayout(): Int = R.layout.fragment_negocio_contenedor

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        incrustarFragments()
        clContenedorNegocio.fadeAnimation()
    }

    private fun incrustarFragments() {
        if (!isAdded) return
        childFragmentManager
            .beginTransaction()
            .replace(R.id.framelayoutDesempenio, desempenioFragment)
            .replace(R.id.framelayoutDatosDeLaSeccion, graficosFragment)
            .commit()
    }

    companion object {
        fun newInstance(personIdentifier: PersonIdentifier) = NegocioContenedorFragment()
            .withPersonIdentifier(personIdentifier)
    }
}
