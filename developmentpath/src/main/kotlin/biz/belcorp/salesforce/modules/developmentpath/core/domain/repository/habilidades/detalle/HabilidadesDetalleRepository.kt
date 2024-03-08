package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.habilidades.detalle

interface HabilidadesDetalleRepository {

    fun obtener(): List<HabilidadesLiderazgoDetalle>

    class HabilidadesLiderazgoDetalle(
        val habilidadId: Long,
        val tipo: Int,
        val text: String
    )
}
