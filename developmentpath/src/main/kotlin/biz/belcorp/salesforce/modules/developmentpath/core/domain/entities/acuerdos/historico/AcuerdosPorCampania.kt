package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.acuerdos.historico

import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.acuerdos.Acuerdo

data class AcuerdosPorCampania(
    val campania: Campania,
    val acuerdos: List<Acuerdo>
)
