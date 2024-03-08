package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.visitas.model

import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO

data class ConsultantInfoModel(
    val newConsultantVisited: Int = NUMBER_ZERO,
    val totalNewConsultants: Int = NUMBER_ZERO,
    val progressNewConsultants: Int = NUMBER_ZERO,
    val consultantVisited: Int = NUMBER_ZERO,
    val totalConsultants: Int = NUMBER_ZERO,
    val progressConsultants: Int = NUMBER_ZERO,
    val bpMakeVisitsQuantity: Int = NUMBER_ZERO,
    val bpTotal: Int = NUMBER_ZERO,
    val progressBpVisits: Int = NUMBER_ZERO,
    val bpWithMore8DaysVisitPercentage: Int = NUMBER_ZERO,
    val bpWithMore8DaysVisits: Int = NUMBER_ZERO,
    val bpVisitsAtLeastOneDay: Int = NUMBER_ZERO,
)
