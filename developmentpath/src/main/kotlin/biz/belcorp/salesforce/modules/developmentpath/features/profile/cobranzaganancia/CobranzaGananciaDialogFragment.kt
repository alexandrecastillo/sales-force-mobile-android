package biz.belcorp.salesforce.modules.developmentpath.features.profile.cobranzaganancia

import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.profile.cobranzaestadodecuenta.BaseDatosDialogFragment

class CobranzaGananciaDialogFragment : BaseDatosDialogFragment() {

    private val cobranzaFragment by lazy {
        CobranzaGananciaFragment.newInstance(personaId, rol)
    }

    override fun getLayout(): Int = R.layout.fragment_cobranza_y_ganancia_contenedor

    override fun incrustarFragments() {
        childFragmentManager.beginTransaction()
            .replace(R.id.framelayoutCobranzaGanancia, cobranzaFragment)
            .commit()
    }
}
