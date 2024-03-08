package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.ruta

import biz.belcorp.salesforce.core.data.repository.auth.SafeApiCallHelper
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.ruta.data.IntencionPedidoDb
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.llaveua.LlaveUAMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.ruta.IntencionPedidoMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.SyncRestApi
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.ruta.IntencionPedidoRepository
import io.reactivex.Completable
import io.reactivex.Single

class IntencionPedidoDataRepository(
    private val mapper: IntencionPedidoMapper,
    private val intencionPedidoDb: IntencionPedidoDb,
    private val syncRestApi: SyncRestApi,
    private val llaveUAMapper: LlaveUAMapper,
    private val apiCallHelper: SafeApiCallHelper
) : IntencionPedidoRepository {

    override fun recuperar(llaveUa: LlaveUA): Boolean? {
        return intencionPedidoDb.recuperar(llaveUa)?.pasaPedido
    }

    override fun guardar(request: IntencionPedidoRepository.RequestGuardado) {
        val modelo = mapper.convertirRequestGuardadoAModelo(request)

        modelo.save()
    }

    override fun sincronizar(): Completable {
        val noEnviadosDb = intencionPedidoDb.recuperarNoEnviados()
        val noEnviadosCloud = mapper.convertirModelosDbARequestCloud(noEnviadosDb)

        return apiCallHelper.safeSingleApiCall {
            syncRestApi.enviarIntencionesPedido(noEnviadosCloud)
        }
            .toCompletable()
            .andThen(intencionPedidoDb.marcarEnviados())
    }

    override fun obtenerIntencionDePedido(llaveUa: LlaveUA): Single<String> {
        return apiCallHelper.safeSingleApiCall {
            syncRestApi.obtenerIntencionDePedido(llaveUAMapper.obtenerUAComoString(llaveUa))
        }
            .flatMapCompletable {
                intencionPedidoDb.guardarCantidadIntencionPedido(
                    mapper.convertirCloudAModelo(llaveUa, it.resultado)
                )
            }.andThen(intencionPedidoDb.obtenerCantidadIntencionPedido(llaveUa))
            .onErrorResumeNext(intencionPedidoDb.obtenerCantidadIntencionPedido(llaveUa))
    }
}
