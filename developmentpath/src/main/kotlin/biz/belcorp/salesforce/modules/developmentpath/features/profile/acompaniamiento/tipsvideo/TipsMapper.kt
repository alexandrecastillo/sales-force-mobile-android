package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsvideo

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas.GrupoTipsVisita

class TipsMapper {
    fun map(entity: GrupoTipsVisita): TipsViewModel {
        return TipsViewModel(
            entity.tituloGrupo,
            entity.tips.map { it.descripcion }
                .toTypedArray(),
            entity.tituloVideo,
            entity.urlVideo)
    }
}
