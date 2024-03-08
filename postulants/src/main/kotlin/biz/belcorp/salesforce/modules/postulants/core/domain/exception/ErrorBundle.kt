package biz.belcorp.salesforce.modules.postulants.core.domain.exception

interface ErrorBundle {
    val exception: Exception
    val errorMessage: String
}
