package biz.belcorp.salesforce.core.utils

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.Json

fun <T> safeJsonParse(deserializer: DeserializationStrategy<T>, string: String?): T? {
    try {
        return Json.decodeFromString(deserializer, string ?: return null)
    } catch (e: Exception) {
        return null
    }
}

