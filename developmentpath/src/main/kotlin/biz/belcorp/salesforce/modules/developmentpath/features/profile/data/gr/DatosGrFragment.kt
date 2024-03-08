package biz.belcorp.salesforce.modules.developmentpath.features.profile.data.gr

import android.os.Bundle
import android.view.View
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.features.utils.lazyPersonIdentifier
import biz.belcorp.salesforce.core.features.utils.withPersonIdentifier
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.profile.cobranzagananciaanterior.CollectionInfoFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.data.contacto.DatosContactoFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.data.desempenio.last6campaigns.DesempenioGrGzFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.data.gz.goals.view.MetaPersonalFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.data.persona.DatosPersonaFragment

class DatosGrFragment : BaseFragment() {

    private val personIdentifier by lazyPersonIdentifier()

    override fun getLayout() = R.layout.fragment_datos_gr

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        incrustarDesplegables()
    }

    private val datosContactoFragment: DatosContactoFragment by lazy {
        DatosContactoFragment.newInstance(personIdentifier)
    }

    private val datosCobranzaCampaniaAnterior: CollectionInfoFragment by lazy {
        CollectionInfoFragment.newInstance(personIdentifier)
    }

    private val metaPersonal: MetaPersonalFragment by lazy {
        MetaPersonalFragment.newInstance(personIdentifier.id, personIdentifier.role)
    }

    private val desempenioGrGzFragment: DesempenioGrGzFragment by lazy {
        DesempenioGrGzFragment.newInstance(personIdentifier.id, personIdentifier.role)
    }

    private val datosPersona: DatosPersonaFragment by lazy {
        DatosPersonaFragment.newInstance(personIdentifier)
    }

    private fun incrustarDesplegables() {
        if (context == null) return

        childFragmentManager.beginTransaction()
            .replace(R.id.flDatosContactoGr, datosContactoFragment)
            .replace(R.id.flCobranzaCampaniaAnterior, datosCobranzaCampaniaAnterior)
            .replace(R.id.flMetaPersonal, metaPersonal)
            .replace(R.id.flDesempenioU6C, desempenioGrGzFragment)
            .replace(R.id.flDatosPersona, datosPersona)
            .commit()
    }

    companion object {

        fun newInstance(personIdentifier: PersonIdentifier) =
            DatosGrFragment()
                .withPersonIdentifier(personIdentifier)

    }

}
