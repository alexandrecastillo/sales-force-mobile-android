package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.ventaganancia.tips.mapper

import android.content.res.Resources
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.ventaganancia.tips.model.OfertaModel
import biz.belcorp.mobile.components.offers.model.OfferModel
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.modules.developmentpath.R

class MiniOfferModelMapper {

    fun map(values: List<OfertaModel>, res: Resources): List<OfferModel> {
        return values.mapIndexed { index: Int, offer: OfertaModel ->
            parse(index.toString(), offer, res)
        }
    }

    fun parse(index: String = "", value: OfertaModel, res: Resources): OfferModel {
        return OfferModel(
            index,
            value.marca.orEmpty(),
            value.productoNombre.orEmpty(),
            value.productoPrecio.orEmpty(),
            Constant.HYPHEN,
            value.productoImagen.orEmpty(),
            Constant.HYPHEN,
            true,
            subtitle = res.getString(R.string.label_precio),
            textButton = res.getString(R.string.btn_compartir)
        )
    }
}
