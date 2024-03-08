package biz.belcorp.salesforce.modules.creditinquiry.core.domain.exception

interface ErrorBundle {
    val exception: Exception
    val errorMessage: String
}
