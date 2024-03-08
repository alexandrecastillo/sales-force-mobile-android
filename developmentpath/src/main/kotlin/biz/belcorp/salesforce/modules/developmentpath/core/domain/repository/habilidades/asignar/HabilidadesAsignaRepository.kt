package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.habilidades.asignar

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA

interface HabilidadesAsignaRepository {

    fun obtenerPorUA(llaveUA: LlaveUA, campania: String): Array<Long>
    fun obtenerDetallesNoEnviados(): List<DetalleHabilidad>
    fun marcarDetallesComoEnviados()
    fun guardar(request: Request)

    class Request(
        val codigoZona: String?,
        val habilidades: List<Long>,
        val campania: String,
        val codigoRegion: String,
        val codigoSeccion: String?,
        val usuario: String)

    class DetalleHabilidad(val codigoZona: String?,
                           val habilidades: String?,
                           val campania: String,
                           val codigoRegion: String,
                           val codigoSeccion: String?,
                           val usuario: String)
}
