package biz.belcorp.salesforce.modules.developmentpath.features.profile.data.gz.goals.view

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.metas.MetaPersonal
import biz.belcorp.salesforce.modules.developmentpath.features.profile.data.gz.goals.model.MetaPersonalModel

class MetaPersonalMapper {

    fun parse(modelo: MetaPersonal): MetaPersonalModel {
        return MetaPersonalModel(
            metaId = modelo.metaId,
            personaId = modelo.personaId,
            descripcion = modelo.descripcion,
            fecha = modelo.fecha
        )
    }

    fun parse(modelos: List<MetaPersonal>) = modelos.map { parse(it) }

    fun parse(modelo: MetaPersonalModel): MetaPersonal {
        return MetaPersonal(
            metaId = modelo.metaId,
            personaId = modelo.personaId,
            descripcion = modelo.descripcion,
            fecha = modelo.fecha
        )
    }
}
