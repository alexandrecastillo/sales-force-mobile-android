package biz.belcorp.salesforce.analytics.core.domain.entities


class Event(
    var logName: String,
    var category: String,
    var action: String,
    var label: String,
    var screenName: String
) {
    var variant: String = Ambiente.getAmbiente()
}
