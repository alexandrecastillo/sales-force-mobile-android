package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.sesion

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.Persona
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.sesion.persona
import biz.belcorp.salesforce.core.domain.entities.session.Sesion

var Sesion.persona
    get() = personaOld as? Persona? ?: Persona()
    set(value) {
        personaOld = value
    }
