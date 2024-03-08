package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.digital.herramientasdigitales

import biz.belcorp.mobile.components.design.chip.ChipModel
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.digital.HerramientaDigital

class HerramientaDigitalViewMapper {

    fun map(value: HerramientaDigital): ChipModel {
        return ChipModel(
            key = value.id.toString(),
            text = value.nombre,
            isChecked = value.usa
        )
    }

    fun map(values: List<HerramientaDigital>): List<ChipModel> {
        return values.map { map(it) }
    }
}
