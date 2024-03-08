package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.habilidades.reconocer

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.habilidades.HabilidadesReconocidasCampania
import io.reactivex.Completable

interface HabilidadesReconoceRepository {

    fun obtener(personaId: Long, codigoCampania: String): Array<Long>
    fun contarReconocimientos(personaId: Long, codigoCampania: String): Long
    fun obtener(personaId: Long): List<HabilidadesReconocidasCampania>
    fun obtenerReconocidasNoEnvidas(): List<HabilidadReconocida>
    fun marcarReconocidasComoEnviadas()
    fun guardar(request: Request): Completable

    class Request(
        val codigoZona: String?,
        val habilidades: List<Long>,
        val campania: String,
        val codigoRegion: String,
        val codigoSeccion: String?,
        val usuarioReconoce: String,
        val usuarioReconocida: String)

    class HabilidadReconocida(val codigoZona: String?,
                              val habilidades: String?,
                              val campania: String,
                              val codigoRegion: String,
                              val codigoSeccion: String?,
                              val usuarioReconoce: String?,
                              val usuarioReconocida: String?)
}
