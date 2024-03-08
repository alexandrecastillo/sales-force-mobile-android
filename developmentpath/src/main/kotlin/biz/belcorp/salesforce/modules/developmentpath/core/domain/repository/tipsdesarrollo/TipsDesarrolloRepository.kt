package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.tipsdesarrollo

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PersonaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.TipDesarrollo

interface TipsDesarrolloRepository {
    fun obtenerTipsDesarrollo(persona: PersonaRdd): TipDesarrollo
}
