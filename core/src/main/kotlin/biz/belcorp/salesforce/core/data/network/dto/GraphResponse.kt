package biz.belcorp.salesforce.core.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class GraphResponse<T>(val data: T)
