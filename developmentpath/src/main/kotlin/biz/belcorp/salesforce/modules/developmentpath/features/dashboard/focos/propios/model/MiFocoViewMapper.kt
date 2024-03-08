package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.focos.propios.model

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.focos.Foco

class MiFocoViewMapper {

    fun parse(foco: Foco) = MiFocoModel(foco.descripcion ?: "")
}
