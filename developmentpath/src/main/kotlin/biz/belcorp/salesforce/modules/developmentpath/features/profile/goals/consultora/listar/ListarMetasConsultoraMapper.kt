package biz.belcorp.salesforce.modules.developmentpath.features.profile.goals.consultora.listar

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.metas.MetaConsultora

class ListarMetasConsultoraMapper {

    fun parse(meta: MetaConsultora): MetaConsultoraEnListaModel {
        return MetaConsultoraEnListaModel(
            montoDescripcion = "${meta.monto} ${meta.descripcionTipoMeta}",
            campanaInicio = meta.camapanaInicio,
            campanaFin = meta.campananFin,
            comentario = meta.comentario
        )
    }

    fun parse(metas: List<MetaConsultora>): List<MetaConsultoraEnListaModel> {
        return metas.map { parse(it) }
    }
}
