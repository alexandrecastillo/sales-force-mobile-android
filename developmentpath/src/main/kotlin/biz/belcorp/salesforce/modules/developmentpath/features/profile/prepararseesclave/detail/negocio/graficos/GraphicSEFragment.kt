package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos

import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.features.utils.lazyPersonIdentifier
import biz.belcorp.salesforce.core.features.utils.withPersonIdentifier

abstract class GraphicSEFragment : BaseFragment() {

    protected val personIdentifier by lazyPersonIdentifier()

    companion object {
        inline fun <reified T : GraphicSEFragment> newInstance(personIdentifier: PersonIdentifier): T {
            return T::class.java.newInstance()
                .withPersonIdentifier(personIdentifier)
        }
    }
}
