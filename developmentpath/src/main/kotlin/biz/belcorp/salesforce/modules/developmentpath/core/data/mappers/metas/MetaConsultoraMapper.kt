package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.metas

import biz.belcorp.salesforce.core.entities.sql.datos.MetasEntity
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.metas.MetaConsultora

class MetaConsultoraMapper {

    fun parse(metaConsultora: MetaConsultora): MetasEntity {
        val meta = MetasEntity()
        meta.consultoraId = metaConsultora.consultoraId
        meta.monto = metaConsultora.monto
        meta.idTipoMeta = metaConsultora.idTipoMeta
        meta.descripcion = metaConsultora.descripcionTipoMeta
        meta.campInic = metaConsultora.camapanaInicio
        meta.campFin = metaConsultora.campananFin
        meta.observaciones = metaConsultora.comentario
        meta.fechaRegistro = metaConsultora.fecha
        return meta
    }

    fun reverseParse(metasEntity: MetasEntity) =
        MetaConsultora(metasEntity.consultoraId ?: 0,
            metasEntity.idTipoMeta ?: 0,
            metasEntity.descripcion.orEmpty(),
            metasEntity.observaciones.orEmpty(),
            metasEntity.monto.orEmpty(),
            metasEntity.campInic.orEmpty(),
            metasEntity.campFin.orEmpty(),
            metasEntity.fechaRegistro.orEmpty())

    fun reverseParse(metas: List<MetasEntity>) = metas.map { reverseParse(it) }
}
