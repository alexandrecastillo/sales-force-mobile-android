package biz.belcorp.salesforce.modules.consultants.features.filters.utils

import biz.belcorp.salesforce.core.utils.positionOf
import biz.belcorp.salesforce.modules.consultants.features.filters.baseunified.model.ConsultantModel

object ConsultantListSorterUtils {

    private val sortedSegmentKeys by lazy {
        listOf(
            "Nueva",
            "Nuevas",
            "Nueva 1d1",
            "Nueva 2d2",
            "Nueva 3d3",
            "Nueva 4d4",
            "Nueva 5d5",
            "Nueva 6d6",
            "Nueva PEG",
            "Constantes 3",
            "Constantes 2",
            "Constantes 1",
            "Top",
            "Tops",
            "Brilla",
            "Consultora",
            "Coral",
            "Ambar",
            "Perla",
            "Topacio",
            "Brillante",
            "Nueva Inconstante",
            "Nueva Inconstante 1d1",
            "Nueva Inconstante 2d2",
            "Nueva Inconstante 3d3",
            "Nueva Inconstante 4d4",
            "Nueva Inconstante 5d5",
            "Nueva Inconstante 6d6",
            "Inconstantes"
        )
    }

    fun sortBySegment(values: List<ConsultantModel>) = values.let { model ->
        model.sortedWith(compareBy({ sortedSegmentKeys.positionOf(it.segment) }, { it.name }))
    }

}
