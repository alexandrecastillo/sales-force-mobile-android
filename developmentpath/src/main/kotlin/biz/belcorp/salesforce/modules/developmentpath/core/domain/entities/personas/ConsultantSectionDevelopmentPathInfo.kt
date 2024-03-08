package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas

data class ConsultantSectionDevelopmentPathInfo(
    val consultants: List<ConsultantDevelopmentPath>,
    val visitedDays: Int = 0
)
