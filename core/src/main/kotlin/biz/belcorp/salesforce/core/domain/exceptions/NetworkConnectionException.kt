package biz.belcorp.salesforce.core.domain.exceptions

class NetworkConnectionException : Exception {
    constructor() : super()
    constructor(cause: Throwable) : super(cause)
}
