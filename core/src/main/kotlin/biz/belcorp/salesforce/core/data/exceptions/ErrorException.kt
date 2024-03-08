package biz.belcorp.salesforce.core.data.exceptions

import biz.belcorp.salesforce.core.utils.Logger.loge

class ErrorException : Exception {

    constructor() : super() {
        loge(javaClass.simpleName, "", this)
    }

    constructor(message: String?) : super(message) {
        loge(javaClass.simpleName, message, this)
    }

    constructor(message: String?, cause: Throwable) : super(message, cause) {
        loge(javaClass.simpleName, message, cause)
    }

    constructor(cause: Throwable) : super(cause) {
        loge(javaClass.simpleName, cause.message ?: "", cause)
    }
}
