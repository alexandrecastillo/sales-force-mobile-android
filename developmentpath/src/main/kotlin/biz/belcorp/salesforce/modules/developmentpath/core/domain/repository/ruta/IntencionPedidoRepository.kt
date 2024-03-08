package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.ruta

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import io.reactivex.Completable
import io.reactivex.Single

interface IntencionPedidoRepository {
    fun recuperar(llaveUa: LlaveUA): Boolean?

    fun guardar(request: RequestGuardado)

    fun sincronizar(): Completable

    fun obtenerIntencionDePedido(llaveUa: LlaveUA): Single<String>

    data class RequestGuardado(val pasaPedido: Boolean,
                               val codigoCampania: String,
                               val llaveUa: LlaveUA,
                               val usuarioCreacion: String)
}
