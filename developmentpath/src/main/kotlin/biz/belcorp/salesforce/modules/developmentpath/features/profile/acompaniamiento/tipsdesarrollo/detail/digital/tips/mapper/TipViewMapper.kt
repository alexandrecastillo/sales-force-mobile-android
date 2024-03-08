package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.digital.tips.mapper

import biz.belcorp.salesforce.core.utils.Mapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.tips.Tip
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.helper.AcompaniamientoResourcesProvider
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.digital.tips.model.TipModel

class TipViewMapper : Mapper<Tip, TipModel>() {
    override fun map(value: Tip): TipModel {
        return TipModel(
            id = value.id,
            descripcion = value.descripcion,
            colores = value.colores.map { AcompaniamientoResourcesProvider.fromColor(it) },
            orden = value.orden
        )
    }

    override fun reverseMap(value: TipModel) = Tip()
}
