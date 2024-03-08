package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.habilidades.propios.model

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.habilidades.Habilidad

class MiHabilidadViewMapper {

    fun parse(habilidades: List<Habilidad>) = habilidades.map { parse(it) }

    private fun parse(habilidad: Habilidad): MiHabilidadModel {
        return MiHabilidadModel(habilidad.descripcion ?: "")
    }
}
