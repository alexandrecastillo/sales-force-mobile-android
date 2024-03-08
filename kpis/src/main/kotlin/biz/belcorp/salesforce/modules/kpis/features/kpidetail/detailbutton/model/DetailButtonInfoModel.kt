package biz.belcorp.salesforce.modules.kpis.features.kpidetail.detailbutton.model

import biz.belcorp.salesforce.core.constants.Constant

class DetailButtonInfoModel(
    @DetailButtonType val type: Int,
    val title: String = Constant.EMPTY_STRING
)
