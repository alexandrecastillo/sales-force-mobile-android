package biz.belcorp.salesforce.core.utils

import org.json.JSONObject


fun <K, V> Map<K, V>.parseToJson(): String? {
    return try {
        JSONObject(this).toString()
    } catch (e: Exception) {
        null
    }
}
