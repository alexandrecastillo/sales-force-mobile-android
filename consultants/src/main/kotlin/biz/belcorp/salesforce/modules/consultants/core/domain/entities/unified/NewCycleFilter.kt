package biz.belcorp.salesforce.modules.consultants.core.domain.entities.unified

class NewCycleFilter(
    val new5d5: Boolean = false,
    val new4d4: Boolean = false,
    val new3d3: Boolean = false,
    val new2d2: Boolean = false,
    val new1d1: Boolean = false
) : Filterable
