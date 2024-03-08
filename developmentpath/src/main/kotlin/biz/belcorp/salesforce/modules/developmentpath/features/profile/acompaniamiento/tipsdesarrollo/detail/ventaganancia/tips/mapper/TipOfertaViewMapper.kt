package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.ventaganancia.tips.mapper

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.tips.tipsoferta.Oferta
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.tips.tipsoferta.TipOferta
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.helper.AcompaniamientoResourcesProvider
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.ventaganancia.tips.model.TipOfertaModel

class TipOfertaViewMapper(private val innerMapper: OfertaViewMapper) {

    fun map(value: List<TipOferta<List<Oferta>>>): List<TipOfertaModel> {
        return value.map(::parse)
    }

    fun parse(value: TipOferta<List<Oferta>>): TipOfertaModel {
        return TipOfertaModel(
            descripcion = value.descripcion,
            colores = value.colores.map(AcompaniamientoResourcesProvider::fromColor),
            data = innerMapper.map(value.data)
        )
    }
}
