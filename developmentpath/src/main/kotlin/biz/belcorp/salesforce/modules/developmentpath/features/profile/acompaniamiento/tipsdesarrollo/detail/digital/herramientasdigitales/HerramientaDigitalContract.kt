package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.digital.herramientasdigitales

import biz.belcorp.mobile.components.design.chip.ChipModel
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.helper.Params

interface HerramientaDigitalContract {
    interface View {
        fun mostrarHerramientasDigitales(data: List<ChipModel>)
        fun mostrarHerramientasDigitalesVacio()
    }
    interface Presenter {
        fun obtener(params: Params)
    }
}
