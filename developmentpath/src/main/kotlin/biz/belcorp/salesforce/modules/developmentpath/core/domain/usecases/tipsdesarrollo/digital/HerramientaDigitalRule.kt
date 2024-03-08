package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.tipsdesarrollo.digital

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.digital.HerramientaDigital
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.tips.Tip
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.tips.Video

class HerramientaDigitalRule(private val list: List<HerramientaDigital>) {

    fun obtenerListaDeTipsYVideosProcesados(): Pair<List<Tip>, List<Video>> {
        val item = obtenerHerramientaDigital()
        return Pair(item.tips, item.videos)
    }

    fun obtenerHerramientaDigital(): HerramientaDigital {
        val herramientaDigital = obtenerPrimeroQueNoEstaSuscrita()
        return herramientaDigital?.let { it } ?: obtenerPrimerElemento()
    }

    fun obtenerPrimeroQueNoEstaSuscrita(): HerramientaDigital? {
        return list.firstOrNull { !it.usa }
    }

    fun obtenerPrimerElemento(): HerramientaDigital {
        return list.first()
    }
}
