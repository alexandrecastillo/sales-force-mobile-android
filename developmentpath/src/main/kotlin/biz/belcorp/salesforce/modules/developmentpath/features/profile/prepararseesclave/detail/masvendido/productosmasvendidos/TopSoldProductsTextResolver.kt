package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.masvendido.productosmasvendidos

import android.content.Context
import biz.belcorp.salesforce.core.base.BaseTextResolver
import biz.belcorp.salesforce.modules.developmentpath.R

class TopSoldProductsTextResolver(private val context: Context) : BaseTextResolver() {

    fun parsePosition(vararg any: Any): String {
        val format = context.getString(R.string.text_profile_most_sold_products_pos)
        return stringFormat(format, *any)
    }

}
