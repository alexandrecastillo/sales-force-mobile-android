package biz.belcorp.salesforce.modules.developmentpath.features.profile.cobranzaestadodecuenta

import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.profile.cobranzaestadodecuenta.cobranza.DatosCobranzaFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.cobranzaestadodecuenta.estadocuenta.DatosEstadoCuentaFragment

class CobranzaYEstadoCuentadialogFragment : BaseDatosDialogFragment() {

    private val cobranzaFragment by lazy {
        DatosCobranzaFragment.newInstance(personaId, rol)
    }

    private val estadoCuentaFragment by lazy {
        DatosEstadoCuentaFragment.newInstance(personaId, rol)
    }

    override fun getLayout(): Int {
        return R.layout.fragment_cobranza_y_estadodecuenta_contenedor
    }

    override fun incrustarFragments() {
        childFragmentManager.beginTransaction()
            .replace(R.id.framelayoutCobranza, cobranzaFragment)
            .replace(R.id.framelayoutEstadoCuenta, estadoCuentaFragment)
            .commit()
    }
}
