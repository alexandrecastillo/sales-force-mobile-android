package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.reconocimientos

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import io.reactivex.Completable

interface ReconocimientosRepository {
    fun sincronizar(): Completable
    fun guardar(request: GuardarRequest)
    fun contarReconocimientos(personaId: Long, codigoCampania: String): Long

    data class GuardarRequest(
        val codigoCampania: String,
        val llaveUA: LlaveUA,
        val idsReconocidos: List<Long>,
        val idPersonaSesion: Long?
    )
}
