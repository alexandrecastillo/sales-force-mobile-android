package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.ventas.view

import android.content.Context
import biz.belcorp.salesforce.core.base.BaseTextResolver
import biz.belcorp.salesforce.modules.developmentpath.R

class SaleConsultantTextResolver(val context: Context) : BaseTextResolver() {

    fun formatCampaignWithPrefix(vararg args: Any?): String {
        return stringFormat(context.getString(R.string.text_campaign_with_prefix), *args)
    }

    fun formatGainSubTitle(vararg args: Any?): String {
        val format = context.getString(R.string.text_gain_subtitle)
        return stringFormat(format, *args)
    }

    fun formatCurrency(vararg args: Any?): String {
        val format = context.getString(R.string.number_with_currency)
        return stringFormat(format, *args)
    }

    fun formatAmountWithEmoji(vararg args: Any?): String {
        val format = context.getString(R.string.label_descripcion_indicador)
        return stringFormat(format, *args)
    }

    fun getHighValueOrder() = context.getString(R.string.label_venta_pedido_alto_valor)

    fun getLowValueOrder() = context.getString(R.string.label_venta_pedido_bajo_valor)

}
