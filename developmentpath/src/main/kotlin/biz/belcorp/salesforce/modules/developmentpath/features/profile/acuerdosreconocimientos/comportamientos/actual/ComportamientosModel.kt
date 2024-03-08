package biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.comportamientos.actual

import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.comportamientos.model.ComportamientoModel

data class ComportamientosModel(
    var realizado: Boolean = false,
    var reconocimientos: List<ComportamientoModel> = emptyList(),
    var razon: String = "0/0",
    var progresoPorcentaje: Float = 0f
)
