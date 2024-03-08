package biz.belcorp.salesforce.messaging.features.news

import biz.belcorp.salesforce.messaging.features.news.model.NewsModel


sealed class NewsViewState {

    class ShowDetail(val model: NewsModel) : NewsViewState()

}
