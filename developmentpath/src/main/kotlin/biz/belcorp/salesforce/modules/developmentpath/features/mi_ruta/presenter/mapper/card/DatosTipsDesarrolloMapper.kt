package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.presenter.mapper.card

import biz.belcorp.salesforce.core.domain.entities.color.Color
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant.TIPS_DETALLE_CATALOGO_DIGITAL
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant.TIPS_DETALLE_COMPARTE
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant.TIPS_DETALLE_NO_COMPARTE
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.model.card.MiRutaCardModel

class DatosTipsDesarrolloMapper {

    fun map(shareCatalog: Boolean): MiRutaCardModel.DatosTipsDesarrollo {
        val pair = obtainColorDetalle(shareCatalog)
        return MiRutaCardModel.DatosTipsDesarrollo(
            color = pair.first,
            texto = pair.second
        )
    }

    private fun obtainColorDetalle(shareCatalog: Boolean): Pair<Color, String> {
        return Pair(Color.VERDE, "$TIPS_DETALLE_COMPARTE $TIPS_DETALLE_CATALOGO_DIGITAL")
    }
}
