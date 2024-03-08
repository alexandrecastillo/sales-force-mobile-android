package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas

data class AvanceVisitas(
    val visitadas: Int,
    val planificadas: Int,
    val tipoHijas: String
) {
    val progreso: Double
        get() {
            return if (planificadas == 0) 0.0
            else visitadas.toDouble() * 100 / planificadas
        }
}
