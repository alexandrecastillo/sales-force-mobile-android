package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.resultado.mappers

import android.content.Context
import biz.belcorp.salesforce.core.base.BaseTextResolver
import biz.belcorp.salesforce.modules.developmentpath.R

class ResultLastCampaignTextResolver(private val context: Context) : BaseTextResolver() {

    fun getActivesRetention(vararg args: Any?): String {
        val resId = context.getString(R.string.text_actives_retention_cx)
        return stringFormat(resId, *args)
    }

}