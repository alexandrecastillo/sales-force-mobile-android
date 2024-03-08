package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.comportamientos

data class ComportamientoPorcentaje(
    val descripcion: String = "",
    val iconoID: Int = -1,
    val porcentaje: Int = 0
) {

    val cumplimiento get() = porcentaje > LIMITE_CUMPLIMIENTO

    companion object {
        private const val LIMITE_CUMPLIMIENTO = 25
    }
}
