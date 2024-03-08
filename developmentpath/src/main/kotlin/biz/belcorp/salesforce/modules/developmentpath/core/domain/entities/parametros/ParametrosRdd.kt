package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.parametros

data class ParametrosRdd(
    val maxVisitasPorDia: Int = 0,
    val horaInicio: Int = 0,
    val horaFin: Int,
    val rol: String? = null,
    val parametros: String? = null,
    val duracionVisitaHoras: Int = 0,
    val radioBusqueda: Int = 5000,
    val distanciaMin: Int = 100
)
