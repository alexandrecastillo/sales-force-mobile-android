package biz.belcorp.salesforce.core.utils

import kotlinx.serialization.json.Json

object JsonUtil {

    val JsonEncodedDefault = Json {
        encodeDefaults = true
        ignoreUnknownKeys = true
    }

}
