package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.tips

import biz.belcorp.salesforce.core.entities.sql.tips.rdd.DetalleTipsVisitaRDDEntity
import biz.belcorp.salesforce.core.entities.sql.tips.rdd.TipsVisitaRDDEntity
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas.GrupoTipsVisita
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas.TipVisita

class TipsVisitaMapper {

    fun map(model: DetalleTipsVisitaRDDEntity): TipVisita {
        return TipVisita(
            model.id.toLong(),
            model.descripcion)
    }

    fun map(models: List<DetalleTipsVisitaRDDEntity>): List<TipVisita> =
        models.map { map(it) }

    fun map(model: TipsVisitaRDDEntity): GrupoTipsVisita {
        return GrupoTipsVisita(
            model.descripcion,
            model.urlDesc,
            model.urlVideo)
    }
}
