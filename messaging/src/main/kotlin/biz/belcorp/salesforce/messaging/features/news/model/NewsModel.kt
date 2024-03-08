package biz.belcorp.salesforce.messaging.features.news.model

import biz.belcorp.salesforce.core.constants.Constant


class NewsModel {

    var id: Long = 0
    var topic: String = Constant.EMPTY_STRING
    var title: String = Constant.EMPTY_STRING
    var message: String = Constant.EMPTY_STRING

    var imageUrl: String? = null
    var videoUrl: String? = null
    var closeIconColor: String? = null
    var closeBgColor: String? = null
    var action: String? = null

}
