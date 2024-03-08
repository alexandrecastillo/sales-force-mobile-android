package biz.belcorp.salesforce.modules.developmentpath.features.profile.goals.sociaempresaria.mapper

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.metas.MetaSocia
import biz.belcorp.salesforce.modules.developmentpath.features.profile.goals.sociaempresaria.model.MetaSociaModelo

class MetaSociaModelMapper {

    fun reverseParse(modelo: MetaSociaModelo): MetaSocia {
        return MetaSocia(
            id = modelo.id,
            descripcion = modelo.descripcion,
            fecha = modelo.fecha
        )
    }

    fun parse(modelo: MetaSocia): MetaSociaModelo {
        return MetaSociaModelo(
            id = modelo.id,
            fecha = modelo.fecha,
            descripcion = modelo.descripcion
        )
    }
}
