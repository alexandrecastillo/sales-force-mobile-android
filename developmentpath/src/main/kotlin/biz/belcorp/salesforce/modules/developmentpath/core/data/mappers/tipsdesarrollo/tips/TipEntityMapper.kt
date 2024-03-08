package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.tipsdesarrollo.tips

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tips.TipEntity
import biz.belcorp.salesforce.core.utils.Mapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.tips.Tip

class TipEntityMapper : Mapper<TipEntity, Tip>() {
    override fun map(value: TipEntity): Tip {
        return Tip(
            id = value.id,
            descripcion = value.descripcion,
            colores = value.tipoColores,
            orden = value.orden)
    }

    override fun reverseMap(value: Tip): TipEntity {
        return TipEntity(
            id = value.id,
            descripcion = value.descripcion,
            colores = value.tipoColores,
            orden = value.orden
        )
    }
}
