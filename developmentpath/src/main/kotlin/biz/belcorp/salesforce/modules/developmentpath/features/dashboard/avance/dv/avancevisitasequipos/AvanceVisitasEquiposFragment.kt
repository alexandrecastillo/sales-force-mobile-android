package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.dv.avancevisitasequipos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.modules.developmentpath.R
import kotlinx.android.synthetic.main.fragment_avance_visitas_equipos.*


class AvanceVisitasEquiposFragment : BaseFragment(), AvanceVisitasMiEquipoView {

    private val presenter by injectFragment<AvanceVisitasEquiposPresenter>()
    override fun getLayout() = R.layout.fragment_avance_visitas_equipos

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_avance_visitas_equipos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.obtener()
    }
    override fun pintarAvanceGr(avanceGr: Int) {
        cpvGr?.setProgreso(avanceGr, true)
    }

    override fun pintarAvanceGz(avanceGz: Int) {
        cpvGz?.setProgreso(avanceGz, true)
    }

    override fun pintarAvanceSe(avanceSe: Int) {
        cpvSe?.setProgreso(avanceSe, true)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
                AvanceVisitasEquiposFragment()
    }
}
