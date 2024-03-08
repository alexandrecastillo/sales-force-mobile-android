package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.ruta

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.sql.acompaniamiento.CantidadIntencionPedidoEntity
import biz.belcorp.salesforce.core.entities.sql.path.IntencionPedidoDbModel
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.ruta.IntencionPedidoCloudModel
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.ruta.IntencionPedidoRepository

class IntencionPedidoMapper {

    fun convertirRequestGuardadoAModelo(request: IntencionPedidoRepository.RequestGuardado)
        : IntencionPedidoDbModel {

        return IntencionPedidoDbModel(
            id = 0,
            pasaPedido = request.pasaPedido,
            campania = request.codigoCampania,
            region = request.llaveUa.codigoRegion ?: Constant.EMPTY_STRING,
            zona = request.llaveUa.codigoZona ?: Constant.EMPTY_STRING,
            seccion = request.llaveUa.codigoSeccion ?: Constant.EMPTY_STRING,
            consultoraId = request.llaveUa.consultoraId ?: Constant.CERO.toLong(),
            usuario = request.usuarioCreacion,
            enviado = false)
    }

    fun convertirModelosDbARequestCloud(modelosDb: List<IntencionPedidoDbModel>)
        : List<IntencionPedidoCloudModel> {
        return modelosDb.mapNotNull { convertirModeloDbACloud(it) }
    }

    fun convertirModeloDbACloud(modeloDb: IntencionPedidoDbModel): IntencionPedidoCloudModel? {

        return IntencionPedidoCloudModel(
            pasaPedido = modeloDb.pasaPedido,
            campania = modeloDb.campania ?: return null,
            region = modeloDb.region,
            zona = modeloDb.zona,
            seccion = modeloDb.seccion,
            consultoraId = modeloDb.consultoraId,
            usuario = modeloDb.usuario ?: return null)
    }

    fun convertirCloudAModelo(llaveUA: LlaveUA, cantidad: String): CantidadIntencionPedidoEntity {
        val cantidadIntencionPedidoEntity = CantidadIntencionPedidoEntity()
        cantidadIntencionPedidoEntity.region = llaveUA.codigoRegion ?: Constant.HYPHEN
        cantidadIntencionPedidoEntity.zona = llaveUA.codigoZona ?: Constant.HYPHEN
        cantidadIntencionPedidoEntity.seccion = llaveUA.codigoSeccion ?: Constant.HYPHEN
        cantidadIntencionPedidoEntity.cantidad = cantidad
        return cantidadIntencionPedidoEntity
    }
}
