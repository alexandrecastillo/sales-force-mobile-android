package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.focos

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.fragment.app.Fragment
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.focos.gr.FocosHabilidadesDeEquipoFragment
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.focos.gz.view.FocosZonaFragment
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.focos.propios.view.MisFocosFragment
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.habilidades.propios.view.MisHabilidadesFragment

class FocosContenedorFragment : BaseFragment(), FocosContenedorView {

    private val presenter: FocosContenedorPresenter by injectFragment()

    override fun getLayout() = R.layout.fragment_focos_contenedor

    private val focosHabilidadesDeEquipoFragment: FocosHabilidadesDeEquipoFragment by lazy {
        FocosHabilidadesDeEquipoFragment()
    }

    private val misFocosFragment: MisFocosFragment by lazy {
        MisFocosFragment.newInstance()
    }

    private val focosDeZonaFragment: FocosZonaFragment by lazy {
        FocosZonaFragment()
    }

    private val misHabilidadesFragment: MisHabilidadesFragment by lazy {
        MisHabilidadesFragment.newInstance()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler().postDelayed({ agregarFocos() }, 400)
    }

    private fun agregarFocos() {
        presenter.decidirFocosSegunRol()
    }

    override fun instanciarMisFocos() {
        if (!isResumed) return

        childFragmentManager
            .beginTransaction()
            .replace(R.id.flMisFocos, misFocosFragment)
            .commit()
    }

    override fun instanciarMisHabilidades() {
        if (!isResumed) return

        childFragmentManager
            .beginTransaction()
            .replace(R.id.flMisHabilidades, misHabilidadesFragment)
            .commit()
    }

    override fun instanciarFocosEquipoGz() = instanciarFocosEquipo(focosDeZonaFragment)

    override fun instanciarFocosEquipoGr() = instanciarFocosEquipo(focosHabilidadesDeEquipoFragment)

    override fun instanciarFocosEquipoDv() {
        instanciarFocosEquipo(focosHabilidadesDeEquipoFragment)
    }

    private fun instanciarFocosEquipo(fragment: Fragment) {
        if (!isResumed) return

        childFragmentManager
            .beginTransaction()
            .replace(R.id.flFocosDeEquipo, fragment)
            .commit()
    }
}
