package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.habilidades

import biz.belcorp.salesforce.core.entities.sql.habilidades.DetalleHabilidadesLiderazgoRDDEntity
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.habilidades.detalle.HabilidadesDetalleRepository

class HabilidadesLiderazgoDetalleMapper {

    fun parse(entity: DetalleHabilidadesLiderazgoRDDEntity) = HabilidadesDetalleRepository.HabilidadesLiderazgoDetalle(
        habilidadId = entity.codigoHabilidadRDD.toLong(),
        tipo = entity.tipoTexto,
        text = entity.texto ?: Constant.EMPTY_STRING)
}
